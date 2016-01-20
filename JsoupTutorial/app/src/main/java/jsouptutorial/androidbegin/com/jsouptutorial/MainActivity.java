package com.androidbegin.jsouptutorial;

import java.io.IOException;
        import java.io.InputStream;
        import java.util.Calendar;
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
        import android.graphics.BitmapFactory;
        import android.util.Log;
        import android.view.View;
        import android.view.View.OnClickListener;
        import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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



public class MainActivity extends Activity {

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


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        // Locate the Buttons in activity_main.xml
        Button titlebutton = (Button) findViewById(R.id.titlebutton);
        Button descbutton = (Button) findViewById(R.id.descbutton);
        Button logobutton = (Button) findViewById(R.id.logobutton);

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

        // Restore preferences

        // Fonts for hadith




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



        int hour_current = salat_times.getInt("hour",hour);
        int minute_current =salat_times.getInt("minute", minute);
        int second_current = salat_times.getInt("second", second);
        int Month_current = salat_times.getInt("Month", Month);
        int Date_current = salat_times.getInt("Date", Date);
        int Year_current = salat_times.getInt("Year",Year);
        int AmPm_current = salat_times.getInt("AmPm",AmPm);

        String am_or_pm;
        if (AmPm == 0){
            am_or_pm = "AM";
        }
        else
        {
            am_or_pm = "PM";
        }

        String Current_time = Integer.toString(Month_current) + "/"+ Integer.toString(Date_current) + "/" +Integer.toString(Year_current) + " at " + Integer.toString(hour_current) + ":" + Integer.toString(minute_current) + ":" + Integer.toString(second_current) +am_or_pm;

        TextView current_time = (TextView) findViewById(R.id.salat_time_updated);
        current_time.setText(Current_time);

        mediaPlayer= MediaPlayer.create(this,R.raw.adhan);
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

    }

    //To save
    public static void SaveInPreference(Context mContext, String key, String objString) {
        SharedPreferences.Editor editor = mContext.getSharedPreferences(mContext.getString(R.string.app_name),
                Context.MODE_PRIVATE).edit();
        editor.putString(key, objString);
        editor.commit();
    }

    public static String getPrefString(Context mContext, final String key, final String defaultStr) {
        SharedPreferences pref = mContext.getSharedPreferences(mContext.getString(R.string.app_name),
                Context.MODE_PRIVATE);
        return pref.getString(key, defaultStr);
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
                System.out.println("Title: "+ Title);
                // Get the table of data from the url
                Elements tbody = doc.select("tbody");
                // print it out
                System.out.println("tbody.first: "+ tbody.first());

                // get the table of salat times
                Elements tr = tbody.select("tr");
                // from the table, we get the first element
                Elements Fajr = tr.eq(0);
                // now we get the time
                Fajr_time= Fajr.select("td").eq(1).text();
                Fajr_time_iqama =Fajr.select("td").eq(2).text();

                Elements Zuhr = tr.eq(1);
                Zuhr_time = Zuhr.select("td").eq(1).text();
                Zuhr_time_iqama = Zuhr.select("td").eq(2).text();
                Elements Asr = tr.eq(2);
                Asr_time=Asr.select("td").eq(1).text();
                Asr_time_iqama =Asr.select("td").eq(2).text();
                Elements Maghrib = tr.eq(3);
                Maghrib_time = Maghrib.select("td").eq(1).text();
                Maghrib_time_iqama = Maghrib.select("td").eq(2).text();
                Elements Isha = tr.eq(4);
                Isha_time = Isha.select("td").eq(1).text();
                Isha_time_iqama =Isha.select("td").eq(2).text();

                title = Fajr_time+"," +Zuhr_time +","+Asr_time+","+Maghrib_time+","+Isha_time;

                // getting hadith

                Random hadith_book = new Random();
                int hadith_book_number = hadith_book.nextInt(97);

                String hadith_url = "http://sunnah.com/bukhari/" + String.valueOf(hadith_book_number);
                // Fetch the html webpage
                Document hadith_doc = Jsoup.connect(hadith_url).get();
                // get the title of the url
                String title = hadith_doc.title();
                // print it out
                System.out.println("Title: "+ title);
                // Get the table of data from the url
                Elements hadith_body = hadith_doc.select("div");
                // print it out
                Elements hadith_classes = hadith_doc.getElementsByClass("hadith_narrated");

                // generate random number to pick up hadith.

                Random hadith_number_random = new Random();
                // hadith_number_random.nextInt(7);

                int hadith_number = (hadith_number_random.nextInt(7));

                // Hadith title

                //int hadith_number =1;
                hadith_title = hadith_classes.eq(hadith_number).eq(0).eq(0).text();
                //System.out.println("hadith 1:  " + hadith_title);

                Elements hadith_texts = hadith_doc.getElementsByClass("text_details");

                hadith_text = hadith_texts.eq(hadith_number).eq(0).eq(0).text();


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
            if (AmPm == 0){
                am_or_pm = "AM";
            }
            else
            {
                am_or_pm = "PM";
            }

            String Current_time = Integer.toString(Month) + "/"+ Integer.toString(Date) + "/" +Integer.toString(Year) + " at " + Integer.toString(hour) + ":" + Integer.toString(minute) + ":" + Integer.toString(second) +am_or_pm;

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
            editor.putString("fajr_start_time",Fajr_time);
            editor.putString("fajr_iqama_time",Fajr_time_iqama);
            editor.putString("zuhr_start_time",Zuhr_time);
            editor.putString("zuhr_iqama_time",Zuhr_time_iqama);
            editor.putString("asr_start_time",Asr_time);
            editor.putString("asr_iqama_time",Asr_time_iqama);
            editor.putString("maghrib_start_time",Maghrib_time);
            editor.putString("maghrib_iqama_time",Maghrib_time_iqama);
            editor.putString("isha_start_time",Isha_time);
            editor.putString("isha_iqama_time",Isha_time_iqama);

            // For the times

            editor.putInt("hour",hour);
            editor.putInt("minute",minute);
            editor.putInt("second",second);
            editor.putInt("Month",Month);
            editor.putInt("Date",Date);
            editor.putInt("Year",Year);
            editor.putInt("AmPm", AmPm);

            editor.apply();






            // Time to get hadiths
            TextView hadith = (TextView) findViewById(R.id.hadith_textview);
            Typeface font = Typeface.createFromAsset(getAssets(), "DancingScript-Bold.ttf");
            hadith.setText("Hadith of the day:\n" + hadith_title + "   " + hadith_text + "\n");
            hadith.setTypeface(font);

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


            // get fajr alarm
            SharedPreferences salat_times = getApplicationContext().getSharedPreferences(PREFS_NAME, 0);
            String Fajr_start_times = salat_times.getString("fajr_start_time", Fajr_time);
            // take the hour
            int Fajr_Hour = Integer.parseInt(Character.toString(Fajr_start_times.charAt(0)));
            int Fajr_Minutes = Integer.parseInt(Character.toString(Fajr_start_times.charAt(2)));
            Fajr_Minutes = Fajr_Minutes*10 + Integer.parseInt(Character.toString(Fajr_start_times.charAt(3)));

            //Fajr_Minutes = 57;

            int result = Fajr_Hour*60*60*1000+ Fajr_Minutes*60*1000;

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), result, pendingIntent);

            Toast.makeText(context, "Fajr Alarm Set to " + Fajr_Hour + ":" +Fajr_Minutes, Toast.LENGTH_LONG).show();
















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

    private void RegisterAlarmBroadcast()
    {
        mReceiver = new BroadcastReceiver()
        {
            // private static final String TAG = "Alarm Example Receiver";
            @Override
            public void onReceive(Context context, Intent intent)
            {
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
        pendingIntent = PendingIntent.getBroadcast( this, 0, new Intent("sample"),0 );
        alarmManager =(AlarmManager)(this.getSystemService( Context.ALARM_SERVICE ));
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

            alarmManager.cancel(pendingIntent);
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

}