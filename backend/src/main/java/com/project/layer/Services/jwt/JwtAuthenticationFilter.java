package com.project.layer.Services.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,@NonNull HttpServletResponse response,@NonNull FilterChain filterChain)
        throws ServletException, IOException {

        final String token = getTokenFromRequest(request);
            
            
        // Imprimir información sobre la solicitud
        System.err.println("Información de la solicitud:");
        System.err.println("Método: " + request.getMethod());
        System.err.println("URI: " + request.getRequestURI());
        System.err.println("URL completa: " + request.getRequestURL().toString());
        System.err.println("Encabezados:");
        
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.err.println(headerName + ": " + request.getHeader(headerName));
        }
        
        System.err.println("Parámetros:");
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            String paramName = entry.getKey();
            String[] paramValues = entry.getValue();
            System.err.println(paramName + ": " + Arrays.toString(paramValues));
        }
        
        System.err.println("Token: " + token);

        if (token == null) {
            filterChain.doFilter(request, response);
            return;
        }

        filterChain.doFilter(request, response);
    }


    private String getTokenFromRequest(HttpServletRequest request) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(StringUtils.hasText(authHeader) && authHeader.startsWith("Bearer ")){
            return authHeader.substring(7);
        }

        return null;
    }
    
}
