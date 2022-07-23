package api

import models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {
    @POST("register")
    fun register(@Body model: Model): Call<Model>

    @POST("login")
    fun login(@Body model: Model): Call<Model>

    @POST("lost_people")
    fun parent(@Body parentModel: ParentModel): Call<ParentModel>

    @Multipart
    @POST("lost_people")
    fun lostPeople(
        @Part("name") name: RequestBody,
        @Part("age") age: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part file: MultipartBody.Part?,
        @Part("gender") gender: RequestBody
    ): Call<ParentModel?>?


    @Multipart
    @POST("homeless")
    fun homeless(
        @Part("name") name: RequestBody,
        @Part("age") age: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part file: MultipartBody.Part?,
        @Part("gender") gender: RequestBody
    ): Call<ParentModel?>?

    @Multipart
    @POST("lost_people")
    fun parent(
        @Part("name") name: RequestBody,
        @Part("age") age: RequestBody,
        @Part("phone") phone: RequestBody,
        @Part("address") address: RequestBody,
        @Part file: MultipartBody.Part?,
        @Part("gender") gender: RequestBody
    ): Call<ParentModel?>?




    @POST("contact")
    fun contactUsToHelp(@Body contactus: ContactUsModel): Call<ContactUsModel>

    @GET("lost_peoples")
    fun getLostPeopleData(): Call<List<userLostPeople>>

    @GET("events")
    fun event(): Call<List<User>>

    @GET("lost_people/{name}")
    fun getHomeless(@Path("name") post: String): Call<List<ParentModel>>


}