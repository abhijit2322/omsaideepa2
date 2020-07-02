package abhijit.osdm_wop;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import abhijit.osdm_wop.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;

    public String login_Rule="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Dash Board");
        Intent intent = getIntent();
        login_Rule = intent.getStringExtra("loginrule");

        System.out.println("Login Type and rule ......MainActivity.............>>>>>>>>>>>>>>>>>>"+login_Rule);
        AppSettingsData.SetRule(login_Rule);
        /*
        if(GlobalVariables.admin.contains(login_Rule))
            GlobalVariables.SetRule(login_Rule);
        else if(GlobalVariables.owner.contains(login_Rule))
            GlobalVariables.SetRule(login_Rule);
        else if(GlobalVariables.renter.contains(login_Rule))
            GlobalVariables.SetRule(login_Rule);
        else
            GlobalVariables.SetRule("admin");//need to change later
*/
        System.out.println("Login Type and rule ......MainActivity..Get...........>>>>>>>>>>>>>>>>>>"+AppSettingsData.GetRule());
        super.onCreate(savedInstanceState);


        if (AppSettingsData.GetRule().contains("admin"))
        setContentView(R.layout.admin_activity_main);
       else if (AppSettingsData.GetRule().contains("owner"))
            setContentView(R.layout.owner_menu_activity_main);
        else if (AppSettingsData.GetRule().contains("renter"))
            setContentView(R.layout.renter_menu_activity_main);
        else {
           // setContentView(R.layout.admin_activity_main);//need to change later
            Intent ir = new Intent(getApplicationContext(), NoWhereActivity.class);
            startActivity(ir);

        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
       // getSupportActionBar().hide();
        //getSupportActionBar();

       // permision_check();
        DrawerLayout drawer=null;
        if (AppSettingsData.GetRule().contains("admin"))
            drawer= (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        else if (AppSettingsData.GetRule().contains("owner"))
            drawer= (DrawerLayout) findViewById(R.id.owner_drawer_layout);
        else if (AppSettingsData.GetRule().contains("renter"))
            drawer= (DrawerLayout) findViewById(R.id.renter_drawer_layout);
        else
        {
            // setContentView(R.layout.admin_activity_main);//need to change later
            Intent ir = new Intent(getApplicationContext(), NoWhereActivity.class);
            startActivity(ir);
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //add this line to display menu1 when the activity is loaded

        if (AppSettingsData.GetRule().contains("admin"))
        displaySelectedScreen_admin(R.id.nav_menu_admin1);
        else if (AppSettingsData.GetRule().contains("owner"))
            displaySelectedScreen_owner(R.id.nav_menu_owner1);
       else if (AppSettingsData.GetRule().contains("renter"))
            displaySelectedScreen_renter(R.id.nav_menu_rent1);
        else {
            // setContentView(R.layout.admin_activity_main);//need to change later

            System.out.println("Login Type and rule <MainActivity>....  unknown...............");
            Intent ir = new Intent(getApplicationContext(), NoWhereActivity.class);
            startActivity(ir);

        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }*/
    @Override
    public boolean onPrepareOptionsMenu (Menu menu) {

        if (AppSettingsData.GetRule().contains("user")) {
           // menu.getItem(3).setEnabled(false);
            // You can also use something like:
            // menu.findItem(R.id.example_foobar).setEnabled(false);
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        if(drawer==null)
            return;
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
      //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    private void displaySelectedScreen_admin(int itemId) {

        //creating fragment object
        Fragment fragment = null;
        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_menu_admin1:
               fragment = new Menu1();

                break;
             case R.id.nav_menu_admin2:
                Intent ie = new Intent(getApplicationContext(), Enter_Maintance_cost.class);
                startActivity(ie);
                break;
            case R.id.nav_menu_admin3:
                //fragment = new Menu4();
                Intent ir = new Intent(getApplicationContext(), RenterListActivity.class);
                startActivity(ir);
                break;
            case R.id.nav_menu_admin4:
                //fragment = new Menu5();
                Intent oi = new Intent(getApplicationContext(), OwnerListActivity.class);
                startActivity(oi);
                break;

            case R.id.nav_menu_admin5:
                //fragment = new Menu5();
                showDialog();
               // Intent i = new Intent(getApplicationContext(), PdfDownload.class);
                //startActivity(i);
                break;

            case R.id.nav_menu_admin6:
                //fragment = new Menu6();
                Intent db = new Intent(getApplicationContext(), SupportListActivity.class);
                startActivity(db);
                break;

            case R.id.nav_menu_admin7:
                Intent db1 = new Intent(getApplicationContext(), ComplainBox.class);
                startActivity(db1);
                break;

            case R.id.nav_menu_admin_logout:
                //fragment = new Menu6();
                Intent la = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(la);
                finish();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.admin_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void displaySelectedScreen_owner(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_menu_owner1:
                fragment = new Menu1();
                break;

            case R.id.nav_menu_owner2:
               // fragment = new Menu7();
                 showDialog();
                //Intent i = new Intent(getApplicationContext(), PdfDownload.class);
               // startActivity(i);
                break;
             case R.id.nav_menu_owner3:
                //fragment = new Menu4();
                Intent ir = new Intent(getApplicationContext(), RenterListActivity.class);
                startActivity(ir);
                break;
            case R.id.nav_menu_owner4:
                //fragment = new Menu5();
                Intent oi = new Intent(getApplicationContext(), OwnerListActivity.class);
                startActivity(oi);
                break;
            case R.id.nav_menu_owner5:
                //fragment = new Menu6();
                Intent db = new Intent(getApplicationContext(), SupportListActivity.class);
                startActivity(db);
                break;

            case R.id.nav_menu_owner6:
                Intent db1 = new Intent(getApplicationContext(), ComplainBox.class);
                startActivity(db1);
                break;
            case R.id.nav_menu_owner_logout:
                //fragment = new Menu6();
                Intent la = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(la);
                finish();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.owner_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    private void displaySelectedScreen_renter(int itemId) {

        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (itemId) {
            case R.id.nav_menu_rent1:
                fragment = new Menu1();
                break;

            case R.id.nav_menu_rent2:
                //fragment = new Menu7();
                showDialog();
               // Intent i = new Intent(getApplicationContext(), PdfDownload.class);
                // startActivity(i);
                break;

            case R.id.nav_menu_rent3:
                //fragment = new Menu4();
                Intent ir = new Intent(getApplicationContext(), RenterListActivity.class);
                startActivity(ir);
                break;
            case R.id.nav_menu_rent4:
                //fragment = new Menu6();
                Intent db = new Intent(getApplicationContext(), SupportListActivity.class);
                startActivity(db);
                break;
            case R.id.nav_menu_rent5:
                Intent db1 = new Intent(getApplicationContext(), ComplainBox.class);
                startActivity(db1);
                break;
            case R.id.nav_menu_rent_logout:
                //fragment = new Menu6();
                Intent la = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(la);
                finish();
                break;
        }

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.renter_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    public Intent getintent(){
        Intent i = new Intent(getApplicationContext(), ActivityItemCreator.class);
        return i;
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        //calling the method displayselectedscreen and passing the id of selected menu
        //displaySelectedScreen_admin(item.getItemId());

        if (AppSettingsData.GetRule().contains("admin"))
            displaySelectedScreen_admin(item.getItemId());
        else if (AppSettingsData.GetRule().contains("owner"))
              displaySelectedScreen_owner(item.getItemId());
        else if (AppSettingsData.GetRule().contains("renter"))
            displaySelectedScreen_renter(item.getItemId());
        else {
            // setContentView(R.layout.admin_activity_main);//need to change later

            System.out.println("Login Type and rule <MainActivity>....  unknown...............");
            Intent ir = new Intent(getApplicationContext(), NoWhereActivity.class);
            startActivity(ir);

        }


        //make this method blank
        return true;
    }

    public void permision_check() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }


        }
    }
    public void showDialog()
    {

        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.custom_dialog, null);

        final EditText editText = (EditText) dialogView.findViewById(R.id.edt_comment);
        Button button1 = (Button) dialogView.findViewById(R.id.buttonSubmit);
        Button button2 = (Button) dialogView.findViewById(R.id.buttonCancel);

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               dialogBuilder.dismiss();
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String monthyear=editText.getText().toString();
                Intent i = new Intent(getApplicationContext(), PdfDownload.class);
                i.putExtra("monthyear", monthyear);
                startActivity(i);
                dialogBuilder.dismiss();
            }
        });

        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
    }


}
