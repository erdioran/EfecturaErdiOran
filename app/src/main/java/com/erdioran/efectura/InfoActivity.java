package com.erdioran.efectura;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;

public class InfoActivity extends AppCompatActivity {
    TextView textViewInfo;
    private String jsonResponse;
    private static int SPLASH_TIME_OUT = 1500;
    private static final String TAG = InfoActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        check();
        infoData();
        textViewInfo=findViewById(R.id.textViewInfo);
    }



    public void infoData() {


        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Utils.API2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response == null) {
                    Toast.makeText(getApplicationContext(), "data null", Toast.LENGTH_LONG).show();
                    return;

                }
                try {

                    String Cur_ID1 = String.valueOf(response.getInt("Cur_ID"));
                    String Date1 = response.getString("Date");
                    String Cur_Abbreviation1 = response.getString("Cur_Abbreviation");
                    String Cur_Scale1 = String.valueOf(response.getInt("Cur_Scale"));
                    String Cur_Name1 = response.getString("Cur_Name");
                    String Cur_OfficialRate1 = String.valueOf(response.getInt("Cur_OfficialRate"));

                    Log.d("eeeeasad", Cur_ID1);
                    jsonResponse = "";
                    jsonResponse += "Cur_ID: " + Cur_ID1 + "\n\n";
                    jsonResponse += "Date: " + Date1 + "\n\n";
                    jsonResponse += "Cur_Abbreviation: " + Cur_Abbreviation1 + "\n\n";
                    jsonResponse += "Cur_Scale: " + Cur_Scale1 + "\n\n";
                    jsonResponse += "Cur_Name: " + Cur_Name1 + "\n\n";
                    jsonResponse += "Cur_OfficialRate: " + Cur_OfficialRate1 + "\n\n";


                    textViewInfo.setText(jsonResponse);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

        MyApplication.getInstance().addToRequestQueue(jsonObjectRequest);
    }


    @SuppressLint("WrongConstant")
    public void check() {
        Timer t = new Timer();
        boolean checkConnection = new NetworkCheck().checkConnection(this);
        if (checkConnection) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    check();
                }
            }, 4000);
        } else {
            Toast.makeText(InfoActivity.this,
                    "Connection Not Found", SPLASH_TIME_OUT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    check();
                }
            }, 2000);
        }
    }

}
