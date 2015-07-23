package com.speedreflex.speedreflex;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MenuActivity extends Activity {

    Intent intentVar;
    SpeedReflexView speedDif;
    public int testdifficulter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        intentVar = new Intent(this, SpeedReflexActivity.class);
        intentVar.putExtra("difficulter",testdifficulter);

        Button btnStart = (Button) findViewById(R.id.buttonStart);
        Button btnQuitter = (Button) findViewById(R.id.buttonQuit);
        Button btnDificulter = (Button) findViewById(R.id.buttonDifficulte);

        btnQuitter.setOnClickListener(new ButtonQuitClickListener());
        btnStart.setOnClickListener(new ButtonStartClickListener(btnStart));
        btnDificulter.setOnClickListener(new ButtonDifficulteClickListener(btnDificulter));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.resource_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    class ButtonStartClickListener implements View.OnClickListener {
        Button btn;

        ButtonStartClickListener(Button button) {
            this.btn = button;
        }

        @Override
        public void onClick(View v) {
            startActivity(intentVar);
        }
    };

    class ButtonQuitClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    class ButtonDifficulteClickListener implements View.OnClickListener {
        Button btn;

        ButtonDifficulteClickListener(Button button) {
            this.btn = button;
        }

        @Override
        public void onClick(View v) {
            Log.i("bouton difficulter"," : "+testdifficulter);
            if(testdifficulter<2){
                testdifficulter++;
            }else{
                testdifficulter=0;
            }
            Log.i("bouton difficulter"," : "+testdifficulter);

            if (testdifficulter == 0) {
                btn.setText(R.string.bouttonDificulteF);
                intentVar.putExtra("difficulter", testdifficulter);

            } else if(testdifficulter == 1) {
                btn.setText(R.string.bouttonDificulteN);
                intentVar.putExtra("difficulter", testdifficulter);

            } else if(testdifficulter == 2) {
                btn.setText(R.string.bouttonDificulteD);
                intentVar.putExtra("difficulter",testdifficulter);
            }
        }

    };
}
