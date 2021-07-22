package com.example.blackice

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_danger_road.*
import android.widget.*


class DangerRoad : AppCompatActivity() {
    //카카오맵 패키지
    val packagename_kakaomap = "net.daum.android.map"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danger_road)


        kakaoMap.setOnClickListener{
            try{
                val intent = packageManager.getLaunchIntentForPackage(packagename_kakaomap)
                intent!!.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)

            }catch(e:Exception){
                val url = "market://details?id=$packagename_kakaomap"
                val i = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(i)
            }

        }

        // access the items of the list
        val danger = resources.getStringArray(R.array.Danger)

        val spinner = findViewById<Spinner>(R.id.spinner_DR)
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, danger)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    Toast.makeText(this@DangerRoad,
                            getString(R.string.selected_item) + " " +
                                    "" + danger[position], Toast.LENGTH_SHORT).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }
}