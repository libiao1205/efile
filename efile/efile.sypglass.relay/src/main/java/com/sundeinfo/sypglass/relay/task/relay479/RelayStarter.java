package com.sundeinfo.sypglass.relay.task.relay479;

import com.sundeinfo.sypglass.relay.define.RelayKind;
import com.sundeinfo.core.job.AbstractTaskStarter;
import com.sundeinfo.sypglass.relay.mapper.oa.FormTableMain145Mapper;
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

@Component("RelayStarter479")
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
        relayProcessService.updateProcessStatus(RelayKind.RELAY_479,2);//开始执行，状态更新为执行中
        logger.info(RelayKind.RELAY_479.description() + " 开始执行导入: " + formatter.format(new Date()));
    }

    @Override
    protected void end() {
        if (callBack.getResult() == 0){
            relayProcessService.updateProcessStatus(RelayKind.RELAY_479,1);//执行完毕，状态更新为执行正常
            logger.info(RelayKind.RELAY_479.description() + " 导入流程正常执行完毕");
        }else {
            relayProcessService.updateProcessStatus(RelayKind.RELAY_479,9);//执行完毕，状态更新为执行异常
            logger.info(RelayKind.RELAY_479.description() + " 导入流程执行异常");
        }
    }

    @Component("RelayFetchTask479")
    public class RelayFetchTask extends FetchExecute<RelayFetchTask> implements Task {
        @Autowired
        private FormTableMain145Mapper formTableMain145Mapper;

        @Override
        protected List<FormTableMain> findFormTableMainByRequestId(int requestid){
            return formTableMain145Mapper.selectByRequestId(requestid);
        }
        @Override
        protected RelayKind getRelayKind() {
            return RelayKind.RELAY_479;
        }
    }

    @Component("RelayExtractTask479")
    public class RelayExtractTask extends ExtractExecute<RelayExtractTask> implements Task {
        @Override
        protected RelayKind getRelayKind() {
            return RelayKind.RELAY_479;
        }
    }

    @Component("RelayPostTask479")
    public class RelayPostTask extends PostExecute<RelayPostTask> implements Task {
        @Override
        protected RelayKind getRelayKind() {
            return RelayKind.RELAY_479;
        }
    }
}
