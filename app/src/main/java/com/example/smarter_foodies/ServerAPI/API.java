package com.example.smarter_foodies.ServerAPI;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @POST("SubmitRecipeToUser")
    Call<String> SubmitRecipeToUser(
            @Query("uid") String uid,
            @Query("title") String recipe
    );

    @FormUrlEncoded
    @POST("SubmitRecipeToUser2")
    Call<ResponseBody> SubmitRecipeToUser2(
            @Field("uid") String uid,
            @Field("recipe") String recipe
    );

}

