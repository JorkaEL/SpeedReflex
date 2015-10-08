package com.speedreflex.speedreflex;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Vibrator;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.util.Random;
import java.util.StringTokenizer;

/**
 * Created by xavier on 11/01/15.
 */
public class SpeedReflexView extends SurfaceView implements View.OnClickListener,
        View.OnTouchListener, SurfaceHolder.Callback, Runnable {

    public Activity parentActivity;
    Intent intentDataPlayer = null;
    public boolean gameOver; // destiné a savoir si le jeu est fini
    MotionEvent event2 = null;
    boolean continu=false;
    boolean vibre=false;
    boolean sound=false;

    //concerne le chrono
    private Time currentTime = new Time();
    private  Time preCurrentTime = new Time();
    private int tempsJeu = 0;
    private int oldTempsJeu;
    private int tempsDispo;
    private String temps;

    //dificulter

    public int difficulter;
    public String difficulterString;


    public int score;
    //public String scoreString;
    public boolean choixEffectuer=false;

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

    private boolean choixDuJoueur;

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

    //affiche le resultat du joueur
    private Bitmap yes;
    private Bitmap no;
    private boolean afficheLeChoix=false;
    private boolean attendre=false;
    private boolean attendrefirst=false;
    private Time secondeAtt = new Time();
    private int secondepasse;
    public Vibrator vibration;



    public SpeedReflexView(Context context,AttributeSet attrs) {
        super(context, attrs);

        Log.i(">>> Projet", "SpeedReflexView");

        holder = getHolder();// Recuperation du Holder
        holder.addCallback(this);// Ajout

        speedReflexcontext = context;
        Log.i(">>> Projet", "SpeedReflexView 1 ");

        speedReflexRes = speedReflexcontext.getResources();
        Log.i(">>> loadimages", "SpeedReflexView 2 ");

        vibration = (Vibrator) this.speedReflexcontext.getSystemService(Context.VIBRATOR_SERVICE);

        loadimages(speedReflexRes);
        Log.i(">>> Projet", "SpeedReflexView 3 ");

        //rd = new Random();
        cv_thread = new Thread(this);
        setFocusable(true);
        setOnClickListener(this);


    }

    private void loadimages(Resources speedReflexRes) {

        yes=BitmapFactory.decodeResource(speedReflexRes,R.drawable.yes);
        no=BitmapFactory.decodeResource(speedReflexRes,R.drawable.no);

        //image des elements
        voitureB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.voiture_rouge_50x50);
        ourseB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.ours_marron_50x50);
        bonbonB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.bonbon_rose_50x50);
        lunetteB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.lunette_verte_50x50);
        telephoneB = BitmapFactory.decodeResource(speedReflexRes, R.drawable.telephone_noire_50x50);

        //image des Cartes
        carte1 = BitmapFactory.decodeResource(speedReflexRes, R.drawable._1_ours_marron_voiture_verte);
        carte2 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._2_voiture_rouge_bonbon_marron);
        carte3 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._3_bonbon_rose_lunette_noire);
        carte4 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._4_lunette_verte_telephone_rose);
        carte5 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._5_ours_rouge_telephone_noire);
        carte6 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._6_voiture_rose_lunette_noire);
        carte7 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._7_ours_noire_bonbon_vert);
        carte8 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._8_lunette_marron_telephone_rouge);
        carte9 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._9_bonbon_noire_ours_rouge);
        carte10 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._10_lunette_rose_voiture_marron);
        carte11 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._11_bonbon_vert_telephone_rouge);
        carte12 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._12_lunette_marron_telephone_rose);
        carte13 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._13_voiture_noire_ours_vert);
        carte14 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._14_voiture_marron_telephone_rose);
        carte15 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._15_ours_rouge_bonbon_vert);
        carte16 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._16_voiture_noire_lunette_rose);
        carte17 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._17_ours_vert_bonbon_noire_bis);
        carte18 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._18_lunette_rouge_telephone_marron);
        carte19 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._19_ours_rose_telephone_rouge);
        carte20 = BitmapFactory.decodeResource(speedReflexRes,R.drawable._20_ours_rose_voiture_verte);



    }

    public void initparameters() {
        Log.i("-> Fct <-", " initparameters ");

        height = getHeight();
        width = getWidth();
        carteNombre=20;

        tabJeu = new Carte[carteNombre];

        // partie element
        voiture = new Elements(0,"Voiture", voitureB);
        ourse = new Elements(1,"Ourse", ourseB);
        bonbon = new Elements(2,"Bonbon", bonbonB);
        lunette = new Elements(3,"Lunette",lunetteB);
        telephone = new Elements(4,"Telephone",telephoneB);

        if(difficulter==0) {
            tempsDispo = 20;
            difficulterString="Facile";
        }else if(difficulter==1){
            tempsDispo = 15;
            difficulterString="Normal";
        }else{
            tempsDispo=10;
            difficulterString="Difficile";
        }

        secondepasse=0;

        if(loadExist()){
            tabEl=new Elements[nbElements];
            loadGame();
            tempsJeu=oldTempsJeu;
            elementSelectione=-1;
            //Log.i("CarteAfficher"," "+carteAfficher);
            //carteAfficher=0;

        }
        else{
            preCurrentTime.setToNow();
            initTabJeu();
            melangeCarte();
            melangerElements();
            carteAfficher=0;
            choixCarte();
            elementSelectione=-1;
            tempsJeu=tempsDispo;

            score=0;
        }

        //iniTime.setToNow();
       // preCurrentTime.setToNow();

        //initTabJeu();
        heightCarte = tabJeu[0].getImageCarte().getHeight();
        widthCarte = tabJeu[0].getImageCarte().getWidth();
        heightElement = tabEl[0].getImage().getHeight();
        widthElement =  tabEl[0].getImage().getWidth();
       // melangeCarte();
        //carteAfficher=0;
        //choixCarte();
        //elementSelectione=-1;
        //Log.i("Dificulter"," : "+difficulter);
        //Log.i("-> Fct <-", " Element = "+elementSelectione);

        //score=0;

        //tempsJeu=oldtempsJeu;
        //saveGame();
        //loadGame();

        gameOver=false;

    }

    private void initTabJeu(){



        tabEl= new Elements[]{voiture,ourse,bonbon,lunette,telephone};

        //partie carte
        tabJeu[0] = new Carte(ourse,carte1,1);
        tabJeu[1] = new Carte(voiture, carte2,2);
        tabJeu[2] = new Carte(bonbon,carte3,3);
        tabJeu[3] = new Carte(lunette,carte4,4);
        tabJeu[4] = new Carte(telephone,carte5,5);

        tabJeu[5] = new Carte(ourse, carte6,6);
        tabJeu[6] = new Carte(voiture,carte7,7);
        tabJeu[7] = new Carte(bonbon,carte8,8);
        tabJeu[8] = new Carte(lunette,carte9,9);
        tabJeu[9] = new Carte(telephone, carte10,10);

        tabJeu[10] = new Carte(ourse,carte11,11);
        tabJeu[11] = new Carte(voiture,carte12,12);
        tabJeu[12] = new Carte(bonbon,carte13,13);
        tabJeu[13] = new Carte(lunette, carte14,14);
        tabJeu[14] = new Carte(telephone,carte15,15);

        tabJeu[15] = new Carte(ourse,carte16,16);
        tabJeu[16] = new Carte(voiture,carte17,17);
        tabJeu[17] = new Carte(bonbon, carte18,18);
        tabJeu[18] = new Carte(lunette,carte19,19);
        tabJeu[19] = new Carte(telephone,carte20,20);

    }

    private void dessin(Canvas canvas) {
        // Log.i("-> Fct <-", " dessin ");
        canvas.drawRGB(105, 105, 105);
        paintElement(canvas);
        paintCarte(canvas);
        paintChoix(canvas);
        paintTemps(canvas);
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

    }

    public void paintCarte(Canvas canvas){
        canvas.drawBitmap(carteSelectioner.getImageCarte(), widthElement * 2, heightCarte - 70, null);
    }

    private void paintTemps(Canvas canvas) {
        Paint paint = new Paint();

        if(difficulter==0) {
            if (tempsJeu >= 10) {
                paint.setColor(Color.GREEN);
            } else if (tempsJeu >= 5 && tempsJeu <= 9) {
                paint.setColor(Color.YELLOW);
            } else if (tempsJeu <= 4) {
                paint.setColor(Color.RED);
            }else{
                paint.setColor(Color.RED);
            }
        }
        else if(difficulter==1){
            if (tempsJeu >= 10) {
                paint.setColor(Color.GREEN);
            } else if (tempsJeu >= 6 && tempsJeu <= 9) {
                paint.setColor(Color.YELLOW);
            } else if (tempsJeu <= 5) {
                paint.setColor(Color.RED);
            }else{
                paint.setColor(Color.RED);
            }
        }else{
            if (tempsJeu >= 8) {
                paint.setColor(Color.GREEN);
            } else if (tempsJeu >= 5 && tempsJeu <= 7) {
                paint.setColor(Color.YELLOW);
            } else if (tempsJeu <= 4) {
                paint.setColor(Color.RED);
            }else{
                paint.setColor(Color.RED);
            }
        }
        //paint.setColor(Color.BLACK);
        paint.setTextSize(widthCarte/2);
        canvas.drawText(temps, widthElement * 2 + widthElement / 2, width / 3, paint);

    }

    private void paintChoix(Canvas canvas){
        if(attendrefirst) {
            if (afficheLeChoix) {
                canvas.drawBitmap(yes, widthElement * 4, heightCarte+heightElement*2, null);
            } else {
                canvas.drawBitmap(no, widthElement * 4, heightCarte+heightElement*2, null);
            }
        }
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
        int i,ancienneCarte;
        //nbUse = 0;
        boolean boucle2=false, fingame=false;
        //melangeCarte();

        if(carteAfficher==carteNombre-1){
            i=0;
        }else{
            i=carteAfficher+1;
        }
        ancienneCarte=carteAfficher;
        for (;i<carteNombre;i++){
            if(!tabJeu[i].getUse()){
                carteAfficher = i;
                carteSelectioner = getCarteAfficher(carteAfficher);
                boucle2=false;
                break;
            }else{
                boucle2=true;
            }
            //nbUse++;
        }

        if(boucle2) {
            for (int j = 0; j<carteAfficher;j++){
                if(!tabJeu[j].getUse()){
                    carteAfficher = j;
                    carteSelectioner = getCarteAfficher(carteAfficher);
                    fingame=false;
                    break;
                }else{
                    fingame=true;
                }
            }

        }


        if(fingame || ancienneCarte==carteAfficher ){
            gameOver=true;

        }

    }



    public void finGame(){
        //Log.i("FinGame","gameOver = "+gameOver);
        //if(gameOver){
            Log.i("FinGame", "gameOver = " + gameOver);
        if (!attendre){
            in=false;
            //deleteSave();




        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder messVictory = new AlertDialog.Builder(speedReflexcontext);

                messVictory.setTitle("GAME OVER")

                        .setIcon(R.drawable.ic_about);
                if(score==5*carteNombre) {
                    messVictory.setMessage(" Score : "+score+" points, Woa vous etes très rapide ! Vous devriez essayer avec un niveau de difficulté supérieur ! Voulez vous sauvegarder votre score ?");
                }
                else if(score>=5*(carteNombre-(carteNombre/3))){
                    messVictory.setMessage(" Score : "+score+" points, Super ! Aller plus qu'un petit effort, pour être le meilleurd réesayer! Voulez vous sauvegarder votre score ? ");
                }
                else if(score >= 5*(carteNombre/2)) {
                    messVictory.setMessage(" Score : "+score+" points, Bien, mais vous pouvez faire mieux réesayer. Voulez vous sauvegarder votre score ? ");
                }
                else if(score>= 5*(carteNombre/3)){
                    messVictory.setMessage(" Score : "+score+" points, Pas mal, vous commencez a comprendre le jeu, voulez vous sauvegarder votrez score ?");
                }
                else{
                    messVictory.setMessage(" Score : "+score+" points, mmmh vous avez besoin d'entrainement, voulez vous sauvegarder votrez score ?");
                }

                        messVictory.setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("FinGame"," deleteSave");
                                deleteSave();
                                in=false;
                                try {
                                    intentDataPlayer.putExtra("SCORE", ""+score);
                                    intentDataPlayer.putExtra("DIFFICULTER", difficulterString);
                                    Log.i("intentData",": "+intentDataPlayer);
                                    parentActivity.startActivity(intentDataPlayer);
                                } catch (Exception e) {
                                    Log.i("Launch Activity fail", e.toString());
                                }
                                //gameOver=false;
                                parentActivity.finish();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Log.i("FinGame"," deleteSave");
                                deleteSave();
                                in=false;
                                //gameOver=false;
                                parentActivity.finish();

                            }
                        });
                Log.i("FinGame", " Alert cree");
                AlertDialog alertMess = messVictory.create();
                alertMess.show();
            }
        });


        }
    }

    public boolean choixJoueur(){
        if(tabEl[elementSelectione].getId() == carteSelectioner.getBonElements().getId()){
            return true;
        }
        else{
            return false;
        }
    }

    public void setDificulter(int difficulter){
        this.difficulter=difficulter;
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
        if(gameOver){
            deleteSave();
        }
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
        event2=event;
        int i;
        //Log.i("onTouch",": "+event.getAction());
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
                    //gameOver=true;
                    for(i=0;i<nbElements;i++){
                        if((positionClickX >= voitureB.getWidth()*(i+1) && positionClickX < voitureB.getWidth()*(i+2)+5*(nbElements-1)) &&(positionClickY >= (height-(voitureB.getHeight()*2)) && positionClickY<(height-voitureB.getHeight()) )){
                            Log.i("-> Fct <-", " i = "+i);
                            elementSelectione=i;
                            choixDuJoueur=choixJoueur();
                            choixEffectuer=true;
                            Log.i("TestchoixJoueur",": "+choixDuJoueur);


                        }
                        else {
                            elementSelectione=-1;
                        }
                    }
                }
                //finGame2();
                Log.i("down"," "+ event.getAction());


                break;
            case MotionEvent.ACTION_UP:
                //System.out.println("ACTION_UP");
                // RAZ des booleans
                //choixEffectuer=false;
               /* if(gameOver) {
                    finGame();
                }*/
               // Log.i("UP"," "+ event.getAction());

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
                    if(!gameOver) {
                       // if(attendre){ afficheChoix(); }else{
                        if (choixEffectuer) {
                            augmenteScore();

                            //changerCarte();
                            /*if (difficulter != 0) {
                                melangerElements();
                            }*/
                            Log.i("Score :", " " + score);
                            choixEffectuer = false;
                            attendre = true;
                            secondeAtt.setToNow();

                            //tempsJeu = tempsDispo;

                        }
                        else if(attendre){
                            attendrefirst=true;
                            afficheChoix();
                            if(!afficheLeChoix){
                                //vibration.vibrate(500);
                            }
                        }
                        else {
                            if (tempsJeu == 0) {
                                //changerCarte();
                                attendre =true;
                                secondeAtt.setToNow();
                                choixDuJoueur=false;

                                //tempsJeu = tempsDispo;
                                /*if (difficulter != 0) {
                                    melangerElements();
                                }*/

                            }
                        }
                    //}
                    }else {

                        Log.i("Run ", "GameOver");
                        //deleteSave();
                        afficheChoix();
                        finGame();

                    }

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

            if(!attendre) {
                currentTime.setToNow();

                if (preCurrentTime.second != currentTime.second) {

                    if (tempsJeu == 0) {
                        tempsJeu = tempsDispo;
                    } else {
                        tempsJeu -= 1;
                    }

                    preCurrentTime.second = currentTime.second;
                }

                if (tempsJeu < 10) {

                    temps = "0" + tempsJeu;
                } else {

                    temps = "" + tempsJeu;
                }

            }

        }
    }

    public Bitmap getCarte(int id){
        Log.i("GetCarte"," "+id);
        switch (id){
            case 1: return carte1;
            //break;
            case 2: return carte2;
           // break;
            case 3: return carte3;
            //break;
            case 4: return carte4;
            //break;
            case 5: return carte5;
            //break;
            case 6: return carte6;
            //break;
            case 7: return carte7;
            //break;
            case 8: return carte8;
            //break;
            case 9: return carte9;
            //break;
            case 10: return carte10;
            //break;
            case 11: return carte11;
            //break;
            case 12: return carte12;
            //break;
            case 13: return carte13;
            //break;
            case 14: return carte14;
            //break;
            case 15: return carte15;
            //break;
            case 16: return carte16;
            //break;
            case 17: return carte17;
            //break;
            case 18: return carte18;
            //break;
            case 19: return carte19;
            //break;
            case 20: return carte20;
            //break;
            default: return null;
        }
        //return null;
    }

    public Elements getElement(String element){
        if (element.equals("Voiture")) {
            return voiture;

        }
        else if(element.equals("Ourse")){
            return ourse;
        }
        else if(element.equals("Bonbon")){
            return bonbon;
        }
        else if(element.equals("Lunette")){
            return lunette;
        }
        else if(element.equals("Telephone")){
            return telephone;
        }
        else{
            return null;
        }
    }

    public boolean getUSE(String use){
        if(use.equals("true")){
            return true;
        }
        else{
            return  false;
        }

    }

    private void augmenteScore(){
        if(choixDuJoueur) {
            if (difficulter == 0) {
                if (tempsJeu >= 10) {
                    score += 5;
                } else if (tempsJeu <= 9 && tempsJeu >=6) {
                    score += 3;
                } else if (tempsJeu >= 5 && tempsJeu >= 3) {
                    score += 2;
                } else {
                    score += 1;
                }
            } else if (difficulter == 1) {
                if (tempsJeu >= 10) {
                    score += 5;
                } else if (tempsJeu <= 9 && tempsJeu >=6) {
                    score += 2;
                } else if (tempsJeu >= 5 && tempsJeu >= 3) {
                    score += 1;
                }else{
                    score+=0;
                }
            } else {
                if (tempsJeu >= 8) {
                    score += 5;
                } else if (tempsJeu <= 7 && tempsJeu >=5) {
                    score += 2;
                } else if (tempsJeu <= 4 && tempsJeu >= 3) {
                    score += 1;
                } else {
                    score += 0;
                }
            }



        }
    }

    private void changerCarte(){
        if(choixDuJoueur){
            tabJeu[carteAfficher].setUse(true);
            choixCarte();
        }else{
            choixCarte();
        }
    }

    private void afficheChoix(){
        currentTime.setToNow();
        //Log.i("CurrentTime", " : " + currentTime.second);
        //Log.i("SecondeATT"," : "+secondeAtt.second);
        if(secondepasse<2){
            if(secondeAtt.second!=currentTime.second) {

            secondepasse++;
                secondeAtt.second=currentTime.second;
             }

            if(choixDuJoueur){
                afficheLeChoix=true;
                //imgResultat= yes;
            }
            else{
                afficheLeChoix=false;

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {

                        vibration.vibrate(500);
                    }

                });
            }


        }else{
            attendre=false;
            attendrefirst=false;
            changerCarte();
            if (difficulter != 0) {
                melangerElements();
            }
            tempsJeu = tempsDispo;
            secondepasse=0;
            choixEffectuer=false;
            preCurrentTime.second=currentTime.second;

        }

    }

    private void melangerElements(){
        int iNew;
        Elements elementsTmp;

        for (int i = 0; i < nbElements; i++) {

            elementsTmp = tabEl[i];
            rd = new Random();
            iNew = rd.nextInt(nbElements);
            tabEl[i] = tabEl[iNew];
            tabEl[iNew] = elementsTmp;

        }
    }

    public void saveGame() {
        SharedPreferences prefs = getContext().getSharedPreferences(
                "MyContext", SpeedReflexActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        StringBuilder sauvegardeTasCarte = new StringBuilder();
        for (int i = 0; i < carteNombre; i++) {

                sauvegardeTasCarte.append(tabJeu[i].bonElements.getForme()).append(",");
                sauvegardeTasCarte.append(tabJeu[i].getIdCarte()).append(",");
                sauvegardeTasCarte.append(tabJeu[i].getUse()).append(",");

        }
        editor.putString("TasCarte", sauvegardeTasCarte.toString());
        Log.i("TasCarte : ",sauvegardeTasCarte.toString());

        StringBuilder sauvegardeElement = new StringBuilder();
        for (int i = 0; i < nbElements; i++) {

                sauvegardeElement.append(tabEl[i].getForme()).append(",");

        }
        editor.putString("Element", sauvegardeElement.toString());
        Log.i("Element : ", sauvegardeElement.toString());

        editor.putInt("IdCarte", carteSelectioner.getIdCarte());
        editor.putString("Forme", carteSelectioner.getBonElements().getForme());
        editor.putInt("CarteAfficher",carteAfficher);
        editor.putInt("Score", score);
        editor.putInt("TempsJeu", tempsJeu);
        editor.putInt("Difficulter",difficulter);
        editor.putInt("Seconde",preCurrentTime.second);

        editor.commit();

    }

    public void loadGame() {
        SharedPreferences prefs = getContext().getSharedPreferences(
                "MyContext", SpeedReflexActivity.MODE_PRIVATE);

        String retourTasCarte = prefs.getString("TasCarte", null);
        String retourElement = prefs.getString("Element", null);

        Log.i(">>> restaurer", " TasCarte");
        StringTokenizer stRef = new StringTokenizer(retourTasCarte, ",");
        for (int i = 0; i < carteNombre; i++) {
            loadTabCarte(i, stRef.nextToken().toString(), Integer.parseInt(stRef.nextToken()), stRef.nextToken().toString());
               // tabJeu[i] = Carte.parseCarte(stRef.nextToken());


        }

        Log.i(">>> restaurer", " tabElements ");
        StringTokenizer stJeu = new StringTokenizer(retourElement, ",");
        for (int i = 0; i < nbElements; i++) {
                loadTabElement(i,stJeu.nextToken().toString());


        }
        Log.i("avt", " loadCarteSelectionner");
        loadCarteSelectionner(prefs.getInt("IdCarte", 0), prefs.getString("Forme", null));
        carteAfficher=prefs.getInt("CarteAfficher",0);
        score = prefs.getInt("Score", 0);
        oldTempsJeu = prefs.getInt("TempsJeu", 0);
        difficulter= prefs.getInt("Difficulter",0);
        preCurrentTime.second=prefs.getInt("Seconde",0);

    }

    public void loadTabCarte(int i,String element,int idCarte, String use ){
        tabJeu[i] = new Carte(getElement(element),getCarte(idCarte),idCarte);

        tabJeu[i].setUse(getUSE(use));
        Log.i("--> FCT <--","LoadTabCarte");

        //Log.i("3 parametre de loadTabCarte"," "+idCarte);

    }

    public void loadTabElement(int i,String element){
        tabEl[i]=new Elements();
        tabEl[i].setElement(getElement(element));
        Log.i("--> FCT <--", "LoadTabElement");

    }

    public void loadCarteSelectionner(int idCarte,String element){

        Log.i("idCarte"+idCarte," element"+element);
        carteSelectioner=new Carte(getElement(element),getCarte(idCarte),idCarte);
        Log.i("--> FCT <--", "LoadCarteSelectionner");

    }

    public boolean loadExist() {
        SharedPreferences prefs = getContext().getSharedPreferences(
                "MyContext", SpeedReflexActivity.MODE_PRIVATE);

        if ((prefs.getString("TasCarte", null) != null
                && prefs.getString("Element", null) != null
                && prefs.getInt("TempsJeu", 0) != 0
                && prefs.getString("Forme",null)!=null)
                || continu==true) {
            return true;
        } else {

            return false;
        }
    }

    public void deleteSave() {
        SharedPreferences prefs = getContext().getSharedPreferences(
                "MyContext", SpeedReflexActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        editor.remove("TasCarte");
        editor.remove("Element");
        editor.remove("Score");
        editor.remove("TempsJeu");
        editor.remove("Difficulter");
        editor.remove("IdCarte");
        editor.remove("Forme");

        editor.commit();

    }

    public void setIntentDataPlayer(Intent intentClassDataPlayer) {
        this.intentDataPlayer = intentClassDataPlayer;
    }

    public void setContinue(boolean continu){
        this.continu=continu;
    }

    public void setVibration(boolean vibre){
        this.vibre=vibre;
    }

    public void setSound(boolean sound){
        this.sound=sound;
    }

    public boolean getSound(){
        return this.sound;
    }

}
