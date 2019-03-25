package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class BoardCommentRes {
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

        public String getContent() {
            return content;
        }

        public String[] getThumbs_up() {
            return thumbs_up;
        }

        public String getWrite_date() {
            return write_date;
        }

        public int getInvisible() {
            return invisible;
        }

        @SerializedName("_id")
        String _id;
        @SerializedName("name")
        String name;
        @SerializedName("content")
        String content;
        @SerializedName("thumbs_up")
        String[] thumbs_up;
        @SerializedName("write_date")
        String write_date;
        @SerializedName("invisible")
        int invisible;

    }
}