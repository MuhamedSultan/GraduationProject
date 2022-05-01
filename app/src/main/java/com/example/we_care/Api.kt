package com.example.we_care

import models.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {
    @POST("register")
     fun register(@Body model: Model): Call<Model>

    @POST("login")
     fun login(@Body model: Model): Call<Model>

    @POST("lost_people")
     fun parent(@Body parentModel: ParentModel): Call<ParentModel>

    @POST("contact")
    fun contactUsToHelp(@Body contactus: ContactUsModel): Call<ContactUsModel>

    @GET("lost_peoples")
    fun getLostPeopleData(): Call<List<userLostPeople>>

    @GET("lost_people/eslam1")
    fun getChildData(): Call<ParentModel>

    @GET("events")
     fun event(): Call<List<User>>

    @GET("lost_people/{name}")
    open fun getHomeless(@Path("name") post: String): Call<List<GetChiledData>>


//    //
//   // @Headers("Content-Type: application/json")
//    @POST("lost_people")
//    open fun upload(@Body parentModel: String): Call<ParentModel>
//
//    @FormUrlEncoded
//    @POST("lost_people")
//    fun uploadImage(
//       // @Field("image_name") title: String?,
//        @Field("image") image: String?
//    ): Call<Pojo?>?
//
//
// @GET("get_profile_data/2")
//    fun getProfileData(): Call<Model>
//
//    @GET("posts/{id}")
//    fun test(@Path("id") post: String): Call<Pojo>
//
//
//    //



}