package io.defy.chicken.lover.adapter.model

import io.defy.chicken.lover.model.data.FileUploadData

interface FileUploadDataModel {
    /* 데이터 추가 */
    fun add(data: FileUploadData)

    /* 데이터 제거 */
    fun remove(position: Int)

    /* 데이터 초기화*/
    fun clear()
}