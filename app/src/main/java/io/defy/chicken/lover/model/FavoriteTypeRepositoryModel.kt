package com.zeniex.www.zeniexautomarketing.model

import io.defy.chicken.lover.model.data.FavoriteBrandData
import io.defy.chicken.lover.model.data.FavoriteTypeData
import io.realm.RealmResults

interface FavoriteTypeRepositoryModel {
    fun insert(uid : Int?, TypeName : String?, imgUrlName : String?, checked : Boolean?)

    fun select() : FavoriteTypeData

    fun delete()

    fun setCheck(position : Int?) : Boolean

    fun selectByPaging(page : Int) : List<FavoriteTypeData>

    fun selectAll() : List<FavoriteTypeData>

    fun realmClose()
}