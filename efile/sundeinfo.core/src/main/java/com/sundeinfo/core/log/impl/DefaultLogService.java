package com.sundeinfo.core.log.impl;

import com.sundeinfo.core.log.ActionLog;
import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.core.log.LogService;
import com.sundeinfo.core.mvc.service.AbstractService;
import org.aspectj.lang.ProceedingJoinPoint;

import javax.servlet.http.HttpServletRequest;


public class DefaultLogService extends AbstractService<DefaultLogService,LogInfo> implements LogService<DefaultLogService,LogInfo> {

    @Override
    public LogInfo createLog(ProceedingJoinPoint joinPoint, ActionLog log, HttpServletRequest request) {
        return null;
    }

    @Override
    public void export(LogInfo logInfo) {

    }
}
