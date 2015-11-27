package com.raxdenstudios.recycler;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;

import com.raxdenstudios.recycler.callback.ItemTouchHelperCallback;

import java.util.List;

/**
 * Created by agomez on 26/11/2015.
 */
public abstract class RecyclerItemTouchAdapter<T, VH extends ItemTouchViewHolder> extends RecyclerAdapter<T, VH> implements ItemTouchHelperCallback.ItemTouchHelperAdapter {

    private ItemTouchHelper mItemTouchHelper;

    public RecyclerItemTouchAdapter(Context context, int resource) {
        super(context, resource, null);
    }

    public RecyclerItemTouchAdapter(Context context, int resource, List<T> data) {
        super(context, resource, data);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(this);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final VH viewHolder, int position) {
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.onItemSelected();
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return viewHolder.onItemLongSelected();
            }
        });
        viewHolder.itemView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                    mItemTouchHelper.startDrag(viewHolder);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        moveItem(fromPosition, toPosition);
        return true;
    }

    @Override
    public void onItemDismiss(int position) {
        removeItem(position);
    }

}
