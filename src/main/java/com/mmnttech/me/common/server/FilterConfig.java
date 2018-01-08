package com.mmnttech.me.common.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
public class FilterConfig {
	@Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean=new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new SimpleCORSFilter());
        return filterRegistrationBean;
    }

}
