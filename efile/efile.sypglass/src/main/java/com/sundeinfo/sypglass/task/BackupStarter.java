package com.sundeinfo.sypglass.task;

import com.github.pagehelper.PageHelper;
import com.mongodb.gridfs.GridFSDBFile;
import com.sundeinfo.core.job.AbstractTaskStarter;
import com.sundeinfo.core.job.Task;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.file.component.FileOperator;
import com.sundeinfo.file.component.FileReadManager;
import com.sundeinfo.file.fulltext.FullTextReader;
import com.sundeinfo.file.utility.ZipUtils;
import com.sundeinfo.sypglass.configuration.ConfigService;
import com.sundeinfo.sypglass.define.SypGlassConfig;
import com.sundeinfo.sypglass.model.Backup;
import com.sundeinfo.sypglass.model.Config;
import com.sundeinfo.sypglass.service.BackupService;
import com.sundeinfo.sypglass.utility.DateUtils;
import com.sundeinfo.sypglass.utility.FileUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Component("BackupStarter")
public class BackupStarter extends AbstractTaskStarter<BackupStarter> implements Task {

    private static final String MYSQL_BAT_NAME = "backup.mysql.bat";

    private static final String MONGO_BAT_NAME = "backup.mongo.bat";



    /*@Value("${batch.backup.batPath}")*/
    private String BAT_PATH = "classpath:batch";

    @Value("${batch.backup.filePath}")
    private String FILE_PATH;

    @Value("${batch.backup.tmp}")
    private String ZIP_TMP;

    @Autowired
    private BackupService backupService;

    @Autowired
    private ConfigService configService;

    @Override
    protected Task getTask() {
        return this;
    }

    @Override
    public void execute() {
        logger.info( "-------------开始执行自动备份-------------");

        Date now = new Date();

        Backup backup = new Backup();
        backup.setTime(now);

        String datePath = DateUtils.formatDate(now,"yyyyMMddHHmmss");
        String backupResultPath = FILE_PATH + datePath + "//";

        String mySqlBackupFileName = "mysql_" + datePath;
        try {
            File mysqlBatFile = ResourceUtils.getFile(BAT_PATH+ File.separator + MYSQL_BAT_NAME);
            if(!mysqlBatFile.exists()){
                logger.error("没有找到mysql备份用的批处理文件 路径：" + mysqlBatFile.getPath());
                backup.setResult("备份失败");
                backupService.save(backup);
                return;
            }

            File mongoBatFile = ResourceUtils.getFile(BAT_PATH+ File.separator + MONGO_BAT_NAME);
            if(!mongoBatFile.exists()){
                logger.error("没有找到mongo备份用的批处理文件 路径：" + mongoBatFile.getPath());
                backup.setResult("备份失败");
                backupService.save(backup);
                return;
            }

            //执行mysql备份命令
            logger.info( "-------------执行mysql备份命令-------------");
            int mysqlRet = backupService.runMysqlbat(mysqlBatFile.getPath(),backupResultPath,mySqlBackupFileName);

            //执行mongo备份命令
            logger.info( "-------------执行mongo备份命令-------------");
            int mongoRet = backupService.runMongobat(mongoBatFile.getPath(),backupResultPath);

            if (mysqlRet == 0 && mongoRet == 0){
                //校验备份文件是否存在
                File file = new File(backupResultPath + mySqlBackupFileName + ".sql");
                File directory = new File(backupResultPath + "mongo");
                if (file.exists() && directory.exists()){
                    backup.setDirectory(backupResultPath);
                    backup.setResult("备份成功");
                    int i = backupService.save(backup);
                    /*if(i > 0){
                        this.isDeleteBackup();
                    }*/
                }else{
                    backup.setResult("备份失败");
                    backupService.save(backup);
                }
//                //备份成功后执行压缩
//                String zipFileName = datePath + ".zip";
//                String zipFilePath = ZIP_TMP + zipFileName;
//                FileOutputStream zipFileStream = new FileOutputStream(new File(zipFilePath));
//                logger.info( "-------------备份成功后执行压缩-------------");
//                boolean zipRet = ZipUtils.zip(backupResultPath,zipFileStream,true);
//                if (zipRet){
//                    //移动压缩文件
//                    logger.info( "-------------移动压缩文件-------------");
//                    boolean moveResult = FileUtils.moveTo(zipFilePath,backupResultPath);
//                    if (moveResult){
//                        File file = new File(backupResultPath + zipFileName);
//                        if(file.exists()){
//                            backup.setDirectory(backupResultPath + zipFileName);
//                            backup.setResult("备份成功");
//                            backupService.save(backup);
//                        }else{
//                            backup.setResult("备份失败");
//                            backupService.save(backup);
//                        }
//                    }else{
//                        backup.setResult("备份失败");
//                        backupService.save(backup);
//                    }
//                }else{
//                    backup.setResult("备份失败");
//                    backupService.save(backup);
//                }
            }
            else{
                backup.setResult("备份失败");
                backupService.save(backup);
            }
        } catch (Exception e) {
            logger.error("定时备份发生异常：" + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void start() {
        logger.info(" 开始执行自动备份: " + formatter.format(new Date()));
    }

    @Override
    protected void end() {
        logger.info( " 自动备份执行完成");
    }

    /*private void isDeleteBackup(){
        try {
            Config config = configService.findOneByNameAndCode(SypGlassConfig.DELETE_BACKUP_PERIOD_NAME,SypGlassConfig.DELETE_BACKUP_PERIOD_CODE);
            if(config == null || config.getStatus() == 0){
                return;
            }
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            calendar.add(Calendar.MONTH, 0 - Integer.valueOf(config.getValue()));
            List<Backup> backupList = backupService.findAll();
            backupList.forEach(dto ->{
                if(dto.getTime().before(calendar.getTime())){//备份日期 超出 保存周期
                    try {
                        backupService.deleteBackup(dto.getId());
                    } catch (Exception e) {
                        logger.error("定时删除备份文件出错",e);
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            logger.error("定时删除备份文件出错",e);
            e.printStackTrace();
        }
    }*/

}
