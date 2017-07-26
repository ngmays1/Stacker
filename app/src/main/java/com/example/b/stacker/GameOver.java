package com.example.b.stacker;

/**
 * Created by b on 7/25/17.
 */

        import android.content.Intent;
        import android.database.Cursor;
        import android.support.v7.app.AlertDialog;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.view.WindowManager;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

public class GameOver extends AppCompatActivity{


    private TextView scoreV;
    private DatabaseHelper myDB;
    private Button btnLeaderboard;
    private Button btnSubmit;
    EditText editName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        scoreV=(TextView) findViewById(R.id.finalScore);

        scoreV.setText(getIntent().getStringExtra("finalScore"));

        myDB = new DatabaseHelper(this);

        btnSubmit=(Button) findViewById(R.id.submit);
        btnLeaderboard = (Button) findViewById(R.id.leaderboard);

        editName = (EditText) findViewById(R.id.Name);


        submitData();
        viewData();

        editName.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                editName.setText("");


            }


        });





    }

    public void onRestart(View v){

        //starting game activity
        startActivity(new Intent(this, GameActivity.class));
    }
    public void onQuit(View v){

        //starting game activity
        startActivity(new Intent(this, HomeActivity.class));
    }
    public void submitData(){

        btnSubmit.setOnClickListener(
                new View.OnClickListener(){

                    public void onClick(View v){



                        if(editName.getText().toString().equalsIgnoreCase("Enter Name") ||editName.getText().toString().equalsIgnoreCase("")){

                            Toast.makeText(GameOver.this, "Enter a Name!", Toast.LENGTH_LONG).show();
                        }

                        else {
                            //add data
                            boolean isInserted = myDB.insertData(editName.getText().toString(),
                                    scoreV.getText().toString());
                            if (isInserted = true) {
                                Toast.makeText(GameOver.this, "Thank You!", Toast.LENGTH_LONG).show();
                            } else
                                Toast.makeText(GameOver.this, "Data not Inserted", Toast.LENGTH_LONG).show();

                        }





                    }

                }

        );

    }


    public void viewData(){

        btnLeaderboard.setOnClickListener(
                new View.OnClickListener(){

                    public void onClick(View v){

                        //get data from db
                        Cursor res = myDB.getAllData();
                        if(res.getCount()==0){
                            Toast.makeText(GameOver.this, "No Data", Toast.LENGTH_SHORT).show();
                            showMessage("Error","No Data Found");



                        }

                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){

                            //buffer.append("Id " + res.getString(0)+ "\n");
                            buffer.append("Name " + res.getString(1)+ "\n");
                            buffer.append("Score " + res.getString(2)+ "\n");

                        }
                        //showAllData
                        showMessage("Leaderboard",buffer.toString());





                    }

                }

        );

    }


    public void showMessage(String title, String message){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();



    }


}