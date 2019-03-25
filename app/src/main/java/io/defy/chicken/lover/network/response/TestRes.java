package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TestRes {
    @SerializedName("result")
    String result;

    public String getResult() {
        return result;
    }

}