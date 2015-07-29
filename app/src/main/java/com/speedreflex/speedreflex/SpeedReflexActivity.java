package com.speedreflex.speedreflex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

/**
 * Created by xavier on 11/01/15.
 */
public class SpeedReflexActivity extends Activity {

    SpeedReflexView srView;
    public int difficulter;
    public boolean vibration;
    MatricesDataSource database =null;
    Intent intentDataPlayer=null;
    public boolean sound;
    public MediaPlayer monMedPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.speedreflex_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle niveau= this.getIntent().getExtras();
        difficulter=niveau.getInt("difficulter");
        vibration=niveau.getBoolean("vibration");
        sound=niveau.getBoolean("SOUND");


        monMedPlayer = MediaPlayer.create(this.getBaseContext(), R.raw.ambiance);
        monMedPlayer.setLooping(true);
        if (sound){
            monMedPlayer.start();
        }else{
            monMedPlayer.pause();
        }


        intentDataPlayer = new Intent(this,ActivityDatasPlayer.class);

        Log.i("intentData",": "+intentDataPlayer);

        database =new MatricesDataSource(this);

        srView=(SpeedReflexView)findViewById(R.id.SpeedReflexView);
        srView.parentActivity=this;
        Log.i("avt set difficulter:"," "+difficulter);
        srView.setDificulter(difficulter);
        srView.setVibration(vibration);
        srView.setSound(sound);
        if(niveau.getBoolean("NEW")){
            srView.deleteSave();
        }else{
            srView.setContinue(true);
        }
        srView.setIntentDataPlayer(intentDataPlayer);
        //Log.i("apres set difficulter:", " " + difficulter);

        srView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        sound=srView.getSound();
        if(sound){
            monMedPlayer.start();
        }
        srView.initparameters();
        srView.setThread(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //srView.deleteSave();
        Log.i("OnPause ", " SaveGame");
        srView.saveGame();
        srView.setThread(false);

        if (sound) {
            monMedPlayer.pause();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sound) {
            monMedPlayer.pause();
        }
    }



}
