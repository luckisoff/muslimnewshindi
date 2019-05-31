package com.example.safi.muslimissues.fragments;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.safi.muslimissues.MainActivity;
import com.example.safi.muslimissues.R;
import com.example.safi.muslimissues.adapter.StoryAdapter;
import com.example.safi.muslimissues.api.GetDataService;
import com.example.safi.muslimissues.model.Stories;
import com.example.safi.muslimissues.network.RetrofitClientInstance;

import org.w3c.dom.Text;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoryFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private View rootView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView recyclerView;
    private StoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.story_fragments, container, false);
        ((MainActivity) getActivity()).showFloatingActionButton();
        ((MainActivity) getActivity()).showAppTitle("कथाऐ");
        swipeRefresh();
        swipeRefreshLayout.setRefreshing(true);
        fillMain();

        return rootView;
    }

    private void swipeRefresh() {
        swipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    private void fillMain() {
        GetDataService service = RetrofitClientInstance.getCacheEnabledRetrofit(getContext())
                .create(GetDataService.class);
        Call<List<Stories>> call = service.getAllStories();

        call.enqueue(new Callback<List<Stories>>() {
            @Override
            public void onResponse(Call<List<Stories>> call, Response<List<Stories>> response) {
                if (response.body()==null){
                    onRefresh();
                    return;
                }
                swipeRefreshLayout.setRefreshing(false);
                recyclerView = (RecyclerView) rootView.findViewById(R.id.customeView);
                adapter = new StoryAdapter(getContext(), response.body());
                adapter.notifyDataSetChanged();
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Stories>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(true);
                if (RetrofitClientInstance.hasNetwork(getActivity())) {
                    onRefresh();
                    return;
                }
                if (isAdded())
                    Toast.makeText(getContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        fillMain();
    }

}