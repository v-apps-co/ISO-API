package com.vapps.iso.configuration.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;

import com.vapps.iso.configuration.CORSFilter;

@Configuration
@EnableResourceServer
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {
	@Autowired
	JWTConfiguration jwtConfiguration;

	@Override
	public void configure(ResourceServerSecurityConfigurer config) {
		config.tokenServices(jwtConfiguration.defaultTokenServices());
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class);
		http.authorizeRequests().anyRequest().authenticated();
	}
}