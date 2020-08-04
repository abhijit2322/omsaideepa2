package abhijit.osdm_wop;



import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import abhijit.osdm_wop.models.FlatOwner;
import abhijit.osdm_wop.models.Passbook_model;
import abhijit.osdm_wop.rettrofitsupport.RetrofitClient;
import abhijit.osdm_wop.service.HerokuService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Passbook extends AppCompatActivity implements View.OnClickListener {

    private List<Passbook_model> passbooklist =null;

    String companies[] = {"Google", "Windows", "iPhone", "Nokia", "Samsung",
            "Google", "Windows", "iPhone", "Nokia", "Samsung",
            "Google", "Windows", "iPhone", "Nokia", "Samsung"};
    String os[] = {"Android", "Mango", "iOS", "Symbian", "Bada",
            "Android", "Mango", "iOS", "Symbian", "Bada",
            "Android", "Mango", "iOS", "Symbian", "Bada"};

    String amnt[] = {"Google", "Windows", "iPhone", "Nokia", "Samsung",
            "Google", "Windows", "iPhone", "Nokia", "Samsung",
            "Google", "Windows", "iPhone", "Nokia", "Samsung"};
    String from[] = {"Android", "Mango", "iOS", "Symbian", "Bada",
            "Android", "Mango", "iOS", "Symbian", "Bada",
            "Android", "Mango", "iOS", "Symbian", "Bada"};

    String to[] = {"Google", "Windows", "iPhone", "Nokia", "Samsung",
            "Google", "Windows", "iPhone", "Nokia", "Samsung",
            "Google", "Windows", "iPhone", "Nokia", "Samsung"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passbook);
        Passbook pb=new Passbook();
        Passbook_model pbm =new Passbook_model(0,"","103","","","OSDM2");
        passbookdetails(pbm);

        addHeaders();
        //addData();
    }

    private TextView getTextView(int id, String title, int color, int typeface, int bgColor) {
        TextView tv = new TextView(this);
        tv.setId(id);
        tv.setText(title.toUpperCase());
        tv.setTextColor(color);
        tv.setPadding(40, 40, 40, 40);
        tv.setTypeface(Typeface.DEFAULT, typeface);
        tv.setBackgroundColor(bgColor);
        tv.setLayoutParams(getLayoutParams());
        tv.setOnClickListener(this);
        return tv;
    }

    @NonNull
    private LayoutParams getLayoutParams() {
        LayoutParams params = new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        params.setMargins(2, 0, 0, 2);
        return params;
    }

    @NonNull
    private TableLayout.LayoutParams getTblLayoutParams() {
        return new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
    }

    /**
     * This function add the headers to the table
     **/
    public void addHeaders() {
        TableLayout tl = (TableLayout)findViewById(R.id.table);
        TableRow tr = new TableRow(this);
        tr.setLayoutParams(getLayoutParams());
        tr.addView(getTextView(0, "Date", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "Flat#", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tr.addView(getTextView(0, "Amnt", Color.WHITE, Typeface.BOLD, Color.BLUE));
/*        tr.addView(getTextView(0, "From", Color.WHITE, Typeface.BOLD, Color.BLUE));*/
        tr.addView(getTextView(0, "To", Color.WHITE, Typeface.BOLD, Color.BLUE));
        tl.addView(tr, getTblLayoutParams());
    }

    /**
     * This function add the data to the table
     **/
    public void addData() {
        int numCompanies = companies.length;
        TableLayout tl =(TableLayout) findViewById(R.id.table);
        for (int i = 0; i < numCompanies; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, companies[i], Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + numCompanies, os[i], Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + numCompanies, amnt[i], Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
           /* tr.addView(getTextView(i + numCompanies, from[i], Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));*/
            tr.addView(getTextView(i + numCompanies, to[i], Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tl.addView(tr, getTblLayoutParams());
        }
    }


    public void addData_backed() {
        int numCompanies = passbooklist.size();
        TableLayout tl =(TableLayout) findViewById(R.id.table);
        for (int i = 0; i < numCompanies; i++) {
            TableRow tr = new TableRow(this);
            tr.setLayoutParams(getLayoutParams());
            tr.addView(getTextView(i + 1, passbooklist.get(i).getDate(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + numCompanies,  passbooklist.get(i).getFlat_number(),Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tr.addView(getTextView(i + numCompanies,  passbooklist.get(i).getAmount(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
    /*        tr.addView(getTextView(i + numCompanies,  passbooklist.get(i)., Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
   */         tr.addView(getTextView(i + numCompanies,  passbooklist.get(i).getPaid_to(), Color.WHITE, Typeface.NORMAL, ContextCompat.getColor(this, R.color.colorAccent)));
            tl.addView(tr, getTblLayoutParams());
        }
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        TextView tv =(TextView)findViewById(id);
        if (null != tv) {
            Log.i("onClick", "Clicked on row :: " + id);
            Toast.makeText(this, "Clicked on row :: " + id + ", Text :: " + tv.getText(), Toast.LENGTH_SHORT).show();
        }
    }


    private void passbookdetails(Passbook_model passbook) {
        System.out.println("I am here ...<Passbook Activity >..-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getpassbook(passbook).enqueue(new Callback<List<Passbook_model>>() {
            @Override
            public void onResponse(Call<List<Passbook_model>> call, Response<List<Passbook_model>> response) {
                System.out.println("I am here ...<Passbook   activity > -onResponse");
                if(response.isSuccessful()) {
                    passbooklist = response.body();
                    Log.i("autolog", "List<User> userList = response.body();"+passbooklist.get(0).getAmount());
                    addData_backed();
                    Toast.makeText(getApplicationContext(), "Flat Owner Details Updated"+passbooklist.get(0).getAmount(), Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<Passbook_model>>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });

    }
}