package com.site.controllers;

import com.site.dto.LoginResponse;
import com.site.dto.UserLoginDto;
import com.site.dto.UserRegister;
import com.site.models.UserModel;
import com.site.repositories.UserRepository;
import com.site.security.TokenService;
import com.site.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin("*")
public class LoginController {
    @Autowired
    UserService userService;
    @Autowired
    TokenService tokenService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @PostMapping
    public ResponseEntity login(@RequestBody UserLoginDto userLoginDto){
        var usernamePassword = new UsernamePasswordAuthenticationToken(userLoginDto.email(), userLoginDto.password());

        var auth = authenticationManager.authenticate(usernamePassword);
        var token = tokenService.gerarToken((UserModel) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid UserRegister userRegister){
        String senhaEncriptada = new BCryptPasswordEncoder().encode(userRegister.password());
        UserModel newUser = new UserModel(userRegister.name(), userRegister.email(), senhaEncriptada, userRegister.telephone(),
                userRegister.role());
        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }
}
