package io.defy.chicken.lover.adapter.model

import io.defy.chicken.lover.model.data.BoardCommentData

interface BoardCommentDataModel {
    /* 데이터 추가 */
    fun add(data: BoardCommentData)

    /* 데이터 제거 */
    fun remove(position: Int)

    /* 데이터 초기화*/
    fun clear()

    /* 종류 설정*/
    fun setType(type: String?)
}