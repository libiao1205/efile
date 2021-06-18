package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.security.service.AdminDomainService;
import com.sundeinfo.sypglass.define.AdminFolderStatus;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.AdminDto;
import com.sundeinfo.sypglass.dto.AdminFolderDto;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.mapper.AdminFolderMapper;
import com.sundeinfo.sypglass.mapper.AdminMapper;
import com.sundeinfo.sypglass.model.Admin;
import com.sundeinfo.sypglass.model.AdminExample;
import com.sundeinfo.sypglass.model.AdminFolder;
import com.sundeinfo.sypglass.model.AdminFolderExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service("AdminService")
public class AdminService extends AbstractService<AdminService,AdminDto> implements AdminDomainService<AdminService,AdminDto> {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminFolderMapper adminFolderMapper;

    @Autowired
    private UserService userService;

    public boolean isSysAdmin(long id){
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(id);
        List<Admin> admins = adminMapper.selectByExample(example);
        if (admins == null || admins.size() == 0){
            return false;
        }else{
            if (admins.get(0).getLevel() == 1){
                return true;
            }
            return false;
        }
    }

    public boolean hasSameFolderAdmin(AdminFolderDto adminFolderDto){
        AdminFolderExample example = new AdminFolderExample();
        AdminFolderExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(adminFolderDto.getUserId());
        criteria.andFolderidEqualTo(adminFolderDto.getFolderId());
        return adminFolderMapper.selectByExample(example).size() > 0;
    }

    public List<AdminDto> findAll() throws InstantiationException, IllegalAccessException {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<Admin> admins = adminMapper.selectByExample(example);
        return ConvertUtils.convertList(admins,AdminDto.class);
    }

    public AdminDto findByUser(long id) throws InstantiationException, IllegalAccessException {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(id);
        List<Admin> admins = adminMapper.selectByExample(example);
        if (admins == null || admins.size() == 0){
            return null;
        }else {
            AdminDto adminDto = ConvertUtils.convert(admins.get(0), AdminDto.class);
            if (adminDto.getLevel() == 2) {
                adminDto.setAdminFolders(findFolderByAdminId(adminDto.getId()));
            }
            return adminDto;
        }
    }

    public List<AdminFolderDto> findAdminByFolders(List<Long> ids) throws InstantiationException, IllegalAccessException {
        AdminFolderExample example = new AdminFolderExample();
        AdminFolderExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidIn(ids);
        criteria.andStatusEqualTo((byte)AdminFolderStatus.NORMAL.getCode());
        List<AdminFolder> adminFolders = adminFolderMapper.selectByExample(example);
        return ConvertUtils.convertList(adminFolders,AdminFolderDto.class);
    }

    public List<AdminFolderDto> findFolderByAdminId(long id) throws InstantiationException, IllegalAccessException {
        AdminFolderExample example = new AdminFolderExample();
        AdminFolderExample.Criteria criteria = example.createCriteria();
        criteria.andAdminidEqualTo(id);
        criteria.andStatusEqualTo((byte)AdminFolderStatus.NORMAL.getCode());
        List<AdminFolder> adminFolders = adminFolderMapper.selectByExample(example);
        return ConvertUtils.convertList(adminFolders,AdminFolderDto.class);
    }

    public List<AdminDto> findByDept(long deptid) throws InstantiationException, IllegalAccessException {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andDeptidEqualTo(deptid);
        List<Admin> admins = adminMapper.selectByExample(example);
        return  ConvertUtils.convertList(admins,AdminDto.class);
    }

    public List<UserDto> findByFolder(long folderid) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        AdminFolderExample example = new AdminFolderExample();
        AdminFolderExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidEqualTo(folderid);
        criteria.andStatusEqualTo((byte)AdminFolderStatus.NORMAL.getCode());
        List<AdminFolder> adminFolders = adminFolderMapper.selectByExample(example);
        List<Long> userIds = ReflectUtils.reflectDataList(adminFolders,"userid",Long.class);
        return  userService.findByIds(userIds);
    }

    public AdminDto findById(long id) throws InstantiationException, IllegalAccessException {
        Admin admin = adminMapper.selectByPrimaryKey(id);
        return ConvertUtils.convert(admin,AdminDto.class);
    }

    public List<AdminDto> findByIds(List<Long> ids) throws InstantiationException, IllegalAccessException {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        List<Admin> admins = adminMapper.selectByExample(example);
        return  ConvertUtils.convertList(admins,AdminDto.class);
    }

    public int save(AdminDto adminDto) throws InstantiationException, IllegalAccessException {
        Admin admin = ConvertUtils.convert(adminDto,Admin.class);
        return adminMapper.insert(admin);
    }

    @Transactional
    public int saveSysAdmin(long userId,long deptId){
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        List<Admin> admins = adminMapper.selectByExample(example);
        if (admins == null || admins.size() == 0){
            Admin admin = new Admin();
            admin.setUserid(userId);
            admin.setDeptid(deptId);
            admin.setLevel((byte)1);
            admin.setStatus((byte)1);
            return adminMapper.insert(admin);
        }else{
            Admin admin = admins.get(0);
            admin.setLevel((byte)1);
            return adminMapper.updateByPrimaryKeySelective(admin);
        }
    }

    @Transactional
    public int save(AdminFolderDto adminFolderDto) throws InstantiationException, IllegalAccessException {
        AdminExample example = new AdminExample();
        AdminExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(adminFolderDto.getUserId());
        List<Admin> admins = adminMapper.selectByExample(example);
        if (admins == null || admins.size() == 0){
            Admin admin = new Admin();
            admin.setUserid(adminFolderDto.getUserId());
            admin.setDeptid(adminFolderDto.getDeptId());
            admin.setLevel((byte)2);
            admin.setStatus((byte)1);
            int i = adminMapper.insert(admin);
            if (i > 0){
                AdminFolder adminFolder = ConvertUtils.convert(adminFolderDto,AdminFolder.class);
                adminFolder.setStatus((byte)AdminFolderStatus.NORMAL.getCode());
                adminFolder.setAdminid(admin.getId());
                return adminFolderMapper.insert(adminFolder);
            }else{
                return i;
            }
        }else{
            Admin admin = admins.get(0);
            AdminFolder adminFolder = ConvertUtils.convert(adminFolderDto,AdminFolder.class);
            adminFolder.setAdminid(admin.getId());
            return adminFolderMapper.insert(adminFolder);
        }
    }

    public int modAdminFolderStatusByFolderId(long id,AdminFolderStatus status){
        AdminFolderExample example = new AdminFolderExample();
        AdminFolderExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidEqualTo(id);

        AdminFolder adminFolder = new AdminFolder();
        adminFolder.setStatus((byte)status.getCode());

        return adminFolderMapper.updateByExampleSelective(adminFolder,example);
    }

    public int modAdminFolderStatusByFolderIds(List<Long> ids,AdminFolderStatus status){
        AdminFolderExample example = new AdminFolderExample();
        AdminFolderExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidIn(ids);

        AdminFolder adminFolder = new AdminFolder();
        adminFolder.setStatus((byte)status.getCode());

        return adminFolderMapper.updateByExampleSelective(adminFolder,example);
    }

    @Transactional
    public int deleteSystemAdminByAdminId(long id) {
        return adminMapper.deleteByPrimaryKey(id);
    }

    @Transactional
    public int deleteFolderAdminByAdminId(long id) {
        AdminFolderExample example = new AdminFolderExample();
        AdminFolderExample.Criteria criteria = example.createCriteria();
        criteria.andAdminidEqualTo(id);

        List<AdminFolder> folders = adminFolderMapper.selectByExample(example);
        if (folders!= null && folders.size()>0) {
            int i = adminFolderMapper.deleteByExample(example);
            if (i > 0){
                return adminMapper.deleteByPrimaryKey(id);
            }else{
                return 0;
            }
        }else {
            return adminMapper.deleteByPrimaryKey(id);
        }
    }

    @Transactional
    public int deleteAdminFolderById(long id) {
        return adminFolderMapper.deleteByPrimaryKey(id);
    }

    public int deleteAdminFolderByFolderId(long id){
        AdminFolderExample example = new AdminFolderExample();
        AdminFolderExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidEqualTo(id);

        return adminFolderMapper.deleteByExample(example);
    }

    public int deleteAdminFolderByFolderIds(List<Long> ids){
        AdminFolderExample example = new AdminFolderExample();
        AdminFolderExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidIn(ids);

        return adminFolderMapper.deleteByExample(example);
    }

}
