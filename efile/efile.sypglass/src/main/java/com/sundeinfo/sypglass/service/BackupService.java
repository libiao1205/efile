package com.sundeinfo.sypglass.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.sypglass.component.BackupSchedulerAdapter;
import com.sundeinfo.sypglass.configuration.ConfigService;
import com.sundeinfo.sypglass.define.SypGlassConfig;
import com.sundeinfo.sypglass.dto.DepartmentDto;
import com.sundeinfo.sypglass.dto.UserDto;
import com.sundeinfo.sypglass.mapper.BackupMapper;
import com.sundeinfo.sypglass.mapper.QuartzSettingMapper;
import com.sundeinfo.sypglass.model.*;
import com.sundeinfo.sypglass.utility.FileUtils;
import com.sundeinfo.sypglass.utility.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service("BackupService")
public class BackupService extends AbstractService<BackupService, Backup> {

    private static final String BACKUP_GROUP = "backupgroup";

    @Value("${batch.backup.mysqlPath}")
    private String MYSQL_PATH;

    @Value("${batch.backup.mysqlhost}")
    private String MYSQLDB_HOST;

    @Value("${batch.backup.mysqlpassword}")
    private String MYSQLDB_PASSWORD;

    @Value("${batch.backup.mysqldbname}")
    private String MYSQLDB_NAME;

    @Value("${batch.backup.mongoPath}")
    private String MONGO_PATH;

    @Value("${batch.backup.mongohost}")
    private String MONGO_HOST;

    @Value("${batch.backup.mongodbname}")
    private String MONGODB_NAME;

    @Value("${batch.backup.system.environment}")
    private String SYSTEM_ENVIRONMENT;

    @Autowired
    private BackupSchedulerAdapter backupSchedulerAdapter;

    @Autowired
    private BackupMapper backupMapper;

    @Autowired
    private QuartzSettingMapper quartzSettingMapper;

    @Autowired
    private ConfigService configService;

    public QuartzSetting findBackupScheduler(){
        QuartzSettingExample example = new QuartzSettingExample();
        QuartzSettingExample.Criteria criteria = example.createCriteria();
        criteria.andGroupnameEqualTo(BACKUP_GROUP);
        return quartzSettingMapper.selectByExample(example).get(0);
    }

    public Backup findById(long id){
        return backupMapper.selectByPrimaryKey(id);
    }

    public List<Backup> findAll(){
        BackupExample example = new BackupExample();
        example.setOrderByClause("TIME DESC");
        return backupMapper.selectByExample(example);
    }

    public int runMysqlbat(String batPath,String filePath,String fileName){
        StringBuilder builder = new StringBuilder();
        String line;
        InputStream inputStream = null;
        try {
            java.io.File backupFolderPath = new java.io.File(filePath);
            if(!backupFolderPath.exists()) {
                backupFolderPath.mkdirs();
            }
            //../batch/backup.mysql.bat
            //D://wamp//bin//mysql//mysql5.6.17//bin// D://sypglass//dev//backup//mysql// filename localhost password yp

            Process ps;
            logger.info("开始执行"+SYSTEM_ENVIRONMENT+"备份文件");
            if("windows".equals(SYSTEM_ENVIRONMENT)){
                String command = batPath
                        + " " + MYSQL_PATH
                        + " " + filePath
                        + " " + fileName
                        + " " + MYSQLDB_HOST
                        + " " + MYSQLDB_PASSWORD
                        + " " + MYSQLDB_NAME;
                ps = Runtime.getRuntime().exec("cmd /c start   " + command);
            }else{
                String[] command = {
                        "MYSQLDUMP="+MYSQL_PATH,
                        "BAK_DIR=" +filePath,
                        "FILENAME="+fileName,
                        "MYSQLDB_NAME=" + MYSQLDB_NAME,
                        "MYSQLDB_HOST=" + MYSQLDB_HOST,
                        "MYSQLDB_PASSWORD=" + MYSQLDB_PASSWORD
                };
                ps = Runtime.getRuntime().exec(batPath,command);
            }
            logger.info("结束执行"+SYSTEM_ENVIRONMENT+"备份文件");
            inputStream = ps.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while((line=bufferedReader.readLine()) != null) {
                builder.append(line);
                builder.append("\\n");
            }
            logger.info(builder.toString());
            ps.waitFor();
            return ps.exitValue();
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
            return -1;
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public int runMongobat(String batPath,String filePath){
        StringBuilder builder = new StringBuilder();
        String line;
        InputStream inputStream = null;
        try {
            java.io.File backupFolderPath = new java.io.File(filePath);
            if(!backupFolderPath.exists()) {
                backupFolderPath.mkdirs();
            }

            //../batch/backup.mongo.bat
            //D://MongoDB//Server//4.0//bin// D://sypglass//uat//backup//mongo// test 127.0.0.1:27017 yp

            Process ps;
            logger.info("开始执行"+SYSTEM_ENVIRONMENT+"备份文件");
            if("windows".equals(SYSTEM_ENVIRONMENT)){
                String command = batPath
                        + " " + MONGO_PATH
                        + " " + filePath
                        + " " + "mongo"
                        + " " + MONGO_HOST
                        + " " + MONGODB_NAME;
                ps = Runtime.getRuntime().exec("cmd /c start   " + command);
            }else{
                String[] command = {
                        "MONGODUMP=" + MONGO_PATH,
                        "MONGO_HOST=" + MONGO_HOST,
                        "MONGODB_NAME=" + MONGODB_NAME,
                        "BAK_DIR=" + filePath+"/mongo"
                };
                ps = Runtime.getRuntime().exec(batPath,command);
            }
            logger.info("结束执行"+SYSTEM_ENVIRONMENT+"备份文件");
            inputStream = ps.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

            while((line=bufferedReader.readLine()) != null) {
                builder.append(line);
                builder.append("\\n");
            }
            logger.info(builder.toString());
            ps.waitFor();
            return ps.exitValue();
        } catch (IOException | InterruptedException ioe) {
            ioe.printStackTrace();
            logger.info("异常");
            return -1;
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    public int save(Backup backup) throws InstantiationException, IllegalAccessException {
        return backupMapper.insert(backup);
    }

    public int deleteBackup(long id) throws Exception {
        Backup backup = backupMapper.selectByPrimaryKey(id);
        if (StringUtils.isNotBlank(backup.getDirectory())){
            java.io.File file = new java.io.File(backup.getDirectory());
            if (file.exists()){
                FileUtils.deleteFile(backup.getDirectory());
            }
        }
        return backupMapper.deleteByPrimaryKey(id);
    }

    public void ExceedTimeDeleteBackup(){
        Config config = configService.findOneByNameAndCode(SypGlassConfig.DELETE_BACKUP_PERIOD_NAME,SypGlassConfig.DELETE_BACKUP_PERIOD_CODE);
        if(config == null || config.getStatus() == 0){
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MONTH, 0 - Integer.valueOf(config.getValue()));
        List<Backup> backupList = this.findAll();
        backupList.forEach(dto ->{
            if(dto.getTime().before(calendar.getTime())){//备份日期 超出 保存周期
                try {
                    java.io.File file = new java.io.File(dto.getDirectory());
                    if (file.exists()){
                        FileUtils.deleteFile(dto.getDirectory());
                    }
                    backupMapper.deleteByPrimaryKey(dto.getId());
                }catch (Throwable e){
                    logger.info("定时删除备份文件出错",e);
                }
            }
        });
    }
    public int saveConfig(QuartzSetting setting){
        return backupSchedulerAdapter.reScheduleJob(setting.getId(),setting.getCron());
    }

    public int start(QuartzSetting setting){
        return backupSchedulerAdapter.resumeJob(setting.getId());
    }

    public int stop(QuartzSetting setting){
        return backupSchedulerAdapter.pauseJob(setting.getId());
    }
}
