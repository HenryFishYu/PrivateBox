package priv.henryyu.privatebox.tools;

import javax.servlet.http.HttpServletRequest;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年1月11日上午10:42:30
 * @version 1.0.0
 */
public class RequestUtil {
	public static String getIPAddress(HttpServletRequest request) {  
        String ip = request.getHeader("x-forwarded-for");  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
        	ip = request.getHeader("Proxy-Client-IP");  
        }else {
        	int index = ip.indexOf(",");
    		if (index != -1) {
    			return ip.substring(0, index);
    		}else {
    			return ip;
    		}
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("WL-Proxy-Client-IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_CLIENT_IP");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");  
        }  
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {  
            ip = request.getRemoteAddr();  
        }  
         return ip;
    } 
}
 

