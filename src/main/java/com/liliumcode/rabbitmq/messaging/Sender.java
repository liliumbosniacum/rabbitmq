package com.liliumcode.rabbitmq.messaging;

import com.liliumcode.rabbitmq.util.RabbitMqUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

@Component
public class Sender {
    private static final Logger LOG = LoggerFactory.getLogger(Sender.class);

    @PostConstruct
    private void init() {
        final Scanner scanner = new Scanner(System.in);

        askForMessages(scanner);
    }

    private void sendMessage(final String message) {
        final ConnectionFactory factory = new ConnectionFactory();

        try (final Connection connection = factory.newConnection()) {
            final Channel channel = connection.createChannel();
            channel.exchangeDeclare(RabbitMqUtil.getExchange(), RabbitMqUtil.getType());

            channel.basicPublish(
                    RabbitMqUtil.getExchange(),
                    RabbitMqUtil.getRoutingKey(),
                    false,
                    null,
                    message.getBytes()
            );
        } catch (TimeoutException | IOException e) {
            LOG.error(e.getMessage(), e);
        }
    }

    private void askForMessages(final Scanner scanner) {
        LOG.info("Enter your message: ");
        final String message = scanner.nextLine() + " " + LocalDateTime.now();

        sendMessage(message);

        askForMessages(scanner);
    }
}
