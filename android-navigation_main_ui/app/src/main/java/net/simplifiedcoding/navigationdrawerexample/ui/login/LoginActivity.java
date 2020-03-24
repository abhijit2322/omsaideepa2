package net.simplifiedcoding.navigationdrawerexample.ui.login;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
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

import net.simplifiedcoding.navigationdrawerexample.Edit_Renter_Profile;
import net.simplifiedcoding.navigationdrawerexample.MainActivity;
import net.simplifiedcoding.navigationdrawerexample.R;
import net.simplifiedcoding.navigationdrawerexample.models.Admin;
import net.simplifiedcoding.navigationdrawerexample.service.HerokuService;
import net.simplifiedcoding.navigationdrawerexample.ui.login.LoginViewModel;
import net.simplifiedcoding.navigationdrawerexample.ui.login.LoginViewModelFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    List<Admin> admin_s=null;
    String loginrole="";
    boolean login_st=false;
    boolean response_status=false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
         //       .get(LoginViewModel.class);

        List<Admin> admin_re=GetAdmins();
        /*while(response_status==false)
        {

        }*/

        final EditText usernameEditText =(EditText) findViewById(R.id.username);
        final EditText passwordEditText = (EditText)findViewById(R.id.password);
        final Button loginButton =(Button) findViewById(R.id.login);
        final ProgressBar loadingProgressBar = (ProgressBar)findViewById(R.id.loading);
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
                 login_st=check_login(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                System.out.println("i am in login activity---1   LoginActivity  afterTextChanged   password:  "+passwordEditText.getText().toString()+"   User name   "+usernameEditText.getText().toString()+"  status :   "+login_st);
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
                    login_st=check_login(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                    if(login_st==true)
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

                login_st=check_login(usernameEditText.getText().toString(),passwordEditText.getText().toString());
                System.out.println("i am in login activity---1  LoginActivity  setOnClickListener  got user name  :"+usernameEditText.getText().toString()+ "  password:   "+passwordEditText.getText().toString()+" status  "+login_st);
                if(true)
                {
                    login_st=false;
                    loginrole="admin";
                    Intent opa = new Intent(v.getContext(), MainActivity.class);
                    if(loginrole!="")
                    opa.putExtra("loginrule", loginrole);
                    v.getContext().startActivity(opa);

                }
            }
        });
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

    public List<Admin> GetAdmins ()
    {
        Log.i("autolog", "getUserList");

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
            Call<List<Admin>> call = service.getAdmins(); //its a post...
            Log.i("autolog", "Call<List<User>> call = service.getAdmins();");
            System.out.println("I am here   78..<LoginActivity>....(onresponse)    ");

            call.enqueue(new Callback<List<Admin>>() {
                @Override
                public void onResponse(Call <List<Admin>> call, Response<List<Admin>> response) {
                    admin_s= response.body();
                    response_status=true;
                    System.out.println("I am here ...<LoginActivity>...(onresponse) rules   "+admin_s.size());//+"    "+admindatas.get(0).getAdmin_rule());

                }

                @Override
                public void onFailure(Call<List<Admin>> call, Throwable t) {
                    response_status=true;
                    System.out.println("I am here ..<LoginActivity>....(onresponse) Failure   "+t.getMessage());

                }
            });
        }catch (Exception e) {Log.i("autolog", "Exception:   "+e.getMessage());}

        return admin_s;
    }

    public boolean check_login(String username,String password)
    {
         if(admin_s==null)
            return false;
       for (Admin s : admin_s) {
            if (s.getAdminname().contains(username) & s.getAdminpassword().contains(password)) {
                System.out.println("aadmin user present..........................."+ username+"    "+password+"   "+s.getAdminname()+"   "+s.getAdmin_rule());
                loginrole=s.getAdmin_rule();
                return true;
            }
            else {
                 System.out.println("aadmin user Not present..........................."+ username+"    "+password+"   "+s.getAdminname());
                 //return false;
            }
        }
       return false;
    }


    public String getLogintype(Admin admin)
    {
        return admin.getAdmin_rule().toString();
    }
}
