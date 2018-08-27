package jsouptutorial.androidbegin.com.jsouptutorial;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;


// to save using sharedprefreferences
import android.content.BroadcastReceiver;
import android.widget.Toast;

// from Alarm class


import android.view.Menu;
import android.view.MenuItem;






public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener{

    // saving preference stuff
    private static final int RESULT_SETTINGS = 1;
    public static Context context;


    private PendingIntent pendingIntent;
    private GrabSalatTimes salat_times;
    Intent alarm_intent;

    // String for font style (default)
    String Font_style = "NULL";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        salat_times = new GrabSalatTimes(context);

        alarm_intent = new Intent(this, MyReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), 0, alarm_intent, 0);

        // Locate the Buttons in activity_main.xml
        Button titlebutton = (Button) findViewById(R.id.titlebutton);
        Button start_fajr_alarm_button = (Button) findViewById(R.id.start_fajr_alarm_button);
        Button stop_fajr_alarm_button = (Button) findViewById(R.id.stop_fajr_alarm_button);
        final Button save_hadith_button = (Button) findViewById(R.id.button_save_hadith);

        // Capture button click
        titlebutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute Title AsyncTask
//                new Title().execute();
                SalatTimes latest_salat_times = salat_times.get_salat_times();
                update_salat_times_view(latest_salat_times);

                Hadith hadith = salat_times.get_hadith();
                update_latest_hadith(hadith);
            }
        });

        // Set fajr alarm
        start_fajr_alarm_button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                Toast.makeText(getApplicationContext(), "Setting alarm 30 minutes after Fajr",Toast.LENGTH_LONG).show();
                start_alarm();
            }
        });

        // stop fajr alarm
        stop_fajr_alarm_button.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                stop_alarm();

            }
        });

        save_hadith_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s;
                Hadith hadith = salat_times.get_hadith();
                if (hadith.hadith_text == ""){
                    s="Oops no Hadith";
                }
                else{
                    s ="Saving Hadith:\n" + hadith.hadith_title + "\n" + hadith.hadith_text;
                    SaveInPreference(getApplicationContext(), hadith.hadith_url, hadith.hadith_text);
                    SaveInPreference(getApplicationContext(), "last_saved_hadith_title", hadith.hadith_title);
                    SaveInPreference(getApplicationContext(), "last_saved_hadith_text", hadith.hadith_text);
                }
                Toast.makeText(getApplicationContext(), s,Toast.LENGTH_LONG).show();
            }
        });

        // Restore preferences
        // upload last saved hadith
        String old_hadith = getPrefString(getApplicationContext(), "last_saved_hadith_text","");
        TextView t = (TextView) findViewById(R.id.hadith_textview);
        t.setText(old_hadith);


        final SalatTimes latest_salat_times = salat_times.get_salat_times();

        new CountDownTimer(5000, 1000) {

            public void onTick(long millisUntilFinished) {
                String time = "Updating : " + Long.toString(millisUntilFinished);
                Toast.makeText(getApplicationContext(), time,Toast.LENGTH_LONG).show();
                if (latest_salat_times.is_stale()){
                    this.cancel();
                }
            }

            public void onFinish() {
                Toast.makeText(getApplicationContext(), "done",Toast.LENGTH_LONG).show();
                update_salat_times_view(latest_salat_times);
            }
        }.start();

//        update_salat_times_view(latest_salat_times);
    }


    public void update_salat_times_view(SalatTimes latest_salat_times){
        TextView fajr_start_time = findViewById(R.id.fajr_start_time);
        fajr_start_time.setText(latest_salat_times.fajr_athan);

        TextView fajr_iqama_time = findViewById(R.id.fajr_iqama_time);
        fajr_iqama_time.setText(latest_salat_times.fajr_iqama);


        TextView zuhr_start_time = findViewById(R.id.zuhr_start_time);
        zuhr_start_time.setText(latest_salat_times.thuhr_athan);

        TextView zuhr_iqama_time = findViewById(R.id.zuhr_iqama_time);
        zuhr_iqama_time.setText(latest_salat_times.thuhr_iqama);


        TextView asr_start_time = findViewById(R.id.asr_start_time);
        asr_start_time.setText(latest_salat_times.asr_athan);

        TextView asr_iqama_time = findViewById(R.id.asr_iqama_time);
        asr_iqama_time.setText(latest_salat_times.asr_iqama);


        TextView maghrib_start_time = findViewById(R.id.maghrib_start_time);
        maghrib_start_time.setText(latest_salat_times.maghrib_athan);

        TextView maghrib_iqama_time = findViewById(R.id.maghrib_iqama_time);
        maghrib_iqama_time.setText(latest_salat_times.maghrib_iqama);


        TextView isha_start_time = findViewById(R.id.isha_start_time);
        isha_start_time.setText(latest_salat_times.isha_athan);

        TextView isha_iqama_time = findViewById(R.id.isha_iqama_time);
        isha_iqama_time.setText(latest_salat_times.isha_iqama);



        String am_or_pm;
        if (latest_salat_times._am_pm == 0) {
            am_or_pm = "AM";
        } else {
            am_or_pm = "PM";
        }

        String Current_time = Integer.toString(latest_salat_times._month+1) + "/" +
                Integer.toString(latest_salat_times._date) + "/" +
                Integer.toString(latest_salat_times._year) + " at " +
                Integer.toString(latest_salat_times._hour) + ":" +
                Integer.toString(latest_salat_times._minute) + ":" +
                Integer.toString(latest_salat_times._second) + am_or_pm;

        TextView current_time = (TextView) findViewById(R.id.salat_time_updated);
        current_time.setText(Current_time);

        Log.d("DEBUG TIME WHEN UPDATED", Current_time);
    }

    public void update_latest_hadith(Hadith hadith){
        TextView _hadith = findViewById(R.id.hadith_textview);
        Typeface font = Typeface.DEFAULT;

        if (Font_style.equals(Integer.toString(3))){//Integer.toString(0))){// Integer.toString(0)){
            font = Typeface.createFromAsset(getAssets(), "DancingScript-Bold.ttf");
        }
        else if (Font_style.equals(Integer.toString(4))){// == Integer.toString(1)){
            font = Typeface.createFromAsset(getAssets(), "FlyBoyBB.ttf");
        }
        else if (Font_style.equals(Integer.toString(1))){// == Integer.toString(1)){
            font = Typeface.createFromAsset(getAssets(), "Admiration Pains.ttf");
        }
        else if (Font_style.equals(Integer.toString(2))){// == Integer.toString(1)){
            font = Typeface.createFromAsset(getAssets(), "always forever.ttf");
        }
        else if (Font_style.equals(Integer.toString(5))){// == Integer.toString(1)){
            font = Typeface.createFromAsset(getAssets(), "Milton_One.ttf");
        }
        else if(Font_style.equals(Integer.toString(0))){// == Integer.toString(1)){){
            font = Typeface.DEFAULT;
        }


        _hadith.setText("Hadith of the day:\n" + hadith.hadith_title + "   " + hadith.hadith_text + "\n");
        _hadith.setTypeface(font);

        System.out.println(hadith.hadith_title + hadith.hadith_text);
    }

    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();

    }


    public void start_alarm() {
        SalatTimes latest_salat_times = salat_times.get_salat_times();
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000;
        Calendar cal = Calendar.getInstance();

        if(latest_salat_times.fajr_athan == ""){
            Toast.makeText(this, "Fajr_time is empty", Toast.LENGTH_SHORT).show();

        }
//        Log.d("DEBUG", Fajr_time);
        cal.add(Calendar.SECOND, 2);
        cal.add(Calendar.HOUR, 14);

        manager.setRepeating(AlarmManager.RTC, cal.getTimeInMillis(), 1000, pendingIntent);
    }

    public void stop_alarm() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        manager.cancel(pendingIntent);


        Toast.makeText(this, "stopping athan.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(this, DismissBroadcast.class);
        getApplicationContext().sendBroadcast(intent);

        Toast.makeText(this, "You better have gotten up \uD83D\uDE20 ", Toast.LENGTH_SHORT).show();
    }



    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

//            case R.id.menu_refresh:
//
//                // Here we might start a background refresh task
//                return true;

//            case R.id.menu_location:
//                // Here we might call LocationManager.requestLocationUpdates()
//                return true;

            case R.id.menu_settings:
                // To save user preferences
                Intent i = new Intent(this, UserSettingActivity.class);
                startActivityForResult(i, RESULT_SETTINGS);

                Log.d("DEBUG", "onoptionsitemselected");
                // Here we would open up our settings activity
                return true;

            case R.id.saved_hadiths:
                // To save user preferences
                Intent j = new Intent(this, saved_hadiths.class);
                startActivityForResult(j, RESULT_SETTINGS);

                Log.d("DEBUG", "onoptionsitemselected");
                // Here we would open up our settings activity
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Use this method to instantiate your menu, and add your items to it. You
     * should return true if you have added items to it and want the menu to be displayed.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate our menu from the resources by using the menu inflater.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        // It is also possible add items here. Use a generated id from
        // resources (ids.xml) to ensure that all menu ids are distinct.

//        MenuItem locationItem = menu.add(0, R.id.menu_location, 0, R.string.menu_location);
//        locationItem.setIcon(R.drawable.ic_action_location);
//
//        // Need to use MenuItemCompat methods to call any action item related methods
//        MenuItemCompat.setShowAsAction(locationItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        return true;
    }
    // END_INCLUDE(create_menu)

    // BEGIN_INCLUDE(menu_item_selected)
    /**
     * This method is called when one of the menu items to selected. These items
     * can be on the Action Bar, the overflow menu, or the standard options menu. You
     * should return true if you handle the selection.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

//            case R.id.menu_refresh:
//
//                // Here we might start a background refresh task
//                return true;

//            case R.id.menu_location:
//                // Here we might call LocationManager.requestLocationUpdates()
//                return true;

            case R.id.menu_settings:
                // To save user preferences
                Intent i = new Intent(this, UserSettingActivity.class);
                startActivityForResult(i,RESULT_SETTINGS);

                Log.d("DEBUG","onoptionsitemselected");
                // Here we would open up our settings activity
                return true;

            case R.id.saved_hadiths:
                // To save user preferences
                Intent j = new Intent(this, saved_hadiths.class);
                startActivityForResult(j,RESULT_SETTINGS);

                Log.d("DEBUG","onoptionsitemselected");
                // Here we would open up our settings activity
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data)
    {
        super.onActivityResult(requestCode,resultCode,data);
        switch (requestCode) {
            case RESULT_SETTINGS:

                Log.d("DEBUG","onActivitiyResult");
                chooseFontSource();
                showUserSettings();


                break;
        }
    }


    //To save
    public static void SaveInPreference(Context mContext, String key, String objString) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getString(R.string.app_name),
                Context.MODE_PRIVATE).edit();
        editor.putString(key, objString);
        editor.apply();
    }

    public static String getPrefString(Context mContext, final String key, final String defaultStr) {
        SharedPreferences pref = mContext.getSharedPreferences(mContext.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        return pref.getString(key, defaultStr);
    }

    public static Map<String,?> dump_pref_strings(Context mContext) {
        SharedPreferences pref = mContext.getSharedPreferences(mContext.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        return pref.getAll();
    }


    // saving preference stuff
    private void showUserSettings(){

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Log.d("DEBUG", "showUserSettings");

        StringBuilder builder = new StringBuilder();
//
//        builder.append("\n Username: "
//                + sharedPrefs.getString("prefUsername", "NULL"));
//
//        builder.append("\n Send report:"
//                + sharedPrefs.getBoolean("prefSendReport", false));
//
//        builder.append("\n Sync Frequency: "
//                + sharedPrefs.getString("prefHadithSource", "NULL"));
//
        // Taking prefHadithSource value and saving it to global value
        Log.d("DEBUG",sharedPrefs.getString("prefHadithSource", "NULL"));

        // save font number in string format
        Hadith  hadith = salat_times.get_hadith();
        hadith.hadith_source = sharedPrefs.getString("prefHadithSource", "NULL");

        // try to save it now
        Context settings = getApplicationContext();
        SaveInPreference(settings, "prefHadithSource", "NULL");



//
//        TextView settingsTextView = (TextView) findViewById(R.id.textUserSettings);
//
//        settingsTextView.setText(builder.toString());
    }

    private void chooseFontSource(){
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);

        Font_style = sharedPrefs.getString("prefFontSource","NULL");


        // also try to save font style
        Context settings = getApplicationContext();
        SaveInPreference(settings, "prefFontSource", Font_style.toString());



    }


}

