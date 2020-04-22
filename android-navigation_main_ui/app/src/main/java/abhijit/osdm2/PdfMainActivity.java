package abhijit.osdm2;

/*import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;*/

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.webkit.WebView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
/*
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;*/
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;

//import java.awt.image.BufferedImage;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import net.sf.andpdf.nio.ByteBuffer;
import abhijit.osdm2.models.ImageDB;
import abhijit.osdm2.models.MonthlyExpence;
import abhijit.osdm2.rettrofitsupport.RetrofitClient;
import abhijit.osdm2.service.HerokuService;

import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.StringTokenizer;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;


public class PdfMainActivity extends AppCompatActivity {

    String myPdfUrl = " ";
    String ImageUrl = " ";
    String reportmonth="";
    String total_cost="";
    String start_cost="";
    String end_cost="";
    WebView web_view=null;
    private int ViewSize = 0;
    String pdfData="";
    MonthlyExpence monthlyExpence;
    List <ImageDB> imagedblist = new ArrayList<ImageDB>();
    String[] emailid;
    String mailingList="";
    String monthyr="";

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_main);
       //Setup webview
        web_view=(WebView) findViewById(R.id.pdfwebView);
        web_view.getSettings().setBuiltInZoomControls(true);//show zoom buttons
        web_view.getSettings().setSupportZoom(true);//allow zoom
        //get the width of the webview
        web_view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {

                System.out.println("Login Type and rule <PdfMainActivity>...   on global layout ");
                ViewSize = web_view.getWidth();
                web_view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }

        });

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        //getActionBar().setDisplayHomeAsUpEnabled(true);

        emailid=getAllEmails();

       Intent intent = getIntent();
        String pdfstring = intent.getStringExtra("pdfString");
        total_cost =intent.getStringExtra("total_cost");
        start_cost =intent.getStringExtra("start_val");
        end_cost=intent.getStringExtra("remain_val");
        monthyr=intent.getStringExtra("currentMonth");

        monthlyExpence=new MonthlyExpence();
        try {
            createPdf_withItextg(pdfstring);
        }
        catch (Exception y){}
        uploadimage(myPdfUrl);
        ShowpdfFile(pdfData);
        web_view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                System.out.println("Abhijit its coming onTouch.............>>>>>>>>>>>>>");
                return false;
            }
        });
     }

   public void ShowpdfFile(String pdf_data)
    {
        try {
            byte[] data = Base64.decode(pdf_data, Base64.DEFAULT);
            pdfLoadImages(data);
        }
        catch(Exception e){

        }
    }
    private void pdfLoadImages(final byte[] data)
    {
        try
        {
            // run async
            new AsyncTask<Void, Void, String>()
            {
                // create and show a progress dialog
                ProgressDialog progressDialog = ProgressDialog.show(PdfMainActivity.this, "", "Abhijit ........Opening...");

                @Override
                protected void onPostExecute(String html)
                {
                    //after async close progress dialog
                    progressDialog.dismiss();
                    //load the html in the webview
                    web_view.loadDataWithBaseURL("", html, "text/html","UTF-8", "");
                }

                @Override
                protected String doInBackground(Void... params)
                {
                    try
                    {
                        //create pdf document object from bytes
                        net.sf.andpdf.nio.ByteBuffer bb = ByteBuffer.NEW(data);
                        PDFFile pdf = new PDFFile(bb);
                        //Get the first page from the pdf doc
                        PDFPage PDFpage = pdf.getPage(1, true);
                        //create a scaling value according to the WebView Width
                        final float scale = ViewSize / PDFpage.getWidth() * 0.95f;
                        //convert the page into a bitmap with a scaling value
                        Bitmap page = PDFpage.getImage((int)(PDFpage.getWidth() * scale), (int)(PDFpage.getHeight() * scale), null, true, true);
                        //save the bitmap to a byte array
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        page.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] byteArray = stream.toByteArray();
                        stream.reset();
                        //convert the byte array to a base64 string
                        String base64 = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                        //create the html + add the first image to the html
                        String html = "<!DOCTYPE html><html><body bgcolor=\"#b4b4b4\"><img src=\"data:image/png;base64,"+base64+"\" hspace=10 vspace=10><br>";
                        //loop though the rest of the pages and repeat the above
                        System.out.println("Abhijit in side Image activity:  number of pdf page ......."+pdf.getNumPages());

                        for(int i = 2; i <= pdf.getNumPages(); i++)
                        {
                            PDFpage = pdf.getPage(i, true);
                            page = PDFpage.getImage((int)(PDFpage.getWidth() * scale), (int)(PDFpage.getHeight() * scale), null, true, true);
                            page.compress(Bitmap.CompressFormat.PNG, 100, stream);
                            byteArray = stream.toByteArray();
                            stream.reset();
                            base64 = Base64.encodeToString(byteArray, Base64.NO_WRAP);
                            html += "<img src=\"data:image/png;base64,"+base64+"\" hspace=10 vspace=10><br>";
                        }
                        stream.close();
                        html += "</body></html>";
                        return html;
                    }
                    catch (Exception e)
                    {
                        Log.d("error", e.toString());
                    }
                    return null;
                }
            }.execute();
            System.gc();// run GC
        }
        catch (Exception e)
        {
            Log.d("error", e.toString());
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && web_view.canGoBack()) {

            System.out.println("Abhijit its coming onTouch.............>>>>>>>>>>>>>");
            web_view.goBack();
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
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
        //ActionBar ab=target.getAction();
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

       // Intent intent = Intent.createChooser(target, "Abhijit");//Open File");
        //intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);


        System.out.println("Abhijit its coming Open PDF...1 >>>>>>>>>>>>>>>>>>>>>>");
        try {
            startActivity(target);//intent);
            finish();
        } catch (ActivityNotFoundException e) {

            e.printStackTrace();
            // Instruct the user to install a PDF reader here, or something
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
       //  Inflate the menu; this adds items to the action bar if it is present.
         getMenuInflater().inflate(R.menu.action_menu, menu);
        System.out.println("ACTION BAR ADD  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        System.out.println("ACTION BAR ADD  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+item.getItemId());
        switch(item.getItemId()) {
        case R.id.sendviamail:
            //add the function to perform here
            System.out.println("ACTION BAR ADD  >>>>>>>>>>>>>>>>>>sendviamail>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+item.getItemId());
            ShareViaEmail(myPdfUrl);
            return(true);
        case R.id.store:
            //add the function to perform here
            System.out.println("ACTION BAR RESET  >>>>>>>>>>>>>>>>>>>>>>>>>store>>>>>>>>>>>>>>>>>>>>>>>>>>>"+item.getItemId());
            return(true);
        case R.id.sharewhatsapp:
            //add the function to perform here
            System.out.println("ACTION BAR ABOUT >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>sharewhatsapp>>>>>>>>>>>>>>>>>>>>>"+item.getItemId());
            return(true);
        case R.id.sharecontact:
            //add the function to perform here
            System.out.println("ACTION BAR EXIT  >>>>>>>>>>>>>>>>>>>>>>>>>>>>>sharecontact>>>>>>>>>>>>>>>>>>>>>>>"+item.getItemId());
            return(true);
    }
        return(super.onOptionsItemSelected(item));
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


    private void createPdf_withItextg(String str) throws Exception {
        Document doc=new Document();
        String mfilepath= Environment.getExternalStorageDirectory()+"/"+"Deepa mention-2 Monthly Maintance report card.pdf";
        myPdfUrl=mfilepath;


        Font blueFont = FontFactory.getFont(FontFactory.TIMES_ITALIC, 30, Font.BOLD,CMYKColor.BLUE);// new CMYKColor(255, 0, 0, 0));
        Font redFont = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD, CMYKColor.RED);//new CMYKColor(0, 255, 0, 0));
        Font greenFont = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD, CMYKColor.GREEN);//new CMYKColor(0, 255, 0, 0));
        Font yellowFont = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD,CMYKColor.YELLOW);// new CMYKColor(0, 0, 255, 0));
        Font blackFont = FontFactory.getFont(FontFactory.COURIER, 20, Font.BOLD,CMYKColor.BLACK);// new CMYKColor(0, 0, 255, 0));
        Font blueFont_sign = FontFactory.getFont(FontFactory.TIMES_ITALIC, 20, Font.BOLD,CMYKColor.BLUE);// new CMYKColor(255, 0, 0, 0));


        String[]monthName={"Jan.","Feb.","Mar.", "Apr.", "May.", "Jun.", "Jul.",
                "Aug.", "Sept.", "Oct.", "Nov.",
                "Dec."};

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);

        System.out.println("Currrent Year>>>>>>>>>>>>>>>>>>"+year);
        System.out.println("Current Month>>>>>>>>>>>>>>>>>>>>>>"+monthName[month]);

        String statement1="Monthly Maintaince Report";
        String statement2=monthName[month]+" of "+year;

        System.out.println("Abhijit its coming After create pdf >>>>>>>>>>>>>>>>>>>>>>"+str);
        StringTokenizer multiTokenizer = new StringTokenizer(str, ":");
        String[] tokens = str.split(":");
        LineSeparator lineSeparator = new LineSeparator();
        lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));

        int i=0,j=0;
        for (String token : tokens)
        {
            if(token!="null")
                System.out.println("token at for loop >>>>>>>>>>>>>>>>>>>>"+token);
        }
        try {
            PdfWriter.getInstance(doc, new FileOutputStream(mfilepath));
            doc.open();
            doc.addAuthor("Om_Sai_Deepa_Mension_2");



            Paragraph p1 = new Paragraph(statement1+" "+statement2,blueFont);
            //com.itextpdf.text.Font paraFont= new com.itextpdf.text.Font(com.itextpdf.text.Font.FontFamily.COURIER,50f);
          //  com.itextpdf.text.Font paraFontf= FontFactory.getFont(FontFactory.TIMES_ROMAN, 33, Font.BOLD, new CMYKColor(255, 255, 0, 17));

            p1.setAlignment(Paragraph.ALIGN_CENTER);
           // p1.setFont(paraFontf);
            doc.add(p1);

            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));
            doc.add(lineSeparator);
            doc.add(new Paragraph(" "));
            doc.add(new Paragraph(" "));
        }
        catch(Exception u){System.out.println("Exception here"+u.getStackTrace());}
        com.itextpdf.text.List orderedList = new com.itextpdf.text.List(com.itextpdf.text.List.ORDERED);
        while (multiTokenizer.hasMoreTokens())
        {
            //System.out.println("token>>>>>>>>>>>>>>>>>>>>>>>>>"+i+"  "+multiTokenizer.nextToken());
            if(multiTokenizer.toString().toString()!="null") {
              // doc.add(new Paragraph(multiTokenizer.nextToken().toString(),blackFont));
                //orderedList
                orderedList.add(new com.itextpdf.text.ListItem(multiTokenizer.nextToken().toString(),blackFont));
                i++;
                j = j + 20;

            }
        }
        doc.add(orderedList);
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));
        doc.add(lineSeparator);
        doc.add(new Paragraph("Starting Bal. for "+statement2+" is: "+start_cost+"/-",blackFont));
        doc.add(new Paragraph("Total expns. for "+statement2+" is: "+total_cost+"/-",blackFont));
        doc.add(new Paragraph("Total Bal.in "+statement2+" is: "+end_cost+"/-",blackFont));
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));
        doc.add(new Paragraph(" "));
        String stamp=" Thanks Sincerly\n" +
                "Maintaince Team of OSDM-2";
        Paragraph p12 = new Paragraph(stamp,blueFont_sign);
        p12.setAlignment(Paragraph.ALIGN_RIGHT);
        doc.add(p12);

        doc.close();
    }
    private  void savepdf()
    {
        Document doc=new Document();
       // String mfile=new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(System.currentTimeMillis());
        String mfilepath= Environment.getExternalStorageDirectory()+"/"+"test.pdf";
        ImageUrl=mfilepath;
        //Font smallBold=new Font(Font.FontFamily.TIMES_ROMAN,12,Font.BOLD);
        try{
            PdfWriter.getInstance(doc,new FileOutputStream(mfilepath));
            doc.open();
            String mtext="Abhijit its with textg pdf";//"";
            doc.addAuthor("harikesh");
            doc.add(new Paragraph(mtext));
            doc.add(new Paragraph("Abhijit This is second line"));
            doc.add(new Paragraph("Abhijit This is third  line"));
            doc.add(new Paragraph("Abhijit This is 4th  line"));
            doc.close();
            Toast.makeText(this, "test.pdf"+" is saved to "+mfilepath, Toast.LENGTH_SHORT).show();
        }
        catch (Exception e)
        {
            Toast.makeText(this,"This is Error msg : " +e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    public void uploadimage(String location)
    {
        byte[] data=null;
        try
        {
            File file = new File(location);//Environment.getExternalStorageDirectory().getPath() + "/randompdf.pdf");
            RandomAccessFile f = new RandomAccessFile(file, "r");
             data= new byte[(int)f.length()];
            f.readFully(data);
        }
        catch(Exception ignored)
        {
        }
        //String month = Base64.encodeBase64String(data);
        String imageData = Base64.encodeToString(data, Base64.DEFAULT);//getEncoder().encodeToString(someByteArray);
        pdfData=imageData;
        ImageDB imagedb=new ImageDB(1,location,imageData,monthyr);
        UploadImage(imagedb);

       /* ImageDB imgd1=new ImageDB();
        imgd1.setMonth("Mar.2020");
        ImageDB imgd=DownloadImage(imgd1);*/

    }


    public void UploadImage(ImageDB imagedb)
    {
   /*     Log.i("autolog", "getUserList");
        try {
            String url = "https://postgres2322.herokuapp.com/imgc/";
            Log.i("autolog<UploadPDF>", "https://postgres2322.herokuapp.com/imgc/");

            Retrofit retrofit = null;
            Log.i("autolog<UploadPDF>", "retrofit");

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Log.i("autolog<UploadPDF>", "build();");
            }


            HerokuService service = retrofit.create(HerokuService.class);
            Log.i("autolog<UploadPDF>", " APIService service = retrofit.create(APIService.class);");


            Call<ImageDB> call = service.uploadImage(imagedb);
            Log.i("autolog<UploadPDF>", "<UploadPDF> call = service.uploadfile();");

            call.enqueue(new Callback<ImageDB>() {
                @Override
                public void onResponse(Call <ImageDB> call, Response<ImageDB> response) {
                    ImageDB fileDB = response.body();
                    Toast.makeText(getApplicationContext(), "File uploaded<UploadPDF>", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ImageDB> call, Throwable t) {

                }
            });
        }catch (Exception e) {Log.i("autolog<UploadPDF>", "Exception");}*/
        System.out.println("I am here ...<Monthly expence Class>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.uploadImage(imagedb).enqueue(new Callback<ImageDB>() {
            @Override
            public void onResponse(Call<ImageDB> call, Response<ImageDB> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    ImageDB fileDB = response.body();
                    Toast.makeText(getApplicationContext(), "File uploaded<UploadPDF>", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ImageDB>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
    }

/*
    public ImageDB  DownloadImage(ImageDB imagedb) {
       /* Log.i("autolog", "downloadimage");
        System.out.println("DownloadPDF>>>>>>>>>>>>>><downloadimage>>>>"+imagedb.getMonth());
        try {
            //https://postgres2322.herokuapp.com/filemnt/downloadfile
            String url = "https://postgres2322.herokuapp.com/imgc/";
            Log.i("autolog", "https://postgres2322.herokuapp.com/imgc/");

            Retrofit retrofit = null;
            Log.i("autolog", "retrofit");

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(new NullOnEmptyConverterFactory())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Log.i("autolog", "build();");
            }


            HerokuService service = retrofit.create(HerokuService.class);
            Log.i("autolog", " APIService service = retrofit.create(APIService.class);");

            Call<List<ImageDB>> call = service.downloadImage(imagedb);//imagedb.getMonth().toString());
            Log.i("autolog", "<FileDB>> call = service.downloaimage(imagrdb);");

            call.enqueue(new Callback<List<ImageDB>>() {
                {
                    Log.i("autolog", "<FileDB>> callback hitted....");
                }
                @Override
                public void onResponse(Call<List<ImageDB>> call, Response<List<ImageDB>> response) {

                    System.out.println("JSON RESPONSE : "+new Gson().toJson(response.body()) );

                    if(response.isSuccessful()) {
                        System.out.println("responsebody  :"+response.body());
                        imagedblist = response.body();
                        System.out.println("DownloadPDF- onResponse" + imagedblist.get(0).getImagename());// + "    " + imgdb.getImagename());

                        Toast.makeText(getApplicationContext(), "File download", Toast.LENGTH_SHORT).show();
                        Intent opa = new Intent(getApplicationContext(), ImageViewActivity.class);
                        opa.putExtra("pdffileurl", myPdfUrl);
                        opa.putExtra("pdfdata", imagedblist.get(0).getImagedata());
                        getApplicationContext().startActivity(opa);
                    }
                    else
                    {
                        System.out.println("DownloadPDF- onResponse NOT Success");
                    }

                }

                @Override
                public void onFailure(Call<List<ImageDB>>call, Throwable t) {
                    //download=false;
                    System.out.println("DownloadPDF---onFailure");
                    System.out.println(t.fillInStackTrace());
                    Toast.makeText(getApplicationContext(), " failure File download", Toast.LENGTH_SHORT).show();

                }
            });

            //System.out.println("DownloadPDF>>>>>>>>>>>>> at last  Callback hitted"+(fileDBs==null));
            Log.i("autolog", "rsponse failure ke last main............");

        } catch (Exception e) {
            Log.i("autolog", "Exception    "+e.getMessage());
        }
        */
/*
        System.out.println("I am here ...<Monthly expence Class>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.downloadImage(imagedb).enqueue(new Callback<List<ImageDB>>() {
            @Override
            public void onResponse(Call<List<ImageDB>> call, Response<List<ImageDB>> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    System.out.println("responsebody  :"+response.body());
                    imagedblist = response.body();
                    System.out.println("DownloadPDF- onResponse" + imagedblist.get(0).getImagename());// + "    " + imgdb.getImagename());

                    Toast.makeText(getApplicationContext(), "File download", Toast.LENGTH_SHORT).show();
                    Intent opa = new Intent(getApplicationContext(), ImageViewActivity.class);
                    opa.putExtra("pdffileurl", myPdfUrl);
                    opa.putExtra("pdfdata", imagedblist.get(0).getImagedata());
                    getApplicationContext().startActivity(opa);
                }
            }
            @Override
            public void onFailure(Call<List<ImageDB>>  call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });
        return null;
    }
*/
    private void ShareViaEmail1(String filelocation) {
        try {
            File root= Environment.getExternalStorageDirectory();
            //String filelocation= root.getAbsolutePath() + folder_name + "/" + file_name;
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            String message="Please find the Monthly report attached here ";
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
            intent.putExtra(Intent.EXTRA_STREAM, Uri.parse( "file://"+filelocation));
            intent.putExtra(Intent.EXTRA_TEXT, message);
            intent.setData(Uri.parse("mailto:"+mailingList));//abhijit.golo@gmail.com;abhijit.golo@gmail.com"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(intent, "Send email using:"));
           // startActivity(intent);
        } catch(Exception e)  {
            System.out.println("is exception raises during sending mail"+e);
        }
    }

    public void ShareViaEmail(String strFile) {
        try {
           // strFile = Environment.getExternalStorageDirectory()
            ///        .getAbsolutePath() + "/temp";

            File file = new File(strFile);
            if (!file.exists())
                file.mkdirs();
         //   strFile = strFile + "/report.html";
          //  createFile();
//
            Log.i(getClass().getSimpleName(), "send  task - start");
//
            final Intent emailIntent = new Intent(
                    android.content.Intent.ACTION_SEND);
//
           String address = mailingList;// "hamdy.ghanem@gmail.com";
           String  subject = "Monthly Maintance Card";
           String  emailtext = "Please Find the Attached Monthly Maintanance Report Card";
//
            emailIntent.setType("plain/text");

            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,
                    new String[] { address });

            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);

            emailIntent.putExtra(Intent.EXTRA_STREAM,
                    Uri.parse("file://" + strFile));

            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, emailtext);

            this.startActivity(Intent
                    .createChooser(emailIntent, "Send mail..."));

        } catch (Throwable t) {
            Toast.makeText(this, "Request failed: " + t.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
    public String [] getAllEmails1()
    {
        String url = "https://postgres2322.herokuapp.com/common/";
        Log.i("autolog<GetEmails>", "https://postgres2322.herokuapp.com/common/");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getAllEmailids().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>>call, Response<List<String>>response) {

                if(response.isSuccessful()) {
                    System.out.println("the Response  >>>>>. in Email Ids" + response.body().toString());
                    System.out.println("Get Email Ids the Response  >>>>> SUCCESS>>>>>>");

                    List<String> emaillist = response.body();
                    emailid = new String[emaillist.size()];
                    for (int i = 0; i < emaillist.size(); i++)
                        emailid[i] = emaillist.get(i);

                    for (String x : emailid) {
                        System.out.print("the email ids : " + x + " ");
                        mailingList = mailingList + x + ";";
                    }
                    System.out.println("Finally the email address : " + mailingList);

                }
                // admin_s= response.body();
                //status=response.body().toString();
                //  response_status=true;
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                System.out.println("This in  Failure >>>>>. Get Email Ids "+t.getMessage());
                // response_status=true;

            }
        });

        return emailid;
    }


    public String [] getAllEmails()
    {
        /*
        Log.i("autolog", "getUserList");

        try {
            String url = "https://postgres2322.herokuapp.com/common/";
            Log.i("autolog<GetEmails>", "https://postgres2322.herokuapp.com/common/");

            Retrofit retrofit = null;
            Log.i("autolog", "retrofit");

            if (retrofit == null) {
                retrofit = new Retrofit.Builder()
                        .baseUrl(url)
                        .addConverterFactory(GsonConverterFactory.create())
                        /*  .client(client)*//*
                        .build();
                Log.i("autolog", "build();");
            }


            HerokuService service = retrofit.create(HerokuService.class);
            Log.i("autolog", " APIService service = retrofit.create(APIService.class);");
            Call<List<String>> call = service.getAllEmailids(); //its a post...
            Log.i("autolog", "Call<List<Email Ids>> call = service.Email Ids();");
            System.out.println("I am here    <Email Ids>....(onresponse)    ");

            call.enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call <List<String>> call, Response<List<String>>response) {
                    if (response.isSuccessful()) {
                        System.out.println("the Response  >>>>>. in Email Ids" + response.body().toString());
                        System.out.println("Get Email Ids the Response  >>>>> SUCCESS>>>>>>");

                        List<String> emaillist = response.body();
                        emailid = new String[emaillist.size()];
                        for (int i = 0; i < emaillist.size(); i++)
                            emailid[i] = emaillist.get(i);

                        for (String x : emailid) {
                            System.out.print("the email ids" + x + " ");
                            mailingList = mailingList + x + ";";
                        }

                        System.out.println("Finally the email address : " + mailingList);

                    }
                }

                @Override
                public void onFailure(Call<List<String>>call, Throwable t) {
                    System.out.println("This in  Failure >>>>>. Get Email Ids "+t.getMessage());

                }
            });
        }catch (Exception e) {Log.i("autolog", " Email Ids Exception:   "+e.getMessage());}*/

        System.out.println("I am here ...<Monthly expence Class>..GetLoginRule-RETROFIT");
        HerokuService apiService = RetrofitClient.getApiService();
        apiService.getAllEmailids().enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                if(response.isSuccessful()) {
                    System.out.println("the Response  >>>>>. in Email Ids" + response.body().toString());
                    System.out.println("Get Email Ids the Response  >>>>> SUCCESS>>>>>>");

                    List<String> emaillist = response.body();
                    emailid = new String[emaillist.size()];
                    for (int i = 0; i < emaillist.size(); i++)
                        emailid[i] = emaillist.get(i);

                    for (String x : emailid) {
                        System.out.print("the email ids" + x + " ");
                        mailingList = mailingList + x + ";";
                    }

                    System.out.println("Finally the email address : " + mailingList);

                }

            }
            @Override
            public void onFailure(Call<List<String>>call, Throwable t) {
                System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                // response_status=true;
            }
        });

        return emailid;
    }
    class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody,Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);
                }
            };
        }
    }
  }
