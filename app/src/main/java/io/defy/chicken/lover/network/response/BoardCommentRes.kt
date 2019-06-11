package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class BoardCommentRes(
    @SerializedName("result") var result: String? = null,
    @SerializedName("result_array") var result_array: List<InnerArray> = ArrayList()
) {
    data class InnerArray(
        @SerializedName("_id") var _id : Int = 0,
        @SerializedName("name") var name: String,
        @SerializedName("content") var content: String,
        @SerializedName("thumbs_up") val thumbs_up : Array<String>,
        @SerializedName("write_date") var write_date: String,
        @SerializedName("invisible") var invisible: Int = 0
    )
}