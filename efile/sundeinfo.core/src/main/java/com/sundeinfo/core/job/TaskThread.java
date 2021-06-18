package com.sundeinfo.core.job;

import org.slf4j.Logger;

import java.util.concurrent.CountDownLatch;

import static java.lang.Thread.sleep;


public class TaskThread implements Runnable {

	//返回值
	private CallBack callback;
	
	//线程计数器
	private CountDownLatch threadSignal;

	//执行对象
	private Task task;

	//执行log
	private Logger logger;

    public TaskThread(CallBack callback, CountDownLatch threadSignal, Task task, Logger logger) {
    	super();
    	this.callback = callback;
		this.threadSignal = threadSignal;
		this.task = task;
		this.logger = logger;
	}
    
    @Override
	public synchronized void run() {
		try {
			logger.info("-----------开始执行-----------");
			sleep(1*1000);
			task.execute();
			callback.setResult("1");
			logger.info("-----------执行结果-----------");
		} catch (Exception e) {
			logger.error("批处理执行出现异常："+e.getMessage());
			e.printStackTrace();
			callback.setResult("0");
		}
		//线程计数器
		if (threadSignal != null){
			threadSignal.countDown();
		}
	}

}
