package abhijit.osdm_wop.DB_tasks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import abhijit.osdm_wop.R;
import abhijit.osdm_wop.service.HerokuService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class OwnerList extends AppCompatActivity {

    private HerokuService service;
    private TextView textView;
    private Button button;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_db);
        textView = (TextView) findViewById(R.id.textView);
        button = (Button) findViewById(R.id.button);


  /*  Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://httpbin.org/")
            .build();
*/

        gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://postgres2322.herokuapp.com/flatowner/getflatowners/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

/*

    service = retrofit.create(HerokuService.class);
    //service = retrofit.create(HerokuService.class);

        button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Call<ResponseBody> call = service.hello();
//            call.enqueue(new Callback<ResponseBody>();
            Call<FlatOwner> call = service.getFlatowner();
            call.enqueue(new Callback<FlatOwner>() {

                @Override
                public void onResponse(Call<FlatOwner> call, Response<FlatOwner> res) {
                    try {
                        //String body = response//string();
                       // System.out.println(res.body().getCity());
                        //textView.setText(res.body().getCity());

                        Toast.makeText(OwnerList.this, "Name : " + res.body().getOwnername() + "\n Email : " + res.body().getEmail(), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                        textView.setText(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<FlatOwner> call, Throwable t) {
                    t.printStackTrace();
                    textView.setText(t.getMessage());
                }
            });
        }
    });*/
}
}
