package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class LoginGuestRes(@SerializedName("result") var result : String? = null)