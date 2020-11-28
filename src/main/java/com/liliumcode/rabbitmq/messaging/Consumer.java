package com.liliumcode.rabbitmq.messaging;

import com.liliumcode.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;

@Component
public class Consumer {
    private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);

    @PostConstruct
    private void init() {
        listenForMessages();
    }

    private void listenForMessages() {
        try {
            final ConnectionFactory factory = new ConnectionFactory();
            final Connection connection = factory.newConnection();
            final Channel channel = connection.createChannel();

            channel.queueDeclare(RabbitMqUtil.getQueue(), false, false, false, null);
            channel.queueBind(RabbitMqUtil.getQueue(), RabbitMqUtil.getExchange(), RabbitMqUtil.getRoutingKey());

            channel.basicConsume(
                    RabbitMqUtil.getQueue(),
                    true,
                    ((consumerTag, message) -> LOG.info("Got message '{}'", new String(message.getBody(), StandardCharsets.UTF_8))),
                    ((consumerTag, sig) -> LOG.error(sig.getMessage())));
        } catch (final Exception e) {
            LOG.error(e.getMessage(), e);
        }
    }
}
