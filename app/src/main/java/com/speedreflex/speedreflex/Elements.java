package com.speedreflex.speedreflex;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * Created by xavier on 17/01/15.
 */
public class Elements {
    public int id;
    public String forme;
    //public int couleur;
    public Bitmap image;

    public Elements(int id,String forme, Bitmap image){
        this.id=id;
        this.forme=forme;
        this.image=image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setForme(String forme) {
        this.forme = forme;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public int getId(){
        return  id;
    }

    public Bitmap getImage() {
        return image;
    }

    public String getForme() {
        return forme;
    }
}
