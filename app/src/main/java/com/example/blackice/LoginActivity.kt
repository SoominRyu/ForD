package com.example.blackice

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_login.*

var id=""
var pwd=""
class LoginActivity : AppCompatActivity() {
    var firebaseRef = Firebase.database.getReference("App")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        SigninBtn.setOnClickListener(View.OnClickListener { // SignipActivity 연결 - 예현 만드는 중(7/9)
            val intent = Intent(this@LoginActivity, SignInActivity::class.java)
            startActivity(intent)
        })
        LoginBtn.setOnClickListener(View.OnClickListener {
            id =userId.text.toString()
            pwd = userPw.text.toString()

            firebaseRef.child("Users").addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val children = snapshot.children.iterator()
                    var check = false
                    var key:String?
                    while (children.hasNext())
                    {
                        key = children.next().key
                        if(key==id)
                        {
                            firebaseRef.child("Users").child(key).child("Userpwd").addListenerForSingleValueEvent(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    val pw=snapshot.value.toString()

                                    if(pwd==pw )
                                    {
                                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                        intent.putExtra("userid", id)
                                        startActivity(intent)
                                        return

                                    }
                                    else
                                    {
                                        toast()

                                    }

                                }

                                override fun onCancelled(error: DatabaseError) {
                                    println("Failed to read value.")
                                }
                            })

                            check=true
                        }
                        else if(key!=id && !children.hasNext() && !check)
                        {
                            toast()

                        }
                    }

                }



                override fun onCancelled(error: DatabaseError) {
                    println("Failed to read value.")
                }
            })

        })
    }
    private fun toast() {
        Toast.makeText(this, "ID 혹은 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
    }
}