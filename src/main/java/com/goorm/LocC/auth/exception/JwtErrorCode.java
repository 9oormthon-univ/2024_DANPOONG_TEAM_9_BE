package com.goorm.LocC.auth.exception;

import com.goorm.LocC.global.exception.BaseErrorCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum JwtErrorCode implements BaseErrorCode {

	EXPIRED_TOKEN(UNAUTHORIZED, "JWT_401_0", "토큰이 만료되었습니다."),
	INVALID_TOKEN(UNAUTHORIZED, "JWT_401_1", "잘못된 토큰입니다."),
	UNAUTHORIZED_ACCESS(UNAUTHORIZED, "JWT_401_2", "사용자 인증이 필요합니다."),
	ACCESS_DENIED(FORBIDDEN, "JWT_403_0", "해당 요청에 대한 접근 권한이 없습니다."),
	JWT_ERROR(INTERNAL_SERVER_ERROR, "JWT_500_0", "JWT 에러가 발생하였습니다. 관리자에게 문의주세요.");

	private final HttpStatus status;
	private final String code;
	private final String message;
}
