package jsouptutorial.androidbegin.com.jsouptutorial;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.widget.Toast;

import com.androidbegin.jsouptutorial.MainActivity;
import com.androidbegin.jsouptutorial.R;

public class AlarmActivitiy extends Activity {

    AlarmManager alarmManager;
    BroadcastReceiver mReceiver;
    MediaPlayer mediaPlayer = MediaPlayer.create(this, R.raw.adhan);



    ProgressDialog mProgressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

//        Intent intent = getIntent();

        mediaPlayer.seekTo(0);
        mediaPlayer.start();
//
//        mReceiver = new BroadcastReceiver() {
//            // private static final String TAG = "Alarm Example Receiver";
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                Toast.makeText(context, "Alarm time has been reached", Toast.LENGTH_LONG).show();
//
//                // start media player
//                mediaPlayer.seekTo(0);
//                mediaPlayer.start();
//
//
//            }
//        };
//




//        new newDescription().execute();
//        Toast.makeText(this, "Fajr Alarm Set to " , Toast.LENGTH_LONG).show();



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
//
//        registerReceiver(mReceiver, new IntentFilter("sample"));
//        pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent("sample"), 0);
//        alarmManager = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
    }




    // Description AsyncTask
    private class newDescription extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog = new ProgressDialog(AlarmActivitiy.this);
            mProgressDialog.setTitle("Arming Alarm!");
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(false);

            mProgressDialog.show();
        }

        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {

            mProgressDialog.dismiss();
        }
    }

}


