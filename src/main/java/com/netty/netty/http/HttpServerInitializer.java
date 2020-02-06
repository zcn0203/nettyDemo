package com.netty.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        // 添加一个http解码器处理类
        ch.pipeline().addLast("MyHttpServerCodec", new HttpServerCodec())
                .addLast("TestHeepHandler", new HttpServerHandler());
    }
}
