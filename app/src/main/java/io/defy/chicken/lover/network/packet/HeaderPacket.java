package io.defy.chicken.lover.network.packet;

import java.io.Serializable;

/**
 * Created by Administrator on 2016-11-28.
 */

public class HeaderPacket implements Serializable {

    private static final long serialVersionUID = 1L;

    private byte requestCode; // 프로토콜 종류

    public HeaderPacket(byte requestCode) {
        this.requestCode = requestCode;
    }

    public byte getRequestCode() {
        return requestCode;
    }
}
