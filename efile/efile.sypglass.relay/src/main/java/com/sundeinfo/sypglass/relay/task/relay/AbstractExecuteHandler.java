package com.sundeinfo.sypglass.relay.task.relay;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.sypglass.relay.define.RelayKind;
import com.sundeinfo.sypglass.relay.define.RelayStatus;
import com.sundeinfo.sypglass.relay.domain.RelayProcess;
import com.sundeinfo.sypglass.relay.domain.RelayProcessInfo;
import com.sundeinfo.sypglass.relay.service.RelayProcessService;
import com.sundeinfo.sypglass.relay.service.RelayPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractExecuteHandler<T> extends AbstractComponent<T> {

    @Value("${Relay.Document}")
    private String relayDocument;

    @Autowired
    protected RelayProcessService relayProcessService;

    @Autowired
    protected RelayPoolService relayPoolService;


    public abstract void execute();

    protected abstract RelayKind getRelayKind();

    private AbstractExecuteHandler successor;

    AbstractExecuteHandler getSuccessor() {
        return successor;
    }

    public void setSuccessor(AbstractExecuteHandler successor) {
        this.successor = successor;
    }

    //没有停止条件
    protected boolean executeContinue(){
        return "true".equals(relayDocument);
    }

    Map<String,String> getState(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        Map<String,String> result = new HashMap<>();
        result.put("Code",root.get("c").asText());
        result.put("Message",root.get("msg").asText());
        return result;
    }

    void updateRelayProcessStatus(RelayStatus status, RelayProcess process, String message){
        String processInfo = "|过程信息:" + message + String.format("|过程数据ID:%s|requestID:%s|workflowID:%s|form:%s|formID:%s|fileID:%s|oaPath:%s|relayPath:%s",
                process.getId(), process.getRequestId(), process.getWorkflowId(),process.getFormCode(),
                process.getFormId(),process.getImageFileId(),process.getFileRealPath(),process.getRelayFilePath());
        String statusInfo = String.format("----%s - %s----",getRelayKind().description(),status.value());
        String logInfo = statusInfo + processInfo;

        RelayProcessInfo info = RelayProcessInfo.initialize(process.getId(),status,process.getRelayFilePath(),logInfo);


        relayPoolService.updateProcessInfo(info);
        relayProcessService.insertProcessLog(statusInfo,processInfo);

        switch (status){
            case FILE_EXTRACTED:
            case RELAY_SUCCESS:
                logger.info(logInfo);
                break;
            case FILE_NOT_FOUND:
            case FILE_UNZIP_ERROR:
            case FILE_RESTORE_ERROR:
            case FILE_SEND_ERROR:
                logger.warn(logInfo);
                break;

        }
    }
}
