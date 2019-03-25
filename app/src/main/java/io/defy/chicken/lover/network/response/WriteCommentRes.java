package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;

public class WriteCommentRes {
    @SerializedName("result")
    String result;

    @SerializedName("comment_id")
    String comment_id;

    public String getResult() {
        return result;
    }

    public String getComment_id() {
        return comment_id;
    }
}