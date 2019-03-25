package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BoardArticleListRes {
    @SerializedName("result")
    String result;

    @SerializedName("result_array")
    ArrayList result_array;

    public String getResult() {
        return result;
    }

    public ArrayList getResultArray() {
        return result_array;
    }
}