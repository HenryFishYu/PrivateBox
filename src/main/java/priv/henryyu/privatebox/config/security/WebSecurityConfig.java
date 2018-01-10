package priv.henryyu.privatebox.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

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
        .and().formLogin().loginPage("/").loginProcessingUrl("/login").defaultSuccessUrl("/user/index").failureUrl("/loginerror").permitAll()
        .and().rememberMe()
        .and().logout().logoutSuccessUrl("/").permitAll();
    }
    @Override
    public void configure(WebSecurity web) throws Exception {
        //解决静态资源被拦截的问题
        web.ignoring().antMatchers("/js/**","/style/**");
    }
}
