package com.stream.netty.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by xi.wei on 2017/7/24.
 */
public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(HeartBeatServerHandler.class);
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("heartBeat channelRead...");
        logger.info(ctx.channel().id() + ":" + msg.toString());
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("heartBeat exception",cause);
    }
}
