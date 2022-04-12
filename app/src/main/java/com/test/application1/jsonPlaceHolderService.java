package com.test.application1;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface jsonPlaceHolderService {
    @GET("posts")
    Call<List<Post>> getPosts();
}