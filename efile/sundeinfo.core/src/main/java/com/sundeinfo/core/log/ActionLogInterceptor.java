package com.sundeinfo.core.log;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
public class ActionLogInterceptor extends AbstractComponent<ActionLogInterceptor> {

    @Autowired
    private LogConfigurationSupport support;


    @Pointcut("execution(* com.sundeinfo.*.apis..*(..)) && @annotation(com.sundeinfo.core.log.ActionLog)")
    public void checkPointcut(){}

    @Around(value = "checkPointcut() && @annotation(log)")
    public Object interceptor(ProceedingJoinPoint joinPoint, ActionLog log) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LogInfo logInfo = support.getService().createLog(joinPoint,log,request);

        if (logInfo == null){
            return joinPoint.proceed();
        }
        long startTime=System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long endTime=System.currentTimeMillis();

        logInfo.setReturnValue(result);
        logInfo.setStartTime(startTime);
        logInfo.setEndTime(endTime);
        logInfo.setExecTime(endTime - startTime);
        if (result != null){
            logInfo.setResult(result.toString());
        }
        logInfo.setExecInfo(logInfo.getLog());
        support.getService().export(logInfo);
        return result;
    }



}
