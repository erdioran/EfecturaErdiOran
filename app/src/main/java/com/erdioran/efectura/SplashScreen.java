package com.erdioran.efectura;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;


public class SplashScreen extends AppCompatActivity {
    ConnectivityManager connectivityManager;
    NetworkInfo info;
    LocationManager locationManager;
    LocationListener locationListener;
    ImageView imageView;
    private static int SPLASH_TIME_OUT = 1500;


    @SuppressLint("WrongConstant")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        imageView=(ImageView) findViewById(R.id.startLogo);

        Picasso.with(this).load(R.drawable.logo).placeholder(R.drawable.logo).resize(200,200).into(imageView);
        ActionBar actionBar=getSupportActionBar();
        actionBar.hide();
        check();



    }



    @SuppressLint("WrongConstant")
    public void check() {
        Timer t = new Timer();
        boolean checkConnection = new NetworkCheck().checkConnection(this);
        if (checkConnection) {
            t.schedule(new splash(), 3000);
        } else {
            Toast.makeText(SplashScreen.this,
                    "Connection Not Found", SPLASH_TIME_OUT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    check();
                }
            }, 2000);
        }
    }

    class splash extends TimerTask {

        @Override
        public void run() {
            Intent i = new Intent(SplashScreen.this, MainActivity.class);
            finish();
            startActivity(i);
        }
    }

}
