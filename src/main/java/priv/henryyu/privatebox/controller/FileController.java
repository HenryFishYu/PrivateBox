package priv.henryyu.privatebox.controller;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.service.FileService;
import priv.henryyu.privatebox.tools.Encrypt;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月10日下午3:24:44
 * @version 1.0.0
 */
@Controller
@RequestMapping("/file")
public class FileController extends BaseComponent{
	private static final Log log=LogFactory.getLog(FileController.class);
	@Autowired
	private FileService fileService;
	@RequestMapping("/upload")
	@ResponseBody
	public ResponseMessage upload(MultipartFile file) throws IllegalStateException, IOException {
		log.info(getUser().getUsername()+"upload param:OriginalFilename:"+file.getOriginalFilename()+
				" ContentType:"+file.getContentType()+" Size:"+file.getSize());
		ResponseMessage responseMessage=fileService.upload(file);
		log.info(getUser().getUsername()+"upload return:"+mapper.writeValueAsString(responseMessage));
		return responseMessage;
	}
	@RequestMapping("/preUpload")
	@ResponseBody
	public ResponseMessage preUpload(String pieceSHA512,String originalFilename) throws IllegalStateException, IOException {
		log.info(getUser().getUsername()+"preUpload param:File,pieceSHA512:"+pieceSHA512+" originalFilename:"+originalFilename);
		ResponseMessage responseMessage=fileService.preUpload(pieceSHA512,originalFilename);
		log.info(getUser().getUsername()+"preUpload return:"+mapper.writeValueAsString(responseMessage));
		return responseMessage;
	}
}
 

