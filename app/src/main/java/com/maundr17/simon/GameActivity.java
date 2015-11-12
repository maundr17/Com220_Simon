package com.maundr17.simon;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class GameActivity extends Activity implements View.OnTouchListener {

    private TextView roundText;
    private TextView scoreText;

    private Button topLeftButton;
    private Button topRightButton;
    private Button bottomLeftButton;
    private Button bottomRightButton;

    private MediaPlayer topLeftPlayer;
    private MediaPlayer topRightPlayer;
    private MediaPlayer bottomLeftPlayer;
    private MediaPlayer bottomRightPlayer;

    private ArrayList<Integer> sequence;

    private int round = 1;
    private int score = 0;
    private int currentIndex = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_game);

        roundText = (TextView) findViewById(R.id.roundText);
        roundText.setText("Round : " + 1);

        scoreText = (TextView) findViewById(R.id.scoreText);
        scoreText.setText("Score  : " + score);

        topLeftButton = (Button) findViewById(R.id.topLeftButton);
        topRightButton = (Button) findViewById(R.id.topRightButton);
        bottomLeftButton = (Button) findViewById(R.id.bottomLeftButton);
        bottomRightButton = (Button) findViewById(R.id.bottomRightButton);


        topLeftButton.setOnTouchListener(this);
        topRightButton.setOnTouchListener(this);
        bottomLeftButton.setOnTouchListener(this);
        bottomRightButton.setOnTouchListener(this);

        topLeftPlayer = MediaPlayer.create(this, R.raw.sound1);
        topRightPlayer = MediaPlayer.create(this, R.raw.sound2);
        bottomLeftPlayer = MediaPlayer.create(this, R.raw.sound3);
        bottomRightPlayer = MediaPlayer.create(this, R.raw.sound4);

        sequence = new ArrayList<>();

        startGame(round);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        topLeftPlayer.release();
        topRightPlayer.release();
        bottomLeftPlayer.release();
        bottomRightPlayer.release();
    }

    public void startGame(int round) {
        currentIndex = 0;
        sequence.clear();

        roundText.setText("Round : " + round);

        createSequence(sequence, round);
        LogSequence(sequence);
        playSequence(sequence);
    }

    public void LogSequence(ArrayList<Integer> sequence) {
        Iterator<Integer> sequenceIterator = sequence.iterator();

        while(sequenceIterator.hasNext()) {
            int nextInSeq = sequenceIterator.next();
            Log.v("Round " + 1 + " Sequence :",+nextInSeq+"");
        }
    }

    public void createSequence(ArrayList<Integer> sequence, int length) {
        Random rand = new Random();
        for(int i = 0; i<= length; i++) {
            int randNum = rand.nextInt(4);
            sequence.add(randNum);
        }
    }

    public void checkSequence(ArrayList<Integer> sequence, int num) {
        if (sequence != null) {
            if (sequence.get(currentIndex) == num) {
                Log.v("CHECK", "correct");
                addScore();

                if(currentIndex == sequence.size() - 1){
                    round++;
                    startGame(round);
                } else if (currentIndex < sequence.size() - 1) {
                    currentIndex++;
                }
            } else {
                Log.v("CHECK", "incorrect");
                loseGame();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.topLeftButton) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                    topLeftButton.setPressed(false);
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    topLeftPlayer.start();
                    topLeftButton.setPressed(true);
                    checkSequence(sequence, 0);
            }
        } else if (v.getId() == R.id.topRightButton) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                    topRightButton.setPressed(false);
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    topRightPlayer.start();
                    topRightButton.setPressed(true);
                    checkSequence(sequence, 1);
            }
        } else if (v.getId() == R.id.bottomLeftButton) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                    bottomLeftButton.setPressed(false);
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    bottomLeftPlayer.start();
                    bottomLeftButton.setPressed(true);
                    checkSequence(sequence, 2);
            }
        } else if (v.getId() == R.id.bottomRightButton) {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                    bottomRightButton.setPressed(false);
            } else if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    bottomRightPlayer.start();
                    bottomRightButton.setPressed(true);
                    checkSequence(sequence, 3);
            }
        }

        return false;
    }

    public void pressSimonButton(int i) {
        switch (i) {
            case 0:
                topLeftPlayer.start();
                topLeftButton.setPressed(true);
                break;
            case 1:
                topRightPlayer.start();
                topRightButton.setPressed(true);
                break;
            case 2:
                bottomLeftPlayer.start();
                bottomLeftButton.setPressed(true);
                break;
            case 3:
                bottomRightPlayer.start();
                bottomRightButton.setPressed(true);
                break;
        }
    }

    public void depressSimonButton(int i) {
        switch (i) {
            case 0:
                topLeftButton.setPressed(false);
                break;
            case 1:
                topRightButton.setPressed(false);
                break;
            case 2:
                bottomLeftButton.setPressed(false);
                break;
            case 3:
                bottomRightButton.setPressed(false);
                break;
        }
    }

    public Runnable pressRunnable(final int i) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                pressSimonButton(i);
            }
        };

        return r;
    }

    public Runnable depressRunnable(final int i) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                depressSimonButton(i);
            }
        };

        return r;
    }

    public void playSequence(final ArrayList<Integer> sequence) {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep((long) 1000);
                    for (Integer i : sequence) {
                        Thread.sleep((long) 500);
                        runOnUiThread(pressRunnable(i));
                        Thread.sleep((long) 500);
                        runOnUiThread(depressRunnable(i));
                    }
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(r).start();
    }

    public void addScore() {
        score = score + 100;
        scoreText.setText("Score  : " + score);
    }

    public void loseGame() {
        Intent intent = new Intent(this, HighScoreActivity.class);
        intent.putExtra("SCORE_VALUE", score);
        startActivity(intent);
    }
}


