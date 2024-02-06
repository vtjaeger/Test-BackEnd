package com.site.controllers;

import com.site.dto.UserDto;
import com.site.models.UserModel;
import com.site.repositories.UserRepository;
import com.site.services.UserService;
import jakarta.validation.Valid;
import org.apache.catalina.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin("*")
public class UserController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<List<UserModel>> getAllUsers(){
        List<UserModel> userList = userRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(userList);
    }

    @PostMapping
    public ResponseEntity<UserModel> saveUser(@RequestBody @Valid UserDto userDto){
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        UserModel savedUser = userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserModel> updateUser(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserDto userDto) {
        Optional<UserModel> existingUser = userRepository.findById(id);

        if (existingUser.isEmpty()) {
            throw new RuntimeException("User not found with id: " + id);
        }
        UserModel userModel = existingUser.get();
        if(!userDto.name().isEmpty()){
            userModel.setName(userDto.name());
        }
        if(!userDto.email().isEmpty()){
            userModel.setEmail(userDto.email());
        }
        if(!userDto.password().isEmpty()){
            userModel.setPassword(userDto.password());
        }
        if(!userDto.telephone().isEmpty()){
            userModel.setTelephone(userDto.telephone());
        }

        UserModel updatedUser = userRepository.save(userModel);

        return ResponseEntity.status(HttpStatus.OK).body(updatedUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id") UUID id) {
        Optional<UserModel> userDeleted = userRepository.findById(id);
        if (userDeleted.isPresent()) {
            UserModel user = userDeleted.get();
            userRepository.deleteById(user.getId());
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }
}
