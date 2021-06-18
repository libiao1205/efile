package com.sundeinfo.sypglass.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sundeinfo.core.domain.Entity;
import com.sundeinfo.security.permission.PermissionAware;
import com.sundeinfo.sypglass.define.FileDocType;
import com.sundeinfo.sypglass.define.FileStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.validator.constraints.NotEmpty;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class FileDto extends Entity implements PermissionAware,Serializable {

    private Integer docType;

    private Long folderId;

    private FolderDto folder;

    private String name;

    private String suffix;

    private Byte status;

    private String statusName;

    private String size;

    private Long createUserId;

    private String createUserName;

    private Date createDate;

    private Long lastUpdateUserId;

    private String lastUpdateUserName;

    private Date lastUpdateDate;

    private Byte isDelete;

    private Long deleteUserId;

    private String deleteUserName;

    private Date deleteDate;

    private String comment;

    private String saveId;

    private String saveMD5;

    private Long saveLength;

    private String parentPath;

    private String parentPathName;

    private String fullPath;

    private String fullName;

    private Long oaFileId;

    private String oaFileInfo;

    private Map<String,Boolean> permissionMap;

    private List<FileAccessoryDto> fileAccessoryDto;

    private List<FileMetadataDto> fileMetadataDto;

    private List<FileHistoryDto> fileHistoryDto;

    private String rankAuthor;

    private String rankSource;

    @JsonIgnore
    public String getSaveId() {
        return saveId;
    }

    @JsonIgnore
    public String getSaveMD5() {
        return saveMD5;
    }

    @JsonIgnore
    public Long getSaveLength() {
        return saveLength;
    }

    @Override
    public void setPermissionMap(Map<String,Boolean> map){
        permissionMap = map;
    }

    public String getFullPath() {
        return parentPath + "/" + getId();
    }

    public String getFullName() {
        return parentPathName + "/" + getName() + "." + getSuffix();
    }

    /********************************以下新文件Builder****************************************/

    public static class Builder {

        private Integer docType;
        private Long folderId;
        private String name;
        private String suffix;
        private Byte status;
        private String size;
        private Long createUserId;
        private String createUserName;
        private Date createDate;
        private Long lastUpdateUserId;
        private String lastUpdateUserName;
        private Date lastUpdateDate;
        private Byte isDelete;
        private Long deleteUserId;
        private String deleteUserName;
        private Date deleteDate;
        private String comment;
        private String saveId;
        private String saveMD5;
        private Long saveLength;
        private String parentPath;
        private String parentPathName;
        private Long oaFileId;
        private String oaFileInfo;
        private String rankAuthor;
        private String rankSource;


        public Builder(){
            this.docType = FileDocType.UNKNOWN.getStatus();
            this.folderId = 0L;
            this.name = "";
            this.suffix = "";
            this.status = 0;
            this.size = "";
            this.createUserId = 0L;
            this.createUserName = "";
            this.createDate = null;
            this.lastUpdateUserId = 0L;
            this.lastUpdateUserName = "";
            this.lastUpdateDate = null;
            this.isDelete = 0;
            this.deleteUserId = 0L;
            this.deleteUserName = "";
            this.deleteDate = null;
            this.comment = "";
            this.saveId = "";
            this.saveMD5 = "";
            this.saveLength = 0L;
            this.parentPath = "";
            this.parentPathName = "";
            this.oaFileId = 0L;
            this.oaFileInfo = "";
            this.rankAuthor = "";
            this.rankSource="";
        }
        public Builder setRankAuthor(String rankAuthor) {
            this.rankAuthor = rankAuthor;
            return this;
        }
        public Builder setRankSource(String rankSource) {
            this.rankSource = rankSource;
            return this;
        }
        public Builder setDocType(FileDocType fileDocType) {
            this.docType = fileDocType.getStatus();
            return this;
        }

        public Builder setFolderId(Long folderId) {
            this.folderId = folderId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        public Builder setStatus(FileStatus status) {
            this.status = (byte)status.code();
            return this;
        }

        public Builder setSize(String size) {
            this.size = size;
            return this;
        }

        public Builder setCreateUserId(Long createUserId) {
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

        public Builder setLastUpdateUserId(Long lastUpdateUserId) {
            this.lastUpdateUserId = lastUpdateUserId;
            return this;
        }

        public Builder setLastUpdateUserName(String lastUpdateUserName) {
            this.lastUpdateUserName = lastUpdateUserName;
            return this;
        }

        public Builder setLastUpdateDate(Date lastUpdateDate) {
            this.lastUpdateDate = lastUpdateDate;
            return this;
        }

        public Builder setIsDelete(byte isDelete) {
            this.isDelete = isDelete;
            return this;
        }

        public Builder setDeleteUserId(Long deleteUserId) {
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

        public Builder setComment(String comment) {
            this.comment = comment;
            return this;
        }

        public Builder setSaveId(String saveId) {
            this.saveId = saveId;
            return this;
        }

        public Builder setSaveMD5(String saveMD5) {
            this.saveMD5 = saveMD5;
            return this;
        }

        public Builder setSaveLength(Long saveLength) {
            this.saveLength = saveLength;
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

        public Builder setOaFileId(long oaFileId) {
            this.oaFileId = oaFileId;
            return this;
        }

        public Builder setOaFileInfo(String oaFileInfo) {
            this.oaFileInfo = oaFileInfo;
            return this;
        }

        public FileDto build(){
            FileDto fileDto = new FileDto();
            fileDto.setDocType(this.docType);
            fileDto.setFolderId(this.folderId);
            fileDto.setName(this.name);
            fileDto.setSuffix(this.suffix);
            fileDto.setStatus(this.status);
            fileDto.setSize(this.size);
            fileDto.setCreateUserId(this.createUserId);
            fileDto.setCreateUserName(this.createUserName);
            fileDto.setCreateDate(this.createDate);
            fileDto.setLastUpdateUserId(this.lastUpdateUserId);
            fileDto.setLastUpdateUserName(this.lastUpdateUserName);
            fileDto.setLastUpdateDate(this.lastUpdateDate);
            fileDto.setIsDelete(this.isDelete);
            fileDto.setDeleteUserId(this.deleteUserId);
            fileDto.setDeleteUserName(this.deleteUserName);
            fileDto.setDeleteDate(this.deleteDate);
            fileDto.setComment(this.comment);
            fileDto.setSaveId(this.saveId);
            fileDto.setSaveMD5(this.saveMD5);
            fileDto.setSaveLength(this.saveLength);
            fileDto.setParentPath(this.parentPath);
            fileDto.setParentPathName(this.parentPathName);
            fileDto.setOaFileId(this.oaFileId);
            fileDto.setOaFileInfo(this.oaFileInfo);
            fileDto.setRankAuthor(this.rankAuthor);
            fileDto.setRankSource(this.rankSource);
            return fileDto;
        }
    }
}
