package net.simplifiedcoding.navigationdrawerexample;

import android.app.LauncherActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*
public class SupportListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_list);
    }
}*/
import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ListView;
        import android.widget.Toast;

import net.simplifiedcoding.navigationdrawerexample.models.Renter;
import net.simplifiedcoding.navigationdrawerexample.models.SupportTable;
import net.simplifiedcoding.navigationdrawerexample.service.HerokuService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupportListActivity extends AppCompatActivity {

    private List<SupportTable> userList =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_list_main_activity);
       // ArrayList userList = getListData();
        List<SupportTable> userLists=getSupportList();
        System.out.println("after ..........onResponse>>>>>>>>>>>>>>>>>>>>>>>>>>"+userList);

    }
    private ArrayList getListData() {
        ArrayList<SupportListItem> results = new ArrayList<>();
        SupportListItem user1 = new SupportListItem();
        user1.setName("Suresh Dasari");
        user1.setDesignation("Team Leader");
        user1.setLocation("Hyderabad");
        results.add(user1);
        SupportListItem user2 = new SupportListItem();
        user2.setName("Rohini Alavala");
        user2.setDesignation("Agricultural Officer");
        user2.setLocation("Guntur");
        results.add(user2);
        SupportListItem user3 = new SupportListItem();
        user3.setName("Trishika Dasari");
        user3.setDesignation("Charted Accountant");
        user3.setLocation("Guntur");
        results.add(user3);
        return results;
    }


    private List<SupportTable> getSupportList() {
        Log.i("autolog", "getUserList");
        System.out.println("getSupportList>>>>>>>>>>>>>>>>>(1))");
        try {
            String url = "https://postgres2322.herokuapp.com/support/";
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
            System.out.println("getSupportList>>>>>>>>>>>>>>>>>(2))");

            Call<List<SupportTable>> call = service.getSupportList();
            Log.i("autolog", "Call<List<User>> call = service.getUserData();");
            System.out.println("getSupportList>>>>>>>>>>>>>>>>>(3))");
            call.enqueue(new Callback<List<SupportTable>>() {
                @Override
                public void onResponse(Call<List<SupportTable>> call, Response<List<SupportTable>> response) {
                    //Log.i("onResponse", response.message());
                   userList = response.body();

                    System.out.println("onResponse>>>>>>>>>>>   4  >>>>>>>>>>>>>>"+userList.size());

                    final ListView lv = (ListView) findViewById(R.id.user_list);
                    lv.setAdapter(new SupportListAdapter(getApplicationContext(), userList));
                    System.out.println("getSupportList>>>>>>>>>>>>>>>>>(5))");
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            SupportListItem user = (SupportListItem) lv.getItemAtPosition(position);
                            Toast.makeText(SupportListActivity.this, "Selected :" + " " + user.getName()+", "+ user.getLocation(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<SupportTable>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        }catch (Exception e) {Log.i("autolog", "Exception");}
        System.out.println("onResponse>>>>>>>>>>>>>>>>>final return>>>>>>>>>");
        return userList;
    }
}