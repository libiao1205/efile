package com.sundeinfo.sypglass.relay.task.relay244;

import com.sundeinfo.sypglass.relay.define.RelayKind;
import com.sundeinfo.core.job.AbstractTaskStarter;
import com.sundeinfo.sypglass.relay.mapper.oa.FormTableMain133Mapper;
import com.sundeinfo.sypglass.relay.model.oa.FormTableMain;
import com.sundeinfo.sypglass.relay.service.RelayProcessService;
import com.sundeinfo.core.job.Task;
import com.sundeinfo.sypglass.relay.task.relay.ExtractExecute;
import com.sundeinfo.sypglass.relay.task.relay.FetchExecute;
import com.sundeinfo.sypglass.relay.task.relay.PostExecute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("RelayStarter244")
public class RelayStarter extends AbstractTaskStarter<RelayStarter> {

    @Autowired
    private RelayProcessService relayProcessService;

    @Autowired
    private RelayExtractTask relayExtractTask;

    @Autowired
    private RelayFetchTask relayFetchTask;

    @Autowired
    private RelayPostTask relayPostTask;

    @Override
    protected Task getTask() {
        relayExtractTask.setSuccessor(relayPostTask);
        relayFetchTask.setSuccessor(relayExtractTask);
        return relayFetchTask;
    }

    @Override
    protected void start() {
        relayProcessService.updateProcessStatus(RelayKind.RELAY_244,2);//开始执行，状态更新为执行中
        logger.info(RelayKind.RELAY_244.description() + " 开始执行导入: " + formatter.format(new Date()));
    }

    @Override
    protected void end() {
        if (callBack.getResult() == 0){
            relayProcessService.updateProcessStatus(RelayKind.RELAY_244,1);//执行完毕，状态更新为执行正常
            logger.info(RelayKind.RELAY_244.description() + " 导入流程正常执行完毕");
        }else {
            relayProcessService.updateProcessStatus(RelayKind.RELAY_244,9);//执行完毕，状态更新为执行异常
            logger.info(RelayKind.RELAY_244.description() + " 导入流程执行异常");
        }
    }

    @Component("RelayFetchTask244")
    public class RelayFetchTask extends FetchExecute<RelayFetchTask> implements Task {
        @Autowired
        private FormTableMain133Mapper formTableMain133Mapper;

        @Override
        protected List<FormTableMain> findFormTableMainByRequestId(int requestid){
            return formTableMain133Mapper.selectByRequestId(requestid);
        }
        @Override
        protected RelayKind getRelayKind() {
            return RelayKind.RELAY_244;
        }
    }

    @Component("RelayExtractTask244")
    public class RelayExtractTask extends ExtractExecute<RelayExtractTask> implements Task {
        @Override
        protected RelayKind getRelayKind() {
            return RelayKind.RELAY_244;
        }
    }

    @Component("RelayPostTask244")
    public class RelayPostTask extends PostExecute<RelayPostTask> implements Task {
        @Override
        protected RelayKind getRelayKind() {
            return RelayKind.RELAY_244;
        }
    }
}
