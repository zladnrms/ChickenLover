package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class BoardArticleListRes(@SerializedName("result") var result : String? = null,
                               @SerializedName("result_array") var result_array : ArrayList<String> = ArrayList())