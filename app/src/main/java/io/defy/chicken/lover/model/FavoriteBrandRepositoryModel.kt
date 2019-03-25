package com.zeniex.www.zeniexautomarketing.model

import io.defy.chicken.lover.model.data.FavoriteBrandData
import io.realm.RealmResults

interface FavoriteBrandRepositoryModel {
    fun insert(uid : Int?, brandName : String?, imgUrlName : String?, checked : Boolean?)

    fun select() : FavoriteBrandData

    fun delete()

    fun setCheck(position : Int?) : Boolean

    fun selectByPaging(page : Int) : List<FavoriteBrandData>

    fun selectAll() : List<FavoriteBrandData>

    fun realmClose()
}