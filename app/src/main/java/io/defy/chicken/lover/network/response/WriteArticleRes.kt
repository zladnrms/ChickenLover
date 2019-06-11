package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class WriteArticleRes(@SerializedName("result") var result: String? = null,
                           @SerializedName("last_id") var last_id: Int = 0)