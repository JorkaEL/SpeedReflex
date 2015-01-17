package com.speedreflex.speedreflex;

/**
 * Created by xavier on 17/01/15.
 */
public class Elements {
    public int id;
    public String forme;
    public int couleur;

    public Elements(int id,String forme, int couleur){
        this.id=id;
        this.forme=forme;
        this.couleur=couleur;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setForme(String forme) {
        this.forme = forme;
    }

    public void setCouleur(int couleur) {
        this.couleur = couleur;
    }

    public int getId(){
        return  id;
    }

    public int getCouleur() {
        return couleur;
    }

    public String getForme() {
        return forme;
    }
}
