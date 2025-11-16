package com.taskmanagement_system.controller;


import com.taskmanagement_system.dto.AuthAndUpdateDTO;
import com.taskmanagement_system.dto.ResponseDTO;
import com.taskmanagement_system.dto.UserDTO;
import com.taskmanagement_system.model.User;
import com.taskmanagement_system.serviceInterface.UserServiceInterface;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    UserServiceInterface userServiceInterface;


    @GetMapping("/")
    public String user(){
        return "Bismillah";
    }


    @GetMapping("get/users")
    public ResponseEntity<ResponseDTO<Page<UserDTO>>> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            HttpServletRequest path
    ) {
        return userServiceInterface.getUsers(page, size, path);
    }

    @PostMapping("/register/user")
    public ResponseEntity<ResponseDTO<User>> addUser(@RequestBody  User user){
        return userServiceInterface.addUser(user);
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<ResponseDTO<User>> deleteUserById(@PathVariable Integer id){
        return userServiceInterface.deleteUser(id);
    }

    @PutMapping("/update-user")
    public ResponseEntity<ResponseDTO<User>> updateUserById(@Valid @RequestBody AuthAndUpdateDTO authAndUpdateDTO){
        return userServiceInterface.authAndUpdateUser(authAndUpdateDTO);
    }

    @PatchMapping("/patch-user/{id}")
    public ResponseEntity<ResponseDTO<Map<String,Object>>> updateSpecificData(@PathVariable Integer id, @RequestBody Map<String,Object> fileds){
        return userServiceInterface.patchUserDetailById(id,fileds);
    }

}
