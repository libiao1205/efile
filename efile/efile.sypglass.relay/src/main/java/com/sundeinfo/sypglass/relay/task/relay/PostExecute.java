package com.sundeinfo.sypglass.relay.task.relay;

import com.sundeinfo.core.http.HttpResult;
import com.sundeinfo.core.http.HttpService;
import com.sundeinfo.sypglass.relay.define.RelayStatus;
import com.sundeinfo.sypglass.relay.domain.RelayProcess;
import com.sundeinfo.core.utility.StringUtils;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class PostExecute<T> extends AbstractExecuteHandler<T>  {

    @Value("${send.url}")
    private String send_url;

    @Autowired
    private HttpService httpService;

    @Override
    public void execute() {
        if (!executeContinue()){
            logger.info("-------------服务配置停止传输文件到归档系统-------------");
            if(getSuccessor() != null){
                getSuccessor().execute();//执行下一步
            }
        }

        logger.info("-------------" + getRelayKind().description() + "开始传输文件到归档系统-------------");
        try{
            List<Byte> statuses = new ArrayList<>();
            statuses.add((byte)RelayStatus.FILE_EXTRACTED.code());//正常文件提取完毕
            statuses.add((byte)RelayStatus.FILE_SEND_ERROR.code());//上传失败的再次尝试

            List<RelayProcess> processes = relayPoolService.findByWorkflow(getRelayKind().id(),statuses);
            logger.info(String.format("RelayProcess检索到需要传输的文档中间数据：%s件",processes.size()));
            relayProcessService.insertProcessLog(getRelayKind().description() + "检索需要传输的文档中间数据",String.format("已经完成从中间表中检索需要传输的文档中间数据，" +
                    "检索到[%s]条RelayProcess有效中间数据",processes.size()));
            for (RelayProcess process:processes) {
                if (StringUtils.isEmpty(process.getRelayFilePath())){
                    continue;
                }
                File file = new File(process.getRelayFilePath());
                String comment = "OA系统导入文档";
                String url = String.format( send_url,
                        process.getRequestId(),
                        process.getImageFileId(),
                        process.getRequestDate(),
                        process.getWorkflowId(),
                        process.getComp(),
                        process.getDept(),
                        process.getLeader()) + "?comment=" + comment;

                HttpResult result = httpService.postFile(url,process.getRequestName(),file);

                switch (result.getCode()){
                    case HttpStatus.SC_OK:
                        Map<String,String> map = getState(result.getBody());
                        if (StringUtils.isEqualsString(map.get("Code"),"0")){
                            updateRelayProcessStatus(RelayStatus.RELAY_SUCCESS,process,"");
                        }else {
                            updateRelayProcessStatus(RelayStatus.FILE_SEND_ERROR,process,map.get("Message"));
                        }
                        break;
                    default:
                        updateRelayProcessStatus(RelayStatus.FILE_SEND_ERROR,process,"服务器通信异常编号：" + result.getCode());
                        break;
                }
            }
        } catch (IllegalAccessException | InstantiationException e) {
            logger.error("-------------" + getRelayKind().description() + "转换格式失败: e.message = " + e.getMessage() + "-------------");
            relayProcessService.insertProcessLog(getRelayKind().description() + "初期化失败","解析数据转换格式失败: e.message = " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        logger.info("-------------" + getRelayKind().description() + "传输文件到归档系统过程完成-------------");
        if(getSuccessor() != null){
            getSuccessor().execute();//执行下一步
        }
    }

}
