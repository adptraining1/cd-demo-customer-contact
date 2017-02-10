package com.innoq.mploed.ddd.customercontact.logging;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class Pointcuts {
    @Pointcut("execution(* com.innoq.mploed.ddd.customercontact.receiver.*.receiveMessage(..))")
    public void receivedEvents(){}
}
