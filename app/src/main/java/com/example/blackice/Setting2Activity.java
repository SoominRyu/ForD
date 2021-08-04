package com.example.blackice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class Setting2Activity extends AppCompatActivity {
    private String userid = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms);

        Intent intent = getIntent(); /*데이터 수신*/
        userid = intent.getExtras().getString("userid"); /*String형*/

        Button back = (Button) findViewById(R.id.back);
        Button next1 = (Button) findViewById(R.id.next1);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingTerm = new Intent(getApplicationContext(), Setting1Activity.class);
                settingTerm.putExtra("userid",userid);
                startActivity(settingTerm);
            }
        });

        next1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingTerm = new Intent(getApplicationContext(), Setting3Activity.class);
                settingTerm.putExtra("userid",userid);
                startActivity(settingTerm);
            }
        });
    }
}