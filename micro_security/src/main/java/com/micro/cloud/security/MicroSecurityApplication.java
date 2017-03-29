package com.micro.cloud.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@SpringBootApplication
@EnableWebSecurity
public class MicroSecurityApplication extends WebMvcConfigurerAdapter {

	/**
	 *
	 * Instead of returning the principal directly, we're returning a custom user object
	 * that exposes the username and authorities list.
	 *
	 * This way we bypass the issue https://github.com/spring-projects/spring-boot/issues/5482
	 *
	 * @param user
	 * @return
	 */
	@RequestMapping("/user")
	public SimpleUser user(Principal user) {
		List<String> authorities = new ArrayList<>();

		//TODO: we should try to avoid casting like this.
		Collection<GrantedAuthority> oauthAuthorities = ((OAuth2Authentication) user).getAuthorities();

		for (GrantedAuthority grantedAuthority : oauthAuthorities) {
			authorities.add(grantedAuthority.getAuthority());
		}

		return new SimpleUser(user.getName(), authorities);
	}

	class SimpleUser {

		String username;
		List<String> authorities;

		SimpleUser(String username, List<String> authorities) {
			this.username=username;
			this.authorities =authorities;
		}

		public String getUsername() {
			return username;
		}

		public List<String> getAuthorities() {
			return authorities;
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(MicroSecurityApplication.class, args);
	}
	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/oauth/confirm_access").setViewName("authorize");
	}

		protected void configure(HttpSecurity http) throws Exception {
			http.formLogin().loginPage("/login").permitAll().and().authorizeRequests().anyRequest().authenticated();
		}

		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication()
					.withUser("user")
					.password("password");
		}

	}
