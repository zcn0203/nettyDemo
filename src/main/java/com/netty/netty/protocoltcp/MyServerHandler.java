package com.netty.netty.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.nio.charset.Charset;
import java.util.UUID;

public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {

    private int count = 0;
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        byte[] content = msg.getContent();
        System.out.println("长度 = " + msg.getLen());
        System.out.println(new String(content, Charset.forName("utf-8")));

        System.out.println("服务器收到的总包数量 = " + (++count));


        MessageProtocol messageProtocol = new MessageProtocol();
        UUID uuid = UUID.randomUUID();
        String contentValue = uuid.toString();
        byte[] bytes = contentValue.getBytes(Charset.forName("utf-8"));
        messageProtocol.setLen(bytes.length);
        messageProtocol.setContent(bytes);

        ctx.writeAndFlush(messageProtocol);

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
