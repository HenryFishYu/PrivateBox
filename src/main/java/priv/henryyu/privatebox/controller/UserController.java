package priv.henryyu.privatebox.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import priv.henryyu.privatebox.entity.User;
import priv.henryyu.privatebox.model.request.RegisterUser;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.service.UserService;
/**
 * UserController class
 * 
 * @author HenryYu
 * @date 2017/12/15
 * @version 1.0.0
 */
@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	UserService userService;
	/**
	* 新用户注册
	* Register new user controller
	* @param 传入注册用户模板 
	* RegisterUser
	* @return 结果
	* ResponseMessage
	* @throws Exception
	*/
	@RequestMapping("/register")
	@ResponseBody
	public ResponseMessage<User> register(RegisterUser registerUser) {
		ResponseMessage<User> responseMessage=userService.registerUser(registerUser);
		return responseMessage;
	}
	
	@RequestMapping("/index")
	public ModelAndView index() {
		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
		request.getSession().setAttribute("userDetails", userDetails);
		request.getSession().removeAttribute("loginError");
		ModelAndView modelAndView=new ModelAndView("user/index");
		return modelAndView;
	}
}
