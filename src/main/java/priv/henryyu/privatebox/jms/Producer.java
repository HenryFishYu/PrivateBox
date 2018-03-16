package priv.henryyu.privatebox.jms;

import javax.jms.Destination;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import priv.henryyu.privatebox.entity.Chat;
import priv.henryyu.privatebox.entity.InvitationCode;
import priv.henryyu.privatebox.entity.LoginDetails;
import priv.henryyu.privatebox.model.RegisterEmailEntity;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月29日上午10:20:48
 * @version 1.0.0
 */
@Service("producer")
public class Producer {
	@Autowired // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装  
    private JmsMessagingTemplate jmsTemplate;  
    // 发送消息，destination是发送到的队列，message是待发送的消息  
    public void sendMessage(Destination destination, final String message){  
        jmsTemplate.convertAndSend(destination, message);  
    }
    public void sendMessage(Destination destination, final Object message){  
        jmsTemplate.convertAndSend(destination, message);  
    }
    public void sendMessage(Destination destination, final LoginDetails message){  
        jmsTemplate.convertAndSend(destination, message);  
    }
    public void sendMessage(Destination destination, final RegisterEmailEntity message){  
        jmsTemplate.convertAndSend(destination, message);  
    }
    public void sendMessage(Destination destination, final InvitationCode invitationCode){  
        jmsTemplate.convertAndSend(destination, invitationCode);  
    }
    public void sendMessage(Destination destination, final Chat chat){  
        jmsTemplate.convertAndSend(destination, chat);  
    }
}
 

