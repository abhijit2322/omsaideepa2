package abhijit.osdm2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import abhijit.osdm2.models.SuppTable;
import abhijit.osdm2.rettrofitsupport.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import abhijit.osdm2.service.HerokuService;

public class UpdateSupportTeam extends AppCompatActivity {
    Button add,delete,back;
    EditText sname,stype,s_contact;
    SuppTable s_table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_edit__renter__profile);
        setContentView(R.layout.activity_update_support_team);

        add = (Button) findViewById(R.id.add_support);
        delete = (Button) findViewById(R.id.delete_support);
        back = (Button) findViewById(R.id.back_support);

        Intent intent = getIntent();
        String s_name = intent.getStringExtra("s_name");
        String s_type = intent.getStringExtra("s_type");
        String s_phone = intent.getStringExtra("s_phone");


        sname=(EditText)findViewById(R.id.support_et1);
        stype=(EditText)findViewById(R.id.support_et2);
        s_contact=(EditText)findViewById(R.id.support_et3);

        s_table=new SuppTable();

        sname.setHint(s_name);
        stype.setText(s_type);
        s_contact.setHint(s_phone);


        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                s_table.setName(sname.getText().toString());
                s_table.setType(stype.getText().toString());
                s_table.setCphoneno(s_contact.getText().toString());
               // s_table.setRentername(rname.getText().toString());

               // Toast.makeText(getApplicationContext(), "update button clicked ", Toast.LENGTH_SHORT).show();
                System.out.println(""+s_table.getType()+" "+s_table.getName()+" "+s_table.getCphoneno());
                UpdateSupport(s_table);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              finish();
            //    Intent opa = new Intent(v.getContext(), RenterListActivity.class);
            //    v.getContext().startActivity(opa);
            /*    Intent i=new Intent(getApplicationContext(), MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);*/
            }
        });

    }
    public void UpdateSupport(SuppTable su_table)
    {
            /*
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


                Call<Renter> call = service.updaterentowner(renter);
                Log.i("autolog", "Call<List<User>> call = service.getUserData();");

                call.enqueue(new Callback<Renter>() {
                    @Override
                    public void onResponse(Call <Renter> call, Response<Renter> response) {
                        Renter user1 = response.body();
                        Toast.makeText(getApplicationContext(), "Renter Owner Details Updated", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Renter> call, Throwable t) {

                    }
                });
            }catch (Exception e) {Log.i("autolog", "Exception");}
            */

        System.out.println("I am here ...<Edit Renter Profile>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.updatersupport(su_table).enqueue(new Callback<SuppTable>() {
            @Override
            public void onResponse(Call<SuppTable> call, Response<SuppTable> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    SuppTable user1 = response.body();
                    Toast.makeText(getApplicationContext(), "Flat Owner Details Updated", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<SuppTable>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
    }
}
