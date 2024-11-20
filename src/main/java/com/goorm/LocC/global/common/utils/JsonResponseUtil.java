package com.goorm.LocC.global.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class JsonResponseUtil {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	public static void sendJsonResponse(HttpServletResponse response, HttpStatus httpStatus, Object body)
		throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_VALUE);
		response.setStatus(httpStatus.value());
		response.setCharacterEncoding("UTF-8");
		String responseBody = objectMapper.writeValueAsString(body);
		response.getWriter().write(responseBody);
	}
}
