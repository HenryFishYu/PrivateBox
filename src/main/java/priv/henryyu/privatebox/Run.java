package priv.henryyu.privatebox;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@SpringBootApplication
@ComponentScan({"priv.henryyu.privatebox"})
@EnableAspectJAutoProxy
public class Run {
	public static void main(String args[]) {
		SpringApplication.run(Run.class, args);
		
		
	}
}
