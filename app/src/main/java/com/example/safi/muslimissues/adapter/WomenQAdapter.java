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
import com.example.safi.muslimissues.model.WomenQAnswerData;

import java.util.List;

public class WomenQAdapter extends RecyclerView.Adapter {
    private List<WomenQAnswerData> dataList;



    public WomenQAdapter(List<WomenQAnswerData> dataList) {
        this.dataList = dataList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.women_qa_layout, parent, false);
        return new WomenViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((WomenViewHolder) holder).title.setText(dataList.get(position).getTitle());

        ((WomenViewHolder) holder).qacardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WomenQAnswerData womenQAnswerData=dataList.get(position);
                Intent intent=new Intent(v.getContext(), WomenAnsActivity.class);
                intent.putExtra("title",womenQAnswerData.getTitle());
                intent.putExtra("content",womenQAnswerData.getContents());

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
        public CardView qacardview;

        public WomenViewHolder(View v) {
            super(v);

            title = v.findViewById(R.id.title);
            qacardview=v.findViewById(R.id.qacardview);
        }
    }


    public void addWomenQA(List<WomenQAnswerData> qAnswerData){
        for (WomenQAnswerData data:qAnswerData){
            dataList.add(data);
        }
        notifyDataSetChanged();

    }
}

