package com.systex.project.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systex.project.filter.LoginFilter;
import com.systex.project.service.AccountService;

@Configuration
public class FilterConfig {
	@Bean
	public FilterRegistrationBean<LoginFilter> loggingFilterRegistration(AccountService accountService) {
	    FilterRegistrationBean<LoginFilter> registrationBean = new FilterRegistrationBean<>();
	    registrationBean.setFilter(new LoginFilter(accountService));//new的時候加入需要用到的class filter就不需要標註@Autowired和@Componetnt
	    registrationBean.addUrlPatterns("/*");//需控管的URL
	    return registrationBean;
	}
}
