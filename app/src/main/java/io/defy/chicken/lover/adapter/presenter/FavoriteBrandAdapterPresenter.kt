package io.defy.chicken.lover.adapter.presenter

import com.zeniex.www.zeniexautomarketing.model.FavoriteBrandRepositoryModel
import io.defy.chicken.lover.contract.FavoriteBrandContract
import io.defy.chicken.lover.model.FavoriteBrandRepository
import io.defy.chicken.lover.model.data.FavoriteBrandData
import io.defy.chicken.lover.presenter.AbstractPresenter

/**
 * Created by kim on 2017-09-14.
 */
class FavoriteBrandAdapterPresenter : FavoriteBrandContract.Presenter, AbstractPresenter<FavoriteBrandContract.View>() {

    private var repo: FavoriteBrandRepositoryModel? = null

    override fun attachView(view: FavoriteBrandContract.View) {
        super.attachView(view)
        this.repo = FavoriteBrandRepository.getInstance()
    }

    override fun detachView() {
        super.detachView()
        this.repo = null
    }

    override fun selectPaging(page : Int) : List<FavoriteBrandData>? {
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