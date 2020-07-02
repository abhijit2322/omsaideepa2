package abhijit.osdm_wop;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import abhijit.osdm_wop.R;
import abhijit.osdm_wop.models.ComplainBoxModel;

public class ComplainBox extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FirebaseUser user;
    private Spinner spinner;
    private static final String[] paths = {"Select type of complain ", "Electricity", "Water","Security","Maintance cost","Expence Cost","Misc"};
    ComplainBoxModel complainBoxModel;
    private EditText nameedit,flatno,desc;
    private Button submit,back;
    private DatabaseReference myRef;
    private ArrayList<ComplainBoxModel> complainList;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complain_box);

        complainBoxModel=new ComplainBoxModel();

        nameedit=(EditText)findViewById(R.id.nameedit);
        flatno=(EditText)findViewById(R.id.flatnoedit);
        desc=(EditText)findViewById(R.id.complainedit);

        submit=(Button)findViewById(R.id.submit);
        back=(Button)findViewById(R.id.back);

        spinner = (Spinner)findViewById(R.id.typespinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(ComplainBox.this,
                android.R.layout.simple_spinner_item,paths);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        complainList = new ArrayList<>();

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // your handler code here
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                FirebaseApp.initializeApp(v.getContext());
                FirebaseInstanceId.getInstance().getToken();
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                mAuth = FirebaseAuth.getInstance();
                startSignIn(v);
                 String uid =null;
               // if(FirebaseAuth.getInstance()!=null)
               uid= FirebaseAuth.getInstance().getCurrentUser().getUid();
                user = mAuth.getCurrentUser();
                uid= user.getUid();
                myRef = database.getReference("data");
                /* save ++*/
                 complainBoxModel.setName(nameedit.getText().toString());
                complainBoxModel.setFlatnumber(flatno.getText().toString());
                complainBoxModel.setDesc(desc.getText().toString());
                complainBoxModel.setStatus("false");
                 Map<String, Object> messageValues = complainBoxModel.toMap();
                Map<String, Object> childUpdates = new HashMap<>();

                String key = myRef.child("messages").push().getKey();

                childUpdates.put("/messages/"+key, messageValues);
                myRef.updateChildren(childUpdates);

                if(myRef!=null)
                    myRef.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                             List<ComplainBoxModel> clist=new ArrayList<>();
                            for (DataSnapshot imageSnapshot: dataSnapshot.getChildren()) {
                                System.out.println("postMessages: <FOr loop> @ onChildAdded  key: "+imageSnapshot.getKey()+" Value "+imageSnapshot.getValue());
                                ObjectMapper mapper = new ObjectMapper();
                                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
                                ComplainBoxModel cmodel = mapper.convertValue(imageSnapshot.getValue(),ComplainBoxModel.class);
                                complainList.add(cmodel);
                                System.out.println("postMessages: <FOr loop> @ onChildAdded  Describtion >>>>    "+cmodel.getDesc()+" "+cmodel.getStatus());
                            }
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
                            Toast.makeText(ComplainBox.this, "Failed to load Message.", Toast.LENGTH_SHORT).show();
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
                SharedPreferences settings = v.getContext().getSharedPreferences(AppSettingsData.PREFS_NAME, MODE_PRIVATE);
                Intent opa = new Intent(getApplicationContext(), MainActivity.class);
                opa.putExtra("loginrule", settings.getString("loginrole",null));
                startActivity(opa);
                finish();
            }

        });

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                SharedPreferences settings = v.getContext().getSharedPreferences(AppSettingsData.PREFS_NAME, MODE_PRIVATE);
                Intent opa = new Intent(getApplicationContext(), MainActivity.class);
                opa.putExtra("loginrule", settings.getString("loginrole",null));
                startActivity(opa);
                finish();
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                complainBoxModel.setType(paths[position]);
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                complainBoxModel.setType(paths[position]);
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;
            case 3:
                // Whatever you want to happen when the first item gets selected
                complainBoxModel.setType(paths[position]);
                break;
            case 4:
                // Whatever you want to happen when the second item gets selected
                complainBoxModel.setType(paths[position]);
                break;
            case 5:
                // Whatever you want to happen when the thrid item gets selected
                complainBoxModel.setType(paths[position]);
                break;
             case 6:
                // Whatever you want to happen when the thrid item gets selected
                 complainBoxModel.setType(paths[position]);
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }

    private void startSignIn(View context) {
        // Initiate sign in with custom token
        System.out.println("Abhijit the list of Item>>>>  >>>>>startSignIn");

        // [START sign_in_custom]
        String email = "abhijit.golo@gmail.com";
        String password = "Golo@123";
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
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
