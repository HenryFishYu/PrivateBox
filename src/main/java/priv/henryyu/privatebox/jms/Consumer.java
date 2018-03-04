package priv.henryyu.privatebox.jms;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
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
	@Autowired
	private JavaMailSender mailSender;
	@JmsListener(destination = "loginDetails.queue")  
    public void receiveQueue(LoginDetails loginDetails) {
		loginDetailsRepository.save(loginDetails);
        //System.out.println("Consumer收到的报文为:"+loginDetails);  
    } 
	@JmsListener(destination = "sendEmail.queue")  
    public void receiveQueue(String string) throws MessagingException {
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("yuhaolun8888@163.com");
		helper.setTo("448780883@qq.com");
		helper.setSubject("Welcome Register PrivateBox");
		helper.setText("<html><body><a href='https://www.douyu.com/58428'>baidu</a></body></html>", true);
		mailSender.send(mimeMessage);
    } 
}
 

