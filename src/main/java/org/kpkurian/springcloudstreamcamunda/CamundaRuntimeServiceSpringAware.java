package org.kpkurian.springcloudstreamcamunda;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.springframework.context.Lifecycle;
import org.springframework.stereotype.Component;

@Component
public class CamundaRuntimeServiceSpringAware  implements Lifecycle{
	
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
					log.info("Got Interrupted Exception ... ", e);
				}
				log.info("checking again ..."+isRunning);
			}while(!isRunning);
			return this.runtimeService;
		}
	}
	

	@Override
	public void start() {
		log.info("Started ....");
		this.isRunning = true;
	}

	@Override
	public void stop() {
		log.info("Stopped ....");
		this.isRunning = false;
	}

	@Override
	public boolean isRunning() {
		log.info("isRunning ...." + this.isRunning);
		return this.isRunning;
	}

}
