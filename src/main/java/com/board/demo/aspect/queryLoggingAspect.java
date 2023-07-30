package com.board.demo.aspect;

import java.util.Map;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class queryLoggingAspect {

    @Pointcut("execution(* com.board.demo.controller..*(..))")
    public void controllerMethod() {}

    @Before("controllerMethod()")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();

        if (args.length > 0 && args[0] instanceof Map) {
            Map<String, Object> paramMap = (Map<String, Object>) args[0];
            System.out.println("Executing query: " + methodName);
            System.out.println("Parameters: " + paramMap);
        }
    }

    @AfterReturning(pointcut = "controllerMethod()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) {
        String methodName = joinPoint.getSignature().getName();
        System.out.println("Result of " + methodName + ": " + result);
    }
}

