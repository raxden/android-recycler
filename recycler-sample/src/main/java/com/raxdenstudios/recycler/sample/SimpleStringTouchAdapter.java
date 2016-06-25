package com.raxdenstudios.recycler.sample;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.raxdenstudios.recycler.ItemTouchViewHolder;
import com.raxdenstudios.recycler.RecyclerItemTouchAdapter;

import java.util.List;

/**
 * Created by agomez on 30/11/2015.
 */
public class SimpleStringTouchAdapter extends RecyclerItemTouchAdapter<String, SimpleStringTouchAdapter.StringViewHolder> {

    private static final String TAG = SimpleStringTouchAdapter.class.getSimpleName();

    public SimpleStringTouchAdapter(Context context) {
        super(context, R.layout.string_row);
    }

    public SimpleStringTouchAdapter(Context context, List<String> data) {
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

    public class StringViewHolder extends ItemTouchViewHolder {

        public TextView textView;

        public StringViewHolder(View itemView) {
            super(itemView);
            textView = (TextView)itemView.findViewById(R.id.text_view);
        }
    }
}
