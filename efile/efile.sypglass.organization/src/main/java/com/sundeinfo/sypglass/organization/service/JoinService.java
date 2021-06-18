package com.sundeinfo.sypglass.organization.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundeinfo.core.http.HttpService;
import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.organization.configuration.ConfigService;
import com.sundeinfo.sypglass.organization.define.DepartmentStatus;
import com.sundeinfo.sypglass.organization.define.SypGlass;
import com.sundeinfo.sypglass.organization.define.SypGlassConfig;
import com.sundeinfo.sypglass.organization.define.UserStatus;
import com.sundeinfo.sypglass.organization.dto.DepartmentDto;
import com.sundeinfo.sypglass.organization.dto.UserDto;
import com.sundeinfo.sypglass.organization.mapper.DeptJoinMapper;
import com.sundeinfo.sypglass.organization.mapper.UserJoinMapper;
import com.sundeinfo.sypglass.organization.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
            config = configService.create(SypGlassConfig.INPUT_DEPT_SETTING[0],SypGlassConfig.INPUT_DEPT_SETTING[1],"","部门最后同步时间");
            resultJson = httpService.doGet(synchronizer_depts_url);
        }else{
            String url = synchronizer_depts_url + config.getValue();
            resultJson = httpService.doGet(url);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(resultJson);
        List<DepartmentTransformer> departmentTransformers = new ArrayList<>();
        for (JsonNode node:root.get("ctt")){
            String ctt = node.toString();
            departmentTransformers.add(mapper.readValue(ctt, new TypeReference<DepartmentTransformer>() {}));
        }
        saveInputDepartment(departmentTransformers);
        return 1;
    }

    private void saveInputDepartment(List<DepartmentTransformer> departmentTransformers) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        List<DepartmentTransformer> canntSaveList = new ArrayList<>();
        int saved = 0;
        for (DepartmentTransformer departmentTransformer : departmentTransformers) {
            Long selfSysId = findSavedSysDeptId(departmentTransformer.getOakind(), departmentTransformer.getCompanyid(), departmentTransformer.getOaid());
            if (selfSysId == null){//新数据
                if ("Company".equals(departmentTransformer.getOakind())) {//基地新数据
                    int parentId = departmentTransformer.getParentid();
                    if (parentId == 0) {//基地
                        saved = saved + saveDepartment(departmentTransformer,SypGlass.ROOT_DEPT_ID);
                    }else {//子基地
                        //找父基地
                        Long parentSysId = findSavedSysDeptId("Company",0L, departmentTransformer.getParentid());
                        if (parentSysId == null){//上级基地没有导入，新的基地先缓存
                            canntSaveList.add(departmentTransformer);
                        }else{
                            saved = saved + saveDepartment(departmentTransformer,parentSysId);
                        }
                    }
                }else {//部门新数据
                    if (departmentTransformer.getParentid() == 0){//基地的部门
                        //找父基地
                        Long parentSysId = findSavedSysDeptId("Company",0L, departmentTransformer.getCompanyid());
                        if (parentSysId == null){//上级基地没有导入，新的部门先缓存
                            canntSaveList.add(departmentTransformer);
                        }else{
                            saved = saved + saveDepartment(departmentTransformer,parentSysId);
                        }
                    }else{//部门的子部门
                        Long parentSysId = findSavedSysDeptId("Department", departmentTransformer.getCompanyid(), departmentTransformer.getParentid());
                        if (parentSysId == null){//上级部门没有导入，新的部门先缓存
                            canntSaveList.add(departmentTransformer);
                        }else{
                            saved = saved + saveDepartment(departmentTransformer,parentSysId);
                        }
                    }
                }
            }else{//修正数据
                if ("Company".equals(departmentTransformer.getOakind())) {//基地修正数据
                    int parentId = departmentTransformer.getParentid();
                    if (parentId == 0) {//基地
                        saved = saved + updateDepartment(departmentTransformer,SypGlass.ROOT_DEPT_ID);
                    }else{//子基地
                        //找父基地
                        Long parentSysId = findSavedSysDeptId("Company", departmentTransformer.getCompanyid(), departmentTransformer.getParentid());
                        if (parentSysId == null){//上级基地没有导入，新的基地先缓存
                            canntSaveList.add(departmentTransformer);
                        }else{
                            saved = saved + updateDepartment(departmentTransformer,parentSysId);
                        }
                    }
                }else {//部门修正数据
                    if (departmentTransformer.getParentid() == 0) {//基地的部门
                        //找父基地
                        Long parentSysId = findSavedSysDeptId("Company", departmentTransformer.getCompanyid(), departmentTransformer.getOaid());
                        if (parentSysId == null) {//上级基地没有导入，新的部门先缓存
                            canntSaveList.add(departmentTransformer);
                        } else {
                            saved = saved + updateDepartment(departmentTransformer, parentSysId);
                        }
                    } else {//部门的子部门
                        Long parentSysId = findSavedSysDeptId("Department", departmentTransformer.getCompanyid(), departmentTransformer.getParentid());
                        if (parentSysId == null) {//上级部门没有导入，新的部门先缓存
                            canntSaveList.add(departmentTransformer);
                        } else {
                            saved = saved + updateDepartment(departmentTransformer, parentSysId);
                        }
                    }
                }
            }
        }

        if (saved == 0){
            if (canntSaveList.size() > 0){
                logger.warn("有部门没有找到上级部门，导入失败");
                for (DepartmentTransformer departmentTransformer :canntSaveList) {
                    logger.warn(String.format("导入失败数据：oaId: %s , oaKind:%s", departmentTransformer.getOaid(), departmentTransformer.getOakind()));
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
    int saveDepartment(DepartmentTransformer departmentTransformer, long parentId) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName(departmentTransformer.getName());
        if (departmentTransformer.getCanceled() == 1){
            departmentDto.setStatus((byte)DepartmentStatus.DELETE.getStatus());
        }else{
            departmentDto.setStatus((byte)DepartmentStatus.NORMAL.getStatus());
        }
        departmentDto.setType((byte)1);
        departmentDto.setParentId(parentId);

        departmentService.save(departmentDto);

        DeptJoin deptJoin = new DeptJoin();
        if ("Company".equals(departmentTransformer.getOakind())){
            deptJoin.setOacompid(0L);
        }else{
            deptJoin.setOacompid((long) departmentTransformer.getCompanyid());
        }
        deptJoin.setOadeptid((long) departmentTransformer.getOaid());
        deptJoin.setSysid(departmentDto.getId());
        deptJoinMapper.insert(deptJoin);
        return 1;
    }

    @Transactional
    int updateDepartment(DepartmentTransformer departmentTransformer, long parentId) throws IllegalAccessException, InstantiationException {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setName(departmentTransformer.getName());
        if (departmentTransformer.getCanceled() == 1){
            departmentDto.setStatus((byte)DepartmentStatus.DELETE.getStatus());
        }else{
            departmentDto.setStatus((byte)DepartmentStatus.NORMAL.getStatus());
        }
        departmentDto.setType((byte)1);
        departmentDto.setParentId(parentId);

        departmentService.mod(departmentDto);
        return 1;
    }

    public int inputUser() throws Exception {
        Config config = configService.findOneByNameAndCode(SypGlassConfig.INPUT_USER_SETTING[0],SypGlassConfig.INPUT_USER_SETTING[1]);
        String resultJson;
        if (config == null){
            config = configService.create(SypGlassConfig.INPUT_USER_SETTING[0],SypGlassConfig.INPUT_USER_SETTING[1],"","用户最后同步时间");
            resultJson = httpService.doGet(synchronizer_users_url);
        }else{
            String url = synchronizer_users_url + config.getValue();
            resultJson = httpService.doGet(url);
        }

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(resultJson);
        List<UserTransformer> userTransformers = new ArrayList<>();
        for (JsonNode node:root.get("ctt")){
            String ctt = node.toString();
            userTransformers.add(mapper.readValue(ctt, new TypeReference<UserTransformer>() {}));
        }
        saveInputUser(userTransformers);
        return 1;
    }

    private void saveInputUser(List<UserTransformer> userTransformers) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        for (UserTransformer userTransformer:userTransformers) {
            Long selfSysId = findSavedSysUserId(userTransformer.getOaid());
            if (selfSysId == null) {//新数据
                Long selfSysDeptId = findSavedSysDeptId("Department",userTransformer.getCompanyid(),userTransformer.getDepartmentid());
                if (selfSysDeptId != null){
                    saveUser(userTransformer,selfSysDeptId);
                }else{
                    logger.warn(String.format("导入用户失败数据：oaId: %s , oaName: %s ,基地: %s  ,部门: %s ",
                            userTransformer.getOaid(),userTransformer.getName(),userTransformer.getCompanyid(),userTransformer.getDepartmentid()));
                }
            }else{

            }
        }

    }

    private Long findSavedSysUserId(long user){
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
            return userJoins.get(0).getSysid();
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

    private int saveUser(UserTransformer userTransformer,long deptId) throws IllegalAccessException, InstantiationException {
        UserDto userDto = new UserDto();
        if(StringUtils.isEmpty(userTransformer.getUsername())){
            userDto.setUsername("");
            userDto.setStatus((byte) UserStatus.LEAVE.code());
        }else {
            userDto.setUsername(userTransformer.getUsername());
            userDto.setStatus((byte) UserStatus.NORMAL.code());
        }
        userDto.setPassword("111");
        userDto.setName(userTransformer.getName());

        List<DepartmentDto> departmentDtos = new ArrayList<>();
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(deptId);
        departmentDtos.add(departmentDto);

        userService.save(userDto,departmentDtos);

        UserJoin userJoin = new UserJoin();
        userJoin.setSysid(userDto.getId());
        userJoin.setOaid((long)userTransformer.getOaid());
        userJoin.setName(userTransformer.getName());

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
