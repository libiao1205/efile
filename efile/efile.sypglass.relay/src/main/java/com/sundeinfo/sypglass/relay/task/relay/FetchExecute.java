package com.sundeinfo.sypglass.relay.task.relay;

import com.sundeinfo.sypglass.relay.domain.OAProcesses;
import com.sundeinfo.sypglass.relay.domain.RelayProcess;
import com.sundeinfo.sypglass.relay.mapper.oa.DocImageFileMapper;
import com.sundeinfo.sypglass.relay.mapper.oa.ImageFileMapper;
import com.sundeinfo.sypglass.relay.mapper.oa.WorkflowRequestBaseMapper;
import com.sundeinfo.sypglass.relay.model.oa.*;
import com.sundeinfo.sypglass.relay.service.RelayPoolService;
import com.sundeinfo.sypglass.relay.task.ProcessDataConverter;
import com.sundeinfo.sypglass.relay.utility.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class FetchExecute<T> extends AbstractExecuteHandler<T> {

    @Autowired
    private RelayPoolService relayPoolService;

    @Autowired
    private WorkflowRequestBaseMapper workflowRequestBaseMapper;

    @Autowired
    private DocImageFileMapper docImageFileMapper;

    @Autowired
    private ImageFileMapper imageFileMapper;

    protected abstract List<FormTableMain> findFormTableMainByRequestId(int requestid) throws InstantiationException, IllegalAccessException;

    @Override
    public void execute() {

        if (!executeContinue()){
            logger.info("-------------服务配置停止检索OA数据-------------");
            if(getSuccessor() != null){
                getSuccessor().execute();//执行下一步
            }
        }

        String logInfo;

        logger.info("-------------" + getRelayKind().description() + "开始检索OA数据-------------");
        String startDate = relayProcessService.computeRelayDatetime(getRelayKind());
        String endDate = DateUtils.getShortDateString(new Date());
        logInfo = String.format("流程[%s]初期化开始，检索开始日[%s],检索结束日[%s]",getRelayKind().value(),startDate,endDate);
        logger.info("-------------" + logInfo + "-------------");
        relayProcessService.insertProcessLog(getRelayKind().description() + "初期化",logInfo);
        try {

            int count = workflowRequestBaseMapper.countWorkflowRequestBaseByWorkflowIdAndPeriod(getRelayKind().id(),startDate,endDate);
            logInfo =  String.format("流程[%s]共检索到[%s]条workflow_request数据，开始抽取并解析OA数据",getRelayKind().value(),count);
            logger.info("-------------" + logInfo + "-------------");
            relayProcessService.insertProcessLog(getRelayKind().description() + "检索OA数据",logInfo);
            int firstIndex = 0;
            do{
                List<OAProcesses> processes = findByWorkflowIdAndStartDate(getRelayKind().id(),startDate,endDate,firstIndex);
                List<RelayProcess> inputs = ProcessDataConverter.createRelayInput(processes,getRelayKind().form());
                logInfo = String.format("%s检索到[%s]条OA数据,解析出[%s]条Relay数据",getRelayKind().description(),processes.size(),inputs.size());
                logger.info("-------------" + logInfo + "-------------");
                relayProcessService.insertProcessLog(getRelayKind().description() + "初期化",logInfo);

                int saveRelayInputResult = relayPoolService.saveAll(processes,inputs);
                if (saveRelayInputResult == 0){
                    logger.error(getRelayKind().description() + "初期化发生异常，数据保存失败");
                    relayProcessService.insertProcessLog(getRelayKind().description() + "初期化失败", "初期化发生异常，数据保存失败");
                    throw new RuntimeException(getRelayKind().description() + "初期化发生异常，数据保存失败");
                }
                firstIndex = firstIndex + 100;
            }while (count >= firstIndex);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error("-------------" + getRelayKind().description() + "数据检索发生异常: error.message = " + e.getMessage() + "-------------");
            relayProcessService.insertProcessLog(getRelayKind().description() + "初期化失败","OA数据检索时发生异常: e.message = " + e.getMessage());
            e.printStackTrace();
        }
        if(getSuccessor() != null){
            getSuccessor().execute();//执行下一步
        }
    }

    private List<OAProcesses> findByWorkflowIdAndStartDate(int workflowId, String startDate, String endDate,int firstIndex) throws InstantiationException, IllegalAccessException {
        List<OAProcesses> oaProcessesList = new ArrayList<>();

        List<WorkflowRequestBase> requestBases =
                workflowRequestBaseMapper.selectWorkflowRequestBaseByWorkflowIdAndPeriod(workflowId,startDate,endDate,firstIndex);
        if (requestBases != null){
            logger.info("workflowRequestBase检索到相关数据：" + requestBases.size() + "件");
        }else{
            logger.info("workflowRequestBase检索结果为null");
            return oaProcessesList;
        }
        for (WorkflowRequestBase requestBase:requestBases) {
            if (relayPoolService.filterOAWorkflowRequest(requestBase) != null){
                oaProcessesList.add(createOAProcesses(requestBase));
            }
        }
        return oaProcessesList;
    }

    private List<OAProcesses> findByRequestIdAndWorkflowId(int requestId, int workflowId,int firstIndex) throws InstantiationException, IllegalAccessException {
        List<OAProcesses> oaProcessesList = new ArrayList<>();
        List<WorkflowRequestBase> requestBases =
                workflowRequestBaseMapper.selectWorkflowRequestBaseByRequestIdAndWorkflowId(requestId,workflowId,firstIndex);
        if (requestBases != null){
            logger.info("workflowRequestBase检索到相关数据：" + requestBases.size() + "件");
        }else{
            logger.info("workflowRequestBase检索结果为null");
            return oaProcessesList;
        }
        for (WorkflowRequestBase requestBase:requestBases) {
            if (relayPoolService.filterOAWorkflowRequest(requestBase) != null){
                oaProcessesList.add(createOAProcesses(requestBase));
            }
        }
        return oaProcessesList;
    }

    private List<OAProcesses> findByWorkflowId(int workflowId,int firstIndex) throws InstantiationException, IllegalAccessException {
        List<OAProcesses> oaProcessesList = new ArrayList<>();
        List<WorkflowRequestBase> requestBases =
                workflowRequestBaseMapper.selectWorkflowRequestBaseByWorkflowId(workflowId,firstIndex);
        if (requestBases != null){
            logger.info("workflowRequestBase检索到相关数据：" + requestBases.size() + "件");
        }else{
            logger.info("workflowRequestBase检索结果为null");
            return oaProcessesList;
        }
        for (WorkflowRequestBase requestBase:requestBases) {
            if (relayPoolService.filterOAWorkflowRequest(requestBase) != null){
                oaProcessesList.add(createOAProcesses(requestBase));
            }
        }
        return oaProcessesList;
    }

    private OAProcesses createOAProcesses(WorkflowRequestBase requestBase) throws InstantiationException, IllegalAccessException {
        List<FormTableMain> formTableMains = findFormTableMainByRequestId(requestBase.getRequestid());
        for (FormTableMain formTableMain:formTableMains) {
            String additional1  = formTableMain.getAdditional1();
            formTableMain.addDocImageFiles(getDocImages(additional1));

            String additional2  = formTableMain.getAdditional2();
            formTableMain.addDocImageFiles(getDocImages(additional2));

            String additional3  = formTableMain.getAdditional3();
            formTableMain.addDocImageFiles(getDocImages(additional3));

            requestBase.addFormTableMain(formTableMain);
        }
        return new OAProcesses(requestBase);
    }

    private List<DocImageFile> getDocImages(String imageCodes){
        List<DocImageFile> docImageFiles = new ArrayList<>();
        if (!StringUtils.isEmpty(imageCodes)){
            String imageCodeSplit [] = imageCodes.split(",");
            for (String imageCode:imageCodeSplit) {
                DocImageFile docImageFile = findDocImageFileByDocId(Integer.parseInt(imageCode));
                if (docImageFile != null){
                    ImageFile imageFile = findImageFileById(docImageFile.getImagefileid());
                    if (imageFile != null){
                        docImageFile.setImageFile(imageFile);
                        docImageFiles.add(docImageFile);
                    }
                }
            }
        }
        return docImageFiles;
    }

    private DocImageFile findDocImageFileByDocId(int docId){
        List<DocImageFile> docImageFiles = docImageFileMapper.selectDocImageFileByDocId(docId);
        if (docImageFiles.size() > 0){
            return docImageFiles.get(0);
        }else{
            return null;
        }
    }

    private ImageFile findImageFileById(int id){
        List<ImageFile> imageFiles = imageFileMapper.selectImageFileById(id);
        if (imageFiles.size() > 0){
            return imageFiles.get(0);
        }else{
            return null;
        }
    }


}