package com.sundeinfo.core.log;

import com.sundeinfo.core.mvc.service.Service;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;

public interface  LogService<T,D extends LogInfo> extends Service<T,D> {

    D createLog(ProceedingJoinPoint joinPoint, ActionLog log,HttpServletRequest request);

    void export(D d);
}
