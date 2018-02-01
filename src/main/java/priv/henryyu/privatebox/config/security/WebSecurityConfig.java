package priv.henryyu.privatebox.config.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import priv.henryyu.privatebox.config.security.loginHandler.OnAuthenticationFailureHandler;
import priv.henryyu.privatebox.config.security.loginHandler.OnAuthenticationSuccessHandler;
import priv.henryyu.privatebox.service.UserService;
/**
 * WebSecurityConfig class
 * 
 * @author HenryYu
 * @date 2017/12/15
 * @version 1.0.0
 */

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
	OnAuthenticationSuccessHandler onAuthenticationSuccessHandler;
	@Autowired
	OnAuthenticationFailureHandler onAuthenticationFailureHandler;
	@Bean
    UserDetailsService UserService() {
        return new UserService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(UserService());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

    	http.authorizeRequests()
    	.antMatchers("/*").permitAll()
    	.antMatchers("/user/register").permitAll()
    	.antMatchers("/admin/**").hasAnyRole("ADMIN")
    	//hasAnyRole("ADMIN") is the same as has hasAnyAuthority("ROLE_ADMIN")
    	.anyRequest().authenticated()
        .and().formLogin().loginPage("/").loginProcessingUrl("/login")
        .successHandler(onAuthenticationSuccessHandler)
        .failureHandler(onAuthenticationFailureHandler)
        .permitAll()
        .and().rememberMe()
        .and().logout().logoutSuccessUrl("/").permitAll();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/js/**","/style/**");
    }
}
