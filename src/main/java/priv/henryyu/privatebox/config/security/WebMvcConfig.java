package priv.henryyu.privatebox.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import priv.henryyu.privatebox.config.security.interceptor.IPInterceptor;

/**
 * WebMvcConfig class
 * 
 * @author HenryYu
 * @date 2017/12/15
 * @version 1.0.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
	@Bean
	public HandlerInterceptor getIPInterceptor() {
		return new IPInterceptor();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		// 多个拦截器组成一个拦截器链
		// addPathPatterns 用于添加拦截规则, 这里假设拦截 /url 后面的全部链接
		// excludePathPatterns 用户排除拦截
		registry.addInterceptor(getIPInterceptor()).addPathPatterns("/**");
		super.addInterceptors(registry);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		// registry.addViewController("/").setViewName("index");
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// 将所有/static/** 访问都映射到classpath:/static/ 目录下
		registry.addResourceHandler("/files/**").addResourceLocations("classpath:/files/");
	}
}
