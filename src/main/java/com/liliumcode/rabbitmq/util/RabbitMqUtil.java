package com.liliumcode.rabbitmq.util;

public final class RabbitMqUtil {
    private static final String EXCHANGE = "our-exc";
    private static final String ROUTING_KEY = "routing-key";
    private static final String TYPE = "direct";
    private static final String QUEUE = "our-queue";

    private RabbitMqUtil() {
    }

    public static String getExchange() {
        return EXCHANGE;
    }

    public static String getType() {
        return TYPE;
    }

    public static String getRoutingKey() {
        return ROUTING_KEY;
    }

    public static String getQueue() {
        return QUEUE;
    }
}
