package net.simplifiedcoding.navigationdrawerexample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Profile_Owner_Activity extends AppCompatActivity {


    //@BindView(R.id.profile_bg_main)
    ImageView mProfileImage;
  //  @BindView(R.id.profile_img)
    ImageView mProfileSImage;
   // @BindView(R.id.app_bar)
    //Toolbar toolbar;
     TextView owner_name,owner_phone,owner_email,owner_flatnumner;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.profile_activity_main);
            ButterKnife.bind(this);
            /*setSupportActionBar(toolbar);
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
*/



            Intent intent = getIntent();
            String oname = intent.getStringExtra("ownername");
            String onumber = intent.getStringExtra("flatnumber");
            String ophone = intent.getStringExtra("ownerphonno");
            String oemail = intent.getStringExtra("owneremailid");


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
            mProfileSImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent opa = new Intent(v.getContext(), Edit_Owner_Profile.class);

                    opa.putExtra("ownername", owner_name.getText());
                    opa.putExtra("flatnumber", owner_flatnumner.getText());
                    opa.putExtra("ownerphonno", owner_phone.getText());
                    opa.putExtra("owneremailid", owner_email.getText());
                    v.getContext().startActivity(opa);
                    //startActivity(new Intent(getApplicationContext(), Edit_Owner_Profile.class));
                }
            });
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId() == android.R.id.home) {
                finish();
            }
            return super.onOptionsItemSelected(item);
        }

}
