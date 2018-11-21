package com.erdioran.efectura;


import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.erdioran.efectura.Adapter.MyAdapterRecyclerView;
import com.erdioran.efectura.Adapter.MyItemTouchHelperCallback;
import com.erdioran.efectura.Interfaces.CallbackItemTouch;
import com.erdioran.efectura.Model.Item;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallbackItemTouch {
    private RecyclerView mRecyclerView;
    private MyAdapterRecyclerView myAdapterRecyclerView;
    private List<Item> mList;
    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = MainActivity.class.getSimpleName();

    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    private Menu menu;
    private FloatingActionButton fab;
    private String appVer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        fab = findViewById(R.id.fab);

        fabButton();
        data();
        recyclerView();

        appVer=(BuildConfig.APPLICATION_ID+" | v" + BuildConfig.VERSION_NAME);
        Log.d("zzzzz",appVer);




//        btnSendMail.setOnClickListener(new View.OnClickListener()
//        {
//            public void onClick(View v)
//            {
//                String phoneNo = txtMail.getText().toString();
//
//                displayLocation();
//                if (phoneNo.length()>0 && message.length()>0)
//                    sendEmail(phoneNo, message);
//                else
//                    Toast.makeText(getBaseContext(),
//                            "Please enter both phone number and message.",
//                            Toast.LENGTH_SHORT).show();
//            }
//        });


    }

    private void recyclerView(){
        mList = new ArrayList<>();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager in the RecyclerView
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        myAdapterRecyclerView = new MyAdapterRecyclerView(mList); // Create Instance of MyAdapterRecyclerView
        mRecyclerView.setAdapter(myAdapterRecyclerView); // Set Adapter for RecyclerView
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback((CallbackItemTouch) this);// create MyItemTouchHelperCallback
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback); // Create ItemTouchHelper and pass with parameter the MyItemTouchHelperCallback
        touchHelper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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

                        myAdapterRecyclerView.notifyDataSetChanged();
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

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        mList.add(newPosition, mList.remove(oldPosition));
        myAdapterRecyclerView.notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        myAdapterRecyclerView.notifyItemRemoved(position);
    }

    public void fabButton() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, FloatingActionButton.class);
                startActivity(intent);
            }
        });
    }


    @Override
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
    }
}
