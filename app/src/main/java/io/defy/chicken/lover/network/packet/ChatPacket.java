package io.defy.chicken.lover.network.packet;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-11-28.
 */

public class ChatPacket extends HeaderPacket implements Serializable {

    private static final long serialVersionUID = 4L;

    private int roomId;
	private String name;
	private String content;
	private String timestamp;
    
    public ChatPacket(int kinds, int roomId, String name, String content, String timestamp) {
        super((byte) kinds);
        this.roomId = roomId;
        this.name = name;
        this.content = content;
        this.timestamp = timestamp;
    }

    public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	
}
