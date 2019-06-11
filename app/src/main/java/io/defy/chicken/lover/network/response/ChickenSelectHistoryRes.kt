package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class ChickenSelectHistoryRes(
    @SerializedName("result") var result: String? = null,
    @SerializedName("result_array") var result_array: List<InnerArray> = ArrayList()
) {
    data class InnerArray(
        @SerializedName("_id") var _id : Int = 0,
        @SerializedName("name") var name: String,
        @SerializedName("chicken_name") var chicken_name: String,
        @SerializedName("chicken_brand") val chicken_brand : String,
        @SerializedName("select_date") var select_date: String
    )
}