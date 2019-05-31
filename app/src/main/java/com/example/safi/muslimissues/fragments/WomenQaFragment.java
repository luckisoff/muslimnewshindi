package com.example.safi.muslimissues.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.safi.muslimissues.R;
import com.example.safi.muslimissues.activity.WomenSectionActivity;
import com.example.safi.muslimissues.adapter.WomenQAdapter;
import com.example.safi.muslimissues.api.GetDataService;
import com.example.safi.muslimissues.model.WomenQAnswer;
import com.example.safi.muslimissues.model.WomenQAnswerData;
import com.example.safi.muslimissues.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WomenQaFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private WomenQAdapter adapter;
    private ProgressBar progressBar;

    List<WomenQAnswerData> qaData;
    private int page=1;
    private boolean isLoading=true;
    private int pastVisibleItems,visibleItemCount,totalItemCount,previous_total=0;
    private int view_threshold=5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.women_qa_main, container, false);
        recyclerView=rootView.findViewById(R.id.customeView);
        progressBar=rootView.findViewById(R.id.progressbar);

        ((WomenSectionActivity)getContext()).setTitle("सवाल व जवाब");
        swipeRefresh();
        fillMain();


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                visibleItemCount=layoutManager.getChildCount();
                totalItemCount=layoutManager.getItemCount();
                pastVisibleItems=layoutManager.findFirstVisibleItemPosition();

                if (dy>0) {
                    progressBar.setVisibility(View.VISIBLE);
                    if (isLoading) {
                        if (totalItemCount > previous_total) {
                            isLoading = false;
                            previous_total = totalItemCount;
                        }
                    }

                    if (!isLoading && ((totalItemCount - visibleItemCount) <= (pastVisibleItems + view_threshold))) {
                        page++;
                        pagination(page);
                        isLoading = true;

                    }
                }else{
                    progressBar.setVisibility(View.GONE);
                }


            }
        });

        return rootView;
    }


    private void fillMain(){

        GetDataService service= RetrofitClientInstance.getCacheEnabledRetrofit(getContext())
                .create(GetDataService.class);
        Call<WomenQAnswer> call=service.getAllWomenQA(1);

        call.enqueue(new Callback<WomenQAnswer>() {
            @Override
            public void onResponse(Call<WomenQAnswer> call, Response<WomenQAnswer> response) {
                System.out.println(response.body().getData());
                if(response.body().getData().size()>0){

                    layoutManager=new GridLayoutManager(getContext(),1);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter=new WomenQAdapter(response.body().getData());
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    Toast.makeText(getContext(), "Visit again. Nothing available!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<WomenQAnswer> call, Throwable t) {

                if (RetrofitClientInstance.hasNetwork(getActivity())){
                    Toast.makeText(getContext(), "Failed to retrive", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                    return;
                }
                if (isAdded())
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
            }
        });
    }


    private void pagination(int page){
        GetDataService service=RetrofitClientInstance.getCacheEnabledRetrofit(getContext())
                .create(GetDataService.class);
        Call<WomenQAnswer> call=service.getAllWomenQA(page);
        call.enqueue(new Callback<WomenQAnswer>() {
            @Override
            public void onResponse(Call<WomenQAnswer> call, Response<WomenQAnswer> response) {

                if (response.body().getData().size()>0){
                    qaData=response.body().getData();
                    adapter.addWomenQA(qaData);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<WomenQAnswer> call, Throwable t) {

                if (RetrofitClientInstance.hasNetwork(getActivity())){
                    Toast.makeText(getContext(), "Failed to retrieve", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isAdded())
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void swipeRefresh(){
        swipeRefreshLayout=rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);
    }



    @Override
    public void onRefresh() {
        page=1;
        fillMain();
        adapter.notifyDataSetChanged();
    }
}
