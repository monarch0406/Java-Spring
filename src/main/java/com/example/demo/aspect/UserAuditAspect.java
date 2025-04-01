package com.example.demo.aspect;

// import
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserAuditAspect {

    @Pointcut("execution(* com.example.demo.service.UserService.getAllUser())")
    public void userQueryMethods() {}

    @After("userQueryMethods()")
    public void afterQueryUser(JoinPoint joinPoint) {
        System.out.println(">>> AOP: afterQueryUser");
    }
}

