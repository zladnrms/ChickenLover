package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class BoardArticleRes(@SerializedName("_id") var _id : Int = 0,
                           @SerializedName("title") var title : String? = null,
                           @SerializedName("writer") var writer : String? = null,
                           @SerializedName("content") var content : String? = null,
                           @SerializedName("img_url") var img_url : ArrayList<String> = ArrayList(),
                           @SerializedName("create_date") var create_date : String? = null,
                           @SerializedName("thumbs") var thumbs : String? = null,
                           @SerializedName("comment_id") var comment_id : String? = null)