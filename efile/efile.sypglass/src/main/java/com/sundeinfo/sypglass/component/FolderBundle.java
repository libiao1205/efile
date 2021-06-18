package com.sundeinfo.sypglass.component;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.security.model.AnyUser;
import com.sundeinfo.security.utility.AuthenticationGetter;
import com.sundeinfo.sypglass.define.FolderStatus;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.service.FileService;
import com.sundeinfo.sypglass.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FolderBundle  extends AbstractComponent<FolderBundle> {

    @Autowired
    private FolderService folderService;

    @Autowired
    private AuthenticationGetter authenticationGetter;


    public long bundle(long id,String path) throws IllegalAccessException, InstantiationException {
        AnyUser anyUser = authenticationGetter.getAnyUser();


        long parentId = id;
        FolderDto parentFolderDto = folderService.findById(parentId);

        String[] fileNameArray = path.split("\\\\");
        for (String folderNodeName:fileNameArray) {
            FolderDto folderDto = folderService.findByParentIdAndName(parentId,folderNodeName);
            if (folderDto == null){
                //新建文件夹
                FolderDto newFolder = folderService.createNew()
                        .setParentId(parentId)
                        .setName(folderNodeName)
                        .setStatus((byte)FolderStatus.NORMAL.code())
                        .setCreateUserId(1)
                        .setCreateUserName(anyUser.getUser().getName())
                        .setCreateDate(new Date())
                        .setIsDelete((byte) 0)
                        .setParentPath(parentFolderDto.getFullPath())
                        .setParentPathName(parentFolderDto.getFullName())
                        .build();
                int i = folderService.save(newFolder);
                if (i == 0){
                    return 0;
                }else{
                    parentId = newFolder.getId();
                    parentFolderDto = ConvertUtils.convert(newFolder,FolderDto.class);
                }
            }else{
                parentId = folderDto.getId();
                parentFolderDto = folderDto;
            }
        }

        return parentId;
    }


}
