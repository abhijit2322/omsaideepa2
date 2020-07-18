package abhijit.osdm_wop;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import abhijit.osdm_wop.models.FlatOwner;
import abhijit.osdm_wop.rettrofitsupport.RetrofitClient;
import abhijit.osdm_wop.service.HerokuService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OwnerListActivity extends AppCompatActivity {

    private LinearLayoutManager layoutManager;
    private List<FlatOwner> userList =null;
    private ImageView iv=null;
    String flatnumberdeleteted;
    Context gcontext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Owner List");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ownerlist_main);
        Log.i("autolog", "onCreate");
        FlatOwner flatowner=new FlatOwner();
        flatowner.setApartmentid(AppSettingsData.getApartmentID());
        getUserList(flatowner);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (AppSettingsData.GetRule().contains("admin")) {
            getMenuInflater().inflate(R.menu.owner_action_menu, menu);
            return true;
        }
        else
            return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        boolean from_add=false;
        switch(item.getItemId()) {

        case R.id.add:
            System.out.println("Owner list Add pressed");
            from_add=true;
            Intent opa = new Intent(getApplicationContext(), Edit_Owner_Profile.class);
            getApplicationContext().startActivity(opa);
            finish();
            return(true);
        case R.id.delete:
            delete_dialog();
            System.out.println("Owner list reset pressed");
            return(true);
            default:
                return(super.onOptionsItemSelected(item));
        }


    }

    public void delete_dialog()
    {
        LayoutInflater factory = LayoutInflater.from(this);
         View deleteDialogView = factory.inflate(R.layout.custom_dialog,null);
        final AlertDialog deleteDialog = new AlertDialog.Builder(this).create();

         deleteDialog.setView(deleteDialogView);
        TextView tv = (TextView) deleteDialogView.findViewById(R.id.textView);
        tv.setText("Enter Flat number to be deleted");
        final EditText editText = (EditText) deleteDialogView.findViewById(R.id.edt_comment);
        editText.setHint("Enter Flat number to be deleted");
        deleteDialogView.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //your business logic
                flatnumberdeleteted = editText.getText().toString();
                if (flatnumberdeleteted != null) {
                    FlatOwner flatOwner = new FlatOwner();
                    flatOwner.setFlatnumber(flatnumberdeleteted);
                    flatOwner.setApartmentid(AppSettingsData.getApartmentID());
                    DeleteFlatowner(flatOwner);
                }
                deleteDialog.dismiss();
                updateview();

            }
        });
        deleteDialogView.findViewById(R.id.buttonCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //deleteDialog.dismiss();
                deleteDialog.cancel();
                updateview();
            }
        });

        deleteDialog.show();
    }

    public void DeleteFlatowner(FlatOwner flatOwner){
     System.out.println("I am here ...<Edit Owner Profile>..DeleteFlatowner-RETROFIT");
    HerokuService apiService = RetrofitClient.getApiService();
        apiService.deleteflatowner(flatOwner).enqueue(new Callback<FlatOwner>() {
        @Override
        public void onResponse(Call<FlatOwner> call, Response<FlatOwner> response) {
            System.out.println("I am here ...<LoginActivity>..DeleteFlatowner-onResponse");
            if(response.isSuccessful()) {
                System.out.println("I am here ...<LoginActivity>..Delete owner-On Success");
                FlatOwner user1 = response.body();
                Toast.makeText(getApplicationContext(), "Flat Owner Details Updated", Toast.LENGTH_SHORT).show();
                updateview();
            }
        }
        @Override
        public void onFailure(Call<FlatOwner>  call, Throwable t) {
            System.out.println("This in  Failure    DeleteFlatowner>>>>>. in login activity "+t.getMessage());
            updateview();
            // response_status=true;
        }
    });

        System.out.println("This in  Failure    DeleteFlatowner>>>>>.last--------------------------------------");
       // updateview();

}
public void updateview()
{
   // Intent ownerli = new Intent(getApplicationContext(), OwnerListActivity.class);
  //  getApplicationContext().startActivity(ownerli);
   // startActivity(getIntent());
    finish();
    getIntent();
}
    private void getUserList(FlatOwner flatowner) {
           System.out.println("I am here ...<Owner List Activity>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getFlatowner(flatowner).enqueue(new Callback<List<FlatOwner>>() {
            @Override
            public void onResponse(Call<List<FlatOwner>>call, Response<List<FlatOwner>> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    userList = response.body();
                    Log.i("autolog", "List<User> userList = response.body();");

                    RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);
                    Log.i("autolog", "RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler);");

                    layoutManager = new LinearLayoutManager(OwnerListActivity.this);
                    Log.i("autolog", "layoutManager = new LinearLayoutManager(MainActivity.this);");
                    recyclerView.setLayoutManager(layoutManager);
                    Log.i("autolog", "recyclerView.setLayoutManager(layoutManager);");

                    RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(getApplicationContext(), userList);
                    Log.i("autolog", "RecyclerViewAdapter recyclerViewAdapter =new RecyclerViewAdapter(getApplicationContext(), userList);");
                    recyclerView.setAdapter(recyclerViewAdapter);
                    Log.i("autolog", "recyclerView.setAdapter(recyclerViewAdapter);");
                    Toast.makeText(getApplicationContext(), "Flat Owner Details Updated", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<FlatOwner>>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
    }
    public static Intent getInstance(Context context) {
        System.out.println("Abhijit ----> its in login---getInstance--1");
        return new Intent(context, OwnerListActivity.class);
    }

    @Override
    protected void onRestart() {
        System.out.println("Abhijit ----> OnRestart  ---------------Owner List------");
        super.onRestart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println("Abhijit ----> onActivityResult  ---------------Owner List------");
        //Check the result and request code here and update ur activity class

    }


}