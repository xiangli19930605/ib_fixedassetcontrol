package com.idealbank.ib_secretassetcontrol.Netty;

import android.os.Handler;

import com.idealbank.ib_secretassetcontrol.Netty.web.MyWebSocketChannelHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class FileServer {
    private static Handler handler;
    ChannelFuture f;

    private static class Holder {
        private static final FileServer HANDLER = new FileServer(handler);
    }

    public static FileServer getInstance() {
        return Holder.HANDLER;
    }

    public FileServer(Handler handler) {
        FileServer.handler = handler;
    }

    public void start() {
        new WebServerThread().start();
    }

    class WebServerThread extends Thread {
        @Override
        public void run() {

                EventLoopGroup bossGroup = new NioEventLoopGroup();
                EventLoopGroup workGroup = new NioEventLoopGroup();
                try {
                    ServerBootstrap b = new ServerBootstrap();
                    b.group(bossGroup, workGroup);
                    b.channel(NioServerSocketChannel.class);
                    b.childHandler(new MyWebSocketChannelHandler());
                    System.out.println("服务端开启等待客户端连接....");
                    Channel ch = b.bind(8080).sync().channel();
                    ch.closeFuture().sync();

                } catch (Exception e) {
                    e.printStackTrace();
                }finally{
                    //优雅的退出程序
                    bossGroup.shutdownGracefully();
                    workGroup.shutdownGracefully();
                }
        }
    }
}
