//package com.makeup.demo.controllers;
//
//import com.makeup.demo.config.JwtUtils;
//import com.makeup.demo.dao.UserDao;
//import com.makeup.demo.dto.AuthenticationRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping("/auth")
//public class AuthenticationController {
//
//
//    private final AuthenticationManager authenticationManager;
//    private final UserDao userDao;
//    private final JwtUtils jwtUtils;
//
//
//@PostMapping("/authenticate")
//    public ResponseEntity<String> authenticate (
//            @RequestBody AuthenticationRequest request){
//    authenticationManager.authenticate(
//            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//    );
//    final UserDetails user = userDao.findUserByEmail(request.getEmail());
//    if (user != null){
//        return ResponseEntity.ok(jwtUtils.generateToken(user));
//    }
//    return  ResponseEntity.status(400).body("Error");
//    }
//
//}
