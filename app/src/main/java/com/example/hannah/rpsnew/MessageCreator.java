package com.example.hannah.rpsnew;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jet on 05/03/2016.
 */
public class MessageCreator {

    public int id;

    public MessageCreator(){
        this.id = id;
    }


    public String sendmove(int move){
        JSONObject json = new JSONObject();
        try {
            json.put("type",1);
            json.put("move",move);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("jet","sending message this"+json.toString());
        return json.toString();
    }

    public String handShake(int rounds){
        char urcolor;
        JSONObject json= new JSONObject();
        try {
            json.put("type",2);
            json.put("rounds", rounds);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("jet", "Send the color " + json.toString());
        return json.toString();
    }

    public int getTurn(JSONObject json){
        int turn = -1;
        try {
            turn=json.getInt("turn");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return turn;
    }


}
