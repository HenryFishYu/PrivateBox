package priv.henryyu.privatebox.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.alibaba.druid.util.StringUtils;

import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.entity.InvitationCode;
import priv.henryyu.privatebox.entity.User;
import priv.henryyu.privatebox.model.request.InvitationCodeQueryForm;
import priv.henryyu.privatebox.model.response.DataGrid;
import priv.henryyu.privatebox.model.response.ResponseMessage;
import priv.henryyu.privatebox.model.response.error.ResponseError;
import priv.henryyu.privatebox.repository.InvitationCodeRepository;
import priv.henryyu.privatebox.repository.UserRepository;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2017年12月26日上午11:26:35
 * @version 1.0.0
 */
@Service
public class AdminService extends BaseComponent{
	@Autowired
	private InvitationCodeRepository invitationCodeRepository;
	@Autowired
	private UserRepository userRepository;
	private static final String empty="";
	
	public ResponseMessage<Set<InvitationCode>> generateInvitationCodes(String amount) {
		ResponseMessage<Set<InvitationCode>> responseMessage=new ResponseMessage<Set<InvitationCode>>();
		Locale locale= RequestContextUtils.getLocale(request);
		//String LoginError=messageSource.getMessage("loginError", null,locale);
		if(empty.equals(amount)||!StringUtils.isNumber(amount)) {
			responseMessage.setError(ResponseError.IllegalInput);
			responseMessage.setMessage(messageSource.getMessage("remind_InputNumber", null,locale));
			return responseMessage;
		}
		int intAmount=Integer.valueOf(amount);
		if(intAmount<=0||intAmount>10) {
			responseMessage.setError(ResponseError.IllegalInput);
			responseMessage.setMessage(messageSource.getMessage("remind_OutOfLimit", null,locale));
			return responseMessage;
		}
		User user=userRepository.findByUsername(getUser().getUsername());
		Set<InvitationCode> invitationCodes=new HashSet<InvitationCode>();
		for(int i=0;i<intAmount;i++) {
			invitationCodes.add(new InvitationCode());
		}
		user.addGeneratedInvoInvitationCodes(invitationCodes);
		invitationCodeRepository.save(invitationCodes);
		userRepository.save(user);
		responseMessage.setError(ResponseError.Success);
		for(InvitationCode invitationCode:invitationCodes) {
			invitationCode.setCreateUser(null);
		}
		responseMessage.setData(invitationCodes);
		return responseMessage;
	}
	
	public DataGrid queryInvitationCodes(InvitationCodeQueryForm invitationCodeQueryForm){
		DataGrid dataGrid=new DataGrid();
		//User user=userRepository.findByUsername(getUser().getUsername());
		Pageable pageable;
		if(invitationCodeQueryForm.getSort()==null||invitationCodeQueryForm.getOrder()==null) {
			pageable=new PageRequest(invitationCodeQueryForm.getPage()-1, invitationCodeQueryForm.getRows());
		}else {
			Sort sort=new Sort(Direction.fromString(invitationCodeQueryForm.getOrder()),invitationCodeQueryForm.getSort());
			pageable=new PageRequest(invitationCodeQueryForm.getPage()-1, invitationCodeQueryForm.getRows(),sort);
		}
		if(invitationCodeQueryForm.getCode()==null) {
			invitationCodeQueryForm.setCode("");
		}
		Page<InvitationCode> page=invitationCodeRepository.findByCreateUserAndCodeLike(getUser(),"%"+invitationCodeQueryForm.getCode()+"%",pageable);
		dataGrid.setTotal((int)page.getTotalElements());
		dataGrid.setRows(page.getContent());
		return dataGrid;
	}
}
 

