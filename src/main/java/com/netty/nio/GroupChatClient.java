package com.netty.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GroupChatClient {

    private SocketChannel socketChannel;
    private Selector selector;
    private final String IP = "127.0.0.1";
    private final int PORT = 6666;
    private String username;

    public GroupChatClient(){
        try {

            socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress(IP, PORT));
            selector = Selector.open();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_READ);
            username = socketChannel.getLocalAddress().toString().substring(1);
            System.out.println("客户端启动成功" + username);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendInfo(String msg){
        try {
            msg = (username + "say ->" + msg);
            socketChannel.write(ByteBuffer.wrap(msg.getBytes()));
            System.out.println("发送成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void reciveMsg(){
        try {

            int count = selector.select();
            if (count > 0) {
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel)key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        channel.read(buffer);
                        String msg = new String(buffer.array());
                        System.out.println(msg.trim());
                    }

                    iterator.remove();
                }
            } else {
                System.out.println("没有接收到消息");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        final GroupChatClient client = new GroupChatClient();

        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            public void run() {
                while (true) {
                    client.reciveMsg();
                    try {
                        Thread.currentThread().sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String s = scanner.nextLine();
            client.sendInfo(s);
        }

    }

}
