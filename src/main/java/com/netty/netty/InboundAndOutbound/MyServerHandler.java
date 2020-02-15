package com.netty.netty.InboundAndOutbound;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MyServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println("服务器接收到消息 : " + msg);
        System.out.println("服务端返回消息");
        ctx.channel().writeAndFlush(7654321L);
    }
}
