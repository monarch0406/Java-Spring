package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.entity.Department;
import com.example.demo.respository.UserRepository;
import com.example.demo.respository.DepartmentRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

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
        // 先用 departmentName 找 Department，若無則建立
        String deptName = user.getDepartment().getDepartmentName();
        Department department = departmentRepository.findByDepartmentName(deptName)
            .orElseGet(() -> {
                Department newDept = new Department();
                newDept.setDepartmentName(deptName);
                return departmentRepository.save(newDept);
            });

        // 關聯回 user
        user.setDepartment(department);
        return userRepository.save(user);
    }

    // 更新使用者
    public User updateUser(Long id, User updateUser){
        return userRepository.findById(id).map(user -> {
            // 同樣處理部門
            String deptName = updateUser.getDepartment().getDepartmentName();
            Department department = departmentRepository.findByDepartmentName(deptName)
                .orElseGet(() -> {
                    Department newDept = new Department();
                    newDept.setDepartmentName(deptName);
                    return departmentRepository.save(newDept);
                });

            // 更新其他欄位
            user.setUsername(updateUser.getUsername());
            user.setEmail(updateUser.getEmail());
            user.setFirstname(updateUser.getFirstname());
            user.setLastname(updateUser.getLastname());
            user.setDepartment(department);

            return userRepository.save(user);
        }).orElse(null);
    }

    // 刪除使用者
    public boolean deleteUser(Long id) {
        return userRepository.findById(id).map(user -> {
            userRepository.delete(user);
            return true;
        }).orElse(false);
    }
}


