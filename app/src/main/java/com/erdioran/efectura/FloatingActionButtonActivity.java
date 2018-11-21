package com.erdioran.efectura;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class FloatingActionButtonActivity extends AppCompatActivity {

    Button btnSendMail;
    EditText txtMail;
    Context context=this;
    String appVer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);


        appVer=(BuildConfig.APPLICATION_ID+" | v" + BuildConfig.VERSION_NAME);
        Log.d("zzzzz",appVer);

        btnSendMail = (Button) findViewById(R.id.btnSendEmail);
        txtMail = (EditText) findViewById(R.id.et_email);




//        final Uri uri=Uri.parse("mailto:"+email).buildUpon().appendQueryParameter("subject","efectura app").build();

        btnSendMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String[] email = {txtMail.getText().toString()};
                Intent intent = new Intent(Intent.ACTION_SEND,Uri.parse("mailto:"));
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL,email);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


    }

    protected void sendEmail() {

//        Intent intent = new Intent(Intent.ACTION_SEND,Uri.parse("mailto:"));
//        intent.setType("message/rfc822");
//        intent.putExtra(Intent.EXTRA_EMAIL,email);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
    }
}
