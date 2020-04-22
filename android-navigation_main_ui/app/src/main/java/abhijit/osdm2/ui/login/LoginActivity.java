package abhijit.osdm2.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import abhijit.osdm2.AppSettingsData;
import abhijit.osdm2.MainActivity;
import abhijit.osdm2.R;
import abhijit.osdm2.models.Admin;
import abhijit.osdm2.models.FlatOwner;
import abhijit.osdm2.rettrofitsupport.RetrofitClient;
import abhijit.osdm2.service.HerokuService;
import abhijit.osdm2.ui.login.LoginViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    List<Admin> admin_s = null;
    String loginrole = "";
    boolean login_st = false;
    boolean response_status = false;
    String status="";
    String loginrule="";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS_EXT = 1;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS_CALL = 2;

    private static final int PERMISSION_SEND_SMS = 123;
    @Override
    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
        //       .get(LoginViewModel.class);
       // requestSmsPermission();
        requestExtStoragePermission();
      //  requestCallPermission();*/

        List<Admin> admin_re = GetAdmins();
        /*while(response_status==false)
        {

        }*/

        final EditText usernameEditText = (EditText) findViewById(R.id.username);
        final EditText passwordEditText = (EditText) findViewById(R.id.password);
        final Button loginButton = (Button) findViewById(R.id.login);
        final ProgressBar loadingProgressBar = (ProgressBar) findViewById(R.id.loading);
        System.out.println("i am in login activity---onCreate  1");
        loginButton.setEnabled(true);
        /*loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {

                  if (loginFormState == null) {
                    return;
                }
               loginButton.setEnabled(true);//loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }

                System.out.println("i am in login activity  LoginActivity---user name  1"+usernameEditText.getText().toString()+"    password"+passwordEditText.getText().toString());

            }
        });*/
/*
        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                System.out.println("i am in login activity---1 LoginActivity   getLoginResult");
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                    return;
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }  //(here *//*)
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });
*/

      TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
                System.out.println("i am in login activity---1 LoginActivity   beforeTextChanged IME_ACTION_DONE");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
                System.out.println("i am in login activity---1 LoginActivity   onTextChanged IME_ACTION_DONE");
            }

            @Override
            public void afterTextChanged(Editable s) {
                System.out.println("i am in login activity---1 LoginActivity   afterTextChanged");
                // loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                //       passwordEditText.getText().toString());
                login_st = check_login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                System.out.println("i am in login activity---1   LoginActivity  afterTextChanged   password:  " + passwordEditText.getText().toString() + "   User name   " + usernameEditText.getText().toString() + "  status :   " + login_st);
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {

                    System.out.println("i am in login activity---1 LoginActivity   IME_ACTION_DONE");
                    // loginViewModel.login(usernameEditText.getText().toString(),
                    //       passwordEditText.getText().toString());
                    login_st = check_login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                    if (login_st == true)
                        return true;
                    else
                        return false;
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("i am in login activity---1  LoginActivity  setOnClickListener");

                loadingProgressBar.setVisibility(View.VISIBLE);
                // boolean stat=loginViewModel.login(usernameEditText.getText().toString(),
                //         passwordEditText.getText().toString());
                //stat=false;
               // requestSmsPermission();  //its workinig need to copy other place.
               // SendSMS();
                Admin admin=new Admin();
                admin.setAdminname(usernameEditText.getText().toString());
                loginrule=GetLoginRule(admin);
                //loginrule=GetLoginRule_sync(admin);
                FlatOwner flt=new FlatOwner();
                flt.setFlatnumber(usernameEditText.getText().toString());

                System.out.println("After GetLoginRule Login rules for : " +usernameEditText.getText().toString()+" is   "+loginrule);
                login_st = check_login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                if(login_st==false)
                {
                    AppSettingsData.AppDialog("Login Fail","Please Try Again- ","", LoginActivity.this);
                }
                System.out.println("i am in login activity---1  LoginActivity  setOnClickListener  got user name  :" + usernameEditText.getText().toString() + "  password:   " + passwordEditText.getText().toString() + " status  " + login_st);
                //String flat_st=GetFlatStatus_sync(flt);
                String flat_st=GetFlatStatus(flt);
                 System.out.println("i am in login activity---1  LoginActivity  setOnClickListener  AFTER FLAT STATUS "+flat_st);

                if (login_st == true) {
                    login_st = false;
                    //loginrole="admin"; //for testing purpose its kept as Admin by default
                    FlatOwner flt1=new FlatOwner();
                    flt1.setFlatnumber(usernameEditText.getText().toString());
                    AppSettingsData.setLoginFlatOwner(usernameEditText.getText().toString());


                  /* requestSmsPermission();
                   requestExtStoragePermission();
                   requestCallPermission();*/

                    if (usernameEditText.getText().toString().contains(passwordEditText.getText().toString())) {
                        Intent opa = new Intent(v.getContext(), SetNewPassword.class);
                        opa.putExtra("flatnumber",usernameEditText.getText().toString());
                        opa.putExtra("password", passwordEditText.getText().toString());
                        opa.putExtra("loginrole", loginrole);
                        v.getContext().startActivity(opa);
                    }
                    else
                    {

                        Intent opa = new Intent(v.getContext(), MainActivity.class);
                        opa.putExtra("loginrule", loginrole);
                        v.getContext().startActivity(opa);
                        finish();
                    }

                }
            }
        });
    }
    private void requestSmsPermission() {

        // check permission is given

        System.out.println("In side SMS Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----1");
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            // request permission (see result in onRequestPermissionsResult() method)
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS},
                    PERMISSION_SEND_SMS);
            System.out.println("In side SMS Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----1-1");
        } else {
            // permission already granted run sms send
            //sendSms(phone, message);

            System.out.println("In side SMS Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----2");
            AppSettingsData.setSmsPermission(true);
        }
    }
    public void requestCallPermission() {

        System.out.println("In side Call Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----1");
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE))
                != PackageManager.PERMISSION_GRANTED) {

            System.out.println("In side Call Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----1-1");

// Permission is not granted
// Should we show an explanation?

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CALL_PHONE") ||
                    ActivityCompat.shouldShowRequestPermissionRationale(this, "android.permission.CALL_PHONE")) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                System.out.println("In side Call Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----1-2");

            } else {

                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{"android.permission.CALL_PHONE"},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS_CALL);

                System.out.println("In side Call Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----1-3");
                // REQUEST_CODE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            System.out.println("In side Call Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----2");
            AppSettingsData.setCallPermission(true);
        }
    }
    public void requestExtStoragePermission() {
        // Here, thisActivity is the current activity
        System.out.println("In side EXT Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----1");
        if (((ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE))|(ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE))|((ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS)))) != PackageManager.PERMISSION_GRANTED) {
        /*if (ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            System.out.println("In side EXT Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----1-1");*/

            // Should we show an explanation?
           if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)|ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.SEND_SMS)|ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
 /*           if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                System.out.println("In side EXT Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----1-2");*/
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.
                System.out.println("In side EXT Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----1-3");
                /*ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);*/
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS_EXT);

              ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);
               /* ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS_EXT);*/

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
                AppSettingsData.setExtStoragePermission(true);
            }


        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        System.out.println("  Permission code >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+requestCode);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
;

                } else {
                    // permission denied
                }

            break;
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS_EXT:
            {

            }
            break;
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS_CALL:
            {

            }
            break;
        }
    }
    private void updateUiWithUser(LoggedInUserView model) {

        System.out.println("i am in login activity---1    START ......LoginActivity....ACTIVITY AFTER LOGIN");
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        System.out.println("i am in login activity---1   LoginActivity  showLoginFailed");
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }




    public List<Admin> GetAdmins() {

        String url = "https://postgres2322.herokuapp.com/api/v1/";
        HerokuService apiService = RetrofitClient.getApiService();
        System.out.println("the Response  >>>>>. in login activity "+url);
        apiService.getAdmins().enqueue(new Callback<List<Admin>>() {
            @Override
            public void onResponse(Call<List<Admin>>  call, Response<List<Admin>>  response) {
                if(response.isSuccessful()) {
                    System.out.println("the Response  >>>>>. in  GetAdmins login activity " + response.body().toString());
                    System.out.println("the Response  >>>GetAdmins>> SUCCESS>>>>>>");
                    admin_s = response.body();
                    response_status = true;
                }
                else
                {
                    System.out.println("the Response  >>>>> Failed>>>>>>");
                }

            }

            @Override
            public void onFailure(Call<List<Admin>>  call, Throwable t) {
                System.out.println("This in  Failure >>>>>. in login activity "+t.getMessage());
                response_status=true;

            }
        });

        return admin_s;
    }


    public String  GetLoginRule_sync (Admin admin)
    {
        Log.i("autolog", "GetAdminssync");

        try {
            String url = "https://postgres2322.herokuapp.com/admin/";
            Log.i("autolog", "https://postgres2322.herokuapp.com/admin/");
            System.out.println("Its in GetLoginRule_sync...: "+ url);

            Retrofit retrofit = null;
            Log.i("autolog", "retrofit");

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        /*  .client(client)*/
                        .build();
                Log.i("autolog", "build();");
            }
            HerokuService service = retrofit.create(HerokuService.class);
            Call<String> call = service.getLoginrule(admin);
            Response<String> response = call.execute();
            String apiResponse = response.body();
            System.out.println("Sync API response >>>>>>>>>>>>>>>>>>> GetLoginRule_sync "+apiResponse);
        }
        catch (Exception e){ System.out.println("Failure in Sync call ..GetLoginRule_sync... ..:  "+e.getStackTrace().toString()+" Message :"+e.getMessage());}
        return loginrule;
    }

    public String GetLoginRule (Admin admin)
    {
        /*
        Log.i("autolog", "GetLoginRule");
        try {
            String url = "https://postgres2322.herokuapp.com/admin/";
            Log.i("autolog", "https://postgres2322.herokuapp.com/admin/");
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
            Call<String> call = service.getLoginrule(admin); //its a post...
            Log.i("autolog", "Call<List<User>> call = service.getLoginrule();");
                 call.enqueue(new Callback <String>() {
                @Override
                public void onResponse(Call <String> call, Response<String> response) {

                    System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                   if(response.isSuccessful()){
                        loginrule = response.body();
                        System.out.println("I am here ...<LoginActivity>...(onresponse) rules   " + loginrule);//+"    "+admindatas.get(0).getAdmin_rule());
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    System.out.println("I am here ..<LoginActivity>....(onresponse) Failure   "+t.getMessage());

                }
            });
        }catch (Exception e) {Log.i("autolog", "Exception:   "+e.getMessage());}

        return loginrule;

         */
        String url = "https://postgres2322.herokuapp.com/admin/";

        System.out.println("I am here ...<LoginActivity>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getLoginrule(admin).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    System.out.println("the Response  >>>>>. in login activity  GetLoginRule " + response.body().toString());
                    System.out.println("Updated Password the Response  GetLoginRule>>>>> SUCCESS>>>>>>");
                    loginrule = response.body().toString();
                    AppSettingsData.SetRule(loginrule);
                }
            }
            @Override
            public void onFailure(Call<String>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
        return loginrule;
    }

    public String GetFlatStatus_sync(FlatOwner flatOwner) {
        String url = "https://postgres2322.herokuapp.com/flatowner/";
        System.out.println("Sync API response >>>>>>>>>>>>>>>>>>> GetFlatStatus_sync "+url);
        Retrofit retrofit = null;
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        try {
            HerokuService service = retrofit.create(HerokuService.class);
            Call<String> call = service.getFlatStatus(flatOwner);
            Response<String> response = call.execute();
            if(response.isSuccessful()) {
                String apiResponse = response.body();
                System.out.println("Sync API response >>>>>>SUCCESS>>>>>>>>>>>>> GetFlatStatus_sync " + apiResponse);
            }
            else
            {
                System.out.println("Sync API response  NOT SUCCESS >>>>>>>>>>>>>>>>>>> GetFlatStatus_sync "+response.body().toString());
            }
        } catch (Exception e) {

            System.out.println("Failure in Sync call ..GetFlatStatus_sync... ..:  "+e.getStackTrace()+" Message :"+e.getMessage());
        }
        return  status;
    }


    public String GetFlatStatus(FlatOwner flatOwner){

        String url = "https://postgres2322.herokuapp.com/flatowner/";
        System.out.println("ASync API response >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> GetFlatStatus "+url);
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getFlatStatus(flatOwner).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.isSuccessful()) {
                    System.out.println(">>>>>>>>>>>>>>>>>the Response  >>>>>. GetFlatStatus" + response.body().toString());
                    // admin_s= response.body();
                    AppSettingsData.setFlatstatus(response.body().toString());
                    status = response.body().toString();
                    //  response_status=true;
                }
            }

            @Override
            public void onFailure(Call<String>  call, Throwable t) {
                System.out.println("This in  Failure >>>>>. in login activity "+t.getMessage());
                // response_status=true;

            }
        });
        return status;
    }

    public boolean check_login(String username,String password)
    {

        System.out.println("Inside Check Login >>>>>>>>>>    Usrename: "+username+"  Password: "+password);

        if(username.isEmpty()||password.isEmpty())
            return false;

         if(admin_s==null) {
             System.out.println("Inside Check Login >>>>>>>>>> ADMIN NULL");
             finish();
             startActivity(getIntent());
             return false;
         }
       for (Admin s : admin_s) {
            if (s.getAdminname().contains(username) & s.getAdminpassword().contains(password)) {
                System.out.println("aadmin user present..........................."+ username+"    "+password+"   "+s.getAdminname()+"   "+s.getAdmin_rule());
                loginrole=s.getAdmin_rule();
                return true;
            }
            else {
                 System.out.println("aadmin user Not present..........................."+ username+"    "+password+" From DB >>   "+s.getAdminname()+"   "+s.getAdminpassword());
                 //return false;
            }
        }
       return false;
    }


    public String getLogintype(Admin admin)
    {
        return admin.getAdmin_rule().toString();
    }

    public void SendSMS()
    {
            String sperator="; ";
            int i=0;
            String numbers[]={"934307786","9740856007"};
             SmsManager smgr = SmsManager.getDefault();
             while(numbers.length>i)
             {
                 smgr.sendTextMessage(numbers[i],null,"hello how are you",null,null);
                i++;
             }
           // smgr.sendTextMessage("934307786"+sperator+"9740856007",null,"hello how are you",null,null);

       /* Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:934307786;9740856007"));
        smsIntent.putExtra("sms_body", "sms message goes here");
        startActivity(smsIntent);*/


            Toast.makeText(LoginActivity.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
    }


}
