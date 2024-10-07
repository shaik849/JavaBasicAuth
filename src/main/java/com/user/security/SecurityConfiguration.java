package com.user.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfToken;
import com.user.service.CustomUserSecurity;


@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration  {
	
	@Autowired
	private CustomUserSecurity userSecurity;
	
	

	@Bean
	protected SecurityFilterChain securityFilter(HttpSecurity http) throws Exception {
		
//       http.csrf().disable(); this is deprecated
		
		return http.csrf(customize -> customize.disable())
						   .authorizeHttpRequests((requests) -> requests
					                .requestMatchers(HttpMethod.POST, "/api/user").permitAll() // Allow only POST requests to /api/user
//					                .requestMatchers("/api/admin").hasRole("ADMIN")
					                .requestMatchers("/api/**").authenticated())
				                    .httpBasic(Customizer.withDefaults())
		                            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		                            .build();
	}
	
	  @Bean
	    public AuthenticationProvider authenticationProvider() {
	      DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
	        provider.setPasswordEncoder(passwordEncoder());
	        provider.setUserDetailsService(userSecurity);
	        
	        return provider;
	    }
	  
	    @Bean
	    protected PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder(); // Use BCrypt for secure password storage
	    }
	
//	@Bean
//	public UserDetailsService userDetails() {
//		UserDetails user = User
//				.withDefaultPasswordEncoder()
//				.username("hello")
//				.password("shaik")
//				.roles("USER")
//				.build();
//		
//		return new InMemoryUserDetailsManager(user);
//	}
	
//	public userDe
}
