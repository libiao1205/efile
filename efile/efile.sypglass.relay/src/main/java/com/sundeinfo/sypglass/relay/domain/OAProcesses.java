package com.sundeinfo.sypglass.relay.domain;

import com.sundeinfo.core.domain.Entity;
import com.sundeinfo.sypglass.relay.model.oa.WorkflowRequestBase;
import com.sundeinfo.sypglass.relay.model.relay.RelayRequestBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
public class OAProcesses extends Entity implements Serializable {

    private WorkflowRequestBase requestBase;

    public OAProcesses(WorkflowRequestBase requestBase) {
        this.requestBase = requestBase;
    }
}