package com.example.hannah.rpsnew;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class StartActivity extends AppCompatActivity {

    // UI Things
    LinearLayout startView;
    ProgressBar loadingView;
    Context context;
    Button connectBtn, startBtn;
    Spinner roundsDropdown;
    //--------------------

    View connectView;
    protected Intent play;
    public static Communicator communicator = new Communicator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);

        // UI Things

        Typeface museo700 = Typeface.createFromAsset(getAssets(), "museo700.otf");
        Typeface museo300 = Typeface.createFromAsset(getAssets(), "museo300.otf");

        TextView title = (TextView) findViewById(R.id.title);
        title.setTypeface(museo300);

        startView = (LinearLayout) findViewById(R.id.startLayout);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(LAYOUT_INFLATER_SERVICE);
        loadingView = (ProgressBar) inflater.inflate(R.layout.loading, null);
        loadingView.getIndeterminateDrawable().setColorFilter(Color.WHITE, android.graphics.PorterDuff.Mode.MULTIPLY);

        context = this;
        connectBtn = (Button) findViewById(R.id.connect);   connectBtn.setTypeface(museo700);
        startBtn = (Button) findViewById(R.id.start);       startBtn.setTypeface(museo700);

        roundsDropdown = (Spinner) findViewById(R.id.roundsDropdown);
        String[] items = new String[]{"race to 5", "race to 10"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        roundsDropdown.setAdapter(adapter);

        this.play = new Intent(this, GameActivity.class);


        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog();
            }
        });

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rounds;
                String selected = (String) roundsDropdown.getSelectedItem();
                if(selected.equals("race to 5"))
                    rounds =5;
                else rounds = 10;

                new waitConnection().execute(rounds);

                Toast.makeText(context, "Now waiting for connections", Toast.LENGTH_LONG).show();
            }
        });

    }


    public void createDialog() {
        LayoutInflater inflater = getLayoutInflater();
        this.connectView = inflater.inflate(R.layout.connect, null);
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);

        dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Dis will connect.
                EditText et = (EditText) connectView.findViewById(R.id.address);
                new startConnection().execute(et.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.setView(connectView);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class waitConnection extends AsyncTask<Integer,Void,Void> {


        @Override
        protected Void doInBackground(Integer... params) {
            Log.i("jet", "Waiting For connection");

            StartActivity.communicator.TryGame(params[0]);

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //startView.removeView(loadingView);
            super.onPostExecute(aVoid);
            startActivity(play);
        }
    }

    public class startConnection extends AsyncTask<String,Void,Void> {


        @Override
        protected Void doInBackground(String... params) {
            Log.i("jet", "Trying to connect");
            StartActivity.communicator.TryHandshake(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            //startView.removeView(loadingView);
            super.onPostExecute(aVoid);
            startActivity(play);
        }
    }

}
