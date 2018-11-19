package com.erdioran.efectura.Adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erdioran.efectura.Interfaces.ItemTouchHelperViewHolder;
import com.erdioran.efectura.Model.Item;
import com.erdioran.efectura.R;

import java.util.List;

public class MyAdapterRecyclerView  extends RecyclerView.Adapter<MyAdapterRecyclerView.MyViewHolder>{

    private List<Item> mList;



    public MyAdapterRecyclerView(List<Item> mList) {
        this.mList = mList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Item item = mList.get(position);
        holder.setCur_Name(item.getCur_Name());
        holder.setCur_ParentID(item.getCur_ParentID());

    }

    @Override
    public int getItemCount() {
        if (mList == null){
            return 0;
        }else {
            return mList.size();
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements ItemTouchHelperViewHolder{

        public RelativeLayout viewForeground,viewBackground;
        TextView Cur_Name, Cur_ParentID;
        public MyViewHolder(View itemView) {
            super(itemView);

            Cur_Name          = (TextView)itemView.findViewById(R.id.cur_name1);
            Cur_ParentID   = (TextView)itemView.findViewById(R.id.cur_name2);
            viewForeground = itemView.findViewById(R.id.view_foreground);
            viewBackground = itemView.findViewById(R.id.view_background);
        }

        public void setCur_Name(String cur_Name){
            Cur_Name.setText(cur_Name);
        }

        public void setCur_ParentID(int cur_parentID){
            Cur_ParentID.setText(String.valueOf(cur_parentID));
        }

        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        public void onItemClear() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }



    }
    public void removeItem(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    public void restoreItem(Item item, int position) {
        mList.add(position, item);
        notifyItemInserted(position);
    }



}
