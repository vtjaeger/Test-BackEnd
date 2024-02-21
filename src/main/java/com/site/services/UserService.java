package com.site.services;

import com.site.dto.UserDto;
import com.site.dto.UserLoginDto;
import com.site.models.UserModel;
import com.site.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    UserRepository userRepository;
    BCryptPasswordEncoder encoder;

    public UserService(UserRepository userRepository){
        this.encoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }


    public List<UserModel> getAllUsers(){
        List<UserModel> userList = userRepository.findAll();
        return userList;
    }

    public ResponseEntity saveUser(UserDto userDto) {
        UserModel newUser = new UserModel(userDto);

        userRepository.save(newUser);
        return ResponseEntity.ok().build();
    }

    public UserModel updateUser(UUID id, UserDto userDto) {
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
            String encode = this.encoder.encode(userDto.password());
            userModel.setPassword(encode);

        }
        if(!userDto.telephone().isEmpty()){
            userModel.setTelephone(userDto.telephone());
        }

        return userRepository.save(userModel);
    }

    public Boolean deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new RuntimeException("User not found with id: " + id);
        }
    }

    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
