package com.erdioran.efectura;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.erdioran.efectura.Adapter.RecyclerListAdapter;
import com.erdioran.efectura.Adapter.SimpleItemTouchHelperCallback;
import com.erdioran.efectura.Interfaces.OnStartDragListener;
import com.erdioran.efectura.Model.Item;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class MainActivity extends AppCompatActivity implements OnStartDragListener {
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
    Context context = this;
    private static int SPLASH_TIME_OUT = 1500;



    Item item;
    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        check();
        init();
        fabUpButton();
        fabButton();
        data();
        recyclerView();
        fabUp.hide();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data();

          /*      new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        data();
                    }
                }, 500);*/
            }
        });


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


        swipeRefreshLayout.setEnabled(false);


//        swipeRefreshLayout.setDistanceToTriggerSync(Integer.MAX_VALUE);


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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                Intent intent = new Intent(this, InfoActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void recyclerView() {

        mList = new ArrayList<>();
        recyclerView.setHasFixedSize(true);
        adapter = new RecyclerListAdapter(mList, (OnStartDragListener) this);

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setNestedScrollingEnabled(false);
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


    }


    private void data() {
        JsonArrayRequest request = new JsonArrayRequest(Utils.API,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        try {


                            if (response == null) {
                                Toast.makeText(getApplicationContext(), "data null", Toast.LENGTH_LONG).show();
                                return;

                            }

                            List<Item> items = new Gson().fromJson(response.toString(), new TypeToken<List<Item>>() {
                            }.getType());

                            mList.clear();
                            mList.addAll(items);

                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {

                        }


//                        swipeRefreshLayout.setRefreshing(false);
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
            Toast.makeText(MainActivity.this,
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
