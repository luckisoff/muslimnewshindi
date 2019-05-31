package com.example.safi.muslimissues.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.safi.muslimissues.MainActivity;
import com.example.safi.muslimissues.R;
import com.example.safi.muslimissues.Utils;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.Geofence;

import net.alqs.iclib.salat.AngleRule;
import net.alqs.iclib.salat.TimeAdjustment;
import net.alqs.iclib.salat.TimeCalculator;
import net.alqs.iclib.salat.Times;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import io.nlopez.smartlocation.OnActivityUpdatedListener;
import io.nlopez.smartlocation.OnGeofencingTransitionListener;
import io.nlopez.smartlocation.OnLocationUpdatedListener;
import io.nlopez.smartlocation.OnReverseGeocodingListener;
import io.nlopez.smartlocation.SmartLocation;
import io.nlopez.smartlocation.geofencing.model.GeofenceModel;
import io.nlopez.smartlocation.geofencing.utils.TransitionGeofence;
import io.nlopez.smartlocation.location.providers.LocationGooglePlayServicesProvider;

public class SalatFragment extends Fragment implements OnLocationUpdatedListener, OnActivityUpdatedListener, OnGeofencingTransitionListener  {

    private View rootView;
    private TextView date,locationtxt;
    ListView listView;
    private Location location;
    private LocationGooglePlayServicesProvider provider;
    ProgressBar progressBar;
    ProgressBar progressBar1;
    List<Address> addressElements;
    FloatingActionButton fab;


    private static final int LOCATION_PERMISSION_ID = 1001;
    //GpsTracker gps;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.salat, container, false);

        ((MainActivity) getActivity()).hideFloatingActionButton();

        prepareItems();
        setItem();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_ID);

        }else if(location==null){
            startLocation();
        } else{
            showLast();
        }

        return rootView;
    }

    private void prepareItems() {
        locationtxt=rootView.findViewById(R.id.location);
        date=rootView.findViewById(R.id.date);
        progressBar=rootView.findViewById(R.id.prograss_load_photo);
        progressBar1=rootView.findViewById(R.id.progress_location);
        listView=rootView.findViewById(R.id.salatList_layout);
        progressBar.setVisibility(View.VISIBLE);
        fab=rootView.findViewById(R.id.calendarBtn);

    }

    private void setItem() {
        SharedPreferences pref= PreferenceManager.getDefaultSharedPreferences(getContext());
        int dateAdjust=Integer.parseInt(pref.getString(getString(R.string.key_hijriday),getString(R.string.value_hijriday)));
        date.setText(Utils.umalKuraDate(dateAdjust));

    }

    private void salatTiming(double latitude, double longitude) {

        boolean hanafiRatio;
        AngleRule anglerule;

        SharedPreferences pref=PreferenceManager.getDefaultSharedPreferences(getContext());
        String juristic=pref.getString(getString(R.string.key_juristic),getString(R.string.value_jusristic));
        String method=pref.getString(getString(R.string.key_calculation_method),getString(R.string.value_calculation_method));
        int dateAdjust=Integer.parseInt(pref.getString(getString(R.string.key_hijriday),getString(R.string.value_hijriday)));

        if (juristic.equals("Hanafi")){
            hanafiRatio=true;
        }else{
            hanafiRatio=false;
        }

        if (method.equals("MWL")){
            anglerule=AngleRule.MWL;
        }else if(method.equals("ISNA")){
            anglerule=AngleRule.ISNA;
        }else if(method.equals("EGYPT")){
            anglerule=AngleRule.EGYPT;
        }else{
            anglerule=AngleRule.KARACHI;
        }


        TimeZone timeZone=Calendar.getInstance().getTimeZone();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        format.setTimeZone(TimeZone.getTimeZone(timeZone.getID()));
        GregorianCalendar cal=new GregorianCalendar();
        cal.add(cal.DATE,dateAdjust);
        Times times = new TimeCalculator()
                .date(cal)
                .location(latitude, longitude, 0, 0)
                .method(anglerule,hanafiRatio, TimeAdjustment.ZEROS)
                .calculate();

        String[] titleArry={"फजर","सूर्योदय","जुहर","असर","मग्रिब","इशा"};
        String[] dateArray={format.format(times.getFajr()),format.format(times.getSunrise()),format.format(times.getZuhr()),
                format.format(times.getAsr()),format.format(times.getMaghrib()),format.format(times.getIsha())};
        List<Map<String,String>> listArray=new ArrayList<>();
        for (int i=0;i<titleArry.length;i++){
            Map<String,String> listItem=new HashMap<>();
            listItem.put("dateArray",dateArray[i]);
            listItem.put("titleKey",titleArry[i]);

            listArray.add(listItem);
        }
        SimpleAdapter simpleAdapter=new SimpleAdapter(getContext(),listArray,R.layout.salat_layout,
                new String[]{"titleKey","dateArray"},new int[]{R.id.salat_title,R.id.salat_timing});
        listView.setAdapter(simpleAdapter);


        Geocoder geocoder=new Geocoder(getContext());
        try {
            List<Address> address = geocoder.getFromLocation(latitude,longitude,1);
            if (address.size()>0){
                Address ad=address.get(0);
                locationtxt.setText(ad.getLocality()+", "+ad.getCountryName());
                progressBar1.setVisibility(View.GONE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        progressBar.setVisibility(View.GONE);

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATION_PERMISSION_ID && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            if (location!=null)
                showLast();
            else
                startLocation();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (provider != null) {
            provider.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onActivityUpdated(DetectedActivity detectedActivity) {

        if(location!=null) {
            showLast();
        }else{
            startLocation();
        }
    }

    @Override
    public void onGeofenceTransition(TransitionGeofence transitionGeofence) {

    }

    @Override
    public void onLocationUpdated(Location location) {

        if (location!=null){
            showLast();
        }else{
            startLocation();
        }

    }


    private void showLast() {
        Location lastLocation = SmartLocation.with(getContext()).location().getLastLocation();
        if (lastLocation != null) {
            location=lastLocation;
            salatTiming(location.getLatitude(),location.getLongitude());
        }
    }

    private void startLocation() {
        progressBar.setVisibility(View.VISIBLE);
        provider = new LocationGooglePlayServicesProvider();
        provider.setCheckLocationSettings(true);

        SmartLocation smartLocation = new SmartLocation.Builder(getContext()).logging(true).build();
        smartLocation.location(provider).start(this);
        smartLocation.activity().start(this);
        location=smartLocation.location().getLastLocation();

        if (location!=null){
            // Create some geofences
            salatTiming(location.getLatitude(),location.getLongitude());
            progressBar.setVisibility(View.GONE);
        }
    }

    private void stopLocation() {
        SmartLocation.with(getContext()).location().stop();
        SmartLocation.with(getContext()).activity().stop();
        SmartLocation.with(getContext()).geofencing().stop();
    }

}
