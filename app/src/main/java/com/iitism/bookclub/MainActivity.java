package com.iitism.bookclub;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.iitism.bookclub.SignInActivity.SignInActivity;

import org.w3c.dom.Text;

public class  MainActivity extends AppCompatActivity {
Button SignInbutton;


ScrollView scroll;
TextView textView;
IntentIntegrator intentIntegrator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SignInbutton = findViewById(R.id.button);
        scroll=findViewById(R.id.scroll);
        textView=findViewById(R.id.text);
        SignInbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });

        intentIntegrator=new IntentIntegrator(this);

        Button button=findViewById(R.id.button2);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intentIntegrator.initiateScan();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        IntentResult result=IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!=null)
        {
            if(result.getContents()==null)
                Toast.makeText(this,"Nothing found",Toast.LENGTH_SHORT);
            else {
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }
}
