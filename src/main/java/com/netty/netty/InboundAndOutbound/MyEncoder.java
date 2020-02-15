package com.netty.netty.InboundAndOutbound;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

public class MyEncoder extends MessageToByteEncoder<Long> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("编码");
        System.out.println("message = " + msg);
        out.writeLong(msg);


    }
}
