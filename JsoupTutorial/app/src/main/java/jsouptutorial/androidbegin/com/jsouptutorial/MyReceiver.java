package jsouptutorial.androidbegin.com.jsouptutorial;

import android.app.AlarmManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import jsouptutorial.androidbegin.com.jsouptutorial.MainActivity;
import jsouptutorial.androidbegin.com.jsouptutorial.R;

/**
 * Created by yasser on 1/31/16.
 */
//public class MyReceiver extends BroadcastReceiver {
public class MyReceiver extends BroadcastReceiver {


    @Override
    public void onReceive( Context context, Intent intent ) {

        MediaPlayer mediaPlayer = MediaPlayer.create(context,R.raw.adhan);

        Intent myIntent = new Intent( context, MainActivity.class );

        context.startService( myIntent );

        Toast.makeText(context, "Fajr Alarm Set to ", Toast.LENGTH_LONG).show();

//        Intent intent = getIntent();

        mediaPlayer.seekTo(0);
        mediaPlayer.start();



    }
}

