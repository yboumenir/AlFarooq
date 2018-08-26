package jsouptutorial.androidbegin.com.jsouptutorial;

import android.app.AlarmManager;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Toast;

import java.io.Serializable;

import jsouptutorial.androidbegin.com.jsouptutorial.MainActivity;
import jsouptutorial.androidbegin.com.jsouptutorial.R;


/**
 * Created by yasser on 1/31/16.
 */
//public class MyReceiver extends BroadcastReceiver {
public class MyReceiver extends BroadcastReceiver{

    @Override
    public void onReceive( Context context, Intent intent ) {
        MusicControl.getInstance(context).playMusic();
        Toast.makeText(context, "Athan set ", Toast.LENGTH_LONG).show();
    }
}


