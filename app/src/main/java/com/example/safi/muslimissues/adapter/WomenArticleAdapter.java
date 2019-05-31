package com.example.safi.muslimissues.adapter;

import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.safi.muslimissues.R;
import com.example.safi.muslimissues.activity.WomenAnsActivity;
import com.example.safi.muslimissues.model.Data;

import java.util.Iterator;
import java.util.List;

public class WomenArticleAdapter extends RecyclerView.Adapter {
    private List<Data> dataList;



    public WomenArticleAdapter(List<Data> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.women_article_list, parent, false);
            return new WomenViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            ((WomenViewHolder) holder).title.setText(dataList.get(position).getTitle());
            ((WomenViewHolder) holder).sumamry.setText(dataList.get(position).getSummary());

            ((WomenViewHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Data data=dataList.get(position);
                    Intent intent=new Intent(v.getContext(), WomenAnsActivity.class);
                    intent.putExtra("title",data.getTitle());
                    intent.putExtra("content",data.getContents());

                    v.getContext().startActivity(intent);
                }
            });


    }


    @Override
    public int getItemCount() {
        return dataList!=null ? dataList.size():0;
    }


    //
    public static class WomenViewHolder extends RecyclerView.ViewHolder {

        public TextView title;
        public TextView sumamry;
        public CardView cardView;

        public WomenViewHolder(View v) {
            super(v);

            title = v.findViewById(R.id.title);
            sumamry = v.findViewById(R.id.summary);
            cardView=v.findViewById(R.id.womenarticlecard);
        }
    }


    public void addWomenArticles(List<Data> articles){
        for (Data data:articles){
            dataList.add(data);
        }
        notifyDataSetChanged();

    }
}
