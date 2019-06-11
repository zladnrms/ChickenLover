package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class WriteCommentRes(@SerializedName("result") var result: String? = null,
                           @SerializedName("comment_id") var comment_id: String? = null)