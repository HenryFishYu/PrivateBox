package priv.henryyu.privatebox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.entity.LoginDetails;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.repository.LoginDetailsRepository;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月11日下午2:29:09
 * @version 1.0.0
 */
@Service
public class IndexService extends BaseComponent{
	@Autowired
	LoginDetailsRepository loginDetailsRepository;
	
	public ResponseMessage<LoginDetails> saveLoginDetails(){
		ResponseMessage<LoginDetails> responseMessage=new ResponseMessage<LoginDetails>();
		LoginDetails loginDetails=new LoginDetails(request);
		LoginDetails savedLoginDetails=loginDetailsRepository.save(loginDetails);
		responseMessage.setData(savedLoginDetails);
		return responseMessage;
	}
}
 

