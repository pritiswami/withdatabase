package com.example.spring_security_jwt.controller;

import com.example.spring_security_jwt.entity.AuthRequest;
import com.example.spring_security_jwt.entity.UserInfo;
import com.example.spring_security_jwt.service.JwtService;
import com.example.spring_security_jwt.service.UserInfoService;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;



    @GetMapping("/welcome")
    public String welcome(){
        return "welcome in spring security";
    }

    @PostMapping("/addUser")
    public String addUser(@RequestBody UserInfo userInfo){
       return userInfoService.addUser(userInfo);
    }

    @PostMapping("/login")
    public String userLogin(@RequestBody AuthRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                (authRequest.getUserName(), authRequest.getPassword()));
        if (authenticate.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUserName());
        } else {
            throw new UsernameNotFoundException("invalid user request");
        }
    }

    @GetMapping("/getUsers")
    public List<UserInfo> getAllUsers(){
        return userInfoService.getAllUser();
    }

    @GetMapping("/getUsers/{id}")
    public UserInfo getAllUsers(@PathVariable Integer id){
        return userInfoService.getUser(id);
    }



}
