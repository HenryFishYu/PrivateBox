package priv.henryyu.privatebox;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import priv.henryyu.privatebox.entity.User;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode("123"));
	}

}
