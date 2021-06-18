package com.sundeinfo.sypglass.apis.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.sundeinfo.core.mvc.controller.AbstractController;
import com.sundeinfo.core.permission.FunctionType;
import com.sundeinfo.core.request.GetCallback;
import com.sundeinfo.core.request.PostCallback;
import com.sundeinfo.core.request.RequestState;
import com.sundeinfo.core.request.ResponseCallback;
import com.sundeinfo.core.request.result.Result;
import com.sundeinfo.file.utility.ZipUtils;
import com.sundeinfo.sypglass.configuration.ConfigService;
import com.sundeinfo.sypglass.model.Backup;
import com.sundeinfo.sypglass.model.Config;
import com.sundeinfo.sypglass.model.QuartzSetting;
import com.sundeinfo.sypglass.service.BackupService;
import com.sundeinfo.sypglass.utility.DateUtils;
import com.sundeinfo.sypglass.utility.EncodeUtils;
import com.sundeinfo.sypglass.utility.FileUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.jni.FileInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Api(description = "管理系统API")
@RestController
@RequestMapping("backup")
public class BackupController extends AbstractController<BackupController> {

    private static final String MYSQL_BAT_NAME = "backup.mysql";

    private static final String MONGO_BAT_NAME = "backup.mongo";

   /* @Value("${batch.backup.batPath}")*/
    private String BAT_PATH = "classpath:batch";

    @Value("${batch.backup.filePath}")
    private String FILE_PATH;

    @Value("${batch.backup.tmp}")
    private String ZIP_TMP;

    @Value("${batch.backup.system.environment}")
    private String SYSTEM_ENVIRONMENT;

    @Autowired
    private BackupService backupService;

    @ApiOperation(value="数据备份", notes="数据备份")
    @PostMapping()
    public ResponseEntity backup(){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        logger.info( "-------------开始执行手动备份-------------");

                        Date now = new Date();
                        Backup backup = new Backup();
                        backup.setTime(now);

                        String datePath = DateUtils.formatDate(now,"yyyyMMddHHmmss");
                        String backupResultPath = FILE_PATH + "/"+ datePath + "/";

                        String mySqlBackupFileName = "mysql_" + datePath;
                        String runMysqlBackUpPath;
                        String runMongolBackUpPath;
                        if("windows".equals(SYSTEM_ENVIRONMENT)){
                            runMysqlBackUpPath = MYSQL_BAT_NAME + ".bat";
                            runMongolBackUpPath = MONGO_BAT_NAME + ".bat";
                        }else{
                            runMysqlBackUpPath = MYSQL_BAT_NAME + ".sh";
                            runMongolBackUpPath = MONGO_BAT_NAME + ".sh";
                        }
                        File mysqlBatFile = ResourceUtils.getFile(BAT_PATH+ File.separator + runMysqlBackUpPath);
                        if(!mysqlBatFile.exists()){
                            logger.error("没有找到mysql备份用的批处理文件 路径：" + mysqlBatFile.getPath());
                            backup.setResult("备份失败");
                            backupService.save(backup);
                            return RequestState.DB_BACKUP_ERROR;
                        }

                        File mongoBatFile = ResourceUtils.getFile(BAT_PATH+ File.separator + runMongolBackUpPath);
                        if(!mongoBatFile.exists()){
                            logger.error("没有找到mongo备份用的批处理文件 路径：" + mongoBatFile.getPath());
                            backup.setResult("备份失败");
                            backupService.save(backup);
                            return RequestState.DB_BACKUP_ERROR;
                        }

                        //执行mysql备份命令
                        logger.info( "-------------执行mysql备份命令-------------");
                        int mysqlRet = backupService.runMysqlbat(mysqlBatFile.getPath(),backupResultPath,mySqlBackupFileName);

                        //执行mongo备份命令
                        logger.info( "-------------执行mongo备份命令-------------");
                        int mongoRet = backupService.runMongobat(mongoBatFile.getPath(),backupResultPath);
                        logger.info("mysql:"+mysqlRet+",mongo:"+mongoRet);
                        if (mysqlRet == 0 && mongoRet == 0){
                            //校验备份文件是否存在
                            File file = new File(backupResultPath + mySqlBackupFileName + ".sql");
                            File directory = new File(backupResultPath + "mongo");
                            if (file.exists() && directory.exists()){
                                backup.setDirectory(backupResultPath);
                                backup.setResult("备份成功");
                                int i = backupService.save(backup);
                                if(i > 0){
                                    backupService.ExceedTimeDeleteBackup();
                                }
                                return RequestState.SUCCESS;
                            }else{
                                backup.setResult("备份失败");
                                backupService.save(backup);
                                return RequestState.DB_BACKUP_ERROR;
                            }
//                            //都备份成功后执行压缩
//                            String zipFileName = datePath + ".zip";
//                            String zipFilePath = ZIP_TMP + zipFileName;
//                            FileOutputStream zipFileStream = new FileOutputStream(new File(zipFilePath));
//                            logger.info( "-------------备份成功后执行压缩-------------");
//                            boolean zipRet = ZipUtils.zip(backupResultPath,zipFileStream,true);
//                            if (zipRet){
//                                //移动压缩文件
//                                logger.info( "-------------移动压缩文件-------------");
//                                boolean moveResult = FileUtils.moveTo(zipFilePath,backupResultPath);
//                                logger.info( "-------------备份执行完成，确认备份结果并返回备份结果-------------");
//                                if (moveResult){
//                                    File file = new File(backupResultPath + zipFileName);
//                                    if(file.exists()){
//                                        backup.setDirectory(backupResultPath + zipFileName);
//                                        backup.setResult("备份成功");
//                                        backupService.save(backup);
//                                        return RequestState.SUCCESS;
//                                    }else{
//                                        backup.setResult("备份失败");
//                                        backupService.save(backup);
//                                        return RequestState.DB_BACKUP_ERROR;
//                                    }
//                                }else{
//                                    backup.setResult("备份失败");
//                                    backupService.save(backup);
//                                    return RequestState.DB_BACKUP_ERROR;
//                                }
//                            }else{
//                                backup.setResult("备份失败");
//                                backupService.save(backup);
//                                return RequestState.DB_BACKUP_ERROR;
//                            }
                        }
                        else{
                            backup.setResult("备份失败");
                            backupService.save(backup);
                            return RequestState.DB_BACKUP_ERROR;
                        }
                    }
                }
        );
    }

    @ApiOperation(value="查找备份配置", notes="查找备份配置")
    @GetMapping(value = "config")
    public ResponseEntity<Result<QuartzSetting>> getConfig(){
        return requestHandler.doGet(
                new GetCallback<QuartzSetting>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public QuartzSetting invoke() throws Exception {
                        return backupService.findBackupScheduler();
                    }
                }
        );
    }

    @ApiOperation(value="编辑备份配置", notes="编辑备份配置")
    @PutMapping(value = "config")
    public ResponseEntity<Result> putConfig(@RequestBody QuartzSetting setting){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception{
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int iRet = backupService.saveConfig(setting);
                        if(iRet > 0) {
                            return RequestState.SUCCESS;
                        }else{
                            return RequestState.DB_UPDATE_ERROR;
                        }
                    }
                }
        );
    }

    @ApiOperation(value="开始执行定时策略", notes="开始执行定时策略")
    @PutMapping(value = "start")
    public ResponseEntity<Result> putStart(@RequestBody QuartzSetting setting){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception{
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int iRet = backupService.start(setting);
                        if(iRet > 0) {
                            return RequestState.SUCCESS;
                        }else{
                            return RequestState.DB_UPDATE_ERROR;
                        }
                    }
                }
        );
    }

    @ApiOperation(value="暂停执行定时策略", notes="暂停执行定时策略")
    @PutMapping(value = "stop")
    public ResponseEntity<Result> putStop(@RequestBody QuartzSetting setting){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception{
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int iRet = backupService.stop(setting);
                        if(iRet > 0) {
                            return RequestState.SUCCESS;
                        }else{
                            return RequestState.DB_UPDATE_ERROR;
                        }
                    }
                }
        );
    }



    @ApiOperation(value="批量查找备份列表", notes="批量查找备份列表")
    @GetMapping(value = "list/{pagenum}-{pagesize}")
    public ResponseEntity<Result<PageInfo<Backup>>> getBackups(@PathVariable(value = "pagenum") int pagenum, @PathVariable(value = "pagesize") int pagesize){
        return requestHandler.doGet(
                new GetCallback<PageInfo<Backup>>() {
                    @Override
                    public RequestState doCheck() {
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public PageInfo<Backup> invoke() throws Exception {
                        PageHelper.startPage(pagenum, pagesize);
                        List<Backup> backups = backupService.findAll();
                        return new PageInfo<>(backups);
                    }
                }
        );
    }

    @ApiOperation(value="删除备份", notes="删除备份")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Result> deleteBackup(@PathVariable(value = "id") int id){
        return requestHandler.doPost(
                new PostCallback() {
                    @Override
                    public RequestState doCheck() throws Exception{
                        return RequestState.SUCCESS;
                    }
                    @Override
                    public RequestState invoke() throws Exception {
                        int iRet = backupService.deleteBackup(id);
                        if(iRet > 0) {
                            return RequestState.SUCCESS;
                        }else{
                            return RequestState.DB_UPDATE_ERROR;
                        }
                    }
                }
        );
    }

    @ApiOperation(value="文件下载", notes="文件下载")
    @PostMapping("download/{id}")
    public void downloadFile(@PathVariable long id, HttpServletResponse response){
        try {
            Backup up = backupService.findById(id);
            File file = new File(up.getDirectory());
            if(!file.exists()){
                return;
            }
            String[] filePath = up.getDirectory().split("/");
            String fileName = filePath[filePath.length-1];
            OutputStream outputStream  = new BufferedOutputStream(response.getOutputStream());
            InputStream inputStream = new BufferedInputStream(new FileInputStream(file));

            // 通知浏览器进行文件下载
            response.addHeader("Access-Control-Expose-Headers","Content-Disposition");
            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
            //response.addHeader("Content-Length", "" + file.length());
            response.setContentType("application/octet-stream");


            byte[] buffer = new byte[1024];
            //inputStream.read(buffer);

            int len = 0;
            while ((len = inputStream.read(buffer)) > 0) {
                //将缓冲区的数据输出到客户端浏览器
                outputStream.write(buffer,0,len);
            }
            //outputStream.write(buffer);

            inputStream.close();
            outputStream.flush();
            outputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
