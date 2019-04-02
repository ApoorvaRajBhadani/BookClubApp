package com.iitism.bookclub.SignUpActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.iitism.bookclub.Profile.Profile_Model;
import com.iitism.bookclub.R;
import com.iitism.bookclub.SignInActivity.SignInActivity;

public class SignUp_Page extends AppCompatActivity
{

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    String details[]=new String[5];
    int positions[]=new int[5];
    int extras[]=new int[5];

    IntentIntegrator intentIntegrator;
    Button scanner,signup;
    TextView verification;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference("User_Profile");


        scanner=findViewById(R.id.scanner);
        signup=findViewById(R.id.signup_signup);
        verification=findViewById(R.id.verification);
        intentIntegrator=new IntentIntegrator(this);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authenticate();
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
                verification.setText("Email : "+details[3]+'\n'+"Password : Admission Number");
            }
        }
        else
            super.onActivityResult(requestCode, resultCode, data);
    }


    void authenticate()
    {
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseAuth.createUserWithEmailAndPassword(details[3].trim(),details[1].trim()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(SignUp_Page.this,"Successsfully Created account",Toast.LENGTH_SHORT).show();
                    Profile_Model model=new Profile_Model(details[0],details[1],details[2],details[3],details[4]);
                    databaseReference.child(details[1].substring(0,8)).setValue(model);
                    startActivity(new Intent(SignUp_Page.this,SignInActivity.class));

                }
                else
                {
                    Toast.makeText(SignUp_Page.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
