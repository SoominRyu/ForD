package com.example.blackice

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_mypage.*
import kotlinx.android.synthetic.main.mypage_dialog.view.*

var name=""
class MypageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mypage)

        // 이전 화면(메인화면)으로 돌아가기
        back.setOnClickListener({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })


        // changeUserName
        val builder = AlertDialog.Builder(this)
        changeUserName.setOnClickListener {
            val dialogView = layoutInflater.inflate(R.layout.mypage_dialog, null)


            builder.setView(dialogView)
                    .setPositiveButton("저장"){dialogInterface, i ->
                        name=dialogView.editext.text.toString()
                        userName.setText(name)
                    }
                    .setNegativeButton("취소") { dialogInterface, i ->
                        /* 취소일 때 아무 액션이 없으므로 빈칸 */
                    }

                    .show()
        }


    }
}