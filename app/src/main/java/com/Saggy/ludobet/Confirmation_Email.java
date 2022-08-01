package com.Saggy.ludobet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Confirmation_Email extends AppCompatActivity {
    EditText emailet;
    TextView signup;
    Button reset;
    FirebaseAuth firebaseAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmationemail);

        emailet = findViewById(R.id.emailet);
        signup = findViewById(R.id.signup);
        reset = findViewById(R.id.reset);
        firebaseAuth = FirebaseAuth.getInstance();
        signup.setVisibility(View.INVISIBLE);
        user = firebaseAuth.getCurrentUser();
        assert user != null;
        emailet.setText(user.getEmail());

        reset.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String email = emailet.getText().toString();
                    if (email.isEmpty()) {
                        emailet.setError("Please Enter your Email");
                        emailet.requestFocus();
                    }
                    else{
                        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(Confirmation_Email.this,"Verification Email Sent",Toast.LENGTH_LONG).show();
                                signup.setVisibility(View.VISIBLE);
                                signup.setText(R.string.ifvreifiedcontinue);
                                signup.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        firebaseAuth.signOut();
                                        Intent loginto = new Intent(Confirmation_Email.this,MainActivity.class);
                                        startActivity(loginto);

                                    }
                                }) ;
                            }
                        });
                    }
                }
            });


        }

    }



