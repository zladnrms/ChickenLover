package com.zeniex.www.zeniexautomarketing.model

import io.defy.chicken.lover.model.data.LocalChickenInfoData

interface LocalChickenInfoModel {
    fun insert(brand: String?, name : String?)

    fun select(text: String) : LocalChickenInfoData

    fun delete()

    fun realmClose()

    fun selectAll() : List<LocalChickenInfoData>
}