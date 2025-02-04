package com.mawulidev.reflectionannotation;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogExecutionTimeAspect {
    private final Logger logger = LoggerFactory.getLogger(LogExecutionTimeAspect.class);

    //Intercept methods with @LogExecutionTime
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        //Execute the actual method
        Object result = joinPoint.proceed();
        long endTime = System.currentTimeMillis();

        logger.info("Execution time of {} is {} ms", joinPoint.getSignature(), (endTime - startTime));
        return result;

    }
}
