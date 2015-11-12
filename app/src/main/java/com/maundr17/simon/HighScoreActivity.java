package com.maundr17.simon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScoreActivity extends Activity {
    private int score;
    private TextView scoreView;
    private Button playAgainButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_high_score);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            score = extras.getInt("SCORE_VALUE");
        }

        scoreView = (TextView) findViewById(R.id.scoreTextId);
        scoreView.setText("Your Score is : " + score);

        playAgainButton = (Button) findViewById(R.id.playAgainButtonId);

        playAgainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent restartIntent = new Intent(getApplicationContext(), GameActivity.class);
                startActivity(restartIntent);
            }
        });
    }

}
