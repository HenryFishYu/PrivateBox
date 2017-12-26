package priv.henryyu.privatebox.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2017年12月25日下午5:14:58
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView modelAndView=new ModelAndView("admin/index");
		return modelAndView;
	}
}
 

