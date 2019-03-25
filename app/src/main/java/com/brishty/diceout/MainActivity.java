package com.brishty.diceout;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG=MainActivity.class.getSimpleName();

    Random rand;

    //field to hold the roll result text
    TextView rollResult;

    //field to hold the score
    int score;

    //field to hold the die value
    int die1;
    int die2;
    int die3;

    //field to hold the score text
    TextView scoreText;

    //Array to hold all these dice values
    ArrayList <Integer> dice;

    //ArrayList to hold all three dice images
    ArrayList <ImageView> diceImageViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor("#8a000000"));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                rollDice(view);
            }
        });

        //set initial score
        score = 0;

        //link instances to widgets in the activity view
        rollResult = (TextView)findViewById(R.id.roll_result);
        scoreText = (TextView) findViewById(R.id.scoreText);

        //create greeting
        Toast.makeText(this, "Welcome to DiceOut", Toast.LENGTH_SHORT).show();
        rand = new Random();

        //create ArrayList container for the dice values
        dice = new ArrayList <Integer>();

        ImageView die1Image = (ImageView) findViewById(R.id.die1_image);
        ImageView die2Image = (ImageView) findViewById(R.id.die2_image);
        ImageView die3Image = (ImageView) findViewById(R.id.die3_image);

        diceImageViews = new ArrayList<ImageView>();
        diceImageViews.add(die1Image);
        diceImageViews.add(die2Image);
        diceImageViews.add(die3Image);

    }

    public void rollDice (View v) {
        rollResult.setText("Clicked");

        //roll dice
        die1 = rand.nextInt(6) + 1;
        die2 = rand.nextInt(6) + 1;
        die3 = rand.nextInt(6) + 1;

        //set dice value into an ArrayList
        dice.clear();
        dice.add(die1);
        dice.add(die2);
        dice.add(die3);

        for(int dieOfSet = 0; dieOfSet < 3; dieOfSet++){
            String imageName = "dice_" + dice.get(dieOfSet) + ".PNG";
            Log.i(TAG,"imageName "+imageName);

            try{
                InputStream stream = getAssets().open(imageName);
                Drawable d = Drawable.createFromStream(stream, null);
                diceImageViews.get(dieOfSet).setImageDrawable(d);
                Log.i(TAG,"set image ");
            } catch (IOException e){
                Log.e(TAG,"IOException "+e.getMessage());
                e.printStackTrace();
            }
        }

        //Build message with the result
        String msg;
        if (die1 == die2 && die1 == die3) {
            //Triples
            int scoreDelta = die1 * 100;
            msg = "You rolled a triple " + die1 + "! You score " + scoreDelta + " points!";
            score += scoreDelta;
        } else if (die1 == die2 || die1 == die3 || die2 == die3) {
            //double
            int scoreDelta = die1 * 50;
            msg = "You rolled a double " + die1 + "! You score " + scoreDelta + " points!";
            score += 50;
        } else {
            msg = "You didn't score this roll. Try again!";
        }

        Log.i(TAG,"msg " + msg);
        //update the app to display the result message
        rollResult.setText(msg);
        scoreText.setText("Score: " + score);
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
}
