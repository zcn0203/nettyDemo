package com.netty.netty.InboundAndOutbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 解码器 - 入站
        pipeline.addLast(new MyDecoder());
        // 编码器 - 出站
        pipeline.addLast(new MyEncoder());
        // 处理器
        pipeline.addLast(new MyServerHandler());
    }
}
