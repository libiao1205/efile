package com.sundeinfo.sypglass.relay.mapper.oa;

import com.sundeinfo.sypglass.relay.model.oa.WorkflowRequestBase;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface WorkflowRequestBaseMapper {

    List<WorkflowRequestBase> selectWorkflowRequestBaseByRequestIdAndWorkflowId(
            @Param("requestid") int requestId ,@Param("workflowid") int workflowId,@Param("startindex") int startindex);

    List<WorkflowRequestBase> selectWorkflowRequestBaseByWorkflowId(
            @Param("workflowid") int workflowId,@Param("startindex") int startindex);

    int countWorkflowRequestBaseByWorkflowIdAndPeriod(
            @Param("workflowid") int workflowId,@Param("sdate") String startDate,@Param("edate") String endDate);

    List<WorkflowRequestBase> selectWorkflowRequestBaseByWorkflowIdAndPeriod(
            @Param("workflowid") int workflowId,@Param("sdate") String startDate,@Param("edate") String endDate,@Param("startindex") int startindex);
}