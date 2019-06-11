package io.defy.chicken.lover.network.response

import com.google.gson.annotations.SerializedName

data class VersionCheckRes(@SerializedName("code") var code: Int = 0)