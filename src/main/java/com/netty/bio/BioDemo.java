package com.netty.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioDemo {

    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6666);

        System.out.println("服务器已启动");

        ExecutorService executorService = Executors.newCachedThreadPool();

        while (true) {
            final Socket socket = serverSocket.accept();
            System.out.println("接收到一个连接");
            executorService.execute(new Runnable() {
                public void run() {
                    handler(socket);
                }
            });
        }


    }

    public static void handler(Socket socket) {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] bytes = new byte[1024];
            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println(new String(bytes, 0, read));
                }else{
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                System.out.println("关闭客户端连接");
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
