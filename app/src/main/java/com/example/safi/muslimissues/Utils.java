package com.example.safi.muslimissues;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Utils {

    //public static final String BASE_URL="https://muslimissues.000webhostapp.com/";
    public static final String BASE_URL="http://192.168.0.19/muslimnews/";
    public static final String storage_url=BASE_URL+"storage/";

    public static String getDate(String timestamp) throws ParseException {
        Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
        return new SimpleDateFormat("dd MMMM yyyy").format(date);
    }

    public static String getTime(String timestamp) throws ParseException {
        Date date=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestamp);
        return new SimpleDateFormat("HH:mm").format(date);
    }




    public static String umalKuraDate(int dateAdjust){
        Locale english=new Locale("en");

        UmmalquraCalendar cal=new UmmalquraCalendar(english);
        cal.add(cal.DATE,dateAdjust);

        SimpleDateFormat dateFormat = new SimpleDateFormat("", english);
        dateFormat.setCalendar(cal);
        dateFormat.applyPattern("d/M/y");
        return cal.get(Calendar.DAY_OF_MONTH)+" "+cal.getDisplayName(Calendar.MONTH, Calendar.LONG, english)+" "+cal.get(Calendar.YEAR);

    }

    public static String calanderDate(int dateAdjust){
        Locale english=new Locale("en");

        UmmalquraCalendar cal=new UmmalquraCalendar(english);
        cal.add(cal.DATE,dateAdjust);

        SimpleDateFormat dateFormat = new SimpleDateFormat("", english);
        dateFormat.setCalendar(cal);
        dateFormat.applyPattern("d/M/y");
        return cal.get(Calendar.DAY_OF_MONTH)+" "+cal.getDisplayName(Calendar.MONTH, Calendar.LONG, english);

    }

}
