package io.defy.chicken.lover.network.packet;

import java.io.Serializable;
import java.util.LinkedList;

/**
 * Created by Administrator on 2016-11-28.
 */

public class ChatHistoryPacket extends HeaderPacket implements Serializable {

    private static final long serialVersionUID = 5L;

    private LinkedList<ChatPacket> chatList;

	public ChatHistoryPacket(int kinds, LinkedList<ChatPacket> chatList) {
        super((byte) kinds);
        
        this.chatList = chatList;
    }
	
	public LinkedList<ChatPacket> getChatList() {
		return chatList;
	}

	public void setChatList(LinkedList<ChatPacket> chatList) {
		this.chatList = chatList;
	}
}