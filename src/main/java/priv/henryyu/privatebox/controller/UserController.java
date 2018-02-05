package priv.henryyu.privatebox.controller;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.entity.File;
import priv.henryyu.privatebox.entity.User;
import priv.henryyu.privatebox.model.request.FileQueryForm;
import priv.henryyu.privatebox.model.request.PaginationForm;
import priv.henryyu.privatebox.model.request.RegisterUser;
import priv.henryyu.privatebox.model.response.DataGrid;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.service.FileService;
import priv.henryyu.privatebox.service.UserService;
/**
 * UserController class
 * 
 * @author HenryYu
 * @date 2017/12/15
 * @version 1.0.0
 */
@Controller
@RequestMapping("/user")
public class UserController extends BaseComponent{
	private static final Log log = LogFactory.getLog(UserController.class);
	@Autowired
	UserService userService;
	@Autowired
	FileService fileService;
	/**
	* 新用户注册
	* Register new user controller
	* @param 传入注册用户模板 
	* RegisterUser
	* @return 结果
	* ResponseMessage
	 * @throws JsonProcessingException 
	* @throws Exception
	*/
	@RequestMapping("/register")
	@ResponseBody
	public ResponseMessage<User> register(RegisterUser registerUser) throws JsonProcessingException {	
		log.info("user register param:"+mapper.writeValueAsString(registerUser));
		ResponseMessage<User> responseMessage=userService.registerUser(registerUser);
		log.info("user register return:"+mapper.writeValueAsString(responseMessage));
		return responseMessage;	
	}
	
	@RequestMapping("/index")
	public ModelAndView index() {
		log.info(getUser().getUsername()+"--login");
		ModelAndView modelAndView=new ModelAndView("user/index");
		return modelAndView;
	}

	@RequestMapping("/showUploadFile")
	public ModelAndView showUploadFile() {
		log.info(getUser().getUsername()+"--showUploadFile");
		ModelAndView modelAndView=new ModelAndView("user/uploadFile");
		return modelAndView;
	}
	
	@RequestMapping("/queryFiles")     
	@ResponseBody
	public DataGrid<File> queryFiles(FileQueryForm fileQueryForm) throws JsonProcessingException {
		log.info(getUser().getUsername()+" queryFiles FileQueryForm:"+mapper.writeValueAsString(fileQueryForm));
		DataGrid<File> dataGrid=fileService.queryFiles(fileQueryForm);
		log.info(getUser().getUsername()+" queryFiles result:"+mapper.writeValueAsString(dataGrid ));
		return dataGrid;
	}
}
