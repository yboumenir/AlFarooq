package jsouptutorial.androidbegin.com.jsouptutorial;

import android.app.Activity;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;
import android.view.Window;
import android.widget.TextView;

import java.util.Map;

import jsouptutorial.androidbegin.com.jsouptutorial.R;

import static jsouptutorial.androidbegin.com.jsouptutorial.MainActivity.dump_pref_strings;
import static jsouptutorial.androidbegin.com.jsouptutorial.MainActivity.getPrefString;

public class saved_hadiths extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.saved_hadiths);

        String old_hadith = "";
        TextView t = (TextView) findViewById(R.id.saved_hadiths_textview);


        Map<String, ?> strings = dump_pref_strings(getApplicationContext());

        for (Map.Entry<String, ?> entry: strings.entrySet()) {
            if (entry.getKey().contains("http")){
                old_hadith = old_hadith + "\n\n\t" + entry.getValue().toString() + "\nFrom: " + entry.getKey();
                Log.v("SharedPreferences", entry.getKey() + ":" +
                        entry.getValue().toString());
            }
        }

        t.setText(old_hadith);

    }
}
