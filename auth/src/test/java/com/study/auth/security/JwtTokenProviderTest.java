package com.study.auth.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtTokenProviderTest {

	@Autowired
	JwtTokenProvider jwtTokenProvider;

	@Test
	@DisplayName("토큰 발급")
	void createJwtToken() {
		// GIVEN RESOURCES
		String email = "test@test.com";

		// DO
		String createdToken = jwtTokenProvider.createToken(email);

		// RESULT
		assertNotNull(createdToken);
		System.out.println(createdToken);

	}

	@Test
	@DisplayName("토큰에서 이메일 추출")
	void extractEmailFromToken() {
		// GIVEN RESOURCES
		String email = "hello@hello.com";

		// DO
		String createToken = jwtTokenProvider.createToken(email);
		String extractedEmail = jwtTokenProvider.getEmailFromToken(createToken);

		// RESULT
		assertEquals(email, extractedEmail);

	}

	@Test
	@DisplayName("새로 받은 토큰 유효성 성공 검사")
	void checkValidationWithNew() {
		// GIVEN RESOURCES
		String email = "test2@test2.com";

		// DO
		String createdToken = jwtTokenProvider.createToken(email);
		boolean result = jwtTokenProvider.isValidToken(createdToken);

		// RESULT
		assertTrue(result);
	}

	@Test
	@DisplayName("오래된 토큰 실패 검사")
	void checkValidationWithOld() {
		// GIVEN RESOURCES
		String oldToken = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0ZXN0QHRlc3QuY29tIiwiaWF0IjoxNjkwMDkyNzU3LCJleHAiOjE2OTAwOTI5Mzd9.nIKdmPHvWuC8Q-qSagFgOUuyt0rhl7kZ27tN7avPhxrafqH9Qgj7NKUP-c92x3r3GE_j1PrVT_8Gitl3kWOOrQ";

		// DO
		boolean result = jwtTokenProvider.isValidToken(oldToken);

		// RESULT
		assertFalse(result);
	}

}