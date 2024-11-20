package com.goorm.LocC.auth.exception;

import com.goorm.LocC.global.exception.BaseException;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class JwtException extends BaseException {

	public JwtException(JwtErrorCode errorCode) {
		super(errorCode);
	}
}
