package io.defy.chicken.lover.presenter

import com.zeniex.www.zeniexautomarketing.network.ApiInterface
import io.defy.chicken.lover.contract.SelectChickenTypeContract
import io.defy.chicken.lover.model.data.SelectChickenTypeData


class SelectChickenTypePresenter : SelectChickenTypeContract.Presenter {

    private var view: SelectChickenTypeContract.View? = null
    private val list :  ArrayList<SelectChickenTypeData> by lazy {
        arrayListOf(SelectChickenTypeData(0,"후라이드"),
            SelectChickenTypeData(1,"양념"),
            SelectChickenTypeData(2,"치즈"),
            SelectChickenTypeData(3,"간장"),
            SelectChickenTypeData(4,"파닭"),
            SelectChickenTypeData(5,"갈릭"),
            SelectChickenTypeData(6,"매운맛"))
    }

    val retrofitClient by lazy {
        ApiInterface.create()
    }

    override fun attachView(view: Any) {
        this.view = view as SelectChickenTypeContract.View
    }

    override fun detachView(view: Any) {
        this.view = null
    }

    override fun getChickenTypeList(): ArrayList<SelectChickenTypeData> {
        return list
    }
}