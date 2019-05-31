package com.example.safi.muslimissues.fragments;

import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.safi.muslimissues.EndlessRecyclerOnScrollListener;
import com.example.safi.muslimissues.R;
import com.example.safi.muslimissues.activity.WomenSectionActivity;
import com.example.safi.muslimissues.adapter.Adapter;
import com.example.safi.muslimissues.adapter.WomenArticleAdapter;
import com.example.safi.muslimissues.api.GetDataService;
import com.example.safi.muslimissues.model.Articles;
import com.example.safi.muslimissues.model.Data;
import com.example.safi.muslimissues.model.WomenArticle;
import com.example.safi.muslimissues.network.RetrofitClientInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WomenArticleFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private View rootView;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private WomenArticleAdapter adapter;
    private LinearLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;
    List<Data> articleData;

    private int page=1;
    private boolean isLoading=true;
    private int pastVisibleItems,visibleItemCount,totalItemCount,previous_total=0;
    private int view_threshold=5;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.women_article_fragment, container, false);
        recyclerView=rootView.findViewById(R.id.women_article);
        progressBar=rootView.findViewById(R.id.progressbar);

        ((WomenSectionActivity)getContext()).setTitle("लेख");;



        setSwipeRefreshLayout();
        fillAll();

        recyclerView.addOnScrollListener(new OnScrollListener() {
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
                        progressBar.setVisibility(View.GONE);

                    }
                }else{
                    progressBar.setVisibility(View.GONE);
                }


            }
        });
        return rootView;
    }



    private void fillAll(){

        GetDataService service= RetrofitClientInstance.getCacheEnabledRetrofit(getContext())
                .create(GetDataService.class);
        Call<WomenArticle> call=service.getAllWomenArticles(1);

        call.enqueue(new Callback<WomenArticle>() {
            @Override
            public void onResponse(Call<WomenArticle> call, Response<WomenArticle> response) {
                if(response.body().getData().size()>0){

                    layoutManager=new GridLayoutManager(getContext(),1);
                    recyclerView.setLayoutManager(layoutManager);
                    adapter=new WomenArticleAdapter(response.body().getData());
                    recyclerView.setAdapter(adapter);
                    swipeRefreshLayout.setRefreshing(false);
                }else{
                    Toast.makeText(getContext(), "Visit again. Nothing available!", Toast.LENGTH_SHORT).show();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }

            @Override
            public void onFailure(Call<WomenArticle> call, Throwable t) {

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
        Call<WomenArticle> call=service.getAllWomenArticles(page);
        call.enqueue(new Callback<WomenArticle>() {
            @Override
            public void onResponse(Call<WomenArticle> call, Response<WomenArticle> response) {

                if (response.body().getData().size()>0){
                    articleData=response.body().getData();
                    adapter.addWomenArticles(articleData);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<WomenArticle> call, Throwable t) {

                if (RetrofitClientInstance.hasNetwork(getActivity())){
                    Toast.makeText(getContext(), "Failed to retrieve", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (isAdded())
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setSwipeRefreshLayout(){
        swipeRefreshLayout=rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void onRefresh() {
        fillAll();
        page=1;
    }
}

