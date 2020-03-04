package net.simplifiedcoding.navigationdrawerexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Profile_Renter_Activity extends AppCompatActivity {

    ImageView mProfileImage;
    //  @BindView(R.id.profile_img)
    ImageView mProfileSImage;
    // @BindView(R.id.app_bar)
    //Toolbar toolbar;
    TextView rent_name,rent_phone,rent_email,rent_flatnumner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_renter);

        Intent intent = getIntent();
        String rname = intent.getStringExtra("rentname");
        String rnumber = intent.getStringExtra("rentflatnumber");
        String rphone = intent.getStringExtra("rentphonno");
        String remail = intent.getStringExtra("rentemailid");


        rent_name=(TextView)findViewById(R.id.renter_name);
        rent_flatnumner=(TextView)findViewById(R.id.renter_flatnumber);
        rent_phone=(TextView)findViewById(R.id.renter_ch_no);//owner_ph_no_work);//owner_ph_no);//owner_ph_no_work);//
        rent_email=(TextView)findViewById(R.id.renter_email_id);

        rent_name.setText(rname);
        rent_flatnumner.setText(rnumber);
        rent_phone.setText(rphone);
        rent_email.setText(remail);


        mProfileImage =(ImageView) findViewById(R.id.profile_bg_main);
        mProfileImage.setImageResource(R.drawable.omd2image);
        mProfileSImage=(ImageView) findViewById(R.id.profile_img_main);
        Picasso.with(this).load(R.drawable.omd2image).centerCrop().fit().into(mProfileImage);
        Picasso.with(this).load(R.drawable.omd2image).centerCrop().fit().into(mProfileSImage);
        mProfileSImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent opa = new Intent(v.getContext(), Edit_Renter_Profile.class);

                opa.putExtra("rentname", rent_name.getText());
                opa.putExtra("rentflatnumber", rent_flatnumner.getText());
                opa.putExtra("rentphonno", rent_phone.getText());
                opa.putExtra("rentemailid", rent_email.getText());
                v.getContext().startActivity(opa);
                //startActivity(new Intent(getApplicationContext(), Edit_Owner_Profile.class));
            }
        });
    }
}
