package priv.henryyu.privatebox.service;

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

import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.entity.InvitationCode;
import priv.henryyu.privatebox.entity.LoginDetails;
import priv.henryyu.privatebox.entity.Role;
import priv.henryyu.privatebox.entity.User;
import priv.henryyu.privatebox.model.request.RegisterUser;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.model.response.error.ResponseError;
import priv.henryyu.privatebox.repository.InvitationCodeRepository;
import priv.henryyu.privatebox.repository.LoginDetailsRepository;
import priv.henryyu.privatebox.repository.RoleRepository;
import priv.henryyu.privatebox.repository.UserRepository;
import static org.springframework.beans.BeanUtils.copyProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityNotFoundException;

/**
 * UserService class
 * 
 * @author HenryYu
 * @date 2017/12/21
 * @version 1.1.0
 */
@Service
public class UserService extends BaseComponent implements UserDetailsService{
	@Autowired
    UserRepository userRepository;
	@Autowired
    RoleRepository roleRepository;
	@Autowired
	LoginDetailsRepository loginDetailsRepository;
	@Autowired
    InvitationCodeRepository invitationCodeRepository;
	/**
	* Spring-boot-security
	* 用户登陆方法
	* User login
	* @param 传入用户姓名
	* Username
	* @return 数据库交互用户
	* User
	* @throws Exception
	*/
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepository.findByUsername(username);
		if (user == null) {
            throw new UsernameNotFoundException("User not exist");
        }
		LoginDetails loginDetails=new LoginDetails(request);
		user.addLoginDetails(loginDetails);
		loginDetailsRepository.save(loginDetails);
		userRepository.save(user);
        System.out.println("username:"+user.getUsername()+";password:"+user.getPassword());
        return user;
	}
	/**
	* 新用户注册方法
	* Register new user
	* @param 传入注册用户模板 
	* RegisterUser
	* @return 结果
	* ResponseMessage
	* @throws Exception
	*/
	public ResponseMessage<User> registerUser(RegisterUser registerUser) {
		ResponseMessage<User> jpaResponseMessage=new ResponseMessage<User>();
		Locale locale=  LocaleContextHolder.getLocale();
		User user=newUserTransform(registerUser);
		if(userRepository.findByUsername(registerUser.getUsername())!=null) {
			String message = messageSource.getMessage("userAlreadyExist",null,locale);
			jpaResponseMessage.setMessage(message);
			jpaResponseMessage.setError(ResponseError.UserAlreadyExist);
			return jpaResponseMessage;
		}
		InvitationCode invitationCode=invitationCodeRepository.findOne(registerUser.getInvitationCode());
		if(invitationCode==null) {
			String message = messageSource.getMessage("errorInvitationCode",null,locale);
			jpaResponseMessage.setMessage(message);
			jpaResponseMessage.setError(ResponseError.ErrorInvitationCode);
			return jpaResponseMessage;
		}
		if(invitationCode.isUsed()) {
			String message = messageSource.getMessage("usedInvitationCode",null,locale);
			jpaResponseMessage.setMessage(message);
			jpaResponseMessage.setError(ResponseError.UsedInvitationCode);
			return jpaResponseMessage;
		}
		User savedUser=userRepository.save(user);
		invitationCodeRepository.save(invitationCode);
		savedUser.usedInvitationCode(invitationCode);
		savedUser=userRepository.save(savedUser);
		jpaResponseMessage.setError(ResponseError.Success);
		String message = messageSource.getMessage("registerSuccess",null,locale);
		jpaResponseMessage.setMessage(message+"------"+savedUser.getUsername());
		//jpaResponseMessage.setData(savedUser);
		return jpaResponseMessage;
		
	}
	
	/**
	* 初始化权限操作
	* Authority Initialization 
	* @param RoleRepository
	* 
	* @return null
	* 
	* @throws Exception
	*/
	
	public void addRoles(RoleRepository roleRepository) {
		Role userRole=new Role();
		userRole.setName("ROLE_USER");
		roleRepository.save(userRole);
		Role adminRole=new Role();
		adminRole.setName("ROLE_ADMIN");
		roleRepository.save(adminRole);
	}
	/**
	* 将注册请求用户转化为数据库操作用户
	* Transform RegisterUser to User
	* @param 传入注册用户模板 
	* RegisterUser 
	* @return 转化后的数据库交互用户
	* User
	* @throws Exception
	*/
	public User newUserTransform(RegisterUser registerUser) {
		User user=new User();
		copyProperties(registerUser, user);
		user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(registerUser.getSha512Password()));
		Role role=new Role();
		role.setName("ROLE_USER");
		try{
			user.addRole(role);
		}catch (EntityNotFoundException e) {
			addRoles(roleRepository);
			user.addRole(role);
		}
		return user;
	}
	
	public boolean isFirstTime() {
		if(userRepository.findByUsername("admin")!=null) {
			return false;
		}
		User user=new User();
		user.setUsername("admin");
		user.setPassword("{bcrypt}$2a$10$fv7vd1rn8Xc1yqOMW2wIf.8Zcs1JkSxcRVH/uDZ3VNy2BbZN2n2Re");
		List<Role> roles=new ArrayList<Role>();
		Role userRole=new Role();
		userRole.setName("ROLE_USER");
		Role adminRole=new Role();
		adminRole.setName("ROLE_ADMIN");
		user.addRole(userRole);
		user.addRole(adminRole);
		userRepository.save(user);
		return true;
	}
}
