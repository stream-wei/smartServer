package com.stream.netty.handle;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by stream on 2017/6/24.
 */
public class BeatServerHandler extends ChannelInboundHandlerAdapter {
    
    private static final Logger logger = LoggerFactory.getLogger(BeatServerHandler.class);
    
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("新接入客户端");
        System.out.println(ctx.channel().id());
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端关闭");
        System.out.println(ctx.channel().id());
    }
    
    int i = 0;
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        logger.info("server userEventTriggered");
        if (evt instanceof IdleStateEvent){
            logger.error("服务端监听到读操作超时");
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE){
                logger.error("没有收到客户端信息");
                i++;
                if (i == 2){
                    System.out.println(ctx.channel().id());
                    ctx.channel().close();
                }
            }
        } else {
            logger.info("服务端监听到超时操作");
            super.userEventTriggered(ctx,evt);
        }
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        logger.info("服务端收到报文message={}",msg);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
        System.out.println(byteBuf.readByte());
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("服务端异常",cause);
        ctx.close();
    }
}
