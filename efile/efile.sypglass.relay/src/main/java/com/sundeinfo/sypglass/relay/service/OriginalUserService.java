package com.sundeinfo.sypglass.relay.service;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.sypglass.relay.mapper.oa.OriginalUserMapper;
import com.sundeinfo.sypglass.relay.mapper.relay.UserInfoMapper;
import com.sundeinfo.sypglass.relay.model.oa.HrmResource;
import com.sundeinfo.sypglass.relay.model.relay.UserInfo;
import com.sundeinfo.sypglass.relay.model.relay.UserInfoExample;
import com.sundeinfo.core.utility.StringUtils;
import io.jsonwebtoken.lang.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component("OriginalUserService")
public class OriginalUserService extends AbstractComponent<OriginalUserService> {

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private OriginalUserMapper originalUserMapper;


    public List<UserInfo> findAll(){
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();

        return userInfoMapper.selectByExample(example);
    }

    public List<UserInfo> findByDate(Date date){
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andSynchrodateGreaterThan(date);

        return userInfoMapper.selectByExample(example);
    }

    public int importUser(){
        List<HrmResource> hrmResources = originalUserMapper.selectAllHrmResource();

        List<UserInfo> insertUserInfos = new ArrayList<>();
        List<UserInfo> updateUserInfos = new ArrayList<>();

        for(HrmResource resource:hrmResources){
            UserInfo oa = convert(resource);
            UserInfo existing = findByOaInfo(oa.getOaid());
            if (existing != null){
                if (!isSameUserInfo(existing,oa)){
                    oa.setId(existing.getId());
                    updateUserInfos.add(oa);
                }
            }else{
                oa.setSynchrodate(new Date());
                insertUserInfos.add(oa);
            }
        }
        int insertResult = insertUserInfo(insertUserInfos);
        int updateResult = updateUserInfo(updateUserInfos);
        return (insertResult + updateResult) == 2 ? 1 : 0;
    }

    private UserInfo convert(HrmResource resource){
        UserInfo userInfo = new UserInfo();
        userInfo.setOaid(resource.getId());
        userInfo.setUsername(resource.getLoginid());
        userInfo.setName(resource.getLastname());
        userInfo.setCompanyid(resource.getSubcompanyid1());
        userInfo.setDepartmentid(resource.getDepartmentid());
        userInfo.setEnddate(resource.getEnddate());
        userInfo.setLastmoddate(resource.getLastmoddate());
        return userInfo;
    }

    public UserInfo findByOaInfo(int id){
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andOaidEqualTo(id);

        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        if (userInfos == null || userInfos.size() == 0) return null;
        if (userInfos.size() > 1){
            logger.warn(String.format("发现复数个用户记录，请确认数据正确性,id:%s",id));
        }
        return userInfos.get(0);
    }

    @Transactional
    public int insertUserInfo(List<UserInfo> userInfos){
        int result = 0;
        for (UserInfo userInfo:userInfos) {
            result = insertUserInfo(userInfo);
            if (result < 0) return 0;
        }
        return 1;
    }

    public int insertUserInfo(UserInfo userInfo){
        userInfo.setSynchrodate(new Date());
        return userInfoMapper.insert(userInfo);
    }

    @Transactional
    public int updateUserInfo(List<UserInfo> userInfos){
        int result = 0;
        for (UserInfo userInfo:userInfos) {
            result = updateUserInfo(userInfo);
            if (result < 0) return 0;
        }
        return 1;
    }

    public int updateUserInfo(UserInfo userInfo){
        userInfo.setSynchrodate(new Date());

        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andOaidEqualTo(userInfo.getOaid());

        return userInfoMapper.updateByExample(userInfo,example);
    }

    private boolean isSameUserInfo(UserInfo source,UserInfo target){
        Assert.notNull(source,"UserInfo is null");
        Assert.notNull(target,"UserInfo is null");

        if (!StringUtils.isEqualsString(source.getUsername(),target.getUsername()))return false;
        if (!StringUtils.isEqualsString(source.getName(),target.getName()))return false;
        if (!(source.getCompanyid() != null && source.getCompanyid().equals(target.getCompanyid())))return false;
        if (!(source.getDepartmentid() != null && source.getDepartmentid().equals(target.getDepartmentid())))return false;
        if (!StringUtils.isEqualsString(source.getEnddate(),target.getEnddate()))return false;
        if (!StringUtils.isEqualsString(source.getLastmoddate(),target.getLastmoddate()))return false;
        return true;
    }

}