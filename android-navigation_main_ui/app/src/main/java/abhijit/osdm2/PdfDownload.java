package abhijit.osdm2;

import android.content.Intent;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.Toast;

import abhijit.osdm2.models.Admin;
import abhijit.osdm2.models.ImageDB;
import abhijit.osdm2.models.MonthlyExpence;
import abhijit.osdm2.rettrofitsupport.RetrofitClient;
import abhijit.osdm2.service.HerokuService;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
//import com.google.*;


//public class PdfDownload extends AppCompatActivity {

   /* @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_download);
    }*/
public class PdfDownload extends AppCompatActivity {
               String myPdfUrl = " ";
               String reportmonth = "";
               String total_cost = "";
               String start_cost = "";
               String end_cost = "";
               WebView web_view = null;
               MonthlyExpence monthlyExpence;
               boolean downloadcomplete=false;
               boolean download=false;
               private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
               int prog=0;
              // FileDB fileDBs=null;
               ImageDB imgdb=new ImageDB();
               List<ImageDB> imagelist=null;

               @Override
               protected void onCreate(Bundle savedInstanceState) {
                   setTitle("Preview Of Monthly Report");
                   super.onCreate(savedInstanceState);
                 //  setContentView(R.layout.activity_pdf_main);
                 //  web_view = (WebView) findViewById(R.id.web_view);
                   ImageDB imagedb = new ImageDB();
                   Intent intent = getIntent();
                   String monthyear=intent.getStringExtra("monthyear");

                   System.out.println("Abhijit its coming before  <PdfDownload> create pdf >>>>>>>>>>>>>>>>>>>>>>");
                   // CreatePdf();
                   StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                   StrictMode.setVmPolicy(builder.build());
                   try {
                        imagedb.setImagename("");
                       imagedb.setId(0);
                       imagedb.setImagedata(" ");
                       imagedb.setMonth("Mar.2020");
                         System.out.println("Abhijit gettomg stuck here <PdfDownload>>>>>>>>>>>>>>>monthyear >>>"+monthyear);
                        int i=0;
                       imagelist = new ArrayList<ImageDB>();
                       ImageDB imgd1=new ImageDB();
                       imgd1.setMonth(monthyear);
                       imgdb=DownloadImage(imgd1);
                   } catch (Exception y) {

                       System.out.println("Abhijit the downlowded file name Exception "+y.getMessage());
                   }

                   System.out.println("Abhijit its coming After create pdf >>>>>>><PdfDownload>>>>>>>>>>>>>>>>");

               }

       public ImageDB  DownloadImage(ImageDB imagedb) {
         /*  Log.i("autolog", "downloadimage");
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
                           imagelist = response.body();
                           System.out.println("DownloadPDF- onResponse" + imagelist.get(0).getImagename());// + "    " + imgdb.getImagename());

                           Toast.makeText(getApplicationContext(), "File download", Toast.LENGTH_SHORT).show();

                           Intent opa = new Intent(getApplicationContext(), ImageViewActivity.class);
                           opa.putExtra("pdffileurl", myPdfUrl);
                           opa.putExtra("pdfdata", imagelist.get(0).getImagedata());
                           getApplicationContext().startActivity(opa);
                           finish();
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
           }*/
           System.out.println("I am here ...<pdf download class>..GetLoginRule-RETROFIT");
           HerokuService apiService = RetrofitClient.getApiService();
           apiService.downloadImage(imagedb).enqueue(new Callback<ImageDB>() {
               @Override
               public void onResponse(Call<ImageDB> call, Response<ImageDB> response) {
                   System.out.println("I am here ...<LoginActivity>..GetLoginRule-onResponse");
                   if(response.isSuccessful()) {
                       System.out.println("responsebody  :"+response.body());
                     //  imagelist = response.body();
                       ImageDB idb=response.body();
                       System.out.println("DownloadPDF- onResponse +++++++++++++++++++++++++" + idb.getImagename()+" "+idb.getMonth());// + "    " + imgdb.getImagename());

                       Toast.makeText(getApplicationContext(), "File download", Toast.LENGTH_SHORT).show();

                       Intent opa = new Intent(getApplicationContext(), ImageViewActivity.class);
                       opa.putExtra("pdffileurl", myPdfUrl);
                      // opa.putExtra("pdfdata", imagelist.get(0).getImagedata());
                       opa.putExtra("pdfdata", idb.getImagedata());
                       getApplicationContext().startActivity(opa);
                       finish();
                   }
               }
               @Override
               public void onFailure(Call<ImageDB>  call, Throwable t) {
                   System.out.println("This in  Failure    GetLoginRule>>>>>. in login activity "+t.getMessage());
                   // response_status=true;
               }
           });
           return null;
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
