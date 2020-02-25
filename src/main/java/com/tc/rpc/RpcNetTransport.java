package com.tc.rpc;

import com.tc.RpcRequest;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/**
 * @author taosh
 * @create 2019-12-14 16:08
 */
public class RpcNetTransport extends SimpleChannelInboundHandler<Object> {
    private String serviceAddress;

    public RpcNetTransport(String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }

    private Object result;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        this.result = 0;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("异常:---");
        ctx.close();
    }

    public Object send(RpcRequest request){
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new ObjectDecoder(Integer.MAX_VALUE, ClassResolvers.cacheDisabled(null)))
                                .addLast(new ObjectEncoder())
                                .addLast(RpcNetTransport.this);
                    }
                }).option(ChannelOption.TCP_NODELAY, true);

        try {
            String urls[] = serviceAddress.split(":");
            ChannelFuture channelFuture = bootstrap.connect(urls[0], Integer.parseInt(urls[1])).sync();
            channelFuture.channel().writeAndFlush(request).sync();

            if ( request != null ){
                channelFuture.channel().closeFuture().sync();
            }
        }catch ( Exception e ){
            e.printStackTrace();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

        return result;
    }
}
