package abhijit.osdm_wop;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import abhijit.osdm_wop.models.Admin;
import abhijit.osdm_wop.models.MonthlyExpence;
import abhijit.osdm_wop.rettrofitsupport.RetrofitClient;
import abhijit.osdm_wop.service.HerokuService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Enter_Maintance_cost extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    CRUD crud = new CRUD();
    Dialog d;
    int is_submitted=0;
    String Pdf_string="";
    List<String> item_name=new ArrayList();
    List<String> item_cost=new ArrayList();
    String Month_year="";
    int startingbalance=0;
    int total_expnd=0;
    int remainingcost=0;
    MonthlyExpence monthlyExpence;
    SharedPreferences settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Current Month Maintaince Expences");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnt_activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        settings = this.getSharedPreferences(AppSettingsData.PREFS_NAME, MODE_PRIVATE);
        if(settings.getString("last_month_balance",null)==null)
            showDialog();
        else
        {
            if(settings.getString("last_month_balance",null)!=null)
            {
                startingbalance=Integer.parseInt(settings.getString("last_month_balance",null));
            }
        }
        is_submitted=0;
        lv = (ListView) findViewById(R.id.lv);
        monthlyExpence=new MonthlyExpence();
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (d != null) {
                    if (!d.isShowing()) {
                        displayInputDialog(i);
                    } else {
                        d.dismiss();
                    }
                }
            }
        });

        final FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(is_submitted==0)
                displayInputDialog(-1);/*-1*/
            }
        });
    }
    public void showDialog()
    {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);
        TextView tv=(TextView)dialogView.findViewById(R.id.textView);
        tv.setText("Enter Previous Month Balance");
        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        editText.setHint("Enter Previous Month Balance");
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editText.getText().toString().length()>1) {
                    dialogBuilder.dismiss();
                }
                else
                {
                    dialogBuilder.dismiss();
                    showDialog();
                }
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lastcost=editText.getText().toString();
                if(lastcost.length()>1) {
                    startingbalance=Integer.parseInt(lastcost);
                    dialogBuilder.dismiss();
                }
                else
                {
                    dialogBuilder.dismiss();
                    showDialog();

                }
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        dialogBuilder.getButton(AlertDialog.BUTTON_POSITIVE).setAllCaps(true);
    }
    private void displayInputDialog(final int pos) {
        d = new Dialog(this);
        d.setTitle("LISTVIEW CRUD");
        d.setContentView(R.layout.mnt_input_dialog);

        final EditText nameEditTxt = (EditText) d.findViewById(R.id.nameEditText);
        final EditText costEdittxt = (EditText) d.findViewById(R.id.price);
        Button addBtn = (Button) d.findViewById(R.id.addBtn);
        Button updateBtn = (Button) d.findViewById(R.id.updateBtn);
        Button deleteBtn = (Button) d.findViewById(R.id.deleteBtn);
        Button submit=(Button) d.findViewById(R.id.submitBtn);


        if (pos == -1) {
            addBtn.setEnabled(true);
            updateBtn.setEnabled(false);
            deleteBtn.setEnabled(false);
        } else {
            addBtn.setEnabled(true);
            updateBtn.setEnabled(true);
            deleteBtn.setEnabled(true);
            nameEditTxt.setText(crud.getNames().get(pos));
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GET DATA

                String name = nameEditTxt.getText().toString();
                String cost = costEdittxt.getText().toString();

                if((name.isEmpty())|| (cost.isEmpty())){
                    AppSettingsData.AppDialog("Warning","Field cant be empty , Please Try Again- ","", Enter_Maintance_cost.this);
                    return;
                }


                item_name.add(name);
                item_cost.add(cost);
                System.out.println("List -------<add>----------get itm cost(1)-----u------"+cost+"Name>>>>>>>>>>>>>>>"+name);

                String[]monthName={"Jan.","Feb.","Mar.", "Apr.", "May.", "Jun.", "Jul.",
                        "Aug.", "Sept.", "Oct.", "Nov.",
                        "Dec."};

                Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);

                System.out.println("Currrent Year>>>>>>>>>>>>>>>>>>"+year);
                System.out.println("Current Month>>>>>>>>>>>>>>>>>>>>>>"+monthName[month]);
                Month_year=monthName[month]+Integer.toString(year);
                System.out.println("Current Month>>>>>>>>>>>>Month_year>>>>>>>>>>"+Month_year );



                if((name.contains("lst"))|| (name.contains("LST"))) {
                    System.out.println("This is last month balance>>>>>>>>>>>>>>>>");
                    startingbalance=Integer.parseInt(cost);
                    costEdittxt.setText("");
                    return;
                }
                total_expnd=total_expnd+Integer.parseInt(cost);

                //VALIDATE
                if (name.length() > 0 && name != null) {
                    //save

                    crud.save(name, cost);

                    nameEditTxt.setText("");
                    costEdittxt.setText("");
                    adapter = new ArrayAdapter<String>(Enter_Maintance_cost.this, android.R.layout.simple_list_item_1, crud.getNames());
                    lv.setAdapter(adapter);
                 //   fillexpdb(monthlyExpence);

                } else {
                    Toast.makeText(Enter_Maintance_cost.this, "Enter Expance Type Name", Toast.LENGTH_SHORT).show();
                }

                //crud.saveTotal(name, cost);
               // ArrayAdapter<String> ad = new ArrayAdapter<String>(Enter_Maintance_cost.this, android.R.layout.simple_list_item_1, crud.getTotal());
                //lv.setAdapter(ad);
            }
        });
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //GET DATA
                String newName = nameEditTxt.getText().toString();
                String cost = costEdittxt.getText().toString();
               final  int ipos=pos;
                if (crud.delete(pos)) {
                    nameEditTxt.setText("");
                    costEdittxt.setText("");
                    adapter = new ArrayAdapter<String>(Enter_Maintance_cost.this, android.R.layout.simple_list_item_1, crud.getNames());
                    lv.setAdapter(adapter);
                }

                //VALIDATE
                if (newName.length() > 0 && newName != null) {
                    //save
                    if (crud.update(ipos, newName, cost)) {
                        nameEditTxt.setText(newName);
                        costEdittxt.setText(cost);
                        adapter = new ArrayAdapter<String>(Enter_Maintance_cost.this, android.R.layout.simple_list_item_1, crud.getNames());
                        lv.setAdapter(adapter);
                    }

                } else {
                    Toast.makeText(Enter_Maintance_cost.this, "Enter Expance Type Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //DELETE
                if (crud.delete(pos)) {
                    nameEditTxt.setText("");
                    adapter = new ArrayAdapter<String>(Enter_Maintance_cost.this, android.R.layout.simple_list_item_1, crud.getNames());
                    lv.setAdapter(adapter);
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_submitted=1;
                //DELETE
             /*   if (crud.delete(pos)) {
                    nameEditTxt.setText("");
                    adapter = new ArrayAdapter<String>(Enter_Maintance_cost.this, android.R.layout.simple_list_item_1, crud.getNames());
                    lv.setAdapter(adapter);
                }*/
             String s;
             int i=0;
                System.out.println("List -----------------display-----before ------"+lv.getScrollBarSize()+"   ");
                if(lv.getAdapter()!=null){
                int ln=((ArrayAdapter) lv.getAdapter()).getCount();
             for(i=0;i<ln;i++){
                 final String item = (String) lv.getItemAtPosition(i);
                 System.out.println("List -----------------display----item-------"+item);
               String u= ((ArrayAdapter) lv.getAdapter()).getItem(i).toString();
                 System.out.println("List -----------------display-----u------"+u);
                 Pdf_string=u+":"+Pdf_string+":";


             }
                remainingcost=startingbalance-total_expnd;



                monthlyExpence.setStartbal(Integer.toString(startingbalance));
                monthlyExpence.setTotalexpence(Integer.toString(total_expnd));
                monthlyExpence.setRemainingbal(Integer.toString(remainingcost));
                monthlyExpence.setMonth(Month_year);
                monthlyExpence.setItemname(item_name);
                monthlyExpence.setItemcost(item_cost);

                SharedPreferences.Editor editor = settings.edit();
                editor.putString("last_month_balance", Integer.toString(remainingcost));
                editor.apply();

                System.out.println("List -----------------get itm cost<last>-----u------"+monthlyExpence.getItemcost()+"Name>>>>>>>>>>>>>>>"+monthlyExpence.getItemname());
                System.out.println("List -----------------get others   rb<last>:   "+monthlyExpence.getRemainingbal()+"start bl:   "+ monthlyExpence.getStartbal()+"mpnthly          "+monthlyExpence.getTotalexpence()+"     "+monthlyExpence.getMonth());

                monthlyExpence.setApartmentid(AppSettingsData.getApartmentID());
                fillexpdb(monthlyExpence);
                Intent opa = new Intent(v.getContext(), PdfMainActivity.class);
                opa.putExtra("pdfString", Pdf_string);
                opa.putExtra("total_cost", Integer.toString(total_expnd));
                opa.putExtra("start_val", Integer.toString(startingbalance));
                opa.putExtra("remain_val", Integer.toString(remainingcost));
                opa.putExtra("currentMonth", Month_year);
                v.getContext().startActivity(opa);
                finish();
                System.out.println("Complite String -------"+Pdf_string);
                d.dismiss();
            }
            } });


       d.show();

    }

    public void fillexpdb(MonthlyExpence monthlyExpence)
    {
       System.out.println("I am here ...<updatemonthlyexp>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.updatemonthlyexp(monthlyExpence).enqueue(new Callback<MonthlyExpence>() {
            @Override
            public void onResponse(Call<MonthlyExpence> call, Response<MonthlyExpence> response) {
                System.out.println("I am here ...<updatemonthlyexp>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    MonthlyExpence user1 = response.body();
                    Toast.makeText(getApplicationContext(), "Flat Owner Details Updated", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<MonthlyExpence>  call, Throwable t) {
                System.out.println("This in  Failure    updatemonthlyexp>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
    }
}