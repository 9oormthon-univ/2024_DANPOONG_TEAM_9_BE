package com.goorm.LocC.global.config;

import com.goorm.LocC.auth.jwt.JwtAccessDeniedHandler;
import com.goorm.LocC.auth.jwt.JwtAuthenticationEntryPoint;
import com.goorm.LocC.auth.jwt.filter.JwtAuthenticationFilter;
import com.goorm.LocC.auth.jwt.filter.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final JwtExceptionFilter jwtExceptionFilter;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	private final String[] whitelist = {
		"/",
		"/api/v1/auth/**",
		"/swagger-ui/**",
		"/v3/api-docs/**"
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(AbstractHttpConfigurer::disable)
			.cors(Customizer.withDefaults())
			.formLogin(AbstractHttpConfigurer::disable)
			.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(request -> request
				.requestMatchers(whitelist).permitAll()
			.anyRequest().authenticated())
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
			.exceptionHandling(exceptions -> exceptions
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
			);

		return http.build();
	}
}
