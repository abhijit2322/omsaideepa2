package net.simplifiedcoding.navigationdrawerexample;

import android.app.Dialog;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Enter_Maintance_cost extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    CRUD crud = new CRUD();
    Dialog d;

    String Pdf_string="";
    int startingbalance=0;
    int total_expnd=0;
    int remainingcost=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mnt_activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        lv = (ListView) findViewById(R.id.lv);

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

                displayInputDialog(-1);
            }
        });
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

                //DELETE
             /*   if (crud.delete(pos)) {
                    nameEditTxt.setText("");
                    adapter = new ArrayAdapter<String>(Enter_Maintance_cost.this, android.R.layout.simple_list_item_1, crud.getNames());
                    lv.setAdapter(adapter);
                }*/
             String s;
             int i=0;
                System.out.println("List -----------------display-----before ------"+lv.getScrollBarSize()+"   ");
                int ln=((ArrayAdapter) lv.getAdapter()).getCount();
             for(i=0;i<ln;i++){
                 final String item = (String) lv.getItemAtPosition(i);
                 System.out.println("List -----------------display----item-------"+item);
               String u= ((ArrayAdapter) lv.getAdapter()).getItem(i).toString();
                 System.out.println("List -----------------display-----u------"+u);
                 Pdf_string=u+":"+Pdf_string+":";


             }
                remainingcost=startingbalance-total_expnd;
                Intent opa = new Intent(v.getContext(), PdfMainActivity.class);
                opa.putExtra("pdfString", Pdf_string);
                opa.putExtra("total_cost", Integer.toString(total_expnd));
                opa.putExtra("start_val", Integer.toString(startingbalance));
                opa.putExtra("remain_val", Integer.toString(remainingcost));
                v.getContext().startActivity(opa);
                System.out.println("Complite String -------"+Pdf_string);

            }
        });


        d.show();
    }
}