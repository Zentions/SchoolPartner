package com.example.schoolpartner;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolpartner.db.QueryDB;
import com.example.schoolpartner.gson.Person;
import com.example.schoolpartner.gson.Task;
import com.example.schoolpartner.util.HttpUtil;
import com.example.schoolpartner.util.Utility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class TaskContent extends AppCompatActivity {
    private Task task;
    private String UserId;
    TextView neiRong,pay,xianZhiShiJian,AddTime,current;
    Button faBuPerson,jieQu,refuse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_content);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.taskToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        Intent intent = getIntent();
        task = (Task)intent.getSerializableExtra("task");
        UserId = intent.getStringExtra("UserId");
        neiRong = (TextView)findViewById(R.id.neirong);
        pay = (TextView)findViewById(R.id.pay);
        xianZhiShiJian = (TextView)findViewById(R.id.xianzhishijian);
        faBuPerson= (Button)findViewById(R.id.ziliaoButton);
        AddTime = (TextView)findViewById(R.id.addTime);
        current = (TextView)findViewById(R.id.currentState);
        jieQu = (Button)findViewById(R.id.one);
        refuse= (Button)findViewById(R.id.refuse);
        neiRong.setText(task.getContent());
        pay.setText("悬赏金额："+task.getMoney()+"（元）");
        xianZhiShiJian.setText("限制时间："+task.getTime()+"（小时）");
        faBuPerson.setText("发布人："+task.getFId()+"（点击查看资料卡）");
        AddTime.setText("添加时间："+task.getAddTime());
        current.setText(task.getState());
        faBuPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callInfo();
            }
        });
        jieQu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(UserId.equals(String.valueOf(task.getFId()))){
                    if(jieQu.getText().equals("确认任务完成")){
                        task.setFinished(true);
                        update2();
                        jieQu.setText("任务结束");
                    }else if(jieQu.getText().equals("同意服务")){
                        task.setAgree(true);
                        update1();
                        jieQu.setText("确认任务完成");
                        refuse.setVisibility(View.INVISIBLE);
                    }else ;

                }else{
                    task.setAccept(true);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss ");
                    Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                    String str = formatter.format(curDate);
                    task.setAcceptTime(str);
                    task.setHelper(Integer.parseInt(UserId));
                    update();
                    jieQu.setClickable(false);
                }
            }
        });
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task.setAccept(false);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss ");
                Date curDate = new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);
                task.setAcceptTime(str);
                task.setHelper(0);
                update();
                refuse.setVisibility(View.INVISIBLE);
                jieQu.setVisibility(View.INVISIBLE);
            }
        });
        init();
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


    private void update() {
        String url = "http://" + HttpUtil.location + ":8086/task/update?id=" + task.getNumber() + "&helper=" + task.getHelper() + "&accept=" + task.isAccept() + "&acceptTime=" + task.getAcceptTime();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TaskContent.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String ResponseText = response.body().string();
                Log.d("gao", ResponseText);
                if (!ResponseText.equals("true")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaskContent.this, "更新失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaskContent.this, "更新成功", Toast.LENGTH_SHORT).show();//duihualuang

                        }
                    });
                }
            }
        });
    }
    private void update1(){
        String url = "http://" + HttpUtil.location + ":8086/task/update1?id="+task.getNumber()+"&agree="+task.isAgree();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TaskContent.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String ResponseText = response.body().string();
                Log.d("gao", ResponseText);
                if (!ResponseText.equals("true")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaskContent.this, "更新失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaskContent.this, "更新成功", Toast.LENGTH_SHORT).show();//duihualuang

                        }
                    });
                }
            }
        });
    }
    private void update2(){
        String url = "http://" + HttpUtil.location + ":8086/task/update2?id="+task.getNumber()+"&finish="+task.isFinished();
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TaskContent.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String ResponseText = response.body().string();
                Log.d("gao", ResponseText);
                if (!ResponseText.equals("true")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaskContent.this, "更新失败", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaskContent.this, "更新成功", Toast.LENGTH_SHORT).show();//duihualuang

                        }
                    });
                }
            }
        });
    }
    private void init(){
        if(UserId.equals(String.valueOf(task.getFId()))){
            if(task.isAccept()) {
                faBuPerson.setText("接取人："+task.getHelper()+"(点击查看资料卡)");
                jieQu.setVisibility(View.VISIBLE);
                jieQu.setText("同意服务");
                refuse.setVisibility(View.VISIBLE);
            }
            else{
                faBuPerson.setText("无人接取，请耐心等待");
                faBuPerson.setClickable(false);
                jieQu.setVisibility(View.INVISIBLE);
            }
            if(task.isAgree()){
                jieQu.setText("确认任务完成");
                refuse.setVisibility(View.INVISIBLE);

            }
            if(task.isFinished()){
                jieQu.setText("任务结束");
                refuse.setVisibility(View.INVISIBLE);
            }

        }else{
            faBuPerson.setText("发布人"+task.getFId()+"(点击查看资料卡)");
            if(!task.isAccept()){
                jieQu.setText("接取任务");
            }else{
                jieQu.setClickable(false);
            }
        }
    }
    private void callInfo(){
        final String checkId;
        if(UserId.equals(String.valueOf(task.getFId()))){
            checkId = String.valueOf(task.getHelper());
        }else{
            checkId = String.valueOf(task.getFId());
        }
        String url =  "http://"+HttpUtil.location+":8086/person/get?id="+checkId;
        HttpUtil.sendHttpRequest(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(TaskContent.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String ResponseText = response.body().string();
                if (!Utility.handlePersonResponse(ResponseText)) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(TaskContent.this, "系统错误", Toast.LENGTH_SHORT).show();;
                        }
                    });
                }else{
                    Person user =  QueryDB.QueryPerson(checkId).get(0);
                    Log.d("gao",user.toString());
                    Intent intent = new Intent(TaskContent.this,Information.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            }
        });

    }
}
