package com.example.safi.muslimissues.fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.safi.muslimissues.MainActivity;
import com.example.safi.muslimissues.R;
import com.example.safi.muslimissues.decorator.EventDecorator;
import com.example.safi.muslimissues.decorator.HighlightWeekendsDecorator;
import com.example.safi.muslimissues.decorator.MySelectorDecorator;
import com.example.safi.muslimissues.decorator.OneDayDecorator;
import com.github.eltohamy.materialhijricalendarview.CalendarDay;
import com.github.eltohamy.materialhijricalendarview.MaterialHijriCalendarView;
import com.github.eltohamy.materialhijricalendarview.OnDateSelectedListener;
import com.github.msarhan.ummalqura.calendar.UmmalquraCalendar;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class CalendarFragment extends Fragment implements OnDateSelectedListener{

    private View rootView;
    //@BindView(R.id.calendarView)
    MaterialHijriCalendarView widget;
    private final OneDayDecorator oneDayDecorator = new OneDayDecorator();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.calendar_layout,container,false);

        ((MainActivity)getContext()).showFloatingActionButton();
        ((MainActivity)getContext()).showAppTitle("हिज्रि क्यालेण्डर");
        widget=rootView.findViewById(R.id.calendarView);
        widget.setOnDateChangedListener(this);


        //ButterKnife.bind(getActivity());



        return rootView;
    }

    @Override
    public void onDateSelected(@NonNull MaterialHijriCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        //If you change a decorate, you need to invalidate decorators
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
    }

    @Override
    public void onStart(){
        super.onStart();

        widget.setOnDateChangedListener(this);
        widget.setShowOtherDates(MaterialHijriCalendarView.SHOW_ALL);

        Calendar calendar = Calendar.getInstance();
        widget.setSelectedDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR), Calendar.JANUARY, 1);
        widget.setMinimumDate(calendar.getTime());

        calendar.set(calendar.get(Calendar.YEAR), Calendar.DECEMBER, 31);
        widget.setMaximumDate(calendar.getTime());

        widget.addDecorators(
                new MySelectorDecorator(getActivity()),
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );

        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                UmmalquraCalendar ummalquraCalendar = new UmmalquraCalendar();
                ummalquraCalendar.setTime(calendar.getTime());
                CalendarDay day = CalendarDay.from(ummalquraCalendar);
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }

            return dates;
        }

        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

//            if (((MainActivity)getContext()).isFinishing()) {
//                return;
//            }
//
//            widget.addDecorator(new EventDecorator(getResources().getColor(R.color.colorPrimary), calendarDays));
        }
    }


    }
