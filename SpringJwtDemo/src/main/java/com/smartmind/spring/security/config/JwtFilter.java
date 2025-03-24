package com.smartmind.spring.security.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import com.smartmind.spring.security.service.CustomUserDetailsService;
import com.smartmind.spring.security.service.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	JwtService jwtService;
	@Autowired
	CustomUserDetailsService customUserDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String authHeader = request.getHeader("Authorization");
		String userName = null;
		String token = null;

		if (authHeader != null && authHeader.startsWith("Bearer ") && authHeader.length() > 6) {
			token = authHeader.substring(7);
			userName=jwtService.extractName(token);
		}
		if(userName!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userdetails=customUserDetailsService.loadUserByUsername(userName);
		if(jwtService.validateDetails(userdetails,token)) {
			UsernamePasswordAuthenticationToken authToekn=new UsernamePasswordAuthenticationToken(userdetails,null,userdetails.getAuthorities());
			authToekn.setDetails(new WebAuthenticationDetails(request));
			SecurityContextHolder.getContext().setAuthentication(authToekn);
		}
		}
		filterChain.doFilter(request, response);
	}

}
