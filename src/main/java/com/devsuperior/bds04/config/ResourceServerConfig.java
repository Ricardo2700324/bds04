package com.devsuperior.bds04.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter{

	
	@Autowired
	private Environment env;
	
	@Autowired
	private JwtTokenStore tokenStore;
	
//	private static final String[] PUBLIC = { "/oauth/token" ,"/h2-console/**","/events/**", "/cities/**" };
//	private static final String[] CLIENT_POST = {"/events/**"};

	private static final String[] PUBLIC = { "/oauth/token", "/h2-console/**" };
	private static final String[] PUBLIC_GET = { "/events/**", "/cities/**" };
	private static final String[] PRIVATE_ANYROLE = { "/events/**" };


	
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.tokenStore(tokenStore);
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		//H2
		if(Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();
		}
		
//		http.authorizeRequests()
//		.antMatchers(HttpMethod.GET, PUBLIC).permitAll()
//		.antMatchers(HttpMethod.POST,CLIENT_POST).hasAnyRole("CLIENT")
//		.antMatchers().hasAnyRole("ADMIN")
//		.anyRequest().authenticated();
//		
		http.authorizeRequests()
		.antMatchers(PUBLIC).permitAll() 
		.antMatchers(HttpMethod.GET, PUBLIC_GET).permitAll()  
		.antMatchers(HttpMethod.POST,PUBLIC_GET).hasAnyRole("CLIENT")
		.antMatchers(HttpMethod.POST, PRIVATE_ANYROLE).hasAnyRole("CLIENT", "ADMIN")
		.anyRequest().hasRole("ADMIN"); 
		
		
	}
	
}
