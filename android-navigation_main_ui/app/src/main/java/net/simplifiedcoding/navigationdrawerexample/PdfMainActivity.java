package net.simplifiedcoding.navigationdrawerexample;

/*import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;*/

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/*
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;*/
import com.google.gson.Gson;
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

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
//import java.awt.image.BufferedImage;
//import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import net.simplifiedcoding.navigationdrawerexample.models.FileDB;
import net.simplifiedcoding.navigationdrawerexample.models.ImageDB;
import net.simplifiedcoding.navigationdrawerexample.models.MonthlyExpence;
import net.simplifiedcoding.navigationdrawerexample.service.HerokuService;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import retrofit2.converter.gson.GsonConverterFactory;


public class PdfMainActivity extends AppCompatActivity {

    String myPdfUrl = " ";
    String ImageUrl = " ";
    String reportmonth="";
    String total_cost="";
    String start_cost="";
    String end_cost="";
    WebView web_view=null;
    MonthlyExpence monthlyExpence;
    List <ImageDB> imagedblist = new ArrayList<ImageDB>();

    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_main);
        web_view=(WebView) findViewById(R.id.web_view);

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

        monthlyExpence=new MonthlyExpence();
        try {
            //createPdfnice(pdfstring);
            createPdf_withItextg(pdfstring);
        }
        catch (Exception y){}
       // createPdfh("Monthly Expenditure\t"+pdfstring);
      //  pdfreader(myPdfUrl);
///====================working================
   //     savepdf();
    //    uploadimage(ImageUrl);

        //===================working=========
      //  uploadPdffile(myPdfUrl);
        uploadimage(myPdfUrl);
        OpenpdfFile(myPdfUrl);
     //   String tempurl=myPdfUrl;


        /* Intent opa = new Intent(getApplicationContext(), ImageViewActivity.class);
        opa.putExtra("pdffileurl", myPdfUrl);
        opa.putExtra("pdfdata", imagedblist.get(0).getMonth());
         getApplicationContext().startActivity(opa);*/
        /*Intent opa = new Intent(getApplicationContext(), ShowWebViewPdf.class);
        opa.putExtra("pdfurl", myPdfUrl);
        startActivity(opa);*/
        // myPdfUrl=tempurl;
        System.out.println("Abhijit its coming After create pdf >>>>>>>>>>>>>>>>>>>>>>");


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.setCancelable(false);
      //  WebView web_view = findViewById(R.id.web_view);
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


        web_view.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                System.out.println("Abhijit its coming onTouch.............>>>>>>>>>>>>>");
                return false;
            }
        });



       web_view.getSettings().setJavaScriptEnabled(true);
        final Activity activity = this;
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
        System.out.println("Abhijit its coming Open PDF...1 >>>>>>>>>>>>>>>>>>>>>>");
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
        reportmonth=monthName[month]+year; //tested pupose
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

        paint.setColor(Color.BLACK);//.GREEN);
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
        paint.setColor(Color.GREEN);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        int y=100+j+30;
        canvas.drawText("Starting Bal. for "+statement2+" is: "+start_cost+"/-", 05, y, paint);

        canvas.drawText("Total expns. for "+statement2+" is: "+total_cost+"/-", 05, y+20, paint);

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
            LineSeparator lineSeparator = new LineSeparator();
            lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));
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
        doc.add(new Paragraph("Starting Bal. for "+statement2+" is: "+start_cost+"/-",blackFont));
        doc.add(new Paragraph("Total expns. for "+statement2+" is: "+total_cost+"/-",blackFont));
        doc.add(new Paragraph("Total Bal.in "+statement2+" is: "+end_cost+"/-",blackFont));

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

    public void UploadPDF(FileDB fileDB)
    {
        Log.i("autolog", "getUserList");
        try {
            String url = "https://postgres2322.herokuapp.com/filemnt/";
            Log.i("autolog<UploadPDF>", "https://postgres2322.herokuapp.com/filemnt/");

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


            Call<FileDB> call = service.uploadfile(fileDB);
            Log.i("autolog<UploadPDF>", "<UploadPDF> call = service.uploadfile();");

            call.enqueue(new Callback<FileDB>() {
                @Override
                public void onResponse(Call <FileDB> call, Response<FileDB> response) {
                    FileDB fileDB = response.body();
                    Toast.makeText(getApplicationContext(), "File uploaded<UploadPDF>", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<FileDB> call, Throwable t) {

                }
            });
        }catch (Exception e) {Log.i("autolog<UploadPDF>", "Exception");}
    }

    public static byte[] stringToByte(String text) {


        try {
            System.out.println("Returning <FileUpController--stringToByte> 1"+text.length()+"{"+text+"}");
            System.out.println("Returning <FileUpController--stringToByte> 2");
            final String[] split = text.split("\\s+");
            final byte[] result = new byte[split.length];
            int i = 0;
            for (String b : split) result[i++] = (byte)Integer.parseInt(b, 16);
            System.out.println("Returning <FileUpController--stringToByte> 3");
            return result;
        }
        catch(Exception j) {System.out.println("Returning <FileUpController> Exception..stringToByte..:"+j.getMessage());}
        return null;
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
        ImageDB imagedb=new ImageDB(1,location,imageData,"Mar.2020");
        UploadImage(imagedb);

       /* ImageDB imgd1=new ImageDB();
        imgd1.setMonth("Mar.2020");
        ImageDB imgd=DownloadImage(imgd1);*/

    }


    public void UploadImage(ImageDB imagedb)
    {
        Log.i("autolog", "getUserList");
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
        }catch (Exception e) {Log.i("autolog<UploadPDF>", "Exception");}
    }


    public ImageDB  DownloadImage(ImageDB imagedb) {
        Log.i("autolog", "downloadimage");
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

        return null;
    }


    public void uploadPdffile(String flocation)
    {
        FileDB fileDB=new FileDB();
        byte[] buf = new byte[8192];
        //flocation="%2fstorage%2femulated%2f0%2fmypdf%2fDeepa+mention-2+Monthly+Maintance+report+card.pdf";
        System.out.println(" its in uploadPdffile >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 1.");
        byte[] buffer = new byte[4096*2];
        try {
            File f=new File(flocation);//"C:\\Documents and Settings\\abc\\Desktop\\abc.pdf");

         //   OutputStream oos = new FileOutputStream("test.pdf");

            //Path path = Paths.get(flocation);
            buf= convertbytearray(flocation);
            //Files.readAllBytes(path);
           // System.out.println(" its in uploadPdffile >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 1. BUF:  "+buf+"  String: "+new String(buf));
            InputStream is = new FileInputStream(f);

           /* int c = 0;

            while ((c = is.read(buf, 0, buf.length)) > 0) {
               // oos.write(buf, 0, c);

                System.out.println(" its in uploadPdffile >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 2."+buf);
              //  oos.flush();
            }
            System.out.println(" its in uploadPdffile >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 3."+buf);*/
          //  oos.close();
            System.out.println(" its in uploadPdffile >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 3.-------stop");
            is.close();
        }
        catch(Exception e){  System.out.println(" its in uploadPdffile >>>>>>>>>>>><Exception>>>>>>>>>>>>>>>>>>>>>>>> 3."+e.getMessage());  }

        fileDB.setFilename(flocation);
        fileDB.setFiletype("pdf");
        fileDB.setFiledata(buf);
        fileDB.setMonth(reportmonth);
        fileDB.setStringdata(new String(buf));
        UploadPDF(fileDB);

    }
    public byte[] convertbytearray(String sourcePath)
    {
        try {
            InputStream inputStream = new FileInputStream(sourcePath);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            int data;
            while ((data = inputStream.read()) >= 0) {
                outputStream.write(data);
            }

            inputStream.close();
            return outputStream.toByteArray();
        }
        catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
    public void pdfreader(URL flocation,String filename) {


        // input stream to read file - with 8k buffer
        File file = new File(filename);
        URL url = flocation;
        try {
            InputStream input = new BufferedInputStream(url.openStream(), 1024);
            file.createNewFile();

            OutputStream output = new FileOutputStream(file);


            byte[] data = new byte[1024];

            long total = 0;
            int count=0;

            while ((input.read(data)) != -1) {
                // writing data to file
                output.write(data, 0, count);

                System.out.println("  <FileDB>  output .............................> "+output);
                count++;
            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


  public void PDFtoImage(String location)
  {


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
