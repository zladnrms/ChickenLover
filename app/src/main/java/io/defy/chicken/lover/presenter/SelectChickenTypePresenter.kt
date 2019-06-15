package io.defy.chicken.lover.presenter

import io.defy.chicken.lover.contract.SelectChickenTypeContract
import io.defy.chicken.lover.model.data.SelectChickenTypeData
import io.defy.chicken.lover.network.ApiInterface


class SelectChickenTypePresenter : SelectChickenTypeContract.Presenter, AbstractPresenter<SelectChickenTypeContract.View>() {

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

    override fun attachView(view: SelectChickenTypeContract.View) {
        super.attachView(view)
    }

    override fun detachView() {
        super.detachView()
    }

    override fun getChickenTypeList(): ArrayList<SelectChickenTypeData> {
        return list
    }
}