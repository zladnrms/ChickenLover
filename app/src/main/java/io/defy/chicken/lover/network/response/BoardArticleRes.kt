package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class BoardArticleRes(@SerializedName("_id") var _id : Int = 0,
                           @SerializedName("title") var title : String,
                           @SerializedName("writer") var writer : String,
                           @SerializedName("content") var content : String,
                           @SerializedName("img_url") var img_url : ArrayList<String>,
                           @SerializedName("create_date") var create_date : String,
                           @SerializedName("thumbs") var thumbs : String,
                           @SerializedName("comment_id") var comment_id : Int = 0)