package com.sundeinfo.sypglass.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.permission.FunctionType;
import com.sundeinfo.core.permission.PermissionHelper;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.define.*;
import com.sundeinfo.sypglass.dto.*;
import com.sundeinfo.sypglass.mapper.*;
import com.sundeinfo.sypglass.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service("FolderService")
public class FolderService extends AbstractService<FolderService,FolderDto>{

    @Autowired
    private DocumentMapper documentMapper;

    @Autowired
    private FolderMapper folderMapper;

    @Autowired
    private UserFolderSortMapper userFolderSortMapper;

    @Autowired
    private UserFolderDisabledMapper userFolderDisabledMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private AdminService adminService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @Autowired
    private ActionHistoryService actionHistoryService;

    public FolderDto.Builder createNew(){
        return new FolderDto.Builder();
    }

    public FolderDto findById(long id) throws InstantiationException, IllegalAccessException {
        Folder folder = folderMapper.selectByPrimaryKey(id);
        return ConvertUtils.convert(folder,FolderDto.class);
    }

    public List<FolderDto> findByIds(List<Long> ids) throws InstantiationException, IllegalAccessException {
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);

        List<Folder> folders = folderMapper.selectByExample(example);
        return ConvertUtils.convertList(folders,FolderDto.class);
    }

    public FolderDto findCompleteById(long id) throws InstantiationException, IllegalAccessException {
        Folder folder = folderMapper.selectByPrimaryKey(id);
        FolderDto folderDto = ConvertUtils.convert(folder,FolderDto.class);

        FolderExample folderExample = new FolderExample();
        FolderExample.Criteria folderCriteria = folderExample.createCriteria();
        folderCriteria.andParentidEqualTo(id);
        folderCriteria.andStatusEqualTo((byte)FolderStatus.NORMAL.code());
        List<Folder> childrenFolder = folderMapper.selectByExample(folderExample);
        folderDto.setChildren(ConvertUtils.convertList(childrenFolder,FolderDto.class));

        FileExample fileExample = new FileExample();
        FileExample.Criteria fileCriteria = fileExample.createCriteria();
        fileCriteria.andFolderidEqualTo(id);
        fileCriteria.andStatusEqualTo((byte)FolderStatus.NORMAL.code());
        List<File> childrenFile = fileMapper.selectByExample(fileExample);
        folderDto.setFiles(ConvertUtils.convertList(childrenFile,FileDto.class));

        return folderDto;
    }

    public List<FolderDto> findByParentId(long id) throws InstantiationException, IllegalAccessException {
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andParentidEqualTo(id);
        criteria.andStatusEqualTo((byte)FolderStatus.NORMAL.code());
        List<Folder> folders = folderMapper.selectByExample(example);

        return ConvertUtils.convertList(folders,FolderDto.class);
    }

    @Cacheable(value = "Parent" ,key = "#id")
    public List<FolderDto> findParentListByFolderId(long id) throws InstantiationException, IllegalAccessException {
        List<Folder> folders = documentMapper.selectFolderAndParentsById(id);
        return ConvertUtils.convertList(folders,FolderDto.class);
    }

    //@Cacheable(value = "Child" ,key = "#id")
    public List<FolderDto> findChildrenListByFolderId(long id) throws InstantiationException, IllegalAccessException {
        List<Folder> folders = documentMapper.selectFolderAndChildrenById(id);
        return ConvertUtils.convertList(folders,FolderDto.class);
    }

    public FolderDto findChildrenListByFolderId(long id,long userId) throws IllegalAccessException, InstantiationException,NoSuchFieldException{
        FolderDto self = findById(id);
        Map<Long,List<UserFolderSortJsonDto>> folderSortMap = new HashMap<>();
        Map<Long,UserFolderDisabled> map = findUserFolderDisabledByFolder(userId);

        self.setDisabled(false);
        List<FolderDto> childrenList = findByParentId(id);

        for (FolderDto nodeFolderDto:childrenList)  {
            //设定隐藏标志位
            if (map.containsKey(nodeFolderDto.getId()) && map.get(nodeFolderDto.getId()).getDisabled()){
                nodeFolderDto.setDisabled(true);
            }
            self.addChild(nodeFolderDto);
            if (userId > 0){
                if (folderSortMap.containsKey(self.getId())){
                    self.sortChildren(folderSortMap.get(self.getId()));
                }else{
                    UserFolderSort userFolderSort =  findUserFolderSortByParentAndUser(self.getId(),userId);
                    if (userFolderSort != null){
                        List<UserFolderSortJsonDto> sortDtos = JSONArray.parseArray(userFolderSort.getSortjson(), UserFolderSortJsonDto.class);
                        folderSortMap.put(self.getId(),sortDtos);
                        self.sortChildren(sortDtos);
                    }
                }
            }
        }
        return self;
    }

    public FolderDto findChildrenTreeByFolderId(long id) throws IllegalAccessException, InstantiationException,NoSuchFieldException{
        //TODO:层数太多搜索异常，这里先搜全表
        //List<Folder> folders = documentMapper.selectFolderAndChildrenById(id);
        List<Folder> folders = new ArrayList<>();
        if (id == 0 || id == 1){
            folders = documentMapper.selectFolderFromRoot();
        }else{
            folders = documentMapper.selectFolderAndChildrenById(id);
        }
        FolderDto rootFolderDto = null;
        for (Folder folder:folders) {
            if (rootFolderDto == null){
                rootFolderDto = ConvertUtils.convert(folder,FolderDto.class);
            }else{
                FolderDto nodeFolderDto = ConvertUtils.convert(folder,FolderDto.class);
                FolderDto parent = getParentNode(rootFolderDto,nodeFolderDto.getParentId());
                if (parent != null){
                    parent.addChild(nodeFolderDto);
                }
            }
        }
        return rootFolderDto;
    }

    private FolderDto getParentNode(FolderDto node,long parentId){
        if (node.getId() == parentId){return node;}
        for (FolderDto subFolderDto:node.getChildren()) {
            if(subFolderDto.getId() == parentId){
                return subFolderDto;
            }
        }
        for (FolderDto subFolderDto:node.getChildren()) {
            FolderDto parent = getParentNode(subFolderDto,parentId);
            if (parent != null){return parent;}
        }
        return null;
    }

    public UserFolderSort findUserFolderSortByParentAndUser(long parentId,long userId){
        UserFolderSortExample example = new UserFolderSortExample();
        UserFolderSortExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        criteria.andParentidEqualTo(parentId);

        List<UserFolderSort> userFolderSorts = userFolderSortMapper.selectByExample(example);
        if (userFolderSorts != null && userFolderSorts.size() > 0){
            return userFolderSorts.get(0);
        }
        return null;
    }

    public FolderDto findWorkflowFolderByParentAndWorkflow(long parentId,long workflowId) throws InstantiationException, IllegalAccessException {
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andParentidEqualTo(parentId);
        criteria.andOaworkflowidEqualTo(workflowId);
        criteria.andStatusEqualTo((byte)FolderStatus.NORMAL.code());
        List<Folder> folders = folderMapper.selectByExample(example);

        if (folders == null || folders.size() == 0){
            return null;
        }else{
            return ConvertUtils.convert(folders.get(0),FolderDto.class);
        }
    }

    public FolderDto findByParentIdAndName(long parentId,String name) throws InstantiationException, IllegalAccessException {
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andParentidEqualTo(parentId);
        criteria.andNameEqualTo(name);
        criteria.andStatusEqualTo((byte)FolderStatus.NORMAL.code());
        List<Folder> folders = folderMapper.selectByExample(example);

        if (folders == null || folders.size() == 0){
            return null;
        }else{
            return ConvertUtils.convert(folders.get(0),FolderDto.class);
        }
    }

    /********************************以下数据库读写****************************************/

    @Transactional
    public int save(FolderDto folderDto) throws InstantiationException, IllegalAccessException {
        Folder folder = ConvertUtils.convert(folderDto,Folder.class);
        folderMapper.insert(folder);
        folderDto.setId(folder.getId());
        actionHistoryService.saveFolderAction(folderDto.getId(),FunctionType.NEW,folderDto.getCreateUserId(),folderDto.getCreateUserName());
        return 1;
    }

    private List<RolePermissionDto> defaultPermission(FolderDto folderDto) throws IllegalAccessException, InstantiationException {
        List<RolePermissionDto> parentFolderPermissions = rolePermissionService.findByResource(SypGlass.RESOURCE_TYPE_FOLDER,folderDto.getParentId());
        return rolePermissionService.createNews(parentFolderPermissions,folderDto);
    }

//    @Transactional
//    @CacheEvict(value = "Parent",key = "#folderDto.id")
//    public int mod(FolderDto folderDto) throws InstantiationException, IllegalAccessException {
//        Folder folder = ConvertUtils.convert(folderDto,Folder.class);
//        return folderMapper.updateByPrimaryKey(folder);
//    }

    @Transactional
    @CacheEvict(value = "Parent",key = "#folderDto.id")
    public int modFolderNameAndParent(FolderDto folderDto) throws InstantiationException, IllegalAccessException {
        Folder folder = new Folder();
        folder.setName(folderDto.getName());
        folder.setParentid(folderDto.getParentId());
        folder.setParentpath(folderDto.getParentPath());
        folder.setParentpathname(folderDto.getParentPathName());
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(folderDto.getId());
        return folderMapper.updateByExampleSelective(folder,example);
    }

    @Transactional
    public int modFolderStatusToDelete(long id, UserDto userDto){
        //先删除相关的文件夹管理权限
        adminService.modAdminFolderStatusByFolderId(id,AdminFolderStatus.DELETE) ;

        Folder folder = new Folder();
        folder.setStatus((byte)FolderStatus.DELETE.code());
        folder.setDeleteuserid(userDto.getId());
        folder.setDeleteusername(userDto.getName());
        folder.setDeletedate(new Date());
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return folderMapper.updateByExampleSelective(folder,example);
    }

    @Transactional
    public int modFoldersStatusToDelete(List<Long> ids, UserDto userDto){
        //先删除相关的文件夹管理权限
        adminService.modAdminFolderStatusByFolderIds(ids,AdminFolderStatus.DELETE) ;

        Folder folder = new Folder();
        folder.setStatus((byte)FolderStatus.DELETE.code());
        folder.setDeleteuserid(userDto.getId());
        folder.setDeleteusername(userDto.getName());
        folder.setDeletedate(new Date());
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return folderMapper.updateByExampleSelective(folder,example);
    }

    private Map<Long,UserFolderDisabled> findUserFolderDisabledByFolder(long userId){
        if (userId == 0) return new HashMap<>();
        UserFolderDisabledExample example = new UserFolderDisabledExample();
        UserFolderDisabledExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        List<UserFolderDisabled> userFolderDisableds = userFolderDisabledMapper.selectByExample(example);

        if (userFolderDisableds == null || userFolderDisableds.size() == 0){
            return new HashMap<>();
        }else{
            Map<Long,UserFolderDisabled> map = new HashMap<>();
            for (UserFolderDisabled disabled:userFolderDisableds) {
                map.put(disabled.getFolderid(),disabled);
            }
            return map;
        }
    }

    private UserFolderDisabled findUserFolderDisabledByUserAndFolder(long userId,long folderId){
        UserFolderDisabledExample example = new UserFolderDisabledExample();
        UserFolderDisabledExample.Criteria criteria = example.createCriteria();
        criteria.andUseridEqualTo(userId);
        criteria.andFolderidEqualTo(folderId);
        List<UserFolderDisabled> userFolderDisableds = userFolderDisabledMapper.selectByExample(example);

        if (userFolderDisableds == null || userFolderDisableds.size() == 0){
            return null;
        }else{
            return userFolderDisableds.get(0);
        }
    }

    public int disabledFolder(long userId,long folderId) throws IllegalAccessException, InstantiationException {
        UserFolderDisabled userFolderDisabled = findUserFolderDisabledByUserAndFolder(userId,folderId);
        if (userFolderDisabled == null){
            UserFolderDisabled disabled = new UserFolderDisabled();
            disabled.setUserid(userId);
            disabled.setFolderid(folderId);
            disabled.setDisabled(true);
            return userFolderDisabledMapper.insert(disabled);
        }else{
            if (!userFolderDisabled.getDisabled()){
                userFolderDisabled.setDisabled(true);
                return  userFolderDisabledMapper.updateByPrimaryKey(userFolderDisabled);
            }
            return 1;
        }
    }

    public int displayFolder(long userId,long folderId) throws IllegalAccessException, InstantiationException {
        UserFolderDisabled userFolderDisabled = findUserFolderDisabledByUserAndFolder(userId,folderId);
        if (userFolderDisabled != null){
            if (userFolderDisabled.getDisabled()){
                userFolderDisabled.setDisabled(false);
                return  userFolderDisabledMapper.updateByPrimaryKey(userFolderDisabled);
            }
            return 1;
        }
        return 1;
    }

    public int sortFolder(Long id, UserDto userDto,SortType type) throws IllegalAccessException, InstantiationException {
        FolderDto folderDto = findById(id);
        UserFolderSort userFolderSort = findUserFolderSortByParentAndUser(folderDto.getParentId(),userDto.getId());

        if (userFolderSort == null){
            userFolderSort = new UserFolderSort();
            List<FolderDto> folderDtos = findByParentId(folderDto.getParentId());

            FolderDto selfFolder = folderDtos.stream().filter(folder -> id == folder.getId()).findFirst().get();
            int index = folderDtos.indexOf(selfFolder);
            if (type == SortType.UP){
                if (index > 0){
                    Collections.swap(folderDtos,index,index - 1);
                }
            }else if (type == SortType.DOWN){
                if (index < folderDtos.size() - 1){
                    Collections.swap(folderDtos,index,index + 1);
                }
            }
            for (int sortIndex = 0;sortIndex <= folderDtos.size() - 1;sortIndex++){
                folderDtos.get(sortIndex).setSortNo(sortIndex + 1);
            }

            List<UserFolderSortJsonDto> sortJsonDtos = ConvertUtils.convertList(folderDtos,UserFolderSortJsonDto.class);
            String jsons = JSON.toJSONString(sortJsonDtos);

            userFolderSort.setParentid(folderDto.getParentId());
            userFolderSort.setUserid(userDto.getId());
            userFolderSort.setSortjson(jsons);
            return userFolderSortMapper.insert(userFolderSort);

        }else{
            List<UserFolderSortJsonDto> oldSortDtos = JSONArray.parseArray(userFolderSort.getSortjson(), UserFolderSortJsonDto.class);
            //先依照历史排序方式再次排序
            List<FolderDto> folderDtos = findByParentId(folderDto.getParentId());
            for (FolderDto current : folderDtos) {
                Optional<UserFolderSortJsonDto> firstSortDto = oldSortDtos.stream().filter(sortfolder -> current.getId() == sortfolder.getId()).findFirst();
                firstSortDto.ifPresent(userFolderSortJsonDto -> current.setSortNo(userFolderSortJsonDto.getSortNo()));
            }
            Collections.sort(folderDtos);
            FolderDto selfFolder = folderDtos.stream().filter(folder -> id == folder.getId()).findFirst().get();
            int index = folderDtos.indexOf(selfFolder);
            if (type == SortType.UP){
                if (index > 0){
                    Collections.swap(folderDtos,index,index - 1);
                }
            }else if (type == SortType.DOWN){
                if (index < folderDtos.size() - 1){
                    Collections.swap(folderDtos,index,index + 1);
                }
            }
            for (int sortIndex = 0;sortIndex <= folderDtos.size() - 1;sortIndex++){
                folderDtos.get(sortIndex).setSortNo(sortIndex + 1);
            }
            List<UserFolderSortJsonDto> sortJsonDtos = ConvertUtils.convertList(folderDtos,UserFolderSortJsonDto.class);
            String jsons = JSON.toJSONString(sortJsonDtos);

            userFolderSort.setSortjson(jsons);
            return userFolderSortMapper.updateByPrimaryKey(userFolderSort);
        }
    }

    @Transactional
    public int modFolderStatusToNormal(long id){
        //先恢复相关的文件夹管理权限
        adminService.modAdminFolderStatusByFolderId(id,AdminFolderStatus.NORMAL) ;

        Folder folder = new Folder();
        folder.setStatus((byte)FileStatus.NORMAL.code());
        folder.setDeleteuserid(null);
        folder.setDeleteusername(null);
        folder.setDeletedate(null);

        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return folderMapper.updateByExampleSelective(folder,example);
    }

    @Transactional
    public int modFoldersStatusToNormal(List<Long> ids){
        //先恢复相关的文件夹管理权限
        adminService.modAdminFolderStatusByFolderIds(ids,AdminFolderStatus.NORMAL) ;

        Folder folder = new Folder();
        folder.setStatus((byte)FileStatus.NORMAL.code());
        folder.setDeleteuserid(null);
        folder.setDeleteusername(null);
        folder.setDeletedate(null);

        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return folderMapper.updateByExampleSelective(folder,example);
    }
    public int modFolderFileSize(long id,int size) throws IllegalAccessException, NoSuchFieldException, InstantiationException {
        Folder folder = new Folder();
        folder.setFilesize(size);
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return folderMapper.updateByExampleSelective(folder,example);
    }

    //修改父级权限时清除自己对父级的缓存
    @CacheEvict(value = "Parent", key = "#id")
    public void cacheEvict(Long id){
        logger.info("*************清除子级对父级的缓存*************");
    }

    @Transactional
    public int delete(long id, UserDto userDto){
        //先删除相关的文件夹管理权限
        adminService.deleteAdminFolderByFolderId(id);

        Folder folder = getDeleteStatusFolder(userDto);
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return folderMapper.updateByExampleSelective(folder,example);
    }

    @Transactional
    public int delete(List<Long> ids, UserDto userDto){
        //先删除相关的文件夹管理权限
        adminService.deleteAdminFolderByFolderIds(ids);

        Folder folder = getDeleteStatusFolder(userDto);
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return folderMapper.updateByExampleSelective(folder,example);
    }

    private Folder getDeleteStatusFolder(UserDto userDto){
        Folder folder = new Folder();
        folder.setIsdelete((byte)1);
        folder.setStatus((byte)FileStatus.DELETE.code());
        folder.setDeleteuserid(userDto.getId());
        folder.setDeleteusername(userDto.getName());
        folder.setDeletedate(new Date());
        return folder;
    }

    @Transactional
    public int rollback(long id){
        Folder folder = folderMapper.selectByPrimaryKey(id);
        folder.setIsdelete((byte)0);
        folder.setStatus((byte)FileStatus.NORMAL.code());
        folder.setDeleteuserid(null);
        folder.setDeleteusername(null);
        folder.setDeletedate(null);
        return folderMapper.updateByPrimaryKey(folder);
    }


    @Transactional
    public int rollback(List<Long> ids){
        FolderExample example = new FolderExample();
        FolderExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        List<Folder> folders = folderMapper.selectByExample(example);
        for (Folder folder : folders) {
            folder.setIsdelete((byte)0);
            folder.setStatus((byte)FileStatus.NORMAL.code());
            folder.setDeleteuserid(null);
            folder.setDeleteusername(null);
            folder.setDeletedate(null);
            folderMapper.updateByPrimaryKey(folder);
        }
        return 1;
    }
}
