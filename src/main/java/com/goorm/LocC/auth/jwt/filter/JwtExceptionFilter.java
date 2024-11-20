package com.goorm.LocC.auth.jwt.filter;

import com.goorm.LocC.auth.exception.JwtErrorCode;
import com.goorm.LocC.auth.exception.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.goorm.LocC.global.common.utils.JsonResponseUtil.sendJsonResponse;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@Slf4j
@Component
public class JwtExceptionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {
		try {
			filterChain.doFilter(request, response);
		} catch (JwtException e) {
			log.warn("Custom Jwt Exception: {}", e.getMessage());

			sendJsonResponse(
				response,
				e.getErrorCode().getStatus(),
				e.getErrorCode().toApiResponse()
			);
		} catch (Exception e) {
			log.error("Jwt Exception: {}", e.getMessage());

			sendJsonResponse(
					response,
					INTERNAL_SERVER_ERROR,
					JwtErrorCode.JWT_ERROR.toApiResponse()
			);
		}
	}
}
