package com.erdioran.efectura;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.erdioran.efectura.Adapter.RecyclerListAdapter;
import com.erdioran.efectura.Adapter.SimpleItemTouchHelperCallback;
import com.erdioran.efectura.Interfaces.OnStartDragListener;
import com.erdioran.efectura.Model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements OnStartDragListener, LocationListener {
    private RecyclerView recyclerView;
    private ItemTouchHelper mItemTouchHelper;
    private RecyclerListAdapter adapter;
    private List<Item> mList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = MainActivity.class.getSimpleName();

    private FloatingActionButton fab, fabUp;

    private FrameLayout frameLayout;


    private String appVer;
    private double latitude;
    private double longitude;


    private Location location;
    LocationListener locationListener;
    LocationManager locationManager;
    Button btnShowLocation;
    Context context = this;

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        fabUpButton();
        fabButton();
        data();
        recyclerView();

        fabUp.hide();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0 && fabUp.getVisibility() == View.VISIBLE) {
                    fabUp.hide();
                } else if (dy < 0 && fabUp.getVisibility() != View.VISIBLE) {
                    fabUp.show();
                }
            }
        });


//        swipeRefreshLayout.setDistanceToTriggerSync(Integer.MAX_VALUE);
        swipeRefreshLayout.setEnabled(false);


      /*  locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);*/


    }

    public void init() {
        appVer = (BuildConfig.APPLICATION_ID + " | v" + BuildConfig.VERSION_NAME);
        recyclerView = findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);
        fabUp = findViewById(R.id.upFab);
        frameLayout = findViewById(R.id.frameLayout);
        swipeRefreshLayout = findViewById(R.id.swipe_container);
    }


    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        Log.d("LatLong", String.valueOf(latitude) + String.valueOf(longitude));
        locationManager.removeUpdates(this);
        Log.d("LatLong", String.valueOf(latitude) + String.valueOf(longitude));

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

                data();

//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        swipeRefreshLayout.setRefreshing(false);
//                        data();
//                    }
//                }, 500);
            }
        });
    }

    private void data() {
        JsonArrayRequest request = new JsonArrayRequest(Utils.API,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response == null) {
                            Toast.makeText(getApplicationContext(), "data null", Toast.LENGTH_LONG).show();
                            return;

                        }

                        List<Item> items = new Gson().fromJson(response.toString(), new TypeToken<List<Item>>() {
                        }.getType());

                        mList.clear();
                        mList.addAll(items);

                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
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

                try {

                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("text/email");
                    email.putExtra(Intent.EXTRA_EMAIL, "");
                    email.putExtra(Intent.EXTRA_TEXT, appVer + "\nLatitude: " + String.valueOf(latitude) + " \nLongitude: " + String.valueOf(longitude));
                    startActivity(Intent.createChooser(email, "Send info"));
                } catch (Exception e) {
                    System.out.println("Error" + e.getMessage());
                }
            }
        });
    }

    public void fabUpButton() {
        fabUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                layoutManager.scrollToPositionWithOffset(0, 0);
            }
        });
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
