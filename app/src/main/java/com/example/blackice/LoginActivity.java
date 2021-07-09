package com.example.blackice;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {
    private Button Login;
    private Button Signin;
    private EditText LoginId;
    private EditText LoginPw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Signin = (Button) findViewById(R.id.SigninBtn);
        Login = (Button) findViewById(R.id.LoginBtn);
        LoginId = (EditText) findViewById(R.id.userId);
        LoginPw = (EditText) findViewById(R.id.userPw);

        Signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // SignipActivity 연결 - 예현 만드는 중(7/9)
                Intent intent = new Intent(LoginActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userid = LoginId.getText().toString().trim();
                String userpw = LoginPw.getText().toString().trim();
                /* 파이어베이스 연결 부분 ,
                 firebaseAuth.signInWithEmailAndPassword(userid,userpw)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Intent intent = new Intent(MainActivity.this, BottomNaviActivity.class);
                                    startActivity(intent);

                                }else{
                                    Toast.makeText(MainActivity.this,"로그인 오류",Toast.LENGTH_SHORT).show();
                                }
                 */

            }
        });



    }








}
