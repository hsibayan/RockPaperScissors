package com.example.hannah.rpsnew;

import android.util.Log;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * Created by Jet on 20/02/2016.
 */
public class Server extends Thread {

    private InputStream inputstream;
    private BufferedReader reader;
    private int receivedRounds = -1;
    Server_Response sr;



    public Server(InputStream is){
        this.inputstream = is;
        this.reader = new BufferedReader(new InputStreamReader(is));
        sr = new Server_Response();
    }


    public void run(){
        JSONObject m;

        try{
            String message;
            while((message = reader.readLine())!= null ){
                try {
                    m = new JSONObject(message);
                    if(m.getInt("type") == 2){
                        get_rounds_from_client(m.getInt("rounds"));
                    }

                    if(m.getInt("type") == 1){
                        incoming_move(m.getInt("move"));
                    }

                } catch (Exception t){
                    //send message to client.
                    Log.e("jet",t.toString());
                    //Log.e("My App", "Yup over here Could not parse malformed JSON: \"" + message + "\"");
                }
                Log.i("Server",message);
            }

        }catch(IOException e){
            Log.i("Error in e","Bro u have an error in the server side");
            Log.e("Error in e",e.toString());
        }


    }

    public synchronized void get_rounds_from_client(int c){
        receivedRounds = c;
        notifyAll();
    }

    public synchronized int waitRound(){

        while(receivedRounds == -1){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return receivedRounds;
    }


    public synchronized void incoming_move(int move){
        Log.i("jet2","INCOMING BOARD ");
        this.sr.read =false;
        this.sr.response = move;
        notifyAll();

    }

    public synchronized Server_Response waitForServer(){
        Log.i("jet","about to sleep waiting for server");
        while(this.sr.read){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.i("jet","woke up");
        this.sr.read = true;
        return sr;
    }





}
