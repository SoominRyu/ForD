package com.example.blackice;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MypageActivity extends AppCompatActivity implements View.OnClickListener {

    Button changeNickname; //팝업버튼선언

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        //팝업버튼 설정
        changeNickname = (Button) findViewById(R.id.changeUserName); //R.id.alert는 팝업버튼 아이디
        changeNickname.setOnClickListener(this);

        //뒤로 가기 버튼
        ImageView back;
        back = findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MypageActivity.this, MainActivity.class);
                startActivity(intent); //액티비티 이동
            }
        });


    }

    public void onClick(View view) {
        if (view == changeNickname) { //view가 alert 이면 팝업실행 즉 버튼을 누르면 팝업창이 뜨는 조건
            Context mContext = getApplicationContext();
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

            //R.layout.dialog는 xml 파일명이고 R.id.popup은 보여줄 레이아웃 아이디
            View layout = inflater.inflate(R.layout.activity_mypage_popup, (ViewGroup) findViewById(R.id.mypage_popup));
            AlertDialog.Builder aDialog = new AlertDialog.Builder(MypageActivity.this);

            aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅

            //그냥 닫기버튼을 위한 부분
            aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            //팝업창 생성

            AlertDialog ad = aDialog.create();
            ad.show();//보여줌!
        }
    }
}