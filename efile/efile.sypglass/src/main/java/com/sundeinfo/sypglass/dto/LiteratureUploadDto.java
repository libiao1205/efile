package com.sundeinfo.sypglass.dto;

import com.sundeinfo.core.domain.Entity;
import com.sundeinfo.security.permission.PermissionAware;
import com.sundeinfo.sypglass.model.FileMetadata;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@EqualsAndHashCode(callSuper = false)
public class LiteratureUploadDto extends Entity implements Serializable {

    /**
     * 上传的文件列表（修改时存放新增文件）
     */
    private MultipartFile[] files;

    /**
     * 上传的文件列表（修改时存放覆盖文件）
     */
    private MultipartFile[] coverFiles;

    /**
     * 修改时需要删除的文件id
     */
    private List<Long> deleteList;

    /**
     * 通知用户列表
     */
    private String sender;

    /**
     * 数据库
     */
    private Long database;

    /**
     * 注释
     */
    private String  comment;


    /**
     * 相关元数据
     */
    private List<FileMetadataDto> metadata;

    private String metaStr;

    private String deleteStr;
}
