package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class BoardArticleAmountRes(@SerializedName("result") var result : String? = null,
                                 @SerializedName("amount") var amount : Int = 0)