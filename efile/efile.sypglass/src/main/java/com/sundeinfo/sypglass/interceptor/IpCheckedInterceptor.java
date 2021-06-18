package com.sundeinfo.sypglass.interceptor;

import com.sundeinfo.core.domain.Entity;
import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.result.ResultFactory;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.security.model.AnyResource;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.annotation.IpChecked;
import com.sundeinfo.sypglass.dto.FolderIpDto;
import com.sundeinfo.sypglass.service.FolderIpService;
import com.sundeinfo.sypglass.service.FolderService;
import com.sundeinfo.sypglass.utility.IpWhiteCheckUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
public class IpCheckedInterceptor extends AbstractComponent<IpCheckedInterceptor> {
    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Autowired
    private FolderIpService folderIpService;

    @Pointcut("execution(* com.sundeinfo.*.apis..*(..)) && @annotation(com.sundeinfo.sypglass.annotation.IpChecked)")
    public void checkPointcut(){}

    @Around(value = "checkPointcut() && @annotation(ipChecked)")
    public Object interceptor(ProceedingJoinPoint point, IpChecked ipChecked) throws Throwable{
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ip = request.getRemoteAddr();

        if (getLocalIpList().contains(ip)){//本机调试总是允许
            return point.proceed();
        }

        Long id = Long.parseLong(point.getArgs()[0].toString());
        FolderIpDto ipDto = folderIpService.findIpInfoByFolderId(id);
        if(ipDto != null){
            if(IpWhiteCheckUtil.isPermited(ip,ipDto.getIpInfo())){
                return point.proceed();
            }else{
                return ResultFactory.getErrorMessage(RequestState.IP_ERROR,RequestState.IP_ERROR.value());
            }
        }else{
            return point.proceed();
        }

    }

    private List<String> getLocalIpList(){
        List<String> ips = new ArrayList<>();
        ips.add("0:0:0:0:0:0:0:1");
        ips.add("127.0.0.1");
        return ips;
    }
}
