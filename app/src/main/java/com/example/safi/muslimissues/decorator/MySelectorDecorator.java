package com.example.safi.muslimissues.decorator;

import android.app.Activity;
import android.graphics.drawable.Drawable;

import com.example.safi.muslimissues.R;
import com.github.eltohamy.materialhijricalendarview.CalendarDay;
import com.github.eltohamy.materialhijricalendarview.DayViewDecorator;
import com.github.eltohamy.materialhijricalendarview.DayViewFacade;

public class MySelectorDecorator implements DayViewDecorator {
    private final Drawable drawable;

    public MySelectorDecorator(Activity context) {
        drawable = context.getResources().getDrawable(R.drawable.my_selector);
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        return true;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setSelectionDrawable(drawable);
    }
}
