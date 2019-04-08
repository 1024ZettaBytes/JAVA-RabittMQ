/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorrabbit;

/**
 *
 * @author ed000
 */
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import java.io.IOException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServidorRabbit {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        //Cola en la que se leerán los mensajes
        String nombreCola = "cola";
        //Dirección del host en la que se encuentra la cola
        String host = "localhost";
        //Por default el puerto es 5672

        // Se crea el factory y se establece el host al que se conectará
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");

        // Se establece la conexión y se crea el canal
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        // Se establece la cola para leer los mensajes
        channel.queueDeclare(nombreCola, false, false, false, null);
        System.out.println("Esperando por mensajes...");
        // Se establecen las acciones a realizar una vez se reciba o se lea un mensaje
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Mensaje recibido: '" + message + "'");
        };
        channel.basicConsume(nombreCola, true, deliverCallback, consumerTag -> {
        });

    }

}
