package abhijit.osdm_wop;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.graphics.Typeface;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import abhijit.osdm_wop.R;
import abhijit.osdm_wop.models.BBC_News;
import abhijit.osdm_wop.models.ComplainBoxModel;
import abhijit.osdm_wop.models.FirebaseChildDataStore;

/**
 * Created by Belal on 18/09/16.
 */
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

@SuppressWarnings("MagicConstant")
public class Menu1 extends Fragment {

    private Activity mContext;
    private ListView listView,comView;
    private Button button;
    DashBoardNewsListAdapter customCountryList;
    String[] countryNames= new String[200];
    String[] capitalNames=new String[200];;
    String[] imageid= new String[200];
    String[] newsurl= new String[200];
    String[] newsdesc= new String[500];
    ComplainBoxModel complainBoxModel;
    private DatabaseReference myRef;
    List<ComplainBoxModel> clist;
    List<FirebaseChildDataStore> pclist;
    ArrayAdapter<String> arrayAdapter;

    private FirebaseAuth mAuth;

    String bbc_url = "https://newsapi.org/v1/articles?source=bbc-news&sortBy=top&apiKey=4dbc17e007ab436fb66416009dfb59a8";
    private String countryNames1[] = {
            "India",
            "China",
            "Nepal",
            "Bhutan"
    };

    private String capitalNames1[] = {
            "Delhi",
            "Beijing",
            "Kathmandu",
            "Thimphu"
    };
    private Integer imageid1[] = {
            R.drawable.add_green,
            R.drawable.add_green,
            R.drawable.add_green,
            R.drawable.add_green

    };


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //returning our layout file
        //change R.layout.yourlayoutfilename for each of your fragments

        mAuth = FirebaseAuth.getInstance();


        View rooView=inflater.inflate(R.layout.fragment_menu_1, container, false);
        clist=new ArrayList<>();
        pclist=new ArrayList<>();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
       // return inflater.inflate(R.layout.fragment_menu_1, container, false);
        new NewsCollector().execute();
        // Setting header
        TextView textView = new TextView(container.getContext());
        textView.setTypeface(Typeface.DEFAULT_BOLD);
        textView.setText("Top 5 News of the world");

          listView=(ListView)rooView.findViewById(R.id.news_list);
         listView.addHeaderView(textView);
        startSignIn(rooView);
        // For populating list data
        customCountryList = new DashBoardNewsListAdapter(mContext, countryNames, capitalNames, imageid);
        listView.setAdapter(customCountryList);
        Get_FirebaseData(rooView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
             //   Toast.makeText(mContext,"You Selected "+countryNames[position-1]+ " as Country",Toast.LENGTH_SHORT).show();
//NewsViewActivity
                Intent opa = new Intent(getContext(), NewsViewActivity.class);
                opa.putExtra("newsurl", newsurl[position+1]);
                opa.putExtra("newsdata", newsdesc[position-1]);
                getContext().startActivity(opa);

            }
        });
        TextView textView1 = new TextView(container.getContext());
        textView1.setTypeface(Typeface.DEFAULT_BOLD);
        textView1.setText("Performance of Maintance");
        CreateBarChart(rooView);

        TextView textView2 = (TextView)rooView.findViewById(R.id.comtext);
        textView2.setTypeface(Typeface.DEFAULT_BOLD);
        //textView2.setText("Top 5 News of the world");

        comView = (ListView)rooView.findViewById(R.id.comview);
        button = (Button)rooView.findViewById(R.id.button);

        comView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        comView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 CheckedTextView v = (CheckedTextView) view;
                 String status="false";

               // System.out.println( " on Notice Board onItemClick: " +position+"item desc "+pclist.get(position).getComplainBoxModel().getDesc()+" checked ?=   "+v.isChecked());//clist.get(position).getDesc());
                AppSettingsData.AppDialog("Description",pclist.get(position).getComplainBoxModel().getDesc(),"", view.getContext());
                boolean currentCheck = v.isChecked();

                if(currentCheck==true)
                    status="true";

                pclist.get(position).getComplainBoxModel().setStatus(status);
                Set_FirebaseData(view,pclist.get(position));
              //  clist.get(position).getDesc();
             //   UserAccount user = (UserAccount) listView.getItemAtPosition(position);
              //  user.setActive(!currentCheck);
            }
        });


        //EditText notice_board=(EditText)rooView.findViewById(R.id.notice);
        //notice_board.setText("This is Notice Board");

        return rooView;
    }
    private void initListViewData(View rooView)  {
      //  System.out.println("YOU ARE IN METHOD STARTING .....--- initListViewData");

        List<String> desc_list = new ArrayList<>();
      //  System.out.println("The Size of List from FireBase >>>>>>>>>>>>>>>>  CLIST:"+pclist.size());
        //arrayAdapter = new ArrayAdapter<String>(rooView.getContext(), android.R.layout.simple_list_item_checked, desc_data);
        if(pclist.size()>0) {
            for (int i = 0; i < pclist.size(); i++) {
                desc_list.add(pclist.get(i).getComplainBoxModel().getFlatnumber() +" raise a concern on "+pclist.get(i).getComplainBoxModel().getType());
            }
           // System.out.println("YOU ARE IN METHOD End .....--- initListViewData -problem here-1");
            // android.R.layout.simple_list_item_checked:
            // ListItem is very simple (Only one CheckedTextView).
            //ArrayAdapter<String> arrayAdapter
             arrayAdapter = new ArrayAdapter<String>(rooView.getContext(), android.R.layout.simple_list_item_checked, desc_list);
           // System.out.println("YOU ARE IN METHOD End .....--- initListViewData -problem here-2");
            comView.setAdapter(arrayAdapter);

            for (int i = 0; i < pclist.size(); i++) {
                if(pclist.get(i).getComplainBoxModel().getStatus()!=null) {
                    if (pclist.get(i).getComplainBoxModel().getStatus().contains("false"))
                        comView.setItemChecked(i, false);
                    else
                        comView.setItemChecked(i, true);
                }

            }
         //   System.out.println("YOU ARE IN METHOD End .....--- initListViewData -problem here-3");
        }
      //  System.out.println("YOU ARE IN METHOD End .....--- initListViewData");
    }


    private  void Set_FirebaseData(final View rooView,FirebaseChildDataStore fcmodel)
    {

       // System.out.println("YOU ARE IN METHOD STARTING .....--- Set_FirebaseData key "+fcmodel.getKey());
        FirebaseApp.initializeApp(rooView.getContext());
        FirebaseInstanceId.getInstance().getToken();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid ="";
        // if(FirebaseAuth.getInstance()!=null)
        //    uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
        myRef = database.getReference("data");

        Map<String, Object> messageValues = fcmodel.getComplainBoxModel().toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        String key = fcmodel.getKey();
        childUpdates.put("/messages/"+key, messageValues);
        myRef.updateChildren(childUpdates);

        if(myRef!=null)
            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                    for (DataSnapshot imageSnapshot: dataSnapshot.getChildren()) {
                       // System.out.println("postMessages: <FOr loop> @ onChildAdded  key: "+imageSnapshot.getKey()+" Value "+imageSnapshot.getValue());
                        ObjectMapper mapper = new ObjectMapper();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        ComplainBoxModel cmodel = mapper.convertValue(imageSnapshot.getValue(),ComplainBoxModel.class);
                        clist.add(cmodel);
                        System.out.println("postMessages: <FOr loop> @ onChildAdded  Describtion >>>>    "+cmodel.getDesc()+"status  "+cmodel.getStatus());//.is);
                    }
                    /*String[] desc_data = new String[4096];
                    System.out.println("The Size of List from FireBase >>>>>>>>>>>>>>>>  CLIST:"+clist.size());

                    if(clist.size()>0) {
                        for (int i = 0; i < clist.size(); i++) {
                            desc_data[i] = clist.get(i).getDesc();
                          //  arrayAdapter.add(clist.get(i).getDesc());
                        }
                    }
                  //  arrayAdapter.add(desc_data[0]);
                    arrayAdapter.notifyDataSetChanged();*/
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                    System.out.println("postMessages:  @ onChildChanged");
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    System.out.println("postMessages:  @ onChildRemoved");
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                    // A message has changed position
                    System.out.println("postMessages:  @ onChildMoved");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("postMessages:onCancelled"+ databaseError.toException());

                }
            });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot imageSnapshot: dataSnapshot.getChildren()) {
                    ComplainBoxModel value1 = imageSnapshot.getValue(ComplainBoxModel.class);
                    String data=imageSnapshot.child("messages").getValue(String.class);
                    System.out.println("The vale is the user name>>>>>ComplainBoxModel>>>onDataChange>>>>>>>>>>>>>>>>>"+data);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value."+error.toException());
            }
        });
        System.out.println("YOU ARE IN METHOD End .....--- Get_FirebaseData");
    }
    private  void Get_FirebaseData(final View rooView)
    {

        System.out.println("YOU ARE IN METHOD STARTING .....--- Get_FirebaseData");
        FirebaseApp.initializeApp(rooView.getContext());
        FirebaseInstanceId.getInstance().getToken();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String uid =null;
        // if(FirebaseAuth.getInstance()!=null)
        //    uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
          myRef = database.getReference("data");

        if(myRef!=null)
            myRef.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                    for (DataSnapshot imageSnapshot: dataSnapshot.getChildren()) {
                        System.out.println("postMessages: <FOr loop> @ onChildAdded  key: "+imageSnapshot.getKey()+" Value "+imageSnapshot.getValue());
                        ObjectMapper mapper = new ObjectMapper();
                        FirebaseChildDataStore fcmodel=new FirebaseChildDataStore();
                        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                        ComplainBoxModel cmodel = mapper.convertValue(imageSnapshot.getValue(),ComplainBoxModel.class);
                        fcmodel.setComplainBoxModel(cmodel);
                        fcmodel.setKey(imageSnapshot.getKey());
                        pclist.add(fcmodel);
                        //clist.add(cmodel);
                        System.out.println("postMessages: <FOr loop> @ onChildAdded  Describtion >>>>    "+fcmodel.getComplainBoxModel().getDesc());//cmodel.getDesc());
                    }
                    /*String[] desc_data = new String[4096];
                    System.out.println("The Size of List from FireBase >>>>>>>>>>>>>>>>  CLIST:"+clist.size());

                    if(clist.size()>0) {
                        for (int i = 0; i < clist.size(); i++) {
                            desc_data[i] = clist.get(i).getDesc();
                          //  arrayAdapter.add(clist.get(i).getDesc());
                        }
                    }
                  //  arrayAdapter.add(desc_data[0]);
                    arrayAdapter.notifyDataSetChanged();*/
                    initListViewData(rooView);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                    System.out.println("postMessages:  @ onChildChanged");
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {
                    System.out.println("postMessages:  @ onChildRemoved");
                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
                    // A message has changed position
                    System.out.println("postMessages:  @ onChildMoved");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    System.out.println("postMessages:onCancelled"+ databaseError.toException());

                }
            });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot imageSnapshot: dataSnapshot.getChildren()) {
                    ComplainBoxModel value1 = imageSnapshot.getValue(ComplainBoxModel.class);
                    String data=imageSnapshot.child("messages").getValue(String.class);
                    System.out.println("The vale is the user name>>>>>ComplainBoxModel>>>onDataChange>>>>>>>>>>>>>>>>>"+data);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                System.out.println("Failed to read value."+error.toException());
            }
        });
        System.out.println("YOU ARE IN METHOD End .....--- Get_FirebaseData");
    }
public void CreateBarChart(View v){

    BarChart barChart = (BarChart) v.findViewById(R.id.barchart);
    ArrayList<BarEntry> entries = new ArrayList<>();
    entries.add(new BarEntry(8f, 0));
    entries.add(new BarEntry(2f, 1));
    entries.add(new BarEntry(5f, 2));
    entries.add(new BarEntry(20f, 3));
    entries.add(new BarEntry(15f, 4));
    entries.add(new BarEntry(19f, 5));

    BarDataSet bardataset = new BarDataSet(entries, "Cells");

    ArrayList<String> labels = new ArrayList<String>();
    labels.add("2016");
    labels.add("2015");
    labels.add("2014");
    labels.add("2013");
    labels.add("2012");
    labels.add("2011");

    BarData data = new BarData(labels, bardataset);
    barChart.setData(data); // set the data and list of labels into chart
    barChart.setDescription("Set Bar Chart Description Here");  // set the description
    bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
    barChart.animateY(5000);
}
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //customCountryList = new DashBoardNewsListAdapter(mContext, countryNames, capitalNames, imageid);
       // listView.setAdapter(customCountryList);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        //you can set the title for your toolbar here for different fragments different titles
      //  getActivity().setTitle("Menu 1");
       // Intent ol = new MainActivity().getintent();
       // startActivity(ol);
    }

    @Override
    public void onAttach(Activity context) {
        super.onAttach(context);
        mContext=context;
    }


    class NewsCollector extends AsyncTask<String,Void,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb=null;
            BufferedReader reader=null;
            String serverResponse=null;
            BBC_News bbcnews=new BBC_News();
            try {

                URL url = new URL(bbc_url);//"http://192.168.2.106/?Lichterkette=1");
                System.out.println("BBC NEWS -- GOT >>>>>>>>>>>>>>200 URL:"+url);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                connection.setConnectTimeout(5000);
                connection.setRequestMethod("GET");
                connection.connect();
                int statusCode = connection.getResponseCode();
                //Log.e("statusCode", "" + statusCode);
                if (statusCode == 200) {
                    System.out.println("BBC NEWS -- GOT >>>>>>>>>>>>>>200");
                    sb = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    while ((line = reader.readLine()) != null) {
                        System.out.println("BBC NEWS -- GOT >>>>>>>>>>>>>>200 Line "+line);

                        Gson gson = new Gson();
                        bbcnews= gson.fromJson(line, BBC_News.class);
                        sb.append(line + "\n");
                    }
                    System.out.println("BBC NEWS -- GOT >>>>>>>>>>>>>>200  data"+bbcnews.getArticles().get(0).getUrl());


                      for(int i=0;i<5;i++)
                      {
                          countryNames[i]=bbcnews.getArticles().get(i).getTitle();
                          capitalNames[i]=bbcnews.getArticles().get(i).getDescription();
                          imageid[i]=bbcnews.getArticles().get(i).getUrlToImage();
                          newsdesc[i]=bbcnews.getArticles().get(i).getDescription();
                          newsurl[i]=bbcnews.getArticles().get(i).getUrl();

                      }
                   // customCountryList = new DashBoardNewsListAdapter(mContext, countryNames, capitalNames, imageid);
                   // customCountryList.updateListRow(0,countryNames, capitalNames, imageid);
                   // customCountryList.notifyDataSetChanged();
                   // customCountryList.notifyDataSetInvalidated();
                }

                connection.disconnect();
                if (sb!=null)
                    serverResponse=sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            return serverResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //All your UI operation can be performed here
            System.out.println(s);
        }
    }

    private void startSignIn(View context) {
        // Initiate sign in with custom token
        System.out.println("Abhijit the list of Item>>>>  >>>>>startSignIn");

        // [START sign_in_custom]
        String email = "abhijit.golo@gmail.com";
        String password = "Golo@123";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            System.out.println("Abhijit the list of Item>>>>  >>>>>>>>>>>>>>  signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {

                            System.out.println("Abhijit the list of Item>>>>  >>>>>>>>>>>>>> signInWithEmail:failure" + task.getException());

                        }

                        // ...
                    }
                });
    }
}
