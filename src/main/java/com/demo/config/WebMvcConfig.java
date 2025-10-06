package com.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.demo.security.JwtAuthInterceptor;


@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.demo.controllers")
public class WebMvcConfig implements WebMvcConfigurer{
	@Autowired
    private JwtAuthInterceptor jwtAuthInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtAuthInterceptor)
                .addPathPatterns("/**") // Áp dụng cho toàn bộ API
                .excludePathPatterns("/public/**", "/health");
    }
	@Bean
	public MappingJackson2HttpMessageConverter jacksonMessageConverter() {
		return new MappingJackson2HttpMessageConverter();
	}
	
}