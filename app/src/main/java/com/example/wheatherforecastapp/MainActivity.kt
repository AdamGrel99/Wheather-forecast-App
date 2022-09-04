package com.example.wheatherforecastapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import com.example.wheatherforecastapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        val listOfCity = arrayListOf(
            "New York", "London", "Tokyo", "Warsaw"
        )

        val myAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, listOfCity)
        binding.listviewOfCity.adapter = myAdapter
        binding.listviewOfCity.setOnItemClickListener{ _,_,position,_ ->
            val intent = Intent(this, WeeklyWeatherActivity::class.java)
            intent.putExtra("city", listOfCity[position])
            intent.putExtra("position", position)
            startActivity(intent)
        }

        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Sources : \n https://www.weatherapi.com", Snackbar.LENGTH_SHORT)
                .setAction("Action", null)
                .show()
        }
    }
}
