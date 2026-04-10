package org.example.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

    @Pointcut("within(org.example.service.LessonService)")
    public void method() {
    }

    @Before("method()")
    public void startCountingTheTime(JoinPoint joinPoint) {
        long start = System.currentTimeMillis();
        String nameMethod = joinPoint.getSignature().getName();
        LOGGER.info("Method {} started at {}", nameMethod, start);
    }

    @After("method()")
    public void endCountingTheTime(JoinPoint joinPoint) {
        long end = System.currentTimeMillis();
        String nameMethod = joinPoint.getSignature().getName();
        LOGGER.info("Method {} finished working in {}", nameMethod, end);
    }
}