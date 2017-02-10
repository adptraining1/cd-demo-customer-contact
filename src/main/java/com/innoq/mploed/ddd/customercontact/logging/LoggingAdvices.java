package com.innoq.mploed.ddd.customercontact.logging;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import static net.logstash.logback.argument.StructuredArguments.*;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class LoggingAdvices {
    public static Logger LOGGER = LoggerFactory.getLogger(LoggingAdvices.class);

    @Around("com.innoq.mploed.ddd.customercontact.logging.Pointcuts.receivedEvents()")
    public Object performanceLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long t1 = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long t2 = System.currentTimeMillis();
        Map structuredArguments = new HashMap();
        structuredArguments.put("log_type", "performance");
        structuredArguments.put("class", proceedingJoinPoint.getTarget().toString());
        structuredArguments.put("time_in_ms", (t2 - t1));
        LOGGER.info("Performance Log: {}", entries(structuredArguments));
        return result;
    }

    @AfterReturning("com.innoq.mploed.ddd.customercontact.logging.Pointcuts.receivedEvents()")
    public void successfullyProcessedEvents(JoinPoint joinPoint) throws Throwable {
        Map structuredArguments = new HashMap();
        structuredArguments.put("log_type", "successfully_processed_events");
        structuredArguments.put("event", joinPoint.getArgs()[0].getClass().toString());
        LOGGER.info("Event successfully processed: {}", entries(structuredArguments));
    }

    @AfterThrowing(value = "com.innoq.mploed.ddd.customercontact.logging.Pointcuts.receivedEvents()", throwing = "throwable")
    public void unsuccessfullyProcessedEvents(JoinPoint joinPoint, Throwable throwable) throws Throwable {
        Map structuredArguments = new HashMap();
        structuredArguments.put("log_type", "not_successfully_processed_events");
        structuredArguments.put("event", joinPoint.getArgs()[0].getClass().toString());
        structuredArguments.put("exception", throwable.getClass().toString());
        structuredArguments.put("message", throwable.getMessage());
        LOGGER.error("Event processed with exception: {}", entries(structuredArguments), throwable);
    }
}
