package com.gxecard.customerservice.Scheduled;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.gxecard.customerservice.Scheduled.task.AssistAcountTask;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class MyScheduled {

	private AssistAcountTask task;
	
	@Autowired
	public MyScheduled(AssistAcountTask task) {
		super();
		this.task = task;
	}

	/**
	 * 生成辅助对账文件，并存到ftp服务器
	 */
	 @Scheduled(cron="0 0 1 * * ?")  
	public void createAssistAcountFile(){
		//log.info("每天凌晨1点触发");
		
		new Thread(task).start();
		
	}
}
