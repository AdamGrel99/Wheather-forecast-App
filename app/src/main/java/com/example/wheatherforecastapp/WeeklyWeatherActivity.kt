package com.example.wheatherforecastapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.example.wheatherforecastapp.databinding.ActivityWeeklyWeatherBinding
import com.koushikdutta.ion.Ion
import com.squareup.picasso.Picasso
import org.json.JSONObject

class WeeklyWeatherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityWeeklyWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWeeklyWeatherBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val city = intent.getStringExtra("city")
        val position = intent.getIntExtra("position",10)
        binding.choiceItem.text = city

        when (position) {
            0 -> { weatherApi("http://api.weatherapi.com/v1/current.json?key=b501259713f8483f906102211221008&q=New York&aqi=yes") }
            1 -> { weatherApi("http://api.weatherapi.com/v1/current.json?key=b501259713f8483f906102211221008&q=London&aqi=yes") }
            2 -> { weatherApi("http://api.weatherapi.com/v1/current.json?key=b501259713f8483f906102211221008&q=Tokyo&aqi=yes") }
            else -> { weatherApi("http://api.weatherapi.com/v1/current.json?key=b501259713f8483f906102211221008&q=Warsaw&aqi=yes") }
        }
    }

    private fun weatherApi(link: String){
        Ion.with(this)
            .load(link)
            .asString()
            .setCallback { _, result ->
                val temperature = JSONObject(result).getJSONObject("current").getInt("temp_c")
                val textOfCondition = JSONObject(result).getJSONObject("current").getJSONObject("condition").getString("text")
                val iconUrl = JSONObject(result).getJSONObject("current").getJSONObject("condition").getString("icon")
                Log.d("Test", "The Json data is: \n $temperature $textOfCondition $iconUrl")
                binding.temperature.text = "$temperature °C"
                binding.description.text = textOfCondition
                Picasso.get()
                    .load("http:$iconUrl")
                    .into(binding.iconImage)
            }
    }

    /*
    {
   "location":{
      "name":"London",
      "region":"City of London, Greater London",
      "country":"United Kingdom",
      "lat":51.52,
      "lon":-0.11,
      "tz_id":"Europe/London",
      "localtime_epoch":1660128822,
      "localtime":"2022-08-10 11:53"
   },
   "current":{
      "last_updated_epoch":1660128300,
      "last_updated":"2022-08-10 11:45",
      "temp_c":26.0,
      "temp_f":78.8,
      "is_day":1,
      "condition":{
         "text":"Sunny",
         "icon":"//cdn.weatherapi.com/weather/64x64/day/113.png",
         "code":1000
      },
      "wind_mph":10.5,
      "wind_kph":16.9,
      "wind_degree":80,
      "wind_dir":"E",
      "pressure_mb":1026.0,
      "pressure_in":30.3,
      "precip_mm":0.0,
      "precip_in":0.0,
      "humidity":45,
      "cloud":0,
      "feelslike_c":26.3,
      "feelslike_f":79.3,
      "vis_km":10.0,
      "vis_miles":6.0,
      "uv":7.0,
      "gust_mph":8.1,
      "gust_kph":13.0,
      "air_quality":{
         "co":185.3000030517578,
         "no2":9.0,
         "o3":79.4000015258789,
         "so2":6.300000190734863,
         "pm2_5":3.9000000953674316,
         "pm10":6.5,
         "us-epa-index":1,
         "gb-defra-index":1
      }
   }
}
     */
}
