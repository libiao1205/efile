package com.sundeinfo.sypglass.relay.task;

import com.sundeinfo.sypglass.relay.define.RelayStatus;
import com.sundeinfo.sypglass.relay.domain.OAProcesses;
import com.sundeinfo.sypglass.relay.domain.RelayProcess;
import com.sundeinfo.sypglass.relay.model.oa.DocImageFile;
import com.sundeinfo.sypglass.relay.model.oa.FormTableMain;
import com.sundeinfo.sypglass.relay.model.oa.ImageFile;
import com.sundeinfo.sypglass.relay.model.oa.WorkflowRequestBase;
import com.sundeinfo.sypglass.relay.utility.DateUtils;

import java.util.ArrayList;
import java.util.List;

public class ProcessDataConverter {

    public static List<RelayProcess> createRelayInput(List<OAProcesses> processes,String formCode){
        List<RelayProcess> inputs = new ArrayList<>();

        for (OAProcesses processe:processes) {
            WorkflowRequestBase workflowRequestBase = processe.getRequestBase();
            for (FormTableMain formTableMain:workflowRequestBase.getFormTableMains()) {
                for (DocImageFile docImageFile:formTableMain.getDocImageFiles()) {
                    ImageFile imageFile = docImageFile.getImageFile();
                    RelayProcess inputPool = createRelayProcess(workflowRequestBase,formTableMain,docImageFile,imageFile,formCode);
                    inputs.add(inputPool);
                }
            }
        }
        return inputs;
    }

    private static RelayProcess createRelayProcess(WorkflowRequestBase workflowRequestBase,
                                            FormTableMain formTableMain,
                                            DocImageFile docImageFile,
                                            ImageFile imageFile,
                                            String formCode){
        RelayProcess relayProcess = new RelayProcess();
        relayProcess.setRequestId(workflowRequestBase.getRequestid());
        relayProcess.setWorkflowId(workflowRequestBase.getWorkflowid());
        relayProcess.setRequestName(workflowRequestBase.getRequestname());
        relayProcess.setCreater(workflowRequestBase.getCreater() + "");
        relayProcess.setCreateDate(workflowRequestBase.getCreatedate());
        relayProcess.setCreateTime(workflowRequestBase.getCreatetime());
        relayProcess.setLastOperator(workflowRequestBase.getLastoperator() + "");
        relayProcess.setLastOperateDate(workflowRequestBase.getLastoperatedate());
        relayProcess.setLastOperateTime(workflowRequestBase.getLastoperatetime());
        relayProcess.setDeleted(workflowRequestBase.getDeleted());
        relayProcess.setFormCode(formCode);
        relayProcess.setFormId(formTableMain.getId());
        relayProcess.setRequestDate(formTableMain.getRequestDate());
        relayProcess.setComp(formTableMain.getComp());
        relayProcess.setDept(formTableMain.getDept());
        relayProcess.setLeader(formTableMain.getLeader());
        relayProcess.setAdditional1(formTableMain.getAdditional1());
        relayProcess.setAdditional2(formTableMain.getAdditional2());
        relayProcess.setAdditional3(formTableMain.getAdditional3());
        relayProcess.setDocImageId(docImageFile.getId());
        relayProcess.setDocId(docImageFile.getDocid());
        relayProcess.setImageFileId(docImageFile.getImagefileid());
        relayProcess.setImageFileName(docImageFile.getImagefilename());
        relayProcess.setImageFileType(imageFile.getImagefiletype());
        relayProcess.setVersionId(docImageFile.getVersionid());
        relayProcess.setFileRealPath(imageFile.getFilerealpath());
        relayProcess.setIsZip(imageFile.getIszip());
        relayProcess.setFileSize(imageFile.getFilesize());
        relayProcess.setRelayDate(DateUtils.getTodayShort());
        relayProcess.setRelayStatus((byte)RelayStatus.RELAY_INITIALISE.code());
        return relayProcess;
    }


}
