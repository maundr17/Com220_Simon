package com.maundr17.simon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/*
**  MainActivity is a simple HUB to start the game, look at high scores and exit
*/

public class MainActivity extends Activity implements View.OnClickListener {

    // declare references
    private ImageView startImageView;
    private ImageView scoresImageView;
    private ImageView creditsImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // get references
        startImageView = (ImageView) findViewById(R.id.startImageView);
        scoresImageView = (ImageView) findViewById(R.id.scoresImageView);
        creditsImageView = (ImageView) findViewById(R.id.creditsImageView);

        // set onclickListeners
        startImageView.setOnClickListener(this);
        scoresImageView.setOnClickListener(this);
        creditsImageView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.startImageView :
                Intent startIntent = new Intent(this, GameActivity.class);
                startActivity(startIntent);
                break;
            case R.id.scoresImageView:
                Intent highScoresIntent = new Intent(this, HighScoreActivity.class);
                startActivity(highScoresIntent);
                break;
            case R.id.creditsImageView:
                Intent creditsIntent = new Intent(this, CreditsActivity.class);
                startActivity(creditsIntent);
                break;
        }
    }
}
