package io.defy.chicken.lover.network.netty;

import android.util.Log;
import io.defy.chicken.lover.contract.ChatContract;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    private ChatContract.View view;

    NettyClientInitializer(ChatContract.View view) {
        this.view = view;
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {

        ChannelPipeline pipeline = ch.pipeline();

        Log.d("initChannel", ch.pipeline().channel() + " channel 초기화");

        pipeline.addLast(new ObjectDecoder( 1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())),new ObjectEncoder());
        pipeline.addLast("handler", new NettyClientHandler(this.view));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);

        this.view = null;
    }
}