package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;

public class ArticleThumbsRes {
    @SerializedName("result")
    String result;

    public String getResult() {
        return result;
    }
}