package com.example.hannah.rpsnew;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by Jet on 20/02/2016.
 */
public class Communicator {

    public static final int port = 1118;
    public Client client;
    public Server server;
    public Socket peer;
    public static MessageCreator mc = new MessageCreator();
    public static int rounds = 0;

    public boolean waiting_server = false;


    public void sendTurn(JSONObject board, int turn){
        Log.i("jet", "Sending Board ??");
        //client.sendMessage(mc.sendBoard(board,turn));
    }

    public void sendMove(int move){
        client.sendMessage(mc.sendmove(move));
    }


    public Server_Response waitResponse(){
        return server.waitForServer();
    }


    public void TryGame(int rounds){

        ServerSocket sv = null;

        try{
            Communicator.rounds = rounds;
            sv = new ServerSocket();
            sv.setReuseAddress(true);
            sv.bind(new InetSocketAddress(Communicator.port));
            Log.i("Jet","Now Accepting Connections");
            this.peer =sv.accept();
            Log.i("Jet","Accepted a connection!");

            this.server = new Server(this.peer.getInputStream());
            this.server.start();
            this.client = new Client(this.peer.getOutputStream());
            this.client.sendMessage(Communicator.mc.handShake(rounds));


        }catch(Exception e){
            Log.i("Server side", e.toString());
        }

    }


    public void TryHandshake(String address){

        try{

            Socket s = new Socket(address,Communicator.port);
            this.peer = s;
            this.server = new Server(this.peer.getInputStream());
            this.server.start();


            this.client = new Client(this.peer.getOutputStream());
            Communicator.rounds = this.server.waitRound();
            Log.i("jet2", "i just got my color" + rounds);
        }catch(UnknownHostException e){
            Log.i("mylog", "Bro error in the client socket");
            Log.e("mylog", e.toString());
        }catch(IOException e){
            Log.e("mylog", e.toString());
        }

    }

    public void closeConnection(){
        if(!this.peer.isClosed()){
            try {
                this.peer.close();
            } catch (IOException e) {
                Log.i("Error",e.toString());
            }
        }
    }


}





