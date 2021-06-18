package com.sundeinfo.core.job;

import com.sundeinfo.core.mvc.component.AbstractComponent;
import com.sundeinfo.core.utility.StringUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractTaskStarter<T> extends AbstractComponent<T> implements Job {

    //线程池
    private ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);

    protected final SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm");

    //线程执行结果
    protected CallBackResult callBack;

    protected abstract Task getTask();

    protected abstract void start();

    protected abstract void end();

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            start();
            callBack = new CallBackResult();
            CountDownLatch threadSignal = new CountDownLatch(1);//线程计数器
            fixedThreadPool.execute(new TaskThread(callBack, threadSignal,getTask(),logger));
            threadSignal.await();
            end();
        } catch (Exception e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }


    public class CallBackResult implements CallBack {
        int result = 0;
        public void setResult(Object result){
            if (! StringUtils.isEqualsString("1", result.toString())){
                this.result ++;
            }
        }
        public int getResult(){
            return result;
        }
    }
}