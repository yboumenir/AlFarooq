package jsouptutorial.androidbegin.com.jsouptutorial;

import android.app.AlarmManager;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
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

//        MusicControl.getInstance(context).playMusic();

        Toast.makeText(context, "Athan set ", Toast.LENGTH_LONG).show();

        Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        }else{
            //deprecated in API 26
            v.vibrate(500);
        }
    }
}


