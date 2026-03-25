package com.amazingbooks.issuerms.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;



@Configuration
public class WebClientConfig {
	
	@Bean
	@LoadBalanced
	public WebClient.Builder webclientBuilder(){
		return WebClient.builder().baseUrl("http://bookms");
	}
	
	@Bean
	public WebClient build(WebClient.Builder builder) {
		return builder.build();
	}
}
