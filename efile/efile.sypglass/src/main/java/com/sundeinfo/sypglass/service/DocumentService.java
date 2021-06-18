package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.security.domain.AdminDomain;
import com.sundeinfo.sypglass.dto.AdminDto;
import com.sundeinfo.sypglass.dto.AdminFolderDto;
import com.sundeinfo.sypglass.mapper.DocumentMapper;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.model.Admin;
import com.sundeinfo.sypglass.model.Document;
import com.sundeinfo.sypglass.model.Folder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service("DocumentService")
public class DocumentService extends AbstractService<DocumentService,Document> {

    @Autowired
    private DocumentMapper documentMapper;

    public List<Document> findDocumentByFolderId(long id){
        return documentMapper.selectDocumentByParentid(id);
    }

    public List<Document> findDocumentByFolderIdAndUserid(long id,long userId,String sortOrder){
        return documentMapper.selectDocumentByParentidAndUserid(id,userId,sortOrder);
    }

    public Document findDocumentByid(long id,String kind){
        return documentMapper.selectDocumentByid(id,kind);
    }


    public List<Document> findTrashDocument(){
        return documentMapper.selectTrashDocument();
    }

    public List<Document> findTrashDocumentBySearchKey(String word){
        return documentMapper.selectTrashDocumentBySearchKey(word);
    }

    public List<Document> findTrashDocumentByFolder(AdminDomain adminDomain){
        List<AdminFolderDto> adminFolderDtoList = ((AdminDto)adminDomain).getAdminFolders();

        if (adminFolderDtoList == null || adminFolderDtoList.size() == 0) return new ArrayList<>();
        List<String> folderList = new ArrayList<>();
        for (AdminFolderDto folderDto:adminFolderDtoList){
            folderList.add("/" + folderDto.getFolderName());
        }
        return documentMapper.selectTrashDocumentByFolderName(adminDomain.getUserId(),folderList);
    }

    public List<Document> findTrashDocumentByFolderAndSearchKey(AdminDomain adminDomain,String word){
        List<AdminFolderDto> adminFolderDtoList = ((AdminDto)adminDomain).getAdminFolders();

        if (adminFolderDtoList == null || adminFolderDtoList.size() == 0) return new ArrayList<>();
        List<String> folderList = new ArrayList<>();
        for (AdminFolderDto folderDto:adminFolderDtoList){
            folderList.add("/" + folderDto.getFolderName());
        }
        return documentMapper.selectTrashDocumentByFolderNameAndSearchKey(word,adminDomain.getUserId(),folderList);
    }

    public List<Document> findTrashDocumentByUserId(long userId){
        return documentMapper.selectTrashDocumentByUserId(userId);
    }

    public List<Document> findTrashDocumentByUserIdAndSearchKey(long userId,String word){
        return documentMapper.selectTrashDocumentByUserIdAndSearchKey(userId,word);
    }

    public List<Document> findDeletedDocument(){
        return documentMapper.selectDeletedDocument();
    }
}
