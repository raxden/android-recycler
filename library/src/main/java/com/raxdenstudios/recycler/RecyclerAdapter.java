package com.raxdenstudios.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by agomez on 26/11/2015.
 */
public abstract class RecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected final Context mContext;
    protected int mResource;
    protected List<T> mData;

    public RecyclerAdapter(Context context, int resource) {
       this(context, resource, null);
    }

    public RecyclerAdapter(Context context, int resource, List<T> data) {
        mContext = context;
        mResource = resource;
        mData = data != null ? data : new ArrayList<T>();
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        onBindViewItemHolder(holder, getItem(position), position);
    }

    public abstract void onBindViewItemHolder(VH holder, T data, int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public int getItemPosition(T data) {
        return mData.indexOf(data);
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void addItem(T data) {
        mData.add(data);
        notifyItemInserted(getItemPosition(data));
    }

    public void addItem(T data, int position) {
        position = position == -1 ? getItemCount()  : position;
        mData.add(position, data);
        notifyItemInserted(position);
    }

    public void addItems(List<T> data) {
        int position = mData.size();
        mData.addAll(data);
        notifyItemRangeInserted(position, data.size());
    }

    public void addItems(List<T> data, int position) {
        mData.addAll(position, data);
        notifyItemRangeInserted(position, data.size());
    }

    public void removeItem(T data){
        removeItem(getItemPosition(data));
    }

    public void removeItem(int position){
        if (position < getItemCount()) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void moveItem(T data, int toPosition) {
        moveItem(getItemPosition(data), toPosition);
    }

    public void moveItem(int fromPosition, int toPosition) {
        Collections.swap(mData, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
    }

    public void clearItems() {
        mData = new ArrayList<T>();
        notifyDataSetChanged();
    }

}
