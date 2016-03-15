package com.example.hannah.rpsnew;

import android.util.Log;

/**
 * Created by Jet on 14/03/2016.
 */
public class Game extends Thread{

    private InterfaceMonitor im;
    private int maxrounds;
    private int serverclient = 0;
    //private Communicator com;

    public Game(InterfaceMonitor im, int rounds){
        this.im= im;
        this.maxrounds =  rounds;

    }

    public void run(){
        Server_Response sr;
        int round = 0;
        int move=-1;
        int enemymove=-1;
        int wins= 0;
        int lose= 0;
        int flag=0;
        while(round < maxrounds){

            Log.i("Jet", "Waiting for user press");
            im.enable();
            move = im.waitInput();
            StartActivity.communicator.sendMove(move);

            //i got a move here.
            sr = StartActivity.communicator.waitResponse();
            round++;
            Log.i("Jet", "I Got One here");
            enemymove = sr.response;
            int didwin = isWin(move,enemymove);
            //do something here.*/
            if(didwin == 1){
                wins++;
                flag=1;
            }else if(didwin == 0){
                lose++;
                flag = 0;
            }else if(didwin == -1){
                flag = -1;
            }

            if(round != maxrounds ){
                im.endRound(move,enemymove,wins,lose,flag,round+1);
            }

        }
        String overall="";
        if(wins > lose)
            overall = "Win";
        else if(wins<lose){
            overall= "Lose";
        }else if(wins == lose){
            overall ="Draw";
        }

        im.finishGame(overall, move,enemymove,wins,lose,flag);
    }


    private int isWin(int mymove,int enemymove){
        //1 rock
        //2 scissors
        //3 paper
        int win = 0;

        if(mymove == enemymove){
            return -1;
        }

        switch(mymove){
            case 1: if(enemymove ==2) win = 1;
                break;
            case 2: if(enemymove== 3 ) win= 1;
                break;
            case 3: if(enemymove ==1 ) win =1;
                break;
        }
        return win;
    }




}
