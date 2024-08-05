package com.systex.project.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.systex.project.filter.loginFilter;

@Configuration
public class FilterConfig {

	@Bean
	public FilterRegistrationBean<loginFilter> logProcessFilter() {
		FilterRegistrationBean<loginFilter> bean = new FilterRegistrationBean<>();
		bean.setFilter(new loginFilter());
		bean.addUrlPatterns("/project/login");
		bean.setOrder(1);
		return bean;

	}
}
