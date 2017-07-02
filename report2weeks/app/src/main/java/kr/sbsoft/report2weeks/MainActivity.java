package kr.sbsoft.report2weeks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import kr.sbsoft.report2weeks.kotlin.KtWeatherActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Context getContext(){ return (Context)this; }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnWetherJava = (Button) findViewById(R.id.btn_weather_java);
        btnWetherJava.setOnClickListener(this);
        Button btnWetherKotlin = (Button) findViewById(R.id.btn_weather_kotlin);
        btnWetherKotlin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_weather_java){
            startActivity(new Intent(getContext(), WeatherActivity.class));
        }else if (v.getId() == R.id.btn_weather_kotlin){
            startActivity(new Intent(getContext(), KtWeatherActivity.class));
        }
    }
}

