package com.sundeinfo.sypglass.model;

import com.sundeinfo.security.permission.PermissionAware;
import com.sundeinfo.sypglass.dto.FileAccessoryDto;
import com.sundeinfo.sypglass.dto.FileMetadataDto;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Data
public class Document implements PermissionAware,Serializable,Comparable<Document>{

    private Long id;

    private Long parentId;

    private Integer sortNo;

    private String kind;

    private Integer docType;

    private Byte status;

    private String name;

    private String suffix;

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

    private String parentPath;

    private String parentPathName;

    private String fullPath;

    private String fullName;

    private String comment;

    private String searchData;

    private Boolean disabled;

    private String rankAuthor;

    private String rankSource;

    private Serializable resource;

    private List<FileAccessoryDto> fileAccessoryDto;

    private List<FileMetadataDto> fileMetadataDto;

    public Document() {
    }

    public Document(Long id, Long parentId, Integer sortNo, String kind, Integer docType,Byte status,
                    String name, String suffix, String size,
                    Long createUserId, String createUserName, Date createDate,
                    Long lastUpdateUserId, String lastUpdateUserName, Date lastUpdateDate,
                    Byte isDelete, Long deleteUserId, String deleteUserName, Date deleteDate,
                    String parentPath, String parentPathName, String fullPath, String fullName,
                    String comment,String searchData,Boolean disabled,String rankAuthor,String rankSource) {
        this.id = id;
        this.parentId = parentId;
        this.sortNo = sortNo;
        this.kind = kind;
        this.docType = docType;
        this.status = status;
        this.name = name;
        this.suffix = suffix;
        this.size = size;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.createDate = createDate;
        this.lastUpdateUserId = lastUpdateUserId;
        this.lastUpdateUserName = lastUpdateUserName;
        this.lastUpdateDate = lastUpdateDate;
        this.isDelete = isDelete;
        this.deleteUserId = deleteUserId;
        this.deleteUserName = deleteUserName;
        this.deleteDate = deleteDate;
        this.parentPath = parentPath;
        this.parentPathName = parentPathName;
        this.fullPath = fullPath;
        this.fullName = fullName;
        this.comment = comment;
        this.searchData = searchData;
        this.disabled = disabled;
        this.rankAuthor = rankAuthor;
        this.rankSource = rankSource;
    }

    public Document(Long id, Long parentId, Integer sortNo, String kind, Integer docType,Byte status,
                    String name, String suffix, String size,
                    Long createUserId, String createUserName, Date createDate,
                    Long lastUpdateUserId, String lastUpdateUserName, Date lastUpdateDate,
                    Byte isDelete, Long deleteUserId, String deleteUserName, Date deleteDate,
                    String parentPath, String parentPathName, String fullPath, String fullName,
                    String comment,String searchData) {
        this.id = id;
        this.parentId = parentId;
        this.sortNo = sortNo;
        this.kind = kind;
        this.docType = docType;
        this.status = status;
        this.name = name;
        this.suffix = suffix;
        this.size = size;
        this.createUserId = createUserId;
        this.createUserName = createUserName;
        this.createDate = createDate;
        this.lastUpdateUserId = lastUpdateUserId;
        this.lastUpdateUserName = lastUpdateUserName;
        this.lastUpdateDate = lastUpdateDate;
        this.isDelete = isDelete;
        this.deleteUserId = deleteUserId;
        this.deleteUserName = deleteUserName;
        this.deleteDate = deleteDate;
        this.parentPath = parentPath;
        this.parentPathName = parentPathName;
        this.fullPath = fullPath;
        this.fullName = fullName;
        this.comment = comment;
        this.searchData = searchData;
    }

    private Map<String,Boolean> permissionMap;

    @Override
    public void setPermissionMap(Map<String,Boolean> map){
        permissionMap = map;
    }


    public String getKey(){
        return kind + "-" + id;
    }

    @Override
    public int compareTo(Document document) {

        if (document.getSortNo() == null || document.getSortNo() == 0){
            if (this.sortNo == null || this.sortNo == 0){
                return 0;
            }else{
                return -1;
            }
        }else {
            if (this.sortNo == null || this.sortNo == 0){
                return 1;
            }else{
                return (this.sortNo - document.getSortNo());
            }
        }
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
}
