package com.iitism.bookclub.SignInActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
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

    String details[]=new String[5];
    int positions[]=new int[5];
    int extras[]=new int[5];

    IntentIntegrator intentIntegrator;
    private TextInputLayout email;
    private TextInputLayout password;Button login,scanner;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        firebaseAuth=FirebaseAuth.getInstance();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email =  findViewById(R.id.login_email);
        login=findViewById(R.id.login);
        scanner=findViewById(R.id.signin_scanner);
        password = findViewById(R.id.login_password);
        intentIntegrator=new IntentIntegrator(this);
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

        scanner.setOnClickListener(new View.OnClickListener() {
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
            else
            {
                String s=result.getContents();
                String name="Name";
                String Admin="Admission No.";
                String branch="Branch";
                String email="E-mail";
                String contact="Contact No.";
                positions[0]=s.indexOf(name);
                positions[1]=s.indexOf(Admin);
                positions[2]=s.indexOf(branch);
                positions[3]=s.indexOf(email);
                positions[4]=s.indexOf(contact);
                extras[0]=7;
                extras[1]=15;
                extras[2]=9;
                extras[3]=8;
                extras[4]=12;

                for(int i=0;i<5;i++)
                {
                    String cut="";
                    for(int j=positions[i];j>0;j++)
                    {
                        char ch=s.charAt(j);
                        if (ch=='\n')
                        {
                            cut=s.substring(positions[i]+extras[i],j);
                            break;
                        }
                    }
                    details[i]=cut;
                }

                firebaseAuth.signInWithEmailAndPassword(details[3].trim(),details[1].trim())
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(SignInActivity.this,"Logged In",Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(SignInActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);

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
