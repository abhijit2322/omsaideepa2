package net.simplifiedcoding.navigationdrawerexample;

//import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import net.simplifiedcoding.navigationdrawerexample.R;
import net.simplifiedcoding.navigationdrawerexample.models.FlatOwner;
import net.simplifiedcoding.navigationdrawerexample.service.HerokuService;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Edit_Owner_Profile extends AppCompatActivity {
    Button update,delete,back;
    EditText foname,fofnumber,focontact,foemail;
    FlatOwner flatOwner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_owner_profile_activity_main);

        update = (Button) findViewById(R.id.update);
        delete = (Button) findViewById(R.id.delete);
        back = (Button) findViewById(R.id.back);

        Intent intent = getIntent();
        String oname = intent.getStringExtra("ownername");
        String onumber = intent.getStringExtra("flatnumber");
        String ophone = intent.getStringExtra("ownerphonno");
        String oemail = intent.getStringExtra("owneremailid");

        foname=(EditText)findViewById(R.id.editText1);
        fofnumber=(EditText)findViewById(R.id.editText2);
        focontact=(EditText)findViewById(R.id.editText3);
        foemail=(EditText)findViewById(R.id.editText4);
        flatOwner=new FlatOwner();

        foname.setHint(oname);
        fofnumber.setHint(onumber);
        focontact.setHint(ophone);
        foemail.setHint(oemail);

        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                flatOwner.setEmail(foemail.getText().toString());
                flatOwner.setFlatnumber(fofnumber.getText().toString());
                flatOwner.setOwnercontactno(focontact.getText().toString());
                flatOwner.setOwnername(foname.getText().toString());

                Toast.makeText(getApplicationContext(), "update button clicked ", Toast.LENGTH_SHORT).show();
                System.out.println(""+flatOwner.getOwnername()+" "+flatOwner.getEmail()+" "+flatOwner.getFlatnumber()+" "+flatOwner.getOwnercontactno());
                UpdateFlatowner(flatOwner);
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent opa = new Intent(v.getContext(), OwnerListActivity.class);
                v.getContext().startActivity(opa);
            }
            });

    }
    public void UpdateFlatowner(FlatOwner flatOwner)
    {
        Log.i("autolog", "getUserList");
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


            Call<FlatOwner> call = service.updateflatowner(flatOwner);
            Log.i("autolog", "Call<List<User>> call = service.getUserData();");

            call.enqueue(new Callback<FlatOwner>() {
                @Override
                public void onResponse(Call <FlatOwner> call, Response<FlatOwner> response) {
                    FlatOwner user1 = response.body();
                    Toast.makeText(getApplicationContext(), "Flat Owner Details Updated", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<FlatOwner> call, Throwable t) {

                }
            });
        }catch (Exception e) {Log.i("autolog", "Exception");}
    }
}
