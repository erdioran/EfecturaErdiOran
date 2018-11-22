package com.erdioran.efectura.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erdioran.efectura.Interfaces.ItemTouchHelperAdapter;
import com.erdioran.efectura.Interfaces.ItemTouchHelperViewHolder;
import com.erdioran.efectura.Interfaces.OnStartDragListener;
import com.erdioran.efectura.Model.Item;


import java.util.Collections;
import java.util.List;

public class RecyclerListAdapter extends RecyclerView.Adapter<RecyclerListAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {
    private List<Item> mList;

    private final OnStartDragListener mDragStartListener;

    public RecyclerListAdapter(List<Item> mList,OnStartDragListener dragStartListener) {

        this.mList=mList;
        mDragStartListener = dragStartListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(final ItemViewHolder holder, int position) {
        Item item = mList.get(position);
        holder.setIsRecyclable(true);
        if (position % 2 == 0)
        {
            holder.viewForeground.setBackgroundColor(R.color);
        }else{
            holder.viewForeground.setBackgroundColor(000000);
        }
        holder.Cur_ID.setText(String.valueOf(item.getCur_ID()));
        holder.Cur_ParentID.setText(String.valueOf(item.getCur_ParentID()));
        holder.Cur_Code.setText(item.getCur_Code());
        holder.Cur_Abbreviation.setText(item.getCur_Abbreviation());
        holder.Cur_Name.setText(item.getCur_Name());
        holder.Cur_Name_Bel.setText(item.getCur_Name_Bel());
        holder.Cur_Name_Eng.setText(item.getCur_Name_Eng());
        holder.Cur_QuotName.setText(item.getCur_QuotName());
        holder.Cur_QuotName_Bel.setText(item.getCur_QuotName_Bel());
        holder.Cur_QuotName_Eng.setText(item.getCur_QuotName_Eng());
        holder.Cur_NameMulti.setText(item.getCur_NameMulti());
        holder.Cur_Name_BelMulti.setText(item.getCur_Name_BelMulti());
        holder.Cur_Name_EngMulti.setText(item.getCur_Name_EngMulti());
        holder.Cur_Scale.setText(String.valueOf(item.getCur_Scale()));
        holder.Cur_Periodicity.setText(String.valueOf(item.getCur_Periodicity()));
        holder.Cur_DateStart.setText(String.valueOf(item.getCur_DateStart()));
        holder.Cur_DateEnd.setText(String.valueOf(item.getCur_DateEnd()));




        // Start a drag whenever the handle view it touched
        holder.handleView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mDragStartListener.onStartDrag(holder);
                }
                return false;
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mList, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        if (mList == null) {
            return 0;
        } else {
            return mList.size();
        }
//        return mItems.size();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        public RelativeLayout viewForeground, viewBackground;
        TextView Cur_ID, Cur_Name,Cur_Scale,Cur_DateEnd,Cur_DateStart,Cur_Periodicity,Cur_Abbreviation,Cur_NameMulti,Cur_Name_EngMulti,Cur_Name_BelMulti,Cur_QuotName_Bel,Cur_QuotName_Eng, Cur_Code, Cur_ParentID,Cur_Name_Bel,Cur_Name_Eng,Cur_QuotName;
        public final ImageView handleView;

        public ItemViewHolder(View itemView) {
            super(itemView);
            Cur_ID = (TextView) itemView.findViewById(R.id.textview1);
            Cur_ParentID = (TextView) itemView.findViewById(R.id.textview2);
            Cur_Code = (TextView) itemView.findViewById(R.id.textview3);
            Cur_Abbreviation = (TextView) itemView.findViewById(R.id.textview4);
            Cur_Name = (TextView) itemView.findViewById(R.id.textview12);
            Cur_Name_Bel = (TextView) itemView.findViewById(R.id.textview13);
            Cur_Name_Eng = (TextView) itemView.findViewById(R.id.textview14);
            Cur_QuotName = (TextView) itemView.findViewById(R.id.textview9);
            Cur_QuotName_Bel = (TextView) itemView.findViewById(R.id.textview10);
            Cur_QuotName_Eng = (TextView) itemView.findViewById(R.id.textview11);
            Cur_NameMulti = (TextView) itemView.findViewById(R.id.textview15);
            Cur_Name_BelMulti = (TextView) itemView.findViewById(R.id.textview16);
            Cur_Name_EngMulti = (TextView) itemView.findViewById(R.id.textview17);
            Cur_Scale = (TextView) itemView.findViewById(R.id.textview5);
            Cur_Periodicity = (TextView) itemView.findViewById(R.id.textview6);
            Cur_DateStart = (TextView) itemView.findViewById(R.id.textview7);
            Cur_DateEnd = (TextView) itemView.findViewById(R.id.textview8);

            viewForeground = itemView.findViewById(R.id.view_foreground);
            viewBackground = itemView.findViewById(R.id.view_background);
            handleView = (ImageView) itemView.findViewById(R.id.handle);
        }

        @Override
        public void onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY);
        }

        @Override
        public void onItemClear() {
            itemView.setBackgroundColor(0);
        }
    }
}
