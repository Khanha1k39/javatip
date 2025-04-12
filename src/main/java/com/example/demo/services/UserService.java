package com.example.demo.services;
import com.example.demo.domain.User;

import com.example.demo.repository.UserRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User handleCreateUser(User user) throws BadRequestException {
        if(this.userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User already exist");
        }

        return this.userRepository.save(user);
    }
    public void handleDeleteUser(Long id) {
         this.userRepository.deleteById(id);
    }
    public List<User>  handleGetUser() {
        return this.userRepository.findAll();
    }
    public User handleUpdateUser (User user) {
        return this.userRepository.save(user);
    }
    public User handleGetUserByEmail (String email) {
        return this.userRepository.getUserByEmail(email);
    }
    public boolean existsById(Long id) {
        return this.userRepository.existsById(id);
    }
    public void updateUserToken(String token,String email) {
        User user = this.userRepository.getUserByEmail(email);
        if(user != null) {
            user.setRefreshToken(token);
            this.userRepository.save(user);
        }
    }
}
