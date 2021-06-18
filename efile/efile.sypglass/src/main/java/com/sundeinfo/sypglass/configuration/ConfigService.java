package com.sundeinfo.sypglass.configuration;

import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;
import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.sypglass.define.SypGlassConfig;
import com.sundeinfo.sypglass.dto.*;
import com.sundeinfo.sypglass.mapper.ConfigMapper;
import com.sundeinfo.sypglass.model.Config;
import com.sundeinfo.sypglass.model.ConfigExample;
import com.sundeinfo.sypglass.service.GridfsService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

@Service("ConfigService")
public class ConfigService extends AbstractService<ConfigService,Config> {

    @Autowired
    private ConfigMapper configMapper;

    @Autowired
    private GridfsService gridfsService;

    public Config create(String name,String code,String value,String commnet){
        Config config = new Config();
        config.setName(name);
        config.setCode(code);
        config.setSeq((byte)1);
        config.setValue(value);
        config.setStatus((byte)1);
        config.setComment(commnet);
        return config;
    }

    public Config create(String name,String code,int seq,String value,String commnet){
        Config config = create(name,code,value,commnet);
        config.setSeq((byte)seq);
        return config;
    }

    public List<Config> findByName(String name){
        ConfigExample example = new ConfigExample();
        ConfigExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        return configMapper.selectByExample(example);
    }

    public List<Config> findByNameAndCode(String name,String code){
        ConfigExample example = new ConfigExample();
        ConfigExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andCodeEqualTo(code);
        return configMapper.selectByExample(example);
    }

    public Config findOneByNameAndCode(String name,String code){
        List<Config> list =  findByNameAndCode(name,code);
        if (list == null || list.size() == 0) return null;
        return list.get(0);
    }

    public Config findByNameAndCodeAndSeq(String name,String code,Byte seq){
        ConfigExample example = new ConfigExample();
        ConfigExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andCodeEqualTo(code);
        criteria.andSeqEqualTo(seq);
        List<Config> list = configMapper.selectByExample(example);
        if (list == null || list.size() == 0) return null;
        return list.get(0);
    }

    public int insert(Config config){
        return configMapper.insert(config);
    }

    @Transactional
    public int editConfig(Config config){
       return configMapper.updateByPrimaryKeySelective(config);
    }

    public PasswordExceedDto psdExceedFindByName(){
        PasswordExceedDto passwordExceedDto = new PasswordExceedDto();
        List<Config> configList = this.findByName(SypGlassConfig.PASSWORD_EXCEED);
        configList.forEach((dto) ->{
            if(SypGlassConfig.PASSWORD_USABLE_DAY.equals(dto.getCode())){
                passwordExceedDto.setUsableDay(dto.getValue());
                passwordExceedDto.setState(dto.getStatus().toString());
            }else if(SypGlassConfig.PASSWORD_AHEAD_DAY.equals(dto.getCode())){
                passwordExceedDto.setAheadDay(dto.getValue());
            }
        });
        return passwordExceedDto;
    }

    public PasswordComplexityDto psdComplexityFindByName(){
        PasswordComplexityDto passwordComplexityDto = new PasswordComplexityDto();
        List<Config> configList = this.findByName(SypGlassConfig.PASSWORD_COMPLEXITY);
        configList.forEach((dto) ->{
            if(SypGlassConfig.PSD_CHECK.equals(dto.getCode())){
                passwordComplexityDto.setPsdCheck(dto.getValue());
            }else if(SypGlassConfig.PSD_LENGTH.equals(dto.getCode())){
                passwordComplexityDto.setPsdLength(dto.getValue());
            }
        });
        return passwordComplexityDto;
    }
    public String checkPsdComplexity(String password){
        PasswordComplexityDto passwordComplexityDto = this.psdComplexityFindByName();
        if(passwordComplexityDto == null){
            return "";
        }
        if(password.length() < Integer.valueOf(passwordComplexityDto.getPsdLength())){
            return String.format("密码长度不足%s位",passwordComplexityDto.getPsdLength());
        }
        if("1".equals(passwordComplexityDto.getPsdCheck())){
            if (password.matches(SypGlassConfig.PSD_CHECK_TOP1)){
                return "";
            }else{
                return "密码必须是大写字母、小写字母与数字的组合";
            }
        }
        if("2".equals(passwordComplexityDto.getPsdCheck())){
            if (password.matches(SypGlassConfig.PSD_CHECK_TOP2)){
                return "";
            }else{
                return "密码必须是字母、数字、特殊字符的组合";
            }
        }
        return "";
    }

    @Transactional
    public int updatePasswordExceed(PasswordExceedDto passwordExceedDto){
        try {
            List<Config> configList = this.findByName(SypGlassConfig.PASSWORD_EXCEED);
            if(configList.size() == 0){
                this.insert(this.create(SypGlassConfig.PASSWORD_EXCEED,SypGlassConfig.PASSWORD_USABLE_DAY,1,passwordExceedDto.getUsableDay(),"强制密码修改天数"));
                this.insert(this.create(SypGlassConfig.PASSWORD_EXCEED,SypGlassConfig.PASSWORD_AHEAD_DAY,2,passwordExceedDto.getAheadDay(),"提前几天提醒修改密码"));
                return 1;
            }
            Config record = new Config();
            record.setValue(passwordExceedDto.getUsableDay());
            record.setStatus(Byte.valueOf(passwordExceedDto.getState()));
            this.updateByExampleSelective(SypGlassConfig.PASSWORD_EXCEED,SypGlassConfig.PASSWORD_USABLE_DAY,record);
            record.setValue(passwordExceedDto.getAheadDay());
            this.updateByExampleSelective(SypGlassConfig.PASSWORD_EXCEED,SypGlassConfig.PASSWORD_AHEAD_DAY,record);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Transactional
    public int updatePasswordMistakeCount(PasswordLockDto passwordLockDto){
        try {
            List<Config> configList = this.findByName(SypGlassConfig.PSD_LOCK);
            if(configList.size() == 0){
                return this.insert(this.create(SypGlassConfig.PSD_LOCK,SypGlassConfig.PSD_MISTAKE_COUNT,1,passwordLockDto.getPsdMistakeCount(),"锁定密码错误次数"));
            }
            Config record = new Config();
            record.setValue(passwordLockDto.getPsdMistakeCount());
            record.setStatus(Byte.valueOf(passwordLockDto.getState()));
            return this.updateByExampleSelective(SypGlassConfig.PSD_LOCK,SypGlassConfig.PSD_MISTAKE_COUNT,record);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    @Transactional
    public int updatePasswordComplexity(PasswordComplexityDto passwordComplexityDto){
        try {
            List<Config> configList = this.findByName(SypGlassConfig.PASSWORD_COMPLEXITY);
            if(configList.size() == 0){
                this.insert(this.create(SypGlassConfig.PASSWORD_COMPLEXITY,SypGlassConfig.PSD_CHECK,1,passwordComplexityDto.getPsdCheck(),"校验级别"));
                this.insert(this.create(SypGlassConfig.PASSWORD_COMPLEXITY,SypGlassConfig.PSD_LENGTH,2,passwordComplexityDto.getPsdLength(),"长度"));
                return 1;
            }
            Config record = new Config();
            record.setValue(passwordComplexityDto.getPsdCheck());
            this.updateByExampleSelective(SypGlassConfig.PASSWORD_COMPLEXITY,SypGlassConfig.PSD_CHECK,record);
            record.setValue(passwordComplexityDto.getPsdLength());
            this.updateByExampleSelective(SypGlassConfig.PASSWORD_COMPLEXITY,SypGlassConfig.PSD_LENGTH,record);
            return 1;
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    @Transactional
    public int updateDeleteBackup(DeleteBackupDto deleteBackupDto){
        try {
            List<Config> configList = this.findByName(SypGlassConfig.DELETE_BACKUP_PERIOD_NAME);
            if(configList.size() == 0){
                return this.insert(this.create(SypGlassConfig.DELETE_BACKUP_PERIOD_NAME,SypGlassConfig.DELETE_BACKUP_PERIOD_CODE,1,deleteBackupDto.getMonth(),"自动删除备份文件，单位：月"));
            }
            Config record = new Config();
            record.setValue(deleteBackupDto.getMonth());
            record.setStatus(Byte.valueOf(deleteBackupDto.getState()));
            return this.updateByExampleSelective(SypGlassConfig.DELETE_BACKUP_PERIOD_NAME,SypGlassConfig.DELETE_BACKUP_PERIOD_CODE,record);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    public void getLogoImg(HttpServletResponse response,String name, String code){
        OutputStream outputStream = null;
        try {
            Config config = this.findOneByNameAndCode(name,code);
            response.setCharacterEncoding("UTF-8");
            if(config.getStatus() == 0){
                response.addHeader("Content-type", "image/png");
            }else{
                response.addHeader("Content-type", "image/jpeg;charset=UTF-8");
            }
            response.addHeader("cache-control", "max-age=2628000");
            GridFSDBFile file = gridfsService.getById(new ObjectId(config.getValue()));
            outputStream = response.getOutputStream();
            file.writeTo(outputStream);
            outputStream.flush();
            outputStream.close();
        }catch (Exception e){
            try{
                if (outputStream != null) {
                    outputStream.close();
                }
            }catch (Exception ignored){}
            e.printStackTrace();
        }

    }

    @Transactional
    public RequestState saveLogoImg(MultipartFile[] files){
        try {
            List<Config> configList = this.findByName(SypGlassConfig.FOLDER_ROOT_LOGO_NAME);

            GridFSInputFile input = gridfsService.save(files[0]);
            if (input == null) {
                return RequestState.FILE_INSERT_ERROR;
            }
            if(configList.size() == 0){
//                String[] strArray = input.getFilename().split("\\.");
//                int suffixIndex = strArray.length -1;
//                int state = 1;
//                if (strArray[suffixIndex].toLowerCase().equals("png")) {
//                    state = 0;
//                }
                int flag = this.insert(this.create(SypGlassConfig.FOLDER_ROOT_LOGO_NAME,SypGlassConfig.FOLDER_ROOT_LOGO_CODE,1,input.getId().toString(),"系统logo，0：.png,1：其他"));
                if(flag > 0){
                    return RequestState.SUCCESS;
                }
                return RequestState.DB_UPDATE_ERROR;
            }
            gridfsService.remove(configList.get(0).getValue());
            Config record = new Config();
            record.setValue(input.getId().toString());
            int flag =  this.updateByExampleSelective(SypGlassConfig.FOLDER_ROOT_LOGO_NAME,SypGlassConfig.FOLDER_ROOT_LOGO_CODE,record);
            if(flag > 0){
                return RequestState.SUCCESS;
            }
            return RequestState.SUCCESS;
        }catch (Exception e){
            e.printStackTrace();
            return RequestState.DB_UPDATE_ERROR;
        }
    }
    @Transactional
    public int constructionData(SystemSwitchDto systemSwitchDto){
        try {
            List<Config> configList = this.findByName(SypGlassConfig.CONSTRUCTION_DATA_SWITCH_NAME);
            if(configList.size() == 0){
                return this.insert(this.create(SypGlassConfig.CONSTRUCTION_DATA_SWITCH_NAME,SypGlassConfig.CONSTRUCTION_DATA_SWITCH_CODE,1,systemSwitchDto.getValue(),"是否支持结构化数据，0：不支持，1：支持"));
            }
            Config record = new Config();
            record.setValue(systemSwitchDto.getValue());
            return this.updateByExampleSelective(SypGlassConfig.CONSTRUCTION_DATA_SWITCH_NAME,SypGlassConfig.CONSTRUCTION_DATA_SWITCH_CODE,record);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    @Transactional
    public int solrSynchronizationSwitch(SystemSwitchDto systemSwitchDto){
        try {
            List<Config> configList = this.findByNameAndCode(SypGlassConfig.SOLR_SYNCHRONIZATION_SWITCH_NAME,SypGlassConfig.SOLR_SYNCHRONIZATION_SWITCH_CODE);
            if(configList.size() == 0){
                return this.insert(this.create(SypGlassConfig.SOLR_SYNCHRONIZATION_SWITCH_NAME,SypGlassConfig.SOLR_SYNCHRONIZATION_SWITCH_CODE,1,systemSwitchDto.getValue(),"solr同步模式，1：即时同步，2：每日晚间同步"));
            }
            Config record = new Config();
            record.setValue(systemSwitchDto.getValue());
            return this.updateByExampleSelective(SypGlassConfig.SOLR_SYNCHRONIZATION_SWITCH_NAME,SypGlassConfig.SOLR_SYNCHRONIZATION_SWITCH_CODE,record);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    @Transactional
    public int solrQueryTemplate(SystemSwitchDto systemSwitchDto){
        try {
            List<Config> configList = this.findByNameAndCode(SypGlassConfig.SOLR_QUERY_TEMPLATE_NAME,SypGlassConfig.SOLR_QUERY_TEMPLATE_CODE);
            if(configList.size() == 0){
                return this.insert(this.create(SypGlassConfig.SOLR_QUERY_TEMPLATE_NAME,SypGlassConfig.SOLR_QUERY_TEMPLATE_CODE,1,systemSwitchDto.getQueryTemplate(),"自定义solr检索语句"));
            }
            Config record = new Config();
            record.setValue(systemSwitchDto.getQueryTemplate());
            return this.updateByExampleSelective(SypGlassConfig.SOLR_QUERY_TEMPLATE_NAME,SypGlassConfig.SOLR_QUERY_TEMPLATE_CODE,record);
        }catch (Exception e){
            e.printStackTrace();
            return 0;
        }
    }
    private int updateByExampleSelective(String name,String code,Config record){
        ConfigExample example = new ConfigExample();
        ConfigExample.Criteria criteria = example.createCriteria();
        criteria.andNameEqualTo(name);
        criteria.andCodeEqualTo(code);
        return configMapper.updateByExampleSelective(record,example);
    }

}