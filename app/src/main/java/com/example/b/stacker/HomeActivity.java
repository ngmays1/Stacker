package com.example.b.stacker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homescreen);
    }

    public void OpenStackerRules(View view){
        Intent intent = new Intent(this,StackerActivity.class);
        startActivity(intent);




    }
}