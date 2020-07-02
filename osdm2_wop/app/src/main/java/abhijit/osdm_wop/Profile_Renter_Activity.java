package abhijit.osdm_wop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import abhijit.osdm_wop.models.FlatOwner;
import abhijit.osdm_wop.models.Renter;
import abhijit.osdm_wop.rettrofitsupport.RetrofitClient;
import abhijit.osdm_wop.service.HerokuService;

import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Renter_Activity extends AppCompatActivity {

    ImageView mProfileImage;
    //  @BindView(R.id.profile_img)
    ImageView mProfileSImage;
    // @BindView(R.id.app_bar)
    private List<FlatOwner> userList =null;
    //Toolbar toolbar;
    TextView rent_name, rent_phone, rent_email, rent_flatnumner;
    CheckBox chkIos;

    String[]monthName={"Jan.","Feb.","Mar.", "Apr.", "May.", "Jun.", "Jul.",
            "Aug.", "Sept.", "Oct.", "Nov.",
            "Dec."};

    Calendar c = Calendar.getInstance();
    int year = c.get(Calendar.YEAR);
    int month = c.get(Calendar.MONTH);

    String mnt_status_renter="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_renter);





        Intent intent = getIntent();
        String rname = intent.getStringExtra("rentname");
        String rnumber = intent.getStringExtra("rentflatnumber");
        String rphone = intent.getStringExtra("rentphonno");
        String remail = intent.getStringExtra("rentemailid");
        String mntStatus = intent.getStringExtra("mntstatus");

        Renter renter=new Renter();
        renter.setFlatnumber(rnumber);
        renter.setApartmentid(AppSettingsData.getApartmentID());
        mnt_status_renter=GetMntStatus_Renter(renter);

        rent_name = (TextView) findViewById(R.id.renter_name);
        rent_flatnumner = (TextView) findViewById(R.id.renter_flatnumber);
        rent_phone = (TextView) findViewById(R.id.renter_ch_no);//owner_ph_no_work);//owner_ph_no);//owner_ph_no_work);//
        rent_email = (TextView) findViewById(R.id.renter_email_id);

        rent_name.setText(rname);
        rent_flatnumner.setText(rnumber);
        rent_phone.setText(rphone);
        rent_email.setText(remail);


        mProfileImage = (ImageView) findViewById(R.id.profile_bg_main);
        mProfileImage.setImageResource(R.drawable.omd2image);
        mProfileSImage = (ImageView) findViewById(R.id.profile_img_main);
        Picasso.with(this).load(R.drawable.omd2image).centerCrop().fit().into(mProfileImage);
        Picasso.with(this).load(R.drawable.omd2image).centerCrop().fit().into(mProfileSImage);
        mProfileSImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                System.out.println("GlobalVariables -rule (Profile_Renter_Activity)>>>>>>>>>>>>>>>>>>     "+AppSettingsData.GetRule());

                if(AppSettingsData.GetRule().contains("admin")) {
                    System.out.println("GlobalVariables -rule  (Profile_Renter_Activity)>>>>>>>>>>>>>>>>>>     admin");

                    Intent opa = new Intent(v.getContext(), Edit_Renter_Profile.class);

                    opa.putExtra("rentname", rent_name.getText());
                    opa.putExtra("rentflatnumber", rent_flatnumner.getText());
                    opa.putExtra("rentphonno", rent_phone.getText());
                    opa.putExtra("rentemailid", rent_email.getText());
                    v.getContext().startActivity(opa);
                }
                else if(AppSettingsData.GetRule().contains("owner")&AppSettingsData.getLoginFlatOwner().contains(rent_flatnumner.getText())&AppSettingsData.getFlatstatus().contains("yes"))
                {
                    Intent opa = new Intent(v.getContext(), Tenent_Update_Activity.class);
                    opa.putExtra("ownername", rent_name.getText());
                    opa.putExtra("flatnumber", rent_flatnumner.getText());
                    opa.putExtra("ownerphonno", rent_phone.getText());
                    opa.putExtra("owneremailid", rent_email.getText());
                    v.getContext().startActivity(opa);

                }
                else
                {
                    AppSettingsData.AppDialog("Update Fail","This flat you cant update kindly try flatnumber- ",AppSettingsData.getLoginFlatOwner(),Profile_Renter_Activity.this);
                }


                //startActivity(new Intent(getApplicationContext(), Edit_Owner_Profile.class));
            }
        });


        chkIos = (CheckBox) findViewById(R.id.maintance_paid);


        System.out.println("Currrent Year>>>>>>>>>>>>>>>>>>"+year);
        System.out.println("Current Month>>>>>>>>>>>>>>>>>>>>>>"+monthName[month]);

        System.out.println("Complite String mnt_status_renter.....................>>-------"+mnt_status_renter);
        if(mntStatus!=null)
        if(("y"+"_"+monthName[month]).contentEquals((mnt_status_renter)))
        chkIos.setChecked(true);


        chkIos.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //is chkIos checked?
                if (((CheckBox) v).isChecked()) {
                    String [] phnumber=null;
                    int i=0;
                    Toast.makeText(Profile_Renter_Activity.this,
                            "paid Maintance :)", Toast.LENGTH_LONG).show();
                    Renter renter=new Renter();
                    renter.setFlatnumber(rent_flatnumner.getText().toString());
                    renter.setRmaintaincepaid("y"+"_"+monthName[month]);
                    renter.setApartmentid(AppSettingsData.getApartmentID());
                    UpdateMntStatus(renter);
                    FlatOwner flatowner=new FlatOwner();
                    flatowner.setApartmentid(AppSettingsData.getApartmentID());

                    userList=GetCareTakerPhno(flatowner);

                    //getCareTackerPh
                }

            }
        });
    }


    public void SendSMS_to_Caretaker(String numbers[],String flatnumber)
    {
        String sperator="; ";
        int i=0;
        System.out.println("Phone Numbers>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+numbers[0]+"  2nd  "+numbers[1]);
        Intent smsIntent = new Intent(android.content.Intent.ACTION_VIEW);
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address","+91"+numbers[0]+";+91"+numbers[1]);
        smsIntent.putExtra("sms_body","Maintaince paied by "+flatnumber);
        smsIntent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(smsIntent);

        // String numbers[]={"934307786","9740856007"};
       /* SmsManager smgr = SmsManager.getDefault();
        while(numbers.length-1>i)
        {
            System.out.println("The phone numbers in renter ...................... "+numbers[i]);
            smgr.sendTextMessage(numbers[i],null,"Maintaince paied by "+flatnumber,null,null);
            i++;
        }*/
        // smgr.sendTextMessage("934307786"+sperator+"9740856007",null,"hello how are you",null,null);

       /* Intent smsIntent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:934307786;9740856007"));
        smsIntent.putExtra("sms_body", "sms message goes here");
        startActivity(smsIntent);*/


        Toast.makeText(Profile_Renter_Activity.this, "SMS Sent Successfully", Toast.LENGTH_SHORT).show();
    }

    public String GetMntStatus_Renter(Renter renter){

        System.out.println("I am here ...<Monthly expence Class>..GetLoginRule-RETROFIT");

        HerokuService apiService = RetrofitClient.getApiService();
        apiService.GetMntStatus_renter(renter).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String status="";
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    mnt_status_renter= response.body();
                    if(("y"+"_"+monthName[month]).contentEquals((mnt_status_renter)))
                        chkIos.setChecked(true);
                    Toast.makeText(getApplicationContext(), "Renter Owner Details Updated", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onFailure(Call<String>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
       return mnt_status_renter;
    }
    public void UpdateMntStatus(Renter renter)
    {

        System.out.println("I am here ...<Monthly expence Class>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.updatemntstatus(renter).enqueue(new Callback<Renter>() {
            @Override
            public void onResponse(Call<Renter> call, Response<Renter> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    Renter user1 = response.body();
                    Toast.makeText(getApplicationContext(), "Renter Owner Details Updated", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<Renter>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
    }

    public List<FlatOwner> GetCareTakerPhno(FlatOwner flatowner)
    {
        System.out.println("I am here ...<Monthly expence Class>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getCareTackerPh(flatowner).enqueue(new Callback<List<FlatOwner>>() {
            @Override
            public void onResponse(Call<List<FlatOwner>> call, Response<List<FlatOwner>> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {

                    userList = response.body();
                    String[] phnumber = new String[3];
                    int i=0;
                    while(userList.size()>i ){
                        phnumber[i]=userList.get(i).getOwnercontactno();
                        i++;

                    }
                    SendSMS_to_Caretaker(phnumber,rent_flatnumner.getText().toString());
                    Toast.makeText(getApplicationContext(), "Renter Owner Details Updated", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call <List<FlatOwner>>  call, Throwable t) {
                System.out.println("This in  Failure    GetCareTakerPhno>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });


        return userList;
    }
}