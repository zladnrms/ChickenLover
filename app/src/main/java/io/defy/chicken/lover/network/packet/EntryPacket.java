package io.defy.chicken.lover.network.packet;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-11-28.
 */

public class EntryPacket extends HeaderPacket implements Serializable {

    private static final long serialVersionUID = 2L;

    private int roomId;
	private String name;
    
    public EntryPacket(int kinds, int roomId, String name) {
        super((byte) kinds);
        this.roomId = roomId;
        this.name = name;
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

}
