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
import java.text.SimpleDateFormat;
import java.util.Date;

import jsouptutorial.androidbegin.com.jsouptutorial.MainActivity;
import jsouptutorial.androidbegin.com.jsouptutorial.R;


/**
 * Created by yasser on 1/31/16.
 */
//public class MyReceiver extends BroadcastReceiver {
public class MyReceiver extends BroadcastReceiver{

    @Override
    public void onReceive( Context context, Intent intent ) {

        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm") ;
        dateFormat.format(date);
        if(date.getHours() < 12){
        MusicControl.getInstance(context).playMusic();
        }
//        System.out.println(dateFormat.format(date));
//
//        if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("12:07")))
//        {
//            System.out.println("Current time is greater than 12.07");
//        }else{
//            System.out.println("Current time is less than 12.07");
//        }

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


