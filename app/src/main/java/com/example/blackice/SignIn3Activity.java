package com.example.blackice;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignIn3Activity extends AppCompatActivity {
    //private Button goLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in3);

        //LoginActivity로 연결
        Button goLogin = findViewById(R.id.LoginBtn3);
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn3Activity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}