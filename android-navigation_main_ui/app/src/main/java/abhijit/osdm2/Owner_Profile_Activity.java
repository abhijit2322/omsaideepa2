package abhijit.osdm2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Owner_Profile_Activity extends AppCompatActivity {

    LinearLayout personalinfo, experience, review;
    TextView personalinfobtn, experiencebtn, reviewbtn,ownername,flatnumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_profile_);

        Intent intent = getIntent();
        String str = intent.getStringExtra("ownername");
        String fstr = intent.getStringExtra("flatnumber");

        personalinfo =(LinearLayout) findViewById(R.id.personalinfo);
        experience = (LinearLayout)findViewById(R.id.experience);
        review =(LinearLayout) findViewById(R.id.review);
        ownername=(TextView)findViewById(R.id.ownername);
        flatnumber=(TextView)findViewById(R.id.flatnumber);
        ownername.setText(str.toString());
        flatnumber.setText(fstr.toString());

        personalinfobtn =(TextView)findViewById(R.id.personalinfobtn);
        experiencebtn = (TextView)findViewById(R.id.experiencebtn);
        reviewbtn = (TextView)findViewById(R.id.reviewbtn);
        /*making personal info visible*/
        personalinfo.setVisibility(View.VISIBLE);
        experience.setVisibility(View.GONE);
        review.setVisibility(View.GONE);


        personalinfobtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.VISIBLE);
               // experience.setVisibility(View.GONE);
              //  review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.blue));
               // experiencebtn.setTextColor(getResources().getColor(R.color.grey));
              //  reviewbtn.setTextColor(getResources().getColor(R.color.grey));

            }
        });

        experiencebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.GONE);
                //experience.setVisibility(View.VISIBLE);
               // review.setVisibility(View.GONE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
              //  experiencebtn.setTextColor(getResources().getColor(R.color.blue));
              //  reviewbtn.setTextColor(getResources().getColor(R.color.grey));

            }
        });

        reviewbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                personalinfo.setVisibility(View.GONE);
              //  experience.setVisibility(View.GONE);
              //  review.setVisibility(View.VISIBLE);
                personalinfobtn.setTextColor(getResources().getColor(R.color.grey));
              //  experiencebtn.setTextColor(getResources().getColor(R.color.grey));
              //  reviewbtn.setTextColor(getResources().getColor(R.color.blue));

            }
        });




    }
}
