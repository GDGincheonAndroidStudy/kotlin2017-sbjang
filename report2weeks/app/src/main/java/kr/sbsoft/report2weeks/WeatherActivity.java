package kr.sbsoft.report2weeks;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import retrofit2.http.GET;

public class WeatherActivity extends AppCompatActivity {

    private Context getContext(){ return (Context)this; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        // https://openweathermap.org/current
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticVariables.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        WeatherInterface service = retrofit.create(WeatherInterface.class);
        Call<ResponseBody> result = service.test();


        result.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    String responseData = response.body().string();

                    ((TextView)findViewById(R.id.tv_log)).setText(responseData);

                    JSONObject jobj = new JSONObject(responseData);
                    JSONArray jarr = jobj.getJSONArray("weather");
                    jobj = jarr.getJSONObject(0);
                    final String main = jobj.getString("main");
                    final String desc = jobj.getString("description");
                    final String imgId = jobj.getString("icon");

                    ((TextView)findViewById(R.id.tv_main)).setText(main);
                    ((TextView)findViewById(R.id.tv_description)).setText(desc);

                    // 날씨 아이콘 => https://openweathermap.org/weather-conditions

                    Picasso.with(getContext())
                            .load("http://openweathermap.org/img/w/"+imgId+".png")
                            .into((ImageView)findViewById(R.id.iv_weather));


                } catch (Exception e) {
                    Toast.makeText(getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });

    }

    public interface WeatherInterface {
        @GET("/data/2.5/weather?q=Incheon&APPID="+StaticVariables.API_KEY)
        Call<ResponseBody> test(/*@Path("city") String city*/);
    }
}
