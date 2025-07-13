package com.letrogthien.auth.anotation;

import com.letrogthien.auth.entities.AuditLog;
import com.letrogthien.auth.repositories.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.UUID;

@Aspect
@Component
@RequiredArgsConstructor
public class AuditAspect {


    private final AuditLogRepository auditLogRepository;

    @Around("@annotation(com.letrogthien.auth.anotation.CusAuditable)")
    public Object logAudit(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        CusAuditable auditAnnotation = method.getAnnotation(CusAuditable.class);
        String action = auditAnnotation.action();
        String description = auditAnnotation.description();
        UUID userId = null;
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();
        for (int i = 0; i < parameterNames.length; i++) {
            if ("userId".equals(parameterNames[i]) && args[i] instanceof UUID) {
                userId = (UUID) args[i];
                break;
            }
        }
        AuditLog auditLog = new AuditLog();
        auditLog.setUserId(userId);
        auditLog.setAction(action);
        auditLog.setDescription(description);
        auditLogRepository.save(auditLog);
        return joinPoint.proceed();
    }
}