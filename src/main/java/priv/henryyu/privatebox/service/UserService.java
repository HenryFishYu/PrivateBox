package priv.henryyu.privatebox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;

import priv.henryyu.privatebox.entity.Role;
import priv.henryyu.privatebox.entity.User;
import priv.henryyu.privatebox.model.request.RegisterUser;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.model.response.error.ResponseError;
import priv.henryyu.privatebox.repository.UserRepository;
import static org.springframework.beans.BeanUtils.copyProperties;

import java.util.Locale;

/**
 * UserService class
 * 
 * @author HenryYu
 * @date 2017/12/21
 * @version 1.0.0
 */
@Service
public class UserService implements UserDetailsService{
	@Autowired
    UserRepository userRepository;
	@Autowired
	private MessageSource messageSource;
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
            throw new UsernameNotFoundException("用户名不存在");
        }
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
		User savedUser=userRepository.save(user);
		jpaResponseMessage.setError(ResponseError.Success);
		String message = messageSource.getMessage("registerSuccess",null,locale);
		jpaResponseMessage.setMessage(message+"------"+savedUser.getUsername());
		//jpaResponseMessage.setData(savedUser);
		return jpaResponseMessage;
		
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
	public static User newUserTransform(RegisterUser registerUser) {
		User user=new User();
		copyProperties(registerUser, user);
		user.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(registerUser.getSha512Password()));
		Role role=new Role();
		role.setName("ROLE_USER");
		user.addRole(role);
		return user;
	}
}
