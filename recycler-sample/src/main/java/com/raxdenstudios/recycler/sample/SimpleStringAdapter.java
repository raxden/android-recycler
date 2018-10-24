package com.raxdenstudios.recycler.sample;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raxdenstudios.recycler.RecyclerAdapter;

import java.util.List;

/**
 * Created by agomez on 30/11/2015.
 */
public class SimpleStringAdapter extends RecyclerAdapter<String, SimpleStringAdapter.StringViewHolder> {

    public SimpleStringAdapter(Context context) {
        super(context, R.layout.string_row);
    }

    public SimpleStringAdapter(Context context, List<String> data) {
        super(context, R.layout.string_row, data);
    }

    @Override
    public void onBindViewItemHolder(StringViewHolder holder, String data, int position) {
        holder.textView.setText(data);
    }

    @Override
    public StringViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new StringViewHolder(LayoutInflater.from(mContext).inflate(mResource, null));
    }

    public class StringViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public StringViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.text_view);
        }

    }
}
