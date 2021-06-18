package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.exception.FolderSettingException;
import com.sundeinfo.sypglass.mapper.FolderSettingMapper;
import com.sundeinfo.sypglass.model.FolderSetting;
import com.sundeinfo.sypglass.model.FolderSettingExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("FolderSettingService")
public class FolderSettingService extends AbstractService<FolderSettingService,FolderSetting> {

    @Autowired
    private FolderSettingMapper folderSettingMapper;

    public List<FolderSetting> findAll(){
        FolderSettingExample example = new FolderSettingExample();
        FolderSettingExample.Criteria criteria = example.createCriteria();
        criteria.andIdIsNotNull();
        return folderSettingMapper.selectByExample(example);
    }

    public FolderSetting findById(long id){
        return folderSettingMapper.selectByPrimaryKey(id);
    }

    public FolderSetting findByKey(long workflowId,String firstCode,String secondCode,String thirdCode){
        FolderSettingExample example = new FolderSettingExample();
        FolderSettingExample.Criteria criteria = example.createCriteria();
        criteria.andWorkflowidEqualTo(workflowId);
        criteria.andFirstdictcodeEqualTo(firstCode);
        criteria.andSeconddictcodeEqualTo(secondCode);
        criteria.andThirddictcodeEqualTo(thirdCode);
        List<FolderSetting> folderSettings = folderSettingMapper.selectByExample(example);
        if (folderSettings == null || folderSettings.size() == 0 || folderSettings.size() > 1) return null;
        return folderSettings.get(0);
    }

    public List<FolderSetting> findByKey(long workflowId,String firstCode,String secondCode){
        FolderSettingExample example = new FolderSettingExample();
        FolderSettingExample.Criteria criteria = example.createCriteria();
        criteria.andWorkflowidEqualTo(workflowId);
        criteria.andFirstdictcodeEqualTo(firstCode);
        criteria.andSeconddictcodeEqualTo(secondCode);
        List<FolderSetting> folderSettings = folderSettingMapper.selectByExample(example);
        if (folderSettings == null || folderSettings.size() == 0) return new ArrayList<>();
        return folderSettings;
    }

    public int save(FolderSetting setting){
        return folderSettingMapper.insert(setting);
    }

    public int edit(FolderSetting setting){
        return  folderSettingMapper.updateByPrimaryKeySelective(setting);
    }

    public int delete(long id){
        return folderSettingMapper.deleteByPrimaryKey(id);
    }

}
