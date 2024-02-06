package com.site.controllers;

import com.site.dto.UserDto;
import com.site.models.UserModel;
import com.site.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<UserModel> getAllUsers(){
        return userRepository.findAll();
    }

    @PostMapping
    public UserModel saveUser(@RequestBody @Valid UserDto userDto){
        var userModel = new UserModel();
        BeanUtils.copyProperties(userDto, userModel);
        return userRepository.save(userModel);
    }

    @PutMapping("/{id}")
    public UserModel editUser(@PathVariable(value = "id") UUID id, @RequestBody @Valid UserDto userDto) {
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

        return userRepository.save(userModel);
    }

    @DeleteMapping("/{id}")
    public Optional<UserModel> deleteUser(@PathVariable(value = "id") UUID id) {
        Optional<UserModel> userDeleted = userRepository.findById(id);
        if (userDeleted.isPresent()) {
            UserModel user = userDeleted.get();
            userRepository.deleteById(user.getId());
        }
        return userDeleted;
    }
}
