package com.idealbank.ib_secretassetcontrol.Netty.web;

import android.os.Looper;

import com.idealbank.ib_secretassetcontrol.Netty.ChannelMap;
import com.idealbank.ib_secretassetcontrol.Netty.bean.Message;
import com.idealbank.ib_secretassetcontrol.Netty.bean.MsgType;
import com.idealbank.ib_secretassetcontrol.Netty.bean.Response;
import com.idealbank.ib_secretassetcontrol.app.DbManager;
import com.idealbank.ib_secretassetcontrol.bean.ReceiveId;
import com.idealbank.ib_secretassetcontrol.bean.Upload;

import java.util.Date;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;
import me.jessyan.armscomponent.commonres.dialog.AppDialog;
import me.jessyan.armscomponent.commonres.dialog.DialogType;
import me.jessyan.armscomponent.commonsdk.app.MyApplication;
import me.jessyan.armscomponent.commonsdk.bean.Event;
import me.jessyan.armscomponent.commonsdk.bean.Historyrecord.TaskBean;
import me.jessyan.armscomponent.commonsdk.core.EventBusTags;
import me.jessyan.armscomponent.commonsdk.utils.EventBusUtils;
import me.jessyan.armscomponent.commonsdk.utils.GsonUtil;

import static me.jessyan.armscomponent.commonsdk.utils.ToastUtil.showToast;

/**
 * 接收/处理/响应客户端websocket请求的核心业务处理类
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketServerHandshaker handshaker;
    public static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final String WEB_SOCKET_URL = "ws://localhost:8000/websocket";


    //服务端处理客户端websocket请求的核心方法
    @Override
    protected void channelRead0(ChannelHandlerContext context, Object msg) throws Exception {
        //处理客户端向服务端发起http握手请求的业务
        if (msg instanceof FullHttpRequest) {
            handHttpRequest(context, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) { //处理websocket连接业务
            handWebsocketFrame(context, (WebSocketFrame) msg);
        }
    }
//    @Override
//    protected void messageReceived(ChannelHandlerContext context, Object msg) throws Exception {
//        //处理客户端向服务端发起http握手请求的业务
//        if (msg instanceof FullHttpRequest) {
//            handHttpRequest(context,  (FullHttpRequest)msg);
//        }else if (msg instanceof WebSocketFrame) { //处理websocket连接业务
//            handWebsocketFrame(context, (WebSocketFrame)msg);
//        }
//    }

    // 处理客户端与服务端之前的websocket业务
    private void handWebsocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        //判断是否是关闭websocket的指令
        if (frame instanceof CloseWebSocketFrame) {
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
        }
        //判断是否是ping消息
        if (frame instanceof PingWebSocketFrame) {
            System.out.println("ping消息");
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        //判断是否是二进制消息，如果是二进制消息，抛出异常
//        if( ! (frame instanceof TextWebSocketFrame) ){
//            System.out.println("目前我们不支持二进制消息");
//            throw new RuntimeException("【"+this.getClass().getName()+"】不支持消息");
//        }
        // endregion
        // region 二进制消息 此处使用了MessagePack编解码方式
        if (frame instanceof BinaryWebSocketFrame) {
            BinaryWebSocketFrame binaryWebSocketFrame = (BinaryWebSocketFrame) frame;
            ByteBuf content = binaryWebSocketFrame.content();
            System.out.println("├ [二进制数据]:{}");
            final int length = content.readableBytes();
            final byte[] array = new byte[length];
            content.getBytes(content.readerIndex(), array, 0, length);

//            MessagePack messagePack = new MessagePack();
//            WebSocketMessageEntity webSocketMessageEntity = messagePack.read(array, WebSocketMessageEntity.class);
//            LOGGER.info("├ [解码数据]: {}", webSocketMessageEntity);
//            WebSocketUsers.sendMessageToUser(webSocketMessageEntity.getAcceptName(), webSocketMessageEntity.getContent());
        }
        // region 纯文本消息
        if (frame instanceof TextWebSocketFrame) {
            //返回应答消息
            //获取客户端向服务端发送的消息
            String request = ((TextWebSocketFrame) frame).text();
            System.out.println("服务端收到客户端的消息====>>>" + request);
            System.out.println("服务端收到客户端的消息====>>>" + GsonUtil.GsonString(request));
            EventBusUtils.sendEvent(new Event(request));
            TextWebSocketFrame tws = new TextWebSocketFrame(new Date().toString() + ctx.channel().id() + " ===>>> " + request);
            //服务端向每个连接上来的客户端群发消息
//            NettyConfig.group.writeAndFlush(tws);


            Response response = GsonUtil.GsonToBean(request, Response.class);

            switch (response.getType()) {
                case DOWNLOAD:
                    //下载盘点任务
                    Upload upload = GsonUtil.GsonToBean(GsonUtil.GsonString(response.getData()), Upload.class);

                    //判断数据库中是否有该任务
                    if( new DbManager().queryTaskBeanWhereID(upload.getCheck().getId())!=null){
//                        new Thread() {
//                            @Override
//                            public void run() {
//                                Looper.prepare();
//                                new AppDialog(MyApplication.getContext(), DialogType.DEFAULT).setTitle("已存在该任务，是否覆盖？")
//                                        .setLeftButton("取消", new AppDialog.OnButtonClickListener() {
//                                            @Override
//                                            public void onClick(String val) {
//                                            }
//                                        })
//                                        .setRightButton("确定", new AppDialog.OnButtonClickListener() {
//                                            @Override
//                                            public void onClick(String val) {
//                                                TaskBean taskBean= new DbManager().queryTaskBeanWhereID(upload.getCheck().getId());
//                                                //先删除
//                                                new DbManager().delTaskBeanWhereId(taskBean.getNumid());
//                                                new DbManager().delAssetsBeanWhereId(taskBean.getId()
//                                                );
//                                                //先插入任务，再插入资产
//                                                new DbManager().insertTaskBean(upload.getCheck());
//                                                new DbManager().insertInTxAssetsBean(upload.getCheckAssets());
//                                                EventBusUtils.sendEvent(new Event(""), EventBusTags.REFRESH);
//                                            }
//                                        })
//                                        .show();
//                                Looper.loop();// 进入loop中的循环，查看消息队列
//                            }
//                        }.start();

                        TaskBean taskBean= new DbManager().queryTaskBeanWhereID(upload.getCheck().getId());
                        //先删除
                        new DbManager().delTaskBeanWhereId(taskBean.getNumid());
                        new DbManager().delAssetsBeanWhereId(taskBean.getId());
                        //先插入任务，再插入资产
                        new DbManager().insertTaskBean(upload.getCheck());
                        new DbManager().insertInTxAssetsBean(upload.getCheckAssets());
                        EventBusUtils.sendEvent(new Event(""), EventBusTags.REFRESH);
                    }else{
                        //先插入任务，再插入
                        TaskBean taskBean=upload.getCheck();
                        new DbManager().insertTaskBean(upload.getCheck());
                        new DbManager().insertInTxAssetsBean(upload.getCheckAssets());
                        EventBusUtils.sendEvent(new Event(""), EventBusTags.REFRESH);
                    }

                    break;
                case UPLOADDATA:
//上传盘点任务
                    if (ChannelMap.getChannel("1") != null) {

                        Message message = new Message();
                        message.setId(1);
                        message.setType(MsgType.UPLOADDATA);

                        ReceiveId receiveId = GsonUtil.GsonToBean(GsonUtil.GsonString(response.getData()), ReceiveId.class);
                        Upload upload2 = new Upload();
                        upload2.setCheck(new DbManager().queryTaskBeanWhereID(receiveId.getId()));
                        upload2.setCheckAssets(new DbManager().queryAssetsBeanWhereId(receiveId.getId()));
                        message.setResponseMessage(upload2);
                        ChannelMap.getChannel("1").writeAndFlush(new TextWebSocketFrame(GsonUtil.GsonString(message)));
                    } else {
                        showToast("未连接");
                    }


                    break;
                case INFO_SEARCH_RFID_LIST:


                    break;
            }
        }


    }

    // 处理客户端向服务端发起http握手请求的业务
    private void handHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        if (!req.getDecoderResult().isSuccess()
                || !("websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req,
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            return;
        }
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                WEB_SOCKET_URL, null, true,65536*1024);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
        }
    }

    // 服务端向客户端响应消息
    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req,
                                  DefaultFullHttpResponse res) {
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
        }
        //服务端向客户端发送数据
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }


    //客户端与服务端创建连接的时候调用
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("用户上线: " + ctx.channel().remoteAddress());
        Channel incoming = ctx.channel();
        for (Channel channel : channels) {
            channel.writeAndFlush("[SERVER] - " + incoming.remoteAddress() + " 加入\n");
        }
        channels.add(ctx.channel());
        ChannelMap.addChannel("1", incoming);
        NettyConfig.group.add(ctx.channel());
        System.out.println("客户端与服务端连接开启...");
    }

    //客户端与服务端断开连接的时候调用
    @Override

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        NettyConfig.group.remove(ctx.channel());
        ChannelMap.delChannel("1");
        System.out.println("用户下线: " + ctx.channel().remoteAddress());
    }

    //服务端接收客户端发送过来的数据结束之后调用
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    //工程出现异常的时候调用
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    int count = 0;

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            System.out.println(ctx.channel().remoteAddress() + "超时次数:" + count);
            String type = "";
            if (event.state() == IdleState.READER_IDLE) {
                type = "read idle";
//                count++;
//                if(count>5) {
//                    System.out.println("超时次数达到最大值了，断开连接");
//                    channels.remove(ctx.channel());
//                    ctx.channel().close();
//                }
            } else if (event.state() == IdleState.WRITER_IDLE) {
                type = "write idle";
                count = 0;
            } else if (event.state() == IdleState.ALL_IDLE) {
                type = "all idle";
                count = 0;
            }
            ctx.writeAndFlush(new TextWebSocketFrame("Heartbeat")).addListener(ChannelFutureListener.CLOSE_ON_FAILURE);
            System.out.println(ctx.channel().remoteAddress() + "超时类型：" + type);
        }
        super.userEventTriggered(ctx, evt);

    }

}
