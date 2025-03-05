package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.respository.UserRepository;
import java.util.Optional;


@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    // 取得所有使用者
    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    // 取得單一使用者
    public Optional<User> getUserById(Long id){
        return userRepository.findById(id);
    }

    // 新增使用者
    public User createUser(User user){
        return userRepository.save(user);
    }

    // 更新使用者
    public User updateUser(Long id, User updateUser){
        User user = userRepository.findById(id).orElse(null);
        if (user == null){
            return null;
        }
        user.setUsername(updateUser.getUsername());
        user.setEmail(updateUser.getEmail());    
        user.setFirstname(updateUser.getFirstname());
        user.setLastname(updateUser.getLastname());
        return userRepository.save(user);
    }

    // 刪除使用者
    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}
