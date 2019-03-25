package io.defy.chicken.lover.contract

interface SelectFavoriteTypeContract {
    interface View {
        fun nextPageClick()

        fun previousPageClick()
    }

    interface Presenter {
        fun attachView(view: Any)

        fun detachView(view: Any)

        fun initFirstType()

        fun nextPage()

        fun previousPage()

        fun getPage() : Int

        fun submit()
    }
}