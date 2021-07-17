package com.example.blackice;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;


public class SignIn2Activity extends AppCompatActivity {
    private Button JoinBtn;
    private EditText UserID;
    private EditText UserPWD1;
    private EditText UserPWD2;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in2);

        UserID = (EditText)findViewById(R.id.userIdEdit);
        UserPWD1 = (EditText)findViewById(R.id.userPwEdit);
        UserPWD2 = (EditText)findViewById(R.id.userPWEdit2);
        JoinBtn = findViewById(R.id.JoinBtn);


        JoinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SignIn2Activity.this, SignIn3Activity.class);
                //  startActivity(intent);

                myRef.child("App").child("Users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                        Log.w("userid", UserID.getText().toString());
                        //String value = dataSnapshot.getValue(String.class);
                        String val=UserID.getText().toString();

                        if (!dataSnapshot.hasChild(val)) {
                            //Log.w("FireBaseData", "haschild");
                            // Toast.makeText(SignIn2Activity.this, "사용가능한 ID입니다.", Toast.LENGTH_SHORT).show();

                            if(UserPWD1.getText().toString().equals(UserPWD2.getText().toString()))
                            {
                                String pwd=UserPWD1.getText().toString();
                                myRef.child("App").child("Users").child(val).child("Userpwd").setValue(pwd);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(SignIn2Activity.this, "비밀번호가 불일치합니다.", Toast.LENGTH_SHORT).show();
                            }


                        } else {
                            Toast.makeText(SignIn2Activity.this, "중복된 ID입니다..", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value

                    }
                });
            }
        });

    }
}