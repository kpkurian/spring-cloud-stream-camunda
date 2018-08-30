package org.kpkurian.springcloudstreamcamunda;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.camunda.bpm.spring.boot.starter.event.PostDeployEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.event.EventListener;

@SpringBootApplication
@EnableProcessApplication
@EnableBinding(Sink.class)
public class SpringCloudStreamCamundaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringCloudStreamCamundaApplication.class, args);
	}

	@Autowired
	private RuntimeService runtimeService;

	@StreamListener(Sink.INPUT)
	public void onNewMessage(String message) {

		System.out.println("Received ... "+message );
		startWf();

	}

	@EventListener
	private void processPostDeploy(PostDeployEvent event) {
		System.out.println("Camunda Initialized...");
	}
	
	private void startWf() {
		runtimeService.startProcessInstanceByKey("Simple_BPMN");
	}

}
