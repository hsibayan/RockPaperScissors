package com.example.hannah.rpsnew;

/**
 * Created by Jet on 15/03/2016.
 */
public class Finish_Display {
    public String pick;
    public String result;
    public String my_score;
    public String ur_score;
    public String overall;

    public Finish_Display(String overall,String pick, String result,String my_score,String ur_score){
        this.pick = pick;
        this.result= result;
        this.my_score= my_score;
        this.ur_score= ur_score;
        this.overall = overall;
    }

}
