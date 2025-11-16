package com.taskmanagement_system.serviceInterface;


import com.taskmanagement_system.dto.AuthAndUpdateDTO;
import com.taskmanagement_system.dto.ResponseDTO;
import com.taskmanagement_system.dto.UserDTO;
import com.taskmanagement_system.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface UserServiceInterface {

    ResponseEntity<ResponseDTO<Page<UserDTO>>> getUsers(int page, int size, HttpServletRequest path);

    ResponseEntity<ResponseDTO<User>> addUser(User user);

    ResponseEntity<ResponseDTO<User>> deleteUser(Integer id);

    ResponseEntity<ResponseDTO<User>> authAndUpdateUser(@Valid AuthAndUpdateDTO authAndUpdateDTO);

    ResponseEntity<ResponseDTO<Map<String, Object>>> patchUserDetailById(Integer id,Map<String,Object> fields);
}
