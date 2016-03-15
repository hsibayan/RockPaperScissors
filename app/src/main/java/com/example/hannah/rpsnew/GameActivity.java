package com.example.hannah.rpsnew;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

public class GameActivity extends AppCompatActivity {

    View resultView;
    Button rockBtn, paperBtn, scissorsBtn;
    TextView rounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);

        // UI Things
        Typeface museo700 = Typeface.createFromAsset(getAssets(), "museo700.otf");
        Typeface museo300 = Typeface.createFromAsset(getAssets(), "museo300.otf");

        rounds = (TextView) findViewById(R.id.rounds);      rounds.setTypeface(museo700);
        rockBtn = (Button) findViewById(R.id.rock);         rockBtn.setTypeface(museo300);
        paperBtn = (Button) findViewById(R.id.rock);        paperBtn.setTypeface(museo300);
        scissorsBtn = (Button) findViewById(R.id.rock);     scissorsBtn.setTypeface(museo300);
    }

    public void enableUI(boolean isEnabled) {
        if(isEnabled) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                    WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
        else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
    }

    public void createDialog() {
        LayoutInflater inflater = getLayoutInflater();
        this.resultView = inflater.inflate(R.layout.results, null);
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

}
