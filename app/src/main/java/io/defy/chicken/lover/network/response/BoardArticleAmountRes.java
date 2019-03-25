package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BoardArticleAmountRes {
    @SerializedName("result")
    String result;

    @SerializedName("amount")
    int amount;

    public String getResult() {
        return result;
    }

    public int getAmount() {
        return amount;
    }

}