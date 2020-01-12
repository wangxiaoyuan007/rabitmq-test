package com.wxy.rabitmqtest.mq;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("122.51.205.137");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        Connection connection = connectionFactory.newConnection();

        Channel channel = connection.createChannel();

        //声明一个队列
        channel.queueDeclare("test001",true, false, false, null);

        //创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //设置channel
        channel.basicConsume("test001", true, queueingConsumer);

        //获取消息
        QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
        String message = new String(delivery.getBody());
        System.out.println(message);

        channel.close();
        connection.close();
    }
}
