package abhijit.osdm2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Profile_MainActivity extends AppCompatActivity {

    @BindView(R.id.profile_bg_main)
    ImageView mProfileImage;
    @BindView(R.id.profile_img_main)
    ImageView mProfileSImage;
   // @BindView(R.id.app_bar)
   // Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity_main);
        ButterKnife.bind(this);
        System.out.println("Abhijit its coming here -1 >>>>>>>>>>>>>>>>>>>>>>");
       // setSupportActionBar(toolbar);
       /* getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);*/
       // Picasso.with(this).load(R.drawable.omd2image).centerCrop().fit().into(mProfileImage);
        //Picasso.with(this).load(R.drawable.omd2image).centerCrop().fit().into(mProfileSImage);

        Picasso.with(this).load(R.mipmap.omd2image).centerCrop().fit().into(mProfileImage);
        Picasso.with(this).load(R.mipmap.omd2image).centerCrop().fit().into(mProfileSImage);
        System.out.println("Abhijit its coming here -2 >>>>>>>>>>>>>>>>>>>>>>");
         mProfileSImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        System.out.println("Abhijit its coming here -3 >>>>>>>>>>>>>>>>>>>>>>");
    }

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }*/
}
