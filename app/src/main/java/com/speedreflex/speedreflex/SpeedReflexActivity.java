package com.speedreflex.speedreflex;

import android.app.Activity;
import android.content.pm.ActivityInfo;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.speedreflex_game);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Bundle niveau= this.getIntent().getExtras();
        difficulter=niveau.getInt("difficulter");

        srView=(SpeedReflexView)findViewById(R.id.SpeedReflexView);
        srView.parentActivity=this;
        Log.i("avt set difficulter:"," "+difficulter);
        srView.setDificulter(difficulter);
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
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
