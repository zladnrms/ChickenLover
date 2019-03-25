package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;

public class JoinGuestRes {
    @SerializedName("result")
    String result;

    @SerializedName("hashed_value")
    String hashed_value;

    @SerializedName("guest_id")
    String guest_id;

    @SerializedName("name")
    String name;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }


    public String getHashed_value() {
        return hashed_value;
    }

    public void setHashed_value(String hashed_value) {
        this.hashed_value = hashed_value;
    }

    public String getGuest_id() {
        return guest_id;
    }

    public void setGuest_id(String guest_id) {
        this.guest_id = guest_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}