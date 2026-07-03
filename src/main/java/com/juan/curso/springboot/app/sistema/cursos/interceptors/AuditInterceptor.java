package com.juan.curso.springboot.app.sistema.cursos.interceptors;

import java.util.Date;

import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.juan.curso.springboot.app.sistema.cursos.entities.Audit;
import com.juan.curso.springboot.app.sistema.cursos.repositories.AuditRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuditInterceptor implements HandlerInterceptor{
	
	@Autowired
	private AuditRepository auditRepository;
	
	@Override
	public void afterCompletion(
		HttpServletRequest request,
		HttpServletResponse response, 
		Object handler,
		@Nullable Exception ex
	) throws Exception {
		if( "DELETE".equals(request.getMethod()) || "GET".equals(request.getMethod()) ) {
			Audit audit = new Audit();
			
			
			audit.setIP(request.getRemoteAddr());
			audit.setMethod(request.getMethod());
			
			String uri = getResourceName(request.getRequestURI());
			audit.setAction( request.getMethod() + ": " +uri );
			
			String uriId = getIdAction(request.getRequestURI());
			audit.setDetails(uriId);
			
			audit.setEndpoint(request.getRequestURI());
			audit.setDate(new Date());
			
			auditRepository.save(audit);
		}
	}
	
	
	public String getResourceName(String uri) {
		if(uri.split("/").length >= 3) {
			return uri.split("/")[2];
		}
		
		return "No result";
	}
	
	public String getIdAction(String uri) {
		if(uri.split("/").length > 0) {
			return uri.split("/")[uri.split("/").length -1];
		}
		return "No result";
	}
}














