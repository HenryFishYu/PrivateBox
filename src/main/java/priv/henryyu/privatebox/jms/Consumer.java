package priv.henryyu.privatebox.jms;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import priv.henryyu.privatebox.entity.Chat;
import priv.henryyu.privatebox.entity.InvitationCode;
import priv.henryyu.privatebox.entity.LoginDetails;
import priv.henryyu.privatebox.model.RegisterEmailEntity;
import priv.henryyu.privatebox.repository.ChatRepository;
import priv.henryyu.privatebox.repository.InvitationCodeRepository;
import priv.henryyu.privatebox.repository.LoginDetailsRepository;
import priv.henryyu.privatebox.siglton.Siglton;

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
	private InvitationCodeRepository invitationCodeRepository;
	@Autowired
	private ChatRepository chatRepository;
	@Autowired
	private JavaMailSender mailSender;
	@JmsListener(destination = "loginDetails.queue")  
    public void receiveQueue(LoginDetails loginDetails) {
		loginDetailsRepository.save(loginDetails);
        //System.out.println("Consumer收到的报文为:"+loginDetails);  
    } 
	@JmsListener(destination = "sendEmail.queue")  
    public void receiveQueue(RegisterEmailEntity registerEmailEntity) throws MessagingException {
		String activeURL="http://192.168.108.69:9090/activeAccount?registerUsername="+registerEmailEntity.getRegisterUsername()+"&activationCode="+registerEmailEntity.getActivationCode();
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		mimeMessage.addRecipients(Message.RecipientType.CC, InternetAddress.parse("yuhaolun8888@163.com"));
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
		helper.setFrom("yuhaolun8888@163.com");
		helper.setTo(registerEmailEntity.getRegisterUsername());
		helper.setSubject("Welcome Register PrivateBox");
		helper.setText("<html><body><a href='"+activeURL+"'>"+activeURL+"</a></body></html>", true);
		mailSender.send(mimeMessage);
		Siglton.INSTANCE.getRegisterExpiringMap().get(registerEmailEntity.getRegisterUsername()).setSend(true);
    } 
	@JmsListener(destination = "invitationCode.queue")  
    public void receiveQueue(InvitationCode invitationCode) throws MessagingException {
		invitationCodeRepository.save(invitationCode);
    } 
    @JmsListener(destination = "groupChat.queue")  
    public void receiveQueue(Chat chat) throws MessagingException {
    	chatRepository.save(chat);
    }
}
 

