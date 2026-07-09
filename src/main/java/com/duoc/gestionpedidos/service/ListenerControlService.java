package com.duoc.gestionpedidos.service;

import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListenerControlService {

	@Autowired
	private RabbitListenerEndpointRegistry registry;

	public void pausarListener(String id) {

		MessageListenerContainer container = registry.getListenerContainer(id);
		if (container != null && container.isRunning()) {
			container.stop();
			System.out.println("Listener pausado: " + id);
		}
	}

	public void reanudarListener(String id) {

		MessageListenerContainer container = registry.getListenerContainer(id);
		if (container != null && !container.isRunning()) {
			container.start();
			System.out.println("Listener reanudado: " + id);
		}
	}

	public boolean isListenerRunning(String id) {

		MessageListenerContainer container = registry.getListenerContainer(id);
		return container != null && container.isRunning();
	}
}