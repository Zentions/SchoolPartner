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
import android.widget.Toast;

import com.example.schoolpartner.db.QueryDB;
import com.example.schoolpartner.gson.Person;
import com.example.schoolpartner.util.HttpUtil;
import com.example.schoolpartner.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class Register extends AppCompatActivity {
   EditText nameView,numberView,passwordView1,passwordView2,sigView,phoneView,gradeView,magarView;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final Toolbar toolbar = (Toolbar)findViewById(R.id.ReleseTaskToolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        nameView = (EditText)findViewById(R.id.name1);
        numberView = (EditText)findViewById(R.id.number1);
        passwordView1 = (EditText)findViewById(R.id.password1);
        passwordView2 = (EditText)findViewById(R.id.password2);
        sigView = (EditText)findViewById(R.id.sig);
        phoneView = (EditText)findViewById(R.id.phone1);
        gradeView = (EditText)findViewById(R.id.grade1);
        magarView = (EditText)findViewById(R.id.mager1);
        register = (Button)findViewById(R.id.register1);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptCommit();
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
    private void attemptCommit(){
        nameView.setError(null);
        numberView.setError(null);
        passwordView1.setError(null);
        passwordView2.setError(null);
        sigView.setError(null);
        phoneView.setError(null);
        gradeView.setError(null);
        magarView.setError(null);
        // Store values at the time of the login attempt.
        final String name = nameView.getText().toString();
        final String password1 = passwordView1.getText().toString();
        final String password2 = passwordView2.getText().toString();
        final String number = numberView.getText().toString();
        final String sig = sigView.getText().toString();
        final String phone = phoneView.getText().toString();
        final String grade = gradeView.getText().toString();
        final String mager = magarView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password1) || !isPasswordValid(password1)) {
            passwordView1.setError(getString(R.string.error_invalid_password));
            focusView = passwordView1;
            cancel = true;
        }
        if (!password1.equals(password2)) {
            passwordView1.setError("Two passwords must be same");
            focusView = passwordView1;
            cancel = true;
        }
        // Check for a valid email address.
        if (TextUtils.isEmpty(number)) {
            numberView.setError(getString(R.string.error_field_required));
            focusView = numberView;
            cancel = true;
        } else if (!isEmailValid(number)) {
            numberView.setError(getString(R.string.error_invalid_email));
            focusView = numberView;
            cancel = true;
        }
        if (TextUtils.isEmpty(phone)) {
            phoneView.setError(getString(R.string.error_field_required));
            focusView = phoneView;
            cancel = true;
        }
        if (TextUtils.isEmpty(grade)) {
            gradeView.setError(getString(R.string.error_field_required));
            focusView = gradeView;
            cancel = true;
        }
        if (TextUtils.isEmpty(mager)) {
            magarView.setError(getString(R.string.error_field_required));
            focusView = magarView;
            cancel = true;
        }

        if (TextUtils.isEmpty(sig)) {
            sigView.setError(getString(R.string.error_field_required));
            focusView = sigView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            String url =  "http://"+ HttpUtil.location+":8086/person/create?id="+number+"&name="+name+"&password="+password1+"&credit=100&signature="+sig+"&phoneNumber="+phone+"&mager="+mager+"&gragde="+grade;
            HttpUtil.sendHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(Register.this, "网络错误", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(Register.this, "注册失败", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }else{
                        runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(Register.this, "注册成功", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }

                }
            });
        }
    }
    private boolean isEmailValid(String number) {
        if(number.matches("[0-9]{6,10}") && number.length()>=6)return true;
        return false;
    }

    private boolean isPasswordValid(String password) {
            //TODO: Replace this with your own logic
        return password.length() > 5;
    }
}
