package com.erdioran.efectura.Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.erdioran.efectura.Interfaces.CallbackItemTouch;
import com.erdioran.efectura.Model.Item;

public class MyItemTouchHelperCallback extends ItemTouchHelper.Callback {

    CallbackItemTouch callbackItemTouch; // interface
    public static final float ALPHA_FULL = 1.0f;


    public MyItemTouchHelperCallback(CallbackItemTouch callbackItemTouch) {
        this.callbackItemTouch = callbackItemTouch;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT; // movements drag
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags); // as parameter, action drag and flags drag
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        callbackItemTouch.itemTouchOnMove(viewHolder.getAdapterPosition(), target.getAdapterPosition()); // information to the interface
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        callbackItemTouch.onItemDismiss(viewHolder.getAdapterPosition());


    }

    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {

        if (viewHolder != null) {
            final View foregroundView = ((MyAdapterRecyclerView.MyViewHolder) viewHolder).viewForeground;

            getDefaultUIUtil().onSelected(foregroundView);
        }
    }


//    @Override
//    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
//
//            View itemView = viewHolder.itemView;
//
//            Paint p = new Paint();
//
//            if (dX > 0) {
//
//                c.drawRect((float) itemView.getLeft(), (float) itemView.getTop(), dX,
//                        (float) itemView.getBottom(), p);
//            } else {
//                c.drawRect((float) itemView.getRight() + dX, (float) itemView.getTop(),
//                        (float) itemView.getRight(), (float) itemView.getBottom(), p);
//            }
//            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
//            viewHolder.itemView.setAlpha(alpha);
//            viewHolder.itemView.setTranslationX(dX);
//        } else {
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        }
//
//    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView,
                            RecyclerView.ViewHolder viewHolder, float dX, float dY,
                            int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((MyAdapterRecyclerView.MyViewHolder) viewHolder).viewForeground;

        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((MyAdapterRecyclerView.MyViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
                actionState, isCurrentlyActive);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((MyAdapterRecyclerView.MyViewHolder) viewHolder).viewForeground;
        getDefaultUIUtil().clearView(foregroundView);
    }


}
