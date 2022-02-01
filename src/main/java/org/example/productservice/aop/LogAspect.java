package org.example.productservice.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.example.modelproject.dto.LogMongoDTO;
import org.example.productservice.service.producer.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;

@Slf4j
@Aspect
@Component
public class LogAspect {
    private final ProducerService producerService;

    @Autowired
    public LogAspect(ProducerService producerService) {
        this.producerService = producerService;
    }

    @Pointcut("within(org.example.productservice.service.impl..*)")
    public void applicationPackagePointcut() {
        // Method is empty as this is just a Pointcut, the implementations are in the advices.
    }

    @Before("applicationPackagePointcut()")
    public void logAround(JoinPoint joinPoint) {
        producerService.publishToTopic(new LogMongoDTO(getTimestamp(),getUrl()));
    }

    private String getUrl(){
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();

        String url = request.getRequestURL().toString();
        String method = request.getMethod();
        log.info("URL: {} - Method: {}", url, method);
        return method + " - " + url;
    }

    private Timestamp getTimestamp(){
        Timestamp time = new Timestamp(System.currentTimeMillis());
        log.info("Timestamp: "+time);
        return time;
    }
}