package com.sundeinfo.sypglass.component;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.security.model.AnyResource;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.define.MessageType;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.MessageDto;
import com.sundeinfo.sypglass.dto.UserSettingDetailDto;
import com.sundeinfo.sypglass.service.MessageService;
import com.sundeinfo.sypglass.service.ResourceService;
import com.sundeinfo.sypglass.service.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component("SendMessageAdapter")
public class SendMessageAdapter extends AbstractComponent<SendMessageAdapter> {

    @Autowired
    private UserSettingService userSettingService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    @Autowired
    private ResourceService resourceService;

    public void send(String sendUserSetting,long resourceId,MessageType type) throws IllegalAccessException, InstantiationException {
        String[] settings = sendUserSetting.split("_");
        HashMap<String,UserSettingDetailDto> settingDetailDtoHashMap = new HashMap<>();
        for (String setting:settings) {
            if (StringUtils.isNotBlank(setting)){
                List<UserSettingDetailDto> detailDtos = userSettingService.findDetailBySettingId(Long.parseLong(setting));
                if (detailDtos != null){
                    for (UserSettingDetailDto detail:detailDtos) {
                        if (!settingDetailDtoHashMap.containsKey(detail.getCode())){
                            settingDetailDtoHashMap.put(detail.getCode(),detail);
                        }
                    }
                }
            }
        }

        if (!settingDetailDtoHashMap.isEmpty()){
            AnyResource resource = resourceService.getResourceById(SypGlass.RESOURCE_TYPE_FILE,resourceId);
            List<MessageDto> messageDtos = new ArrayList<>();
            MessageDto message = null;
            for (UserSettingDetailDto detail:settingDetailDtoHashMap.values()) {
                message = messageService.createNew(authenticationGetter.getAnyUser(),resource,type);
                message.setToUser(Long.parseLong(detail.getCode()));
                messageDtos.add(message);
            }
            messageService.save(messageDtos);
        }
    }

}