package abhijit.osdm2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import android.widget.Button;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFImage;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFPaint;
import net.sf.andpdf.nio.ByteBuffer;
import net.sf.andpdf.refs.HardReference;
import java.io.ByteArrayOutputStream;


public class ImageViewActivity extends AppCompatActivity {
    //Globals:
    private WebView wv;
    private int ViewSize = 0;
    private Button emailshare;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_image_view);



        //Settings
        PDFImage.sShowImages = true; // show images
        PDFPaint.s_doAntiAlias = true; // make text smooth
        HardReference.sKeepCaches = true; // save images in cache


        Intent intent = getIntent();
        String pdffileurl = intent.getStringExtra("pdffileurl");
        String pdfdata = intent.getStringExtra("pdfdata");


        //Setup webview
        wv = (WebView)findViewById(R.id.webView1);
       // emailshare=(Button) findViewById(R.id.share_email);

        wv.getSettings().setBuiltInZoomControls(true);//show zoom buttons
        wv.getSettings().setSupportZoom(true);//allow zoom
        //get the width of the webview
        wv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener()
        {
            @Override
            public void onGlobalLayout()
            {

                System.out.println("Login Type and rule <ImageViewActivity>...   on global layout ");
                ViewSize = wv.getWidth();
                wv.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }

        });
//added for check the actionbar ++
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("Android ");
       // AdRequest adRequest = new AdRequest.Builder().build();
//added for check the actionbar --

        try
        {
           // File file = new File(pdffileurl);//Environment.getExternalStorageDirectory().getPath() + "/randompdf.pdf");
           // RandomAccessFile f = new RandomAccessFile(file, "r");
           // byte[] data = new byte[(int)f.length()];
           // f.readFully(data);

          //  byte[] data= new byte[1024*4];
            //byte [] data=new byte[4096*8];
            //data=pdfdata.toByteArray();
            byte [] data = Base64.decode(pdfdata, Base64.DEFAULT);
                   // String month = Base64.decode()encodeToString(data, Base64.DEFAULT);
            pdfLoadImages(data);
        }
        catch(Exception ignored)
        {
        }


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        System.out.println("Login Type and rule <ImageViewActivity>....  onOptionsItemSelected...............");
        switch (item.getItemId()) {
            case android.R.id.home:
                //Write your logic here
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Load Images:
    private void pdfLoadImages(final byte[] data)
    {
        try
        {
            // run async
            new AsyncTask<Void, Void, String>()
            {
                // create and show a progress dialog
                ProgressDialog progressDialog = ProgressDialog.show(ImageViewActivity.this, "", "Abhijit ........Opening...");

                @Override
                protected void onPostExecute(String html)
                {
                    //after async close progress dialog
                    progressDialog.dismiss();
                    //load the html in the webview
                    wv.loadDataWithBaseURL("", html, "text/html","UTF-8", "");
                }

                @Override
                protected String doInBackground(Void... params)
                {
                    try
                    {
                        //create pdf document object from bytes
                        ByteBuffer bb = ByteBuffer.NEW(data);
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
                        html += "<button type=\"button\">Click Me!</button></body></html>";
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



}
