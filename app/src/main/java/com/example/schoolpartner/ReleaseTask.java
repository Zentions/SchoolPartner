package com.example.schoolpartner;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.schoolpartner.db.QueryDB;
import com.example.schoolpartner.gson.Person;
import com.example.schoolpartner.util.HttpUtil;
import com.example.schoolpartner.util.Utility;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ReleaseTask extends AppCompatActivity implements View.OnClickListener{
    EditText content,matter,time,money;
    EditText title;
    Button commit1;
    ImageButton commit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_task);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.ReleseTaskToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        content = (EditText)findViewById(R.id.content1);
        matter= (EditText)findViewById(R.id.matter);
        time = (EditText)findViewById(R.id.time1);
        money = (EditText)findViewById(R.id.money1);
        title = (EditText)findViewById(R.id.title1);
        commit=(ImageButton)findViewById(R.id.commit);
        commit1 = (Button)findViewById(R.id.commit1);
        commit.setOnClickListener(this);
        commit1.setOnClickListener(this);
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
    void attemptCommit(){
        money.setError(null);
        time.setError(null);

        // Store values at the time of the login attempt.
        final String moneyStr = money.getText().toString();
        final String timeStr = time.getText().toString();
        final String titleStr = title.getText().toString();
        final String contentStr = content.getText().toString();
        String matterStr = matter.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(timeStr)) {
            time.setError("限制时间不能为空");
            focusView = time;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(moneyStr)) {
            money.setError("悬赏金额不能为空");
            focusView = money;
            cancel = true;
        }
        if (TextUtils.isEmpty(titleStr)){
            title.setError("标题不能为空");
            focusView = title;
            cancel = true;
        }
        if (TextUtils.isEmpty(contentStr)){
            Toast.makeText(ReleaseTask.this,"内容不能为空",Toast.LENGTH_SHORT).show();
            focusView = content;
            cancel = true;
        }
        if (TextUtils.isEmpty(matterStr)){
            matterStr = "无";
        }
        if (cancel) {
            focusView.requestFocus();
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss ");
            Date curDate = new Date(System.currentTimeMillis());//获取当前时间
            String str = formatter.format(curDate);
            Intent intent = getIntent();
            String url =  "http://"+ HttpUtil.location+":8086/task/create?title="+titleStr+"&classification=wu&content="+contentStr+"&time="+timeStr+"&money="+moneyStr+"&addTime="+str+"&mattersNeedAttention="+matterStr+"&FId="+intent.getStringExtra("id")+"&phoneNumber="+intent.getStringExtra("phone");
            HttpUtil.sendHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(ReleaseTask.this, "网络错误", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    final String ResponseText = response.body().string();
                    if (!ResponseText.equals("true")) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ReleaseTask.this, "提交失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(ReleaseTask.this, "提交成功", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            });


                    }
                }
            });


        }
    }

    @Override
    public void onClick(View v) {
        attemptCommit();
    }
}
