package com.sundeinfo.sypglass.relay.model.oa;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class WorkflowRequestBase implements Serializable {
    private int requestid;

    private int workflowid;

    private String requestname;

    private int creater;

    private String createdate;

    private String createtime;

    private int lastoperator;

    private String lastoperatedate;

    private String lastoperatetime;

    private byte deleted;

    public WorkflowRequestBase(Integer requestid, Integer workflowid, String requestname,Byte deleted,
                               Integer creater, String createdate, String createtime,
                               Integer lastoperator, String lastoperatedate, String lastoperatetime) {
        this.requestid = requestid;
        this.workflowid = workflowid;
        this.requestname = requestname;
        this.deleted = deleted;
        this.creater = creater;
        this.createdate = createdate;
        this.createtime = createtime;
        this.lastoperator = lastoperator;
        this.lastoperatedate = lastoperatedate;
        this.lastoperatetime = lastoperatetime;
        formTableMains = new ArrayList<>();
    }

    private List<FormTableMain> formTableMains;

    public void addFormTableMain(FormTableMain formTableMain){
        formTableMains.add(formTableMain);
    }

}