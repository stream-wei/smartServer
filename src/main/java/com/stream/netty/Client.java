package com.stream.netty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by xi.wei on 2017/7/24.
 */
public class Client {
    
    private static final Logger logger = LoggerFactory.getLogger(Client.class);
    
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("127.0.0.1", 9898);
        OutputStream outputStream = socket.getOutputStream();
    
        String message = "123456";
        byte [] m = message.getBytes("UTF-8");
        byte [] length = new byte[]{(byte)message.length()};
        while (true){
            outputStream.write(0xAA);
            outputStream.write(length);
            outputStream.write(m);
            outputStream.flush();
            Thread.sleep(6000);
        }
    }
}
