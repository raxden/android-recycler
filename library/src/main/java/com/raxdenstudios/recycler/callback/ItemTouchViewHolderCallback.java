package com.raxdenstudios.recycler.callback;

import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Created by agomez on 26/11/2015.
 */
public interface ItemTouchViewHolderCallback {

    void onItemSelected();

    boolean onItemLongSelected();

    /**
     * Called when the {@link ItemTouchHelper} first registers an item as being swiped.
     * Implementations should update the item view to indicate it's active state.
     */
    void onItemSwiped();

    /**
     * Called when the {@link ItemTouchHelper} first registers an item as being moved.
     * Implementations should update the item view to indicate it's active state.
     */
    void onItemMoved();

    /**
     * Called when the {@link ItemTouchHelper} has completed the move or swipe, and the active item
     * state should be cleared.
     */
    void onItemCleared();

}
