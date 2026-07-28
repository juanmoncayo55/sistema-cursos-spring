package com.juan.curso.springboot.app.sistema.cursos.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.juan.curso.springboot.app.sistema.cursos.security.filter.JwtAuthenticationFilter;
import com.juan.curso.springboot.app.sistema.cursos.security.filter.JwtValidationFilter;

@Configuration
public class SpringSecurityConfig {

	@Autowired
	private AuthenticationConfiguration authenticationConfiguration;
	
	@Value("${frontend.server.deploy}")
	private String URL_FRONT;
	
	@Bean
	AuthenticationManager authenticationManager() throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.authorizeHttpRequests( (authz) -> authz
			.requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
			.requestMatchers(HttpMethod.GET, "/api/auth/information").hasRole("ADMIN")
			.requestMatchers(HttpMethod.POST, "/api/student").hasRole("ADMIN")
			.requestMatchers(HttpMethod.POST, "/api/teacher").hasRole("ADMIN")
			.requestMatchers(HttpMethod.POST, "/api/course").hasRole("ADMIN")
			.requestMatchers(HttpMethod.POST, "/api/class").hasRole("ADMIN")
			.requestMatchers(HttpMethod.GET, "/api/class").permitAll()
			.requestMatchers(HttpMethod.GET, "/api/class/{id}").permitAll()
			.requestMatchers(HttpMethod.GET, "/api/comment/{idClass}/student/{idStudent}").permitAll()
			.requestMatchers(HttpMethod.GET, "/api/comment/class/{idClass}").permitAll()
			.requestMatchers(HttpMethod.GET, "/api/comment/student/{idStudent}").permitAll()
			.anyRequest().authenticated()
		)
		.addFilter(new JwtAuthenticationFilter(authenticationManager()))
		.addFilter(new JwtValidationFilter(authenticationManager()))
		.csrf(config -> config.disable())
		.cors(cors -> cors.configurationSource(corsConfigurationSource()))
		.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		.build();
	}
	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration config = new CorsConfiguration();
		config.setAllowedOriginPatterns(Arrays.asList("*"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
		config.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
		config.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration(URL_FRONT, config);
		source.registerCorsConfiguration("/**", config);
		return source;
	}
	
	@Bean
	FilterRegistrationBean<CorsFilter> corsFilter() {
		FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
			new CorsFilter(corsConfigurationSource())
		);
		
		corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
		return corsBean;
		
	}
}