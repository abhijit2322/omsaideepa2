package abhijit.osdm_wop;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import abhijit.osdm_wop.R;

public class DashBoardNewsListAdapter extends ArrayAdapter {
    private String[] countryNames;
    private String[] capitalNames;
    private String[] imageid;
   // private Integer[] imageid;
    private Activity context;

    TextView textViewCountry  ;
    TextView textViewCapital ;
    ImageView imageFlag  ;

    View row;
    private Integer imageidnull[] = {
            R.drawable.add_green,
            R.drawable.add_green,
            R.drawable.add_green,
            R.drawable.add_green

    };


    public DashBoardNewsListAdapter(Activity context, String[] countryNames, String[] capitalNames, String imageid[]){//Integer[] imageid) {
        super(context, R.layout.dashboardnews, countryNames);
        this.context = context;
        this.countryNames = countryNames;
        this.capitalNames = capitalNames;
        this.imageid = imageid;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        row=convertView;
        LayoutInflater inflater = context.getLayoutInflater();
        row= inflater.inflate(R.layout.dashboardnews, null, true);
  /*      if(convertView==null)
            row = inflater.inflate(R.layout.dashboardnews, null, false);*/
         textViewCountry = (TextView) row.findViewById(R.id.textheadline);
         textViewCapital = (TextView) row.findViewById(R.id.text_subheadline);
         imageFlag = (ImageView) row.findViewById(R.id.imageView);


      /*  if(imageid==null) {
            System.out.println("Image When Null>>>>>>>>>>>"+imageidnull[position]);
            imageFlag.setImageResource(imageidnull[position]);
        }
        else {*/
            if(imageid[position]!=null){
                System.out.println("Image When Not Null>>>>>>>>>>>" + imageid[position] + "The Length  " + imageid[position].length()+"position   "+position);
                imageFlag.setImageBitmap(getBitmapFromURL(imageid[position]));
                textViewCountry.setText(countryNames[position]);
                textViewCapital.setText(capitalNames[position]);
            }
      //  }


        return  row;
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            System.out.println("Image When Not Null>>>>>>>>>>> The IMAGE URL>>"+src);
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}