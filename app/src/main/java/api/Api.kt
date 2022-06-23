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
    fun parent(
        @Part("name") name: RequestBody,
        @Part("age") age: RequestBody,
        @Part("address") address: RequestBody,
        @Part file: MultipartBody.Part?,
        @Part("gender") gender: RequestBody
    ): Call<ParentModel?>?









    @POST("contact")
    fun contactUsToHelp(@Body contactus: ContactUsModel): Call<ContactUsModel>

    @GET("lost_peoples")
    fun getLostPeopleData(): Call<List<userLostPeople>>

    @GET("lost_people/eslam1")
    fun getChildData(): Call<ParentModel>

    @GET("events")
     fun event(): Call<List<User>>

    @GET("lost_people/{name}")
    open fun getHomeless(@Path("name") post: String): Call<List<ParentModel>>

    @GET("lost_people")
    fun getMy(@Query("name") name: String) :Call<List<ParentModel>>


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