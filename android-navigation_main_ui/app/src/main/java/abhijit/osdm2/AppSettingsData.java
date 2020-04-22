package abhijit.osdm2;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class AppSettingsData {


    public static  int FLATNUMBER=16;
    public static String loginowner;
    public static String flatstatus;
    public static String apartmentid="ODM1";
    public static String  OWNERURL="https://postgres2322.herokuapp.com/flatowner/";
    public static String  RENTERURL="https://postgres2322.herokuapp.com/renter/";
    public static String  SUPPORTURL="https://postgres2322.herokuapp.com/support/";
    public static String  MNTDATAURL="https://postgres2322.herokuapp.com/monthlyex/";
    public static String owner="owner";
    public static String renter="renter";
    public static String admin="admin";
    public static boolean call_permission=false;
    public static boolean sms_permission=false;
    public static boolean extstorage_permission=false;

    public static String  rule="";


    enum Status
    {
        ADMIN,TENENT,OWNER;
    }

    //public static String  OWNERURL="";
    //public static String  OWNERURL="";


    public static void SetRule(String Rule){
        rule=Rule;
    }

    public static String GetRule(){
        return rule;
    }

    public static void setLoginFlatOwner(String loginfown)
    {
        loginowner=loginfown;
    }
    public static String getLoginFlatOwner()
    {
       return loginowner;
    }

    public static void setFlatstatus(String flatst)
    {
        flatstatus=flatst;
    }
    public static String getFlatstatus()
    {
        return flatstatus;
    }

    public static void AppDialog(String title,String message,String sub_msg,Context context){
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message+" "+sub_msg)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Whatever...

                    }
                }).show();
    }
    public static void setSmsPermission(boolean st)
    {
        sms_permission=st;
    }
    public static boolean getSmsPermission(boolean st)
    {
        return sms_permission;
    }

    public static void setCallPermission(boolean st)
    {
        call_permission=st;
    }
    public static boolean getCallPermission(boolean st)
    {
        return call_permission;
    }

    public static void setExtStoragePermission(boolean st)
    {
        extstorage_permission=st;
    }
    public static boolean getExtStoragePermission(boolean st)
    {
        return extstorage_permission;
    }

}
