package com.example.b.stacker;

/**
 * Created by b on 7/25/17.
 */


        import android.content.Intent;
        import android.graphics.Point;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.ContextMenu;
        import android.view.Display;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.MotionEvent;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.AdapterView;
        import android.widget.Button;
        import android.widget.RelativeLayout;
        import android.widget.TextView;
        import android.widget.Toast;

        import static android.support.v4.content.ContextCompat.startActivity;


//gameover can be done in 2 ways... context menu or new activity. context menu can be implemented through block stop click. (if lives =0)

public class GameActivity extends AppCompatActivity {

    //declare gameview
    private GameView gameView;
    private RelativeLayout RL;
    private TextView scoreView;
    private Button pause;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //gets rid of title bar
        //unnecessary: done in manifest for whole project: kept just in case
        //this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //gets rid of notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //setting content view
        setContentView(R.layout.activity_game);



        //get the max points on the screeen
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);



        //Initialize game view object
        gameView = new GameView(this,size.x,size.y);




        //initialize GUI elements
        scoreView=(TextView) findViewById(R.id.Score);
        RL=(RelativeLayout) findViewById(R.id.lex);
        pause = (Button) findViewById(R.id.Pause);



        //setting gameView to the relative layout inside activity_game
        RL.addView(gameView);
        //gameView.run();
        pause.getBackground().setAlpha(128);  // 50% transparent


    }



    public void Pause(View v){
        //pause the game
        gameView.pause();
        //registering pause button for context menu
        registerForContextMenu(pause);

        //opening the context menu
        openContextMenu(pause);


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        //caling superclass constructor
        super.onCreateContextMenu(menu, v, menuInfo);
        //creating new menu inflater object
        MenuInflater inflater = getMenuInflater();
        //setting menu inflater to popup_menu (inside menu directory)
        inflater.inflate(R.menu.popup_menu, menu);
    }


    //when items in popup_menu are selected these will happen
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.item1://show the score, resume the game
                Toast.makeText(this, ""+scoreView.getText(), Toast.LENGTH_SHORT).show();
                gameView.resume();

                return true;
            case R.id.item2://resume the game
                gameView.resume();

                return true;
            case R.id.item3://reset the game: has to empty stacker placeholder array
                gameView.setPlayer(18);
                gameView.setScore(0);
                gameView.resetSpeed();
                gameView.resetLives();
                gameView.resetPlaceHolder();
                gameView.emptyStackerList();
                gameView.resume();
                startActivity(new Intent(this,GameActivity.class));


                return true;
            case R.id.item4://reset the game and return to the home screen
                gameView.setPlayer(18);
                gameView.setScore(0);
                gameView.resetSpeed();
                gameView.resetLives();
                gameView.resetPlaceHolder();
                gameView.emptyStackerList();
                gameView.resume();
                startActivity(new Intent(this, HomeActivity.class));

                return true;

            default:
                return super.onContextItemSelected(item);
        }
    }


    //pausing the game when activity is paused
    @Override
    protected void onPause(){
        super.onPause();
        gameView.pause();
    }

    //running when game is resumed
    @Override
    protected void onResume(){
        super.onResume();
        gameView.resume();

    }


    //this is what increments the score and moves blocks to the next level
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                //when the screen is pressed do something




                if(!(gameView.getPlayer() == 18)) {//if not at the bottom check the line below
                    gameView.checkPlayerBelow(gameView.getPlayer(), gameView.getPlayer()+1);//this won't work once the game reaches the top
                }//this decrements lives if not directly above

                gameView.addStacker(gameView.getPlayer());



                //new player for a new y pos
                gameView.decrementPlayer();


                if(gameView.getPlayer()==0){//if at the top, go to the bottom
                    gameView.setPlayer(18);
                    gameView.setStackerLives(18);
                    gameView.emptyStackerList();
                    gameView.resetPlaceHolder();

                }


                gameView.incrementScore();//increment the score unless game over

                scoreView.setText(Integer.toString(gameView.getScore()));//set the text to the score(will be used for DB @ gameover)

                if(!(gameView.getSpeed()==0)) {//if the speed isnt already at its maximum increase the speed
                    gameView.increaseSpeed();
                }

                if(gameView.getLives()==0){


                    //startActivity(new Intent(this, GameOver.class));
                    Intent intent;
                    intent = new Intent(this, GameOver.class);
                    intent.putExtra("finalScore",scoreView.getText());


                    startActivity(intent);



                }







                break;
            case MotionEvent.ACTION_DOWN:
                //when the screen is released do something






                break;
        }
        return true;
    }



}
