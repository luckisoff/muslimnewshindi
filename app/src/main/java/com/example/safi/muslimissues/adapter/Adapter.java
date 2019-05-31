package com.example.safi.muslimissues.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.safi.muslimissues.R;
import com.example.safi.muslimissues.Utils;
import com.example.safi.muslimissues.activity.ArticleDetailActivity;
import com.example.safi.muslimissues.model.Articles;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;



public class Adapter extends RecyclerView.Adapter<Adapter.AdapterViewHolder> {

    private List<Articles> dataList;
    private Context context;


    public Adapter(Context context,List<Articles> dataList){
        this.context = context;
        this.dataList = dataList;
    }


    class AdapterViewHolder extends RecyclerView.ViewHolder{

        public final View mView;

        TextView txtTitle;
        TextView txtContents;
        TextView date;
        private ImageView coverImage;
        private CardView articleCardView;
        ProgressBar progressBar;


        AdapterViewHolder(View itemView) {
            super(itemView);
            mView = itemView;


            txtTitle = mView.findViewById(R.id.title);
            txtContents=mView.findViewById(R.id.txtContents);
            coverImage = mView.findViewById(R.id.image);
            date=mView.findViewById(R.id.publishedAt);
            articleCardView=itemView.findViewById(R.id.articleCardView);
            progressBar=itemView.findViewById(R.id.prograss_load_photo);
        }

    }

    @NonNull
    @Override
    public AdapterViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.content_main,parent,false);

        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterViewHolder holder, int position) {

        holder.articleCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Articles article=dataList.get(position);
                Intent intent=new Intent(v.getContext(), ArticleDetailActivity.class);
                intent.putExtra("title",article.getTitle());
                intent.putExtra("content",article.getContents());
                intent.putExtra("date",article.getCreated_at());
                intent.putExtra("source",article.getSource());
                intent.putExtra("sourceUrl",article.getSourceUrl());
                intent.putExtra("imageUrl",article.getImgUrl());

                v.getContext().startActivity(intent);
            }
        });


        holder.txtTitle.setText(dataList.get(position).getTitle());
        holder.txtContents.setText(Html.fromHtml(dataList.get(position).getShort_content()));

        try {
           Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dataList.get(position).getCreated_at());
           String publishedDate=new SimpleDateFormat("dd MMMM yyyy").format(date);


            holder.date.setText(publishedDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));


        builder.build().load(Utils.storage_url+dataList.get(position).getImgUrl())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.coverImage, new Callback() {
                    @Override
                    public void onSuccess() {
                        holder.progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError() {
                        holder.progressBar.setVisibility(View.GONE);

                    }
                });



    }

    @Override
    public int getItemCount() {

        return dataList==null ? 0: dataList.size();
    }


}
