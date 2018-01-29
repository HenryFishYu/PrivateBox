package priv.henryyu.privatebox.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import priv.henryyu.privatebox.entity.LoginDetails;
import priv.henryyu.privatebox.repository.LoginDetailsRepository;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月29日上午10:23:19
 * @version 1.0.0
 */
@Component
public class Consumer {
	@Autowired
	private LoginDetailsRepository loginDetailsRepository;
	@JmsListener(destination = "loginDetails.queue")  
    public void receiveQueue(LoginDetails loginDetails) {
		loginDetailsRepository.save(loginDetails);
        //System.out.println("Consumer收到的报文为:"+loginDetails);  
    } 
}
 

