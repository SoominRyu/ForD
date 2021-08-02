package com.example.blackice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_d_r_custom_list.view.*



data class ListViewItem2(val title: String, val subTitle: String, val date: String)

class ListViewAdapter2(private val items: MutableList<ListViewItem2>): BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): ListViewItem2 = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.activity_d_r_custom_list, parent, false)

        val item: ListViewItem2 = items[position]

        if (convertView != null) {
            convertView.text2.text = item.title
        }
        if (convertView != null) {
            convertView.text3.text = item.subTitle
        }
        if (convertView != null) {
            convertView.text7.text = item.date
        }
        return convertView
    }
}

class DRListActivity : AppCompatActivity() {
    var cnt=0
    var firebaseRef = Firebase.database.getReference("List")

    val LIST_MENU = mutableListOf<ListViewItem2>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_r_list)

        val listview = findViewById<ListView>(R.id.IdListview)
        val adapter = ListViewAdapter2(LIST_MENU)
        listview.adapter = adapter





        val back: ImageView
        back = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this@DRListActivity, MainActivity::class.java)
            startActivity(intent) //액티비티 이동
        }

        initcount(listview, adapter)



    }

    private fun initcount(listview: ListView?, adapter: ListViewAdapter) {

        firebaseRef.child("DangersReportLIst").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cnt = snapshot.childrenCount.toInt()
               // data_date(listview, adapter, cnt)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })

    }


}