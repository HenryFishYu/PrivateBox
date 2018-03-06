package priv.henryyu.privatebox.config.security.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import net.jodah.expiringmap.ExpiringMap;
import priv.henryyu.privatebox.siglton.Siglton;
import priv.henryyu.privatebox.tools.RequestUtil;

/**
 * XXX class
 * 
 * @author HenryYu
 * @date 2018年3月6日上午10:23:36
 * @version 1.0.0
 */
public class IPInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		String ip=RequestUtil.getIPAddress(request);
		ExpiringMap<String,Integer> ipMap=Siglton.INSTANCE.getIpMap();
		if(ipMap.get(ip)==null) {
			ipMap.put(ip, 0);
			return;
		}
		Integer temp=ipMap.get(ip);
		temp++;
		if(ipMap.get(ip)>200) {
			if(modelAndView==null) {
				return;
			}
			modelAndView.setViewName("redirect:/spider");
			return;
		}
		ipMap.put(ip, temp);
		
		// TODO Auto-generated method stub
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

}
 

