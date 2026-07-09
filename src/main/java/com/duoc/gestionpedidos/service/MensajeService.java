package com.duoc.gestionpedidos.service;

import java.io.IOException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import com.rabbitmq.client.*;

import com.duoc.gestionpedidos.config.RabbitMQConfig;
import com.duoc.gestionpedidos.dto.GuiaDespachoRequestDTO;
import com.duoc.gestionpedidos.dto.GuiaDespachoResponseDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MensajeService {

    @Value("${spring.rabbitmq.host}")
	private String host;

	@Value("${spring.rabbitmq.username}")
	private String username;

	@Value("${spring.rabbitmq.password}")
	private String password;

    private final GuiaDespachoService guiaDespachoService;

    public MensajeService(GuiaDespachoService guiaDespachoService) {
        this.guiaDespachoService = guiaDespachoService;
    }

    public String obtenerUltimoMensaje() {

        String mensaje = null;

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost(host);
        factory.setUsername(username);
        factory.setPassword(password);

        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {

            GetResponse response = channel.basicGet(RabbitMQConfig.MAIN_QUEUE, true);

            if (response != null) {
                mensaje = new String(response.getBody(), "UTF-8");
                System.out.println("Mensaje recibido: " + mensaje);
            } else {
                System.out.println("No hay mensajes en la cola");
            }

        } catch (Exception e) {
            System.out.println("Error al consumir mensaje de RabbitMQ");
            e.printStackTrace();
        }
        return mensaje;
    }

    @RabbitListener(queues = RabbitMQConfig.MAIN_QUEUE, ackMode = "MANUAL")
    public void recibirGuiaDespacho(@Payload GuiaDespachoRequestDTO guiaDespachoRequestDTO, Message mensaje, Channel canal) throws IOException {

        try{ 
            log.info("Mensaje recibido: " +  new String(mensaje.getBody()));
            Thread.sleep(5000);

            GuiaDespachoResponseDTO nuevaGuia = guiaDespachoService.crearGuiaDeDespacho(guiaDespachoRequestDTO);

            if (nuevaGuia != null) {
                log.info("Guia de despacho procesada correctamente con id {}", nuevaGuia.getId());

                canal.basicAck(mensaje.getMessageProperties().getDeliveryTag(), false);
                log.info("Acknowledge OK enviado.");
                return;
            }

            log.info("La guia de despacho recibida no pudo ser procesada");
        } catch (Exception e) {
            log.error("Error al procesar la guia de despacho: {}", e.getMessage());

            canal.basicNack(mensaje.getMessageProperties().getDeliveryTag(), false, false);
            log.info("Acknowledge NO OK enviado.");
        }
        
    }

}