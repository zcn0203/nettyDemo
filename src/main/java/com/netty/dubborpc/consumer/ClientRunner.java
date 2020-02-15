package com.netty.dubborpc.consumer;

import com.netty.dubborpc.netty.NettyClient;
import com.netty.dubborpc.publicinterface.HelloService;

public class ClientRunner {
    public static void main(String[] args) {

        NettyClient nettyClient = new NettyClient();
        HelloService helloService = (HelloService) nettyClient.getBean(HelloService.class, "HelloService#hello#");
        String result = helloService.hello("哈哈哈哈");
        System.out.println("客户端调用的结果 = " + result);

    }
}
