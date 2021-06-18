package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.sypglass.dto.MessageSettingDto;
import com.sundeinfo.sypglass.mapper.MessageSettingMapper;
import com.sundeinfo.sypglass.model.MessageSetting;
import com.sundeinfo.sypglass.model.MessageSettingExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("MessageSettingService")
public class MessageSettingService extends AbstractService<MessageSettingService, MessageSettingDto> {

    @Autowired
    MessageSettingMapper settingMapper;

    public MessageSettingDto findByUser(long userId) throws InstantiationException, IllegalAccessException {
        MessageSettingExample example = new MessageSettingExample();
        MessageSettingExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        List<MessageSetting> settings = settingMapper.selectByExample(example);
        if (settings == null || settings.size() == 0) return null;
        return ConvertUtils.convert(settings.get(0),MessageSettingDto.class);
    }

    @Transactional
    public int save(MessageSettingDto settingDto) throws InstantiationException, IllegalAccessException {
        MessageSetting setting = ConvertUtils.convert(settingDto,MessageSetting.class);
        return settingMapper.insertSelective(setting);
    }

    @Transactional
    public int editMessageSetting(MessageSettingDto settingDto) throws InstantiationException, IllegalAccessException {
        MessageSetting setting = ConvertUtils.convert(settingDto,MessageSetting.class);
        return settingMapper.updateByPrimaryKeySelective(setting);
    }
}
