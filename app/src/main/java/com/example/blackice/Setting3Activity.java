package com.example.blackice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;



public class Setting3Activity extends AppCompatActivity {
    private String userid = "";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_info);

        Intent intent = getIntent(); /*데이터 수신*/
        userid = intent.getExtras().getString("userid"); /*String형*/

        Button back = (Button) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingTerm = new Intent(getApplicationContext(), Setting2Activity.class);
                settingTerm.putExtra("userid",userid);
                startActivity(settingTerm);
            }
        });
    }
}
