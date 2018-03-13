package priv.henryyu.privatebox.base;

import java.io.File;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import priv.henryyu.privatebox.controller.UserController;
import priv.henryyu.privatebox.entity.InvitationCode;
import priv.henryyu.privatebox.repository.InvitationCodeRepository;
import priv.henryyu.privatebox.repository.RoleRepository;
import priv.henryyu.privatebox.service.UserService;
import priv.henryyu.privatebox.siglton.Siglton;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月8日上午11:41:12
 * @version 1.0.0
 */
@Component
public class Startup implements CommandLineRunner {

	@Autowired
	private UserService userService;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private InvitationCodeRepository invitationCodeRepository;

	@Override
	public void run(String... arg0) throws Exception {
		// TODO Auto-generated method stub
		boolean isFirstTime = userService.isFirstTime();
		System.out.println("Is first time run this application:" + isFirstTime);
		String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "files";
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdir();
			System.out.println("Generate File Folder");
		}
		if (!isFirstTime) {
			for (InvitationCode invitationCode : invitationCodeRepository.findAll()) {
				Siglton.INSTANCE.getInvitationCodeMap().put(invitationCode.getCode(), invitationCode);
			}
		}
		System.out.println("PrivateBox Run");

	}

}
