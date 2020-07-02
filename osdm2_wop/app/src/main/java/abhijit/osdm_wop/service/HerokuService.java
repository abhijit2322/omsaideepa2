package abhijit.osdm_wop.service;

import abhijit.osdm_wop.models.Admin;
import abhijit.osdm_wop.models.FileDB;
import abhijit.osdm_wop.models.ImageDB;
import abhijit.osdm_wop.models.MonthlyExpence;
import abhijit.osdm_wop.models.Renter;
import abhijit.osdm_wop.models.FlatOwner;
import abhijit.osdm_wop.models.SuppTable;
import abhijit.osdm_wop.models.User;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

import java.util.ArrayList;
import java.util.List;

public interface HerokuService {
    @GET("get")
    Call<ResponseBody> hello();

    @GET("getuser")
    Call<User> getUser();

    @POST("getflatowners")
    Call<List<FlatOwner>> getFlatowner(@Body FlatOwner flatOwner);

    @POST("getsupportinfo")
    Call<List<SuppTable>> getSupportList(@Body SuppTable suppTable);
   // Call<List<SupportTable>> getSupportList();

    @POST("getrenter")
    Call<List<Renter>> getRenterList(@Body Renter renter);

    @POST("emailid")
    Call<List<String>> getAllEmailids(@Body FlatOwner apartmentid);

    @POST("ownermnt_status_get")
    Call <String> GetMntStatus_Owner(@Body FlatOwner flatOwner);
    @POST("rentermnt_status_get")
    Call <String> GetMntStatus_renter(@Body Renter renter);

    @GET("downloadfile")
    Call<FileDB> downloadFiles(@Query("month") String month);


    //=======================================================


    @POST("updatepass")
    Call<Admin> updateAdminTable(@Body Admin admin);

    @POST("updatefowner")
    Call<FlatOwner> updateflatowner(@Body FlatOwner flatOwner);

    @POST("insertfowner")
    Call<FlatOwner> insertflatowner(@Body FlatOwner flatOwner);

    @POST("flatownerstatus")
    Call <String> getFlatStatus(@Body FlatOwner flatOwner);


    @POST("getadmin")
    Call <List<Admin>> getAdmin(@Body Admin admin);

    @POST("getadmins")
    Call <List<Admin>> getAdmins(@Body Admin admin);

    @POST("getloginrule")
    Call <String> getLoginrule(@Body Admin admin);

    @POST("updaterenter")
    Call<Renter> updaterentowner(@Body Renter renter);

    @POST("addsupport")
    Call<SuppTable> updatersupport(@Body SuppTable renter);

    @POST("mnt_status")
    Call<Renter> updatemntstatus(@Body Renter renter);

    @POST("ownermnt_status")
    Call<FlatOwner> updatemntstatus_owner(@Body FlatOwner flatOwner);

    @POST("updatemexp")
    Call<MonthlyExpence> updatemonthlyexp(@Body MonthlyExpence monthlyExpence);

    @POST("getMntCaretNo")
     Call <List <FlatOwner>> getCareTackerPh(@Body FlatOwner flatOwner);

    @POST("uploadfile")
    Call<FileDB> uploadfile(@Body FileDB fileDB);

   // @POST("downloadfile")
   // Call<FileDB> downloadFiles( @Body FileDB fileDB);
    @POST("downloadfile")
    Call <List<FileDB>> downloadFiles_New(@Body FileDB fileDB);


    @POST("getfiledb")
    Call <List<FileDB>> getfiledb(@Body FileDB admin);
    @POST("getfiledb")
    Call <List<Void>> getfiledb_void(@Body FileDB admin);

    @POST("getfiledb_array")
    Call <ArrayList<FileDB>> getfiledb_array(@Body FileDB admin);

    @POST("uploadfile")
    Call<ImageDB> uploadImage(@Body ImageDB imagedb);
    @POST("downloadimage")
    //Call<List<ImageDB>> downloadImage(@Body ImageDB imagedb);
    Call<ImageDB> downloadImage(@Body ImageDB imagedb);


}
