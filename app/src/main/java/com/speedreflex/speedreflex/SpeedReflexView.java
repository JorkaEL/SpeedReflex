package com.speedreflex.speedreflex;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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

    public Elements voiture;
    public Elements ourse;
    public Elements bonbon;
    public Elements lunette;
    public Elements telephone;

    private Resources speedReflexRes;
    private Context speedReflexcontext;

    private boolean in = true;
    private Thread cv_thread;
    SurfaceHolder holder;
    private Random rd;





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
    }

    public void initparameters() {
        Log.i("-> Fct <-", " initparameters ");
        voiture = new Elements(0,"Voiture", Color.rgb(255,0,0));
        ourse = new Elements(1,"Ourse", Color.rgb(88, 41, 0));
        bonbon = new Elements(2,"Bonbon", Color.rgb(253, 108, 158));
        lunette = new Elements(3,"Lunette", Color.rgb(0, 255, 0));
        telephone = new Elements(4,"Telephone", Color.rgb(0, 0, 0));
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

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

    }
}
