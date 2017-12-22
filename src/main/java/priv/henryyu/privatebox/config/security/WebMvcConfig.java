package priv.henryyu.privatebox.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
/**
 * WebMvcConfig class
 * 
 * @author HenryYu
 * @date 2017/12/15
 * @version 1.0.0
 */
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter{
	@Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/").setViewName("index");
    }
}
