package com.example.safi.muslimissues.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import com.example.safi.muslimissues.R;

public class SalatSettings extends PreferenceActivity{

    private static final String TAG = SalatSettings.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getFragmentManager().beginTransaction().replace(android.R.id.content,new MyPreferenceFragment()).commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }








    public static class MyPreferenceFragment extends PreferenceFragment{
        AppCompatDelegate mDelegate;

        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            addPreferencesFromResource(R.xml.salat_settings);
        }

        public void setSupportActionBar(@Nullable Toolbar toolbar) {
            getDelegate().setSupportActionBar(toolbar);
        }

        public ActionBar getSupportActionBar() {
            return getDelegate().getSupportActionBar();
        }

        private AppCompatDelegate getDelegate() {
            if (mDelegate == null) {
                mDelegate = AppCompatDelegate.create(getActivity(),null);
            }
            return mDelegate;
        }
    }
}
