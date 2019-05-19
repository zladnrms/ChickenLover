package io.defy.chicken.lover.network.netty;

import android.util.Log;
import io.defy.chicken.lover.contract.ChatContract;
import io.defy.chicken.lover.model.data.ChatData;
import io.defy.chicken.lover.network.packet.*;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Iterator;
import java.util.LinkedList;

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

                Log.d("EntryPacket","Name $entryName, Room Id : $entryRoomId, $view : " + view);

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
                ChatPacket chatPacket1 = (ChatPacket) headerPacket;
                String chatName1 = chatPacket1.getName();
                int chatRoomId1 = chatPacket1.getRoomId();
                String chatContent1 = chatPacket1.getContent();
                String chatTimestamp1 = chatPacket1.getTimestamp();

                Log.d("ChatPacket","Name" + chatName1 + ", Room Id : " + chatRoomId1 + ", Content : " + chatContent1 + ", timestamp : " + chatTimestamp1);
                ChatData chatData = new ChatData(chatName1, chatRoomId1, chatContent1, chatTimestamp1);
                view.appendChatMessage(chatData);

                break;

            case 3:
                ChatHistoryPacket chatHistoryPacket = (ChatHistoryPacket) headerPacket;
                LinkedList<ChatPacket> chatList = chatHistoryPacket.getChatList();

                Iterator<ChatPacket> iterator = chatList.iterator(); // get iterator instance
                while (iterator.hasNext()) { // using Iterator
                    ChatPacket chatPacket2 = iterator.next();

                    ChatData chatData2 = new ChatData(chatPacket2.getName(), chatPacket2.getRoomId(),chatPacket2.getContent(), chatPacket2.getTimestamp());
                    view.appendChatMessage(chatData2);
                }
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