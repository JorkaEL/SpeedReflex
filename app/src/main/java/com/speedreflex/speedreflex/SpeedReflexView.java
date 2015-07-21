package com.speedreflex.speedreflex;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.format.Time;
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
    public boolean gameOver; // destiné a savoir si le jeu est fini

    //concerne le chrono
    private Time iniTime = new Time();
    private Time currentTime = new Time();
    private int tempsJeu = 0;
    private int minuteJeu = 0;
    private int oldTempsJeu = 0;
    private boolean first;
    private String temps;


    private Bitmap voitureB;
    private Bitmap ourseB;
    private Bitmap bonbonB;
    private Bitmap lunetteB;
    private Bitmap telephoneB;

    public Elements voiture;
    public Elements ourse;
    public Elements bonbon;
    public Elements lunette;
    public Elements telephone;

    public int nbElements=5;
    public int elementSelectione;

    public int heightElement;
    public int widthElement;

    public  Elements[] tabEl;//tableau des elements a choisir

    private Resources speedReflexRes;
    private Context speedReflexcontext;

    private boolean in = true;
    private Thread cv_thread;
    SurfaceHolder holder;
    private Random rd;

    public int height;
    public int width;

    //concerne les Cartes
    private Bitmap carte1;
    private Bitmap carte2;
    private Bitmap carte3;
    private Bitmap carte4;
    private Bitmap carte5;
    private Bitmap carte6;
    private Bitmap carte7;
    private Bitmap carte8;
    private Bitmap carte9;
    private Bitmap carte10;
    private Bitmap carte11;
    private Bitmap carte12;
    private Bitmap carte13;
    private Bitmap carte14;
    private Bitmap carte15;
    private Bitmap carte16;
    private Bitmap carte17;
    private Bitmap carte18;
    private Bitmap carte19;
    private Bitmap carte20;


    public Carte[] tabJeu; // paquet de carte du jeu
    public Carte   carteSelectioner; // contient la carte qui doit être affiché

    public int carteAfficher; // emplacement de la carte à afficher
    public int carteNombre; // nombre de carte dans le tableau
    public int nbUse; // nombre de carte déjà trouvé

    public int heightCarte;
    public int widthCarte;

    /*endroit ou le joueur clique */
    public float positionClickX;
    public float positionClickY;



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
        //image des elements
        voitureB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.voiture);
        ourseB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.ourson);
        bonbonB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.bonbon);
        lunetteB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.lunette);
        telephoneB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.telephone);

        //image des Cartes
        carte1 = BitmapFactory.decodeResource(speedReflexRes, R.drawable.testcarte);

    }

    public void initparameters() {
        Log.i("-> Fct <-", " initparameters ");

        height = getHeight();
        width = getWidth();
       carteNombre=1;
        tabJeu = new Carte[carteNombre];


        iniTime.setToNow();
        initTabJeu();
        heightCarte = tabJeu[0].getImageCarte().getHeight();
        widthCarte = tabJeu[0].getImageCarte().getWidth();
        heightElement = tabEl[0].getImage().getHeight();
        widthElement =  tabEl[0].getImage().getWidth();
        melangeCarte();
        choixCarte();
        elementSelectione=-1;
        Log.i("-> Fct <-", " Element = "+elementSelectione);


        gameOver=false;

    }

    private void initTabJeu(){

        // partie element
         voiture = new Elements(0,"Voiture", voitureB);
         ourse = new Elements(1,"Ourse", ourseB);
         bonbon = new Elements(2,"Bonbon", bonbonB);
         lunette = new Elements(3,"Lunette",lunetteB);
         telephone = new Elements(4,"Telephone",telephoneB);

        tabEl= new Elements[]{voiture,ourse,bonbon,lunette,telephone};

        //partie carte
        tabJeu[0] = new Carte(lunette,carte1);

    }

    private void dessin(Canvas canvas) {
        // Log.i("-> Fct <-", " dessin ");
        canvas.drawRGB(105, 105, 105);
        paintElement(canvas);
        paintCarte(canvas);
        paintScore(canvas);
    }

    public void paintElement(Canvas canvas){
        int i,w,h,n=1;
        //Bitmap tmp;
        //w= tabEl[0].getImage().getWidth();
        h= height - tabEl[0].getImage().getHeight()*2;
        for(i=0;i<nbElements;i++){
            //tmp=tabEl[i].getImage();
            if(i==0){
                canvas.drawBitmap(tabEl[i].getImage(),widthElement*(n+i),h, null);
            }else{
                canvas.drawBitmap(tabEl[i].getImage(),widthElement*(n+i)+5*i,h, null);
            }

        }

        //canvas.drawBitmap(voitureB,voitureB.getWidth(),height - voitureB.getHeight()*2, null);
        //canvas.drawBitmap(ourseB,voitureB.getWidth()*2+5,height - voitureB.getHeight()*2, null);
        //canvas.drawBitmap(bonbonB,voitureB.getWidth()*3 +10,height - voitureB.getHeight()*2, null);
        //canvas.drawBitmap(lunetteB,voitureB.getWidth()*4 +15,height - voitureB.getHeight()*2, null);
        //canvas.drawBitmap(telephoneB,voitureB.getWidth()*5 +20,height - voitureB.getHeight()*2, null);

    }

    public void paintCarte(Canvas canvas){
        canvas.drawBitmap(carteSelectioner.getImageCarte(), widthElement*2, heightCarte-70, null);
    }

    private void paintScore(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(widthElement);
        canvas.drawText(temps, getWidth() / 3, width / 3, paint);

    }


    private Carte getCarteAfficher(int i) {

        return tabJeu[i];

    }

    public void melangeCarte(){
        int iNew;
        Carte carTmp;

        for (int i = 0; i < carteNombre; i++) {

                carTmp = tabJeu[i];
                rd = new Random();
                iNew = rd.nextInt(carteNombre);
                tabJeu[i] = tabJeu[iNew];
                tabJeu[iNew] = carTmp;

        }

    }

    public void choixCarte(){
        int i;
        nbUse = 0;

        for (i=0;i<carteNombre;i++){
            if(!tabJeu[i].getUse()){
                carteAfficher = i;
                carteSelectioner = getCarteAfficher(carteAfficher);
                break;
            }
            nbUse++;
        }

        if(nbUse == carteNombre -1){
            gameOver=true;
        }

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
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        // Log.d("onTouchEvent", "r�cup�ration de la position du doigt");
        positionClickX = event.getX();// recuperation position X
        positionClickY = event.getY();// recuperation position Y
        int i;
        switch (event.getAction()) {// Swtich sur le type d'action
            case MotionEvent.ACTION_MOVE:
                // System.out.println("ACTION_MOVE");

                break;
            case MotionEvent.ACTION_DOWN:
                //System.out.println("ACTION_DOWN");
                // Action lorsque le joueur touche l'ecran
                if((positionClickX >= voitureB.getWidth() && positionClickX<(voitureB.getWidth()*6+5*(nbElements-1))) && (positionClickY >= (height-(voitureB.getHeight()*2)) && positionClickY<(height-voitureB.getHeight()) ) ){
                    Log.i("-> Fct <-", " clic X "+positionClickX);
                    Log.i("-> Fct <-", " clic Y "+positionClickY);
                    for(i=0;i<nbElements;i++){
                        if((positionClickX >= voitureB.getWidth()*(i+1) && positionClickX < voitureB.getWidth()*(i+2)+5*(nbElements-1)) &&(positionClickY >= (height-(voitureB.getHeight()*2)) && positionClickY<(height-voitureB.getHeight()) )){
                            Log.i("-> Fct <-", " i = "+i);
                            elementSelectione=i;
                        }
                        else {
                            elementSelectione=-1;
                        }
                    }
                }


                break;
            case MotionEvent.ACTION_UP:
                //System.out.println("ACTION_UP");
                // RAZ des booleans

                break;
            default:
                System.out.println();
        }

        return super.onTouchEvent(event);
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
            tempsEcoule();
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

    private void tempsEcoule() {

        if (!gameOver) {
            currentTime.setToNow();
            if (currentTime.second >= iniTime.second) {
                tempsJeu = (currentTime.second - iniTime.second);
                tempsJeu += oldTempsJeu;
                if (tempsJeu >= 21) {
                    tempsJeu -= 21;

                } else {
                    if (tempsJeu != 20)
                        first = true;
                }
                if (tempsJeu < 10) {
                    first = true;

                    temps = "0" + tempsJeu;
                } else {
                    //if (first) {

                        temps = "" + tempsJeu;
                    //}
                    if (tempsJeu == 20 && first) {
                        first = false;
                    }
                }

            } else {
                Log.i("Fct chrono","current < init");
                tempsJeu = ((currentTime.second + 21) - iniTime.second);
                tempsJeu += oldTempsJeu;
                if (tempsJeu >= 21) {
                    tempsJeu -= 21;
                } else {
                    if (tempsJeu != 20)
                        first = true;
                }
                if (tempsJeu < 10) {
                    first = true;

                    temps = "0" + tempsJeu;
                } else {
                    //if (first) {

                        temps = "" + tempsJeu;
                    //}
                    if (tempsJeu == 20 && first) {

                        first = false;
                    }
                }
            }
        }
    }

}
