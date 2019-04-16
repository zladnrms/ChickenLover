package io.defy.chicken.lover.network.netty;

import io.defy.chicken.lover.BuildConfig;
import io.defy.chicken.lover.contract.ChatContract;
import io.defy.chicken.lover.network.packet.ChatPacket;
import io.defy.chicken.lover.network.packet.EntryPacket;
import io.defy.chicken.lover.network.packet.ExitPacket;
import io.defy.chicken.lover.network.packet.HeaderPacket;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyClient extends Thread {

    private String host = BuildConfig.TCP_URL;
    private int port = 6060;
    private boolean submitFlag = false;
    private int type;

    private HeaderPacket headerPacket;
    private EntryPacket entryPacket;
    private ExitPacket exitPacket;
    private ChatPacket chatPacket;

    private boolean flag = true;

    private ChatContract.View view;

    public NettyClient(ChatContract.View view) {
        this.view = view;
    }

    @Override
    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap()
                    .group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new NettyClientInitializer(this.view));
            Channel channel = bootstrap.connect(host, port).sync().channel();

            while (flag) {
                if(submitFlag) {
                    switch (this.type) {
                        case 0:
                            channel.writeAndFlush(this.entryPacket);
                            break;
                        case 1:
                            channel.writeAndFlush(this.exitPacket);
                            break;
                        case 2:
                            channel.writeAndFlush(this.chatPacket);
                            break;
                    }
                    submitFlag = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
            this.view = null;
        }
    }

    public void send(int type, HeaderPacket headerPacket) {
        switch (type) {
            case 0:
                this.type = 0;
                this.entryPacket = (EntryPacket) headerPacket;
                submitFlag = true;
                break;
            case 1:
                this.type = 1;
                this.exitPacket = (ExitPacket) headerPacket;
                submitFlag = true;
                break;
            case 2:
                this.type = 2;
                this.chatPacket = (ChatPacket) headerPacket;
                submitFlag = true;
                break;
        }
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}