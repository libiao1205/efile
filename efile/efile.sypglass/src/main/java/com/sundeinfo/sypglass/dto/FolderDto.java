package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import com.sundeinfo.security.permission.PermissionAware;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.beans.factory.annotation.Value;

import java.io.Serializable;
import java.util.*;

@Data
@EqualsAndHashCode(callSuper = false)
public class FolderDto extends Entity implements PermissionAware,Serializable,Comparable<FolderDto> {

    private long parentId;

    private int sortNo;

    private String name;

    private byte status;

    private long createUserId;

    private String createUserName;

    private Date createDate;

    private byte isDelete;

    private long deleteUserId;

    private String deleteUserName;

    private Date deleteDate;

    private String parentPath;

    private String parentPathName;

    private long oaworkflowid;

    private String oaworkflowinfo;

    private String fullPath;

    private String fullName;

    private boolean disabled;

    private int fileSize;
    
    private int maxSize;

    private List<FolderDto> children;

    private List<FileDto> files;

    private List<UserDto> admins;

    private Map<String,Boolean> permissionMap;

    private FolderIpDto folderIpDto;

    public FolderDto() {
        children = new ArrayList<>();
        files = new ArrayList<>();
    }

    @Override
    public void setPermissionMap(Map<String,Boolean> map){
        permissionMap = map;
    }

    public void addChild(FolderDto folder){
        this.children.add(folder);
    }

    public void addFile(FileDto file){
        this.files.add(file);
    }

    public String getFullPath() {
        return parentPath + "/" + getId();
    }

    public String getFullName() {
        return parentPathName + "/" + getName();
    }

    public void sortChildren(List<UserFolderSortJsonDto> sortJsonDtos){
        for (FolderDto current : this.children) {
            Optional<UserFolderSortJsonDto> firstSortDto = sortJsonDtos.stream().filter(sortfolder -> current.getId() == sortfolder.getId()).findFirst();
            firstSortDto.ifPresent(userFolderSortJsonDto -> current.setSortNo(userFolderSortJsonDto.getSortNo()));
        }
        Collections.sort(this.children);
    }

    public boolean isMyParent(FolderDto folderDto){
        String[] parentList = this.parentPath.split("/");
        return Arrays.binarySearch(parentList,folderDto.getId()+"") > 0;
    }

    public boolean isMyParent(long id){
        String[] parentList = this.parentPath.split("/");
        return Arrays.binarySearch(parentList,id+"") > 0;
    }

    public boolean isYourChild(FolderDto folderDto){
        String[] parentList = folderDto.parentPath.split("/");
        return Arrays.binarySearch(parentList,this.getId()+"") > 0;
    }

    public void confirmFileSize(List<FolderDto> parents,int defaultSize){
        if (this.getFileSize() == 0){
            for (FolderDto parent:parents) {
                if (parent.getFileSize() > 0){
                    this.setFileSize(parent.getFileSize());
                }
            }
            if (this.getFileSize() == 0){
                this.setFileSize(defaultSize);
            }
        }
    }

    @Override
    public int compareTo(FolderDto o) {
        return (this.sortNo - o.getSortNo());
//        // 按name排序
//        if (this.name.compareTo(o.getName()) > 0) {
//            return 1;
//        }
//        if (this.name.compareTo(o.getName()) < 0) {
//            return -1;
//        }
//
//        return 0;
    }

    /********************************以下新文件Builder****************************************/

    public static class Builder {

        private long parentId;
        private String name;
        private byte status;
        private long createUserId;
        private String createUserName;
        private Date createDate;
        private byte isDelete;
        private long deleteUserId;
        private String deleteUserName;
        private Date deleteDate;
        private String parentPath;
        private String parentPathName;
        private long oaworkflowid;
        private String oaworkflowinfo;



        public Builder(){
            this.parentId = 0;
            this.name = "";
            this.status = 0;
            this.createUserId = 0;
            this.createUserName = "";
            this.createDate = null;
            this.isDelete = 0;
            this.deleteUserId = 0;
            this.deleteUserName = "";
            this.deleteDate = null;
            this.parentPath = "";
            this.parentPathName = "";
            this.oaworkflowid = 0;
            this.oaworkflowinfo = "";
        }

        public Builder setParentId(long parentId) {
            this.parentId = parentId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setStatus(byte status) {
            this.status = status;
            return this;
        }

        public Builder setCreateUserId(long createUserId) {
            this.createUserId = createUserId;
            return this;
        }

        public Builder setCreateUserName(String createUserName) {
            this.createUserName = createUserName;
            return this;
        }

        public Builder setCreateDate(Date createDate) {
            this.createDate = createDate;
            return this;
        }

        public Builder setIsDelete(byte isDelete) {
            this.isDelete = isDelete;
            return this;
        }

        public Builder setDeleteUserId(long deleteUserId) {
            this.deleteUserId = deleteUserId;
            return this;
        }

        public Builder setDeleteUserName(String deleteUserName) {
            this.deleteUserName = deleteUserName;
            return this;
        }

        public Builder setDeleteDate(Date deleteDate) {
            this.deleteDate = deleteDate;
            return this;
        }

        public Builder setParentPath(String parentPath) {
            this.parentPath = parentPath;
            return this;
        }

        public Builder setParentPathName(String parentPathName) {
            this.parentPathName = parentPathName;
            return this;
        }

        public Builder setOaworkflowid(Long oaworkflowid) {
            this.oaworkflowid = oaworkflowid;
            return this;
        }

        public Builder setOaworkflowinfo(String oaworkflowinfo) {
            this.oaworkflowinfo = oaworkflowinfo;
            return this;
        }

        public FolderDto build(){
            FolderDto folderDto = new FolderDto();
            folderDto.setParentId(this.parentId);
            folderDto.setName(this.name);
            folderDto.setStatus(this.status);
            folderDto.setCreateUserId(this.createUserId);
            folderDto.setCreateUserName(this.createUserName);
            folderDto.setCreateDate(this.createDate);
            folderDto.setIsDelete(this.isDelete);
            folderDto.setDeleteUserId(this.deleteUserId);
            folderDto.setDeleteUserName(this.deleteUserName);
            folderDto.setDeleteDate(this.deleteDate);
            folderDto.setParentPath(this.parentPath);
            folderDto.setParentPathName(this.parentPathName);
            folderDto.setOaworkflowid(this.oaworkflowid);
            folderDto.setOaworkflowinfo(this.oaworkflowinfo);
            return folderDto;
        }
    }
}
