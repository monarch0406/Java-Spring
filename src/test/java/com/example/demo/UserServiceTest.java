package com.example.demo;

import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.example.demo.entity.User;
import com.example.demo.repository.DepartmentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.UserService;

public class UserServiceTest {
    Mockery mockery = new Mockery();
    private UserService userService;
    private UserRepository userRepository;
    private DepartmentRepository departmentRepository;

    @BeforeClass
    public void setUp() {
        userRepository = mockery.mock(UserRepository.class);
        departmentRepository = mockery.mock(DepartmentRepository.class);
        
        mockery.checking(new Expectations(){{
            allowing(userRepository).findAll();
            will(returnValue(null));
        }});

        userService = new UserService(userRepository, departmentRepository);
    }

    @Test
    public void testGetAllUser() {
        List<User> result = userService.getAllUser();
        Assert.assertNull(result);
    }
}
