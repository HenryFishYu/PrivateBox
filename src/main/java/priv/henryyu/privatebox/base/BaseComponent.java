package priv.henryyu.privatebox.base;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import priv.henryyu.privatebox.entity.User;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月4日下午1:13:44
 * @version 1.0.0
 */

@Component
public class BaseComponent {
	@Autowired
	protected HttpServletRequest request;
	@Autowired
	protected HttpServletResponse response;
	@Autowired
	protected MessageSource messageSource;
	
	public HttpSession getSession() {
		return request.getSession();
	}
	public User getUser() {
		if(SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal() instanceof String) {
			return null;
		}
		return (User) SecurityContextHolder.getContext()
			    .getAuthentication()
			    .getPrincipal();
	}
}
 

