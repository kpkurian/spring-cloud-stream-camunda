package org.kpkurian.springcloudstreamcamunda;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CamundaRuntimeServiceSpringAware {
	
	private static Log log = LogFactory.getLog(CamundaRuntimeServiceSpringAware.class);
	
	private RuntimeService runtimeService;
	private Boolean isRunning = false;
	
	public CamundaRuntimeServiceSpringAware(RuntimeService runtimeService ) {
		this.runtimeService = runtimeService;
	}
	
	public RuntimeService getRuntimeService() {
		log.info("running state ..."+isRunning );
		if(isRunning) {
			return this.runtimeService;
		} else {
			do {
				try {
					log.info("sleeping ...");
					Thread.sleep(2*1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				log.info("checking again ..."+isRunning);
			}while(!isRunning);
			return this.runtimeService;
		}
	}
	
	@EventListener
	private void processPostDeploy(PostDeployEvent event) {
		log.info("Camunda Initialized... CamundaRuntimeServiceSpringAware is in running state");
		this.isRunning = true;
	}

}
