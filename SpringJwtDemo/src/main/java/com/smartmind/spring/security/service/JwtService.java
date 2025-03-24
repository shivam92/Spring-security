package com.smartmind.spring.security.service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	 private String secretKey;
	 public JwtService() {
		 secretKey=getSecretKey(); 
	 }
	private String getSecretKey() {
		// TODO Auto-generated method stub
		try {
			KeyGenerator keyGen=KeyGenerator.getInstance("HmacSHA256");
			SecretKey secretKey=keyGen.generateKey();
			System.out.println(secretKey);
			return Base64.getEncoder().encodeToString(secretKey.getEncoded());
		}catch(Exception e) {
			throw new RuntimeException("Error generating secret key", e);
		}
	}
	public String extractName(String token) {
	
		return extractClaim(token,Claims::getSubject);
	}
	private <T> T extractClaim(String token,Function<Claims, T> claimResolver) {
		final Claims claim=extractAllClaims(token);
		return claimResolver.apply(claim);
	}
 private Claims  extractAllClaims(String token) {
	 return Jwts.parserBuilder().setSigningKey(getKey()).build()
	 .parseClaimsJws(token).getBody();
 }
   private Key getKey() {
	 byte[] keyBytes=Decoders.BASE64.decode(secretKey);
	 return Keys.hmacShaKeyFor(keyBytes);
 }
 
	public boolean validateDetails(UserDetails userdetails, String token) {
		String userName=extractName(token);
		return (userName.equals(userdetails.getUsername()) && !isTokenExpired(token));
		
	}
	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	private Date extractExpiration(String token) {
		 return extractClaim(token, Claims::getExpiration);
	}
	
	public String generateToken(String userName) {
		Map<String,Object>claims=new HashMap();
		return Jwts.builder().setClaims(claims)
				.setSubject(userName)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*30))
				.signWith(getKey(),SignatureAlgorithm.HS256).compact();
	}

}
