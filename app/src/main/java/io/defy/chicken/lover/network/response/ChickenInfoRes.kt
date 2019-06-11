package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class ChickenInfoRes(@SerializedName("_id") var _id : Int = 0,
                          @SerializedName("way") var way : String? = null,
                          @SerializedName("name") var name : String? = null,
                          @SerializedName("brand") var brand : String? = null,
                          @SerializedName("type_array") var type_array : String? = null,
                          @SerializedName("type_number") var type_number : Int = 0,
                          @SerializedName("thumbs_up") var thumbs_up : Int = 0)