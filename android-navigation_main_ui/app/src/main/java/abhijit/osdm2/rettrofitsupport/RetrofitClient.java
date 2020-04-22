package abhijit.osdm2.rettrofitsupport;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import abhijit.osdm2.service.HerokuService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {
    private static final String BASE_URL = "https://postgres2322.herokuapp.com/api/v1/";
    private static Retrofit retrofit = null;
    public static HerokuService getApiService() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addCallAdapterFactory(RetryCallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit.create(HerokuService.class);
    }
}