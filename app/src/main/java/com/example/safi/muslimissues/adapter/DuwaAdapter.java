package com.example.safi.muslimissues.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safi.muslimissues.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class DuwaAdapter extends RecyclerView.Adapter<DuwaAdapter.MyViewHolder> {

    LayoutInflater layoutInflater;
    Context context;
    List<JSONObject> dataList;
    private static int selectedPosition=-1;


    public DuwaAdapter(Context context, List<JSONObject> arraylist) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.dataList=arraylist;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        layoutInflater=LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.duwa_layout, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DuwaAdapter.MyViewHolder holder, int position) {

        Typeface typeface=Typeface.createFromAsset(context.getAssets(),"amri.ttf");



        holder.duwa_cardview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.duwa_dialogue_view);
                dialog.closeOptionsMenu();

                TextView ref = dialog.findViewById(R.id.reference);
                TextView arabic = dialog.findViewById(R.id.duwa_arabic);
                arabic.setTextColor(context.getResources().getColor(R.color.colorPrimary));
                //arabic.setTextSize(24.0f);
                TextView hindi = dialog.findViewById(R.id.duwa_hindi);
                try {
                    ref.setText("Quran("+dataList.get(position).getString("chapter")+")");
                    arabic.setText(dataList.get(position).getString("duwa_text"));
                    hindi.setText(dataList.get(position).getString("duwa_hindi"));
                    arabic.setTypeface(typeface);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                dialog.show();
                selectedPosition=position;
                notifyDataSetChanged();
            }
        });





        try {
            holder.duwa_text.setText(dataList.get(position).getString("duwa_text"));
            holder.duwa_text.setTypeface(typeface);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (selectedPosition==position){
            holder.duwa_cardview.setBackgroundColor(context.getResources().getColor(R.color.colorPrimary));
            holder.duwa_text.setTextColor(context.getResources().getColor(R.color.colorWhite));
        }else{
            holder.duwa_cardview.setBackgroundColor(context.getResources().getColor(R.color.colorWhite));
            holder.duwa_text.setTextColor(context.getResources().getColor(R.color.colorTextTitle));
        }

    }

    @Override
    public int getItemCount() {
        return dataList==null ? 0: dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        final View mView;
        CardView duwa_cardview;
        TextView duwa_text;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;


            duwa_text=mView.findViewById(R.id.duwa_arabic);
            duwa_cardview=mView.findViewById(R.id.duwa_card);

        }
    }
}
