package com.example.b.stacker;

/**
 * Created by b on 7/25/17.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;


public class Stacker {
    //Bitmap to get character from image
    private Bitmap bitmap;
    private Bitmap bitmap2;
    private Bitmap bitmap3;


    //private int lives;
    private int levels;
    private int lbar;
    private int rbar;
    private int xlpos;
    private int xrpos;
    private int ypos;
    private int midBar;
    private int xmpos;


    private int movingr;

    private int[] board = new int[10];
    private int[] placeHolder = new int[10];
    private int maxY;
    private int minY;
    private int maxX;
    private int minX;
    private int lives;






    //constructor
    public Stacker(Context context, int screenX, int screenY, int y, int l) {

        lives = l;//takes in lives to change how the items interact with the array


        levels = 20;

        ypos=y;
        xlpos= (int) (Math.random() * 6);//random start pos
        xmpos = xlpos+1;
        xrpos=xlpos+2;

        int random = (int) (Math.random() * 10);


        if(random > 5){//random start moving direction

            movingr=1;
        }
        else {
            movingr = 0;
        }



        bitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.square4);
        bitmap2= BitmapFactory.decodeResource(context.getResources(), R.drawable.square4);
        bitmap3= BitmapFactory.decodeResource(context.getResources(), R.drawable.square4);


        //get the height and width of the screen
        maxY=screenY-bitmap.getHeight();
        minY = 0;

        maxX=screenX +bitmap.getWidth();
        minX=0;









        //create an array of x positions and set place holder to 0s
        for (int j = 0; j < 10; j++) {
            placeHolder[j] = 0;

            if (j == 0) {
                board[j] = 0;
            } else {
                board[j] = (maxX / 10)*j;
            }





        }
        //set initial positions at the start to the first 3 x positions after random num
        lbar=board[xlpos];
        midBar=board[xmpos];
        rbar =board[xrpos];

    }




    //update coordinates of block
    public void update() {
        //updating x coordinate



        if (xrpos == 9 || xmpos==9)
            movingr = 0;
        else if (xlpos == 0 || xmpos ==0)
            movingr = 1;


        //have blocks traverse array of x positions if moving right
        if (movingr==1 && lives == 3) {
            lbar=board[xlpos];
            rbar=board[xrpos];
            midBar=board[xmpos];


            placeHolder[xlpos]=1;
            placeHolder[xrpos]=1;
            placeHolder[xmpos]=1;

            if(!(xlpos==0)&& !(xrpos==9)){//making sure everywhere else that isnt 1 is 0
                placeHolder[xlpos - 1] = 0;
                placeHolder[xrpos + 1] = 0;
            }
            else if ((xrpos==9) || (xlpos==0)){

                placeHolder[3]=0;
                placeHolder[6]=0;

            }

            xlpos++;
            xrpos++;
            xmpos++;
        } else if (movingr == 0 && lives == 3) {//traverse array of x positions if moving left
            lbar=board[xlpos];
            rbar=board[xrpos];
            midBar=board[xmpos];


            placeHolder[xlpos]=1;
            placeHolder[xrpos]=1;
            placeHolder[xmpos]=1;

            if(!(xlpos==0)&& !(xrpos==9)){//making sure everywhere else that isnt 1 is 0
                placeHolder[xlpos - 1] = 0;
                placeHolder[xrpos + 1] = 0;
            }
            else if ((xrpos==9) || (xlpos==0)){

                placeHolder[3]=0;
                placeHolder[6]=0;

            }

            xlpos--;
            xrpos--;
            xmpos--;
        }
        else if (movingr==1 && lives == 2) {//dont need right pos because only two lives
            lbar=board[xlpos];
            midBar=board[xmpos];
            xrpos=-1;//to avoid using this value


            placeHolder[xlpos]=1;
            placeHolder[xmpos]=1;

            if(!(xlpos==0)&& !(xmpos==9)){//making sure everywhere else that isnt 1 is 0
                placeHolder[xlpos - 1] = 0;
                placeHolder[xmpos + 1] = 0;
            }
            else if ((xmpos==9) || (xlpos==0)){

                placeHolder[2]=0;
                placeHolder[7]=0;

            }

            xlpos++;
            xmpos++;
        }
        else if (movingr == 0 && lives == 2) {//traverse array of x positions if moving left
            lbar=board[xlpos];
            midBar=board[xmpos];
            xrpos=-1;//to avoid using this value


            placeHolder[xlpos]=1;
            placeHolder[xmpos]=1;

            if(!(xlpos==0)&& !(xmpos==9)){//making sure everywhere else that isnt 1 is 0
                placeHolder[xlpos - 1] = 0;
                placeHolder[xmpos + 1] = 0;
            }
            else if ((xmpos==9) || (xlpos==0)){

                placeHolder[2]=0;
                placeHolder[7]=0;

            }

            xlpos--;
            xmpos--;
        }
        else if (movingr==1 && lives == 1) {//dont need right pos or left pos because only 1 life
            midBar=board[xmpos];
            xlpos=-1;//to avoid using these values
            xrpos=-1;



            placeHolder[xmpos]=1;

            if(!(xmpos==0)&& !(xmpos==9)){//making sure everywhere else that isnt 1 is 0
                placeHolder[xmpos - 1] = 0;
                placeHolder[xmpos + 1] = 0;
            }
            else if ((xmpos==9) || (xmpos==0)){

                placeHolder[1]=0;
                placeHolder[8]=0;

            }

            xmpos++;
        }
        else if (movingr == 0 && lives == 1) {//traverse array of x positions if moving left
            midBar=board[xmpos];
            xlpos=-1;//to avoid using these values
            xrpos=-1;



            placeHolder[xmpos]=1;

            if(!(xmpos==0)&& !(xmpos==9)){//making sure everywhere else that isnt 1 is 0
                placeHolder[xmpos - 1] = 0;
                placeHolder[xmpos + 1] = 0;
            }
            else if ((xmpos==9) || (xmpos==0)){

                placeHolder[1]=0;
                placeHolder[8]=0;

            }

            xmpos--;
        }



    }//closes update
    public void fallOff(int pos){

        placeHolder[pos] =0;

    }


    public void setLives(int l){


        lives = l;
    }

    public void emptyPlaceHolder(){

        for(int i = 0; i<10; i++){

            placeHolder[i] =0;

        }

    }


    public Bitmap getBitmap() {
        return bitmap;
    }
    public Bitmap getBitmap2() {
        return bitmap2;
    }

    public Bitmap getBitmap3() {
        return bitmap3;
    }


    /*public int getLives() {
        return lives;
    }//number of blocks being painted*/

    public int getLbar() {
        return lbar ;
    }//position of l bar

    public int getRbar() {
        return rbar;
    }//position of r bar


    public int getXC(){return midBar;}//position of mid bar

    public int getYpos(){//to set y position on screen

        return (maxY/levels)*ypos;

    }

    public int getBoardValue(int finalPosition){//to check player positions


        return placeHolder[finalPosition];
    }

    public int finalXL(){//recently changed all if statements to account for out of bounds array exception: dont think this will work, try using nested ifs


        if (movingr==1 && !(xlpos==0) && !(xmpos==0)){

            return xlpos-1;
        }
        else if (movingr == 0 && !(xrpos == 9) && !(xmpos ==9)) {

            return xlpos + 1;
        }
        else if(xrpos ==9 && xmpos ==9){
            return xlpos -1;
        }
        else
            return xlpos +1;

    }



    public int finalXM(){//have to account for offset at the end of the update

        if (movingr==1 && !(xlpos==0) && !(xmpos==0)){//if moving right and not at the beginning

            return xmpos-1;
        }
        else if (movingr == 0 && !(xrpos == 9) && !(xmpos ==9)) {//if moving left and not at the end

            return xmpos +1;
        }
        else if(xrpos ==9 && xmpos ==9){//at the right end
            return xmpos -1;
        }
        else//at the left end
            return xmpos +1;

    }

    public int finalXR(){


        if (movingr==1 && !(xlpos==0) && !(xmpos==0)){

            return xrpos-1;
        }
        else if (movingr == 0 && !(xrpos == 9) && !(xmpos ==9)) {

            return xrpos+1;
        }
        else if(xrpos ==9 && xmpos ==9){
            return xrpos-1;
        }
        else
            return xrpos+1;

    }

//changed all ors to && in finals and removed +300 in bars!!!!!!!!

    public int getLives(){

        return lives;
    }

    public int finalLbar(){

        if(movingr==1)
        {
            return board[finalXL()];
        }
        else if(movingr ==0) {
            return board[finalXL()];
        }
        else
            return 1;

    }
    public int finalMidBar(){

        if(movingr==1)
        {
            return board[finalXM()];
        }
        else if(movingr==0) {
            return board[finalXM()];
        }
        else
            return 1;
    }

    public int finalRbar(){

        if(movingr ==1)
        {
            return board[finalXR()];
        }
        else if (movingr ==0) {
            return board[finalXR()];
        }
        else
            return 1;

    }
    public int getBoardPosition(int p){

        return board[p];

    }






}