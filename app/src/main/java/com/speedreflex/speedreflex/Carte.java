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

    private Bitmap imageCarte;


    public Carte( Elements bonElements, Bitmap imageCarte){
        this.bonElements = bonElements;
        this.imageCarte = imageCarte;
        setUse(false);
    }

    public Bitmap getImageCarte() {
        return imageCarte;
    }

    public Elements getBonElements() {
        return bonElements;
    }


    public Boolean getUse(){return use; }

    public void setImageCarte(Bitmap imageCarte) {
        this.imageCarte = imageCarte;
    }

    public void setBonElements(Elements bonElements) {
        this.bonElements = bonElements;
    }

    public void setUse(boolean use){
        this.use=use;
    }
}
