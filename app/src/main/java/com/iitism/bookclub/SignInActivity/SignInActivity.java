package com.iitism.bookclub.SignInActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iitism.bookclub.R;
import com.iitism.bookclub.SignUpActivity.SignUp_Page;

import java.util.regex.Pattern;

public class SignInActivity extends AppCompatActivity
{
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
 /*   private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    //"(?=.*[0-9])" +         //at least 1 digit
                    //"(?=.*[a-z])" +         //at least 1 lower case letter
                    //"(?=.*[A-Z])" +         //at least 1 upper case letter
                    "(?=.*[a-zA-Z])" +      //any letter
                    "(?=.*[@#$%^&+=])" +    //at least 1 special character
                    "(?=\\S+$)" +           //no white spaces
                    ".{4,}" +               //at least 4 characters
                    "$");
*/
    private TextInputLayout email;
    private TextInputLayout password;Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        firebaseAuth=FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email =  findViewById(R.id.login_email);
        login=findViewById(R.id.login);
        password = findViewById(R.id.login_password);
        email.setHint("Enter Email");
        password.setHint("Enter Password");
        TextView b1=findViewById(R.id.signin_signup);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(SignInActivity.this, SignUp_Page.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });


    }

    private void loginUser()
    {
        String emailinput=email.getEditText().getText().toString().trim();
        String passwordInput=password.getEditText().getText().toString().trim();
        firebaseAuth.signInWithEmailAndPassword(emailinput,passwordInput)
        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    Toast.makeText(SignInActivity.this,"LoggedIn Successfully",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(SignInActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }
    /*
    private boolean validateEmail() {
        String emailInput = email.getEditText().getText().toString().trim();

        if (emailInput.isEmpty()) {
            email.setError("Field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            email.setError("Please enter a valid email address");
            return false;
        } else {
            email.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {
        String passwordInput = password.getEditText().getText().toString().trim();

        if (passwordInput.isEmpty()) {
            password.setError("Field can't be empty");
            return false;
        } else if (!PASSWORD_PATTERN.matcher(passwordInput).matches()) {
            password.setError("Password too weak");
            return false;
        } else {
            password.setError(null);
            return true;
        }
    }
    public void confirmInput(View v) {
        if (!validateEmail() |  !validatePassword()) {
            return;
        }

        String input = "Email: " + email.getEditText().getText().toString();
        input += "\n";
        input += "Password: " + password.getEditText().getText().toString();

        Toast.makeText(this, input, Toast.LENGTH_SHORT).show();
    }*/
}
