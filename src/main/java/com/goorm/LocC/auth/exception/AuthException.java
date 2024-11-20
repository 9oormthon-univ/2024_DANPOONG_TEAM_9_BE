package com.goorm.LocC.auth.exception;

import com.goorm.LocC.global.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends BaseException {

	public AuthException(AuthErrorCode errorCode) {
		super(errorCode);
	}
}
