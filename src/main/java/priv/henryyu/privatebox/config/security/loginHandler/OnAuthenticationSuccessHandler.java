package priv.henryyu.privatebox.config.security.loginHandler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月22日下午5:40:11
 * @version 1.0.0
 */
@Component
public class OnAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
	        response.setContentType("application/json;charset=utf-8");
	        PrintWriter out = response.getWriter();
	        out.write("{\"status\":\"success\"}");
	        out.flush();
	        out.close();
	}

}
 

