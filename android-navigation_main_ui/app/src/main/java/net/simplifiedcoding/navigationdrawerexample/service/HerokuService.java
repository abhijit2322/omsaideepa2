package net.simplifiedcoding.navigationdrawerexample.service;

import net.simplifiedcoding.navigationdrawerexample.models.Renter;
import net.simplifiedcoding.navigationdrawerexample.models.FlatOwner;
import net.simplifiedcoding.navigationdrawerexample.models.Renter;
import net.simplifiedcoding.navigationdrawerexample.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.util.List;

public interface HerokuService {
    @GET("get")
    Call<ResponseBody> hello();

    @GET("getuser")
    Call<User> getUser();

    @GET("getflatowners")
    Call<List<FlatOwner>> getFlatowner();

    @POST("updatefowner")
    Call<FlatOwner> updateflatowner(@Body FlatOwner flatOwner);

    @GET("getrenter")
    Call<List<Renter>> getRenterList();

    @POST("updaterenter")
    Call<Renter> updaterentowner(@Body Renter renter);

}
