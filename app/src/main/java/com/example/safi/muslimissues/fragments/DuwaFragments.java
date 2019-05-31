package com.example.safi.muslimissues.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.safi.muslimissues.MainActivity;
import com.example.safi.muslimissues.R;
import com.example.safi.muslimissues.adapter.DuwaAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DuwaFragments extends Fragment {
    private View rootView;
    RecyclerView recyclerView;
    DuwaAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.duwa_fragment, container, false);

        ((MainActivity)getContext()).showFloatingActionButton();
        ((MainActivity)getContext()).showAppTitle("रब्बना दुवाऐ");
        prepareItem();
        setDuwa();

        return rootView;
    }

    private void prepareItem(){
        recyclerView=rootView.findViewById(R.id.duwa_listView);
    }

    private void setDuwa(){

        JSONObject json= null;
        List<JSONObject> duwaJsonOb = new ArrayList<>();
        try {
            json = new JSONObject(loadJSONFromAsset());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            JSONArray data=json.getJSONArray("data");

            for (int i=0;i<data.length();i++){
                JSONObject j=data.getJSONObject(i);
                duwaJsonOb.add(j);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        adapter=new DuwaAdapter(getContext(),duwaJsonOb);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }


    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getActivity().getAssets().open("duwa.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

}
