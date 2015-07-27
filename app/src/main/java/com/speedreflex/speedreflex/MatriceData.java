package com.speedreflex.speedreflex;

/**
 * Created by xavier on 09/01/15.
 */
public class MatriceData {

    private long id;
    private String pseudo;
    private String score;
    private String difficulter;

    //Setteurs
    public void setId(long id){
        this.id = id;
    }
    public void setPseudo(String pseudo){
        this.pseudo=pseudo;
    }
    public void setDifficulter(String difficulter){
        this.difficulter = difficulter;
    }

    public void setScore(String score){
        this.score = score;
    }


    //Getteurs
    public long getId(){
        return id;
    }
    public String getDifficulter(){
        return difficulter;
    }

    public String getScore(){
        return this.score;
    }

    public String getPseudo(){
        return this.pseudo;
    }


    public String toString(){
        String strData = pseudo + ","+score+","+","+difficulter;
        return strData;

    }

}
