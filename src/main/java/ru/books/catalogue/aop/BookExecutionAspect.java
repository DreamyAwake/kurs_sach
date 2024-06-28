package ru.books.catalogue.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class BookExecutionAspect {

    @Pointcut("execution(public * ru.books.catalogue.controller.BookController.addBook(..))")
    public void callBookAdd() {}

    @Around("callBookAdd()")
    public Object aroundBookAdd(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long endTime = System.currentTimeMillis();
        log.info("Start with user {} {} in {} and end {}", SecurityContextHolder.getContext().getAuthentication().getName(), proceedingJoinPoint.getSignature().getName(), startTime, endTime);
        return result;
    }
}
