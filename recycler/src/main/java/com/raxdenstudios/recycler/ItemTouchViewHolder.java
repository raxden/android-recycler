package com.raxdenstudios.recycler;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.raxdenstudios.recycler.callback.ItemTouchViewHolderCallback;

/**
 * Created by agomez on 26/11/2015.
 */
public class ItemTouchViewHolder extends RecyclerView.ViewHolder implements ItemTouchViewHolderCallback {

    public ItemTouchViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public boolean onItemLongSelected() {
        return true;
    }

    @Override
    public void onItemSwiped() {

    }

    @Override
    public void onItemMoved() {

    }

    @Override
    public void onItemCleared() {

    }

}
