package com.example.schoolpartner;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.schoolpartner.gson.Person;

public class Information extends AppCompatActivity {
    private Person user;
    private TextView nameView,sigView,num,phone,mager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        user = (Person)getIntent().getSerializableExtra("user");
        nameView = (TextView)findViewById(R.id.name4);;
        sigView = (TextView)findViewById(R.id.sig4);
        num= (TextView)findViewById(R.id.num4);
        phone = (TextView)findViewById(R.id.phone4);
        mager = (TextView)findViewById(R.id.mager4);
        init();
        ImageButton back = (ImageButton)findViewById(R.id.back4);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void init(){
        nameView.setText(user.getName());
        sigView.setText(user.getSignature());
        num.setText(user.getNumber());
        phone.setText(user.getNumber());
        mager.setText(user.getGragde()+user.getMager());
    }

}
