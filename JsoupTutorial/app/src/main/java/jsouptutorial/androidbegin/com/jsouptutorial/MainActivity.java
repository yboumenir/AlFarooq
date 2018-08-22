package jsouptutorial.androidbegin.com.jsouptutorial;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Map;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.RadialGradient;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewDebug;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;


// to save using sharedprefreferences
import android.content.SharedPreferences;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.widget.Toast;

// from Alarm class
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;


import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


// media player

import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

// http://viralpatel.net/blogs/android-preferences-activity-example/
//  saving user preference stuff

import android.preference.PreferenceActivity;

import jsouptutorial.androidbegin.com.jsouptutorial.AlarmActivitiy;
import jsouptutorial.androidbegin.com.jsouptutorial.UserSettingActivity;
import jsouptutorial.androidbegin.com.jsouptutorial.saved_hadiths;

public class MainActivity extends Activity implements PopupMenu.OnMenuItemClickListener{

    // saving preference stuff
    private static final int RESULT_SETTINGS = 1;

    Context context;

    // URL Address
    String url = "http://alfarooqmasjid.org";
    ProgressDialog mProgressDialog;


    // to save the salat times data
    public static final String PREFS_NAME = "MyPrefsFile";

    // Global variables to save the times
    String Fajr_time;
    String Zuhr_time;
    String Asr_time;
    String Maghrib_time;
    String Isha_time;

    String Fajr_time_iqama;
    String Zuhr_time_iqama;
    String Asr_time_iqama;
    String Maghrib_time_iqama;
    String Isha_time_iqama;

    String hadith_title;
    String hadith_text;

    // For the current time
    int hour;
    int minute;
    int second;
    int Month;
    int Date;
    int Year;
    int AmPm;


    // for alarm

    //Media player for alarm
    MediaPlayer mediaPlayer;


    PendingIntent pendingIntent;
    AlarmManager alarmManager;
    BroadcastReceiver mReceiver;


    // String for font style (default)
    String Font_style = "3";

    // String for hadith source (default)
    String Hadith_source = Integer.toString(1);

    String hadith_url = "http://sunnah.com/bukhari/"; // + String.valueOf(hadith_book_number);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);


        // Locate the Buttons in activity_main.xml
        Button titlebutton = (Button) findViewById(R.id.titlebutton);
        Button descbutton = (Button) findViewById(R.id.descbutton);
        Button logobutton = (Button) findViewById(R.id.logobutton);
        final Button save_hadith_button = (Button) findViewById(R.id.button_save_hadith);

        // Capture button click
        titlebutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute Title AsyncTask
                new Title().execute();
            }
        });

        // Set fajr alarm
        descbutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute Description AsyncTask

                new Description().execute();
            }
        });

        // Capture button click
        logobutton.setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
                // Execute Logo AsyncTask
                new Logo().execute();
            }
        });

        save_hadith_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s;
                if (hadith_text == null){
                    s="Oops no Hadith";
                }
                else{
                    s ="Saving Hadith:\n" + hadith_title + "\n" + hadith_text;
                    SaveInPreference(getApplicationContext(), hadith_url, hadith_text);
                    SaveInPreference(getApplicationContext(), "last_saved_hadith_title", hadith_title);
                    SaveInPreference(getApplicationContext(), "last_saved_hadith_text", hadith_text);
                }
                Toast.makeText(getApplicationContext(), s,Toast.LENGTH_LONG).show();
            }
        });

        // Restore preferences
        // upload last saved hadith
        String old_hadith = getPrefString(getApplicationContext(), "last_saved_hadith_text","");
        TextView t = (TextView) findViewById(R.id.hadith_textview);
        t.setText(old_hadith);


        //SharedPreferences salat_times = getPreferences(0);
        // String Fajr_times = salat_times.getString("fajr_start_time", fajr);

        // to save a value
        //SharedPreferences fajr_time_save = getPreferences(0);
        //SharedPreferences.Editor fajr_editor = fajr_time_save.edit().putString("fajr_start_time","1");


        SharedPreferences salat_times = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
       /*
        SharedPreferences.Editor editor = salat_times.edit();
        editor.putString("fajr_start_time","5");
        editor.apply();
*/

        String Fajr_start_times = salat_times.getString("fajr_start_time", Fajr_time);
        String Fajr_iqama_times = salat_times.getString("fajr_iqama_time", Fajr_time_iqama);

        String Zuhr_start_times = salat_times.getString("zuhr_start_time", Fajr_time);
        String Zuhr_iqama_times = salat_times.getString("zuhr_iqama_time", Fajr_time_iqama);

        String Asr_start_times = salat_times.getString("asr_start_time", Fajr_time);
        String Asr_iqama_times = salat_times.getString("asr_iqama_time", Fajr_time_iqama);

        String Maghrib_start_times = salat_times.getString("maghrib_start_time", Fajr_time);
        String Maghrib_iqama_times = salat_times.getString("maghrib_iqama_time", Fajr_time_iqama);

        String Isha_start_times = salat_times.getString("isha_start_time", Fajr_time);
        String Isha_iqama_times = salat_times.getString("isha_iqama_time", Fajr_time_iqama);


        // Now we update the textvies in activity_main

        TextView fajr_start_time = (TextView) findViewById(R.id.fajr_start_time);
        fajr_start_time.setText(Fajr_start_times);

        TextView fajr_iqama_time = (TextView) findViewById(R.id.fajr_iqama_time);
        fajr_iqama_time.setText(Fajr_iqama_times);


        TextView zuhr_start_time = (TextView) findViewById(R.id.zuhr_start_time);
        zuhr_start_time.setText(Zuhr_start_times);

        TextView zuhr_iqama_time = (TextView) findViewById(R.id.zuhr_iqama_time);
        zuhr_iqama_time.setText(Zuhr_iqama_times);


        TextView asr_start_time = (TextView) findViewById(R.id.asr_start_time);
        asr_start_time.setText(Asr_start_times);

        TextView asr_iqama_time = (TextView) findViewById(R.id.asr_iqama_time);
        asr_iqama_time.setText(Asr_iqama_times);


        TextView maghrib_start_time = (TextView) findViewById(R.id.maghrib_start_time);
        maghrib_start_time.setText(Maghrib_start_times);

        TextView maghrib_iqama_time = (TextView) findViewById(R.id.maghrib_iqama_time);
        maghrib_iqama_time.setText(Maghrib_iqama_times);


        TextView isha_start_time = (TextView) findViewById(R.id.isha_start_time);
        isha_start_time.setText(Isha_start_times);

        TextView isha_iqama_time = (TextView) findViewById(R.id.isha_iqama_time);
        isha_iqama_time.setText(Isha_iqama_times);


        int hour_current = salat_times.getInt("hour", hour);
        int minute_current = salat_times.getInt("minute", minute);
        int second_current = salat_times.getInt("second", second);
        int Month_current = salat_times.getInt("Month", Month);
        int Date_current = salat_times.getInt("Date", Date);
        int Year_current = salat_times.getInt("Year", Year);
        int AmPm_current = salat_times.getInt("AmPm", AmPm);

        String am_or_pm;
        if (AmPm == 0) {
            am_or_pm = "AM";
        } else {
            am_or_pm = "PM";
        }

        String Current_time = Integer.toString(Month_current+1) + "/" + Integer.toString(Date_current) + "/" + Integer.toString(Year_current) + " at " + Integer.toString(hour_current) + ":" + Integer.toString(minute_current) + ":" + Integer.toString(second_current) + am_or_pm;

        TextView current_time = (TextView) findViewById(R.id.salat_time_updated);
        current_time.setText(Current_time);

        mediaPlayer = MediaPlayer.create(this, R.raw.adhan);
        RegisterAlarmBroadcast();

        //alarm.SetAlarm(this);




        /*

        To save a value in using sharedpreferences:

        SharedPreferences pref = this.getSharedPreferences("Test",0);
                Editor editor = pref.edit();
        editor.putString("VALUE", value);
                editor.commit();

        And get it like that:

        SharedPreferences prfs = getSharedPreferences("Test", Context.MODE_PRIVATE);
                String v= prfs.getString("VALUE", "");


         */


        // Using Elements to get the Meta data
        // Elements description = document.select("meta[name=description]");
        //Elements description = document.select("<tbody>");
        // Locate the content attribute
        //desc = description.attr("<td>");
        //SaveInPreference(this,"fajr_start_time","100");
        //getPrefString(this,fajr,"fajr_start_time");

        // preference stuff...?
        // showUserSettings();
    }


    public void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.menu_main, popup.getMenu());
        popup.show();


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

    // Title AsyncTask
    private class Title extends AsyncTask<Void, Void, Void> {
        String title;


        //Context settings = getApplicationContext();
        //SaveInPreference(this,"fajr_start_time","100");

        /*
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("homeScore", YOUR_HOME_SCORE);

// Apply the edits!
            editor.apply();

// Get from the SharedPreferences
            SharedPreferences settings = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            int homeScore = settings.getInt("homeScore", 0);
*/

        //fajr_editor.putString("fajr_start_time","1");
        //fajr_editor.commit();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Fetching Salat Times Now");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String TAG = "Getting Title";
            try {
                // Url of alfarooq website
                String url = "http://alfarooqmasjid.org";
                // Fetch the html webpage
                Document doc = Jsoup.connect(url).get();
                // get the title of the url
                String Title = doc.title();
                // print it out
                System.out.println("Title: " + Title);
                // Get the table of data from the url
                Elements tbody = doc.select("tbody");
                // print it out
                System.out.println("tbody.first: " + tbody.first());

                // get the table of salat times
                Elements tr = tbody.select("tr");
                // from the table, we get the first element
                Elements Fajr = tr.eq(0);
                // now we get the time
                Fajr_time = Fajr.select("td").eq(1).text();
                Fajr_time_iqama = Fajr.select("td").eq(2).text();

                Elements Zuhr = tr.eq(1);
                Zuhr_time = Zuhr.select("td").eq(1).text();
                Zuhr_time_iqama = Zuhr.select("td").eq(2).text();
                Elements Asr = tr.eq(2);
                Asr_time = Asr.select("td").eq(1).text();
                Asr_time_iqama = Asr.select("td").eq(2).text();
                Elements Maghrib = tr.eq(3);
                Maghrib_time = Maghrib.select("td").eq(1).text();
                Maghrib_time_iqama = Maghrib.select("td").eq(2).text();
                Elements Isha = tr.eq(4);
                Isha_time = Isha.select("td").eq(1).text();
                Isha_time_iqama = Isha.select("td").eq(2).text();

                title = Fajr_time + "," + Zuhr_time + "," + Asr_time + "," + Maghrib_time + "," + Isha_time;

                // getting hadith

                // problem: some book volumes have different number of chapters, and different volumes.
                // to find number of volumes -> search for book_number
                // to find number of chapters -> search for echapno
                // then generate random number of bounded by max size of volume and chapter number.

                // so first find website
                // next find maxs
                // then generate bounded random number
                // then finally display

                // alternative is to look at each volume, then chapter to obtain list of maximum bounds


//                Random hadith_book = new Random();
//                int hadith_book_number = hadith_book.nextInt(97);

//                String hadith_url = "http://sunnah.com/bukhari/"; // + String.valueOf(hadith_book_number);


                // problem: when app is run before configuring setting, it will crash here
                // so we will try to catch it first

                try {
                    Log.e("DEBUG_Hadith_source",Hadith_source);

                    // Fetch the html webpage using one of the below strings:
                    if (Hadith_source.equals(Integer.toString(1))) {
                        hadith_url = "http://sunnah.com/bukhari/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(2))) {
                        hadith_url = "http://sunnah.com/muslim/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(3))) {
                        hadith_url = "http://sunnah.com/nasai/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(4))) {
                        hadith_url = "http://sunnah.com/abudawud/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(5))) {
                        hadith_url = "http://sunnah.com/tirmidhi/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(6))) {
                        hadith_url = "http://sunnah.com/ibnmajah/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(7))) {
                        hadith_url = "http://sunnah.com/malik/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(8))) {
                        hadith_url = "http://sunnah.com/nawawi40/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(9))) {
                        hadith_url = "http://sunnah.com/riyadussaliheen/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(10))) {
                        hadith_url = "http://sunnah.com/adab/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(11))) {
                        hadith_url = "http://sunnah.com/qudsi40/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(12))) {
                        hadith_url = "http://sunnah.com/shamail/";// + String.valueOf(hadith_book_number);
                    } else if (Hadith_source.equals(Integer.toString(13))) {
                        hadith_url = "http://sunnah.com/bulugh/";// + String.valueOf(hadith_book_number);
                    }
                } catch (Exception e){
                    e.printStackTrace();
                    Log.d(TAG, "... Failed");

                    //throw new RuntimeException(e);
                }

                // debug statement to see how many chapters we have
//                String debug_hadith_url = "http://sunnah.com/bukhari/";

                // lazy me
                String debug_hadith_url = hadith_url;
                Document debug_hadith_doc = Jsoup.connect(debug_hadith_url).get();


                Elements book_num_classes = debug_hadith_doc.getElementsByClass("book_number");


                // Now get size of class -> bound of max random number
                int max_book_size = book_num_classes.size();

                // debug output
                Log.e("DEBUG_max_book_size",Integer.toString(max_book_size));


                Random hadith_book = new Random();

                // now we generate the random number for the hadith volume
                // sometimes we get a hadith_book_number = 0, which doesn't exist
                int hadith_book_number = hadith_book.nextInt(max_book_size - 1) + 1;

                // debug output
                Log.e("DEBUG_hadith_book_num", Integer.toString(hadith_book_number));


                // now we regenerate url with new book volume number

                debug_hadith_url = debug_hadith_url + Integer.toString(hadith_book_number);
                hadith_url = debug_hadith_url;
                // debug output
                Log.e("DEBUG_url",debug_hadith_url);


                // now we look for echapno for chapter number
                // first reload document with chapter
                debug_hadith_doc = Jsoup.connect(debug_hadith_url).get();


                Elements chapeter_numbers = debug_hadith_doc.getElementsByClass("echapno");
                int max_chapter_size = chapeter_numbers.size();

                // debug output
                Log.e("DEBUG_max_chapter_size",Integer.toString(max_chapter_size));


                Random chapter_number = new Random();
                int chapter_number_selected = chapter_number.nextInt(max_chapter_size - 1) + 1;

                // debug output
                Log.e("DEBUG_chapter_number_se",Integer.toString(chapter_number_selected));

                // final update of hadith url
                debug_hadith_url+= "/" + Integer.toString(chapter_number_selected);

                // debug output
                Log.e("DEBUG_hadith_url",debug_hadith_url);


//
//                String book_num =hadith_doc.getElementsByClass("book_number").text();
//                Log.e("DEBUG",book_num);

                // generate random number to pick up hadith.

//                Random hadith_number_random = new Random();
                // hadith_number_random.nextInt(7);

//                int hadith_number = (hadith_number_random.nextInt(7));

                // Hadith title

                //int hadith_number =1;
//                hadith_title = hadith_classes.eq(hadith_number).eq(0).eq(0).text();


                Document hadith_doc = Jsoup.connect(debug_hadith_url).get();
                // get the title of the url
                String title = hadith_doc.title();
                // print it out
                System.out.println("Title: " + title);
                // Get the table of data from the url
                Elements hadith_body = hadith_doc.select("div");
                // print it out
                Elements hadith_classes = hadith_doc.getElementsByClass("hadith_narrated");

                Log.e("DEBUG_hadith_classes",hadith_classes.toString());


                hadith_title = hadith_classes.text();
                Log.e("DEBUG_hadith_title",hadith_title);



//                hadith_title = hadith_classes.eq(chapter_number_selected).eq(0).eq(0).text();

                //System.out.println("hadith 1:  " + hadith_title);


                Elements hadith_texts = hadith_doc.getElementsByClass("text_details");

                hadith_text = hadith_texts.text();



//                Log.e("DEBUG_hadith_doc",hadith_doc.toString());

                Log.e("DEBUG_hadith_text",hadith_text.toString());

//                hadith_text = hadith_texts.eq(chapter_number_selected).eq(0).eq(0).text();


            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "... Failed");
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Get current time. should match when we updated salat times
            Calendar c = Calendar.getInstance();
            hour = c.get(Calendar.HOUR);
            minute = c.get(Calendar.MINUTE);
            second = c.get(Calendar.SECOND);
            Month = c.get(Calendar.MONTH);
            Date = c.get(Calendar.DATE);
            Year = c.get(Calendar.YEAR);
            AmPm = c.get(Calendar.AM_PM);

            String am_or_pm;
            if (AmPm == 0) {
                am_or_pm = "AM";
            } else {
                am_or_pm = "PM";
            }

            String Current_time = Integer.toString(Month + 1) + "/" + Integer.toString(Date) + "/" + Integer.toString(Year) + " at " + Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second) + am_or_pm;

            TextView current_time = (TextView) findViewById(R.id.salat_time_updated);
            current_time.setText(Current_time);


            TextView fajr_start_time = (TextView) findViewById(R.id.fajr_start_time);
            fajr_start_time.setText(Fajr_time);

            TextView fajr_iqama_time = (TextView) findViewById(R.id.fajr_iqama_time);
            fajr_iqama_time.setText(Fajr_time_iqama);

            TextView zuhr_start_time = (TextView) findViewById(R.id.zuhr_start_time);
            zuhr_start_time.setText(Zuhr_time);

            TextView zuhr_iqama_time = (TextView) findViewById(R.id.zuhr_iqama_time);
            zuhr_iqama_time.setText(Zuhr_time);

            TextView asr_start_time = (TextView) findViewById(R.id.asr_start_time);
            asr_start_time.setText(Asr_time);

            TextView asr_iqama_time = (TextView) findViewById(R.id.asr_iqama_time);
            asr_iqama_time.setText(Asr_time_iqama);

            TextView maghrib_start_time = (TextView) findViewById(R.id.maghrib_start_time);
            maghrib_start_time.setText(Maghrib_time);

            TextView maghrib_iqama_time = (TextView) findViewById(R.id.maghrib_iqama_time);
            maghrib_iqama_time.setText(Maghrib_time_iqama);

            TextView isha_start_time = (TextView) findViewById(R.id.isha_start_time);
            isha_start_time.setText(Isha_time);

            TextView isha_iqama_time = (TextView) findViewById(R.id.isha_iqama_time);
            isha_iqama_time.setText(Isha_time_iqama);

            Context settings = getApplicationContext();
            SaveInPreference(settings, "fajr_start_time", "100");

            // Now we will save the times...
            SharedPreferences salat_times = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            SharedPreferences.Editor editor = salat_times.edit();
            editor.putString("fajr_start_time", Fajr_time);
            editor.putString("fajr_iqama_time", Fajr_time_iqama);
            editor.putString("zuhr_start_time", Zuhr_time);
            editor.putString("zuhr_iqama_time", Zuhr_time_iqama);
            editor.putString("asr_start_time", Asr_time);
            editor.putString("asr_iqama_time", Asr_time_iqama);
            editor.putString("maghrib_start_time", Maghrib_time);
            editor.putString("maghrib_iqama_time", Maghrib_time_iqama);
            editor.putString("isha_start_time", Isha_time);
            editor.putString("isha_iqama_time", Isha_time_iqama);

            // For the times

            editor.putInt("hour", hour);
            editor.putInt("minute", minute);
            editor.putInt("second", second);
            editor.putInt("Month", Month);
            editor.putInt("Date", Date);
            editor.putInt("Year", Year);
            editor.putInt("AmPm", AmPm);

            // for user settings on font style and hadith source

            editor.putString("saved_font_style",Font_style);
            editor.putString("saved_hadith_source",Hadith_source);

            editor.apply();


            // Time to get hadiths
            TextView hadith = (TextView) findViewById(R.id.hadith_textview);

//            Typeface font = Typeface.createFromAsset(getAssets(), "DancingScript-Bold.ttf");

            if (Font_style.equals(Integer.toString(3))){//Integer.toString(0))){// Integer.toString(0)){
                Typeface font = Typeface.createFromAsset(getAssets(), "DancingScript-Bold.ttf");
                hadith.setText("Hadith of the day:\n" + hadith_title + "   " + hadith_text + "\n");
                hadith.setTypeface(font);

            }
            else if (Font_style.equals(Integer.toString(4))){// == Integer.toString(1)){
                Typeface font = Typeface.createFromAsset(getAssets(), "FlyBoyBB.ttf");
                hadith.setText("Hadith of the day:\n" + hadith_title + "   " + hadith_text + "\n");
                hadith.setTypeface(font);
            }
            else if (Font_style.equals(Integer.toString(1))){// == Integer.toString(1)){
                Typeface font = Typeface.createFromAsset(getAssets(), "Admiration Pains.ttf");
                hadith.setText("Hadith of the day:\n" + hadith_title + "   " + hadith_text + "\n");
                hadith.setTypeface(font);
            }
            else if (Font_style.equals(Integer.toString(2))){// == Integer.toString(1)){
                Typeface font = Typeface.createFromAsset(getAssets(), "always forever.ttf");
                hadith.setText("Hadith of the day:\n" + hadith_title + "   " + hadith_text + "\n");
                hadith.setTypeface(font);
            }
            else if (Font_style.equals(Integer.toString(5))){// == Integer.toString(1)){
                Typeface font = Typeface.createFromAsset(getAssets(), "Milton_One.ttf");
                hadith.setText("Hadith of the day:\n" + hadith_title + "   " + hadith_text + "\n");
                hadith.setTypeface(font);
            }




//            hadith.setText("Hadith of the day:\n" + hadith_title + "   " + hadith_text + "\n");
//            hadith.setTypeface(font);

            System.out.println(hadith_title + hadith_text);


            mProgressDialog.dismiss();
        }
    }

    // Description AsyncTask
    private class Description extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Arming Alarm!");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);

            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {

//            AlarmManager alarmMgr;
//            PendingIntent alarmIntent;
//
//
//            alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
////            Intent intent = new Intent(context, RegisterAlarmBroadcast);
//            Intent intent = new Intent("broadcast_alarm");
//
//            alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//
//
//            // get fajr alarm
//            SharedPreferences salat_times = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
//            String Fajr_start_times = salat_times.getString("fajr_start_time", Fajr_time);
//
//
//            Log.e("DEBUG_fajr_start", Fajr_start_times);
//
//            // take the hour
//            int Fajr_Hour = Integer.parseInt(Character.toString(Fajr_start_times.charAt(0)));
//            int Fajr_Minutes = Integer.parseInt(Character.toString(Fajr_start_times.charAt(2)));
//            Fajr_Minutes = Fajr_Minutes * 10 + Integer.parseInt(Character.toString(Fajr_start_times.charAt(3)));
//
//            Log.e("DEBUG_fajr_Hour", Integer.toString(Fajr_Hour));
//            Log.e("DEBUG_fajr_Minutes",Integer.toString(Fajr_Minutes));
//
//
//
//            int result = Fajr_Hour * 60 * 60 * 1000 + Fajr_Minutes * 60 * 1000;
//
//            Log.e("DEBUG_fajr_result",Integer.toString(result));
//
//
//            alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, result, AlarmManager.INTERVAL_DAY, alarmIntent);
//
//
//            Toast.makeText(context, "Fajr Alarm Set to " + Fajr_Hour + ":" + Fajr_Minutes, Toast.LENGTH_LONG).show();



//
            String alarm = Context.ALARM_SERVICE;
//            alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
            AlarmManager am = ( AlarmManager ) context.getSystemService(Context.ALARM_SERVICE);

            Intent intent = //new Intent( "REFRESH_THIS" );



            new Intent(context,AlarmActivitiy.class);
            startActivity(intent);



//            Intent intent = new Intent(context, AlarmActivitiy.class);


//
//
//            PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0 );
//
//            int type = AlarmManager.ELAPSED_REALTIME_WAKEUP;
////            long interval = AlarmManager.INTERVAL_FIFTEEN_MINUTES;
//            long interval = 1000;
//            long triggerTime = SystemClock.elapsedRealtime() + interval;
//
//            am.setRepeating(type, triggerTime, interval, pi);
//












                /*
                AlarmManager alarmMgr;
                PendingIntent alarmIntent;

                alarmMgr = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), Alarm.class);
                alarmIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0);

                // Set the alarm to start at 8:30 a.m.
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY, 8);
                calendar.set(Calendar.MINUTE, 30);
                */
            // Connect to the web site
            //Document document = Jsoup.connect(url).get();
            // Using Elements to get the Meta data
            // Elements description = document.select("meta[name=description]");
            //Elements description = document.select("<tbody>");
            // Locate the content attribute
            //desc = description.attr("<td>");


            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set description into TextView
            //TextView txtdesc = (TextView) findViewById(R.id.desctxt);
            //txtdesc.setText(desc);

            mProgressDialog.dismiss();
        }
    }

    private void RegisterAlarmBroadcast() {
        mReceiver = new BroadcastReceiver() {
            // private static final String TAG = "Alarm Example Receiver";
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context, "Alarm time has been reached", Toast.LENGTH_LONG).show();

                // start media player
                mediaPlayer.seekTo(0);
                mediaPlayer.start();


            }
        };


        // intent = new Intent(getBaseContext(),MainActivity.class);
        //pendingIntent = PendingIntent.getBroadcast( this, 0, intent,0 );

        //Intent intent = new Intent();
        //registerReceiver(mReceiver, new IntentFilter("intent"));
        //pendingIntent = PendingIntent.getBroadcast( this, 0, intent,0 );

        registerReceiver(mReceiver, new IntentFilter("sample"));
        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("sample"), 0);
        alarmManager = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
    }

    // Logo AsyncTask
    private class Logo extends AsyncTask<Void, Void, Void> {
        Bitmap bitmap;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(MainActivity.this);
            mProgressDialog.setTitle("Disarming Alarm");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {


            // alarmManager.cancel(pendingIntent);



            // Do not call this function...
            // getBaseContext().unregisterReceiver(mReceiver);
            mediaPlayer.pause();


            //alarm.CancelAlarm(getApplicationContext());
            /*
            try {
                // Connect to the web site
                Document document = Jsoup.connect(url).get();
                // Using Elements to get the class data
                Elements img = document.select("a[class=brand brand-image] img[src]");
                // Locate the src attribute
                String imgSrc = img.attr("src");
                // Download image from URL
                InputStream input = new java.net.URL(imgSrc).openStream();
                // Decode Bitmap
                bitmap = BitmapFactory.decodeStream(input);

            } catch (IOException e) {
                e.printStackTrace();
            }
            */
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            // Set downloaded image into ImageView
            /*
            ImageView logoimg = (ImageView) findViewById(R.id.logo);
            logoimg.setImageBitmap(bitmap);
            */
            mProgressDialog.dismiss();
        }
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
        Hadith_source = sharedPrefs.getString("prefHadithSource", "NULL");

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
        SaveInPreference(settings, "prefFontSource", "NULL");



    }


}

