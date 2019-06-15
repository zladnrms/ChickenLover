package io.defy.chicken.lover.presenter

import com.zeniex.www.zeniexautomarketing.model.FavoriteTypeRepositoryModel
import com.zeniex.www.zeniexautomarketing.model.UserInfoDataRepositoryModel
import io.defy.chicken.lover.contract.SelectFavoriteTypeContract
import io.defy.chicken.lover.model.FavoriteTypeRepository
import io.defy.chicken.lover.model.UserInfoDataRepository

class SelectFavoriteTypePresenter : SelectFavoriteTypeContract.Presenter, AbstractPresenter<SelectFavoriteTypeContract.View>() {

    private var userRepo: UserInfoDataRepositoryModel? = null
    private var ftRepo: FavoriteTypeRepositoryModel? = null

    private var ftPage = 0

    override fun attachView(view: SelectFavoriteTypeContract.View) {
        super.attachView(view)
        this.userRepo = UserInfoDataRepository.getInstance()
        this.ftRepo = FavoriteTypeRepository.getInstance()
    }

    override fun detachView() {
        super.detachView()
        this.userRepo = null
        this.ftRepo = null
    }

    override fun initFirstType() {
        this.ftRepo?.let {
            if(it.selectAll().isNotEmpty())
            {
                return
            }

            it.insert(0, "후라이드", "null", false)
            it.insert(1, "양념", "null", false)
            it.insert(2, "반반", "null", false)
            it.insert(3, "간장", "null", false)
            it.insert(4, "허니", "null", false)
            it.insert(5, "베이크치킨", "null", false)
            it.insert(6, "전기구이", "null", false)
            it.insert(7, "파닭", "null", false)
            it.insert(8, "순살", "null", false)
            it.insert(9, "스노윙", "null", false)
            it.insert(10, "스모크", "null", false)
            it.insert(11, "매운맛", "null", false)
            it.insert(12, "치킨볼", "null", false)
            it.insert(13, "어니언", "null", false)
            it.insert(14, "갈릭", "null", false)
            it.insert(15, "와사비", "null", false)
            it.insert(16, "닭강정", "null", false)
            it.insert(17, "구운", "null", false)
        }
    }

    override fun getPage() : Int {
        return ftPage
    }

    override fun nextPage() {
        ftPage++
    }

    override fun previousPage() {
        ftPage--
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