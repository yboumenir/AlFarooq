package jsouptutorial.androidbegin.com.jsouptutorial;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import jsouptutorial.androidbegin.com.jsouptutorial.R;

public class saved_hadiths extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.saved_hadiths);
    }
}
