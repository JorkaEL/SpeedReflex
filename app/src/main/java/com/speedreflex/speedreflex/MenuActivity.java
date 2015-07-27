package com.speedreflex.speedreflex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;


public class MenuActivity extends Activity {

    Intent intentVar;
    SpeedReflexView speedDif;
    public int testdifficulter = 0;
    final Context context = this;
    private MatricesDataSource database=null;
    public  MediaPlayer monMedPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
        intentVar = new Intent(this, SpeedReflexActivity.class);
        intentVar.putExtra("difficulter",testdifficulter);

        Button btnStart = (Button) findViewById(R.id.buttonStart);
        Button btnQuitter = (Button) findViewById(R.id.buttonQuit);
        Button btnDificulter = (Button) findViewById(R.id.buttonDifficulte);
        Button btnScore = (Button) findViewById(R.id.buttonScores);

        btnQuitter.setOnClickListener(new ButtonQuitClickListener());
        btnStart.setOnClickListener(new ButtonStartClickListener(btnStart));
        btnDificulter.setOnClickListener(new ButtonDifficulteClickListener(btnDificulter));
        btnScore.setOnClickListener(new ButtonScoreClickListener(btnScore));

        Log.i("onCreate", "CreationBase de donnees");
        database = new MatricesDataSource(context);

        //monMedPlayer = MediaPlayer.create(this.getBaseContext(),R.raw.am)

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
        switch (item.getItemId()) {
            case R.id.help:
                fctHelp();
                return true;
            case R.id.APropos:
                fctAPropos();
                return true;
            default:
                return false;
        }
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

    class ButtonScoreClickListener implements View.OnClickListener {
        Button btn;

        ButtonScoreClickListener(Button button) {
            this.btn = button;
        }

        @Override
        public void onClick(View v) {
            Log.i("Affichier score", "Affichier score");
            database.open();
            fctAffScore(database.fctRecupDatas());
            database.close();

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

    private void fctAPropos() {
        AlertDialog.Builder dialogAPropos = new AlertDialog.Builder(
                context);

        dialogAPropos.setTitle(R.string.TitreBtnAPropos);

        dialogAPropos.setIcon(R.drawable.ic_about);

        TextView txtViewQts = new TextView(context);

        txtViewQts.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        txtViewQts.setPadding(20, 10, 20, 10);
        txtViewQts.setTextColor(Color.WHITE);
        txtViewQts.setTextSize(20);
        txtViewQts
                .setText(Html
                        .fromHtml("<center><small>SpeedReflex</small></center>"
                                + "<br>"
                                + "<br>"
                                + "<center><b>Developpe par :</b></center><br>"
                                + "<small>- Xavier BOUTANQUOY</small><br>"
                                + "<small>Developpe dans le cadre d'un projet personnel</small><br>"
                                ));
        dialogAPropos.setView(txtViewQts);

        dialogAPropos.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialogAPropos
                .setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
        dialogAPropos.show();

    }

    private void fctHelp() {
        AlertDialog.Builder dialogHelp = new AlertDialog.Builder(
                context);

        dialogHelp.setTitle(R.string.TitreBtnHelps);

        dialogHelp.setIcon(R.drawable.ic_help);

        TextView txtViewQts = new TextView(context);

        txtViewQts.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        txtViewQts.setPadding(20, 10, 20, 10);
        txtViewQts.setTextSize(20);
        txtViewQts.setTextColor(Color.WHITE);

        //txtViewQts.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        txtViewQts.setMovementMethod(new ScrollingMovementMethod());

        txtViewQts
                .setText(Html
                        .fromHtml("<b>Objectif :<b><br>" +
                                "<br>" +
                                "<small>Reformer la forme presente en haut à partir de la matrice au centrale</small><br>" +
                                "<br>" +
                                "<b>Rules</b><br>" +
                                "<br>" +
                                "<small>Utilisez la matrice centrale pour deplacer les cases par des mouvements Gauche/Droite ou Haut/Bas</small>"));
        dialogHelp.setView(txtViewQts);

        dialogHelp.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialogHelp
                .setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
        dialogHelp.show();

    }

    private void fctAffScore(List<MatriceData> lstDatas) {
        Log.i("DataBaseActivity", "fctAffScore");
        String strTxt = "";
        int classement = 0;

        for(MatriceData data : lstDatas){
            classement++;
            if(classement==1){
                strTxt =strTxt+ data.toString()+"<br>";
            }else{
                strTxt =strTxt+ data.toString()+"<br>";
            }
        }
        AlertDialog.Builder dialogMessScore = new AlertDialog.Builder(
                context);

        dialogMessScore.setTitle("Tableau des scores");

        TextView txtViewQts = new TextView(context);

        txtViewQts.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT));
        txtViewQts.setPadding(20, 10, 20, 10);
        txtViewQts.setTextSize(20);
        txtViewQts.setTextColor(Color.WHITE);

        txtViewQts.setMovementMethod(new ScrollingMovementMethod());

        txtViewQts
                .setText(Html
                        .fromHtml("<br>" +
                                "<br>" +
                                "<small>" +
                                strTxt +
                                "</small>"));
        dialogMessScore.setView(txtViewQts);

        dialogMessScore.setPositiveButton("OK",
                new android.content.DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        dialogMessScore
                .setOnCancelListener(new android.content.DialogInterface.OnCancelListener() {

                    @Override
                    public void onCancel(DialogInterface dialog) {

                    }
                });
        dialogMessScore.show();

    }

}
