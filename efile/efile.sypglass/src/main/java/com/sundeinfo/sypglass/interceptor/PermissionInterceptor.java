package com.sundeinfo.sypglass.interceptor;

import com.sundeinfo.core.permission.Permissionable;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.ResultFactory;
import com.sundeinfo.security.permission.AbstractPermissionChecker;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class PermissionInterceptor extends AbstractPermissionChecker<PermissionInterceptor> {

    @Pointcut("execution(* com.sundeinfo.*.apis..*(..)) && @annotation(com.sundeinfo.core.permission.Permissionable)")
    public void checkPointcut(){}

    @Around(value = "checkPointcut() && @annotation(permissionable)")
    public Object interceptor(ProceedingJoinPoint point, Permissionable permissionable) throws Throwable{

        if (permissionable == null || permissionable.dataKey() == null || permissionable.dataKey().trim().isEmpty()) {
            String log = "Permission参数异常: dataKey参数没有配置";
            if (logger.isDebugEnabled()){logger.debug(log);}
            return ResultFactory.getDataMessage(RequestState.TOKEN_IMPL_ERROR);
        }
        return super.invoke(point, permissionable);
    }


}