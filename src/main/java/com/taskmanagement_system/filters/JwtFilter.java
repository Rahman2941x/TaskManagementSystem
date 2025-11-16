package com.taskmanagement_system.filters;

import com.taskmanagement_system.serviceInterface.CustomUserService;
import com.taskmanagement_system.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;


    private CustomUserService customUserService;

    public JwtFilter(CustomUserService customUserService){
        this.customUserService=customUserService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token=null;
        String userName=null;
        if(authHeader!=null && authHeader.contains("Bearer ")){
            token=authHeader.substring(7);
            userName= jwtUtil.extractUserName(token);
        }

        if(userName!=null && SecurityContextHolder.getContext().getAuthentication()== null){
            UserDetails userDetails = customUserService.loadUserByUsername(userName);
            if(jwtUtil.validateToken(userName, userDetails, token)) {
                UsernamePasswordAuthenticationToken authtoken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authtoken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authtoken);

            }
        }
     filterChain.doFilter(request,response );
    }
}
