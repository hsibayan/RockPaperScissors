package com.example.hannah.rpsnew;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

/**
 * Created by Jet on 05/03/2016.
 */
public class InterfaceMonitor {

    private Handler handler;
    private boolean isReceived = false;
    private boolean disableUI = true;
    private int move;



    public InterfaceMonitor(Handler h){
        this.handler = h;
    }

    public synchronized void disable(){
        disableUI = true;
    }

    public synchronized boolean isDisabled(){
        return disableUI;
    }

    public synchronized void enable(){
        disableUI = false;
    }



    public synchronized void move(int move){
        Log.i("jet","I am now moving");
        this.isReceived = true;
        this.move = move;
        notifyAll();
    }


    public synchronized int waitInput(){

        Log.i("jet","ABOUT WO WAIIT");

        try {
            //disableUI = false;
            while(!isReceived){
                wait();
            }
        Log.i("jet", "AWAKE BRO HERE");

        } catch (InterruptedException e) {
            Log.i("jet", e.toString());
        }catch(Exception e){
            Log.i("jet",e.toString());
        }
        this.isReceived = false;

        return this.move;
    }
/*
    public void updateBoard(){
        Message message = Message.obtain();
        message.arg1 = 1;
        handler.sendMessage(message);
    }
*/
/*    public void enableUI(){
        Message message = Message.obtain();
        message.arg1 = 2;
        handler.sendMessage(message);
    }*/

    public void endRound(int urmove, int enemymove ,int win,int lose,int result,int round){
        Result_Display_Seed end_result= new Result_Display_Seed(decode_move_id(urmove)+" "+decode_move_id(enemymove),decode_win(result),win+"",lose+"",round+"");


        Message message = Message.obtain();
        message.arg1 = 1;
        message.obj = end_result;
        handler.sendMessage(message);
    }

    public void finishGame(String overall,int urmove, int enemymove ,int win,int lose,int result){
        Message message = Message.obtain();
        message.arg1 = 3;
        message.obj = new Finish_Display(overall,decode_move_id(urmove)+" "+decode_move_id(enemymove),decode_win(result),win+"",lose+"");
        handler.sendMessage(message);
    }


    public String decode_win(int b){
        if(b== 1)
            return "Win";
        else if(b == 0)return "Lose";

        return "Draw";
    }

    public String decode_move_id(int move_id){
        String move_string="";
        switch(move_id){
            case 1: move_string= "Rock";
                break;
            case 2: move_string ="Scissors";
                break;
            case 3: move_string ="Paper";
                break;
        }
        return move_string;
    }



}
