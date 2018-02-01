package priv.henryyu.privatebox.config.security.loginHandler;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月23日上午9:14:19
 * @version 1.0.0
 */
@Component
public class OnAuthenticationFailureHandler implements AuthenticationFailureHandler{
	@Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        httpServletResponse.setContentType("application/json;charset=utf-8");
        PrintWriter out = httpServletResponse.getWriter();
        out.write("{\"status\":\"error\"}");
        out.flush();
        out.close();
    }
}
 

