package com.sundeinfo.sypglass.task;

import com.sundeinfo.core.job.Task;
import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.sypglass.dto.FileDto;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.dto.FolderIpDto;
import com.sundeinfo.sypglass.mapper.BlockedFileHistoryMapper;
import com.sundeinfo.sypglass.mapper.BlockedFileMapper;
import com.sundeinfo.sypglass.mapper.BlockedFolderIpMapper;
import com.sundeinfo.sypglass.mapper.BlockedFolderMapper;
import com.sundeinfo.sypglass.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("BlockTask")
public class BlockTask extends AbstractComponent<BlockTask> implements Task {

    @Autowired
    private BlockedFolderMapper blockedFolderMapper;

    @Autowired
    private BlockedFolderIpMapper blockedFolderIpMapper;

    @Autowired
    private BlockedFileMapper blockedFileMapper;

    @Autowired
    private BlockedFileHistoryMapper blockedFileHistoryMapper;

    private List<FolderDto> folders;

    private List<FolderIpDto> folderIps;

    private List<FileDto> files;

    private List<FileHistory> histories;

    public void setResource(List<FolderDto> folders, List<FolderIpDto> folderIps, List<FileDto> files, List<FileHistory> histories){
        this.folders = folders;
        this.folderIps = folderIps;
        this.files = files;
        this.histories = histories;
    }

    @Override
    public void execute() {
        try{
            if (folders != null && folders.size() > 0){
                List<BlockedFolder> blockedFolders = ConvertUtils.convertList(folders,BlockedFolder.class);
                for (BlockedFolder blocked:blockedFolders) {
                    blockedFolderMapper.insert(blocked);
                }
            }

            if (folderIps != null && folderIps.size() > 0){
                List<BlockedFolderIp> blockedFolderIps = ConvertUtils.convertList(folderIps,BlockedFolderIp.class);
                for (BlockedFolderIp blocked:blockedFolderIps) {
                    blockedFolderIpMapper.insert(blocked);
                }
            }

            if (files != null && files.size() > 0){
                List<BlockedFile> blockedFiles = ConvertUtils.convertList(files,BlockedFile.class);
                for (BlockedFile blocked:blockedFiles) {
                    blockedFileMapper.insert(blocked);
                }
            }

            if (histories != null && histories.size() > 0){
                List<BlockedFileHistory> blockedFileHistories = ConvertUtils.convertList(histories,BlockedFileHistory.class);
                for (BlockedFileHistory blocked:blockedFileHistories) {
                    blockedFileHistoryMapper.insert(blocked);
                }
            }

        } catch (IllegalAccessException | InstantiationException e) {
            logger.error("删除数据时转储文件和文件夹信息出错");
            e.printStackTrace();
        }

    }

//    private void blockFile(FileDto file) throws InstantiationException, IllegalAccessException {
//
//        BlockedFile blockedFile = ConvertUtils.convert(file,BlockedFile.class);
//        blockedFileMapper.insert(blockedFile);
//
//        List<BlockedFileHistory> blockedFileHistories = ConvertUtils.convertList(file.getFileHistoryDto(),BlockedFileHistory.class);
//        for (BlockedFileHistory blocked:blockedFileHistories) {
//            blockedFileHistoryMapper.insert(blocked);
//        }
//    }
//
//    private void blockFiles() throws InstantiationException, IllegalAccessException {
//
//        List<BlockedFile> blockedFiles = ConvertUtils.convertList(files,BlockedFile.class);
//        for (BlockedFile blocked:blockedFiles) {
//            blockedFileMapper.insert(blocked);
//        }
//
//        List<BlockedFileHistory> blockedFileHistories = ConvertUtils.convertList(histories,BlockedFileHistory.class);
//        for (BlockedFileHistory blocked:blockedFileHistories) {
//            blockedFileHistoryMapper.insert(blocked);
//        }
//    }
}