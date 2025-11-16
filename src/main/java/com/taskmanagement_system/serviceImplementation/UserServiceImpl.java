package com.taskmanagement_system.serviceImplementation;


import com.taskmanagement_system.dto.AuthAndUpdateDTO;
import com.taskmanagement_system.dto.AuthDTO;
import com.taskmanagement_system.dto.ResponseDTO;
import com.taskmanagement_system.dto.UserDTO;
import com.taskmanagement_system.exception.InvalidCredentialsException;
import com.taskmanagement_system.exception.UserAlreadyExistException;
import com.taskmanagement_system.exception.UserNotFoundException;
import com.taskmanagement_system.model.User;
import com.taskmanagement_system.serviceInterface.UserServiceInterface;
import com.taskmanagement_system.userRepository.UserRepo;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserServiceInterface {

    @Autowired
    UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<ResponseDTO<Page<UserDTO>>> getUsers(int page, int size, HttpServletRequest path) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("userName").ascending());
        Page<User> userPage = userRepo.findAll(pageable);
        Page<UserDTO> dtoPage = userPage.map(user ->
                new UserDTO(
                        user.getId(),
                        user.getUserName(),
                        user.getEmail(),
                        user.getMobileNumber(),
                        user.getAlternativeMobileNumber(),
                        user.getAddress(),
                        user.getRole(),
                        user.isActive()
                ));
        if (dtoPage.isEmpty()) {
            throw new UserNotFoundException("No user Found");
        }
        ResponseDTO<Page<UserDTO>> responseDTO = new ResponseDTO<>(HttpStatus.OK, dtoPage, path.getRequestURI());
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO<User>> addUser(User user) {
        Optional<User> userExist=userRepo.findByUserName(user.getUserName());
       if(userExist.isPresent() && userExist.get().isActive()){
            throw new UserAlreadyExistException("User Already Exist: "+user.getUserName());}

           user.setPassWord(passwordEncoder.encode(user.getPassWord()));
           userRepo.save(user);
       return ResponseEntity.ok(new ResponseDTO<>(HttpStatus.CREATED,user));
    }

    @Override
    public ResponseEntity<ResponseDTO<User>> deleteUser(Integer id) {
      User user=userRepo.findById(id).orElseThrow(() -> new UserNotFoundException("User id not found"));
      userRepo.deleteById(id);
      ResponseDTO<User> responseDTO = new ResponseDTO<>(
        HttpStatus.OK,
              user
      );
     return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO<User>> authAndUpdateUser(AuthAndUpdateDTO authAndUpdateDTO)
    {
        AuthDTO authDTO=authAndUpdateDTO.authDTO();
        UserDTO userDTO=authAndUpdateDTO.userDTO();

        User user = userRepo.findByUserName(authDTO.userName())
                .orElseThrow(()->new UserNotFoundException("User not found with: "+authDTO.userName()));

        if(!user.getPassWord().equals(authDTO.Password())){
            throw new InvalidCredentialsException("Invalid PassWord");
        }
        user.setId(userDTO.id());
        user.setUserName(userDTO.userName());
        user.setEmail(userDTO.email());
        user.setMobileNumber(userDTO.number());
        user.setAlternativeMobileNumber(userDTO.alterNativeNumber());
        user.setRole(userDTO.role());
        user.setActive(userDTO.isActive());

        userRepo.save(user);

        ResponseDTO<User> responseDTO= new ResponseDTO<>(HttpStatus.OK,user);
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<ResponseDTO<Map<String, Object>>> patchUserDetailById(Integer id, Map<String, Object> updates) {
        User user = userRepo.findById(id)
                .orElseThrow(()->new UserNotFoundException("user not found in id: "+id));

        //method 1
        updates.forEach((key,value)->{
                    Field field= ReflectionUtils.findField(User.class,key);
                    if(field!=null){
                    field.setAccessible(true);
                    ReflectionUtils.setField(field,user,value);
                    }});
        userRepo.save(user);
        ResponseDTO<Map<String,Object>> responseDTO=new ResponseDTO<>(HttpStatus.OK,updates);

        /*Method 2
        @AutoWired
        ModelMapper mapper

        public ResponseEntity<ResponseDTO<user>> partiallyUpdate(User user,UserDTO dto){
            mapper.map(user,dto);
            retrun ResponseEntity.ok(new ResponseDTO<>(HttpStatus.ok,dto))
        }
        */
        return ResponseEntity.ok(responseDTO);
    }
}
