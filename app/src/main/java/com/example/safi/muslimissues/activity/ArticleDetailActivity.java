package com.example.safi.muslimissues.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.safi.muslimissues.R;
import com.example.safi.muslimissues.Utils;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
public class ArticleDetailActivity extends Activity{

    private TextView title,date,content,source;
    private String strTitle,strDate,strContent,strImage,strSource,srcUrl;
    private ImageView image;
    Toolbar mToolbar;
    ProgressBar progressBar;
    private FloatingActionButton fltbtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_details);
        Intent intent=getIntent();



        prepareContent(intent);
        setItems();
        setContent();
    }

    private  void prepareContent(Intent intent){

        strTitle=intent.getStringExtra("title");
        strContent=intent.getStringExtra("content");
        strImage= Utils.storage_url+intent.getStringExtra("imageUrl");
        strSource=intent.getStringExtra("source");
        srcUrl=intent.getStringExtra("sourceUrl");
        try {
            strDate= Utils.getDate(intent.getStringExtra("date"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.app_name));
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
        date=findViewById(R.id.date);
        content=findViewById(R.id.content);
        image=findViewById(R.id.backdrop);
        source=findViewById(R.id.source);
        fltbtn=findViewById(R.id.fab);
        progressBar=findViewById(R.id.prograss_load_photo);
    }

    private void setContent(){
        title.setText(strTitle);
        date.setText(strDate);
        content.setText(Html.fromHtml(strContent));
        source.setText("Source: "+strSource);
        setImage();



        fltbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ArticleDetailActivity.this,VisitSourceActivity.class);
                intent.putExtra("url",srcUrl);
                intent.putExtra("title",strTitle);
                ArticleDetailActivity.this.startActivity(intent);
            }
        });
    }

    private void setImage(){
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttp3Downloader(this));
        builder.build().load(strImage)
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        progressBar.setVisibility(View.GONE);
                    }
                });
    }

}
