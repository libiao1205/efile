package com.sundeinfo.sypglass.relay.task.relay;

import com.sundeinfo.sypglass.relay.define.RelayStatus;
import com.sundeinfo.sypglass.relay.domain.RelayProcess;
import com.sundeinfo.sypglass.relay.utility.DateUtils;
import com.sundeinfo.core.utility.StringUtils;
import com.sundeinfo.file.utility.ZipUtils;
import jcifs.smb.*;
import org.springframework.beans.factory.annotation.Value;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public abstract class ExtractExecute<T> extends AbstractExecuteHandler<T> {

    @Value("${server.filesystem.ip}")
    private String serverIp;

    @Value("${server.filesystem.username}")
    private String serverUsername;

    @Value("${server.filesystem.password}")
    private String serverPassword;

    @Value("${server.filesystem.local}")
    private String serverLocalPath;

    @Override
    public void execute() {
        if (!executeContinue()){
            logger.info("-------------服务配置停止提取文件-------------");
            if(getSuccessor() != null){
                getSuccessor().execute();//执行下一步
            }
        }

        logger.info("-------------" + getRelayKind().description() + "开始提取文件-------------");
        relayProcessService.insertProcessLog(getRelayKind().description() + "提取文件开始","提取文件开始");
        try{
            List<Byte> statuses = new ArrayList<>();
            statuses.add((byte)RelayStatus.RELAY_INITIALISE.code());//OA数据导入准备完毕

            List<RelayProcess> processes = relayPoolService.findByWorkflow(getRelayKind().id(),statuses);
            logger.info(String.format("RelayProcess检索到有效中间数据：%s件",processes.size()));
            relayProcessService.insertProcessLog(getRelayKind().description() + "检索有效中间数据",String.format("已经完成从中间表中检索有效中间数据，" +
                    "检索到[%s]条RelayProcess有效中间数据",processes.size()));
            for (RelayProcess process:processes) {
                RelayStatus status = extractFile(process);
                updateRelayProcessStatus(status,process,status.value());
            }
            logger.info("-------------" + getRelayKind().description() + "提取文件过程完成-------------");
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error("-------------" + getRelayKind().description() + "转换格式失败: e.message = " + e.getMessage() + "-------------");
            relayProcessService.insertProcessLog(getRelayKind().description() + "转换格式失败","解析数据转换格式失败: e.message = " + e.getMessage());
            e.printStackTrace();
        }

        if(getSuccessor() != null){
            getSuccessor().execute();//执行下一步
        }
    }

    private RelayStatus extractFile(RelayProcess process){
        boolean debug = false;
        if (!debug)
        {
            String fileRealPath = process.getFileRealPath();
            String serverPath = "smb://" + fileRealPath.replace("..",serverIp).replace("\\","/");
            String localPath = copyFileToLocalPath(serverPath);

            if (StringUtils.isBlank(localPath)){//文件没有找到
                return RelayStatus.FILE_NOT_FOUND;
            }
            File zipFile = new File(localPath);
            String zipFolderPath = serverLocalPath + "/" + DateUtils.getShortDateString(new Date()) + "/unzip/" + UUID.randomUUID().toString(); ;
            final boolean unZipResult = ZipUtils.unZip(zipFile,zipFolderPath);

            if(!unZipResult){//解压失败
                return RelayStatus.FILE_UNZIP_ERROR;
            }

            String originalFileName = zipFile.getName().replace(".zip","");
            File originalFile = new File(zipFolderPath + "/" + originalFileName);

            String realFileName = zipFolderPath + "/" + process.getImageFileName();
            final boolean renameResult = originalFile.renameTo(new File(realFileName));
            if(!renameResult){//文件改名失败
                return RelayStatus.FILE_RESTORE_ERROR;
            }
            process.setRelayFilePath(realFileName);
            return RelayStatus.FILE_EXTRACTED;
        }else{
            if (StringUtils.isEmpty(process.getImageFileName())){
                return RelayStatus.FILE_NOT_FOUND;
            }
            String directoryPath = serverLocalPath + "/" + DateUtils.getShortDateString(new Date());
            File directory = new File(directoryPath);//根据指定的文件名创建File对象
            if (!directory.exists() ){  //要创建的文件目录不存在，则创建目录
                directory.mkdirs();
            }
            try{
                String localPath = directoryPath + "/" + process.getImageFileName() + ".txt";
                File file = new File(localPath);//用指定目录的方法创建临时文件
                if (file.exists()){ //要创建的文件存在，则先删除
                    file.delete();
                }
                boolean result = file.createNewFile();
                if (result){
                    process.setRelayFilePath(file.getPath());
                }
            }catch(IOException e){
                e.printStackTrace();
                System.out.println(e.getMessage());
            }
            return RelayStatus.FILE_EXTRACTED;
        }
    }

    /**
 　　* 从共享目录拷贝文件到本地
 　　*/
    private String copyFileToLocalPath(String remoteUrl) {
        InputStream in = null;
        OutputStream out = null;
        try{
            NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication(serverIp, serverUsername, serverPassword);
            //先登录验证
            SmbFile smbRemoteFile = new SmbFile(remoteUrl,auth);
            smbRemoteFile.connect();

            String fileName = smbRemoteFile.getName();
            File localFile = new File(serverLocalPath + "/"  + DateUtils.getShortDateString(new Date())+ "/zip/" + File.separator+fileName);
            if (!localFile.getParentFile().exists()) {
                localFile.getParentFile().mkdirs();
            }
            in = new BufferedInputStream(new SmbFileInputStream(smbRemoteFile));
            out = new BufferedOutputStream(new FileOutputStream(localFile));
            byte[] buffer = new byte[1024];
            while(in.read(buffer)!=-1){
                out.write(buffer);
                buffer = new byte[1024];
            }
            return localFile.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != out){
                    out.close();
                }
                if(null != in){
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
