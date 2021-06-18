package com.sundeinfo.sypglass.relay.service;


import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.core.utility.ConvertUtils;
import com.sundeinfo.sypglass.relay.define.RelayStatus;
import com.sundeinfo.sypglass.relay.domain.OAProcesses;
import com.sundeinfo.sypglass.relay.domain.RelayProcess;
import com.sundeinfo.sypglass.relay.domain.RelayProcessInfo;
import com.sundeinfo.sypglass.relay.mapper.relay.RelayProcessPoolMapper;
import com.sundeinfo.sypglass.relay.mapper.relay.RelayRequestBaseMapper;
import com.sundeinfo.sypglass.relay.model.oa.WorkflowRequestBase;
import com.sundeinfo.sypglass.relay.model.relay.RelayProcessPool;
import com.sundeinfo.sypglass.relay.model.relay.RelayProcessPoolExample;
import com.sundeinfo.sypglass.relay.model.relay.RelayRequestBase;
import com.sundeinfo.sypglass.relay.model.relay.RelayRequestBaseExample;
import com.sundeinfo.sypglass.relay.utility.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("RelayPoolService")
public class RelayPoolService extends AbstractService<RelayPoolService,RelayProcess> {

    @Autowired
    private RelayProcessPoolMapper relayProcessPoolMapper;

    @Autowired
    private RelayRequestBaseMapper relayRequestBaseMapper;

    public List<RelayProcess> findByWorkflow(int id,List<Byte> status) throws InstantiationException, IllegalAccessException {
        RelayProcessPoolExample example = new RelayProcessPoolExample();
        RelayProcessPoolExample.Criteria criteria = example.createCriteria();
        criteria.andWorkflowidEqualTo(id);
        criteria.andRelaystatusIn(status);
        List<RelayProcessPool> relayProcessPoolList = relayProcessPoolMapper.selectByExample(example);
        return ConvertUtils.convertList(relayProcessPoolList,RelayProcess.class);
    }

    public List<RelayProcess> findByFormCode(String code,byte status) throws InstantiationException, IllegalAccessException {
        RelayProcessPoolExample example = new RelayProcessPoolExample();
        RelayProcessPoolExample.Criteria criteria = example.createCriteria();
        criteria.andFormcodeEqualTo(code);
        criteria.andRelaystatusEqualTo(status);
        List<RelayProcessPool> relayProcessPoolList = relayProcessPoolMapper.selectByExample(example);
        return ConvertUtils.convertList(relayProcessPoolList,RelayProcess.class);
    }

    public WorkflowRequestBase filterOAWorkflowRequest(WorkflowRequestBase requestBase){
        RelayRequestBaseExample example = new RelayRequestBaseExample();
        RelayRequestBaseExample.Criteria criteria = example.createCriteria();
        criteria.andRequestidEqualTo((long)requestBase.getRequestid());

        List<RelayRequestBase> relayRequestBases = relayRequestBaseMapper.selectByExample(example);

        if (relayRequestBases == null || relayRequestBases.size() == 0){
            return requestBase;
        }
        RelayRequestBase last = relayRequestBases.get(relayRequestBases.size() - 1);
        if(last.getLastoperatedate().equals(requestBase.getLastoperatedate()) &&
           last.getLastoperatetime().equals(requestBase.getLastoperatetime()) &&
           last.getDeleted().equals(requestBase.getDeleted())){
            return null;
        }
        return  requestBase;
    }

    @Transactional
    public int saveAll(List<OAProcesses> oaProcessesList,List<RelayProcess> relayProcesses) throws InstantiationException, IllegalAccessException {
        for (RelayProcess relayProcess:relayProcesses) {
            int result = saveRelayProcess(relayProcess);
            if (result == 0) return 0;
        }
        for (OAProcesses processes : oaProcessesList ){
            int result = saveRelayRequestBase(processes);
            if (result == 0) return 0;
        }
        return 1;
    }

    private int saveRelayRequestBase(OAProcesses oa) throws InstantiationException, IllegalAccessException {
        RelayRequestBase request = new RelayRequestBase();
        request.setRequestid((long)oa.getRequestBase().getRequestid());
        request.setWorkflowid((long)oa.getRequestBase().getWorkflowid());
        request.setRequestname(oa.getRequestBase().getRequestname());
        request.setCreater(oa.getRequestBase().getCreater());
        request.setCreatedate(oa.getRequestBase().getCreatedate());
        request.setCreatetime(oa.getRequestBase().getCreatetime());
        request.setLastoperator(oa.getRequestBase().getLastoperator());
        request.setLastoperatedate(oa.getRequestBase().getLastoperatedate());
        request.setLastoperatetime(oa.getRequestBase().getLastoperatetime());
        request.setDeleted(oa.getRequestBase().getDeleted());
        request.setRelaydate(DateUtils.getTodayShort());
        request.setRelaystatus((byte)1);
        return relayRequestBaseMapper.insert(request);
    }

    private int saveRelayProcess(RelayProcess relayProcess) throws InstantiationException, IllegalAccessException {
        RelayProcessPool input = ConvertUtils.convert(relayProcess,RelayProcessPool.class);
        return relayProcessPoolMapper.insert(input);
    }

    public int updateProcessInfo(RelayProcessInfo relayProcessInfo){
        RelayProcessPoolExample example = new RelayProcessPoolExample();
        RelayProcessPoolExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(relayProcessInfo.getId());

        RelayProcessPool process = new RelayProcessPool();
        process.setRelaystatus(relayProcessInfo.getRelayStatus());
        process.setRelayfilepath(relayProcessInfo.getRelayFilePath());
        process.setRelayinfo(relayProcessInfo.getRelayInfo());

        return relayProcessPoolMapper.updateByExampleSelective(process,example);
    }

//    public int updateStatus(long id,RelayStatus status,String path,String info){
//        RelayProcessPoolExample example = new RelayProcessPoolExample();
//        RelayProcessPoolExample.Criteria criteria = example.createCriteria();
//        criteria.andIdEqualTo(id);
//
//        RelayProcessPool process = new RelayProcessPool();
//        process.setRelaystatus((byte)status.code());
//        process.setRelayfilepath(path);
//        process.setRelayinfo(info);
//
//        return relayProcessPoolMapper.updateByExampleSelective(process,example);
//    }

}