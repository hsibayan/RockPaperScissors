package com.example.hannah.rpsnew;

import java.io.BufferedWriter;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

/**
 * Created by Jet on 05/03/2016.
 */
public class Client{

    private OutputStream os;
    private PrintWriter sender;

    public Client(OutputStream output){
        this.os = output;
        this.sender = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.os)),true);
    }

    public void sendMessage(String message){
        sender.println(message);
    }

}
