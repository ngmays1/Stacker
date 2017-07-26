package com.example.b.stacker;

/**
 * Created by b on 7/25/17.
 */
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import static android.support.v4.content.ContextCompat.startActivity;


/**
 * Created by nmays293 on 4/4/2017.
 */

public class GameView extends SurfaceView implements Runnable {

    //is the game playing
    volatile boolean playing;

    //make Stacker thread
    private Thread gameThread = null;

    //adding the player
    //private Player player;
    private Stacker[] stacker = new Stacker[19];

    private int speed;
    private int player;
    private TextView scoreView;


    //drawing objects
    private Paint paint;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;
    private int score;
    private int lives;
    private ArrayList<Stacker> stackerList = new ArrayList<Stacker>();
    private int x;

    //constructer
    public GameView(Context context, int screenX, int screenY) {
        super(context);

        //start at player 18 (bottom of the screen)
        player = 18;
        //Log.d("Info", "Game view constructor is called!!!!!!!" );
        score = 0;
        lives = 3;

        x = screenX / 10;


        //initialize player
        //  player = new Player(context);

        //player is being sent as 0
        //intialize all players(1 for each y position)

        for (int i = 0; i < 19; i++) {
            stacker[i] = new Stacker(context, screenX, screenY, i, lives);

        }


        speed = 500;

        //initialize drawing objects
        surfaceHolder = getHolder();
        paint = new Paint();


        //buttonPause = (ImageButton) findViewById(R.id.buttonPause);
        //adding click actionlistener
        //buttonPause.setOnClickListener((OnClickListener) this);


    }


    @Override
    public void run() {
        while (playing) {
            //update the frame

            update();
            draw();
            //draw the frame


        }
    }

    private void update() {
        //updates player position
        stacker[player].update();
        //Log.d("lbar: ", "" + stacker[player].getLbar());


        try {
            gameThread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
            {
            }
        }

    }

    private void draw() {

        //check if surface is valid
        /**
         * Nic added from first if


         if(lives ==0 && surfaceHolder.getSurface().isValid()){

         canvas = surfaceHolder.lockCanvas();
         canvas.drawColor(Color.BLUE);

         paint.setTextSize(150);
         paint.setTextAlign(Paint.Align.CENTER);

         int yPos = (int) ((canvas.getHeight()/2) - ((paint.descent())/2));
         canvas.drawText("Score: "+getScore(), canvas.getWidth()/2, yPos, paint);
         }
         else */if (lives > 0 && surfaceHolder.getSurface().isValid()) {
            //lock the canvas
            canvas = surfaceHolder.lockCanvas();
            //drawing background color for canvas
            canvas.drawColor(Color.BLACK);
            //draw player
            //  canvas.drawBitmap(player.getBitmap(),player.getX(),player.getY(),paint);
            //canvas.drawBitmap(stacker.getBitmap(), stacker.getLbar(),50, paint);

            //try storing stackers and painting them from this list


            for (int i = 0; i < stackerList.size(); i++) {

                if (stackerList.get(i).getLives() == 3) {


                    canvas.drawBitmap(stackerList.get(i).getBitmap(), stackerList.get(i).finalLbar(), stackerList.get(i).getYpos(), paint);
                    canvas.drawBitmap(stackerList.get(i).getBitmap2(), stackerList.get(i).finalMidBar(), stackerList.get(i).getYpos(), paint);
                    canvas.drawBitmap(stackerList.get(i).getBitmap3(), stackerList.get(i).finalRbar(), stackerList.get(i).getYpos(), paint);
                    //Log.d("player: " + i, " x position: " + stackerList.get(i).finalLbar() );
                    //Log.d("Movingr player: " + i, " true/false: " + stackerList.get(i).getMovingr());


                } else if (stackerList.get(i).getLives() == 2) {//can be drawn based off mid or left position

                    if (stackerList.get(i).getBoardValue(stackerList.get(i).finalXL()) == 1) {//if the left hasnt fallen off
                        canvas.drawBitmap(stackerList.get(i).getBitmap(), stackerList.get(i).finalLbar(), stackerList.get(i).getYpos(), paint);
                        canvas.drawBitmap(stackerList.get(i).getBitmap2(), stackerList.get(i).finalMidBar(), stackerList.get(i).getYpos(), paint);
                        //Log.d("player ypos: ", "" + stackerList.get(i).getYpos() + "  Lives: 2");
                    } else if (stackerList.get(i).getBoardValue(stackerList.get(i).finalXL()) == 0) {//if the left has fallen off

                        //when there are two lives
                        //bitmap3 is being drawn 1 to the left of midbar

                        //canvas.drawBitmap(stackerList.get(i).getBitmap(), stackerList.get(i).getLbar(), stackerList.get(i).getYpos(), paint);
                        canvas.drawBitmap(stackerList.get(i).getBitmap2(), stackerList.get(i).finalMidBar(), stackerList.get(i).getYpos(), paint);
                        canvas.drawBitmap(stackerList.get(i).getBitmap3(), stackerList.get(i).getBoardPosition(stackerList.get(i).finalXL() + 2), stackerList.get(i).getYpos(), paint);

                        //Log.d("final XL ", ""+stackerList.get(i).finalXL());
                        //Log.d("final XL+2 ", ""+stackerList.get(i).finalXL()+2);

                    }

                } else if (stackerList.get(i).getLives() == 1) {// all have to be drawn based on mid position(only relevant position)
                    Log.d("hello: ", "i made it to 1");


                    if (stackerList.get(i).getBoardValue(stackerList.get(i).finalXM()) == 1) {
                        canvas.drawBitmap(stackerList.get(i).getBitmap2(), stackerList.get(i).finalMidBar(), stackerList.get(i).getYpos(), paint);
                        Log.d("playerXM: " + i, "" + "  Lives: 1");
                    } else if (stackerList.get(i).getBoardValue(stackerList.get(i).finalXM() - 1) == 1) {
                        canvas.drawBitmap(stackerList.get(i).getBitmap(), stackerList.get(i).getBoardPosition(stackerList.get(i).finalXM() - 1), stackerList.get(i).getYpos(), paint);
                        Log.d("playerXL: " + i, "" + "  Lives: 1");
                    } else if (stackerList.get(i).getBoardValue(stackerList.get(i).finalXM() + 1) == 1) {
                        canvas.drawBitmap(stackerList.get(i).getBitmap3(), stackerList.get(i).getBoardPosition(stackerList.get(i).finalXM() + 1), stackerList.get(i).getYpos(), paint);
                        Log.d("playerXR: " + i, "" + "  Lives: 1");
                    }

                }


            }//closes for loop


            //drawing players dependent on lives
            if (lives == 3) {

                canvas.drawBitmap(stacker[player].getBitmap(), stacker[player].getLbar(), stacker[player].getYpos(), paint);
                canvas.drawBitmap(stacker[player].getBitmap2(), stacker[player].getXC(), stacker[player].getYpos(), paint);
                canvas.drawBitmap(stacker[player].getBitmap3(), stacker[player].getRbar(), stacker[player].getYpos(), paint);
            } else if (lives == 2) {
                canvas.drawBitmap(stacker[player].getBitmap(), stacker[player].getLbar(), stacker[player].getYpos(), paint);
                canvas.drawBitmap(stacker[player].getBitmap2(), stacker[player].getXC(), stacker[player].getYpos(), paint);
            } else if (lives == 1) {
                canvas.drawBitmap(stacker[player].getBitmap2(), stacker[player].getXC(), stacker[player].getYpos(), paint);
            }

            //}
            //unlock canvas

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    public void pause() {
        //when the game has been pasued
        //set plaing to false
        playing = false;
        try {
            //stop thread
            gameThread.join();
        } catch (InterruptedException e) {
        }
    }

    public void resume() {
        //when the game is resumed
        //start the thread again
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();

    }

    public void increaseSpeed() {//speed increases as you play
        if(speed==50||speed==25)
            speed=25;
        else
            speed -= 25;

    }

    public void incrementScore() {//increment score every time you switch to a different player

        score++;

    }

    public void decrementPlayer() {//decrement player to go up the screen

        player--;

    }

    public void setPlayer(int i) {

        player = i;
    }

    public void setScore(int i) {

        score = i;

    }

    public void resetSpeed() {

        speed = 500;
    }

    public void resetLives() {
        lives = 3;

    }

    public void resetPlaceHolder() {

        for (int i = 0; i < 19; i++) {

            stacker[i].emptyPlaceHolder();

        }

    }

    public void emptyStackerList() {


        while (!(stackerList.isEmpty())) {

            stackerList.remove(0);

        }
    }
    public void setStackerLives(int player){

        stacker[player].setLives(lives);
    }

    public void addStacker(int p) { //adds players to the list to be drawn to the canvas

        stackerList.add(stacker[p]);
        Log.d("player: ", "" + p + "has been added");


    }

    public void checkPlayerBelow(int thisPlayer, int playerBelow) {
        //x positions are being incremented at the end of stacker update, so getter methods are recieving +1/-1 values
        //therefore decrement the values by 1 to get actual positions
        //have to account for ++/-- at the end of final update for each stacker[player]
        //meaning if going right (++) have to subtract at the end
        //if going left do the opposite otherwise getter will return 1 position away from final position


        Log.d("Info on Lives:  ", "" + lives);

        //stacker[player].setLives(lives);//have to pass lives to stacker object (because set as 3 at instantiation)

        //when a life is taken away because of a players offset, set the values that were off to 0
        if (lives == 3) {

            if (!(stacker[thisPlayer].getBoardValue(stacker[thisPlayer].finalXL()) == stacker[playerBelow].getBoardValue(stacker[thisPlayer].finalXL()))) {
                stacker[thisPlayer].fallOff(stacker[thisPlayer].finalXL());
                lives--;
                stacker[thisPlayer - 1].setLives(lives);
                stacker[thisPlayer].setLives(lives);
                Log.d("Fall off: ", "" + stacker[thisPlayer].finalXL() + " Fell Off");

            }
            if (!(stacker[thisPlayer].getBoardValue(stacker[thisPlayer].finalXM()) == stacker[playerBelow].getBoardValue(stacker[thisPlayer].finalXM()))) {
                stacker[thisPlayer].fallOff(stacker[thisPlayer].finalXM());
                lives--;
                stacker[thisPlayer - 1].setLives(lives);
                stacker[thisPlayer].setLives(lives);
                Log.d("Fall off: ", "" + stacker[thisPlayer].finalXM() + " Fell Off");

            }
            if (!(stacker[thisPlayer].getBoardValue(stacker[thisPlayer].finalXR()) == stacker[playerBelow].getBoardValue(stacker[thisPlayer].finalXR()))) {
                stacker[thisPlayer].fallOff(stacker[thisPlayer].finalXR());
                lives--;
                stacker[thisPlayer - 1].setLives(lives);
                stacker[thisPlayer].setLives(lives);
                Log.d("Fall off: ", "" + stacker[thisPlayer].finalXR() + " Fell Off");

            }


        } else if (lives == 2) {

            if (!(stacker[thisPlayer].getBoardValue(stacker[thisPlayer].finalXL()) == stacker[playerBelow].getBoardValue(stacker[thisPlayer].finalXL()))) {
                stacker[thisPlayer].fallOff(stacker[thisPlayer].finalXL());
                lives--;

                stacker[thisPlayer - 1].setLives(lives);
                stacker[thisPlayer].setLives(lives);
                Log.d("Fall off: ", "" + stacker[thisPlayer].finalXL() + " Fell Off");

            }
            if (!(stacker[thisPlayer].getBoardValue(stacker[thisPlayer].finalXM()) == stacker[playerBelow].getBoardValue(stacker[thisPlayer].finalXM()))) {
                stacker[thisPlayer].fallOff(stacker[thisPlayer].finalXM());

                lives--;

                stacker[thisPlayer - 1].setLives(lives);
                stacker[thisPlayer].setLives(lives);
                Log.d("Fall off: ", "" + stacker[thisPlayer].finalXM() + " Fell Off");

            }


        } else if (lives == 1) {


            if (!(stacker[thisPlayer].getBoardValue(stacker[thisPlayer].finalXM()) == stacker[playerBelow].getBoardValue(stacker[thisPlayer].finalXM()))) {
                stacker[thisPlayer].fallOff(stacker[thisPlayer].finalXM());
                lives--;

                stacker[thisPlayer - 1].setLives(lives);
                stacker[thisPlayer].setLives(lives);
                Log.d("Fall off: ", "" + stacker[thisPlayer].finalXM() + " Fell Off");

            }


        }
        stacker[thisPlayer - 1].setLives(lives);//need to tell next player that it has x amount of lives






       /*Log.d("This Player Xlpos:  ", "" + stacker[thisPlayer].finalXL() + ", board Values: " + stacker[thisPlayer].getBoardValue(stacker[thisPlayer].finalXL())
                + " " + stacker[thisPlayer].getBoardValue(stacker[thisPlayer].finalXM())+ " " + stacker[thisPlayer].getBoardValue(stacker[thisPlayer].finalXR()));

        Log.d("Info on Xlpos:  ", "" + stacker[playerBelow].finalXL() + ", board Values: " + stacker[playerBelow].getBoardValue(stacker[playerBelow].finalXL())
                + " " + stacker[thisPlayer].getBoardValue(stacker[playerBelow].finalXM())+ " " + stacker[playerBelow].getBoardValue(stacker[playerBelow].finalXR()));*/


        //Log.d("This Player Xmpos:  ", "" + stacker[thisPlayer].finalXM() + ", board Value: " + stacker[thisPlayer].getBoardValue(stacker[thisPlayer].finalXM()));
        //Log.d("Info on Xmpos:  ", "" + stacker[playerBelow].finalXM() + ", board Value: " + stacker[playerBelow].getBoardValue(stacker[playerBelow].finalXM()));


        // Log.d("This Player Xrpos:  ", "" + stacker[thisPlayer].finalXR() + ", board Value: " + stacker[thisPlayer].getBoardValue(stacker[thisPlayer].finalXR()));
        // Log.d("Info on Xrpos:  ", "" + stacker[playerBelow].finalXR() + ", board Value: " + stacker[playerBelow].getBoardValue(stacker[playerBelow].finalXR()));

        Log.d("break:  ", "       ");
        Log.d("break:  ", "       ");


    }


    public int getPlayer() {
        return player;

    }


    //these are for running score
    public int getScore() {


        return score;
    }

    public int getSpeed() {

        return speed;
    }

    public int getLives() {

        return lives;
    }


}