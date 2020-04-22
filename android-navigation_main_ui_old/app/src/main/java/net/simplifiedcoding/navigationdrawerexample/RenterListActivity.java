package net.simplifiedcoding.navigationdrawerexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import net.simplifiedcoding.navigationdrawerexample.models.FlatOwner;
import net.simplifiedcoding.navigationdrawerexample.models.Renter;
import net.simplifiedcoding.navigationdrawerexample.service.HerokuService;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RenterListActivity extends AppCompatActivity {
    private LinearLayoutManager layoutManager;
    private List<Renter> userList =null;
    private ImageView iv=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renterlist_main);
        Log.i("autolog", "onCreate");
        getRenterList();
    }
    private void getRenterList() {
        Log.i("autolog", "getUserList");
        try {
            String url = "https://postgres2322.herokuapp.com/renter/";
            Log.i("autolog", "https://postgres2322.herokuapp.com/renter/");

            Retrofit retrofit = null;
            Log.i("autolog", "retrofit");

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Log.i("autolog", "build();");
            }


            HerokuService service = retrofit.create(HerokuService.class);
            Log.i("autolog", " APIService service = retrofit.create(APIService.class);");


            Call<List<Renter>> call = service.getRenterList();
            Log.i("autolog", "Call<List<User>> call = service.getUserData();");

            call.enqueue(new Callback<List<Renter>>() {
                @Override
                public void onResponse(Call<List<Renter>> call, Response<List<Renter>> response) {
                    //Log.i("onResponse", response.message());
                    Log.i("autolog", "onResponse");

                    userList = response.body();
                    Log.i("autolog", "List<User> userList = response.body();");

                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
                    Log.i("autolog", "RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);");

                    layoutManager = new LinearLayoutManager(RenterListActivity.this);
                    Log.i("autolog", "layoutManager = new LinearLayoutManager(MainActivity.this);");
                    recyclerView.setLayoutManager(layoutManager);
                    Log.i("autolog", "recyclerView.setLayoutManager(layoutManager);");

                    RecyclerViewAdapter_Renter recyclerViewAdapter =new RecyclerViewAdapter_Renter(getApplicationContext(), userList);
                    Log.i("autolog", "RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(getApplicationContext(), userList);");
                    recyclerView.setAdapter(recyclerViewAdapter);
                    Log.i("autolog", "recyclerView.setAdapter(recyclerViewAdapter);");
                }

                @Override
                public void onFailure(Call<List<Renter>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        }catch (Exception e) {Log.i("autolog", "Exception");}
    }
}