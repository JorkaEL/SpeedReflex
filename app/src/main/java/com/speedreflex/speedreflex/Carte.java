package com.speedreflex.speedreflex;

import android.graphics.Bitmap;

/**
 * Created by xavier on 17/01/15.
 */
public class Carte {

    //public Elements premierElements;
    //public Elements deuxiemeElements;
    public Elements bonElements;
    public Boolean  use;
    public int idCarte;

    private Bitmap imageCarte;


    public Carte( Elements bonElements, Bitmap imageCarte,int idCarte){
        this.bonElements = bonElements;
        this.imageCarte = imageCarte;
        this.idCarte=idCarte;
        setUse(false);
    }


    public Bitmap getImageCarte() {
        return imageCarte;
    }

    public Elements getBonElements() {
        return bonElements;
    }

    public int getIdCarte(){return idCarte;}

    public Boolean getUse(){return use; }


    public void setImageCarte(Bitmap imageCarte) {
        this.imageCarte = imageCarte;
    }

    public void setBonElements(Elements bonElements) {
        this.bonElements = bonElements;
    }

    public void setIdCarte(int idCarte){this.idCarte=idCarte;}

    public void setUse(boolean use){
        this.use=use;
    }
}
