package com.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class NIOClient {

    public static void main(String[] args) throws IOException {

        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        if (!socketChannel.connect(new InetSocketAddress("127.0.0.1", 6666))) {
            while (!socketChannel.finishConnect()) {
                System.out.println("等待连接中，客户端正在做其他事情");
            }
        }

        ByteBuffer byteBuffer = ByteBuffer.wrap("hello, world!".getBytes());

        int write = socketChannel.write(byteBuffer);
        System.out.println("客户端发送数据成功");

    }
}
