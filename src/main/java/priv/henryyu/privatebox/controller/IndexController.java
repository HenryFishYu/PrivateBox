package priv.henryyu.privatebox.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.service.IndexService;
/**
 * IndexController class
 * 
 * @author HenryYu
 * @date 2017/12/15
 * @version 1.0.0
 */
@Controller
public class IndexController extends BaseComponent{
	private static Log log=LogFactory.getLog(IndexController.class);
	@RequestMapping("/")
	public String index() {
		if(getUser()!=null) {
			log.info(getUser().getUsername()+"--need logout");
			return "redirect:/user/index";
		}
		return "index";
	}
}
