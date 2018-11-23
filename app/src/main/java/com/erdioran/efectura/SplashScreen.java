package com.erdioran.efectura;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;



public class SplashScreen extends AppCompatActivity implements LocationListener {
//    InternetControl internetControl;
    LocationManager locationManager;
    int PERMISSION_ALL = 1;
    private static int SPLASH_TIME_OUT=2500;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        locationManager=(LocationManager) getSystemService(LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(SplashScreen.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale(SplashScreen.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(SplashScreen.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);
            }
        }






//        new Handler().postDelayed(new Runnable(){
//
//            @Override
//            public void run() {
//                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        },SPLASH_TIME_OUT);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_ALL) {
            Intent intent = new Intent(SplashScreen.this,MainActivity.class);
            startActivity(intent);
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }
}
