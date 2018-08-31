package org.kpkurian.springcloudstreamcamunda;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableProcessApplication
@EnableBinding(Sink.class)
public class SpringCloudStreamCamundaApplication {
	
	private static Log log = LogFactory.getLog(SpringCloudStreamCamundaApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamCamundaApplication.class, args);
	}

	@Autowired
	private CamundaRuntimeServiceSpringAware runtimeServiceWrapper;

	@StreamListener(Sink.INPUT)
	public void onNewMessage(String message) {

		log.info("Received Message ... "+message );
		startWf(message);

	}

	@EventListener
	private void processPostDeploy(PostDeployEvent event) {
		log.info("Camunda Initialized...");
	}
	
	@EventListener
	private void processPostAppContextLoaded(ContextRefreshedEvent contextRefreshedEvent) {
		log.info("Spring App Context Initialized/Refreshed...");
	}
	
	private void startWf(String message) {
		runtimeServiceWrapper.getRuntimeService().startProcessInstanceByKey("Simple_BPMN");
		log.info("started wf for message ..." + message);
	}
	
	@Bean
	public CamundaRuntimeServiceSpringAware camundaRuntimeServiceSpringAware(RuntimeService runtimeService) {
		return new CamundaRuntimeServiceSpringAware(runtimeService);
	}

}
