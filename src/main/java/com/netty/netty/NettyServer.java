package com.netty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) throws InterruptedException {

        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();

        bootstrap.group(bossGroup, workerGroup) // 设置两个线程组
                .channel(NioServerSocketChannel.class)  // 使用NioServerSocket作为服务器通道的实现
                .option(ChannelOption.SO_BACKLOG, 128)  // 设置线程队列的连接个数
                .childOption(ChannelOption.SO_KEEPALIVE, true)  // 保持活动链接状态
                .childHandler(new ChannelInitializer<SocketChannel>() { // 创建一个通道初始化对象
                    // 给pipeline设置处理器
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(null);
                    }
                });// workerGroup的eventLoopGroup对应的管道设置处理器

        System.out.println("服务器启动成功");

        // 绑定一个端口
        ChannelFuture channelFuture = bootstrap.bind(6666).sync();

        // 对关闭通道进行监听
        channelFuture.channel().closeFuture().sync();

    }

}
