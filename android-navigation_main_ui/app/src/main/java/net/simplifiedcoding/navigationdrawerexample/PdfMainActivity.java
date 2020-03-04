package net.simplifiedcoding.navigationdrawerexample;

/*import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;*/

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.constraint.solver.widgets.Rectangle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import android.os.Environment;
import android.os.Bundle;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.TextStyle;
import java.util.Calendar;
import java.util.StringTokenizer;

import butterknife.internal.Utils;


public class PdfMainActivity extends AppCompatActivity {

    String myPdfUrl = " ";
    String filename="";
    String total_cost="";
    String start_cost="";
    String end_cost="";
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_main);
        System.out.println("Abhijit its coming before create pdf >>>>>>>>>>>>>>>>>>>>>>");
       // CreatePdf();
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = getIntent();
        String pdfstring = intent.getStringExtra("pdfString");
        total_cost =intent.getStringExtra("total_cost");
        start_cost =intent.getStringExtra("start_val");
        end_cost=intent.getStringExtra("remain_val");
        //permision_check();
      //  custom_pdf(pdfstring);
        try {
            createPdfnice(pdfstring);
        }
        catch (Exception y){}
       // createPdfh("Monthly Expenditure\t"+pdfstring);
        OpenpdfFile(myPdfUrl);
        System.out.println("Abhijit its coming After create pdf >>>>>>>>>>>>>>>>>>>>>>");


       /* final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
        WebView web_view = findViewById(R.id.web_view);
        web_view.requestFocus();
        web_view.getSettings().setJavaScriptEnabled(true);
       // String myPdfUrl = "gymnasium-wandlitz.de/vplan/vplan.pdf";
        String url = myPdfUrl;

        System.out.println("Abhijit its coming URL >>>>>>>>>>>>>>>>>>>>>>"+url);
        web_view.loadUrl(url);
        web_view.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                System.out.println("Abhijit its coming shouldOverrideUrlLoading >>>>>>>>>>>>>>>>>>>>>>");
                return true;
            }
        });
        web_view.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                if (progress < 100) {
                    System.out.println("Abhijit its coming onProgressChanged <100 >>>>>>>>>>>>>>>>>>>>>>");
                    progressDialog.show();
                }
                if (progress == 100) {
                    System.out.println("Abhijit its coming onProgressChanged =100 >>>>>>>>>>>>>>>>>>>>>>");
                    progressDialog.dismiss();
                }
            }
        });

*/
    }




    private void createPdfh(String sometext){
        // create a new document
        PdfDocument document = new PdfDocument();

        // crate a page description
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();

       // paint.setColor(Color.RED);
        //canvas.drawCircle(50, 50, 30, paint);
        //paint.setColor(Color.BLACK);
        canvas.drawText(sometext, 80, 50, paint);
        //canvas.drawt
        // finish the page
        document.finishPage(page);
// draw text on the graphics object of the page
        // Create Page 2
        pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(100, 100, 100, paint);
        document.finishPage(page);
        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"test-2.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            myPdfUrl=filePath.getAbsolutePath();
            Toast.makeText(this, "Done  File created here...."+filePath.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }
   public void permision_check()
   {
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

    public void OpenpdfFile(String storagepath)
    {
        File file = new File(storagepath);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        Intent intent = Intent.createChooser(target, "Open File");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

            e.printStackTrace();
            // Instruct the user to install a PDF reader here, or something
        }
    }

    public void custom_pdf(String str)
    {
        String[] pdfstr;
        System.out.println("Abhijit its coming After create pdf >>>>>>>>>>>>>>>>>>>>>>"+str);

        StringTokenizer multiTokenizer = new StringTokenizer(str, ":");

        while (multiTokenizer.hasMoreTokens())
        {
            System.out.println("token>>>>>>>>>>>>>>>>>>>>>>>>>"+multiTokenizer.nextToken());
        }

    }


    public void createPdfnice(String str) throws Exception {
       // Document document = new Document();
        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 1).create();
        // start a page
        PdfDocument.Page page = document.startPage(pageInfo);

        /*----------------------------------------------------*/
        String[]monthName={"Jan.","Feb.","Mar.", "Apr.", "May.", "Jun.", "Jul.",
                "Aug.", "Sept.", "Oct.", "Nov.",
                "Dec."};

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        System.out.println("Currrent Year>>>>>>>>>>>>>>>>>>"+year);
        System.out.println("Current Month>>>>>>>>>>>>>>>>>>>>>>"+monthName[month]);

        Canvas canvas = page.getCanvas();

        Paint hpaint = new Paint();
        hpaint.setColor(Color.BLUE);
        hpaint.setFlags(Paint. UNDERLINE_TEXT_FLAG);
        hpaint.setTextSize(16); // set font size
        Typeface hcurrentTypeFace =   hpaint.getTypeface();
        Typeface hbold = Typeface.create(hcurrentTypeFace, Typeface.BOLD);
        hpaint.setTypeface(hbold);
        String statement1="Monthly Maintaince Report";
        String statement2=monthName[month]+" of "+year;
        canvas.drawText(statement1, 40, 50 , hpaint);
       /// hpaint.setTextAlign(Paint.Align.CENTER);
       // canvas.drawText(statement2, 30, 70 , hpaint);
        int headerFontSize = 16;
       // Typeface typeface = StateUtils.SCOREBOARD_FONT_SANS;
        //String header = "Previous Play Calls";
        int xOffset = getApproxXToCenterText(statement2, hcurrentTypeFace, headerFontSize, 300);
        //canvas.drawText(statement1, xOffset, 50 , hpaint);
        canvas.drawText(statement2, xOffset, 70, hpaint);
        /*-------------------------------------*/
        Paint paint = new Paint();

        paint.setColor(Color.GREEN);
        paint.setTextSize(14); // set font size
        Typeface currentTypeFace =   paint.getTypeface();
        Typeface bold = Typeface.create(currentTypeFace, Typeface.BOLD);
        paint.setTypeface(bold);




        System.out.println("Abhijit its coming After create pdf >>>>>>>>>>>>>>>>>>>>>>"+str);
        StringTokenizer multiTokenizer = new StringTokenizer(str, ":");
        String[] tokens = str.split(":");
        int i=0,j=0;
        for (String token : tokens)
        {
            if(token!="null")
            System.out.println("token at for loop >>>>>>>>>>>>>>>>>>>>"+token);
        }

        while (multiTokenizer.hasMoreTokens())
        {
            //System.out.println("token>>>>>>>>>>>>>>>>>>>>>>>>>"+i+"  "+multiTokenizer.nextToken());
            if(multiTokenizer.toString().toString()!="null") {
                canvas.drawText(multiTokenizer.nextToken().toString(), 20, 100 + j, paint);
                i++;
                j = j + 20;
            }
        }


        float startX = 0;
        float startY = 100+j;
        float stopX = 300;
        float stopY = 100+j;

        canvas.drawLine(startX, startY, stopX, stopY, paint);
        int y=100+j+30;
        canvas.drawText("Starting Bal. for "+statement2+" is : "+start_cost+"/-", 05, y, paint);

        canvas.drawText("Total exps. for "+statement2+" is : "+total_cost+"/-", 05, y+20, paint);

        canvas.drawText("Total Bal.in "+statement2+" is: "+end_cost+"/-", 05, y+40, paint);
        /*----------------------------------------------------*/
        document.finishPage(page);
// draw text on the graphics object of the page
        // Create Page 2
        pageInfo = new PdfDocument.PageInfo.Builder(300, 600, 2).create();
        page = document.startPage(pageInfo);
        canvas = page.getCanvas();
        paint = new Paint();
        paint.setColor(Color.BLUE);
        canvas.drawCircle(100, 100, 100, paint);
        document.finishPage(page);
        // write the document content
        String directory_path = Environment.getExternalStorageDirectory().getPath() + "/mypdf/";
        File file = new File(directory_path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String targetPdf = directory_path+"Deepa mention-2 Monthly Maintance report card.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
            myPdfUrl=filePath.getAbsolutePath();
            Toast.makeText(this, "Done  File created here...."+filePath.getAbsolutePath(), Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            Log.e("main", "error "+e.toString());
            Toast.makeText(this, "Something wrong: " + e.toString(),  Toast.LENGTH_LONG).show();
        }
        // close the document
        document.close();
    }
    public static int getApproxXToCenterText(String text, Typeface typeface, int fontSize, int widthToFitStringInto) {
        Paint p = new Paint();
        p.setTypeface(typeface);
        p.setTextSize(fontSize);
        float textWidth = p.measureText(text);
        int xOffset = (int)((widthToFitStringInto-textWidth)/2f) - (int)(fontSize/2f);
        return xOffset;
    }
    public void adddata()
    {
       /* Chunk chunk1 = new Chunk("Date: ", normal);
        Phrase ph1 = new Phrase(chunk1);

        Chunk chunk2 = new Chunk(collectionDate, bold);
        Phrase ph2 = new Phrase(chunk2);

        Chunk chunk3 = new Chunk("\nEmail: ", normal);
        Phrase ph3 = new Phrase(chunk3);

        Chunk chunk4 = new Chunk(email, bold);
        Phrase ph4 = new Phrase(chunk4);

        Paragraph ph = new Paragraph();
        ph.add(ph1);
        ph.add(ph2);
        ph.add(ph3);
        ph.add(ph4);*/

    }


}
