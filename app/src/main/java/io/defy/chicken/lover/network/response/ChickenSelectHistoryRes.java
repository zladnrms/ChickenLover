package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChickenSelectHistoryRes {
    @SerializedName("result")
    String result;

    @SerializedName("result_array")
    List<Obj> result_array;;

    public String getResult() {
        return result;
    }

    public List<Obj>  getResultArray() {
        return result_array;
    }

    public class Obj {
        public String get_id() {
            return _id;
        }

        public String getName() {
            return name;
        }

        public String getChicken_name() {
            return chicken_name;
        }

        public String getChicken_brand() {
            return chicken_brand;
        }

        public String getSelect_date() {
            return select_date;
        }

        @SerializedName("_id")
        String _id;
        @SerializedName("name")
        String name;
        @SerializedName("chicken_name")
        String chicken_name;
        @SerializedName("chicken_brand")
        String chicken_brand;
        @SerializedName("select_date")
        String select_date;

    }
}