//Created by george kafas
package com.example.kafas.connecttree;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import pl.droidsonroids.gif.GifTextView;

public class MainActivity extends AppCompatActivity {

    //public vars
    boolean player; //this is player 1 RED
    int[] boardArray;
    boolean clickable;
    int playedMoves;
    int player1Score=0,player2Score=0;
    MediaPlayer yellowPawnSound;
    MediaPlayer redPawnSound;
    //

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        yellowPawnSound = MediaPlayer.create(this, R.raw.bloops11);
        redPawnSound = MediaPlayer.create(this, R.raw.bloops12);
        initializGame();
    }

    public void initializGame(){
        boardArray = new int[9];
        clickable = true;
        player = true;
        playedMoves = 0;
        for(int i=0;i<boardArray.length;i++){
            boardArray[i]= i+3;
        }
    }

    public void showJumpScare(View view){
        GifTextView js = findViewById(R.id.jumpScare);
        //makeSceneTransparent(view);
        js.setAlpha(1f);
    }

    public void printWinner(View view){
        TextView winner = findViewById(R.id.announceWinnerTextView);
        LinearLayout winInterface = (LinearLayout) findViewById(R.id.winninginterface);
        GridLayout grid = (GridLayout)findViewById(R.id.grid);
        winInterface.setAlpha(1);
        grid.animate().alpha(0.2f).setDuration(300);
        pauseGame(view);
        if(player){
            player2Score++;
            winner.setText("2nd PLAYER WINS");
            winner.setTextColor(0xffffff00);
        }else {
            player1Score++;
            winner.setText("1st PLAYER WINS");
            winner.setTextColor(0xffcc0000);
        }
        scoreUpdate(view);
    }

    public void printTie(View view){
        TextView againMessage = findViewById(R.id.againMessage);
        LinearLayout againLayout = (LinearLayout)findViewById((R.id.againLayout));
        againLayout.setAlpha(1f);
        againLayout.setScaleX(1);
        againLayout.setScaleY(1);
        againLayout.animate().alpha(0f).scaleXBy(10).scaleYBy(10).setDuration(700);
    }

    public void scoreUpdate(View view){
        TextView score1 = findViewById(R.id.score1);
        TextView score2 = findViewById(R.id.score2);
        score1.setText("player 1:"+Integer.toString(player1Score));
        score2.setText("player 2:"+Integer.toString(player2Score));
    }


    public void pauseGame(View view){
        clickable = !clickable;
    }



    public void gameLogic(View view){ //will be called once in every player move
        //horizontal search
        int i = 0;
        System.out.println(playedMoves);
        if(boardArray[i]==boardArray[i+1]){
            if(boardArray[i+1]==boardArray[i+2]){
                printWinner(view);
            }
        }
        i = 3;
        if(boardArray[i]==boardArray[i+1]){
            if(boardArray[i+1]==boardArray[i+2]){
                printWinner(view);
            }
        }
        i = 6;
        if(boardArray[i]==boardArray[i+1]){
            if(boardArray[i+1]==boardArray[i+2]){
                printWinner(view);
            }
        }
        //vertical search
        i = 0;
        if(boardArray[i]==boardArray[i+3]){
            if(boardArray[i+3]==boardArray[i+6]){
                printWinner(view);
            }
        }
        i = 1;
        if(boardArray[i]==boardArray[i+3]){
            if(boardArray[i+3]==boardArray[i+6]){
                printWinner(view);
            }
        }
        i = 2;
        if(boardArray[i]==boardArray[i+3]){
            if(boardArray[i+3]==boardArray[i+6]){
                printWinner(view);
            }
        }
        //diagonal search
        i=0;
        if(boardArray[i]==boardArray[i+4]){
            if(boardArray[i+4]==boardArray[i+8]){
                printWinner(view);
            }
        }
        i=2;
        if(boardArray[i]==boardArray[i+2]){
            if(boardArray[i+2]==boardArray[i+4]){
                printWinner(view);
            }
        }
        if(playedMoves==9 && clickable){
            //showJumpScare(view);
            pauseGame(view);
            printTie(view);
            newGame(view);
        }
    }

    public void setMove(int pos){
        if(player){
            boardArray[pos] = 1;
        }else{
            boardArray[pos] = 2;
        }
    }

    public void switchPlayer(){
        player = !player;
    }

    public void placePawn(View view){
        ImageView pawn = (ImageView) view;
        int tag = Integer.parseInt(pawn.getTag().toString());
        if(player){
            if(pawn.getDrawable() == null && clickable==true) {
                pawn.setAlpha(0f);
                pawn.setImageDrawable(getResources().getDrawable(R.drawable.red));
                pawn.animate().alpha(1f).rotationXBy(360).setDuration(300);
                yellowPawnSound.start();
                setMove(tag);
                switchPlayer();
                playedMoves++;
                gameLogic(view);
            }
        }else{
            if(pawn.getDrawable() == null && clickable==true) {
                pawn.setAlpha(0f);
                pawn.setImageDrawable(getResources().getDrawable(R.drawable.yellow));
                pawn.animate().alpha(1f).rotationYBy(360).setDuration(300);
                redPawnSound.start();
                setMove(tag);
                switchPlayer();
                playedMoves++;
                gameLogic(view);
            }
        }
    }
    public void newGame(View view){
        if(!clickable) {
            LinearLayout winInterface = (LinearLayout) findViewById(R.id.winninginterface);
            GridLayout grid = (GridLayout)findViewById(R.id.grid);
            clearGrid(view);
            initializGame();
            winInterface.setAlpha(0f);
            grid.animate().alpha(1f).setDuration(300);
        }
    }

    public void clearGrid(View view){
        ImageView topL = (ImageView) findViewById(R.id.topLeft);
        ImageView topM = (ImageView) findViewById(R.id.topMid);
        ImageView topR = (ImageView) findViewById(R.id.topRight);
        ImageView midL = (ImageView) findViewById(R.id.midLeft);
        ImageView midM = (ImageView) findViewById(R.id.midMid);
        ImageView midR = (ImageView) findViewById(R.id.midRight);
        ImageView botL = (ImageView) findViewById(R.id.bottomLeft);
        ImageView botM = (ImageView) findViewById(R.id.bottomMid);
        ImageView botR = (ImageView) findViewById(R.id.bottomRight);
        topL.setImageDrawable(null);
        topM.setImageDrawable(null);
        topR.setImageDrawable(null);
        midL.setImageDrawable(null);
        midM.setImageDrawable(null);
        midR.setImageDrawable(null);
        botL.setImageDrawable(null);
        botM.setImageDrawable(null);
        botR.setImageDrawable(null);
    }
}
