package br.ldamd.consumer;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class OrderConsumer {

    private final static String QUEUE_NAME = "fila_sorvetes";

    public void iniciar() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqps://llghxpbq:kxDs5HlUGalovw8KvWe5FIDEAeTquxjt@chimpanzee.rmq.cloudamqp.com/llghxpbq");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
//            System.out.println(" [*] Aguardando pedidos de sorvete. Para sair, pressione CTRL+C");

            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                String pedido = new String(delivery.getBody(), StandardCharsets.UTF_8);
//                System.out.println(" [x] Atendente recebeu: '" + pedido + "'");
                try {
                    int tempoPreparo = 2000 + (int) (Math.random() * 3000);
                    Thread.sleep(tempoPreparo);
                    System.out.println(" [x] " + pedido + "' finalizado e pronto para entrega.");
                } catch (InterruptedException e) {
                    System.out.println("Erro ao preparar o pedido: " + e.getMessage());
                    e.printStackTrace();
                }
            };
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {
            });
            while (true) {
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            System.out.println("Erro ao iniciar o consumidor: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
