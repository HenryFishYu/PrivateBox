package priv.henryyu.privatebox.aspect;

import java.io.File;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import javax.jms.Destination;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamSource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.alibaba.druid.support.json.JSONParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import priv.henryyu.privatebox.base.BaseComponent;
import priv.henryyu.privatebox.entity.LoginDetails;
import priv.henryyu.privatebox.jms.Producer;
import priv.henryyu.privatebox.repository.LoginDetailsRepository;

import org.apache.activemq.command.ActiveMQQueue;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
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
public class UserAspect extends BaseComponent{
	@Autowired
	private LoginDetailsRepository loginDetailsRepository;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private Producer producer;
	/*@Pointcut("execution(* priv.henryyu.privatebox.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")  
    public void controllerMethodPointcut(){}  
	
	@After(value = "execution(* priv.henryyu.privatebox.controller.UserController.index())")
	public void saveLoginMessages() {
		System.out.println("After Login");
	}*/
	@AfterReturning(returning="returnResult"  
	        , pointcut="execution(* priv.henryyu.privatebox.controller..*(..)) and @annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void after(JoinPoint joinPoint,Object returnResult) throws JsonProcessingException{
        boolean paramJsonFlag=true;
        boolean returnJsonFlag=true;
		MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        ObjectMapper objectMapper=new ObjectMapper();
        LoginDetails loginDetails=new LoginDetails(request);
        loginDetails.setClassName(joinPoint.getTarget().getClass().toString());
		if (method.getName().equals("queryFiles")) {
			paramJsonFlag = false;
		} else {
			for (Object object : joinPoint.getArgs()) {
				if (object instanceof Stream) {
					paramJsonFlag = false;
					break;
				}
				if (object instanceof InputStreamSource) {
					paramJsonFlag = false;
					break;
				}
			}
		}
		if(returnResult instanceof HttpEntity) {
			returnJsonFlag = false;
		}
        if(paramJsonFlag) {
        loginDetails.setParam(objectMapper.writeValueAsString(joinPoint.getArgs()));
        }
        if(returnJsonFlag) {
        loginDetails.setReturnResult(objectMapper.writeValueAsString(returnResult));
        }
        loginDetails.setMethodName(method.getName());
        if(returnResult!=null) {
        loginDetails.setReturnResultClass(returnResult.getClass().toString());
        }
        if(getUser()!=null) {
        loginDetails.setUsername(getUser().getUsername());
        }
        Destination destination = new ActiveMQQueue("loginDetails.queue"); 
        producer.sendMessage(destination, loginDetails);
        
		
	}
}
 

