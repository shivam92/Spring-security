package com.smartmind.spring.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig  {

	@Autowired
	UserDetailsService userDetailsService;
	@Bean
	public SecurityFilterChain secuirtyFilterChain(HttpSecurity http) throws Exception {
		return http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for H2 Console
              
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(("/h2-console/**")).permitAll() // Allow access to H2 Console
                        .anyRequest().authenticated()
                )
                .headers(h -> h.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)) // This so embedded frames in h2-console are working
                
                .httpBasic(Customizer.withDefaults()) // Enable Basic Authentication
               // .formLogin(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless Sessions
                .build();
	}
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider authprovider=new DaoAuthenticationProvider();//using DAO authprovider
		authprovider.setUserDetailsService(userDetailsService);
		authprovider.setPasswordEncoder(passwordEncoder());//user for decrypt has pass during login and match with your string pass
		return authprovider;
	}
	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
