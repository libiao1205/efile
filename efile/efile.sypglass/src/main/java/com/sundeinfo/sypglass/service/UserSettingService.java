package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.sypglass.dto.UserSettingDetailDto;
import com.sundeinfo.sypglass.dto.UserSettingDto;
import com.sundeinfo.sypglass.mapper.UserSettingDetailMapper;
import com.sundeinfo.sypglass.mapper.UserSettingMapper;
import com.sundeinfo.sypglass.model.UserSetting;
import com.sundeinfo.sypglass.model.UserSettingDetail;
import com.sundeinfo.sypglass.model.UserSettingDetailExample;
import com.sundeinfo.sypglass.model.UserSettingExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("UserSettingService")
public class UserSettingService extends AbstractService<UserSettingService,UserSettingDto> {

    @Autowired
    private UserSettingMapper userSettingMapper;

    @Autowired
    private UserSettingDetailMapper userSettingDetailMapper;

    public List<UserSettingDto> findUserSettingByUser(long id) throws IllegalAccessException, InstantiationException {
        UserSettingExample example = new UserSettingExample();
        UserSettingExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(id);
        List<UserSetting> settings = userSettingMapper.selectByExample(example);

        if (settings == null || settings.size() == 0){
            return new ArrayList<>();
        }else {
            return ConvertUtils.convertList(settings,UserSettingDto.class);
        }
//        List<Long> ids = ReflectUtils.reflectDataList(settings,"id",Long.class);
    }

    public List<UserSettingDto> findUserSettingByUserAndKind(long id,String kind) throws IllegalAccessException, InstantiationException {
        UserSettingExample example = new UserSettingExample();
        UserSettingExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(id);
        criteria.andKindEqualTo(kind);
        List<UserSetting> settings = userSettingMapper.selectByExample(example);

        if (settings == null || settings.size() == 0){
            return new ArrayList<>();
        }else {
            return ConvertUtils.convertList(settings,UserSettingDto.class);
        }
//        List<Long> ids = ReflectUtils.reflectDataList(settings,"id",Long.class);
    }

    public List<UserSettingDetailDto> findDetailBySettingId(long id) throws InstantiationException, IllegalAccessException {
        UserSettingDetailExample example = new UserSettingDetailExample();
        UserSettingDetailExample.Criteria criteria = example.createCriteria();
        criteria.andSettingidEqualTo(id);
        List<UserSettingDetail> settings = userSettingDetailMapper.selectByExample(example);
        if (settings == null || settings.size() == 0){
            return new ArrayList<>();
        }else{
            return ConvertUtils.convertList(settings,UserSettingDetailDto.class);
        }

    }

    @Transactional
    public int save(UserSettingDto userSettingDto) throws InstantiationException, IllegalAccessException {
        UserSetting setting = ConvertUtils.convert(userSettingDto,UserSetting.class);
        int i = userSettingMapper.insert(setting);

        if (i == 0){
            return 0;
        }

        List<UserSettingDetail> details = ConvertUtils.convertList(userSettingDto.getDetails(),UserSettingDetail.class);
        for (int index = 0; index < details.size(); index++) {
            UserSettingDetail detail = details.get(index);
            detail.setSettingid(setting.getId());
            detail.setSeq(index + 1);
            i = userSettingDetailMapper.insert(detail);
            if (i == 0){
                return 0;
            }
        }
        userSettingDto.setId(setting.getId());
        return 1;
    }

    @Transactional
    public int mod(UserSettingDto userSettingDto) throws InstantiationException, IllegalAccessException {
        UserSetting setting = new UserSetting();
        setting.setName(userSettingDto.getName());

        UserSettingExample example = new UserSettingExample();
        UserSettingExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userSettingDto.getId());


        int i = userSettingMapper.updateByExampleSelective(setting,example);

        if (i == 0){
            return 0;
        }

        //先删后插
        UserSettingDetailExample userSettingDetailExample = new UserSettingDetailExample();
        UserSettingDetailExample.Criteria userSettingDetailCriteria = userSettingDetailExample.createCriteria();
        userSettingDetailCriteria.andSettingidEqualTo(userSettingDto.getId());
        i = userSettingDetailMapper.deleteByExample(userSettingDetailExample);

        List<UserSettingDetail> details = ConvertUtils.convertList(userSettingDto.getDetails(),UserSettingDetail.class);
        for (int index = 0; index < details.size(); index++) {
            UserSettingDetail detail = details.get(index);
            detail.setSettingid(userSettingDto.getId());
            detail.setSeq(index + 1);
            i = userSettingDetailMapper.insert(detail);
            if (i == 0){
                return 0;
            }
        }
        return 1;
    }

    @Transactional
    public int delete(UserSettingDto userSettingDto){
        UserSettingDetailExample example = new UserSettingDetailExample();
        UserSettingDetailExample.Criteria criteria = example.createCriteria();
        criteria.andSettingidEqualTo(userSettingDto.getId());
        userSettingDetailMapper.deleteByExample(example);

        return userSettingMapper.deleteByPrimaryKey(userSettingDto.getId());
    }
}
