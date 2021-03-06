package com.gxecard.customerservice.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyWebAppConfigurer  extends WebMvcConfigurerAdapter {

	@Bean
	public MyInterceptor getMyInterceptor(){
		return new  MyInterceptor();
	}
	 @Override
	    public void addInterceptors(InterceptorRegistry registry) {
	        // 多个拦截器组成一个拦截器链
	        // addPathPatterns 用于添加拦截规则
	        // excludePathPatterns 用户排除拦截
	        registry.addInterceptor(getMyInterceptor()).addPathPatterns("/**");
	        super.addInterceptors(registry);
	    }
}
