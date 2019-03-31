package com.iitism.bookclub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.iitism.bookclub.SignInActivity.SignInActivity;

public class  MainActivity extends AppCompatActivity {
Button SignInbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignInbutton = findViewById(R.id.button);
        SignInbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });
        //comment

        //comment
    }
}
