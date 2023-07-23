package com.study.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.auth.security.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
public class SampleController {
	
	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping("/sample")
	public ResponseEntity<String> sample(String email) {
		String createdToken = jwtTokenProvider.createToken(email);
		String emailFromToken = jwtTokenProvider.getEmailFromToken(createdToken);
		log.info("이메일: {}", emailFromToken);
		
		return ResponseEntity.ok("OK");
	}

}
