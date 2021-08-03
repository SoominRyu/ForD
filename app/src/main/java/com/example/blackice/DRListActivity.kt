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

    var userid =""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_d_r_list)

        val listview = findViewById<ListView>(R.id.IdListview)
        val adapter = ListViewAdapter2(LIST_MENU)
        listview.adapter = adapter
        userid = intent.getStringExtra("userid")!!


        val back: ImageView
        back = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this@DRListActivity, MainActivity::class.java)
            intent.putExtra("userid", userid)
            startActivity(intent) //액티비티 이동
        }

        initcount(listview, adapter)

    }

    private fun initcount(listview: ListView?, adapter: ListViewAdapter2) {

        firebaseRef.child("DangersReportLIst").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cnt = snapshot.childrenCount.toInt()
                data_find(listview, adapter, cnt)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })

    }
    private fun data_find(listview: ListView?, adapter: ListViewAdapter2, cnt: Int) {
        LIST_MENU.clear()
        Log.w("KEY-cnt", cnt.toString())
        for (i in 1..cnt) {


            firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRUser").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    var key_data = snapshot.value.toString()
                    Log.w("KEY-find", userid +"//"+key_data+"//"+i)

                    if (userid == key_data) {

                        Log.w("KEY-find", "ok"+"//"+i)
                        // data_date(listview, adapter, cnt, key_data)
                        LIST_MENU.clear()
                        Log.w("KEY-cnt", cnt.toString())

                        firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRDate").addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {

                                Log.w("KEY-find-date", userid +"//"+key_data+"//"+i)
                                var key_date = snapshot.value.toString()
                                Log.w("KEY-date", key_date)
                                //if(userid==key_data) {
                                data_classify(listview, adapter, i, key_date, key_data)
                                // }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                println("Failed to read value.")
                            }
                        })
                    }
                    else
                    {
                        return
                    }


                    // Log.w("KEY-date", key_date)
                    //data_classify(listview, adapter, i, key_date)
                }

                override fun onCancelled(error: DatabaseError) {
                    println("Failed to read value.")
                }
            })
        }

    }
    private fun data_date(listview: ListView?, adapter: ListViewAdapter2, i: Int, key_data: String) {
        LIST_MENU.clear()
        Log.w("KEY-cnt", cnt.toString())



        firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRDate").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.w("KEY-find-date", userid +"//"+key_data+"//"+i)
                var key_date = snapshot.value.toString()
                Log.w("KEY-date", key_date)
                //if(userid==key_data) {
                data_classify(listview, adapter, i, key_date, key_data)
                // }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })


    }
    private fun  data_classify(listview: ListView?, adapter: ListViewAdapter2, i: Int, key_date: String, key_data: String) {



        firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRClassify").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                Log.w("KEY-find-cla", userid +"//"+key_data+"//"+i)
                var key_classify = snapshot.value.toString()
                Log.w("KEY-classify", key_classify)

                data_content(listview, adapter, i, key_date, key_classify,key_data)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })

    }

    private fun   data_content(listview: ListView?, adapter: ListViewAdapter2, i: Int, key_date: String, key_classify: String, key_data: String) {


        firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRContent").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.w("KEY-find-cont", userid +"//"+key_data+"//"+i)

                val key_content = snapshot.value.toString()
                Log.w("KEY-content", key_content)



                data_locate(listview, adapter, i, key_date, key_classify, key_content,key_data)
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })

    }
    private fun data_locate(listview: ListView?, adapter: ListViewAdapter2, i: Int, key_date: String, key_classify: String, key_content: String, key_data: String) {


        firebaseRef.child("DangersReportLIst").child(i.toString()).child("DRLocate").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val key_locate = snapshot.value.toString()
                Log.w("KEY-find-loca", userid +"//"+key_data+"//"+i)
                // data_user(listview, adapter, i, key_date, key_classify, key_content, key_locate)

                val key_test = key_locate.split("http")
                LIST_MENU.add(ListViewItem2(key_classify + ") " + key_content, key_test[0], key_date))

                adapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })

    }
}
