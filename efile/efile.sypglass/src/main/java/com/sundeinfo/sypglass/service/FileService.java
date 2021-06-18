package com.sundeinfo.sypglass.service;


import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.permission.FunctionType;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.sypglass.define.FileDocType;
import com.sundeinfo.sypglass.define.FileStatus;
import com.sundeinfo.sypglass.define.SypGlass;
import com.sundeinfo.sypglass.dto.*;
import com.sundeinfo.sypglass.mapper.*;
import com.sundeinfo.sypglass.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.StringUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("FileService")
public class FileService extends AbstractService<FileService,FileDto>{

    @Autowired
    private FileMapper fileMapper;

    @Autowired
    private FileAccessoryMapper fileAccessoryMapper;

    @Autowired
    private FileMetadataMapper fileMetadataMapper;

    @Autowired
    private MetadataMapper metadataMapper;

    @Autowired
    private FileHistoryMapper fileHistoryMapper;

    @Autowired
    private ActionHistoryService actionHistoryService;

    @Autowired
    private FileAccessoryHistoryMapper fileAccessoryHistoryMapper;

    @Autowired
    private FileMetadataHistoryMapper fileMetadataHistoryMapper;


    public FileDto.Builder createNew(){
        return new FileDto.Builder();
    }

    public FilePreviewDto createPreviewDto(FileDto fileDto,int seq) throws InstantiationException, IllegalAccessException,FileNotFoundException {
        for (FileHistoryDto history:fileDto.getFileHistoryDto()){
            if (seq == history.getSeq()){
                return ConvertUtils.convert(history,FilePreviewDto.class);
            }
        }
        throw new FileNotFoundException(String.format("文件没有找到：id(%s);seq(%s)",fileDto.getId(),seq));
    }

    public FilePreviewDto createPreviewDto(FileAccessoryDto fileAccessoryDto) throws InstantiationException, IllegalAccessException,FileNotFoundException {
        return ConvertUtils.convert(fileAccessoryDto,FilePreviewDto.class);
    }

    public List<FileDto> findByFolderWithoutDelete(Long folderId) throws InstantiationException, IllegalAccessException {
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidEqualTo(folderId);
        criteria.andStatusEqualTo((byte)1);
        List<File> files = fileMapper.selectByExample(example);
        return ConvertUtils.convertList(files,FileDto.class);
    }

    public List<FileDto> findByFolderWithHistoryWithoutDelete(Long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidEqualTo(id);
        criteria.andStatusEqualTo((byte)1);
        List<File> files = fileMapper.selectByExample(example);

        List<Long> ids = ReflectUtils.reflectDataList(files,"id",Long.class);
        List<FileDto> result = ConvertUtils.convertList(files,FileDto.class);
        if (ids != null && ids.size() > 0){
            List<FileHistory> fileHistories = findHistoryByIds(ids);
            for (FileDto fileDto:result) {
                fileDto.setFileHistoryDto(getHistories(fileDto.getId(),fileHistories));
            }
        }
        return result;
    }

    private List<FileHistoryDto> getHistories(Long id,List<FileHistory> fileHistories) throws InstantiationException, IllegalAccessException {
        List<FileHistory> histories = new ArrayList<>();
        for (FileHistory history:fileHistories) {
            if (history.getFileid() == id){
                histories.add(history);
            }
        }
        return  ConvertUtils.convertList(histories,FileHistoryDto.class);
    }

    public List<FileDto> findByFolders(List<Long> ids) throws InstantiationException, IllegalAccessException {
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidIn(ids);
        List<File> files = fileMapper.selectByExample(example);
        return ConvertUtils.convertList(files,FileDto.class);
    }

    public FileDto findById(Long id) throws InstantiationException, IllegalAccessException {
        File file = fileMapper.selectByPrimaryKey(id);
        return ConvertUtils.convert(file,FileDto.class);
    }

    public List<FileDto> findByIds(List<Long> ids) throws InstantiationException, IllegalAccessException {
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        List<File> files = fileMapper.selectByExample(example);
        return ConvertUtils.convertList(files,FileDto.class);
    }

    public List<FileDto> findBySearchKey(String name) throws InstantiationException, IllegalAccessException {
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        criteria.andNameLike("%" + name + "%");
        criteria.andStatusNotEqualTo((byte)FileStatus.DELETE.code());
        List<File> files = fileMapper.selectByExample(example);
        return ConvertUtils.convertForPage(files,FileDto.class);
    }

    public List<FileDto> findByOaFileId(Long id) throws InstantiationException, IllegalAccessException {
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andOafileidEqualTo(id);
        List<File> files = fileMapper.selectByExample(example);

        List<FileDto> fileDtos = new ArrayList<>();
        for (File file:files) {
            FileDto fileDto = ConvertUtils.convert(file,FileDto.class);
            List<FileHistoryDto> historys = findFileHistory(fileDto.getId());
            fileDto.setFileHistoryDto(historys);
            fileDtos.add(fileDto);
        }
        return fileDtos;
    }

    public FileDto findWithAdditionalById(Long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        File file = fileMapper.selectByPrimaryKey(id);
        if (file == null){
            return null;
        }
        FileDto fileDto = ConvertUtils.convert(file,FileDto.class);
        //如果是文献类型的就加载附件信息和元数据信息
        if (FileDocType.LITERATURE.getStatus() == fileDto.getDocType()){
            fileDto.setFileMetadataDto(findFileMetadataByFileId(id));
            fileDto.setFileAccessoryDto(findFileAccessoryByFileId(id));
        }
        //文献履历信息
        fileDto.setFileHistoryDto(findFileHistory(id));
        return fileDto;
    }

    public List<FileMetadataDto> findFileMetadataByFileId(Long id) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        FileMetadataExample example = new FileMetadataExample();
        FileMetadataExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(id);
        List<FileMetadata> fileMetadata = fileMetadataMapper.selectByExample(example);

        List<Long> ids = ReflectUtils.reflectDataList(fileMetadata,"metadataid",Long.class);
        MetadataExample metadataExample = new MetadataExample();
        MetadataExample.Criteria criteria1 = metadataExample.createCriteria();
        criteria1.andIdIn(ids);
        if(ids.size() == 0){
            return null;
        }
        List<Metadata> metadataList = metadataMapper.selectByExample(metadataExample);

        List<FileMetadataDto> fileMetadataDtoList =  ConvertUtils.convertList(fileMetadata,FileMetadataDto.class);
        for(FileMetadataDto fileMetadataDto : fileMetadataDtoList){
            for(Metadata metadata:metadataList){
                if (metadata.getId().equals(fileMetadataDto.getMetadataId())){
                    fileMetadataDto.setDataType(metadata.getDatatype());
                    fileMetadataDto.setCode(metadata.getCode());
                    fileMetadataDto.setName(metadata.getName());
                    fileMetadataDto.setSeq(metadata.getSeq());
                    fileMetadataDto.setDocType(metadata.getDoctype());
                }
            }
        }
        return fileMetadataDtoList;
    }
    //查询文献元数据履历
    public List<FileMetadataHistoryDto> findFileMetadataHistoriesByFileId(Long id, Integer seq) throws InstantiationException, IllegalAccessException, NoSuchFieldException {
        FileMetadataHistoryExample example = new FileMetadataHistoryExample();
        FileMetadataHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(id);
        criteria.andSeqEqualTo(seq);
        List<FileMetadataHistory> fileMetadata = fileMetadataHistoryMapper.selectByExample(example);

        List<Long> ids = ReflectUtils.reflectDataList(fileMetadata,"metadataid",Long.class);
        if(ids.size() == 0){
            return null;
        }
        MetadataExample metadataExample = new MetadataExample();
        MetadataExample.Criteria criteria1 = metadataExample.createCriteria();
        criteria1.andIdIn(ids);
        List<Metadata> metadataList = metadataMapper.selectByExample(metadataExample);

        List<FileMetadataHistoryDto> fileMetadataHistoryDtoList =  ConvertUtils.convertList(fileMetadata,FileMetadataHistoryDto.class);
        for(FileMetadataHistoryDto fileMetadataHistoryDto : fileMetadataHistoryDtoList){
            for(Metadata metadata:metadataList){
                if (metadata.getId().equals(fileMetadataHistoryDto.getMetadataId())){
                    fileMetadataHistoryDto.setDataType(metadata.getDatatype());
                    fileMetadataHistoryDto.setCode(metadata.getCode());
                    fileMetadataHistoryDto.setName(metadata.getName());
                    fileMetadataHistoryDto.setSeq(metadata.getSeq());
                    fileMetadataHistoryDto.setDocType(metadata.getDoctype());
                }
            }
        }
        return fileMetadataHistoryDtoList;
    }
    //查询文献附件履历
    public List<FileAccessoryHistoryDto> findFileAccessoryHistoriesByFileId(Long id, Integer seq) throws InstantiationException, IllegalAccessException{
        FileAccessoryHistoryExample example = new FileAccessoryHistoryExample();
        FileAccessoryHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(id);
        criteria.andSeqEqualTo(seq);
        List<FileAccessoryHistory> accessoriesHistory = fileAccessoryHistoryMapper.selectByExample(example);

        return ConvertUtils.convertList(accessoriesHistory,FileAccessoryHistoryDto.class);
    }

    //查询文献附件履历
    public FileAccessoryHistoryDto findFileAccessoryHistoriesById(Long id) throws InstantiationException, IllegalAccessException{
        FileAccessoryHistory accessoriesHistory = fileAccessoryHistoryMapper.selectByPrimaryKey(id);
        return ConvertUtils.convert(accessoriesHistory,FileAccessoryHistoryDto.class);
    }

    public List<FileAccessoryDto> findFileAccessoryByFileId(Long id) throws InstantiationException, IllegalAccessException{
        FileAccessoryExample example = new FileAccessoryExample();
        FileAccessoryExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(id);
        criteria.andIsdeleteEqualTo((byte)0);
        List<FileAccessory> accessories = fileAccessoryMapper.selectByExample(example);

        return ConvertUtils.convertList(accessories,FileAccessoryDto.class);
    }

    public List<FileHistoryDto> findFileHistory(Long id) throws InstantiationException, IllegalAccessException{
        FileHistoryExample example = new FileHistoryExample();
        FileHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(id);
        example.setOrderByClause("SEQ DESC");
        List<FileHistory> histories = fileHistoryMapper.selectByExample(example);

        return ConvertUtils.convertList(histories,FileHistoryDto.class);
    }

    public List<FileHistory> findHistoryByIds(List<Long> ids){
        FileHistoryExample example = new FileHistoryExample();
        FileHistoryExample.Criteria criteria = example.createCriteria();
        criteria.andFileidIn(ids);
        return fileHistoryMapper.selectByExample(example);
    }

    public List<ActionHistoryDto> findActionHistory(Long id) throws InstantiationException, IllegalAccessException {
        List<ActionHistoryDto> actionHistoryDtos = new ArrayList<>();
        List<ActionHistory> histories = actionHistoryService.findByKindAndId(SypGlass.RESOURCE_TYPE_FILE,id);
        if (histories == null || histories.size() == 0) return actionHistoryDtos;
        for (ActionHistory history:histories) {
            ActionHistoryDto actionHistoryDto = ConvertUtils.convert(history,ActionHistoryDto.class);
            actionHistoryDto.setActionName(FunctionType.valueOf(history.getAction().toUpperCase()).comment());
            actionHistoryDtos.add(actionHistoryDto);
        }
        return actionHistoryDtos;
    }



    /********************************以下数据库读写****************************************/

    public int addActionHistory(Long id, FunctionType functionType, Long userId, String username){
        actionHistoryService.saveFileAction(id,functionType,userId,username);
        return 1;
    }

    @Transactional
    public int save(FileDto fileDto) throws InstantiationException, IllegalAccessException {
        File file = ConvertUtils.convert(fileDto,File.class);
        int i = fileMapper.insert(file);
        if (i == 0){
            return 0;
        }
        fileDto.setId(file.getId());
        FileHistory history = ConvertUtils.convert(file,FileHistory.class);

        history.setId(null);
        history.setFileid(file.getId());
        history.setSeq(1);
        history.setUserid(fileDto.getLastUpdateUserId());
        history.setUsername(fileDto.getLastUpdateUserName());
        history.setUpdatedate(fileDto.getLastUpdateDate());

        fileHistoryMapper.insert(history);
        return addActionHistory(file.getId(),FunctionType.NEW,file.getCreateuserid(),file.getCreateusername());
    }

    @Transactional
    public int saveLiteratureHistory(List<FileAccessoryDto> fileAccessoryDtoList,Long fileId, List<FileMetadataDto> fileMetadataDtos, Long userId, String userName,Integer seq) throws InstantiationException, IllegalAccessException {
        int result = 0;
        for(FileAccessoryDto fileAccessoryDto:fileAccessoryDtoList) {
            FileAccessoryHistory fileAccessoryHistory = ConvertUtils.convert(fileAccessoryDto, FileAccessoryHistory.class);
            fileAccessoryHistory.setSeq(seq);
            fileAccessoryHistory.setId(null);
            result = fileAccessoryHistoryMapper.insertSelective(fileAccessoryHistory);
            if (result == 0) {
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return 0;
            }
        }
        Date date = new Date();
        for(FileMetadataDto fileMetadataDto:fileMetadataDtos){
            FileMetadataHistory fileMetadataHistory = ConvertUtils.convert(fileMetadataDto,FileMetadataHistory.class);
            fileMetadataHistory.setSeq(seq);
            fileMetadataHistory.setFileid(fileId);
            fileMetadataHistory.setCreateuserid(userId);
            fileMetadataHistory.setCreatedate(date);
            fileMetadataHistory.setCreateusername(userName);
            result = fileMetadataHistoryMapper.insert(fileMetadataHistory);
            if(result == 0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return 0;
            }
        }
        return result;
    }

    @Transactional
    public int saveFileMetadataList(Long fileId,List<FileMetadataDto> fileMetadataDtos,Long userId, String username) throws InstantiationException, IllegalAccessException {
        FileMetadataExample example = new FileMetadataExample();
        FileMetadataExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(fileId);
        fileMetadataMapper.deleteByExample(example);
        int result = 0;
        Date date = new Date();
        for(FileMetadataDto fileMetadataDto:fileMetadataDtos){
            FileMetadata fileMetadata = ConvertUtils.convert(fileMetadataDto,FileMetadata.class);
            fileMetadata.setFileid(fileId);
            fileMetadata.setCreateuserid(userId);
            fileMetadata.setCreatedate(date);
            fileMetadata.setCreateusername(username);
            result = fileMetadataMapper.insert(fileMetadata);

            if(result == 0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return 0;
            }
        }
        return result;
    }
    @Transactional
    public int updateFileMetadataName(Long fileId,String name) throws InstantiationException, IllegalAccessException {
        FileMetadataExample example = new FileMetadataExample();
        FileMetadataExample.Criteria criteria = example.createCriteria();
        criteria.andFileidEqualTo(fileId);
        List<FileMetadata> fileMetadatas = fileMetadataMapper.selectByExample(example);
        List<Long> ids = new ArrayList<>();
        //获取改文件的所有元数据ID
        fileMetadatas.forEach(metDto->{
            ids.add(metDto.getMetadataid());
        });
        int result = 0;
        if(ids.size() == 0){
            return result;
        }
        //查询出name列的元数据ID
        MetadataExample exampleMetadata = new MetadataExample();
        MetadataExample.Criteria criteria1Metadata = exampleMetadata.createCriteria();
        criteria1Metadata.andIdIn(ids);
        criteria1Metadata.andCodeEqualTo("name");
        List<Metadata> metadatas = metadataMapper.selectByExample(exampleMetadata);

        //根据元数据ID和文件ID修改元数据name
        if(metadatas.size() > 0){
            Metadata metadata = metadatas.get(0);
            criteria.andMetadataidEqualTo(metadata.getId());
            FileMetadata record = new FileMetadata();
            record.setValue(name);
            result = fileMetadataMapper.updateByExampleSelective(record,example);
        }
        return result;
    }

    @Transactional
    public int saveFileAccessoryList(List<FileAccessoryDto> fileAccessoryDtos) throws InstantiationException, IllegalAccessException {
        int result = 0;
        for(FileAccessoryDto fileAccessoryDto:fileAccessoryDtos){
            FileAccessory fileAccessory = ConvertUtils.convert(fileAccessoryDto,FileAccessory.class);
            result = fileAccessoryMapper.insert(fileAccessory);
            if(result == 0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return 0;
            }
        }
        return result;
    }

    @Transactional
    public int updateFileAccessoryList(List<FileAccessoryDto> fileAccessoryDtos) throws InstantiationException, IllegalAccessException {
        int result = 0;
        for(FileAccessoryDto fileAccessoryDto:fileAccessoryDtos){
            FileAccessory fileAccessory = ConvertUtils.convert(fileAccessoryDto,FileAccessory.class);
            FileAccessoryExample example = new FileAccessoryExample();
            FileAccessoryExample.Criteria criteria = example.createCriteria();
            criteria.andIdEqualTo(fileAccessoryDto.getId());
            result = fileAccessoryMapper.updateByExample(fileAccessory,example);
            if(result == 0){
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return 0;
            }
        }
        return result;
    }

    @Transactional
    public int deleteFileAccessory(Long id,Long userId,String userName){
        FileAccessoryDto fileAccessoryDto = new FileAccessoryDto();
        fileAccessoryDto.setId(id);
        int result = 0;
        FileAccessoryExample example = new FileAccessoryExample();
        FileAccessoryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(fileAccessoryDto.getId());
        FileAccessory record = new FileAccessory();
        record.setId(fileAccessoryDto.getId());
        record.setIsdelete((byte)1);
        record.setDeletedate(new Date());
        record.setDeleteuserid(userId);
        record.setDeleteusername(userName);
        result = fileAccessoryMapper.updateByExampleSelective(record,example);
        if(result == 0){
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
        return result;
    }

    @Transactional
    public int update(FileDto fileDto) throws InstantiationException, IllegalAccessException {
        File file = ConvertUtils.convert(fileDto,File.class);
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(fileDto.getId());
        fileMapper.updateByExampleSelective(file,example);

        FileHistory history = ConvertUtils.convert(file,FileHistory.class);
        history.setId(null);
        history.setFileid(file.getId());
        history.setSeq(fileDto.getFileHistoryDto().size() + 1);
        history.setUserid(fileDto.getLastUpdateUserId());
        history.setUsername(fileDto.getLastUpdateUserName());
        history.setUpdatedate(fileDto.getLastUpdateDate());

        fileHistoryMapper.insert(history);
        return addActionHistory(fileDto.getId(),FunctionType.UPDATE,fileDto.getLastUpdateUserId(),fileDto.getLastUpdateUserName());
    }

    @Transactional
    public int modFileNameAndFolder(FileDto fileDto) throws IllegalAccessException, InstantiationException {
        File file = new File();
        file.setName(fileDto.getName());
        file.setFolderid(fileDto.getFolderId());
        file.setParentpath(fileDto.getParentPath());
        file.setParentpathname(fileDto.getParentPathName());
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(fileDto.getId());
        fileMapper.updateByExampleSelective(file,example);
        if(fileDto.getDocType() == 20){
            updateFileMetadataName(fileDto.getId(),file.getName());
        }
        return addActionHistory(fileDto.getId(),FunctionType.MODIFY,fileDto.getLastUpdateUserId(),fileDto.getLastUpdateUserName());
    }

    @Transactional
    public int modFileStatusToDelete(Long id,UserDto userDto){
        File file = new File();
        file.setStatus((byte)FileStatus.DELETE.code());
        file.setDeleteuserid(userDto.getId());
        file.setDeleteusername(userDto.getName());
        file.setDeletedate(new Date());
        file.setLastupdateuserid(userDto.getId());
        file.setLastupdateusername(userDto.getName());
        file.setLastupdatedate(new Date());
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return fileMapper.updateByExampleSelective(file,example);
    }

    @Transactional
    public int modFilesStatusToDelete(List<Long> ids,UserDto userDto){
        File file = new File();
        file.setStatus((byte)FileStatus.DELETE.code());
        file.setDeleteuserid(userDto.getId());
        file.setDeleteusername(userDto.getName());
        file.setDeletedate(new Date());
        file.setLastupdateuserid(userDto.getId());
        file.setLastupdateusername(userDto.getName());
        file.setLastupdatedate(new Date());
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return fileMapper.updateByExampleSelective(file,example);
    }

    @Transactional
    public int modFileStatusToNormal(Long id){
        File file = new File();
        file.setStatus((byte)FileStatus.NORMAL.code());
        file.setDeleteuserid(null);
        file.setDeleteusername(null);
        file.setDeletedate(null);
        file.setLastupdateuserid(null);
        file.setLastupdateusername(null);
        file.setLastupdatedate(null);

        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return fileMapper.updateByExampleSelective(file,example);
    }

    @Transactional
    public int modFilesStatusToNormal(List<Long> ids){
        File file = new File();
        file.setStatus((byte)FileStatus.NORMAL.code());
        file.setDeleteuserid(null);
        file.setDeleteusername(null);
        file.setDeletedate(null);
        file.setLastupdateuserid(null);
        file.setLastupdateusername(null);
        file.setLastupdatedate(null);

        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        return fileMapper.updateByExampleSelective(file,example);
    }

    @Transactional
    public int delete(Long id, UserDto userDto){
        File file = new File();
        file.setIsdelete((byte)1);
        file.setDeleteuserid(userDto.getId());
        file.setDeleteusername(userDto.getName());
        file.setDeletedate(new Date());
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        return fileMapper.updateByExampleSelective(file,example);
    }

    @Transactional
    public int delete(List<Long> ids, UserDto userDto){
        int result;
        for (Long id:ids) {
            result = delete(id,userDto);
            if (result <1){
                return 0;
            }
        }
        return 1;
    }

    @Transactional
    public int rollback(Long id){
        File file = fileMapper.selectByPrimaryKey(id);
        file.setIsdelete((byte)0);
        file.setStatus((byte)FileStatus.NORMAL.code());
        file.setDeleteuserid(null);
        file.setDeleteusername(null);
        file.setDeletedate(null);
        return fileMapper.updateByPrimaryKey(file);
    }

    @Transactional
    public int rollback(List<Long> ids){
        FileExample example = new FileExample();
        FileExample.Criteria criteria = example.createCriteria();
        criteria.andIdIn(ids);
        List<File> files = fileMapper.selectByExample(example);

        for (File file:files) {
            file.setIsdelete((byte)0);
            file.setStatus((byte)FileStatus.NORMAL.code());
            file.setDeleteuserid(null);
            file.setDeleteusername(null);
            file.setDeletedate(null);
            fileMapper.updateByPrimaryKey(file);
        }
        return 1;
    }

}