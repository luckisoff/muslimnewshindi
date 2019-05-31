package com.example.safi.muslimissues.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.example.safi.muslimissues.R;

public class WomenAnsActivity extends Activity{

    private TextView title,content;
    private String strTitle,strContent;

    Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.women_qa_detail);
        Intent intent=getIntent();



        prepareContent(intent);
        setItems();
        setContent();
    }

    private  void prepareContent(Intent intent){

        strTitle=intent.getStringExtra("title");
        strContent=intent.getStringExtra("content");

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(strTitle);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


    private void setItems(){
        title=findViewById(R.id.title);
        content=findViewById(R.id.content);
    }

    private void setContent(){
        title.setText(strTitle);
        content.setText(Html.fromHtml(strContent));
    }


}
