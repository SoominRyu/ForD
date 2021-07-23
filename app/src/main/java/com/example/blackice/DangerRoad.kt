package com.example.blackice

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_danger_road.*
import android.widget.*

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


var cnt = 0

class DangerRoad : AppCompatActivity() {
    //카카오맵 패키지
    val packagename_kakaomap = "net.daum.android.map"

    var firebaseRef = Firebase.database.getReference("List")
    var kakaourl = ""
    var drtype = ""
    var drcontent = ""
    var user_id = "test"
    var dr_date = "2021-07-23 11:00 test"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_danger_road)


        //위부앱 (카카오맵)으로 이동 , 미설치자 - 구글 플레이 카카오맵으로
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

        val kakaourlEdit = findViewById<View>(R.id.locationURL) as EditText
        val drcontentEdit = findViewById<View>(R.id.DRcontent) as EditText

        // access the items of the list
        val danger = resources.getStringArray(R.array.Danger)
        val spinner = findViewById<Spinner>(R.id.spinner_DR)

        var pos = ""

        // 스피너 연동
        if (spinner != null) {
            val adapter = ArrayAdapter(this,
                    android.R.layout.simple_spinner_item, danger)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {

                    Toast.makeText(this@DangerRoad,
                            getString(R.string.selected_item) + " " +
                                    "" + danger[position], Toast.LENGTH_SHORT).show()
                    pos = danger[position].toString()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }


        Btn.setOnClickListener {
            //edittext 값이 다 들어갔는지 확인
            kakaourl = kakaourlEdit.text.toString()
            drcontent = drcontentEdit.text.toString()
            drtype = pos
            user_id
            dr_date

            initcount()



        }


    }


    private fun initcount(){
        firebaseRef.child("DangersReportLIst").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                cnt = snapshot.childrenCount.toInt()
                var nextcnt = (cnt+1).toString()
                DRreport(nextcnt)

            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })
    }

    //파베 추가
    private fun DRreport(nextcnt: String){
        firebaseRef.child("DangersReportLIst").child(nextcnt).child("DRClassify").setValue(drtype)
        firebaseRef.child("DangersReportLIst").child(nextcnt).child("DRContent").setValue(drcontent)
        firebaseRef.child("DangersReportLIst").child(nextcnt).child("DRDate").setValue(dr_date)
        firebaseRef.child("DangersReportLIst").child(nextcnt).child("DRLocate").setValue(kakaourl)
        firebaseRef.child("DangersReportLIst").child(nextcnt).child("DRUser").setValue(user_id)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)

    }






}
