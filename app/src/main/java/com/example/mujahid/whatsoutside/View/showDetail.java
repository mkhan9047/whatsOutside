package com.example.mujahid.whatsoutside.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.mujahid.whatsoutside.CustomAdapters.ShowDetailCustomAdapter;
import com.example.mujahid.whatsoutside.Model.Wdata;
import com.example.mujahid.whatsoutside.R;

import java.util.List;

public class showDetail extends AppCompatActivity {
        RecyclerView recyclerView;
        List<Wdata> data;
        ShowDetailCustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_detail);
        data = (List<Wdata>) getIntent().getSerializableExtra("list");

        adapter = new ShowDetailCustomAdapter(this,data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
}
