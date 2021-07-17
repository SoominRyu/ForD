package com.example.blackice;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kakao.kakaonavi.Destination;
import com.kakao.kakaonavi.KakaoNaviParams;
import com.kakao.kakaonavi.KakaoNaviService;
import com.kakao.kakaonavi.Location;
import com.kakao.kakaonavi.NaviOptions;
import com.kakao.kakaonavi.options.CoordType;
import com.kakao.kakaonavi.options.RpOption;
import com.kakao.kakaonavi.options.VehicleType;

import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;


public class NaviActivity extends AppCompatActivity {private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getAppKeyHash();

        onNaviButtonClicked();
    }
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (Exception e) {
// TODO Auto-generated catch block
            Log.e("name not found", e.toString());
        }
    }


    private void onNaviButtonClicked() {
        position = 4;

        if (position == -1) {
            Toast.makeText(getApplicationContext(), "실행하고 싶은 목적지 공유 / 길 안내를 선택하세요.", Toast.LENGTH_LONG).show();
            return;
        }
        Location kakao = Location.newBuilder("카카오 판교 오피스", 321256, 533732).build();
        KakaoNaviParams params;
        switch (position) {
            case 1:
                params = KakaoNaviParams.newBuilder(kakao).build();
                KakaoNaviService.getInstance().shareDestination(this, params);
                break;
            case 2:
                kakao = Destination.newBuilder("카카오 판교 오피스", 127.10821222694533, 37.40205604363057).build();
                params = KakaoNaviParams.newBuilder(kakao).setNaviOptions(NaviOptions.newBuilder().setCoordType(CoordType.WGS84).build()).build();
                KakaoNaviService.getInstance().shareDestination(this, params);
                break;
            case 4:
                kakao = Location.newBuilder("카카오 판교 오피스", 321256, 533732).build();
                params = KakaoNaviParams.newBuilder(kakao).setNaviOptions(new NaviOptions.Builder().build()).build();
                KakaoNaviService.getInstance().navigate(this, params);
                break;
            case 5:
                kakao = Destination.newBuilder("카카오 판교 오피스", 127.10821222694533, 37.40205604363057).build();
                Location stop = Location.newBuilder("서서울호수공원", 126.8322289016308, 37.528495607451205).build();
                List<Location> stops = new LinkedList<Location>();
                stops.add(stop);
                params = KakaoNaviParams.newBuilder(kakao).setNaviOptions(NaviOptions.newBuilder().setCoordType(CoordType.WGS84).build()).setViaList(stops).build();
                KakaoNaviService.getInstance().navigate(this, params);
                break;
            case 6:
                params = KakaoNaviParams.newBuilder(kakao).setNaviOptions(NaviOptions.newBuilder().setRouteInfo(true).build()).build();
                KakaoNaviService.getInstance().navigate(this, params);
                break;
            default:
                break;
        }
    }
}