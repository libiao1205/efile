package com.sundeinfo.sypglass.component;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.permission.PermissionHelper;
import com.sundeinfo.security.domain.RolePermissionDomain;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.FileDto;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.dto.RolePermissionDto;
import com.sundeinfo.sypglass.dto.SearchResultDto;
import com.sundeinfo.sypglass.model.Document;
import com.sundeinfo.sypglass.model.RolePermission;
import com.sundeinfo.sypglass.service.FileService;
import com.sundeinfo.sypglass.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component("PermissionAdapter")
public class PermissionAdapter extends AbstractComponent<PermissionAdapter> {

    @Autowired
    private FolderService folderService;

    @Autowired
    private FileService fileService;

    @Autowired
    private AuthenticationGetter authenticationGetter;

    public void setFolderPermissionMap(FolderDto self, List<FolderDto> parentFolderDtoList){
        List<FolderDto> accumulatorParentFolderDtoList = new ArrayList<>(parentFolderDtoList);
        accumulatorParentFolderDtoList.add(0,self);
        if (self != null){
            self.setPermissionMap(createPermissionMap(SypGlass.RESOURCE_TYPE_FOLDER,self.getId(),parentFolderDtoList));
            for (FolderDto child:self.getChildren()) {
                setFolderPermissionMap(child,accumulatorParentFolderDtoList);
            }
        }
    }

    public void setDocumentPermissionMap(List<Document> list, List<FolderDto> parentFolderDtos){
        for (Document document:list) {
            if (SypGlass.RESOURCE_TYPE_FOLDER.equals(document.getKind())){
                Map<String,Boolean> permissionMap = createPermissionMap(SypGlass.RESOURCE_TYPE_FOLDER,document.getId(),parentFolderDtos);
                document.setPermissionMap(permissionMap);
                if (document.getResource() != null){
                    ((FolderDto)document.getResource()).setPermissionMap(permissionMap);
                }
            }else{
                Map<String,Boolean> permissionMap = createPermissionMap(SypGlass.RESOURCE_TYPE_FILE,document.getId(),parentFolderDtos);
                document.setPermissionMap(permissionMap);
                if (document.getResource() != null){
                    ((FileDto)document.getResource()).setPermissionMap(permissionMap);
                }
            }
        }
    }

    public void setFilePermissionMap(FileDto fileDto, List<FolderDto> parentFolderDtos){
        Map<String,Boolean> permissionMap = createPermissionMap(SypGlass.RESOURCE_TYPE_FILE,fileDto.getId(),parentFolderDtos);
        fileDto.setPermissionMap(permissionMap);
    }

    public void setDocumentPermissionMap(SearchResultDto searchResultDto, List<FolderDto> parentFolderDtos){
        Map<String,Boolean> permissionMap = createPermissionMap(SypGlass.RESOURCE_TYPE_FILE,searchResultDto.getId(),parentFolderDtos);
        searchResultDto.setPermissionMap(permissionMap);
        if (searchResultDto.getResource() != null){
            ((FileDto)searchResultDto.getResource()).setPermissionMap(permissionMap);
        }
    }

    public Map<String,Boolean> getPermissionMap(String key,List<RolePermissionDto> rolePermissionDtoList) throws IllegalAccessException, InstantiationException {
        String keySplit[] = key.split("-");
        return getPermissionMap(keySplit[0],Long.parseLong(keySplit[1]),rolePermissionDtoList);
    }

    public Map<String,Boolean> getPermissionMap(String code,long id,List<RolePermissionDto> rolePermissionDtoList) throws IllegalAccessException, InstantiationException {
        List<FolderDto> parentFolderDtos;
        if(SypGlass.RESOURCE_TYPE_FOLDER.equals(code)){
            parentFolderDtos = folderService.findParentListByFolderId(id);
        }else if(SypGlass.RESOURCE_TYPE_FILE.equals(code)) {
            FileDto fileDto = fileService.findById(id);
            parentFolderDtos = folderService.findParentListByFolderId(fileDto.getFolderId());
        }else{
            parentFolderDtos = new ArrayList<>();
        }
        return createPermissionMap(rolePermissionDtoList,code,id,parentFolderDtos);
    }

    private Map<String,Boolean> createPermissionMap(String code, long id, List<FolderDto> parentFolderDtos){
        List rolePermissionDomains = authenticationGetter.getAnyUser().getRolePermissions();
        return createPermissionMap(rolePermissionDomains,code, id, parentFolderDtos);
    }

    private Map<String,Boolean> createPermissionMap(List<RolePermissionDto> rolePermissionDtoList,String code, long id, List<FolderDto> parentFolderDtos){
        if (rolePermissionDtoList == null || rolePermissionDtoList.size() == 0) {
            return PermissionHelper.getDefaultPermissionMap();
        }
        //找资源本身的权限设定
        List<RolePermissionDomain> selfPermissions =
                rolePermissionDtoList.stream()
                        .filter(rp -> code.equals(rp.getCode()) && id == rp.getResourceId()).collect(Collectors.toList());
        if (selfPermissions != null && selfPermissions.size() > 0){
            if (selfPermissions.size() == 1){
                return PermissionHelper.getPermissionMapByFunction(selfPermissions.get(0).getValue());
            }else{
                for (RolePermissionDomain rolePermissionDomain : selfPermissions){
                    if (rolePermissionDomain.getType() == 2){
                        return PermissionHelper.getPermissionMapByFunction(rolePermissionDomain.getValue());
                    }
                }
                return PermissionHelper.getPermissionMapByFunction(selfPermissions.get(selfPermissions.size()-1).getValue());
            }

        }
        //没有找到自身权限时，继承本资源的上级目录的权限
        for (FolderDto folderDto:parentFolderDtos){
            List<RolePermissionDomain> parentPermissions =
                    rolePermissionDtoList.stream()
                            .filter(rp -> SypGlass.RESOURCE_TYPE_FOLDER.equals(rp.getCode()) && folderDto.getId() == rp.getResourceId()).collect(Collectors.toList());;
            if (parentPermissions != null && parentPermissions.size() > 0){
                if (parentPermissions.size() == 1){
                    return PermissionHelper.getPermissionMapByFunction(parentPermissions.get(0).getValue());
                }else{
                    for (RolePermissionDomain rolePermissionDomain : parentPermissions){
                        if (rolePermissionDomain.getType() == 2){
                            return PermissionHelper.getPermissionMapByFunction(rolePermissionDomain.getValue());
                        }
                    }
                    return PermissionHelper.getPermissionMapByFunction(parentPermissions.get(parentPermissions.size()-1).getValue());
                }

            }
        }
        //如果是文件夹类型的资源，那么在没有找到可继承权限时，还需寻找下级资源的权限。如果有，保证自身可视。
        if(SypGlass.RESOURCE_TYPE_FOLDER.equals(code)){
            for (RolePermissionDto rolePermissionDto:rolePermissionDtoList) {
                boolean flag = rolePermissionDto.getFullPath().contains("/" + id + "/");
                if (flag && rolePermissionDto.getValue() > 0){ //下级资源有权限，并且权限起码是可视的情况下，保证自身可视。
                    return PermissionHelper.getVisiblePermissionMap();
                }
            }
        }
        if (SypGlass.RESOURCE_TYPE_FOLDER.equals(code)){
            logger.warn(String.format("没有找到文件夹(ID:%s)的权限！",id));
        }else if(SypGlass.RESOURCE_TYPE_FILE.equals(code)) {
            logger.warn(String.format("没有找到文件(ID:%s)的权限！",id));
        }else{
            logger.error("资源类型没有找到! [" + code + "]");
        }
        return PermissionHelper.getDefaultPermissionMap();
    }
}