package com.zeniex.www.zeniexautomarketing.network

import com.google.gson.GsonBuilder
import io.defy.chicken.lover.BuildConfig
import io.defy.chicken.lover.network.AddHeaderInterceptor
import io.defy.chicken.lover.network.response.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.POST
import retrofit2.http.FormUrlEncoded

interface ApiInterface {

    companion object {
        /* use for Json Response */
        fun create(): ApiInterface {

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY

            val client = OkHttpClient.Builder()
                    .addInterceptor { chain -> chain.proceed(chain.request()) }
                    .addInterceptor(interceptor)
                    .addNetworkInterceptor(AddHeaderInterceptor()).build()

            val gson = GsonBuilder()
                    .setLenient()
                    .create()

            val retrofit = Retrofit.Builder()
                    .client(client)
                    .baseUrl(BuildConfig.SERVER_URL)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build()

            return retrofit.create(ApiInterface::class.java)
        }
    }

    @FormUrlEncoded
    @POST("/chickenlover/mobile/info/get_chicken_info.php")
    fun getChickenInfo(@Field("way") way: String, @Field("brand") brand: String?, @Field("type") type: String?): Observable<ChickenInfoRes>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/history/get_chicken_select_history.php")
    fun getChickenSelectHistory(@Field("mobile") mobile: String): Observable<ChickenSelectHistoryRes>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/board/article/get_board_article_list.php")
    fun getBoardArticleList(@Field("type") type: String, @Field("index") index: Int?, @Field("limit") limit: Int?): Observable<BoardArticleListRes>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/board/article/get_board_article.php")
    fun getBoardArticle(@Field("type") type: String, @Field("a_id") a_id: Int?, @Field("title") title: String?): Observable<BoardArticleRes>

    @Multipart
    @POST("/chickenlover/mobile/board/article/write_board_article.php")
    fun writeBoardArticle(@Part("type") type: RequestBody, @Part("name") name: RequestBody, @Part("title") title: RequestBody, @Part("content") content: RequestBody, @Part parts : List<MultipartBody.Part>): Observable<WriteArticleRes>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/board/article/test.php")
    fun imgTestUpload(@Part("description") description: RequestBody, @Part files: List<MultipartBody.Part>): Observable<TestRes>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/board/comment/get_board_comment.php")
    fun getBoardComment(@Field("type") type: String, @Field("c_id") c_id: Int): Observable<BoardCommentRes>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/board/comment/write_board_comment.php")
    fun writeBoardComment(@Field("a_id") a_id: Int?, @Field("name") name: String?, @Field("content") content: String?): Observable<WriteCommentRes>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/board/comment/control_board_comment_thumbs.php")
    fun controlBoardCommentThumbs(@Field("type1") type1: String?, @Field("type2") type2: String?, @Field("switch") switch: Int?, @Field("c_id") c_id: Int?, @Field("c_uid") c_uid: Int?, @Field("hashed_value") hashed_value: String?): Observable<CommentThumbsRes>

    @FormUrlEncoded
    @POST("/chickenlover/member/join_as_guest.php")
    fun joinAsGuest(@Field("mobile") mobile: String): Observable<JoinGuestRes>

    @FormUrlEncoded
    @POST("/chickenlover/member/login_as_guest.php")
    fun loginAsGuest(@Field("mobile") mobile: String, @Field("hashed_value") hashed_value: String?): Observable<LoginGuestRes>

    @FormUrlEncoded
    @POST("/chickenlover/member/join_as_normal.php")
    fun joinAsNormal(@Field("mobile") mobile: String, @Field("login_type") login_type: Int, @Field("id") id: String, @Field("password") password: String, @Field("pre_name") pre_name: String?, @Field("next_name") next_name: String): Observable<JoinMemberRes>

    @FormUrlEncoded
    @POST("/chickenlover/member/login_as_auto.php")
    fun loginAsAuto(@Field("mobile") mobile: String, @Field("login_type") login_type: Int, @Field("hashed_value") hashed_value: String?): Observable<LoginGuestRes>

    @FormUrlEncoded
    @POST("/chickenlover/member/login_as_normal.php")
    fun loginAsMember(@Field("mobile") mobile: String, @Field("login_type") login_type: Int, @Field("id") id: String?, @Field("password") password: String?): Observable<LoginMemberRes>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/version/check_chicken_info_version.php")
    fun checkChickenInfoVersion(@Field("mobile") mobile: String): Observable<VersionCheckRes>

    @POST("/chickenlover/mobile/info/get_chicken_info_for_serarch.php")
    fun updateLocalChickenInfo(): Observable<UpdateLocalChickenInfoRes>

    @FormUrlEncoded
    @POST("/chickenlover/mobile/board/article/control_board_article_thumbs.php")
    fun controlBoardArticleThumbs(@Field("type1") type1: String?, @Field("type2") type2: String?, @Field("switch") switch: Int?, @Field("a_id") a_id: Int?, @Field("hashed_value") hashed_value: String?): Observable<ArticleThumbsRes>


}