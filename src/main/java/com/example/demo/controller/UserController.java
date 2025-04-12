package com.example.demo.controller;

import com.example.demo.domain.User;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.services.UserService;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")  // Đặt URL gốc cho controller

public class UserController {
    final UserService userService;
    private final PasswordEncoder passwordEncoder;
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("")
    public UserDto CreateNewUser(@RequestBody User user) throws BadRequestException {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        User newUser = userService.handleCreateUser(user);
        UserDto userDto = new UserDto();
        userDto.setId(newUser.getId());
        userDto.setEmail(newUser.getEmail());
        userDto.setRole(newUser.getRole());
        return userDto;
    }

    @DeleteMapping("/{id}")
    public String deleUser(@PathVariable Long id) {
//        User user = new User();
//        user.setEmail("email@email.com");
//        user.setPassword("password");
//        user.setName("name");
         userService.handleDeleteUser(id);
        return id.toString();
    }
    @GetMapping("")
    public ResponseEntity <List<User> >  handleGetUser() {
//        User user = new User();
//        user.setEmail("email@email.com");
//        user.setPassword("password");
//        user.setName("name");
        return   ResponseEntity.status(HttpStatus.OK).body(userService.handleGetUser());
    }
    @PutMapping("")
    public User  updateUser(@RequestBody User user) {
//        User user = new User();
//        user.setEmail("email@email.com");
//        user.setPassword("password");
//        user.setName("name");
        return       userService.handleUpdateUser(user);
    }
}
