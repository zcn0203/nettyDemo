package com.netty.dubborpc.provider;

import com.netty.dubborpc.publicinterface.HelloService;

public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        System.out.println("收到客户端的消息");
        return "你好客户端，服务端已经收到你发送的消息" + msg;
    }
}
