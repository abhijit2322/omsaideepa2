package abhijit.osdm2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
/*
public class SupportListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_list);
    }
}*/
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import abhijit.osdm2.models.SuppTable;
import abhijit.osdm2.rettrofitsupport.RetrofitClient;
import abhijit.osdm2.service.HerokuService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SupportListActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private List<SuppTable> userList = null;
    private ListView lv = null;//new ListView();
    private SuppTable support = null;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 123;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Support Team List");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_list_main_activity);
        lv = (ListView) findViewById(R.id.user_list);
        // ArrayList userList = getListData();
        List<SuppTable> userLists = getSupportList();
        System.out.println("after ..........onResponse>>>>>>>>>>>>>>>>>>>>>>>>>>" + userList);
    }

    public void showPopup(View v, SuppTable supp) {
        PopupMenu popup = new PopupMenu(SupportListActivity.this, v);
        support = supp;
        popup.setOnMenuItemClickListener(SupportListActivity.this);
        if (AppSettingsData.GetRule().toString().contains("admin")) {
            popup.getMenuInflater().inflate(R.menu.action_support_menu, popup.getMenu());
        } else {
            popup.getMenuInflater().inflate(R.menu.action_support_menu_others, popup.getMenu());
        }
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle() + " id  " + item.getItemId(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.support_add:
                // do your code
                System.out.println("Inside on menu item list check---------------------------------------");
                Intent opa = new Intent(getApplicationContext(), UpdateSupportTeam.class);
                opa.putExtra("s_name", "Abhijit");
                opa.putExtra("s_type", "Electrician");
                opa.putExtra("s_phone", "9768564477");
                getApplicationContext().startActivity(opa);
                return true;
         /*   case R.id.update:
                // do your code
                return true;*/
            case R.id.support_delete:
                // do your code
                return true;
            case R.id.support_call:

                System.out.println("Inside on menu item <support_call> list check-- call number: " + support.getCphoneno() + " Calling Person:" + support.getName());
                // do your code
                //CheckCallPermission(support.getCphoneno());
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + support.getCphoneno()));
                    startActivity(intent);
                }
                catch(SecurityException e){System.out.println(e.getMessage());}

                return true;
            case R.id.support_sms:
                // do your code
                return true;
            case R.id.support_whatsapp:
                // do your code
                return true;
            case R.id.support_call_others:
                // do your code
                System.out.println("Inside on menu item <support_call> list check-- call number: "+ support.getCphoneno()+" Calling Person:"+support.getName());
               // CheckCallPermission(support.getCphoneno());
                try {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + support.getCphoneno()));
                    startActivity(intent);
                }
                catch(SecurityException e){System.out.println(e.getMessage());}
                return true;
            case R.id.support_sms_others:
                // do your code
                return true;
            case R.id.support_whatsapp_others:
                // do your code
                return true;
            default:
                return false;
        }
    }
   private List<SuppTable> getSupportList() {
        /*
        Log.i("autolog", "getUserList");
        System.out.println("getSupportList>>>>>>>>>>>>>>>>>(1))");
        try {
            String url = "https://postgres2322.herokuapp.com/support/";
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
            System.out.println("getSupportList>>>>>>>>>>>>>>>>>(2))");

            Call<List<SupportTable>> call = service.getSupportList();
            Log.i("autolog", "Call<List<User>> call = service.getUserData();");
            System.out.println("getSupportList>>>>>>>>>>>>>>>>>(3))");
            call.enqueue(new Callback<List<SupportTable>>() {
                @Override
                public void onResponse(Call<List<SupportTable>> call, Response<List<SupportTable>> response) {
                    //Log.i("onResponse", response.message());
                   userList = response.body();

                    System.out.println("onResponse>>>>>>>>>>>   4  >>>>>>>>>>>>>>"+userList.size());

                    final ListView lv = (ListView) findViewById(R.id.user_list);
                    lv.setAdapter(new SupportListAdapter(getApplicationContext(), userList));
                    System.out.println("getSupportList>>>>>>>>>>>>>>>>>(5))");
                    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                            SupportListItem user = (SupportListItem) lv.getItemAtPosition(position);
                            Toast.makeText(SupportListActivity.this, "Selected :" + " " + user.getName()+", "+ user.getLocation(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<SupportTable>> call, Throwable t) {
                    Log.i("autolog", t.getMessage());
                }
            });
        }catch (Exception e) {Log.i("autolog", "Exception");}*/
        System.out.println("I am here ...<Monthly expence Class>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getSupportList().enqueue(new Callback<List<SuppTable>>() {
            @Override
            public void onResponse(Call<List<SuppTable>> call, Response<List<SuppTable>> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    userList = response.body();

                    System.out.println("onResponse>>>>>>>>>>>   4  >>>>>>>>>>>>>>"+userList.size()+userList.get(0).getCphoneno());


                    lv.setAdapter(new SupportListAdapter(getApplicationContext(), userList));
                    System.out.println("getSupportList>>>>>>>>>>>>>>>>>(5))>>>>>>>>>>>>>>>>>>>"+AppSettingsData.GetRule().toString());
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> a, View v, int position, long id) {
                                SuppTable supp=(SuppTable) a.getAdapter().getItem(position);
                                System.out.println("onItemClick --< ON Response >-- Support list" + position+" item name  ph:"+supp.getCphoneno()+" item name name"+supp.getName()+" item name type"+supp.getType());
                                /// popupWindowDogs.showAsDropDown(v, -5, 0);
                                showPopup(v,supp);

                                // SupportListItem user = (SupportListItem) lv.getItemAtPosition(position);
                                // Toast.makeText(SupportListActivity.this, "Selected :" + " " + user.getName()+", "+ user.getLocation(), Toast.LENGTH_SHORT).show();
                            }

                        });

                }
            }
            @Override
            public void onFailure(Call<List<SuppTable>>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
        System.out.println("onResponse>>>>>>>>>>>>>>>>>final return>>>>>>>>>");
        return userList;
    }

/*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //  Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.action_support_menu, menu);
        System.out.println("ACTION BAR ADD  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("ACTION BAR ADD  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+item.getItemId());
        switch(item.getItemId()) {
            case R.id.add:
                //add the function to perform here
                System.out.println("ACTION BAR ADD  >>>>>>>>>>>>>>>>>>sendviamail>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+item.getItemId());
                //ShareViaEmail(myPdfUrl);
                return(true);
            case R.id.update:
                //add the function to perform here
                System.out.println("ACTION BAR RESET  >>>>>>>>>>>>>>>>>>>>>>>>>store>>>>>>>>>>>>>>>>>>>>>>>>>>>"+item.getItemId());
                return(true);
            case R.id.delete:
                //add the function to perform here
                System.out.println("ACTION BAR ABOUT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sharewhatsapp>>>>>>>>>>>>>>>>>>>>>"+item.getItemId());
                return(true);
        }
        return(super.onOptionsItemSelected(item));
    }
*/

public void CheckCallPermission(String phno)
{

    System.out.println("  CheckCallPermission================================================================ 1");
    if ((ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE))
            != PackageManager.PERMISSION_GRANTED) {
        System.out.println("  CheckCallPermission================================================================ 2");

// Permission is not granted
// Should we show an explanation?

        if (ActivityCompat.shouldShowRequestPermissionRationale(this,"android.permission.CALL_PHONE") ||
                ActivityCompat.shouldShowRequestPermissionRationale(this,"android.permission.CALL_PHONE")) {

            // Show an explanation to the user *asynchronously* -- don't block
            // this thread waiting for the user's response! After the user
            // sees the explanation, try again to request the permission.
            System.out.println("  CheckCallPermission================================================================ 3");

        } else {

            // No explanation needed; request the permission
            System.out.println("  CheckCallPermission================================================================ 4");
            ActivityCompat.requestPermissions(this,
                    new String[]{"android.permission.CALL_PHONE"},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);



            // REQUEST_CODE is an
            // app-defined int constant. The callback method gets the
            // result of the request.
        }
    }

    else {
        // Permission has already been granted
        System.out.println("  CheckCallPermission================================================================ 5");
        try {
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:" + "9343077086"));
            startActivity(intent);
        }
        catch(SecurityException e){System.out.println(e.getMessage());}
    }


}
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        System.out.println("  Permission code >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+requestCode);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted
                    // sendSms(phone, message);
                    //  SendSMS();
                    try {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:" + "9343077086"));
                        startActivity(intent);
                    }
                    catch(SecurityException e){System.out.println(e.getMessage());}

                } else {
                    // permission denied
                }

                break;
         /*   case MY_PERMISSIONS_REQUEST_READ_CONTACTS_EXT:
            {

            }
            break;
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS_CALL:
            {

            }
            break;*/
        }
    }
}