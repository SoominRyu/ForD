package com.example.blackice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_d_r_list.view.text2
import kotlinx.android.synthetic.main.activity_d_r_list.view.text3
import kotlinx.android.synthetic.main.drw_listview.view.*

data class ListViewItem(val title: String, val subTitle: String, val date: String)

class ListViewAdapter(private val items: MutableList<ListViewItem>): BaseAdapter() {

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): ListViewItem = items[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, view: View?, parent: ViewGroup?): View? {
        var convertView = view
        if (convertView == null) convertView = LayoutInflater.from(parent?.context).inflate(R.layout.drw_listview, parent, false)

        val item: ListViewItem = items[position]
        if (convertView != null) {
            convertView.text2.text = item.title
        }
        if (convertView != null) {
            convertView.text3.text = item.subTitle
        }
        if (convertView != null) {
            convertView.text4.text = item.date
        }

        return convertView
    }
}



class DRwarningActivity : AppCompatActivity() {

    var cnt=0
    var firebaseRef = Firebase.database.getReference("List")

 //   val LIST_MENU: MutableList<String> = mutableListOf<String>("")
    val LIST_MENU = mutableListOf<ListViewItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_rwarning)

        val listview = findViewById<ListView>(R.id.IdListview)
       // val items = mutableListOf<ListViewItem>()
      //  val adapter = ArrayAdapter(this, R.layout.drw_listview,  LIST_MENU)


       val adapter = ListViewAdapter(LIST_MENU)
        listview.adapter = adapter


        initcount(listview,
                adapter)

        val back: TextView
        back = findViewById(R.id.text1)
        back.setOnClickListener {
            val intent = Intent(this@DRwarningActivity, MainActivity::class.java)
            startActivity(intent) //액티비티 이동
        }



    }

    private fun initcount(listview: ListView?, adapter: ListViewAdapter) {

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

    private fun data_date(listview: ListView?, adapter: ListViewAdapter, cnt: Int) {
        LIST_MENU.clear()
        Log.w("KEY-cnt", cnt.toString())
        for (i in 1..cnt) {


            firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRDate").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {


                    var key_date=snapshot.value.toString()
                    Log.w("KEY-date", key_date)
                    data_classify(listview, adapter, i, key_date)
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed to read value.")
                }
            })
        }

    }

    private fun  data_classify(listview: ListView?, adapter: ListViewAdapter, i: Int, key_date: String) {


        firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRClassify").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                var key_classify = snapshot.value.toString()
                Log.w("KEY-classify", key_classify)

                data_content(listview, adapter, i, key_date, key_classify)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })
    }

    private fun   data_content(listview: ListView?, adapter: ListViewAdapter, i: Int, key_date: String, key_classify: String) {


        firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRContent").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                val key_content = snapshot.value.toString()
                Log.w("KEY-content", key_content)

                val key= "["+key_classify+"] "+ key_date+ "\n"+key_content

                //LIST_MENU.add(key)
                LIST_MENU.add(ListViewItem( key_classify+" 구역 안내", key_content, key_date ))
                adapter.notifyDataSetChanged()
                // data_content(listview, adapter, cnt, key_date, key_classify)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })

    }
}