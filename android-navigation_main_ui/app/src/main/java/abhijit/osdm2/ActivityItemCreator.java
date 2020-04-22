package abhijit.osdm2;

//import androidx.appcompat.app.AppCompatActivity;

import android.app.ListActivity;
import android.os.Bundle;
import java.util.ArrayList;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


/** Note that here we are inheriting ListActivity class instead of Activity class **/
public class ActivityItemCreator extends ListActivity {

    /** Items entered by the user is stored in this ArrayList variable */
    ArrayList<String> list = new ArrayList<String>();

    /** Declaring an ArrayAdapter to set items to ListView */
    ArrayAdapter<String> adapter;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /** Setting a custom layout for the list activity */
        setContentView(R.layout.activity_list_iteam);

        /** Reference to the button of the layout main.xml */
        Button add = (Button) findViewById(R.id.btnAdd);

        Button submit = (Button) findViewById(R.id.btnsubmit);

        /** Defining the ArrayAdapter to set items to ListView */
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);

        /** Defining a click event listener for the button "Add" */

        OnClickListener listener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText edit1 = (EditText) findViewById(R.id.itemname);
                EditText edit2 = (EditText) findViewById(R.id.itemcost);
                list.add(edit1.getText().toString()+"  Expences in RS: "+edit2.getText().toString());
                edit1.setText("");
                edit2.setText("");
                adapter.notifyDataSetChanged();
            }
        };

        OnClickListener listener_submit = new OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] mStrings = new String[adapter.getCount()];
                for (int i = 0; i < adapter.getCount(); i++) {
                    mStrings[i] = adapter.getItem(i);
                }

           /*     Intent i = new Intent();//(Intent.ACTION_SCREEN_ON);
                for (int y=0; y < adapter.getCount(); y++){
                    i.putExtra(mStrings[y], y);
            }
                startActivity(i);*/



                Toast.makeText(getApplicationContext(),"Finish Button Press "+ mStrings[0],Toast.LENGTH_SHORT).show();
            }
        };

        /** Setting the event listener for the add button */
        add.setOnClickListener(listener);
        submit.setOnClickListener(listener_submit);

        /** Setting the adapter to the ListView */
        setListAdapter(adapter);
       /*OnClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView, View itemView, int itemPosition, long itemId)
            {

            }
        });*/


       /* adapter.getView(final int position, ListView convertView, ViewGroup parent){
            if (convertView == null) {
                convertView = inflater.inflate(layoutRes, parent, false);
            }
            ListView imageView = convertView.findViewById(R.id.myImage);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListView.removeViewAt(position);
                }
            });
        }*/


        }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        System.out.println("Position selected>>>>>>>>>>>>>>>>>>>>>> "+position);
        l.removeViewAt(position);
        runOnUiThread(new Runnable() {
            public void run() {
                adapter.notifyDataSetChanged();
            }
        });
        Toast.makeText(getApplicationContext(),"Position selected "+position,Toast.LENGTH_SHORT).show();
        System.out.println("Position selected "+position);
        //--do stuff--
    }
}

