package com.erdioran.efectura;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.erdioran.efectura.Adapter.RecyclerListAdapter;
import com.erdioran.efectura.Adapter.SimpleItemTouchHelperCallback;
import com.erdioran.efectura.Interfaces.ItemTouchHelperAdapter;
import com.erdioran.efectura.Interfaces.OnStartDragListener;
import com.erdioran.efectura.Model.Item;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LocationListener, OnStartDragListener {
    private RecyclerView recyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerListAdapter adapter;
    private List<Item> mList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = MainActivity.class.getSimpleName();
    private FloatingActionButton fab;


    private LocationManager locationManager;
    private GoogleApiClient mGoogleApiClient;
    private String appVer, longitude, latitude, provider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);


        fabButton();
        data();
        recyclerView();
        latLog();


        appVer = (BuildConfig.APPLICATION_ID + " | v" + BuildConfig.VERSION_NAME);
        Log.d("zzzzz", appVer);

    }

    private void recyclerView() {
        mList = new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new RecyclerListAdapter(mList, (OnStartDragListener) this);
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);
            }
        });


        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        data();
                    }
                }, 500);
            }
        });
    }

    private void data() {
        JsonArrayRequest request = new JsonArrayRequest(Utils.API,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "Couldn't fetch the menu! Pleas try again.", Toast.LENGTH_LONG).show();
                            return;

                        }

                        List<Item> items = new Gson().fromJson(response.toString(), new TypeToken<List<Item>>() {
                        }.getType());

                        mList.clear();
                        mList.addAll(items);

                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);
    }


    public void fabButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent email = new Intent(Intent.ACTION_SEND);
                email.setType("text/email");
                email.putExtra(Intent.EXTRA_EMAIL, "erdioran@gmail.com");
                email.putExtra(Intent.EXTRA_EMAIL, "");
                email.putExtra(Intent.EXTRA_TEXT, appVer);
                startActivity(Intent.createChooser(email, "Send info"));
            }
        });
    }

    public void latLog() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setCostAllowed(false);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setSpeedRequired(false);
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location != null) {
            onLocationChanged(location);
        } else {
            Log.d("xxxxx", "bulunamadı");
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        double lat = location.getLatitude();
        double lon = location.getLongitude();
        Log.d("aaaaa", "aaa");
        Log.d("abbbbaaaa", String.valueOf(lon));
        /* textenlem.setText(String.valueOf(log));*/
        /*     String latlon=latlongdetail(lat,lon);*/

    }

/*    public String latlongdetail(Double lat2,Double lon2){
List<Adress> adress=
    }*/

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        Toast.makeText(this, "Enable Provider", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Toast.makeText(this, "Disable Provider", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(provider, 100, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(this);
    }

    @Override
    public void onStartDrag(RecyclerView.ViewHolder viewHolder) {
        mItemTouchHelper.startDrag(viewHolder);
    }


    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actions_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                sendMail();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    public void sendMail() {
        Intent intent = new Intent(getApplicationContext(), FloatingActionButtonActivity.class);
        startActivity(intent);
    }*/
}
