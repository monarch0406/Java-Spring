package com.example.demo.aspect;

import com.example.demo.entity.Audit;
import com.example.demo.entity.User;
import com.example.demo.entity.Department;
import com.example.demo.repository.AuditRepository;
import com.example.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Aspect
@Component
@Transactional  // 確保同一交易內的 save 操作
public class AuditAspect {

    private final AuditRepository auditRepo;
    private final UserRepository  userRepo;

    // 存放 update 前的快照
    private final ThreadLocal<User> updateSnapshot = new ThreadLocal<>();

    public AuditAspect(AuditRepository auditRepo, UserRepository userRepo) {
        this.auditRepo = auditRepo;
        this.userRepo  = userRepo;
    }

    /* -------- Pointcuts -------- */
    @Pointcut("execution(* com.example.demo.service.UserService.createUser(..))")
    public void userCreate() {}

    @Pointcut("execution(* com.example.demo.service.UserService.updateUser(..))")
    public void userUpdate() {}

    @Pointcut("execution(* com.example.demo.service.UserService.deleteUser(..))")
    public void userDelete() {}

    /* ======== Create ======== */
    @AfterReturning(pointcut = "userCreate()", returning = "created")
    public void afterCreate(User created) {
        saveDiff(null, created);
    }

    /* ======== Update ======== */
    @Before("userUpdate()")
    public void beforeUpdate(JoinPoint jp) {
        Long id = (Long) jp.getArgs()[0];
        User orig = userRepo.findById(id).orElse(null);
        if (orig != null) {
            // 深拷貝必要欄位
            User snap = new User();
            snap.setId(orig.getId());
            snap.setUsername(orig.getUsername());
            snap.setEmail(orig.getEmail());
            snap.setFirstname(orig.getFirstname());
            snap.setLastname(orig.getLastname());
            Department dep = orig.getDepartment();
            if (dep != null) {
                Department d2 = new Department();
                d2.setDepartmentName(dep.getDepartmentName());
                snap.setDepartment(d2);
            }
            updateSnapshot.set(snap);
        }
    }

    @AfterReturning(pointcut = "userUpdate()", returning = "updated")
    public void afterUpdate(User updated) {
        User before = updateSnapshot.get();
        saveDiff(before, updated);
        updateSnapshot.remove();
    }

    /* ======== Delete ======== */
    @Before("userDelete()")
    public void beforeDelete(JoinPoint jp) {
        Long id = (Long) jp.getArgs()[0];
        User before = userRepo.findById(id).orElse(null);
        // 刪除前就記錄一次「刪除前」的所有欄位
        saveDiff(before, null);
    }

    /* ======== Helper ======== */
    private void saveDiff(User before, User after) {
        Long uid = (after != null) ? after.getId()
                   : (before != null ? before.getId() : null);

        record("username",   uid,
               before != null ? before.getUsername() : null,
               after  != null ? after .getUsername() : null);

        record("email",      uid,
               before != null ? before.getEmail() : null,
               after  != null ? after .getEmail() : null);

        record("firstname",  uid,
               before != null ? before.getFirstname() : null,
               after  != null ? after .getFirstname() : null);

        record("lastname",   uid,
               before != null ? before.getLastname() : null,
               after  != null ? after .getLastname() : null);

        record("department", uid,
               before != null && before.getDepartment() != null
                   ? before.getDepartment().getDepartmentName() : null,
               after  != null && after .getDepartment() != null
                   ? after .getDepartment().getDepartmentName() : null);
    }

    private void record(String col, Long uid, Object before, Object after) {
        if (!Objects.equals(before, after)) {
            Audit a = new Audit();
            a.setUserId(uid);
            a.setColumnName(col);
            a.setBeforeValue(before == null ? null : before.toString());
            a.setAfterValue(after  == null ? null : after .toString());
            auditRepo.save(a);
        }
    }
}
