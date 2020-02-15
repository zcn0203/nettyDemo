package com.netty.netty.InboundAndOutbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class MyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 编码 - 出站
        pipeline.addLast(new MyEncoder());
        // 解码 - 入站
        pipeline.addLast(new MyDecoder());
        // 处理器
        pipeline.addLast(new MyClientHandler());
    }
}
