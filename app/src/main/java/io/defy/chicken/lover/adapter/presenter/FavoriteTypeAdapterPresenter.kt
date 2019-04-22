package io.defy.chicken.lover.adapter.presenter

import com.zeniex.www.zeniexautomarketing.model.FavoriteTypeRepositoryModel
import io.defy.chicken.lover.contract.FavoriteTypeContract
import io.defy.chicken.lover.model.FavoriteTypeRepository
import io.defy.chicken.lover.model.data.FavoriteTypeData

/**
 * Created by kim on 2017-09-14.
 */
class FavoriteTypeAdapterPresenter : FavoriteTypeContract.Presenter {

    private var view: FavoriteTypeContract.View? = null
    private var repo: FavoriteTypeRepositoryModel? = null

    override fun attachView(view: Any) {
        this.view = view as FavoriteTypeContract.View
        this.repo = FavoriteTypeRepository.getInstance()
    }

    override fun detachView(view: Any) {
        this.view = view as FavoriteTypeContract.View
        this.view = null
    }

    override fun selectPaging(page : Int) : List<FavoriteTypeData>? {
        val data = this.repo?.selectByPaging(page)
        return data
    }

    override fun checkDetect(position : Int) : Boolean? {
        /*
        * 해당 postion 번째 가져와서 check 값 변경함 성공하면 success 반환
        */
        return this.repo?.setCheck(position)
    }

}