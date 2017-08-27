package com.vapps.iso.configuration.security.oauth2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class JWTConfiguration {

	@Bean
	public JwtTokenStore tokenStore() {
		return new JwtTokenStore(tokenEnhancer());
	}

	@Bean
	public TokenEnhancerChain tokenEnhancerChain() {
		TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
		List<TokenEnhancer> tokenEnhancerList = new ArrayList<TokenEnhancer>();
		tokenEnhancerList.add(tokenEnhancer());

		tokenEnhancerChain.setTokenEnhancers(tokenEnhancerList);
		return tokenEnhancerChain;
	}

	@Bean
	public JwtAccessTokenConverter tokenEnhancer() {
		JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
		jwtAccessTokenConverter.setAccessTokenConverter(defaultAccessTokenConverter());
		jwtAccessTokenConverter.setSigningKey("c5da803c8b0611e7bb31be2e44b06b34");
		return jwtAccessTokenConverter;
	}

	@Bean
	public DefaultAccessTokenConverter defaultAccessTokenConverter() {
		DefaultAccessTokenConverter converter = new DefaultAccessTokenConverter();
		DefaultUserAuthenticationConverter userConverter = new DefaultUserAuthenticationConverter();
		converter.setUserTokenConverter(userConverter);
		return converter;
	}

	@Bean
	public ResourceServerTokenServices defaultTokenServices() {
		DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
		defaultTokenServices.setTokenStore(tokenStore());
		defaultTokenServices.setTokenEnhancer(tokenEnhancerChain());
		defaultTokenServices.setSupportRefreshToken(true);
		return defaultTokenServices;
	}
}