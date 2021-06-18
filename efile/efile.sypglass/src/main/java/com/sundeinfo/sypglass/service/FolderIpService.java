package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.ReflectUtils;
import com.sundeinfo.sypglass.dto.FolderDto;
import com.sundeinfo.sypglass.dto.FolderIpDto;
import com.sundeinfo.sypglass.mapper.FolderIpMapper;
import com.sundeinfo.sypglass.model.FolderIp;
import com.sundeinfo.sypglass.model.FolderIpExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("FolderIpService")
public class FolderIpService extends AbstractService<FolderIpService,FolderIpDto> {

    @Autowired
    private FolderIpMapper folderIpMapper;

    @Autowired
    private FolderService folderService;

    public List<FolderDto> findWhiteIpFolders() throws NoSuchFieldException, IllegalAccessException, InstantiationException {
        FolderIpExample example = new FolderIpExample();
        FolderIpExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        List<FolderIp> ipInfoList = folderIpMapper.selectByExample(example);
        if (ipInfoList == null || ipInfoList.size() == 0){
            return new ArrayList<>();
        }else{
            List<Long> folderIds = ReflectUtils.reflectDataList(ipInfoList,"folderid",Long.class);
            return folderService.findByIds(folderIds);
        }
    }

    public FolderIpDto findIpInfoByFolderId(long id) throws InstantiationException, IllegalAccessException {
        FolderIpExample example = new FolderIpExample();
        FolderIpExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidEqualTo(id);
        List<FolderIp> ipInfoList = folderIpMapper.selectByExample(example);
        if (ipInfoList == null || ipInfoList.size() == 0){
            return null;
        }else{
            return ConvertUtils.convert(ipInfoList.get(0),FolderIpDto.class);
        }
    }

    List<FolderIp> findIpInfoByFolderIds(List<Long> ids) throws InstantiationException, IllegalAccessException {
        FolderIpExample example = new FolderIpExample();
        FolderIpExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidIn(ids);
        return folderIpMapper.selectByExample(example);
    }

    public int addIpInfo(FolderIpDto folderIpDto){
        FolderIpExample example = new FolderIpExample();
        FolderIpExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidEqualTo(folderIpDto.getFolderId());

        List<FolderIp> ipInfoList = folderIpMapper.selectByExample(example);
        if (ipInfoList == null || ipInfoList.size() == 0){
            FolderIp folderIp = new FolderIp();
            folderIp.setFolderid(folderIpDto.getFolderId());
            folderIp.setIpinfo(folderIpDto.getIpInfo());
            folderIp.setStatus((byte)1);
            return folderIpMapper.insert(folderIp);
        }else{
            FolderIp folderIp = ipInfoList.get(0);
            folderIp.setIpinfo(folderIpDto.getIpInfo());
            return folderIpMapper.updateByPrimaryKey(folderIp);
        }
    }

    public int deleteIpInfo(long id){
        FolderIpExample example = new FolderIpExample();
        FolderIpExample.Criteria criteria = example.createCriteria();
        criteria.andFolderidEqualTo(id);
        return folderIpMapper.deleteByExample(example);
    }
}