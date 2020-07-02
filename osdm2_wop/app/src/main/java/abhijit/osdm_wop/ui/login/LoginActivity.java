package abhijit.osdm_wop.ui.login;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import abhijit.osdm_wop.AppSettingsData;
import abhijit.osdm_wop.MainActivity;
import abhijit.osdm_wop.R;
import abhijit.osdm_wop.models.Admin;
import abhijit.osdm_wop.models.FlatOwner;
import abhijit.osdm_wop.rettrofitsupport.RetrofitClient;
import abhijit.osdm_wop.service.HerokuService;

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


    SharedPreferences settings;

     private EditText usernameEditText ;
     private EditText passwordEditText;
     private Button loginButton ;
     private ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
       // if(sharedPref.getString("ApartmentID", null).length()<=1)
        //if(settings.getString("apartmentid",null).length()<=2)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
        //       .get(LoginViewModel.class);
       // requestSmsPermission();

        settings = this.getSharedPreferences(AppSettingsData.PREFS_NAME, MODE_PRIVATE);
        System.out.println("SHARED PREFERANCE DTATA---------------------"+settings.getString("apartmentid",null));
        if(settings.getString("apartmentid",null)==null)
            showDialog();
        else
        {
            System.out.println("SHARED PREFERANCE DTATA- loginstatus--------------------"+settings.getString("loginstatus",null));

            System.out.println("SHARED PREFERANCE DTATA-----loginrole----------------"+settings.getString("loginrole",null));

            if((settings.getString("loginstatus",null)!=null)&(settings.getString("loginrole",null)!=null))
            {
                Intent opa = new Intent(getApplicationContext(), MainActivity.class);
                opa.putExtra("loginrule", settings.getString("loginrole",null));
                startActivity(opa);
                finish();

            }

            AppSettingsData.setApartmentID(settings.getString("apartmentid",null));
            Admin admin=new Admin();
            System.out.println("Apartment id ad starting Login Activity------->"+AppSettingsData.getApartmentID());
            admin.setApartmentid(AppSettingsData.getApartmentID());
            List<Admin> admin_re = GetAdmins(admin);
        }

    }

    public void CreateLoginUI()
    {

        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        loginButton = (Button) findViewById(R.id.login);
        loadingProgressBar = (ProgressBar) findViewById(R.id.loading);
        requestExtStoragePermission();

        System.out.println("i am in login activity---onCreate  1");
        loginButton.setEnabled(true);

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
                admin.setApartmentid(AppSettingsData.getApartmentID());
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

                System.out.println("Apartment id at Admin class ---> "+ AppSettingsData.getApartmentID());
                flt.setApartmentid(AppSettingsData.getApartmentID());
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
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString("loginstatus", "success");
                        editor.putString("loginrole", loginrole);
                        editor.apply();
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


            } else {

                // No explanation needed, we can request the permission.
                System.out.println("In side EXT Permission >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>----1-3");

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS_EXT);

              ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CALL_PHONE,Manifest.permission.SEND_SMS},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

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




    public List<Admin> GetAdmins(Admin admin) {

        String url = "https://postgres2322.herokuapp.com/api/v1/";
        HerokuService apiService = RetrofitClient.getApiService();
        System.out.println("the Response  >>>>>. in login activity "+url+ "apartment_id :"+admin.getApartmentid());
        apiService.getAdmins(admin).enqueue(new Callback<List<Admin>>() {
            @Override
            public void onResponse(Call<List<Admin>>  call, Response<List<Admin>>  response) {
                if(response.isSuccessful()) {
                    System.out.println("the Response  >>>>>. in  GetAdmins login activity " + response.body().toString());
                    System.out.println("the Response  >>>GetAdmins>> SUCCESS>>>>>>");
                    admin_s = response.body();
                    response_status = true;
                    CreateLoginUI();
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
           System.out.println("Inside Check Login  for loop  >>>>>>>>>>    Usrename: "+username+"  Password: "+password);

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

        System.out.println("Inside Check Login  at the end >>>>>>>>>>    Usrename: "+username+"  Password: "+password);

        return false;
    }


    public String getLogintype(Admin admin)
    {
        return admin.getAdmin_rule().toString();
    }

    public void showDialog()
    {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();



        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        TextView tv=(TextView)dialogView.findViewById(R.id.textView);
        tv.setText("Enter your Apartment Id");
        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        editText.setHint("Enter Shared Apartment ID");
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getText().toString().length()>1) {
                    dialogBuilder.dismiss();
                }
                else
                {
                    dialogBuilder.dismiss();
                    showDialog();
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String apartmentid=editText.getText().toString();

                if(!apartmentid.equals(apartmentid.toUpperCase())) {
                    apartmentid = apartmentid.toUpperCase();
                }
                if(apartmentid.length()>1) {
                    AppSettingsData.setApartmentID(apartmentid);
                    Admin admin = new Admin();
                    System.out.println("Apartment id ad starting Login Activity------->" + AppSettingsData.getApartmentID());
                    admin.setApartmentid(AppSettingsData.getApartmentID());
                    List<Admin> admin_re = GetAdmins(admin);

                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("apartmentid", AppSettingsData.getApartmentID());
                    editor.apply();
                    dialogBuilder.dismiss();
                }
                else
                {
                    dialogBuilder.dismiss();
                    showDialog();

                }
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(true);
    }
    @Override
    public void onStart() {
        super.onStart();
    }



}
