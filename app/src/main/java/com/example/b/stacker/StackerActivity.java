package com.example.b.stacker;

/**
 * Created by b on 7/25/17.
 */
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class StackerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stacker);


        String []rules ={"Press to stop block","Stack blocks as high as you can", "Speed increases as you accumulate more points", "blocks that aren't perfectly on top of another block will fall off"};
        ListAdapter RuleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, rules);
        ListView TetrisListView = (ListView) findViewById(R.id.StackerListView);
        TetrisListView.setAdapter(RuleAdapter);
    }


    public void startStacker(View v){

        Intent intent = new Intent(this,GameActivity.class);
        startActivity(intent);

    }
}