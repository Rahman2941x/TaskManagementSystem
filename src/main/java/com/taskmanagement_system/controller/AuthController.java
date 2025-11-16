package com.taskmanagement_system.controller;

import com.taskmanagement_system.dto.AuthDTO;
import com.taskmanagement_system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtil jwtUtil;

    @PostMapping("/authenticate")
    public String generateToken(@RequestBody AuthDTO authDTO){

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authDTO.userName(),authDTO.Password()));
            return jwtUtil.generateToken(authDTO.userName());
        }catch (Exception e){
            throw e;
        }
    }

}
