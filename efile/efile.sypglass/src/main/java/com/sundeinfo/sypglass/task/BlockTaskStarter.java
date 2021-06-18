package com.sundeinfo.sypglass.task;

import com.sundeinfo.core.job.CallBack;
import com.sundeinfo.core.job.Task;
import com.sundeinfo.core.job.TaskThread;
import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.utility.StringUtils;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component("BlockTaskStarter")
public class BlockTaskStarter extends AbstractComponent<BlockTaskStarter>{

    //线程池
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    public void execute(Task task) {
        try {
            //线程执行结果
            CallBackResult callBack = new CallBackResult();
            //CountDownLatch threadSignal = new CountDownLatch(1);//线程计数器
            fixedThreadPool.execute(new TaskThread(callBack, null,task,logger));
            //threadSignal.await();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public class CallBackResult implements CallBack {
        int result = 0;
        public void setResult(Object result) {
            if (! StringUtils.isEqualsString("1", result.toString())){
                this.result ++;
            }
        }
        public int getResult(){
            return result;
        }
    }



}