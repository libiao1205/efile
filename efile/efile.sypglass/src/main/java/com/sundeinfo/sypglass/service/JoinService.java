package com.sundeinfo.sypglass.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundeinfo.core.http.HttpService;
import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.configuration.ConfigService;
import com.sundeinfo.sypglass.define.DepartmentStatus;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.define.SypGlassConfig;
import com.sundeinfo.sypglass.define.UserStatus;
import com.sundeinfo.sypglass.dto.DepartmentDto;
import com.sundeinfo.sypglass.dto.ProcessSettingDto;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.mapper.DeptJoinMapper;
import com.sundeinfo.sypglass.mapper.UserJoinMapper;
import com.sundeinfo.sypglass.model.*;
import com.sundeinfo.sypglass.utility.DateUtils;
import org.jsoup.helper.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("JoinService")
public class JoinService extends AbstractService<JoinService,Object> {


    @Value("${synchronizer.enabled}")
    private String synchronizer_enabled;

    @Value("${synchronizer.depts.path}")
    private String synchronizer_depts_url;

    @Value("${synchronizer.dept.path}")
    private String synchronizer_dept_url;

    @Value("${synchronizer.users.path}")
    private String synchronizer_users_url;

    @Value("${synchronizer.user.path}")
    private String synchronizer_user_url;


    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DeptJoinMapper deptJoinMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserJoinMapper userJoinMapper;

    @Autowired
    private ConfigService configService;

    @Autowired
    private HttpService httpService;

    public int inputDepartment() throws Exception {
        Config config = configService.findOneByNameAndCode(SypGlassConfig.INPUT_DEPT_SETTING[0],SypGlassConfig.INPUT_DEPT_SETTING[1]);
        String resultJson;
        if (config == null){
            config = configService.create(SypGlassConfig.INPUT_DEPT_SETTING[0],SypGlassConfig.INPUT_DEPT_SETTING[1],DateUtils.getStringDateShort(),"????????????????????????");
            resultJson = httpService.doGet(synchronizer_depts_url);
            configService.insert(config);
        }else{
            String url = synchronizer_depts_url + config.getValue();
            resultJson = httpService.doGet(url);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(resultJson);
        List<DepartmentInfo> departmentInfos = new ArrayList<>();
        for (JsonNode node:root.get("ctt")){
            String ctt = node.toString();
            departmentInfos.add(mapper.readValue(ctt, new TypeReference<DepartmentInfo>() {}));
        }
        saveInputDepartment(departmentInfos);
        config.setValue(DateUtils.getStringDateShort());
        configService.editConfig(config);
        return 1;
    }

    private void saveInputDepartment(List<DepartmentInfo> departmentInfos) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        List<DepartmentInfo> canntSaveList = new ArrayList<>();
        int saved = 0;
        for (DepartmentInfo departmentInfo:departmentInfos) {
            Long selfSysId = findSavedSysDeptId(departmentInfo.getOakind(),departmentInfo.getCompanyid(),departmentInfo.getOaid());
            if (selfSysId == null){//?????????
                if ("Company".equals(departmentInfo.getOakind())) {//???????????????
                    int parentId = departmentInfo.getParentid();
                    if (parentId == 0) {//??????
                        saved = saved + saveDepartment(departmentInfo,SypGlass.ROOT_DEPT_ID);
                    }else {//?????????
                        //????????????
                        Long parentSysId = findSavedSysDeptId("Company",0L,departmentInfo.getParentid());
                        if (parentSysId == null){//????????????????????????????????????????????????
                            canntSaveList.add(departmentInfo);
                        }else{
                            saved = saved + saveDepartment(departmentInfo,parentSysId);
                        }
                    }
                }else {//???????????????
                    if (departmentInfo.getParentid() == 0){//???????????????
                        //????????????
                        Long parentSysId = findSavedSysDeptId("Company",0L,departmentInfo.getCompanyid());
                        if (parentSysId == null){//????????????????????????????????????????????????
                            canntSaveList.add(departmentInfo);
                        }else{
                            saved = saved + saveDepartment(departmentInfo,parentSysId);
                        }
                    }else{//??????????????????
                        Long parentSysId = findSavedSysDeptId("Department",departmentInfo.getCompanyid(),departmentInfo.getParentid());
                        if (parentSysId == null){//????????????????????????????????????????????????
                            canntSaveList.add(departmentInfo);
                        }else{
                            saved = saved + saveDepartment(departmentInfo,parentSysId);
                        }
                    }
                }
            }else{//????????????
                if ("Company".equals(departmentInfo.getOakind())) {//??????????????????
                    int parentId = departmentInfo.getParentid();
                    if (parentId == 0) {//??????
                        saved = saved + updateDepartment(selfSysId,SypGlass.ROOT_DEPT_ID,departmentInfo);
                    }else{//?????????
                        //????????????
                        Long parentSysId = findSavedSysDeptId("Company",departmentInfo.getCompanyid(),departmentInfo.getParentid());
                        if (parentSysId == null){//????????????????????????????????????????????????
                            canntSaveList.add(departmentInfo);
                        }else{
                            saved = saved + updateDepartment(selfSysId,parentSysId,departmentInfo);
                        }
                    }
                }else {//??????????????????
                    if (departmentInfo.getParentid() == 0) {//???????????????
                        //????????????
                        Long parentSysId = findSavedSysDeptId("Company", 0, departmentInfo.getCompanyid());
                        if (parentSysId == null) {//????????????????????????????????????????????????
                            canntSaveList.add(departmentInfo);
                        } else {
                            saved = saved + updateDepartment(selfSysId, parentSysId,departmentInfo);
                        }
                    } else {//??????????????????
                        Long parentSysId = findSavedSysDeptId("Department", departmentInfo.getCompanyid(), departmentInfo.getParentid());
                        if (parentSysId == null) {//????????????????????????????????????????????????
                            canntSaveList.add(departmentInfo);
                        } else {
                            saved = saved + updateDepartment(selfSysId, parentSysId,departmentInfo);
                        }
                    }
                }
            }
        }

        if (saved == 0){
            if (canntSaveList.size() > 0){
                logger.warn("????????????????????????????????????????????????");
                for (DepartmentInfo departmentInfo:canntSaveList) {
                    logger.warn(String.format("?????????????????????oaId: %s , oaKind:%s",departmentInfo.getOaid(),departmentInfo.getOakind()));
                }
            }
        }else{
            if (canntSaveList.size()>0){
                saveInputDepartment(canntSaveList);
            }
        }
    }

    private Long findSavedSysDeptId(String kind,long oaCompanyid,long oaId){
        DeptJoinExample example = new DeptJoinExample();
        DeptJoinExample.Criteria criteria = example.createCriteria();
        if ("Company".equals(kind)){
            criteria.andOacompidEqualTo(0L);
        }else{
            criteria.andOacompidEqualTo(oaCompanyid);
        }
        criteria.andOadeptidEqualTo(oaId);
        List<DeptJoin> deptJoins = deptJoinMapper.selectByExample(example);
        if (deptJoins == null || deptJoins.size() == 0){
            return null;//????????????????????????????????????????????????
        }else{
            if (deptJoins.size() > 1){
                logger.error(String.format("DeptJoin?????????OA?????????????????????????????????????????????????????????{%s},OA??????ID???{%s},  OA??????ID???{%s}" , kind,oaCompanyid,oaId));
            }
            return deptJoins.get(0).getSysid();
        }
    }

    public long findSysDeptId(long comp,long dept){
        DeptJoinExample example = new DeptJoinExample();
        DeptJoinExample.Criteria criteria = example.createCriteria();
        criteria.andOacompidEqualTo(comp);
        criteria.andOadeptidEqualTo(dept);
        List<DeptJoin> deptJoins = deptJoinMapper.selectByExample(example);
        if (deptJoins == null || deptJoins.size() == 0){
            //TODO:??????OA????????????????????????
            return 1;
        }else{
            if (deptJoins.size() > 1){
                logger.error(String.format("DeptJoin?????????OA?????????????????????????????????????????????????????????{Department},OA??????ID???{%s},  OA??????ID???{%s}" ,comp,dept));
            }
            return deptJoins.get(0).getSysid();
        }

    }

    @Transactional
    int saveDepartment(DepartmentInfo departmentInfo, long parentId) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName(departmentInfo.getName());
        if (departmentInfo.getCanceled() == 1){
            departmentDto.setStatus((byte)DepartmentStatus.DELETE.getStatus());
        }else{
            departmentDto.setStatus((byte)DepartmentStatus.NORMAL.getStatus());
        }
        departmentDto.setType((byte)1);
        departmentDto.setParentId(parentId);

        departmentService.save(departmentDto);

        DeptJoin deptJoin = new DeptJoin();
        if ("Company".equals(departmentInfo.getOakind())){
            deptJoin.setOacompid(0L);
        }else{
            deptJoin.setOacompid((long)departmentInfo.getCompanyid());
        }
        deptJoin.setOadeptid((long)departmentInfo.getOaid());
        deptJoin.setSysid(departmentDto.getId());
        deptJoinMapper.insert(deptJoin);
        return 1;
    }

    @Transactional
    int updateDepartment(long id, long parentId,DepartmentInfo departmentInfo) throws IllegalAccessException, InstantiationException {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(id);
        departmentDto.setParentId(parentId);
        departmentDto.setName(departmentInfo.getName());
        if (departmentInfo.getCanceled() == 1){
            departmentDto.setStatus((byte)DepartmentStatus.DELETE.getStatus());
        }else{
            departmentDto.setStatus((byte)DepartmentStatus.NORMAL.getStatus());
        }
        departmentDto.setType((byte)1);
        return departmentService.mod(departmentDto);
    }

    public int inputUser() throws Exception {
        Config config = configService.findOneByNameAndCode(SypGlassConfig.INPUT_USER_SETTING[0],SypGlassConfig.INPUT_USER_SETTING[1]);
        String resultJson;
        if (config == null){
            config = configService.create(SypGlassConfig.INPUT_USER_SETTING[0],SypGlassConfig.INPUT_USER_SETTING[1],DateUtils.getStringDateShort(),"????????????????????????");
            resultJson = httpService.doGet(synchronizer_users_url);
            configService.insert(config);
        }else{
            String url = synchronizer_users_url + config.getValue();
            resultJson = httpService.doGet(url);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(resultJson);
        List<UserInfo> userInfos = new ArrayList<>();
        for (JsonNode node:root.get("ctt")){
            String ctt = node.toString();
            userInfos.add(mapper.readValue(ctt, new TypeReference<UserInfo>() {}));
        }
        saveInputUser(userInfos);
        config.setValue(DateUtils.getStringDateShort());
        configService.editConfig(config);
        return 1;
    }

    private void saveInputUser(List<UserInfo> userInfos) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        for (UserInfo userInfo:userInfos) {
            UserJoin userJoin = findSavedSysUserId(userInfo.getOaid());
            if (userJoin == null) {//???????????????
                Long selfSysDeptId = findSavedSysDeptId("Department",userInfo.getCompanyid(),userInfo.getDepartmentid());
                if (selfSysDeptId != null){
                    saveUser(userInfo,selfSysDeptId);
                }else{
                    logger.warn(String.format("???????????????????????????oaId: %s , oaName: %s ,??????: %s  ,??????: %s ",
                            userInfo.getOaid(),userInfo.getName(),userInfo.getCompanyid(),userInfo.getDepartmentid()));
                }
            }else{//???????????????
                UserDto sysUserDto = userService.findById(userJoin.getSysid());
                sysUserDto.setUsername(userInfo.getUsername());
                sysUserDto.setName(userInfo.getName());
                Long selfSysDeptId = findSavedSysDeptId("Department",userInfo.getCompanyid(),userInfo.getDepartmentid());
                if (selfSysDeptId == null){
                    userService.modBaseInfo(sysUserDto,0,0);//?????????????????? ????????????????????????
                }else{
                    userService.modBaseInfo(sysUserDto,0,selfSysDeptId);
                }
                //???????????????????????????????????????????????????
                if (StringUtils.isEmpty(userInfo.getEnddate())){
                    userService.modUserNormal(sysUserDto.getId());
                }else{
                    userService.modUserLeave(sysUserDto.getId());
                }
            }
        }

    }

    private UserJoin findSavedSysUserId(long user){
        UserJoinExample example = new UserJoinExample();
        UserJoinExample.Criteria criteria = example.createCriteria();
        criteria.andOaidEqualTo(user);

        List<UserJoin> userJoins = userJoinMapper.selectByExample(example);
        if(userJoins == null || userJoins.size() == 0){
            return null;
        }else{
            if (userJoins.size() > 1){
                logger.error("UserJoin?????????OA??????????????????????????????????????????OA??????id???" + user);
            }
            return userJoins.get(0);
        }
    }

    public long findSysUserId(long user){
        UserJoinExample example = new UserJoinExample();
        UserJoinExample.Criteria criteria = example.createCriteria();
        criteria.andOaidEqualTo(user);

        List<UserJoin> userJoins = userJoinMapper.selectByExample(example);

        if(userJoins == null || userJoins.size() == 0){
            //TODO:??????OA????????????????????????
            return 1;
        }else{
            if (userJoins.size() > 1){
                logger.error("UserJoin?????????OA??????????????????????????????????????????OA??????id???" + user);
            }
            return userJoins.get(0).getSysid();
        }
    }

    private int saveUser(UserInfo userInfo,long deptId) throws IllegalAccessException, InstantiationException {
        UserDto userDto = new UserDto();
        if(StringUtils.isEmpty(userInfo.getUsername())){
            userDto.setUsername("");
            userDto.setStatus((byte) UserStatus.LEAVE.code());
        }else {
            userDto.setUsername(userInfo.getUsername());
            userDto.setStatus((byte) UserStatus.NORMAL.code());
        }
        userDto.setPassword("111");
        userDto.setName(userInfo.getName());

        List<DepartmentDto> departmentDtos = new ArrayList<>();
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(deptId);
        departmentDtos.add(departmentDto);

        userService.save(userDto,departmentDtos);

        UserJoin userJoin = new UserJoin();
        userJoin.setSysid(userDto.getId());
        userJoin.setOaid((long)userInfo.getOaid());
        userJoin.setName(userInfo.getName());

        userJoinMapper.insert(userJoin);

        return 1;
    }


    public String getCompName(long deptId) throws IllegalAccessException, InstantiationException {
        DeptJoinExample example = new DeptJoinExample();
        DeptJoinExample.Criteria criteria = example.createCriteria();
        criteria.andSysidEqualTo(deptId);
        List<DeptJoin> deptJoins = deptJoinMapper.selectByExample(example);
        if (deptJoins == null || deptJoins.size() == 0){
            return "";
        }
        DeptJoin deptJoin = deptJoins.get(0);
        Long sysDeptId;
        if (deptJoin.getOacompid() == 0){
            sysDeptId = findSavedSysDeptId("Company",0,deptJoin.getOadeptid());
        }else{
            sysDeptId = findSavedSysDeptId("Company",0,deptJoin.getOacompid());
        }
        if (sysDeptId == null){
            return "";
        }
        return departmentService.findById(sysDeptId).getName();
    }
}
