package io.defy.chicken.lover.model.data;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/*
*
* GUEST 유저 정보
*
* */

public class UserInfoData extends RealmObject {

    @PrimaryKey
    private int _id;

    private int type; // 0 : guest , 1 : member

    private int uid; // member uid

    private String hashed_value;

    private String guestId;

    private String name;

    private String id;

    private String password;

    public UserInfoData() {

    }

    public UserInfoData(int type, int uid, String hashed_value, String guestId, String name, String id, String password) {
        this.type = type;
        this.uid = uid;
        this.hashed_value = hashed_value;
        this.guestId = guestId;
        this.name = name;
        this.id = id;
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getHashed_value() {
        return hashed_value;
    }

    public void setHashed_value(String hashed_value) {
        this.hashed_value = hashed_value;
    }

    public String getGuestId() {
        return guestId;
    }

    public void setGuestId(String guestId) {
        this.guestId = guestId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}