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
            config = configService.create(SypGlassConfig.INPUT_DEPT_SETTING[0],SypGlassConfig.INPUT_DEPT_SETTING[1],"","????????????????????????");
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
            if (selfSysId == null){//?????????
                if ("Company".equals(departmentTransformer.getOakind())) {//???????????????
                    int parentId = departmentTransformer.getParentid();
                    if (parentId == 0) {//??????
                        saved = saved + saveDepartment(departmentTransformer,SypGlass.ROOT_DEPT_ID);
                    }else {//?????????
                        //????????????
                        Long parentSysId = findSavedSysDeptId("Company",0L, departmentTransformer.getParentid());
                        if (parentSysId == null){//????????????????????????????????????????????????
                            canntSaveList.add(departmentTransformer);
                        }else{
                            saved = saved + saveDepartment(departmentTransformer,parentSysId);
                        }
                    }
                }else {//???????????????
                    if (departmentTransformer.getParentid() == 0){//???????????????
                        //????????????
                        Long parentSysId = findSavedSysDeptId("Company",0L, departmentTransformer.getCompanyid());
                        if (parentSysId == null){//????????????????????????????????????????????????
                            canntSaveList.add(departmentTransformer);
                        }else{
                            saved = saved + saveDepartment(departmentTransformer,parentSysId);
                        }
                    }else{//??????????????????
                        Long parentSysId = findSavedSysDeptId("Department", departmentTransformer.getCompanyid(), departmentTransformer.getParentid());
                        if (parentSysId == null){//????????????????????????????????????????????????
                            canntSaveList.add(departmentTransformer);
                        }else{
                            saved = saved + saveDepartment(departmentTransformer,parentSysId);
                        }
                    }
                }
            }else{//????????????
                if ("Company".equals(departmentTransformer.getOakind())) {//??????????????????
                    int parentId = departmentTransformer.getParentid();
                    if (parentId == 0) {//??????
                        saved = saved + updateDepartment(departmentTransformer,SypGlass.ROOT_DEPT_ID);
                    }else{//?????????
                        //????????????
                        Long parentSysId = findSavedSysDeptId("Company", departmentTransformer.getCompanyid(), departmentTransformer.getParentid());
                        if (parentSysId == null){//????????????????????????????????????????????????
                            canntSaveList.add(departmentTransformer);
                        }else{
                            saved = saved + updateDepartment(departmentTransformer,parentSysId);
                        }
                    }
                }else {//??????????????????
                    if (departmentTransformer.getParentid() == 0) {//???????????????
                        //????????????
                        Long parentSysId = findSavedSysDeptId("Company", departmentTransformer.getCompanyid(), departmentTransformer.getOaid());
                        if (parentSysId == null) {//????????????????????????????????????????????????
                            canntSaveList.add(departmentTransformer);
                        } else {
                            saved = saved + updateDepartment(departmentTransformer, parentSysId);
                        }
                    } else {//??????????????????
                        Long parentSysId = findSavedSysDeptId("Department", departmentTransformer.getCompanyid(), departmentTransformer.getParentid());
                        if (parentSysId == null) {//????????????????????????????????????????????????
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
                logger.warn("????????????????????????????????????????????????");
                for (DepartmentTransformer departmentTransformer :canntSaveList) {
                    logger.warn(String.format("?????????????????????oaId: %s , oaKind:%s", departmentTransformer.getOaid(), departmentTransformer.getOakind()));
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
            config = configService.create(SypGlassConfig.INPUT_USER_SETTING[0],SypGlassConfig.INPUT_USER_SETTING[1],"","????????????????????????");
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
            if (selfSysId == null) {//?????????
                Long selfSysDeptId = findSavedSysDeptId("Department",userTransformer.getCompanyid(),userTransformer.getDepartmentid());
                if (selfSysDeptId != null){
                    saveUser(userTransformer,selfSysDeptId);
                }else{
                    logger.warn(String.format("???????????????????????????oaId: %s , oaName: %s ,??????: %s  ,??????: %s ",
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
                logger.error("UserJoin?????????OA??????????????????????????????????????????OA??????id???" + user);
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
            //TODO:??????OA????????????????????????
            return 1;
        }else{
            if (userJoins.size() > 1){
                logger.error("UserJoin?????????OA??????????????????????????????????????????OA??????id???" + user);
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
