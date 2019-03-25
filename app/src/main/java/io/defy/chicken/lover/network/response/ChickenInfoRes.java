package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

public class ChickenInfoRes {

    @SerializedName("way")
    String way;

    @SerializedName("name")
    String name;

    @SerializedName("brand")
    String brand;

    @SerializedName("type_array")
    String type_array;

    public String getWay() {
        return way;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public String getType_array() {
        return type_array;
    }
}