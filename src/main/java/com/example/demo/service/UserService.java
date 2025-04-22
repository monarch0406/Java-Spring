package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.entity.Department;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    // 建構子注入，方便測試時傳入 mock
    public UserService(UserRepository userRepository,
                        DepartmentRepository departmentRepository) {
        this.userRepository = userRepository;
        this.departmentRepository = departmentRepository;
    }

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
            // 取得原本的部門
            Department oldDept = user.getDepartment();

            // 依據更新內容找到或建立新的部門
            String deptName = updateUser.getDepartment().getDepartmentName();
            Department newDept = departmentRepository.findByDepartmentName(deptName)
                .orElseGet(() -> {
                    Department newDeptTemp = new Department();
                    newDeptTemp.setDepartmentName(deptName);
                    return departmentRepository.save(newDeptTemp);
                });

            // 更新其他欄位
            user.setUsername(updateUser.getUsername());
            user.setEmail(updateUser.getEmail());
            user.setFirstname(updateUser.getFirstname());
            user.setLastname(updateUser.getLastname());
            user.setDepartment(newDept);

            User savedUser = userRepository.save(user);

            // 如果使用者更換部門，檢查原部門是否還有其他使用者
            if (!oldDept.equals(newDept)) {
                if (userRepository.findByDepartment(oldDept).isEmpty()) {
                    departmentRepository.delete(oldDept);
                }
            }

            return savedUser;
        }).orElse(null);
    }

    // 刪除使用者
    public boolean deleteUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Department dept = user.getDepartment();
            userRepository.delete(user);
            // 檢查該部門是否還有其他使用者
            if (userRepository.findByDepartment(dept).isEmpty()) {
                departmentRepository.delete(dept);
            }
            return true;
        } else {
            return false;
        }
    }
}



