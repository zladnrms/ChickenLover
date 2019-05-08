package com.zeniex.www.zeniexautomarketing.model

import io.defy.chicken.lover.model.data.LocalChickenInfoData
import java.util.*

interface LocalChickenInfoModel {
    fun insert(brand: String?, name : String?, type_number : Int?, type_array: String?)

    fun select(text: String) : LinkedList<LocalChickenInfoData>

    fun delete()

    fun realmClose()

    fun selectAll() : List<LocalChickenInfoData>
}