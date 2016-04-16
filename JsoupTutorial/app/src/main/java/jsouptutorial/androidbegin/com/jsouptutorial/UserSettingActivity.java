package jsouptutorial.androidbegin.com.jsouptutorial;

import  android.os.Bundle;
import  android.preference.PreferenceActivity;

import com.androidbegin.jsouptutorial.R;

/**
 * Created by yasser on 1/26/16.
 */
public class UserSettingActivity extends PreferenceActivity{

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }
}


