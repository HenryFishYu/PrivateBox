package priv.henryyu.privatebox.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.entity.InvitationCode;
import priv.henryyu.privatebox.model.request.InvitationCodeQueryForm;
import priv.henryyu.privatebox.model.request.PaginationForm;
import priv.henryyu.privatebox.model.response.DataGrid;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.service.AdminService;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2017年12月25日下午5:14:58
 * @version 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class AdminController extends BaseComponent{
	
	@Autowired
	private AdminService adminService;
	@RequestMapping("/index")
	public ModelAndView index() {
		ModelAndView modelAndView=new ModelAndView("admin/index");
		return modelAndView;
	}
	
	@RequestMapping("/generateInvitationCodes")
	@ResponseBody
	public ResponseMessage generateInvitationCodes(String amount){
		ResponseMessage responseMessage=adminService.generateInvitationCodes(amount);
		return responseMessage;
	}
	
	@RequestMapping("/queryInvitationCodes")
	@ResponseBody
	public DataGrid queryInvitationCodes(InvitationCodeQueryForm invitationCodeQueryForm) {
		return adminService.queryInvitationCodes(invitationCodeQueryForm);
	}
}
 

