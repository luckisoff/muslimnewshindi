package com.example.safi.muslimissues.fragments;

import android.app.Application;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
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
import com.example.safi.muslimissues.Utils;
import com.example.safi.muslimissues.adapter.Adapter;
import com.example.safi.muslimissues.api.GetDataService;
import com.example.safi.muslimissues.model.Articles;
import com.example.safi.muslimissues.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener{
    private Adapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    View rootView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.main_fragments,container,false);
        ((MainActivity) getActivity()).showFloatingActionButton();
        ((MainActivity) getActivity()).showAppTitle("मुस्लिम न्युज");
        swipeRefresh();
        swipeRefreshLayout.setRefreshing(true);
        fillMain();
        dateHijri();

        return rootView;
    }


    private void dateHijri(){

        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getContext());
        int dateAdjust=Integer.parseInt(pref.getString(getString(R.string.key_hijriday),getString(R.string.value_hijriday)));


        NavigationView navigationView =((MainActivity)getActivity()).findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView islamicDate =headerView.findViewById(R.id.islamic_date);
        islamicDate.setText(Utils.umalKuraDate(dateAdjust));
    }

    private void swipeRefresh(){
        swipeRefreshLayout =(SwipeRefreshLayout) rootView.findViewById(R.id.swipe_refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    private void fillMain(){
        GetDataService service= RetrofitClientInstance.getCacheEnabledRetrofit(getContext())
                .create(GetDataService.class);
        Call<List<Articles>> call=service.getAllArticles();

        call.enqueue(new Callback<List<Articles>>() {
            @Override
            public void onResponse(Call<List<Articles>> call, Response<List<Articles>> response) {

                if (response.body()==null){
                    onRefresh();
                    return;
                }

                swipeRefreshLayout.setRefreshing(false);
                recyclerView=(RecyclerView) rootView.findViewById(R.id.customeView);
                adapter=new Adapter(getContext(),response.body());
                adapter.notifyDataSetChanged();
                RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(getContext());
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setNestedScrollingEnabled(false);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<List<Articles>> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(true);
                if (RetrofitClientInstance.hasNetwork(getActivity())){
                    onRefresh();
                    return;
                }
                if (isAdded())
                    Toast.makeText(getContext(),"Something went wrong",Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        fillMain();
    }
}
