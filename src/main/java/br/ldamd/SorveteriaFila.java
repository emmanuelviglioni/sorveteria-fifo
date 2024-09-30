package br.ldamd;

import br.ldamd.consumer.OrderConsumer;
import br.ldamd.producer.OrderProducer;

public class SorveteriaFila {
    public static void main(String[] args) {

        new Thread(() -> {
            try {
                OrderProducer.main(new String[]{});
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(() -> {
            try {
                OrderConsumer consumer = new OrderConsumer();
                consumer.iniciar();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
