package com.site.services;

import com.site.dto.UserDto;
import com.site.models.UserModel;
import com.site.repositories.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public List<UserModel> getAllUsers(){
        List<UserModel> userList = userRepository.findAll();
        return userList;
    }

    public UserModel saveUser(UserDto userDto) {
        Optional<UserModel> existingUserByEmail = userRepository.findByEmail(userDto.email());
        if(existingUserByEmail.isPresent()) {
            throw new RuntimeException();
        }
        Optional<UserModel> existingUserByTelephone = userRepository.findByTelephone(userDto.telephone());
        if(existingUserByTelephone.isPresent()){
            throw new RuntimeException();
        }
        var userModel = new UserModel();

        BeanUtils.copyProperties(userDto, userModel);

        UserModel savedUser = userRepository.save(userModel);
        return savedUser;
    }

}
