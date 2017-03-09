package com.imm.push;


import com.google.gson.Gson;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.ReferenceCountUtil;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/** 用于维护 和core的长连接及推送消息 Created by Misnearzhang on 2017/3/9. */

@io.netty.channel.ChannelHandler.Sharable
public class PushService extends SimpleChannelInboundHandler<Object> {

  private static final String TAG = PushService.class.getSimpleName();
  private Channel channel;
  private ExecutorService executor;
  Bootstrap bootstrap;
  EventLoopGroup loopGroup;
  static PushService manager;
  // 连接空闲时间
  public static final int READ_IDLE_TIME = 180; // 秒
  // 心跳超时
  public static final int HEART_TIME_OUT = 180000; // 秒

  private String serverHost;
  private int serverPort;
  private static Gson gson = new Gson();

  PushService(String serverHost,int serverPort) {
    this.serverHost=serverHost;
    this.serverPort=serverPort;
    executor = Executors.newFixedThreadPool(3);
    bootstrap = new Bootstrap();
    loopGroup = new NioEventLoopGroup();
    bootstrap.group(loopGroup);
    bootstrap.channel(NioSocketChannel.class);
    bootstrap.option(ChannelOption.TCP_NODELAY, true);
    bootstrap.handler(
        new ChannelInitializer<SocketChannel>() {
          @Override
          public void initChannel(SocketChannel ch) throws Exception {

            ChannelPipeline pipeline = ch.pipeline();
            ByteBuf delimiter = Unpooled.copiedBuffer("\r\n".getBytes());
            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
            // 设置带长度编码器
            ch.pipeline().addLast(new StringDecoder());
            ch.pipeline().addLast(new LineBasedFrameDecoder(1024 * 5));
            pipeline.addLast("idleStateHandler", new IdleStateHandler(0, 0, READ_IDLE_TIME));
            pipeline.addLast(PushService.this);
          }
        });
  }

  public static synchronized PushService getManager(String serverHost,int serverPort) {
    if (manager == null) {
      manager = new PushService(serverHost,serverPort);
    }
    return manager;
  }

  private synchronized void syncConnection(final String ServerHost, final int ServerPort) {
    try {

      if (isConnected()) {
        return;
      }
      ChannelFuture channelFuture =
          bootstrap
              .connect(new InetSocketAddress(ServerHost, ServerPort))
              .sync(); // 这里的IP和端口，根据自己情况修改
      channel = channelFuture.channel();
    } catch (Exception e) {
     //
    }
  }

  public void connect() {

    Future<?> future =
        executor.submit(
            new Runnable() {
              public void run() {
                syncConnection(serverHost, serverPort);
              }
            });
    /*
     * try { if (future.get() != null) { connect(cimServerHost,
     * cimServerPort); } } catch (Exception e) {
     *
     * connect(cimServerHost, cimServerPort); e.printStackTrace(); }
     */
  }

  /*
     发送信息 系统验证和退出等消息
  */
  public void send(final String body) {
    final ByteBuf send=Unpooled.copiedBuffer(body.getBytes());
    executor.execute(
        new Runnable() {
          public void run() {
            if (channel != null && channel.isActive()) {
              boolean isDone = channel.writeAndFlush(send).awaitUninterruptibly(5000);

            } else {
              //调用重连
            }
          }
        });
  }

  /*
     发送响应
  */
  public void sendReply(String replyBody) {
    replyBody += "\r\n";
    final ByteBuf response = Unpooled.copiedBuffer(replyBody.getBytes());
    executor.execute(
        new Runnable() {
          public void run() {
            if (channel != null && channel.isActive()) {
              boolean isDone = channel.writeAndFlush(response).awaitUninterruptibly(5000);

            } else {
              //重连
            }
          }
        });
  }

  public void destroy() {
    if (manager.channel != null) {
      manager.channel.close();
    }
    loopGroup.shutdownGracefully();
    manager = null;
  }

  public boolean isConnected() {
    if (channel == null) {
      return false;
    }
    return channel.isActive();
  }

  public void closeSession() {
    if (channel != null) {
      channel.close();
    }
  }

  // 检测到连接空闲事件，发送心跳请求命令

  /** 检测到连接空闲事件，发送心跳请求命令 */
  @Override
  public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
    if (evt instanceof IdleStateEvent) {
      if (((IdleStateEvent) evt).isFirst()) {
        System.out.println("first");
      }
    }
  }



  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {


  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    StringBuilder sb = new StringBuilder();
    sb.append("错误日志：" + cause.getMessage() + "\n");
    sb.append("错误堆栈：\n");
    for (int i = 0; i < cause.getStackTrace().length; i++) {
      sb.append(cause.getStackTrace()[i] + "\n");
    }

  }

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
    try {
      System.out.println("message from pushService:"+msg);
    } finally {
      ReferenceCountUtil.release(msg);
    }
  }
}
