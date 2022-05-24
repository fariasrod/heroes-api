package com.heroes.infrastructure.configuration;

import com.heroes.infrastructure.configuration.helper.AspectLogging;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;

@Slf4j
@Aspect
@Component
public class AspectConfiguration {

    @Around("execution(* com.heroes.controller..*(..))")
    public Object profileExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {

        long start = System.currentTimeMillis();
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();

        AspectLogging aspect = AspectLogging.builder()
                .requestId(UUID.randomUUID().toString())
                .host(request.getHeader("host"))
                .uri(request.getRequestURI())
                .method(request.getMethod())
                .api(className + "." + methodName)
                .arguments(Arrays.toString(joinPoint.getArgs()))
                .time(LocalDateTime.now())
                .build();

        Object result = joinPoint.proceed();
        aspect.setExecutionTime(System.currentTimeMillis() - start);
        log.info(aspect.toString());

        return result;
    }
}
