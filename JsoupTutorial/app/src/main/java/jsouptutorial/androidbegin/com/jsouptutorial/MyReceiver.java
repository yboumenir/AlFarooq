package jsouptutorial.androidbegin.com.jsouptutorial;

import android.app.AlarmManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.Toast;

import java.io.Serializable;

import jsouptutorial.androidbegin.com.jsouptutorial.MainActivity;
import jsouptutorial.androidbegin.com.jsouptutorial.R;

/**
 * Created by yasser on 1/31/16.
 */
//public class MyReceiver extends BroadcastReceiver {
public class MyReceiver extends BroadcastReceiver implements Serializable {

    private int mData;
    MediaPlayer media_player;


    @Override
    public void onReceive( Context context, Intent intent ) {
        media_player = MediaPlayer.create(context,R.raw.adhan);
        Intent myIntent = new Intent( context, MainActivity.class );
        context.startService( myIntent );
        Toast.makeText(context, "Fajr Alarm Set to ", Toast.LENGTH_LONG).show();

        start_athan();
    }

    public void stop_athan(){
        media_player.stop();
        media_player.seekTo(0);
    }

    public void start_athan(){
        media_player.seekTo(0);
        media_player.start();
    }


}

