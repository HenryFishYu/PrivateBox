package priv.henryyu.privatebox.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.annotations.Type;
import org.springframework.web.servlet.support.RequestContextUtils;

import nl.bitwalker.useragentutils.UserAgent;
import priv.henryyu.privatebox.tools.RequestUtil;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2017年12月21日下午5:21:54
 * @version 1.0.0
 */
@Entity
public class LoginDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1385434668137053178L;
	@Id
	private String uuid;
	private String ip;
	private String userAgentString;
	private String browser;
	private String browserType;
	private String browserManufacturer;	
	private String browserRenderingEngine;	
	private String browserVersion;
	private String operatingSystem;
	private String operatingSystemDeviceType;
	private String operatingSystemManufacturer;
	private String language;
	private String defaultLanguage;
	private Timestamp createTime;
	private String username;
	private String className;
	@Type(type="text")
	private String param;
	private String methodName;
	@Type(type="text")
	private String returnResult;
	private String returnResultClass;
	
	
	
	public String getReturnResultClass() {
		return returnResultClass;
	}

	public void setReturnResultClass(String returnResultClass) {
		this.returnResultClass = returnResultClass;
	}

	public String getReturnResult() {
		return returnResult;
	}

	public void setReturnResult(String returnResult) {
		this.returnResult = returnResult;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public LoginDetails() {
	}

	public LoginDetails(HttpServletRequest request) {
		uuid=UUID.randomUUID().toString();
		ip=RequestUtil.getIPAddress(request);
		userAgentString=request.getHeader("User-Agent");
		UserAgent userAgent=UserAgent.parseUserAgentString(userAgentString);
		browser=userAgent.getBrowser().toString();
		browserType=userAgent.getBrowser().getBrowserType().toString();
		browserManufacturer=userAgent.getBrowser().getManufacturer().toString();
		browserRenderingEngine=userAgent.getBrowser().getRenderingEngine().toString();
		if(browserVersion!=null) {
		browserVersion=userAgent.getBrowserVersion().toString();
		}
		operatingSystem=userAgent.getOperatingSystem().toString();
		operatingSystemDeviceType=userAgent.getOperatingSystem().getDeviceType().toString();
		operatingSystemManufacturer=userAgent.getOperatingSystem().getManufacturer().toString();
		defaultLanguage=request.getLocale().toString();
		language=RequestContextUtils.getLocale(request).toLanguageTag().replace('-', '_');
		createTime=new Timestamp(System.currentTimeMillis());
	}
	
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	
	public String getDefaultLanguage() {
		return defaultLanguage;
	}

	public void setDefaultLanguage(String defaultLanguage) {
		this.defaultLanguage = defaultLanguage;
	}

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getBrowser() {
		return browser;
	}
	public void setBrowser(String browser) {
		this.browser = browser;
	}
	public String getBrowserType() {
		return browserType;
	}
	public void setBrowserType(String browserType) {
		this.browserType = browserType;
	}
	public String getBrowserManufacturer() {
		return browserManufacturer;
	}
	public void setBrowserManufacturer(String browserManufacturer) {
		this.browserManufacturer = browserManufacturer;
	}
	public String getBrowserRenderingEngine() {
		return browserRenderingEngine;
	}
	public void setBrowserRenderingEngine(String browserRenderingEngine) {
		this.browserRenderingEngine = browserRenderingEngine;
	}
	public String getBrowserVersion() {
		return browserVersion;
	}
	public void setBrowserVersion(String browserVersion) {
		this.browserVersion = browserVersion;
	}
	public String getOperatingSystem() {
		return operatingSystem;
	}
	public void setOperatingSystem(String operatingSystem) {
		this.operatingSystem = operatingSystem;
	}
	public String getOperatingSystemDeviceType() {
		return operatingSystemDeviceType;
	}
	public void setOperatingSystemDeviceType(String operatingSystemDeviceType) {
		this.operatingSystemDeviceType = operatingSystemDeviceType;
	}
	public String getOperatingSystemManufacturer() {
		return operatingSystemManufacturer;
	}
	public void setOperatingSystemManufacturer(String operatingSystemManufacturer) {
		this.operatingSystemManufacturer = operatingSystemManufacturer;
	}
	public String getUserAgentString() {
		return userAgentString;
	}
	public void setUserAgentString(String userAgentString) {
		this.userAgentString = userAgentString;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}
	

	
	
}
 

