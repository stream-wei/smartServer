package com.stream.netty.jms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * Created by stream on 2017/8/5.
 */
public class ActiveMqSendUtils {
    
    private static final String url = "failover://tcp://192.168.1.78:61616";
    
    private static Connection connection;
    
    private static Session session;
    
    private static Destination sendDestination;
    
    private static MessageProducer producer;
    
    static {
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
    
    public static void sendMessage(String message){
        try {
            sendDestination = session.createQueue("test");
            producer = session.createProducer(sendDestination);
            //持久化,默认是文件
            /**
             * <persistenceAdapter>
             *   <kahaDB directory="${activemq.base}/data/kahadb"/>
             * </persistenceAdapter>
             */
            producer.setDeliveryMode(DeliveryMode.PERSISTENT);
            TextMessage textMessage = session.createTextMessage(message);
            producer.send(textMessage);
//            session.commit();
//            close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void close(){
        try {
            connection.close();
            session.close();
            producer.close();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        sendMessage("test");
    }
}
