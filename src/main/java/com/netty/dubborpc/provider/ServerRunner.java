package com.netty.dubborpc.provider;

import com.netty.dubborpc.netty.NettyServer;

public class ServerRunner {

    public static void main(String[] args) {
        NettyServer.startServer("localhost", 7777);
    }

}
