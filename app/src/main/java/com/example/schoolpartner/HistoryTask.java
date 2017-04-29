package com.example.schoolpartner;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.schoolpartner.db.QueryDB;
import com.example.schoolpartner.gson.Task;
import com.example.schoolpartner.util.HttpUtil;
import com.example.schoolpartner.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class HistoryTask extends AppCompatActivity {
    private List<Task> list=new ArrayList<Task>();
    private TaskAdapter adapter;
    private String user;
    private SwipeRefreshLayout swip;
    private String xuhao;
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
        xuhao = intent.getStringExtra("xuhao");
        init(xuhao);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new TaskAdapter(list,user);
        recyclerView.setAdapter(adapter);
        swip = (SwipeRefreshLayout)findViewById(R.id.HisRefresh);
        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTask();
            }
        });
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
        list.clear();
        if(xuhao.equals("0")){
            List<Task> temp = QueryDB.QueryTask1(user);
            for(Task task:temp){
                list.add(task);
            }
        }
        else if(xuhao.equals("1")){
            List<Task> temp =QueryDB.QueryTask2(user);
            for(Task task:temp){
                list.add(task);
            }
        }
        else if(xuhao.equals("2")){
            List<Task> temp=QueryDB.QueryTask3(user);
            for(Task task:temp){
                list.add(task);
            }
        }
        else{
            List<Task> temp=QueryDB.QueryTask4(user);
            for(Task task:temp){
                list.add(task);
            }
        }

    }
    private void refreshTask(){
        String url = "http://"+ HttpUtil.location+":8086/task/getAll";
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(HistoryTask.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String ResponseText = response.body().string();
                if (!Utility.handleAllTask(ResponseText)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(HistoryTask.this, "解析错误", Toast.LENGTH_SHORT).show();
                            swip.setRefreshing(false);
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(HistoryTask.this, "刷新成功", Toast.LENGTH_SHORT).show();
                            init(xuhao);
                            adapter.notifyDataSetChanged();
                            swip.setRefreshing(false);
                        }
                    });
                }

            }});
    }
}
