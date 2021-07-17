package com.example.blackice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        //전체동의
        CheckBox checkBox_all=findViewById(R.id.checkBox_all);
        //필수 서비스이용약관
        CheckBox checkBox1=findViewById(R.id.checkBox1);
        //필수 개인정보
        CheckBox checkBox2=findViewById(R.id.checkBox2);
        //선택 위치정보
        CheckBox checkBox3=findViewById(R.id.checkBox3);

        Button button_cancel = findViewById(R.id.button_cancel);
        Button button_ok = findViewById(R.id.button_ok);

        //전체동의 클릭시
        checkBox_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox_all.isChecked()){
                    checkBox1.setChecked(true);
                    checkBox2.setChecked(true);
                    checkBox3.setChecked(true);
                }
                else {
                    checkBox1.setChecked(false);
                    checkBox2.setChecked(false);
                    checkBox3.setChecked(false);
                }
            }
        });

        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignInActivity.this, SignIn2Activity.class);
                startActivity(intent);
            }
        });

        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox_all.isChecked() == true || checkBox1.isChecked() ==true&&checkBox2.isChecked() ==true&&checkBox3.isChecked() ==true) {
                    Intent intent = new Intent(SignInActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(),
                            "약관을 모두 동의해주세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

}