package com.example.hannah.rpsnew;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;

public class Done_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done_);

        ArrayList<String> sl = getIntent().getStringArrayListExtra("results");
        TextView tv =(TextView) findViewById(R.id.result);
        tv.setText("Overall "+sl.get(4));

        TextView pick =(TextView) findViewById(R.id.pick);
        pick.setText("Your pick,opponent pick "+sl.get(0));

        TextView rs =(TextView) findViewById(R.id.resulting);
        rs.setText("Result "+sl.get(1));

        TextView my =(TextView) findViewById(R.id.myscore);
        my.setText("Your score "+sl.get(2));

        TextView ur =(TextView) findViewById(R.id.urscore);
        ur.setText("Opponent "+sl.get(3));


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_done_, menu);
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
}
