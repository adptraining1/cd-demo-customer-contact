package com.innoq.mploed.ddd.customercontact;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class CustomerContactSpringBootApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(CustomerContactSpringBootApplication.class, args);
    }
}
