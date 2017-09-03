package com.stream.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.Socket;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by xi.wei on 2017/7/24.
 */
public class Client {
    
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    
    public static void main(String[] args) {
//        asyn();
        connect();
    }
    
    public static void asyn() {
        CountDownLatch begin = new CountDownLatch(1);
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        for (int i = 0; i < 1; i++) {
            final int finalI = i;
            Runnable runnable = new Runnable() {
                public void run() {
                    connect();
                }
            };
            executorService.submit(runnable);
        }
        begin.countDown();
        executorService.shutdown();
    }
    
    public static void connect() {
        try {
            Socket socket = new Socket("192.168.137.129", 9898);
            OutputStream outputStream = socket.getOutputStream();
            
            String message = "123456";
            byte[] m = message.getBytes("UTF-8");
            byte[] length = new byte[]{(byte) message.length()};
            while (true){
                outputStream.write(0xAA);
                outputStream.write(length);
                outputStream.write(m);
                outputStream.flush();
                logger.info("trans:" + Arrays.toString(m));
                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
