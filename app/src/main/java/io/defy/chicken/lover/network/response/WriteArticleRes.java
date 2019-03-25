package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;

public class WriteArticleRes {
    @SerializedName("result")
    String result;

    @SerializedName("last_id")
    int last_id;

    public String getResult() {
        return result;
    }

    public int getLast_id() {
        return last_id;
    }

}