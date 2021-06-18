package com.sundeinfo.sypglass.relay.service;

import com.sundeinfo.core.mvc.service.AbstractService;
import com.sundeinfo.sypglass.relay.define.RelayKind;
import com.sundeinfo.sypglass.relay.define.RelayDefine;
import com.sundeinfo.sypglass.relay.mapper.relay.ProcessLogMapper;
import com.sundeinfo.sypglass.relay.mapper.relay.ProcessStatusMapper;
import com.sundeinfo.sypglass.relay.model.relay.*;
import com.sundeinfo.sypglass.relay.utility.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service("ProcessStatusService")
public class RelayProcessService extends AbstractService<RelayProcessService,ProcessStatus> {

    @Autowired
    private ConfigService configService;

    @Autowired
    private ProcessStatusMapper processStatusMapper;

    @Autowired
    private ProcessLogMapper processLogMapper;

    public String computeRelayDatetime(RelayKind kind){
        ProcessStatus status = null;
        ProcessStatusExample example = new ProcessStatusExample();
        ProcessStatusExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(kind.value());
        List<ProcessStatus> statuses = processStatusMapper.selectByExample(example);
        if (statuses != null && statuses.size() > 0){
             status = statuses.get(0);
        }
        if (status == null){
            insertProcessStatus(kind);
            return getInitializeConfig(kind).getValue();
        }else{
            switch (status.getStatus()){
                case 0:
                    return getInitializeConfig(kind).getValue();
                case 1:
                    return DateUtils.getShortDateString(status.getPredate());
                case 2:
                case 9:
                    if (status.getPresuccessdate() == null){
                        return getInitializeConfig(kind).getValue();
                    }else{
                        return DateUtils.getShortDateString(status.getPresuccessdate());
                    }
                default:
                    return getInitializeConfig(kind).getValue();
            }
        }
    }

    private Config getInitializeConfig(RelayKind code){
        String[] setting = null;
        switch (code){
            case RELAY_516:
                setting = RelayDefine.CONFIG_RELAY516_INITIALIZE.split(",");
                break;
            case RELAY_510:
                setting = RelayDefine.CONFIG_RELAY510_INITIALIZE.split(",");
                break;
            case RELAY_244:
                setting = RelayDefine.CONFIG_RELAY244_INITIALIZE.split(",");
                break;
            case RELAY_64:
                setting = RelayDefine.CONFIG_RELAY64_INITIALIZE.split(",");
                break;
            case RELAY_84:
                setting = RelayDefine.CONFIG_RELAY84_INITIALIZE.split(",");
                break;
            case RELAY_349:
                setting = RelayDefine.CONFIG_RELAY349_INITIALIZE.split(",");
                break;
            case RELAY_455:
                setting = RelayDefine.CONFIG_RELAY455_INITIALIZE.split(",");
                break;
            case RELAY_206:
                setting = RelayDefine.CONFIG_RELAY206_INITIALIZE.split(",");
                break;
            case RELAY_479:
                setting = RelayDefine.CONFIG_RELAY479_INITIALIZE.split(",");
                break;
            case RELAY_507:
                setting = RelayDefine.CONFIG_RELAY507_INITIALIZE.split(",");
                break;
            case RELAY_210:
                setting = RelayDefine.CONFIG_RELAY210_INITIALIZE.split(",");
                break;
            case RELAY_220:
                setting = RelayDefine.CONFIG_RELAY220_INITIALIZE.split(",");
                break;
            case RELAY_302:
                setting = RelayDefine.CONFIG_RELAY302_INITIALIZE.split(",");
                break;
            case RELAY_524:
                setting = RelayDefine.CONFIG_RELAY524_INITIALIZE.split(",");
                break;
            case RELAY_276:
                setting = RelayDefine.CONFIG_RELAY276_INITIALIZE.split(",");
                break;
            case RELAY_449:
                setting = RelayDefine.CONFIG_RELAY449_INITIALIZE.split(",");
                break;
            case RELAY_35:
                setting = RelayDefine.CONFIG_RELAY35_INITIALIZE.split(",");
        }
        return configService.findByNameAndCodeAndSeq(setting[0],setting[1],Byte.parseByte(setting[2]));
    }

    public void insertProcessLog(String status,String info){
        ProcessLog log = new ProcessLog();
        log.setProcessdate(new Date());
        log.setStatus(status);
        log.setInfo(info);
        processLogMapper.insert(log);
    }

    private void insertProcessStatus(RelayKind kind){
        ProcessStatus processStatus = new ProcessStatus();
        processStatus.setCode(kind.value());
        processStatus.setStatus((byte) 0);
        processStatusMapper.insert(processStatus);
    }

    public void updateProcessStatus(RelayKind kind, int status){
        ProcessStatus processStatus = new ProcessStatus();
        processStatus.setPredate(new Date());
        processStatus.setStatus((byte)status);
        if (status == 1){
            processStatus.setPresuccessdate(new Date());
        }
        ProcessStatusExample example = new ProcessStatusExample();
        ProcessStatusExample.Criteria criteria = example.createCriteria();
        criteria.andCodeEqualTo(kind.value());
        processStatusMapper.updateByExampleSelective(processStatus,example);
    }
}
