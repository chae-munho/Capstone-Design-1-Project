package com.example.firstSpringboot.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {
    //특정 어노테이션을 대상 지정
    @Pointcut("@annotation(com.example.firstSpringboot.annotation.RunningTime)")
    private void enableRunningTime() {}
    //기본 패키지의 모든 메소드
    @Pointcut("execution(* com.example.firstSpringboot..*.*(..))")
    private void cut() {}

    //실행 시점 설정 : 두 조건의 모두 만족하는 대상을 전후로 부가 기능을 삽입해 실행
    @Around("cut() && enableRunningTime()")
    public void loggingRunningTime(ProceedingJoinPoint joinPoint) throws Throwable {
        //메서드 수행 전, 측정 시작
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        //메서드를 수행
        Object returningObj = joinPoint.proceed();

        //메서드 수행 후, 측정 종료 및 로깅
        stopWatch.stop();
        String methodName = joinPoint.getSignature().getName();
        log.info("{}의 총 수행 시간 => {} sec", methodName, stopWatch.getTotalTimeSeconds());
    }

}
