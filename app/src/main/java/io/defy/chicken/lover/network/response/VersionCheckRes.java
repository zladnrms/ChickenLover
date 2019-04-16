package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;

public class VersionCheckRes {
    @SerializedName("code")
    int code;

    public int getCode() {
        return code;
    }
}