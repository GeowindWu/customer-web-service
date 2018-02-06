package com.gxecard.customerservice.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.gxecard.customerservice.service.AccessControll;

public class MyInterceptor implements HandlerInterceptor  {
	@Autowired
	private AccessControll accessControll;
	private static boolean firstInto = true;//刚进入系统的标记
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object arg2, Exception arg3)
			throws Exception {
		String url = request.getRequestURI();
		System.out.println("访问的路径：" + url);
		if(url.contains("Api") || url.contains("User") || url.contains("api") 
				|| url.contains("user") ){
			// 管理平台执行了与接口和用户有关的操作，主动更新控制信息
			accessControll.initList(true);
		}
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		response.setHeader("Access-Control-Allow-Origin", "*"); //解决跨域访问报错
		System.out.println("拦截器，设置header，解决跨域访问题");
		if(firstInto){
			accessControll.initList(false);
			firstInto = false;
		}
		return true;
	}

}
