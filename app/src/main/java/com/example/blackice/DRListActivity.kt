package com.example.blackice

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

var cnt=0
var data_1= ""
var data_2= ""
var data_3= ""

class DRListActivity : AppCompatActivity() {

    var firebaseRef = Firebase.database.getReference("List")

    val LIST_MENU: MutableList<String> = mutableListOf<String>("")
    val arrList: MutableList<String> = mutableListOf<String>("")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_r_list)

        val listview = findViewById<ListView>(R.id.IdListview)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, LIST_MENU)
        listview.adapter = adapter

        initcount(listview, adapter)


    }

    private fun initcount(listview: ListView?, adapter: ArrayAdapter<String>) {

        firebaseRef.child("DangersReportLIst").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cnt = snapshot.childrenCount.toInt()
                data_date(listview, adapter, cnt)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })

    }

    private fun data_date(listview: ListView?, adapter: ArrayAdapter<String>, cnt: Int) {

        Log.w("KEY-cnt", cnt.toString())
        for (i in 1..cnt) {


            firebaseRef.child("DangersReportLIst").child(cnt.toString()).child("DRDate").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    LIST_MENU.clear()

                    var key_date=snapshot.value.toString()
                    Log.w("KEY-date", key_date)
                    data_classify(listview, adapter, cnt, key_date)
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed to read value.")
                }
            })
        }

    }

    private fun  data_classify(listview: ListView?, adapter: ArrayAdapter<String>, cnt: Int, key_date: String) {
        for (i in 1..cnt) {

            firebaseRef.child("DangersReportLIst").child(cnt.toString()).child("DRClassify").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    LIST_MENU.clear()

                    var key_classify = snapshot.value.toString()
                    Log.w("KEY-classify", key_classify)

                    data_content(listview, adapter, cnt, key_date, key_classify)
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed to read value.")
                }
            })
        }
    }
    private fun   data_content(listview: ListView?, adapter: ArrayAdapter<String>, cnt: Int, key_date: String, key_classify: String) {
        for (i in 1..cnt) {

            firebaseRef.child("DangersReportLIst").child(cnt.toString()).child("DRContent").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    LIST_MENU.clear()

                    val key_content = snapshot.value.toString()
                    Log.w("KEY-content", key_content)

                    val key= key_date+"  ["+key_classify+"]  \n"+key_content
                    LIST_MENU.add(key)
                    adapter.notifyDataSetChanged()
                   // data_content(listview, adapter, cnt, key_date, key_classify)
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed to read value.")
                }
            })
        }
    }



}