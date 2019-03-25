package com.zeniex.www.zeniexautomarketing.model

import io.defy.chicken.lover.model.data.UserInfoData

interface UserInfoDataRepositoryModel {
    fun insert(type : Int?, uid: Int?, hashedValue : String?, guestId : String?, name : String?, id : String?, password : String?)

    fun select() : UserInfoData

    fun update(hashed_value : String?, guest_id : String?, name : String?)

    fun delete()

    fun realmClose()

    fun selectAll() : List<UserInfoData>
}