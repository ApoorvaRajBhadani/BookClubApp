package com.iitism.bookclub.SignUpActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.iitism.bookclub.R;
import com.iitism.bookclub.SignInActivity.SignInActivity;

public class SignUp_Page extends AppCompatActivity
{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TextView b1=findViewById(R.id.signup_signin);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignUp_Page.this, SignInActivity.class));
            }
        });
    }
}
