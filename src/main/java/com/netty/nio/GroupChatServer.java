package com.netty.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {

    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private static final int PORT = 6666;

    public GroupChatServer(){
        try {
            serverSocketChannel = ServerSocketChannel.open();
            selector = Selector.open();
            serverSocketChannel.socket().bind(new InetSocketAddress(PORT));
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen(){
        try {

            while(true){
                int count = selector.select();
                if(count > 0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey key = iterator.next();
                        if (key.isAcceptable()) {
                            SocketChannel channel = serverSocketChannel.accept();
                            channel.configureBlocking(false);
                            channel.register(selector, SelectionKey.OP_READ);
                            System.out.println(channel.getRemoteAddress()+"加入群聊");
                        }
                        if (key.isReadable()) {
                            // 读取
                            System.out.println("服务端获取到数据");
                            readData(key);
                        }
                        iterator.remove();
                    }
                }else{
                    // System.out.println("等待客户端连接中，等待");
                    continue;
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
    }

    // 读取客户端消息
    private void readData(SelectionKey key){
        SocketChannel channel = null;
        try{
            channel = (SocketChannel)key.channel();
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = channel.read(byteBuffer);
            if (read > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println(channel.getRemoteAddress() + ":" + msg);
                // remote msg
                transferMsg(msg, channel);
            }

        }catch (Exception e){
            try {
                System.out.println(channel.getRemoteAddress() + "离线");
                key.cancel();
                channel.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    // 转发消息到其他客户端 排除自己
    private void transferMsg(String msg, SocketChannel self) throws IOException {
        Set<SelectionKey> keys = selector.keys();
        for (SelectionKey key : keys) {
            Channel channel = key.channel();
            if (channel instanceof SocketChannel && channel != self) {
               SocketChannel dest = (SocketChannel)channel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        System.out.println("服务端已启动");
        groupChatServer.listen();
    }

}
