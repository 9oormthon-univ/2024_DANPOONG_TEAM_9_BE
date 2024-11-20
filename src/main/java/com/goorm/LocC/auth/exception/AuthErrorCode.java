package com.goorm.LocC.auth.exception;

import com.goorm.LocC.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum AuthErrorCode implements BaseErrorCode {

	KAKAO_INVALID_TOKEN(UNAUTHORIZED, "AUTH_401_0", "잘못된 카카오 엑세스 토큰입니다."),
	KAKAO_API_ERROR(INTERNAL_SERVER_ERROR, "AUTH_500_0", "카카오 API 에러가 발생했습니다. 관리자에게 문의주세요."),
	;

	private final HttpStatus status;
	private final String code;
	private final String message;
}
