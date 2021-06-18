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
            config = configService.create(SypGlassConfig.INPUT_DEPT_SETTING[0],SypGlassConfig.INPUT_DEPT_SETTING[1],DateUtils.getStringDateShort(),"部门最后同步时间");
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
            if (selfSysId == null){//新数据
                if ("Company".equals(departmentInfo.getOakind())) {//基地新数据
                    int parentId = departmentInfo.getParentid();
                    if (parentId == 0) {//基地
                        saved = saved + saveDepartment(departmentInfo,SypGlass.ROOT_DEPT_ID);
                    }else {//子基地
                        //找父基地
                        Long parentSysId = findSavedSysDeptId("Company",0L,departmentInfo.getParentid());
                        if (parentSysId == null){//上级基地没有导入，新的基地先缓存
                            canntSaveList.add(departmentInfo);
                        }else{
                            saved = saved + saveDepartment(departmentInfo,parentSysId);
                        }
                    }
                }else {//部门新数据
                    if (departmentInfo.getParentid() == 0){//基地的部门
                        //找父基地
                        Long parentSysId = findSavedSysDeptId("Company",0L,departmentInfo.getCompanyid());
                        if (parentSysId == null){//上级基地没有导入，新的部门先缓存
                            canntSaveList.add(departmentInfo);
                        }else{
                            saved = saved + saveDepartment(departmentInfo,parentSysId);
                        }
                    }else{//部门的子部门
                        Long parentSysId = findSavedSysDeptId("Department",departmentInfo.getCompanyid(),departmentInfo.getParentid());
                        if (parentSysId == null){//上级部门没有导入，新的部门先缓存
                            canntSaveList.add(departmentInfo);
                        }else{
                            saved = saved + saveDepartment(departmentInfo,parentSysId);
                        }
                    }
                }
            }else{//修正数据
                if ("Company".equals(departmentInfo.getOakind())) {//基地修正数据
                    int parentId = departmentInfo.getParentid();
                    if (parentId == 0) {//基地
                        saved = saved + updateDepartment(selfSysId,SypGlass.ROOT_DEPT_ID,departmentInfo);
                    }else{//子基地
                        //找父基地
                        Long parentSysId = findSavedSysDeptId("Company",departmentInfo.getCompanyid(),departmentInfo.getParentid());
                        if (parentSysId == null){//上级基地没有导入，新的基地先缓存
                            canntSaveList.add(departmentInfo);
                        }else{
                            saved = saved + updateDepartment(selfSysId,parentSysId,departmentInfo);
                        }
                    }
                }else {//部门修正数据
                    if (departmentInfo.getParentid() == 0) {//基地的部门
                        //找父基地
                        Long parentSysId = findSavedSysDeptId("Company", 0, departmentInfo.getCompanyid());
                        if (parentSysId == null) {//上级基地没有导入，新的部门先缓存
                            canntSaveList.add(departmentInfo);
                        } else {
                            saved = saved + updateDepartment(selfSysId, parentSysId,departmentInfo);
                        }
                    } else {//部门的子部门
                        Long parentSysId = findSavedSysDeptId("Department", departmentInfo.getCompanyid(), departmentInfo.getParentid());
                        if (parentSysId == null) {//上级部门没有导入，新的部门先缓存
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
                logger.warn("有部门没有找到上级部门，导入失败");
                for (DepartmentInfo departmentInfo:canntSaveList) {
                    logger.warn(String.format("导入失败数据：oaId: %s , oaKind:%s",departmentInfo.getOaid(),departmentInfo.getOakind()));
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
            return null;//数据没有导入过，所以是新导入数据
        }else{
            if (deptJoins.size() > 1){
                logger.error(String.format("DeptJoin有多条OA部门数据指向本系统同一部门：部门类型：{%s},OA基地ID：{%s},  OA部门ID：{%s}" , kind,oaCompanyid,oaId));
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
            //TODO:对接OA导入最新部门信息
            return 1;
        }else{
            if (deptJoins.size() > 1){
                logger.error(String.format("DeptJoin有多条OA部门数据指向本系统同一部门：部门类型：{Department},OA基地ID：{%s},  OA部门ID：{%s}" ,comp,dept));
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
            config = configService.create(SypGlassConfig.INPUT_USER_SETTING[0],SypGlassConfig.INPUT_USER_SETTING[1],DateUtils.getStringDateShort(),"用户最后同步时间");
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
            if (userJoin == null) {//新数据插入
                Long selfSysDeptId = findSavedSysDeptId("Department",userInfo.getCompanyid(),userInfo.getDepartmentid());
                if (selfSysDeptId != null){
                    saveUser(userInfo,selfSysDeptId);
                }else{
                    logger.warn(String.format("导入用户失败数据：oaId: %s , oaName: %s ,基地: %s  ,部门: %s ",
                            userInfo.getOaid(),userInfo.getName(),userInfo.getCompanyid(),userInfo.getDepartmentid()));
                }
            }else{//旧数据更新
                UserDto sysUserDto = userService.findById(userJoin.getSysid());
                sysUserDto.setUsername(userInfo.getUsername());
                sysUserDto.setName(userInfo.getName());
                Long selfSysDeptId = findSavedSysDeptId("Department",userInfo.getCompanyid(),userInfo.getDepartmentid());
                if (selfSysDeptId == null){
                    userService.modBaseInfo(sysUserDto,0,0);//如果是新部门 则先修改部门信息
                }else{
                    userService.modBaseInfo(sysUserDto,0,selfSysDeptId);
                }
                //如果已经离职，则更新用户状态为离职
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
                logger.error("UserJoin有多条OA用户数据指向本系统同一用户：OA用户id：" + user);
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
            //TODO:对接OA导入最新用户信息
            return 1;
        }else{
            if (userJoins.size() > 1){
                logger.error("UserJoin有多条OA用户数据指向本系统同一用户：OA用户id：" + user);
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
