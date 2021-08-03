package com.example.blackice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Setting1Activity extends AppCompatActivity {
    private String userid = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        Intent intent = getIntent(); /*데이터 수신*/

        userid = intent.getExtras().getString("userid"); /*String형*/
        Button back = (Button) findViewById(R.id.back);
        Button next = (Button) findViewById(R.id.next);
//        Button btn1 = (Button) findViewById(R.id.switch1);
//        Button btn2 = (Button) findViewById(R.id.switch2);
//        Button btn3 = (Button) findViewById(R.id.switch3);


        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent settingTerm = new Intent(getApplicationContext(),MainActivity.class);
                intent.putExtra("userid",userid);
                startActivity(settingTerm);
            }
        });

        next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent settingTerm = new Intent(getApplicationContext(),Setting2Activity.class);
                intent.putExtra("userid",userid);
                startActivity(settingTerm);
            }
        });
    }
}