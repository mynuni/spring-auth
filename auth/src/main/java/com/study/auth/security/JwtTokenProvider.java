package com.study.auth.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

	@Value("${jwt.key}")
	private String secretKey;

	@Value("${jwt.expiration.time}")
	private long expirationTime;
	
	// 키 생성
	private Key generateSecretKey() {
		byte[] byteKey = secretKey.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(byteKey);
	}
	
	// 토큰 생성
	public String createToken(String email) {
		log.info("EXP_TIME: {}", expirationTime);
		return Jwts.builder()
				.setSubject(email)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(generateSecretKey())
				.compact();
	}
	
	// 식별자 추출
	public String getEmailFromToken(String token) {
		return Jwts.parserBuilder()
				.setSigningKey(generateSecretKey())
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}
	
	// 유효기간 확인
	public boolean isValidToken(String token) {
		Jws<Claims> parseClaimsJws = Jwts.parserBuilder()
				.setSigningKey(generateSecretKey())
				.build()
				.parseClaimsJws(token);

		Date now = new Date();
		Date expirationDate = parseClaimsJws.getBody().getExpiration();

		log.info("NOW:{}", now);
		log.info("EXP:{}", expirationDate);
		
		return expirationDate.after(now) ? true : false;

	}
	
}
