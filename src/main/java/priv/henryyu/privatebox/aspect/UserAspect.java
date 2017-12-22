package priv.henryyu.privatebox.aspect;

import java.lang.reflect.Method;

import org.springframework.stereotype.Component;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2017年12月21日上午11:46:35
 * @version 1.0.0
 */
@Aspect
@Component
public class UserAspect {
	/*@Pointcut("execution(* priv.henryyu.privatebox.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")  
    public void controllerMethodPointcut(){}  
	
	@After(value = "execution(* priv.henryyu.privatebox.controller.UserController.index())")
	public void saveLoginMessages() {
		System.out.println("After Login");
	}*/
	/*@Before("execution(* springAop.MethodService.*(..))")
    public void before(JoinPoint joinPoint){
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        System.out.println("方法式拦截"+method.getName());
    }*/
}
 

