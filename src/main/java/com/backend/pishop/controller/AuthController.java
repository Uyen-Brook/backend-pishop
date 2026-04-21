package com.backend.pishop.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.backend.pishop.request.LoginRequest;
import com.backend.pishop.request.RegisterRequest;
import com.backend.pishop.service.AuthService;

@Controller
@RequestMapping("api/auth")
public class AuthController {
	@Autowired
	private AuthService authService;
	
	   @PostMapping("/register")
	    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		   return authService.register(request);
	   }
	   
	   @PostMapping("/login")
	    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
		   return authService.login(request);
	   }

}
