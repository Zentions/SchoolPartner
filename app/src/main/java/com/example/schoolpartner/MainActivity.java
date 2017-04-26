package com.example.schoolpartner;


import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolpartner.db.QueryDB;
import com.example.schoolpartner.gson.Person;
import com.example.schoolpartner.gson.Task;
import com.example.schoolpartner.util.HttpUtil;
import com.example.schoolpartner.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private List<Task> list;
    private DrawerLayout drawerLayout;
    private Person user;
    private SwipeRefreshLayout swip;
    private TaskAdapter adapter;
    private FloatingActionButton floating;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        user = (Person)getIntent().getSerializableExtra("user");
        Toolbar toolbar = (Toolbar)findViewById(R.id.bar);
        setSupportActionBar(toolbar);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setCheckedItem(R.id.zhengZaiQiuZhu);
        View view = navigationView.inflateHeaderView(R.layout.nav_header);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Intent intent = new Intent(MainActivity.this,HistoryTask.class);
                intent.putExtra("user", user.getNumber());
                switch (item.getItemId()){
                    case R.id.zhengZaiQiuZhu:
                        intent.putExtra("xuhao","0");
                        startActivity(intent);
                        break;
                    case R.id.finish:
                        intent.putExtra("xuhao","1");
                        startActivity(intent);
                        break;
                    case R.id.yiJieShou:
                        intent.putExtra("xuhao","2");
                        startActivity(intent);
                        break;
                    case R.id.finishdeTask:
                        intent.putExtra("xuhao","3");
                        startActivity(intent);
                        break;
                    default:break;
                }
                return true;
            }
        });
        CollapsingToolbarLayout coll = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        coll.setCollapsedTitleTextColor(Color.WHITE);
        coll.setExpandedTitleColor(Color.WHITE);
        list = QueryDB.QueryTask();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.recycler);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new TaskAdapter(list,user.getNumber());
        recyclerView.setAdapter(adapter);
        TextView t1 = (TextView)view.findViewById(R.id.ceName);
        t1.setText(user.getName());
        TextView t2 = (TextView)view.findViewById(R.id.ceSig);
        t2.setText(user.getSignature());
        CircleImageView circleImageView = (CircleImageView)view.findViewById(R.id.nav_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Information.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        swip = (SwipeRefreshLayout)findViewById(R.id.refresh);
        swip.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshTask();
            }
        });
        floating = (FloatingActionButton)findViewById(R.id.floating);
        floating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ReleaseTask.class);
                intent.putExtra("id",user.getNumber());
                intent.putExtra("phone",user.getPhoneNumber());
                startActivity(intent);

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            default:break;
        }
        return true;
    }
    private void refreshTask(){
        String url = "http://"+HttpUtil.location+":8086/task/getAll";
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "网络错误", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(MainActivity.this, "解析错误", Toast.LENGTH_SHORT).show();
                            swip.setRefreshing(false);
                        }
                    });
                } else {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            list.clear();
                            List<Task> temp = QueryDB.QueryTask();
                            for(Task task:temp){
                                list.add(task);
                            }
                            Log.d("gao","+"+list.size());
                            adapter.notifyDataSetChanged();
                            swip.setRefreshing(false);
                        }
                    });
                }

            }});
    }

}
