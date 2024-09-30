package br.ldamd.producer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.Random;

public class OrderProducer {

    private final static String QUEUE_NAME = "fila_sorvetes";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://llghxpbq:kxDs5HlUGalovw8KvWe5FIDEAeTquxjt@chimpanzee.rmq.cloudamqp.com/llghxpbq");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);

            String[] sabores = {"Chocolate", "Baunilha", "Morango", "Menta", "Café"};
            Random random = new Random();

            for (int i = 0; i < 5; i++) {
                String sabor = sabores[random.nextInt(sabores.length)];
                String pedido = "Pedido de sorvete de " + sabor;
                channel.basicPublish("", QUEUE_NAME, null, pedido.getBytes(StandardCharsets.UTF_8));
                System.out.println( pedido);
                Thread.sleep(500);
            }
        }
    }
}
