package abhijit.osdm_wop;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;

import abhijit.osdm_wop.models.Renter;
import abhijit.osdm_wop.rettrofitsupport.RetrofitClient;
import abhijit.osdm_wop.service.HerokuService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RenterListActivity extends AppCompatActivity {
    private LinearLayoutManager layoutManager;
    private List<Renter> userList =null;
    private ImageView iv=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Tenent List");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.renterlist_main);
        Log.i("autolog", "onCreate");
        Renter renter=new Renter();
        renter.setApartmentid(AppSettingsData.getApartmentID());
        getRenterList(renter);
    }
    private void getRenterList(Renter renter) {
      /*  Log.i("autolog", "getUserList");
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
        }catch (Exception e) {Log.i("autolog", "Exception");}*/
        System.out.println("I am here ...<Renter list Class>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getRenterList(renter).enqueue(new Callback<List<Renter>>() {
            @Override
            public void onResponse(Call<List<Renter>> call, Response<List<Renter>> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
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
            }
            @Override
            public void onFailure(Call<List<Renter>>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
    }
}