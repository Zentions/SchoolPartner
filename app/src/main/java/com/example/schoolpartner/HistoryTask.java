package com.example.schoolpartner;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.schoolpartner.db.QueryDB;
import com.example.schoolpartner.gson.Task;

import java.util.List;

public class HistoryTask extends AppCompatActivity {
    private List<Task> list;
    private TaskAdapter adapter;
    private String user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_task);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.Historytoolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        user = intent.getStringExtra("user");
        init(intent.getStringExtra("xuhao"));
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new TaskAdapter(list,user);
        recyclerView.setAdapter(adapter);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void init(String xuhao){
        if(xuhao.equals("0")) list = QueryDB.QueryTask1(user);
        else if(xuhao.equals("1"))list=QueryDB.QueryTask2(user);
        else if(xuhao.equals("2"))list=QueryDB.QueryTask3(user);
        else list=QueryDB.QueryTask4(user);

    }
}
