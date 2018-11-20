package com.erdioran.efectura;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.erdioran.efectura.Adapter.MyAdapterRecyclerView;
import com.erdioran.efectura.Adapter.MyItemTouchHelperCallback;
import com.erdioran.efectura.Interfaces.CallbackItemTouch;
import com.erdioran.efectura.Model.Item;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CallbackItemTouch {
    private RecyclerView mRecyclerView; // RecyclerVIew
    private MyAdapterRecyclerView myAdapterRecyclerView; //The Adapter for RecyclerVIew
    private List<Item> mList; // My List the object 'Item'.
    private SwipeRefreshLayout swipeRefreshLayout;

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        initList(); //call method

        swipeRefreshLayout = findViewById(R.id.swipe_container);
        swipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mList.clear();
                initList();
            }
        });




    }

    private void initList() {
        // Adds data to List of Objects Item
        mList = new ArrayList<>();

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this)); // Set LayoutManager in the RecyclerView
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(this));
        myAdapterRecyclerView = new MyAdapterRecyclerView(mList); // Create Instance of MyAdapterRecyclerView
        mRecyclerView.setAdapter(myAdapterRecyclerView); // Set Adapter for RecyclerView
        ItemTouchHelper.Callback callback = new MyItemTouchHelperCallback((CallbackItemTouch) this);// create MyItemTouchHelperCallback
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback); // Create ItemTouchHelper and pass with parameter the MyItemTouchHelperCallback
        touchHelper.attachToRecyclerView(mRecyclerView); // Attach ItemTouchHelper to RecyclerView
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

                        // adding items to cart list
                        mList.clear();
                        mList.addAll(items);

                        // refreshing recycler view
                        myAdapterRecyclerView.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // error in getting json
                Log.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        MyApplication.getInstance().addToRequestQueue(request);
    }

    @Override
    public void itemTouchOnMove(int oldPosition, int newPosition) {
        mList.add(newPosition, mList.remove(oldPosition));// change position
        myAdapterRecyclerView.notifyItemMoved(oldPosition, newPosition); //notifies changes in adapter, in this case use the notifyItemMoved
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        myAdapterRecyclerView.notifyItemRemoved(position);
    }


}
