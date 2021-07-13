package com.example.blackice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;

public class SignInActivity extends AppCompatActivity {
    private Button Btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        Btn = findViewById(R.id.button_ok);

        Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SignipActivity 연결 - 예현 만드는 중(7/9)
                Intent intent = new Intent(SignInActivity.this, SignIn2Activity.class);
                startActivity(intent);
            }
        });

    }

}
