package abhijit.osdm2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import abhijit.osdm2.models.FlatOwner;
import abhijit.osdm2.rettrofitsupport.RetrofitClient;
import abhijit.osdm2.service.HerokuService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerListActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private List<FlatOwner> userList =null;
    private ImageView iv=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Owner List");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ownerlist_main);
        Log.i("autolog", "onCreate");
        getUserList();
    }
    private void getUserList() {
       /* Log.i("autolog", "getUserList");
        try {
            String url = "https://postgres2322.herokuapp.com/flatowner/";
            Log.i("autolog", "https://postgres2322.herokuapp.com/flatowner/");

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


            Call<List<FlatOwner>> call = service.getFlatowner();
            Log.i("autolog", "Call<List<User>> call = service.getUserData();");

            call.enqueue(new Callback<List<FlatOwner>>() {
                @Override
                public void onResponse(Call<List<FlatOwner>> call, Response<List<FlatOwner>> response) {
                    //Log.i("onResponse", response.message());
                    Log.i("autolog", "onResponse");

                    userList = response.body();
                    Log.i("autolog", "List<User> userList = response.body();");

                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
                    Log.i("autolog", "RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);");

                    layoutManager = new LinearLayoutManager(OwnerListActivity.this);
                    Log.i("autolog", "layoutManager = new LinearLayoutManager(MainActivity.this);");
                    recyclerView.setLayoutManager(layoutManager);
                    Log.i("autolog", "recyclerView.setLayoutManager(layoutManager);");

                    RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(getApplicationContext(), userList);
                    Log.i("autolog", "RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(getApplicationContext(), userList);");
                    recyclerView.setAdapter(recyclerViewAdapter);
                    Log.i("autolog", "recyclerView.setAdapter(recyclerViewAdapter);");
                }

                @Override
                public void onFailure(Call<List<FlatOwner>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        }catch (Exception e) {Log.i("autolog", "Exception");}*/

        System.out.println("I am here ...<Owner List Activity>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getFlatowner().enqueue(new Callback<List<FlatOwner>>() {
            @Override
            public void onResponse(Call<List<FlatOwner>>call, Response<List<FlatOwner>> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    userList = response.body();
                    Log.i("autolog", "List<User> userList = response.body();");

                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
                    Log.i("autolog", "RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);");

                    layoutManager = new LinearLayoutManager(OwnerListActivity.this);
                    Log.i("autolog", "layoutManager = new LinearLayoutManager(MainActivity.this);");
                    recyclerView.setLayoutManager(layoutManager);
                    Log.i("autolog", "recyclerView.setLayoutManager(layoutManager);");

                    RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(getApplicationContext(), userList);
                    Log.i("autolog", "RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(getApplicationContext(), userList);");
                    recyclerView.setAdapter(recyclerViewAdapter);
                    Log.i("autolog", "recyclerView.setAdapter(recyclerViewAdapter);");
                    Toast.makeText(getApplicationContext(), "Flat Owner Details Updated", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<FlatOwner>>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
    }
}