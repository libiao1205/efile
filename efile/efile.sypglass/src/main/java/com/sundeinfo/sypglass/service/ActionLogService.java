package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.log.ActionLog;
import com.sundeinfo.core.log.LogInfo;
import com.sundeinfo.core.log.LogService;
import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.mapper.ActionLogMapper;
import com.sundeinfo.sypglass.model.ActionLogExample;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.List;

@Service("ActionLogService")
public class ActionLogService extends AbstractService<ActionLogService,LogInfo> implements LogService<ActionLogService,LogInfo> {

    private ActionLogMapper actionLogMapper;

    private AuthenticationGetter authenticationGetter;

    public void setActionLogMapper(ActionLogMapper actionLogMapper) {
        this.actionLogMapper = actionLogMapper;
    }

    public void setAuthenticationGetter(AuthenticationGetter authenticationGetter) {
        this.authenticationGetter = authenticationGetter;
    }

    @Override
    public LogInfo createLog(ProceedingJoinPoint joinPoint, ActionLog log, HttpServletRequest request) {
        StringBuilder builder = new StringBuilder();
        builder.append("参数列表(");
        for (Object element:joinPoint.getArgs()) {
            builder.append("type:").append(element.getClass().getTypeName()).append(";");
            builder.append("value:").append(element.toString()).append(";");
        }
        builder.append(")");
        try {
            LogInfo logInfo = create(log,joinPoint);
            logInfo.setHttp_Method(request.getMethod());
            logInfo.setClass_Method(joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
            logInfo.setIp(request.getRemoteAddr());
            logInfo.setUrl(request.getRequestURL().toString());
            logInfo.setParameter(builder.toString());
            if (authenticationGetter != null && authenticationGetter.getAnyUser() != null){
                logInfo.setUserId(authenticationGetter.getAnyUser().getUser().getId());
                logInfo.setUserName(authenticationGetter.getAnyUser().getUser().getName());
            }
            return logInfo;
        }catch (Exception ex){
            return null;
        }
    }

    @Override
    public void export(LogInfo logInfo) {
        try {
            if (logInfo != null && logInfo.isNeedful()){
                actionLogMapper.insert(ConvertUtils.convert(logInfo, com.sundeinfo.sypglass.model.ActionLog.class));
            }
        }catch (Exception ex){
            logger.error("log保存失败，异常信息：" + ex.getMessage());
        }

    }

    private LogInfo create(ActionLog log,ProceedingJoinPoint joinPoint) throws IllegalAccessException, InstantiationException {
        LogInfo logInfo = null;
        if (LogInfo.class.isAssignableFrom(log.execClass())){
            logInfo = (LogInfo)log.execClass().newInstance();
        }else{
            logInfo = new LogInfo();
        }
        logInfo.setJoinPoint(joinPoint);
        return logInfo;
    }
}
