package io.defy.chicken.lover.network.netty;

import android.os.Handler;
import android.util.Log;
import io.defy.chicken.lover.contract.ChatContract;
import io.defy.chicken.lover.model.data.ChatData;
import io.defy.chicken.lover.network.packet.ChatPacket;
import io.defy.chicken.lover.network.packet.EntryPacket;
import io.defy.chicken.lover.network.packet.ExitPacket;
import io.defy.chicken.lover.network.packet.HeaderPacket;
import io.defy.chicken.lover.rxbus.RxBus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends SimpleChannelInboundHandler<HeaderPacket> {

    private ChatContract.View view;

    NettyClientHandler(ChatContract.View view) {
        this.view = view;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HeaderPacket headerPacket) throws Exception {

        int requestCode = headerPacket.getRequestCode();
        Log.d("channelRead0","Packet Code 『" + requestCode + "』");

        switch (requestCode) {
            case 0:
                EntryPacket entryPacket = (EntryPacket) headerPacket;
                String entryName = entryPacket.getName();
                int entryRoomId = entryPacket.getRoomId();

                Log.d("EntryPacket","Name" + entryName + ", Room Id : " + entryRoomId + ", view : " + view);

                ChatData entryData = new ChatData(entryName, entryRoomId, "님이 입장하셨습니다", "");
                view.appendChatMessage(entryData);

                break;

            case 1: // 퇴장 패킷 받아 해석
                ExitPacket exitPacket = (ExitPacket) headerPacket;
                String exitName = exitPacket.getName();
                int exitRoomId = exitPacket.getRoomId();

                ChatData exitData = new ChatData(exitName, exitRoomId, "님이 퇴장하셨습니다", "");
                view.appendChatMessage(exitData);

                Log.d("ExitPacket","Name" + exitName + ", Room Id : " + exitRoomId);
                break;

            case 2: // 채팅 패킷 받아 해석
                ChatPacket chatPacket = (ChatPacket) headerPacket;
                String chatName = chatPacket.getName();
                int chatRoomId = chatPacket.getRoomId();
                String chatContent = chatPacket.getContent();
                String chatTimestamp = chatPacket.getTimestamp();

                Log.d("ChatPacket","Name" + chatName + ", Room Id : " + chatRoomId + ", Content : " + chatContent + ", timestamp : " + chatTimestamp);
                ChatData chatData = new ChatData(chatName, chatRoomId, chatContent, chatTimestamp);
                view.appendChatMessage(chatData);

                break;
        }
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        super.handlerAdded(ctx);

        Log.d("handlerAdded★","handlerAdded");
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        super.handlerRemoved(ctx);
        Log.d("handlerRemoved★","handlerRemoved");
        this.view = null;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);

        Log.d("channelReadComplete★","channelReadComplete");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.d("channelActive★","channelActive");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.d(" channelInactive★"," channelInactive");
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        Log.d(" channelRegistered★"," channelRegistered");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        Log.d(" channelUnregistered★"," channelUnregistered");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        super.channelRead(ctx, msg);
        Log.d(" channelRead★"," channelRead");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);

        Log.d(" exceptionCaught★"," exceptionCaught");
        cause.printStackTrace();
    }
}