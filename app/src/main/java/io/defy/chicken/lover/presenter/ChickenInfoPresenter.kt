package io.defy.chicken.lover.presenter

import android.content.Intent
import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.R
import io.defy.chicken.lover.contract.ChickenInfoContract
import io.defy.chicken.lover.network.response.ChickenInfoRes
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class ChickenInfoPresenter : ChickenInfoContract.Presenter {

    private var typeNumber: Int = 0
    private var view: ChickenInfoContract.View? = null

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as ChickenInfoContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun setTypeNumber(typeNumber: Int) {
        this.typeNumber = typeNumber
    }

    override fun getTypeNumber(): Int {
        return this.typeNumber
    }

    override fun setChickenImage() {
        when(this.typeNumber)
        {
            0 -> view?.setImageResource(R.drawable.fried)
            1 -> view?.setImageResource(R.drawable.seasoned_fried)
            2 -> view?.setImageResource(R.drawable.cheese_fried)
            3 -> view?.setImageResource(R.drawable.soy_fried)
            4 -> view?.setImageResource(R.drawable.green_onion_fried)
            5 -> view?.setImageResource(R.drawable.garlic_fried)
            6 -> view?.setImageResource(R.drawable.peoper_fried)
        }
    }
}