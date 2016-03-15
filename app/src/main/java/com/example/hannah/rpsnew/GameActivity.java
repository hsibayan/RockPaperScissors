package com.example.hannah.rpsnew;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class GameActivity extends AppCompatActivity {

    View resultView;
    Button rockBtn, paperBtn, scissorsBtn;
    TextView rounds;
    public InterfaceMonitor im;
    public myHand hand;
    public Game game;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        context = this;
        // UI Things
        Typeface museo700 = Typeface.createFromAsset(getAssets(), "museo700.otf");
        Typeface museo300 = Typeface.createFromAsset(getAssets(), "museo300.otf");

        rounds = (TextView) findViewById(R.id.rounds);      rounds.setTypeface(museo700);
        rockBtn = (Button) findViewById(R.id.rock);         rockBtn.setTypeface(museo300);
        paperBtn = (Button) findViewById(R.id.paper);        paperBtn.setTypeface(museo300);
        scissorsBtn = (Button) findViewById(R.id.scissors);     scissorsBtn.setTypeface(museo300);
        this.hand = new myHand();
        this.im = new InterfaceMonitor(this.hand);
        rockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!im.isDisabled()){
                    im.disable();
                    im.move(1);
                    Toast.makeText(context, "Your move is :"+1, Toast.LENGTH_LONG).show();
                }else Toast.makeText(context, "Wait for opponent", Toast.LENGTH_LONG).show();
            }
        });
        scissorsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!im.isDisabled()){
                    im.disable();
                    im.move(2);
                    Toast.makeText(context, "Your move is :"+2, Toast.LENGTH_LONG).show();
                }else Toast.makeText(context, "Wait for opponent", Toast.LENGTH_LONG).show();
            }
        });
        paperBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!im.isDisabled()){
                    im.disable();
                    im.move(3);
                    Toast.makeText(context, "Your move is :"+3, Toast.LENGTH_LONG).show();
                }else Toast.makeText(context, "Wait for opponent", Toast.LENGTH_LONG).show();
            }
        });
        //enableUI(false);



        this.game = new Game(this.im,StartActivity.communicator.rounds);
        this.game.start();



    }
/*
    private class moveBtn_action implements View.OnClickListener{

        private int move_press;
        //1 = rock
        //2 = scissors
        //3 = paper
        public moveBtn_action(int move){
            this.move_press= move;
        }
        @Override
        public void onClick(View v){
            if(!im.isDisabled()){
                im.disable();
                im.move(this.move_press);
                Toast.makeText(context, "Your move is :"+this.move_press, Toast.LENGTH_LONG).show();
            }else Toast.makeText(context, "Wait for opponent", Toast.LENGTH_LONG).show();
        }
    }
*/


    public synchronized void enableUI(boolean isEnabled) {
        if(isEnabled) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }


    public void createDialog(String choice,String winner,String you_score, String opponentscore) {

        LayoutInflater inflater = getLayoutInflater();
        this.resultView = inflater.inflate(R.layout.results, null);

        TextView choice_view = (TextView) this.resultView.findViewById(R.id.choice);
        TextView winner_view = (TextView) this.resultView.findViewById(R.id.winner);
        TextView uscore_view = (TextView) this.resultView.findViewById(R.id.youScore);
        TextView opscore_view = (TextView) this.resultView.findViewById(R.id.opponentScore);

        choice_view.setText("Your pick, Enemy Pick : "+choice);
        winner_view.setText("Result: "+ winner);
        uscore_view.setText("Your score: "+you_score);
        opscore_view.setText("Enemy Score " + opponentscore);


        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        dialog.setView(resultView);
        dialog.show();
    }

    public void finishGame(Finish_Display result){
        Intent done = new Intent(this, Done_Activity.class);

        ArrayList<String> sl = new ArrayList();

        sl.add(result.pick);
        sl.add(result.result);
        sl.add(result.my_score);
        sl.add(result.ur_score);
        sl.add(result.overall);

        done.putStringArrayListExtra("results",sl);
        startActivity(done);
        StartActivity.communicator.closeConnection();
        finish();
    }


    class myHand extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 1) {
                Log.i("jet2", "updating the board??? dapat");
                Result_Display_Seed result_display = (Result_Display_Seed) msg.obj;
                createDialog(result_display.pick, result_display.result, result_display.my_score, result_display.ur_score);
                rounds.setText("Round "+result_display.round);
            }
            else if(msg.arg2==2){
                enableUI(true);
            }
            else if(msg.arg1 == 3){
                //finish game
                Finish_Display fd = (Finish_Display) msg.obj;
                finishGame(fd);
            }


        }
    }



}
