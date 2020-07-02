package abhijit.osdm_wop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.List;

import abhijit.osdm_wop.models.FlatOwner;
import abhijit.osdm_wop.rettrofitsupport.RetrofitClient;
import abhijit.osdm_wop.service.HerokuService;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Owner_Activity extends AppCompatActivity {


    //@BindView(R.id.profile_bg_main)
    ImageView mProfileImage;
  //  @BindView(R.id.profile_img)
    ImageView mProfileSImage;
    String status="";
   // @BindView(R.id.app_bar)
    //Toolbar toolbar;
    String onumber="";
    private static String flatStatus;
     TextView owner_name,owner_phone,owner_email,owner_flatnumner;
    CheckBox chkIos;

    String[]monthName={"Jan.","Feb.","Mar.", "Apr.", "May.", "Jun.", "Jul.",
            "Aug.", "Sept.", "Oct.", "Nov.",
            "Dec."};

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);
    private List<FlatOwner> userList =null;
    String mntStatus_Owner="";

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_main);
        ButterKnife.bind(this);



        Intent intent = getIntent();
        String oname = intent.getStringExtra("ownername");
        onumber = intent.getStringExtra("flatnumber");
        String ophone = intent.getStringExtra("ownerphonno");
        String oemail = intent.getStringExtra("owneremailid");
        String mntStatus = intent.getStringExtra("mntstatus");

        FlatOwner flatOwner=new FlatOwner();
        flatOwner.setFlatnumber(onumber);
        flatOwner.setApartmentid(AppSettingsData.getApartmentID());
        flatStatus=GetFlatStatus(flatOwner);
        mntStatus_Owner=GetMntStatusOwner(flatOwner);

        AppSettingsData.setFlatstatus(flatStatus);

        System.out.println(" flat status :>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> flat number: "+onumber+" status is rentend:  "+flatStatus+" Login Flatowner: "+AppSettingsData.getLoginFlatOwner());
        System.out.println("From application conext -----------------AppSettingsData.getFlatstatus() "+ AppSettingsData.getFlatstatus());
        owner_name=(TextView)findViewById(R.id.owner_name);
        owner_flatnumner=(TextView)findViewById(R.id.owner_flatnumber);
        owner_phone=(TextView)findViewById(R.id.owner_ch_no);//owner_ph_no_work);//owner_ph_no);//owner_ph_no_work);//
        owner_email=(TextView)findViewById(R.id.parent_email);


        owner_name.setText(oname);
        owner_flatnumner.setText(onumber);
        owner_phone.setText(ophone);
        owner_email.setText(oemail);


        mProfileImage =(ImageView) findViewById(R.id.profile_bg_main);
        mProfileImage.setImageResource(R.drawable.omd2image);
        mProfileSImage=(ImageView) findViewById(R.id.profile_img_main);
        Picasso.with(this).load(R.drawable.omd2image).centerCrop().fit().into(mProfileImage);
        Picasso.with(this).load(R.drawable.omd2image).centerCrop().fit().into(mProfileSImage);
        chkIos = (CheckBox) findViewById(R.id.maintance_paid_owner);
        mProfileSImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("GlobalVariables -rule  (Profile_Owner_Activity)>>>>>>>>>>>>>>>>>> is rented  "+flatStatus);

                if(AppSettingsData.GetRule().contains("admin")) {
                    System.out.println("GlobalVariables -rule  (Profile_Owner_Activity)>>>>>>>>>>>>>>>>>>     admin"+"  is rented  "+flatStatus);
                    /*if(flatStatus.contains("no")) */{
                        Intent opa = new Intent(v.getContext(), Edit_Owner_Profile.class);
                        opa.putExtra("ownername", owner_name.getText());
                        opa.putExtra("flatnumber", owner_flatnumner.getText());
                        opa.putExtra("ownerphonno", owner_phone.getText());
                        opa.putExtra("owneremailid", owner_email.getText());
                        v.getContext().startActivity(opa);
                    }
                       /*else if(flatStatus.contains("yes")){
                           Intent opa = new Intent(v.getContext(), Tenent_Update_Activity.class);
                           opa.putExtra("ownername", owner_name.getText());
                           opa.putExtra("flatnumber", owner_flatnumner.getText());
                           opa.putExtra("ownerphonno", owner_phone.getText());
                           opa.putExtra("owneremailid", owner_email.getText());
                           v.getContext().startActivity(opa);
                       }*/
                      /* else
                       {
                           System.out.println("GlobalVariables -rule>>>>>>>>>>>>>>>>>>(Profile_Renter_Activity)    from admin Nowhere ");
                           Intent ir = new Intent(getApplicationContext(), NoWhereActivity.class);
                           startActivity(ir);
                       }*/

                }
                else if(AppSettingsData.GetRule().contains("owner") & onumber.contains(AppSettingsData.getLoginFlatOwner())) {


                    if(flatStatus.contains("yes") & AppSettingsData.getLoginFlatOwner().contains(onumber)) {
                        System.out.println("GlobalVariables -rule>>>>>>>>>>>>>>>>>>(Profile_Owner_Activity)     owner ");
                        Intent opa = new Intent(v.getContext(), Tenent_Update_Activity.class);
                        opa.putExtra("ownername", owner_name.getText());
                        opa.putExtra("flatnumber", owner_flatnumner.getText());
                        opa.putExtra("ownerphonno", owner_phone.getText());
                        opa.putExtra("owneremailid", owner_email.getText());
                        v.getContext().startActivity(opa);
                    }
                    else{
                        if(AppSettingsData.getLoginFlatOwner().contains(onumber)) {
                            Intent opa = new Intent(v.getContext(), Edit_Owner_Profile.class);
                            opa.putExtra("ownername", owner_name.getText());
                            opa.putExtra("flatnumber", owner_flatnumner.getText());
                            opa.putExtra("ownerphonno", owner_phone.getText());
                            opa.putExtra("owneremailid", owner_email.getText());
                            v.getContext().startActivity(opa);
                        }
                        else
                        {
                               /* System.out.println("GlobalVariables -rule>>>>>>>>>>>>>>>>>>(Profile_Renter_Activity)   fromowner   Nowhere ");
                                Intent ir = new Intent(getApplicationContext(), NoWhereActivity.class);
                                startActivity(ir);*/
                            AppSettingsData.AppDialog("Update Fail","Please login with your ",AppSettingsData.getLoginFlatOwner(),Profile_Owner_Activity.this);
                        }
                    }
                }
                else
                {

                    //   System.out.println("GlobalVariables -rule>>>>>>>>>>>>>>>>>>(Profile_Renter_Activity)  Global   Nowhere ");
                    //   Intent ir = new Intent(getApplicationContext(), NoWhereActivity.class);
                    //    startActivity(ir);
                       /* new AlertDialog.Builder(Profile_Owner_Activity.this)
                                .setTitle("You are not the owner of this flat")
                                .setMessage("You are not the Owner of the flat kindly update your flat which is "+AppSettingsData.getLoginFlatOwner())
                                .setCancelable(false)
                                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Whatever...
                                        finish();
                                    }
                                }).show();*/
                    AppSettingsData.AppDialog("Update Fail","Please login with your ",AppSettingsData.getLoginFlatOwner(),Profile_Owner_Activity.this);
                }
                //startActivity(new Intent(getApplicationContext(), Edit_Owner_Profile.class));
            }
        });

        System.out.println("Complite String mntStatus.....................>>-------"+mntStatus_Owner);
        if(mntStatus!=null)
            //if("y".contentEquals(mntStatus))
            if(("y"+"_"+monthName[month]).contentEquals((mntStatus_Owner)))
                chkIos.setChecked(true);


        chkIos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {

                    Toast.makeText(Profile_Owner_Activity.this,
                            "paid Maintance :)", Toast.LENGTH_LONG).show();
                    FlatOwner flato=new FlatOwner();
                    flato.setFlatnumber(owner_flatnumner.getText().toString());
                    //flato.setMaintaincepaid("y");//+"_"+monthName[month]);
                    flato.setMaintaincepaid("y"+"_"+monthName[month]);
                    flato.setApartmentid(AppSettingsData.getApartmentID());
                    UpdateMntStatus_Owner(flato);

                    userList=GetCareTakerPhno(flato);

                    //getCareTackerPh

                }

            }
        });
    }

    public void SendSMS_to_Caretaker(String numbers[],String flatnumber)
    {

        System.out.println("Phone Numbers>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+numbers[0]+"  2nd  "+numbers[1]);
        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address","+91"+numbers[0]+";+91"+numbers[1]);
        smsIntent.putExtra("sms_body","Maintaince paied by "+flatnumber);
        smsIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(smsIntent);

      /*  Uri uriSms = Uri.parse("smsto:+919740856007");
        Intent intentSMS = new Intent(Intent.ACTION_SENDTO, uriSms);
        intentSMS.putExtra("sms_body", "The SMS text");
        startActivity(intentSMS);*/


       /* String sperator="; ";
        int i=0;
       // String numbers[]={"934307786","9740856007"};
        SmsManager smgr = SmsManager.getDefault();
        while((numbers.length-1)>i)
        {
          //  System.out.println ("The Numbers are  "+numbers[i]);
            smgr.sendTextMessage(numbers[i],null,"Maintaince paied by "+flatnumber,null,null);
            i++;
        }*/
        // smgr.sendTextMessage("934307786"+sperator+"9740856007",null,"hello how are you",null,null);

       /* Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:934307786;9740856007"));
        smsIntent.putExtra("sms_body", "sms message goes here");
        startActivity(smsIntent);*/


       // Toast.makeText(Profile_Owner_Activity.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
    }



    @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId() == android.R.id.home) {
                finish();
            }
            return super.onOptionsItemSelected(item);
        }
    public String GetMntStatusOwner(FlatOwner flatowner){
        String url = "https://postgres2322.herokuapp.com/flatowner/";
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.GetMntStatus_Owner(flatowner).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
              //  System.out.println("the Response  >>>>>. in login activity "+response.body().toString());
             //   System.out.println("Updated Password the Response  >>>>> SUCCESS>>>>>>");
                // admin_s= response.body();
                status=response.body().toString();
                //  response_status=true;
                mntStatus_Owner=status;
                if(("y"+"_"+monthName[month]).contentEquals((mntStatus_Owner)))
                    chkIos.setChecked(true);
            }

            @Override
            public void onFailure(Call<String>  call, Throwable t) {
                System.out.println("This in  Failure >>>>>. in login activity "+t.getMessage());
                // response_status=true;

            }
        });
        return status;

    }
    public String GetFlatStatus(FlatOwner flatOwner){
        String url = "https://postgres2322.herokuapp.com/flatowner/";
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getFlatStatus(flatOwner).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
              //  System.out.println("the Response  >>>>>. in login activity "+response.body().toString());
              //  System.out.println("Updated Password the Response  >>>>> SUCCESS>>>>>>");
                // admin_s= response.body();
                 status=response.body().toString();
                //  response_status=true;

            }

            @Override
            public void onFailure(Call<String>  call, Throwable t) {
                System.out.println("This in  Failure >>>>>. in login activity "+t.getMessage());
                // response_status=true;

            }
        });
        return status;
    }
    public void UpdateMntStatus_Owner(FlatOwner flatOwner)
    {
        System.out.println("I am here ...<Monthly expence Class>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.updatemntstatus_owner(flatOwner).enqueue(new Callback<FlatOwner>() {
            @Override
            public void onResponse(Call<FlatOwner> call, Response<FlatOwner> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    FlatOwner user1 = response.body();
                    Toast.makeText(getApplicationContext(), "Renter Owner Details Updated", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<FlatOwner>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
    }
    public List<FlatOwner> GetCareTakerPhno(FlatOwner flatowner)
    {
        System.out.println("I am here ... ..GetCareTakerPhno-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getCareTackerPh(flatowner).enqueue(new Callback<List<FlatOwner>>() {
            @Override
            public void onResponse(Call<List<FlatOwner>> call, Response<List<FlatOwner>> response) {
                System.out.println("I am here ...<GetCareTakerPhno>..GetCareTakerPhno-onResponse");
                if(response.isSuccessful()) {

                    userList = response.body();
                    String[] phnumber = new String[3];
                    int i=0;
                    while(userList.size()>i ){
                        phnumber[i]=userList.get(i).getOwnercontactno();
                        i++;

                    }
                    SendSMS_to_Caretaker(phnumber,owner_flatnumner.getText().toString());
                    Toast.makeText(getApplicationContext(), "Renter Owner Details Updated", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call <List<FlatOwner>>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });

        return userList;
    }
}
