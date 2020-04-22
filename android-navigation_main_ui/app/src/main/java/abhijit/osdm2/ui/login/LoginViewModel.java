package abhijit.osdm2.ui.login;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;
import android.util.Patterns;

import abhijit.osdm2.data.LoginRepository;
import abhijit.osdm2.data.Result;
import abhijit.osdm2.data.model.LoggedInUser;
import abhijit.osdm2.R;
import abhijit.osdm2.models.Admin;
import abhijit.osdm2.service.HerokuService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;
    boolean response_fromapi=false;
    List<Admin> admin_s=null;

    Admin admindata;

    private List<Admin> admindatas=null;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        System.out.println("i am in login activity--LoginViewModel-1 getLoginFormState  ");
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public boolean /*void*/ login(String username, String password) {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login(username, password);

        System.out.println("LoginViewModel--1 (login)   login>>>>>>>>>>>  "+username+"   "+password);

        admindata=new Admin();
        admindata.setAdminname(username);
        admindata.setAdminpassword(password);

        admindatas=GetAdmin(admindata);
        boolean loginst=false;
       // System.out.println("LoginViewModel--1 (login) login<<<<<<<<< size not 00000 ");
      if(admindatas!=null) {
          System.out.println("LoginViewModel--1 (login) login<<<<<<<<< size not 00000 ");

          loginst = true;

      }
       else {
          System.out.println("LoginViewModel--1 (login) login<<<<<<<<<  size zero");

      }
        admindatas=null;

        //System.out.println("LoginViewModel--1 (login) login<<<<<<<<<  "+admin_result.get(0).getAdminname()+ "    pass  "+admin_result.get(0).getAdminpassword()+"    rule  "+admin_result.get(0).getAdmin_rule());


       // if (result instanceof Result.Success)
        if(loginst){

            System.out.println("i am in login activity---1  (login)  login  true.......LoginViewModel.....................");
           // LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            //data.setDisplayName("Abhijit");
            //loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            loginst=false;

            loginResult.setValue(new LoginResult(R.string.login_success));
            return true;
        } else {
            System.out.println("i am in login activity---1  (login)  login  false.......LoginViewModel.....................");
            loginResult.setValue(new LoginResult(R.string.login_failed));
            return false;
            }
    }

    public void loginDataChanged(String username, String password) {
       /* if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else*/
        loginFormState.setValue(new LoginFormState(true));
        System.out.println("I am here .....LoginViewModel.........loginDataChanged"+password);
        if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        System.out.println("I am here ......(login).....LoginViewModel...isUserNameValid"+username);
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        System.out.println("I am here ......(login)...LoginViewModel.....isPasswordValid");
        return password != null && password.trim().length() > 2;//5
    }

    public List<Admin> GetAdmin (Admin admin)
    {
        Log.i("autolog", "getUserList");

        try {
            String url = "https://postgres2322.herokuapp.com/admin/";
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
            Call<List<Admin>> call = service.getAdmin(admin); //its a post...
            Log.i("autolog", "Call<List<User>> call = service.getUserData();");

            call.enqueue(new Callback<List<Admin>>() {
                @Override
                public void onResponse(Call <List<Admin>> call, Response<List<Admin>> response) {
                    admin_s= response.body();
                    System.out.println("I am here ......(onresponse) rules   "+admin_s.size());//+"    "+admindatas.get(0).getAdmin_rule());

                }

                @Override
                public void onFailure(Call<List<Admin>> call, Throwable t) {
                    System.out.println("I am here ......(onresponse) Failure   "+t.getMessage());

                }
            });
        }catch (Exception e) {Log.i("autolog", "Exception:   "+e.getMessage());}

        return admin_s;
    }
}
