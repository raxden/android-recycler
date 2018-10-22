package com.raxdenstudios.recycler;

import android.content.Context;
import android.view.View;

import com.raxdenstudios.recycler.callback.ItemTouchHelperCallback;

import java.util.List;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Created by agomez on 26/11/2015.
 */
public abstract class RecyclerItemTouchAdapter<T, VH extends ItemTouchViewHolder> extends RecyclerAdapter<T, VH> implements ItemTouchHelperCallback.ItemTouchHelperAdapter {

    protected ItemTouchHelper mItemTouchHelper;

    public RecyclerItemTouchAdapter(Context context, int resource) {
        super(context, resource, null);
    }

    public RecyclerItemTouchAdapter(Context context, int resource, List<T> data) {
        super(context, resource, data);
    }

    public boolean isLongPressDragEnabled() {
        return true;
    }

    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        ItemTouchHelper.Callback callback = new ItemTouchHelperCallback(this, isItemViewSwipeEnabled(), isLongPressDragEnabled());
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);
    }

    @Override
    public void onBindViewHolder(final VH viewHolder, int position) {
        super.onBindViewHolder(viewHolder, position);

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
