package abhijit.osdm2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import abhijit.osdm2.models.Renter;
import abhijit.osdm2.rettrofitsupport.RetrofitClient;
import abhijit.osdm2.service.HerokuService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Edit_Renter_Profile extends AppCompatActivity {
    Button update,delete,back;
    EditText rname,rfnumber,rcontact,remail;
    Renter renter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__renter__profile);


            update = (Button) findViewById(R.id.update);
            delete = (Button) findViewById(R.id.delete);
            back = (Button) findViewById(R.id.back);

        Intent intent = getIntent();
        String r_name = intent.getStringExtra("rentname");
        String r_number = intent.getStringExtra("rentflatnumber");
        String r_phone = intent.getStringExtra("rentphonno");
        String r_email = intent.getStringExtra("rentemailid");

            rname=(EditText)findViewById(R.id.editText1);
            rfnumber=(EditText)findViewById(R.id.editText2);
            rcontact=(EditText)findViewById(R.id.editText3);
            remail=(EditText)findViewById(R.id.editText4);
            renter=new Renter();

            rname.setHint(r_name);
            rfnumber.setText(r_number);
            rcontact.setHint(r_phone);
            remail.setHint(r_email);

            update.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // your handler code here
                    renter.setEmail(remail.getText().toString());
                    renter.setFlatnumber(rfnumber.getText().toString());
                    renter.setRentercontactno(rcontact.getText().toString());
                    renter.setRentername(rname.getText().toString());

                    Toast.makeText(getApplicationContext(), "update button clicked ", Toast.LENGTH_SHORT).show();
                    System.out.println(""+renter.getRentername()+" "+renter.getEmail()+" "+renter.getFlatnumber()+" "+renter.getRentercontactno());
                    UpdateRentowner(renter);
                }
            });

            back.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent opa = new Intent(v.getContext(), RenterListActivity.class);
                    v.getContext().startActivity(opa);
                }
            });

        }
        public void UpdateRentowner(Renter renter)
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
            apiService.updaterentowner(renter).enqueue(new Callback<Renter>() {
                @Override
                public void onResponse(Call<Renter> call, Response<Renter> response) {
                    System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                    if(response.isSuccessful()) {
                        Renter user1 = response.body();
                        Toast.makeText(getApplicationContext(), "Flat Owner Details Updated", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onFailure(Call<Renter>  call, Throwable t) {
                    System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                    // response_status=true;
                }
            });
        }
    }
