package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class UpdateLocalChickenInfoRes(@SerializedName("result") var result : String? = null,
                                     @SerializedName("result_array") var result_array : ArrayList<String> = ArrayList())