package io.defy.chicken.lover.contract

interface SelectFavoriteBrandContract {
    interface View {
        fun nextPageClick()

        fun previousPageClick()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun initFirstBrand()

        fun nextPage()

        fun previousPage()

        fun getPage() : Int

        fun submit()
    }
}