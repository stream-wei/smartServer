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
        Socket socket = new Socket("127.0.0.1",9898);
        OutputStream outputStream = socket.getOutputStream();
        String msg = "Hello World";
        byte [] f = new byte[]{0,0,0,11};
        outputStream.write(f);
        outputStream.write(msg.getBytes());
        outputStream.close();
        socket.close();
    }
}
