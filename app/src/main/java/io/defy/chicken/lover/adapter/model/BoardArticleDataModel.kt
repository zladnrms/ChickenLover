package io.defy.chicken.lover.adapter.model

import io.defy.chicken.lover.model.data.BoardArticleData

interface BoardArticleDataModel {
    /* 데이터 추가 */
    fun add(data: BoardArticleData)

    /* 데이터 제거 */
    fun remove(position: Int)

    /* 데이터 초기화*/
    fun clear()
}