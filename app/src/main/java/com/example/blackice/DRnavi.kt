package com.example.blackice

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_drnavi.*
import kotlinx.android.synthetic.main.activity_mypage.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class DRnavi : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drnavi)

        var firebaseRef = Firebase.database.getReference("SensorData")
        var anim: Animation? = null
        var start = false

        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("h:mm a")//"""yyyy년 MM월 dd일 HH시 mm분 ss초")
        val formatted = current.format(formatter)

        time01.setText(formatted)

        firebaseRef.child("Sensor3").child("Distance_bool").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //cnt = snapshot.childrenCount.toInt()
                val dis = snapshot.value.toString()

                if(dis=="true")
                {
                    firebaseRef.child("Sensor1").child("Temperature_bool").addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            //cnt = snapshot.childrenCount.toInt()
                            val temp = snapshot.value.toString()

                            if(temp!="false")
                            {
                                warning3.visibility = View.VISIBLE

                                    warning3.startAnimation(anim)


                            }
                            else
                            {
                                warning3.visibility = View.GONE
                                warning3.clearAnimation()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            println("Failed to read value.")
                        }
                    })
                }
                else
                {
                    warning3.visibility = View.GONE
                    warning3.clearAnimation()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                println("Failed to read value.")
            }
        })
        anim = AlphaAnimation(0.0f, 3.0f)
        anim.setDuration(200)
        anim.setStartOffset(20)
        anim.setRepeatMode(Animation.REVERSE)
        anim.setRepeatCount(Animation.INFINITE) //10
    }
}

