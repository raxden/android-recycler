package com.raxdenstudios.recycler.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView.Adapter adapter = null;

        {
            List<String> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                data.add("Item:" + i);
            }
//            adapter = new SimpleStringAdapter(this, data);
        }

        {
            Map<String, List<String>> data = new LinkedHashMap<>();
            for (int i = 0; i < 2; i++) {
                List<String> items = new ArrayList<>();
                for (int j = 0; j < 2; j++) {
                    items.add("Item:" + j);
                }
                data.put("Section: " + i, items);
            }
//            adapter = new SimpleSectionedStringAdapter(this, data);
        }

        {
            List<String> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                data.add("Item:" + i);
            }
            adapter = new SimpleStringTouchAdapter(this, data);
        }

        if (adapter != null) {
            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
    }

}
