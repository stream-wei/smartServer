package com.stream.netty.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by stream on 2017/8/5.
 */
public class ActiveMqReceiveUtils {
    
    private static final String url = "failover://tcp://192.168.1.78:61616";
    
    private static Session session;
    
    private static Destination receiveDestination;
    
    private static MessageConsumer consumer;
    
    static {
        Connection connection = null;
        ConnectionFactory factory = new ActiveMQConnectionFactory(ActiveMQConnection.DEFAULT_USER,
                ActiveMQConnection.DEFAULT_PASSWORD, url);
        try {
            connection = factory.createConnection();
            connection.start();
            session = connection.createSession(Boolean.FALSE, Session.AUTO_ACKNOWLEDGE);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    
    public static void reciveMessage(){
        try {
            receiveDestination = session.createQueue("test");
            consumer = session.createConsumer(receiveDestination);
//            while (true){
                TextMessage message = (TextMessage) consumer.receive();
                System.out.println(message.getText());
//            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        reciveMessage();
    }
}
