package com.example.leed3.taskmanager2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by leed3 on 3/4/2016.
 */
public class DeleteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.delete_page);
        setButtons();
    }

    public void setButtons() {
        Button yes = (Button) findViewById(R.id.yesButton);
        Button no = (Button) findViewById(R.id.noButton);

        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeleteActivity.this,MainActivity.class);
                setResult(RESULT_OK, i);
                finish();
            }
        });

        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DeleteActivity.this,MainActivity.class);
                setResult(RESULT_CANCELED,i);
                finish();
            }
        });
    }
}
