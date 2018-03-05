package priv.henryyu.privatebox.service;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import net.jodah.expiringmap.ExpirationPolicy;
import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.entity.File;
import priv.henryyu.privatebox.entity.InvitationCode;
import priv.henryyu.privatebox.entity.LoginDetails;
import priv.henryyu.privatebox.entity.Role;
import priv.henryyu.privatebox.entity.User;
import priv.henryyu.privatebox.jms.Producer;
import priv.henryyu.privatebox.model.RegisterEmailEntity;
import priv.henryyu.privatebox.model.request.RegisterUser;
import priv.henryyu.privatebox.model.response.DataGrid;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.model.response.error.ResponseCode;
import priv.henryyu.privatebox.repository.InvitationCodeRepository;
import priv.henryyu.privatebox.repository.LoginDetailsRepository;
import priv.henryyu.privatebox.repository.RoleRepository;
import priv.henryyu.privatebox.repository.UserRepository;
import priv.henryyu.privatebox.siglton.Siglton;
import priv.henryyu.privatebox.tools.Encrypt;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.jms.Destination;
import javax.persistence.EntityNotFoundException;

/**
 * UserService class
 * 
 * @author HenryYu
 * @date 2017/12/21
 * @version 1.1.0
 */
@Service
public class UserService extends BaseComponent implements UserDetailsService {
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	LoginDetailsRepository loginDetailsRepository;
	@Autowired
	InvitationCodeRepository invitationCodeRepository;
	@Autowired
	private Producer producer;

	/**
	 * Spring-boot-security 用户登陆方法 User login
	 * 
	 * @param 传入用户姓名
	 * Username
	 * @return 数据库交互用户 User
	 * @throws Exception
	 */
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not exist");
		}
		// System.out.println("username:"+user.getUsername()+";password:"+user.getPassword());
		return user;
	}

	/**
	 * 新用户注册方法 Register new user
	 * 
	 * @param 传入注册用户模板
	 *            RegisterUser
	 * @return 结果 ResponseMessage
	 * @throws Exception
	 */
	public ResponseMessage<User> registerUser(RegisterUser registerUser) {
		ResponseMessage<User> responseMessage = new ResponseMessage<User>();
		Locale locale = LocaleContextHolder.getLocale();
		User user = newUserTransform(registerUser);
		if (userRepository.findByUsername(registerUser.getUsername()) != null) {
			String message = messageSource.getMessage("userAlreadyExist", null, locale);
			responseMessage.setMessage(message);
			responseMessage.setCode(ResponseCode.UserAlreadyExist);
			return responseMessage;
		}
		InvitationCode invitationCode = invitationCodeRepository.findOne(registerUser.getInvitationCode());
		if (invitationCode == null) {
			String message = messageSource.getMessage("errorInvitationCode", null, locale);
			responseMessage.setMessage(message);
			responseMessage.setCode(ResponseCode.ErrorInvitationCode);
			return responseMessage;
		}
		if (invitationCode.isUsed()) {
			String message = messageSource.getMessage("usedInvitationCode", null, locale);
			responseMessage.setMessage(message);
			responseMessage.setCode(ResponseCode.UsedInvitationCode);
			return responseMessage;
		}
		responseMessage=sendActiveEmail(user.getUsername());
		if(responseMessage.getCode()!=ResponseCode.Success) {
			return responseMessage;
		}
		user.setEnabled(false);
		User savedUser = userRepository.save(user);
		invitationCode.setUsedUsername(savedUser.getUsername());
		invitationCode.setUsed(true);
		invitationCode.setUsedTime(new Timestamp(System.currentTimeMillis()));
		invitationCodeRepository.save(invitationCode);
		responseMessage.setCode(ResponseCode.Success);
		String message = messageSource.getMessage("registerSuccess", null, locale);
		responseMessage.setMessage(message + "------" + savedUser.getUsername());
		getSession().setAttribute("registerUsername", savedUser.getUsername());
		sendActiveEmail(user.getUsername());
		// jpaResponseMessage.setData(savedUser);
		return responseMessage;

	}
	public ResponseMessage activeAccount(String registerUsername,String activationCode) {
		ResponseMessage responseMessage=new ResponseMessage();
		User user=userRepository.findByUsername(registerUsername);
		if(user==null) {
			responseMessage.setCode(ResponseCode.Exception);
			return responseMessage;
		}
		RegisterEmailEntity registerEmailEntity=Siglton.INSTANCE.getRegisterExpiringMap().get(registerUsername);
		if(registerEmailEntity==null) {
			responseMessage.setCode(ResponseCode.OutOfTimeLimit);
		}
		if(!registerEmailEntity.getActivationCode().equals(activationCode)) {
			responseMessage.setCode(ResponseCode.UserAlreadyActived);
		}
		if(registerEmailEntity!=null&&registerEmailEntity.getActivationCode().equals(activationCode)) {
			Siglton.INSTANCE.getRegisterExpiringMap().remove(registerUsername);
			responseMessage.setCode(ResponseCode.Success);
			user.setEnabled(true);
			userRepository.save(user);
		}
		
		return responseMessage;
	}
	public ResponseMessage sendActiveEmail(String registerUsername) {
		ResponseMessage responseMessage=new ResponseMessage();
		RegisterEmailEntity registerEmailEntity=Siglton.INSTANCE.getRegisterExpiringMap().get(registerUsername);
		if(registerEmailEntity==null) {
			registerEmailEntity=new RegisterEmailEntity();
			registerEmailEntity.setRegisterUsername(registerUsername);
			Siglton.INSTANCE.getRegisterExpiringMap().put(registerUsername, registerEmailEntity, ExpirationPolicy.ACCESSED, 5, TimeUnit.MINUTES);
			Destination destination = new ActiveMQQueue("sendEmail.queue"); 
	        producer.sendMessage(destination, registerEmailEntity);
	        responseMessage.setCode(ResponseCode.Success);
		}else if(registerEmailEntity!=null){
			if(registerEmailEntity.getTotalRequestTimes()<3) {
			registerEmailEntity.setTotalRequestTimes(registerEmailEntity.getTotalRequestTimes()+1);
			Destination destination = new ActiveMQQueue("sendEmail.queue"); 
	        producer.sendMessage(destination, registerEmailEntity);
	        responseMessage.setCode(ResponseCode.Success);
			}
			if(registerEmailEntity.getTotalRequestTimes()>=3) {
				responseMessage.setCode(ResponseCode.Exception);
				responseMessage.setMessage("Too Many Request");
			}
		}	
		return responseMessage;
	}
	/**
	 * 初始化权限操作 Authority Initialization
	 * 
	 * @param RoleRepository
	 * 
	 * @return null
	 * 
	 * @throws Exception
	 */

	public void addRoles(RoleRepository roleRepository) {
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);
	}

	/**
	 * 将注册请求用户转化为数据库操作用户 
	 * Transform RegisterUser to User
	 * 
	 * @param 传入注册用户模板
	 * RegisterUser
	 * @return 转化后的数据库交互用户 User
	 * @throws Exception
	 */
	public User newUserTransform(RegisterUser registerUser) {
		User user = new User();
		copyProperties(registerUser, user);
		user.setPassword(
				PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(registerUser.getSha512Password()));
		Role role = new Role();
		role.setName("ROLE_USER");
		user.addRole(role);
		return user;
	}
	/**
	 * 判断是否是第一次使用本系统
	 * Judge is first time use PrivateBox
	 * @return true if it's first time
	 */
	public boolean isFirstTime() {
		if (userRepository.findByUsername("admin") != null) {
			return false;
		}
		addRoles(roleRepository);
		User user = new User();
		user.setUsername("admin");
		String defaultPassword = "admin";
		String SHA512Password = Encrypt.EncodeString(defaultPassword, "SHA-512");
		user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(SHA512Password));
		Role userRole = new Role();
		userRole.setName("ROLE_USER");
		Role adminRole = new Role();
		adminRole.setName("ROLE_ADMIN");
		user.addRole(userRole);
		user.addRole(adminRole);
		userRepository.save(user);
		return true;
	}
}
