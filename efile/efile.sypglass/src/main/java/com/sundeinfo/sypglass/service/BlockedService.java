package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.dto.FolderIpDto;
import com.sundeinfo.sypglass.task.BlockTask;
import com.sundeinfo.sypglass.task.BlockTaskStarter;
import com.sundeinfo.sypglass.dto.FileDto;
import com.sundeinfo.sypglass.mapper.*;
import com.sundeinfo.sypglass.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("BlockedService")
public class BlockedService extends AbstractService<BlockedService,Document> {

    @Autowired
    private DocumentMapper documentMapper;

    @Autowired
    private FolderIpService folderIpService;

    @Autowired
    private FileService fileService;

    @Autowired
    private FolderMapper folderMapper;

    @Autowired
    private FolderIpMapper folderIpMapper;

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileHistoryMapper fileHistoryMapper;

    @Autowired
    private BlockTaskStarter blockTaskStarter;

    @Autowired
    private BlockTask blockTask;

    @Transactional
    public int blockFolder(long id) throws IllegalAccessException, InstantiationException, NoSuchFieldException {

        //处理文件夹
        List<Folder> folders = documentMapper.selectFolderAndChildrenByIdWithDelete(id);
        List<FolderDto> folderDtos = ConvertUtils.convertList(folders,FolderDto.class);

        //处理文件夹白名单
        List<Long> folderIds = ReflectUtils.reflectDataList(folders,"id",Long.class);
        List<FolderIp> folderIps = folderIds.size() > 0 ? folderIpService.findIpInfoByFolderIds(folderIds) : new ArrayList<>();
        List<FolderIpDto> folderIpDtos = ConvertUtils.convertList(folderIps,FolderIpDto.class);

        //处理文件
        List<FileDto> files = folderIds.size() > 0 ? fileService.findByFolders(folderIds) : new ArrayList<>();

        //处理文件履历
        List<Long> fileIds = ReflectUtils.reflectDataList(files,"id",Long.class);
        List<FileHistory> histories = fileIds.size() > 0 ? fileService.findHistoryByIds(fileIds) : new ArrayList<>();

        blockTask.setResource(folderDtos,folderIpDtos,files,histories);
        blockTaskStarter.execute(blockTask);

        if (folderIds.size() > 0){
            FolderExample folderExample = new FolderExample();
            FolderExample.Criteria folderCriteria = folderExample.createCriteria();
            folderCriteria.andIdIn(folderIds);
            folderMapper.deleteByExample(folderExample);

            FolderIpExample folderIpExample = new FolderIpExample();
            FolderIpExample.Criteria folderIpCriteria = folderIpExample.createCriteria();
            folderIpCriteria.andFolderidIn(folderIds);
            folderIpMapper.deleteByExample(folderIpExample);
        }

        if (fileIds.size() > 0){
            FileExample fileExample = new FileExample();
            FileExample.Criteria fileCriteria = fileExample.createCriteria();
            fileCriteria.andIdIn(fileIds);
            fileMapper.deleteByExample(fileExample);

            FileHistoryExample fileHistoryExample = new FileHistoryExample();
            FileHistoryExample.Criteria fileHistoryCriteria = fileHistoryExample.createCriteria();
            fileHistoryCriteria.andFileidIn(fileIds);
            fileHistoryMapper.deleteByExample(fileHistoryExample);
        }



        return 1;
    }

    @Transactional
    public int blockFile(long id) throws IllegalAccessException, InstantiationException, NoSuchFieldException {
        List<Long> ids = new ArrayList<>();
        ids.add(id);
        return blockFiles(ids);
    }

    @Transactional
    public int blockFiles(List<Long> ids) throws IllegalAccessException, InstantiationException, NoSuchFieldException {

        List<FileDto> files = fileService.findByIds(ids);
        List<FileHistory> histories = fileService.findHistoryByIds(ids);

        blockTask.setResource(null,null,files,histories);
        blockTaskStarter.execute(blockTask);

        FileExample fileExample = new FileExample();
        FileExample.Criteria fileCriteria = fileExample.createCriteria();
        fileCriteria.andIdIn(ids);
        fileMapper.deleteByExample(fileExample);

        FileHistoryExample fileHistoryExample = new FileHistoryExample();
        FileHistoryExample.Criteria fileHistoryCriteria = fileHistoryExample.createCriteria();
        fileHistoryCriteria.andFileidIn(ids);
        fileHistoryMapper.deleteByExample(fileHistoryExample);

        return 1;
    }

}
