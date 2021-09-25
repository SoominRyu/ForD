package com.example.blackice;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.blackice.NaviActivity2;
import com.example.blackice.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatActivity;


public class NaviActivity2 extends AppCompatActivity {
    private static final int ACTION_MANAGE_OVERLAY_PERMISSION_REQUEST_CODE = 1;

    private WebView webView;
    private String url = "https://kakaonavi-wguide.kakao.com/navigate.html?appkey=63c531d5cfaf5dd48096ae1e9835eede&apiver=1.0&extras=%7B%22KA%22%3A%22sdk%2F1.40.11%20os%2Fjavascript%20sdk_type%2Fjavascript%20lang%2Fen-US%20device%2FLinux_i686%20origin%2Fhttps%253A%252F%252Fhi032-blackice.web.app%22%7D&param=%7B%22destination%22%3A%7B%22name%22%3A%22(%EC%A3%BC)%EC%97%90%EC%8A%A4%EC%95%84%EC%9D%B4%EC%95%8C%EC%86%8C%ED%94%84%ED%8A%B8%22%2C%22x%22%3A127.032423%2C%22y%22%3A37.490928%7D%2C%22option%22%3A%7B%22coord_type%22%3A%22wgs84%22%2C%22vehicle_type%22%3A1%2C%22rpoption%22%3A100%2C%22route_info%22%3Afalse%7D%7D";
    private String userid = "";
    Animation anim;
    // DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
    // DatabaseReference conditionRef = mRootRef.child("SensorData");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi2);

        //setContentView(R.layout.activity_overdraw);

        // Intent intent = getIntent(); /*데이터 수신*/

        //userid = intent.getExtras().getString("userid"); /*String형*/
        webView = (WebView) findViewById(R.id.webView);
        WebSettings settings = webView.getSettings();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl(url);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClientClass());
        webView.getSettings().setDomStorageEnabled(true);


        webView.setWebChromeClient(new WebChromeClient(){

            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                callback.invoke(origin, true, false);
            }
        });

        RelativeLayout layout3 = (RelativeLayout) findViewById(R.id.warning3);
        RelativeLayout layout2 = (RelativeLayout) findViewById(R.id.warning2);
        RelativeLayout layout1 = (RelativeLayout) findViewById(R.id.warning1);


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("SensorData");
        reference.child("Sensor3").child("Temperature_bool").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Boolean val = dataSnapshot.getValue(Boolean.class);

                //System.out.println("test: "+val);

                if(val)
                {
                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("SensorData");
                    reference1.child("Sensor1").child("Distance_bool").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Boolean temp = dataSnapshot.getValue(Boolean.class);

                            System.out.println("test1: "+temp);

                            if(temp)
                            {
                                //warning3.visibility = View.VISIBLE
                                layout1.setVisibility(View.VISIBLE);
                                layout1.startAnimation(anim);
                            }
                            else
                            {
                                layout1.setVisibility(View.GONE);
                                layout1.clearAnimation();

                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException();
                        }
                    });
                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("SensorData");
                    reference2.child("Sensor2").child("Distance_bool").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Boolean temp = dataSnapshot.getValue(Boolean.class);

                            System.out.println("test2: "+temp);

                            if(temp)
                            {
                                //warning3.visibility = View.VISIBLE
                                layout1.setVisibility(View.GONE);
                                layout1.clearAnimation();

                                layout2.setVisibility(View.VISIBLE);
                                layout2.startAnimation(anim);
                            }
                            else
                            {
                                layout2.setVisibility(View.GONE);
                                layout2.clearAnimation();

                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException();
                        }
                    });


                    DatabaseReference reference3 = FirebaseDatabase.getInstance().getReference("SensorData");
                    reference3.child("Sensor3").child("Distance_bool").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Boolean temp = dataSnapshot.getValue(Boolean.class);

                            System.out.println("test3: "+temp);

                            if(temp)
                            {
                                //warning3.visibility = View.VISIBLE
                                layout2.setVisibility(View.GONE);
                                layout2.clearAnimation();

                                layout3.setVisibility(View.VISIBLE);
                                layout3.startAnimation(anim);
                            }
                            else
                            {
                                layout3.setVisibility(View.GONE);
                                layout3.clearAnimation();

                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            throw databaseError.toException();
                        }
                    });
                }
                else
                {
                    layout3.setVisibility(View.GONE);
                    layout3.clearAnimation();
                    layout2.setVisibility(View.GONE);
                    layout2.clearAnimation();
                    layout1.setVisibility(View.GONE);
                    layout1.clearAnimation();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                throw databaseError.toException();
            }
        });

        anim = new AlphaAnimation(0.0f,1.0f);
        anim.setDuration(200);
        anim.setStartOffset(20);
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
            webView.goBack();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            if (url.startsWith("intent:")) {
                try {
                    Intent intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                    Intent existPackage = getPackageManager().getLaunchIntentForPackage(intent.getPackage());
                    if (existPackage != null) {
                        startActivity(intent);
                    } else {
                        Intent marketIntent = new Intent(Intent.ACTION_VIEW);
                        marketIntent.setData(Uri.parse("market://details?id=" + intent.getPackage()));
                        startActivity(marketIntent);
                    }
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                view.loadUrl(url);
            }
            return true;


        }

    }

}