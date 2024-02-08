package com.site.controllers;

import com.site.dto.UserLoginDto;
import com.site.models.UserModel;
import com.site.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login-teste")
@CrossOrigin("*")
public class LoginController {
    @Autowired
    UserService userService;
    @PostMapping
    public ResponseEntity<UserModel> login(@RequestBody UserLoginDto userLoginDto){
        boolean valid = userService.validatePassword(userLoginDto);
        if(!valid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
