package com.example.safi.muslimissues.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.safi.muslimissues.R;
import com.example.safi.muslimissues.activity.WomenAnsActivity;
import com.example.safi.muslimissues.model.Stories;

import java.util.List;

public class StoryAdapter extends RecyclerView.Adapter<StoryAdapter.AdapterViewHolder> {

    private List<Stories> dataList;
    private Context context;

    public StoryAdapter(Context context,List<Stories> dataList){
        this.context = context;
        this.dataList = dataList;
    }


    class AdapterViewHolder extends RecyclerView.ViewHolder{

        public final View mView;

        TextView txtTitle,txtContents;
        private CardView articleCardView;


        AdapterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            txtTitle = mView.findViewById(R.id.title);
            txtContents=mView.findViewById(R.id.txtContents);
            articleCardView=mView.findViewById(R.id.articleCardView);


        }

    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.story_layout,parent,false);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

        holder.articleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Stories story=dataList.get(position);
                Intent intent=new Intent(v.getContext(), WomenAnsActivity.class);
                intent.putExtra("title",story.getTitle());
                intent.putExtra("content",story.getContents());

                v.getContext().startActivity(intent);
            }
        });


        holder.txtTitle.setText(dataList.get(position).getTitle());

    }

    @Override
    public int getItemCount() {

        return dataList==null ? 0: dataList.size();
    }


}
