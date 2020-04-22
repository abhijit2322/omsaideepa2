package abhijit.osdm2.ui.login;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import abhijit.osdm2.MainActivity;
import abhijit.osdm2.R;
import abhijit.osdm2.models.Admin;
import abhijit.osdm2.rettrofitsupport.RetrofitClient;
import abhijit.osdm2.service.HerokuService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SetNewPassword extends AppCompatActivity {

    Button submit,back;
    EditText newpass,repass;
    String loginrole="";
    String r_name="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_new_password);

        Intent intent = getIntent();
        r_name = intent.getStringExtra("flatnumber");
        String r_number = intent.getStringExtra("passwword");
        loginrole=intent.getStringExtra("loginrole");

         submit = (Button) findViewById(R.id.submit_password);
         back = (Button) findViewById(R.id.back_password);

        newpass=(EditText)findViewById(R.id.passeditText1);
        repass=(EditText)findViewById(R.id.passeditText2);
        String p =newpass.getText().toString();


        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
              //updateAdmintable(admintable);
               // this part is working
                if(newpass.getText().toString().contains(repass.getText().toString())) {
                    Admin admin = new Admin();
                    admin.setAdminname(r_name);
                    admin.setAdminpassword(newpass.getText().toString());
                    updateAdmintable(admin);
                }
                else
                {
                    new AlertDialog.Builder(SetNewPassword.this)
                            .setTitle("Wrong password")
                            .setMessage("Your enter password dose not matchs")
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // Whatever...
                                    finish();
                                }
                            }).show();
                }

                    Intent opa = new Intent(v.getContext(), MainActivity.class);
                    if (loginrole != "")
                        opa.putExtra("loginrule", loginrole);
                v.getContext().startActivity(opa);
                finish();


            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                //updateAdmintable(admintable);
            }
        });

    }


    public void updateAdmintable(Admin admin) {
        String url = "https://postgres2322.herokuapp.com/admin/";
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.updateAdminTable(admin).enqueue(new Callback<Admin>() {
            @Override
            public void onResponse(Call<Admin> call, Response<Admin> response) {
                System.out.println("the Response  >>>>>. in login activity "+response.body().toString());
                System.out.println("Updated Password the Response  >>>>> SUCCESS>>>>>>");
               // admin_s= response.body();
              //  response_status=true;


            }

            @Override
            public void onFailure(Call<Admin>  call, Throwable t) {
                System.out.println("This in  Failure >>>>>. in login activity "+t.getMessage());
               // response_status=true;

            }
        });

       // return admin_s;
    }
}
