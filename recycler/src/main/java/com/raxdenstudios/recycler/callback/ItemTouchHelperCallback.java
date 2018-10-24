package com.raxdenstudios.recycler.callback;

import android.graphics.Canvas;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by agomez on 26/11/2015.
 */
public class ItemTouchHelperCallback extends ItemTouchHelper.Callback {

    private static final float ALPHA_FULL = 1.0f;

    private final ItemTouchHelperAdapter mAdapter;
    private final boolean mLongPressDragEnabled;
    private final boolean mItemViewSwipeEnabled;

    public ItemTouchHelperCallback(ItemTouchHelperAdapter adapter) {
        this(adapter, false, false);
    }

    public ItemTouchHelperCallback(ItemTouchHelperAdapter adapter, boolean itemViewSwipeEnabled, boolean longPressDragEnabled) {
        mAdapter = adapter;
        mItemViewSwipeEnabled = itemViewSwipeEnabled;
        mLongPressDragEnabled = longPressDragEnabled;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return mLongPressDragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return mItemViewSwipeEnabled;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // Set movement flags based on the layout manager
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
            final int swipeFlags = 0;
            return makeMovementFlags(dragFlags, swipeFlags);
        } else {
            final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            final int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        if (viewHolder.getItemViewType() == target.getItemViewType()) {
            return mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        } else {
            return false;
        }
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            // Fade out the view as it is swiped out of the parent's bounds
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                viewHolder.itemView.setAlpha(alpha);
                viewHolder.itemView.setTranslationX(dX);
            }
        } else {
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item to change
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                if (viewHolder instanceof ItemTouchViewHolderCallback) {
                    // Let the view holder know that this item is being moved
                    ((ItemTouchViewHolderCallback) viewHolder).onItemSwiped();
                }
            } else if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                if (viewHolder instanceof ItemTouchViewHolderCallback) {
                    // Let the view holder know that this item is being dragged
                    ((ItemTouchViewHolderCallback) viewHolder).onItemMoved();
                }
            }
        }

        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            viewHolder.itemView.setAlpha(ALPHA_FULL);
        }

        if (viewHolder instanceof ItemTouchViewHolderCallback) {
            // Tell the view holder it's time to restore the idle state
            ((ItemTouchViewHolderCallback)viewHolder).onItemCleared();
        }
    }

    public interface ItemTouchHelperAdapter {

        /**
         * Called when an item has been dragged far enough to trigger a move. This is called every time
         * an item is shifted, and <strong>not</strong> at the end of a "drop" event.<br/>
         * <br/>
         * Implementations should call {@link RecyclerView.Adapter#notifyItemMoved(int, int)} after
         * adjusting the underlying data to reflect this move.
         *
         * @param fromPosition The start position of the moved item.
         * @param toPosition   Then resolved position of the moved item.
         * @return True if the item was moved to the new adapter position.
         *
         * @see RecyclerView#getAdapterPositionFor(RecyclerView.ViewHolder)
         * @see RecyclerView.ViewHolder#getAdapterPosition()
         */
        boolean onItemMove(int fromPosition, int toPosition);


        /**
         * Called when an item has been dismissed by a swipe.<br/>
         * <br/>
         * Implementations should call {@link RecyclerView.Adapter#notifyItemRemoved(int)} after
         * adjusting the underlying data to reflect this removal.
         *
         * @param position The position of the item dismissed.
         *
         * @see RecyclerView#getAdapterPositionFor(RecyclerView.ViewHolder)
         * @see RecyclerView.ViewHolder#getAdapterPosition()
         */
        void onItemDismiss(int position);

    }

}
