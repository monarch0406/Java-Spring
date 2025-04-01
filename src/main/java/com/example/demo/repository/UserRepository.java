package com.example.demo.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.User;
import com.example.demo.entity.Department;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // JpaRepository 已經內建 findAll() 方法，不需要額外定義

    // 新增依據 Department 查詢使用者的方法
    List<User> findByDepartment(Department department);
}
