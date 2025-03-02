package com.example.usingpreferences;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.view.View;

import java.util.List;

public class AppPreferenceActivity extends PreferenceActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public void onBuildHeaders(List<Header> target) {
        loadHeadersFromResource(R.xml.prefheaders, target);
    }
    @Override
    protected boolean isValidFragment(String fragmentName) {
        return true;
    }
    public static class PrefFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            PreferenceManager.setDefaultValues(getActivity(),
                    R.xml.myapppreferences, false);
            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.myapppreferences);
        }
        public void onClickLoad(View view) {
            Intent i = new Intent("com.example.AppPreferenceActivity");
            startActivity(i);
        }
    }
}