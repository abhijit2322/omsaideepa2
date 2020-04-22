package abhijit.osdm2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import abhijit.osdm2.R;
import abhijit.osdm2.models.User;
import abhijit.osdm2.service.HerokuService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DBCheckActivty extends AppCompatActivity {

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
                .baseUrl("https://postgres2322.herokuapp.com/user/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();



    service = retrofit.create(HerokuService.class);
    //service = retrofit.create(HerokuService.class);

        button.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            Call<ResponseBody> call = service.hello();
//            call.enqueue(new Callback<ResponseBody>();
            Call<User> call = service.getUser();
            call.enqueue(new Callback<User>() {

                @Override
                public void onResponse(Call<User> call, Response<User> res) {
                    try {
                        //String body = response//string();
                        System.out.println(res.body().getCity());
                        textView.setText(res.body().getCity());
                    } catch (Exception e) {
                        e.printStackTrace();
                        textView.setText(e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    t.printStackTrace();
                    textView.setText(t.getMessage());
                }
            });
        }
    });
}
}
