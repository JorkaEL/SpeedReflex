package com.speedreflex.speedreflex;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by xavier on 09/01/15.
 */
public class ActivityDatasPlayer extends Activity {
    //------Variables-------
    String pseudo = "";

    String TABLE = "";
    String score = "";



    MatricesDataSource database = null;
    //----------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        Log.i("ActivityDatasPlayer"," Oncreate");
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.main_datas_player);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button btnValider = (Button) findViewById(R.id.BtnValDataP);
        Button btnAnnuler = (Button) findViewById(R.id.BtnAnnulerDataP);

        TextView txtViewTime = (TextView) findViewById(R.id.textViewScore);


        btnValider.setOnClickListener(new BtnValiderOnClickListener());
        btnAnnuler.setOnClickListener(new BtnAnnulerOnClickListener());

        Bundle extras = getIntent().getExtras();
        System.out.println("Activity Data Player");
        if (extras != null) {
            score  = extras.getString("SCORE");
            TABLE  = extras.getString("DIFFICULTER");
            Log.i("TABLE : "+TABLE," Score : "+score);
            txtViewTime.setText(" SCORE : " + score+ " points");
        }

        database = new MatricesDataSource(this);

    }
    public void fctAddScore(String difficulter,String pseudo, String score) {
        database.addData(difficulter, pseudo, score);
    }

    class BtnValiderOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            EditText txtPseudo = (EditText) findViewById(R.id.editTextPseudo);
            pseudo = txtPseudo.getText().toString();
            if(!pseudo.equals("")){
                fctAddScore(TABLE, pseudo, score);
            }else{
                txtPseudo.setText("Pseudo Empty");
            }
            finish();

        }

    }
    class BtnAnnulerOnClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            //database.dropTableMatrice();
            finish();
        }

    }

}
