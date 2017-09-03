package com.stream.netty.handle;

import com.stream.netty.db.DBUtils;
import com.stream.netty.db.entity.MacRecord;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
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
        logger.info("新接入客户端,sessionId={}",ctx.channel().id().asLongText());
    }
    
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        logger.info("客户端关闭,sessionId={}",ctx.channel().id().asLongText());
    }
    
    int i = 0;
    
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent){
            logger.error("服务端监听到读操作超时,sessionId={}",ctx.channel().id().asLongText());
            IdleStateEvent idleStateEvent = (IdleStateEvent) evt;
            if (idleStateEvent.state() == IdleState.READER_IDLE){
                logger.error("没有收到客户端信息,sessionId={}",ctx.channel().id().asLongText());
                i++;
                if (i == 2){
                    logger.error("发送报警信息,sessionId={}",ctx.channel().id().asLongText());
                    ctx.channel().close();
                }
            }
        } else {
            logger.info("服务端监听到超时操作,sessionId={}",ctx.channel().id().asLongText());
            super.userEventTriggered(ctx,evt);
        }
    }
    
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        byte [] macIdBytes = ByteBufUtil.getBytes(byteBuf,0,3);
        byte [] imeiBytes = ByteBufUtil.getBytes(byteBuf,3,3);
        MacRecord macRecord = new MacRecord();
        macRecord.setMacId(new String(macIdBytes));
        macRecord.setImei(new String(imeiBytes));
        macRecord.setSessionId(ctx.channel().id().asLongText());
        System.out.println(macRecord);
//        DBUtils.insert(macRecord);
    }
    
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("服务端异常",cause);
        ctx.close();
    }
}
