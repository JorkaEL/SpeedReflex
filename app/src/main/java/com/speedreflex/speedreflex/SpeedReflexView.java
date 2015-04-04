package com.speedreflex.speedreflex;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;

/**
 * Created by xavier on 11/01/15.
 */
public class SpeedReflexView extends SurfaceView implements View.OnClickListener,
        View.OnTouchListener, SurfaceHolder.Callback, Runnable {

    public Activity parentActivity;

    public Elements voiture = new Elements(0,"Voiture", Color.rgb(255,0,0));
    public Elements ourse = new Elements(1,"Ourse", Color.rgb(88, 41, 0));
    public Elements bonbon = new Elements(2,"Bonbon", Color.rgb(253, 108, 158));
    public Elements lunette = new Elements(3,"Lunette", Color.rgb(0, 255, 0));
    public Elements telephone = new Elements(4,"Telephone", Color.rgb(0, 0, 0));

    private Bitmap voitureB;
    private Bitmap ourseB;
    private Bitmap bonbonB;
    private Bitmap lunetteB;
    private Bitmap telephoneB;

    //public  Elements tabEl []={voiture,ourse,bonbon,lunette,telephone};

    private Resources speedReflexRes;
    private Context speedReflexcontext;

    private boolean in = true;
    private Thread cv_thread;
    SurfaceHolder holder;
    private Random rd;

    public int height;
    public int width;





    public SpeedReflexView(Context context,AttributeSet attrs) {
        super(context, attrs);

        Log.i(">>> Projet", "SpeedReflexView");

        holder = getHolder();// Recuperation du Holder
        holder.addCallback(this);// Ajout

        speedReflexcontext = context;
        Log.i(">>> Projet", "SpeedReflexView 1 ");

        speedReflexRes = speedReflexcontext.getResources();
        Log.i(">>> loadimages", "SpeedReflexView 2 ");

        loadimages(speedReflexRes);
        Log.i(">>> Projet", "SpeedReflexView 3 ");

        //rd = new Random();
        cv_thread = new Thread(this);
        setFocusable(true);
        setOnClickListener(this);


    }

    private void loadimages(Resources speedReflexRes) {
        voitureB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.voiture);
        ourseB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.ourson);
        bonbonB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.bonbon);
        lunetteB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.lunette);
        telephoneB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.telephone);
    }

    public void initparameters() {
        Log.i("-> Fct <-", " initparameters ");

        height = getHeight();
        width = getWidth();

        loadimages(speedReflexRes);

    }

    private void dessin(Canvas canvas) {
        // Log.i("-> Fct <-", " dessin ");
        canvas.drawRGB(105, 105, 105);
        paintElement(canvas);
    }

    public void paintElement(Canvas canvas){
        canvas.drawBitmap(voitureB,voitureB.getWidth(),height - voitureB.getHeight()*2, null);
        canvas.drawBitmap(ourseB,voitureB.getWidth()*2+5,height - voitureB.getHeight()*2, null);
        canvas.drawBitmap(bonbonB,voitureB.getWidth()*3 +10,height - voitureB.getHeight()*2, null);
        canvas.drawBitmap(lunetteB,voitureB.getWidth()*4 +15,height - voitureB.getHeight()*2, null);
        canvas.drawBitmap(telephoneB,voitureB.getWidth()*5 +20,height - voitureB.getHeight()*2, null);

    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        initparameters();
        in=true;
        cv_thread = new Thread(this);
        cv_thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void run() {
        Log.i("-> Fct <-", " run ");
        Canvas c = null;
        while (in) {
            try {
                cv_thread.sleep(40);
                try {

                    c = holder.lockCanvas(null);
                    dessin(c);

                } finally {
                    if (c != null) {
                        holder.unlockCanvasAndPost(c);
                    }
                }
            } catch (Exception e) {
                Log.e("-> RUN <-", "PB DANS RUN");
                cv_thread.currentThread().interrupt();
                break;
            }
        }
        cv_thread.interrupt();

    }

    public void setThread(boolean etat){
        this.in=etat;

    }

}
