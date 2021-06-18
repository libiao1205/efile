package com.sundeinfo.sypglass.interceptor;

import com.sundeinfo.core.domain.Entity;
import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.security.model.AnyResource;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.annotation.SendMessage;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.FilePreviewDto;
import com.sundeinfo.sypglass.dto.MessageDto;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.model.ActionHistory;
import com.sundeinfo.sypglass.model.Document;
import com.sundeinfo.sypglass.service.ActionHistoryService;
import com.sundeinfo.sypglass.service.MessageService;
import com.sundeinfo.sypglass.service.ResourceService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

//@Aspect
//@Component
public class SendMessageInterceptor extends AbstractComponent<SendMessageInterceptor> {

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Autowired
    private MessageService messageService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ActionHistoryService actionHistoryService;

    @Pointcut("execution(* com.sundeinfo.*.apis..*(..)) && @annotation(com.sundeinfo.sypglass.annotation.SendMessage)")
    public void checkPointcut(){}

    @Around(value = "checkPointcut() && @annotation(sendMessage)")
    public Object interceptor(ProceedingJoinPoint point, SendMessage sendMessage) throws Throwable{
        Object[] objects = point.getArgs();
        long id = 0;

        if(objects.length > sendMessage.idSeq() &&  objects[sendMessage.idSeq()] instanceof Long ){
            id = (long)objects[sendMessage.idSeq()];
        }else if (objects.length > sendMessage.idSeq() && objects[sendMessage.idSeq()] instanceof Entity){
            id = ((Entity)objects[sendMessage.idSeq()]).getId();
        }
        if (id == 0){
            logger.error("没有找到资源id:" + point.toString());
            return point.proceed();
        }
        AnyUser user = authenticationGetter.getAnyUser();
        AnyResource resource = resourceService.getResourceById( sendMessage.kind(),id);
        if (resource == null){
            return point.proceed();
        }
        String kind = StringUtils.isBlank(resource.getKind())? "file":resource.getKind();
        List<ActionHistory> actionHistories = actionHistoryService.findByKindAndId(kind,resource.getId());
        MessageDto baseMessageDto = messageService.createNew(user,resource,sendMessage.type());

        Object result = point.proceed();
        try {
            List<MessageDto> messageDtos = new ArrayList<>();
            MessageDto messageDto;
            for (ActionHistory actionHistory:actionHistories) {
                messageDto = ConvertUtils.convert(baseMessageDto,MessageDto.class);
                messageDto.setToUser(actionHistory.getUserid());
                messageDtos.add(messageDto);
            }
            List<MessageDto> newDtos = new ArrayList<MessageDto>();
            for(int i=0;i<messageDtos.size();i++){
                if(!newDtos.contains(messageDtos.get(i))){
                    newDtos.add(messageDtos.get(i));
                }
            }
            messageService.save(newDtos);

        }catch (Exception ex){

        }

        return result;
    }

}
