package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CommentThumbsRes {
    @SerializedName("result")
    String result;

    public String getResult() {
        return result;
    }
}