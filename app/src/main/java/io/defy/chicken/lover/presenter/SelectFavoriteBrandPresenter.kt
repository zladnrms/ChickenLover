package io.defy.chicken.lover.presenter

import com.zeniex.www.zeniexautomarketing.model.FavoriteBrandRepositoryModel
import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import io.defy.chicken.lover.contract.SelectFavoriteBrandContract
import io.defy.chicken.lover.model.FavoriteBrandRepository
import io.defy.chicken.lover.model.UserInfoDataRepository

class SelectFavoriteBrandPresenter : SelectFavoriteBrandContract.Presenter, AbstractPresenter<SelectFavoriteBrandContract.View>() {

    private var userRepo: UserInfoDataRepositoryModel? = null
    private var fbRepo: FavoriteBrandRepositoryModel? = null

    private var fbPage = 0

    override fun attachView(view: SelectFavoriteBrandContract.View) {
        super.attachView(view)
        this.userRepo = UserInfoDataRepository.getInstance()
        this.fbRepo = FavoriteBrandRepository.getInstance()
    }

    override fun detachView() {
        super.detachView()
        this.userRepo = null
        this.fbRepo = null
    }

    override fun initFirstBrand() {
        this.fbRepo?.let {
            if(it.selectAll().isNotEmpty())
            {
                return
            }

            it.insert(0, "bhc", "null", false)
            it.insert(1, "bbq", "null", false)
            it.insert(2, "네네치킨", "null", false)
            it.insert(3, "페리카나", "null", false)
            it.insert(4, "맘스터치", "null", false)
            it.insert(5, "교촌치킨", "null", false)
            it.insert(6, "굽네치킨", "null", false)
            it.insert(7, "처갓집양념치킨", "null", false)
            it.insert(8, "호식이두마리치킨", "null", false)
            it.insert(9, "멕시카나", "null", false)
            it.insert(10, "또래오래", "null", false)
            it.insert(11, "또봉이통닭", "null", false)
            it.insert(12, "지코바치킨", "null", false)
            it.insert(13, "썬더치킨", "null", false)
            it.insert(14, "부어치킨", "null", false)
            it.insert(15, "멕시칸치킨", "null", false)
        }
    }

    override fun getPage() : Int {
        return fbPage
    }

    override fun nextPage() {
        fbPage++
    }

    override fun previousPage() {
        fbPage--
    }

    override fun submit() {

        /*
         * 게스트 방문 ( 첫 방문 ) 시 LocalInfoData에 Insert 및 서버에 Insert 한 후
         * flag를 upgate 하는 방식으로 처리해야함
         *
         * 정ㄹ ㅣ : 첫 방문 (게스트 상태) -> 서버에 guest + (랜덤문자)로 아이디 생성
         * 게스트 : 일주일간 미 접속시 삭제됨
         * 게스트 uid와 정보를 클라이언트에서 받은 뒤 LocalInfoData에 Insert
         */
        //this.localRepo?.
    }
}