package com.zeniex.www.zeniexautomarketing.model

interface AppVersionDataModel {
    fun insert(versionCode: Int)

    fun selectVersionCode() : Int

    fun update(versionCode: Int)

    fun delete()

    fun realmClose()
}