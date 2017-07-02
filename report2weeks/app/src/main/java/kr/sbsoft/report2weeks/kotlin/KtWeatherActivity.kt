package kr.sbsoft.report2weeks.kotlin

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.picasso.Picasso
import kr.sbsoft.report2weeks.R
import kr.sbsoft.report2weeks.StaticVariables
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET

/**
 * Created by SBJang on 2017. 7. 1..
 */

class KtWeatherActivity : AppCompatActivity() {

    private val context: Context
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)

        // https://openweathermap.org/current
        val retrofit = Retrofit.Builder()
                .baseUrl(StaticVariables.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build()
        val service = retrofit.create(WeatherInterface::class.java)
        val result = service.test()


        result.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    val responseData = response.body()!!.string()

                    (findViewById(R.id.tv_log) as TextView).text = responseData

                    var jobj: JSONObject = JSONObject(responseData)
                    val jarr: JSONArray = jobj.getJSONArray("weather")
                    jobj = jarr.getJSONObject(0)
                    val main = jobj.getString("main")
                    val desc = jobj.getString("description")
                    val imgId = jobj.getString("icon")

                    (findViewById(R.id.tv_main) as TextView).text = main
                    (findViewById(R.id.tv_description) as TextView).text = desc

                    // 날씨 아이콘 => https://openweathermap.org/weather-conditions

                    Picasso.with(context)
                            .load("http://openweathermap.org/img/w/$imgId.png")
                            .into(findViewById(R.id.iv_weather) as ImageView)


                } catch (e: Exception) {
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                    e.printStackTrace()
                }

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {

            }
        })

    }

    interface WeatherInterface {
        @GET("/data/2.5/weather?q=Incheon&APPID=" + StaticVariables.API_KEY)
        fun test(/*@Path("city") String city*/): Call<ResponseBody>
    }
}