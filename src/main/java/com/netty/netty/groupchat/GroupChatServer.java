package com.netty.netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class GroupChatServer {

    private int port;

    GroupChatServer(int port){
        this.port = port;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

       try{
           ServerBootstrap serverBootstrap = new ServerBootstrap();

           serverBootstrap.group(bossGroup, workerGroup)
                   .channel(NioServerSocketChannel.class)
                   .option(ChannelOption.SO_BACKLOG, 128)
                   .childOption(ChannelOption.SO_KEEPALIVE, true)
                   .childHandler(new ChannelInitializer<SocketChannel>() {
                       @Override
                       protected void initChannel(SocketChannel ch) throws Exception {
                           ch.pipeline().addLast("decoder", new StringDecoder())
                                   .addLast("encoder", new StringEncoder())
                                   .addLast(new GroupChatServerHandler());
                       }
                   });

           ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
           channelFuture.channel().closeFuture().sync();

       }finally {
           bossGroup.shutdownGracefully();
           workerGroup.shutdownGracefully();
       }

    }


    public static void main(String[] args) throws InterruptedException {

        new GroupChatServer(7777).run();

    }

}
