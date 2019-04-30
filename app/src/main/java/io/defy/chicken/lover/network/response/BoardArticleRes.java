package io.defy.chicken.lover.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class BoardArticleRes {
    @SerializedName("_id")
    String _id;

    @SerializedName("title")
    String title;

    @SerializedName("writer")
    String writer;

    @SerializedName("content")
    String content;

    @SerializedName("img_url")
    ArrayList<String> img_url;

    @SerializedName("create_date")
    String create_date;

    @SerializedName("thumbs")
    String thumbs;;

    @SerializedName("comment_id")
    String comment_id;

    public String get_id() {
        return _id;
    }

    public String getTitle() {
        return title;
    }

    public String getWriter() {
        return writer;
    }

    public String getContent() {
        return content;
    }

    public ArrayList<String> getImg_url() {
        return img_url;
    }

    public String getCreate_date() {
        return create_date;
    }

    public String getComment_id() {
        return comment_id;
    }

    public String getThumbs() {
        return thumbs;
    }
}