package com.sundeinfo.sypglass.organization.service;


import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.service.UserDomainService;
import com.sundeinfo.sypglass.organization.define.UserStatus;
import com.sundeinfo.sypglass.organization.dto.DepartmentDto;
import com.sundeinfo.sypglass.organization.dto.PasswordDto;
import com.sundeinfo.sypglass.organization.dto.UserDto;
import com.sundeinfo.sypglass.organization.dto.UserInfoDto;
import com.sundeinfo.sypglass.organization.mapper.UserDepartmentMapper;
import com.sundeinfo.sypglass.organization.mapper.UserInfoMapper;
import com.sundeinfo.sypglass.organization.mapper.UserMapper;
import com.sundeinfo.sypglass.organization.model.*;
import com.sundeinfo.sypglass.organization.utility.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("UserService")
public class UserService extends AbstractService<UserService,UserDto> implements UserDomainService<UserService,UserDto> {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserDepartmentMapper userDepartmentMapper;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private JoinService joinService;

    @Override
    public UserDto findByUsername(String username) throws InstantiationException, IllegalAccessException {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andStatusEqualTo((byte)UserStatus.NORMAL.code());
        List<User> users = userMapper.selectByExample(example);
        if (users == null || users.isEmpty()){return null;}
        return   ConvertUtils.convert(users.get(0),UserDto.class);
    }

    @Override
    public UserDto createNewUser() {
        return new UserDto();
    }

    public List<UserDto> findAll(boolean isNeedLeave,boolean isPage) throws InstantiationException, IllegalAccessException {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<Byte> inStatuses = new ArrayList<>();
        inStatuses.add((byte)UserStatus.NORMAL.code());
        inStatuses.add((byte)UserStatus.LOCK.code());
        if(isNeedLeave){
            inStatuses.add((byte)UserStatus.LEAVE.code());
        }
        criteria.andStatusIn(inStatuses);
        List<User> users = userMapper.selectByExample(example);
        if (isPage){
            return ConvertUtils.convertForPage(users,UserDto.class);
        }else {
            return ConvertUtils.convertList(users,UserDto.class);
        }
    }

    public List<UserDto> findBySearchKey(String name,boolean islove) throws InstantiationException, IllegalAccessException {
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        criteria.andNameLike("%" + name + "%");
        List<Byte> notInStatuses = new ArrayList<>();
        notInStatuses.add((byte)UserStatus.DELETE.code());
        if(islove){
            notInStatuses.add((byte)UserStatus.LEAVE.code());
        }
        criteria.andStatusNotIn(notInStatuses);
        List<User> users = userMapper.selectByExample(example);
        return ConvertUtils.convertForPage(users,UserDto.class);
    }

    public List<UserDto> findByIds(List<Long> ids) throws InstantiationException, IllegalAccessException {
        if (ids == null || ids.size() < 1){
            return new ArrayList<>();
        }
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        List<Byte> inStatuses = new ArrayList<>();
        inStatuses.add((byte)UserStatus.NORMAL.code());
        inStatuses.add((byte)UserStatus.LOCK.code());
        criteria.andStatusIn(inStatuses);
        List<User> users = userMapper.selectByExample(example);
        return  ConvertUtils.convertList(users,UserDto.class);
    }

    public UserDto findById(long id) throws InstantiationException, IllegalAccessException {
        User user = userMapper.selectByPrimaryKey(id);
        return ConvertUtils.convert(user,UserDto.class);
    }

    public List<DepartmentDto> findUserAllDepartments(long id) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        UserDepartmentExample example = new UserDepartmentExample();
        UserDepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(id);
        List<UserDepartment> userDepartments = userDepartmentMapper.selectByExample(example);
        List<Long> ids = ReflectUtils.reflectDataList(userDepartments,"deptid",Long.class);
        List<DepartmentDto> departmentDtos = departmentService.findByIds(ids);
        for (DepartmentDto deptDto:departmentDtos) {
            deptDto.setCompName(joinService.getCompName(deptDto.getId()));
        }
        return departmentDtos;
    }

    public List<DepartmentDto> findUserDepartments(long id) throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        UserDepartmentExample example = new UserDepartmentExample();
        UserDepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(id);
        List<UserDepartment> userDepartments = userDepartmentMapper.selectByExample(example);
        List<Long> ids = ReflectUtils.reflectDataList(userDepartments,"deptid",Long.class);
        List<DepartmentDto> departmentDtos = departmentService.findByIdsWithoutGroupType(ids);
        for (DepartmentDto deptDto:departmentDtos) {
            deptDto.setCompName(joinService.getCompName(deptDto.getId()));
        }
        return departmentDtos;
    }

    public List<UserDto> findByDept(long deptid) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        UserDepartmentExample example = new UserDepartmentExample();
        UserDepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andDeptidEqualTo(deptid);
        List<UserDepartment> departmentUsers = userDepartmentMapper.selectByExample(example);
        return findByIds(ReflectUtils.reflectDataList(departmentUsers,"userid",Long.class));
    }

    public List<UserDto> findByDeptWithChildren(long deptid) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        if (deptid == 0 ||deptid == 1){
            List<UserDto> userDtos = findAll(false,false);
            return findByIds(ReflectUtils.reflectDataList(userDtos,"id",Long.class));
        }else{
            List<DepartmentDto> departmentDtos = departmentService.findChildrenListByDeptId(deptid);
            if (departmentDtos == null || departmentDtos.size() == 0){
                return new ArrayList<>();
            }
            List<Long> ids = ReflectUtils.reflectDataList(departmentDtos,"id",Long.class);
            UserDepartmentExample example = new UserDepartmentExample();
            UserDepartmentExample.Criteria criteria = example.createCriteria();
            criteria.andDeptidIn(ids);
            List<UserDepartment> departmentUsers = userDepartmentMapper.selectByExample(example);
            return findByIds(ReflectUtils.reflectDataList(departmentUsers,"userid",Long.class));
        }
    }

    public List<UserDto> findByDeptForPage(long deptid) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        UserDepartmentExample example = new UserDepartmentExample();
        UserDepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andDeptidEqualTo(deptid);
        List<UserDepartment> departmentUsers = userDepartmentMapper.selectByExample(example);
        List<UserDto> userDtos = findByIds(ReflectUtils.reflectDataList(departmentUsers,"userid",Long.class));
        return ConvertUtils.convertForPage(departmentUsers,userDtos);
    }

    public UserInfoDto findUserInfoByUserId(long id) throws InstantiationException, IllegalAccessException {
        UserInfoExample example = new UserInfoExample();
        UserInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(id);
        List<UserInfo> userInfos = userInfoMapper.selectByExample(example);
        if (userInfos == null || userInfos.size() == 0){
            return new UserInfoDto();
        }
        return ConvertUtils.convert(userInfos.get(0),UserInfoDto.class);
    }

    /********************************以下数据库读写****************************************/

    @Transactional
    public int save(UserDto userDto, List<DepartmentDto> departmentDtos) throws InstantiationException, IllegalAccessException {
        User user = ConvertUtils.convert(userDto,User.class);
        user.setPassword(MD5.encodePassword(user.getPassword()));
        userMapper.insert(user);
        userDto.setId(user.getId());
        UserDepartment userDepartment = null;
        for (DepartmentDto departmentDto:departmentDtos) {
            userDepartment = new UserDepartment();
            userDepartment.setUserid(user.getId());
            userDepartment.setDeptid(departmentDto.getId());
            userDepartment.setPositionid(0L);
            userDepartmentMapper.insert(userDepartment);
        }
        return 1;
    }

    public int saveInfo(UserDto userDto,UserInfoDto userInfoDto) throws InstantiationException, IllegalAccessException {
        if (userDto.isEmptyInfo()){
            UserInfo userInfo = new UserInfo();
            userInfo.setUserid(userDto.getId());
            userInfo.setTel(userInfoDto.getTel());
            userInfo.setMobile(userInfoDto.getMobile());
            userInfo.setMail(userInfoDto.getMail());
            userInfo.setInfo(userInfoDto.getInfo());
            return userInfoMapper.insert(userInfo);
        }else{
            UserInfo userInfo = new UserInfo();
            userInfo.setTel(userInfoDto.getTel());
            userInfo.setMobile(userInfoDto.getMobile());
            userInfo.setMail(userInfoDto.getMail());
            userInfo.setInfo(userInfoDto.getInfo());

            UserInfoExample example = new UserInfoExample();
            UserInfoExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(userDto.getInfo().getId());

            return userInfoMapper.updateByExampleSelective(userInfo,example);
        }
    }

    @Transactional
    public int modBaseInfo(UserDto userDto,long oldDeptId,long newDeptId) throws InstantiationException, IllegalAccessException {
        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userDto.getId());
        userMapper.updateByExampleSelective(user,example);
        if (oldDeptId != newDeptId){
            modUserDept(userDto.getId(),oldDeptId,newDeptId);
        }
        return 1;
    }

    @Transactional
    public int modPassword(UserDto userDto) throws InstantiationException, IllegalAccessException {
        User user = new User();
        user.setPassword(MD5.encodePassword(userDto.getPassword()));
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userDto.getId());
        user.setPsdmistakecount(0L);
        return userMapper.updateByExampleSelective(user,example);
    }

    @Transactional
    public int updateUserPsd(PasswordDto passwordDto) {
        User user = new User();
        user.setId(passwordDto.getId());
        user.setPassword(MD5.encodePassword(passwordDto.getCheckPass1()));
        user.setLastpasswordresetdate(new Date());
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(user.getId());
        return userMapper.updateByExampleSelective(user,example);

    }

    @Transactional
    public int modUserDept(long userId,long oldId,long newId){
        UserDepartmentExample example = new UserDepartmentExample();
        UserDepartmentExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        criteria.andDeptidEqualTo(oldId);

        UserDepartment ud = new UserDepartment();
        ud.setDeptid(newId);

        return userDepartmentMapper.updateByExampleSelective(ud,example);
    }

    public int modUserLock(long userId){
        return changeUserStatus(userId,UserStatus.LOCK);
    }

    public int modUserLeave(long userId){
        return changeUserStatus(userId,UserStatus.LEAVE);
    }

    public int modUserNormal(long userId){
        return changeUserStatus(userId,UserStatus.NORMAL);
    }

    private int changeUserStatus(long userId,UserStatus status){
        User user = new User();
        user.setStatus((byte)status.code());
        UserExample example = new UserExample();
        UserExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(userId);
        return userMapper.updateByExampleSelective(user,example);
    }

    @Transactional
    public int delete(UserDto userDto) throws IllegalAccessException, InstantiationException {
//        long id = findByUsername(userDto.getUsername()).getId();
//        UserDepartmentExample example = new UserDepartmentExample();
//        UserDepartmentExample.Criteria criteria = example.createCriteria();
//        criteria.andUseridEqualTo(id);
//        userDepartmentMapper.deleteByExample(example);
//        userMapper.deleteByPrimaryKey(id);
//        return 1;
        return changeUserStatus(userDto.getId(),UserStatus.DELETE);
    }

    @Transactional
    public int updateLastPasswordResetDate(AnyUser anyUser){
        User user = new User();
        user.setId(anyUser.getUser().getId());
        user.setLastpasswordresetdate(new Date());
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional
    public int updatePasswordExceedNotRemind(Long userId){
        User user = new User();
        user.setId(userId);
        user.setRemind(false);
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Transactional
    public int updateByPrimaryKeySelective(User user){
        return userMapper.updateByPrimaryKeySelective(user);
    }
}
