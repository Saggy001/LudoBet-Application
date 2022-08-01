package com.Saggy.ludobet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignUp_Activity extends AppCompatActivity {
    EditText etUsername, etEmail, etPassword, etconfirmpassword, etphoneno, refercode;
    FirebaseAuth firebaseAuth;
    long phoneno = 0;
    int checker = 0;
    Button signUpButton;
    TextView signIn, troubleshoot, terms;
    CheckBox passwordcheckbox, termscheckbox, agecheckbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etUsername = findViewById(R.id.name_edit);
        etEmail = findViewById(R.id.email_edit);
        etPassword = findViewById(R.id.password_edit);
        refercode = findViewById(R.id.referralcode);
        etconfirmpassword = findViewById(R.id.repassword_edit);
        etphoneno = findViewById(R.id.phone_edit);
        signUpButton = findViewById(R.id.signupbutton);
        signIn = findViewById(R.id.login_screen);
        troubleshoot = findViewById(R.id.troubleshoot);
        passwordcheckbox = findViewById(R.id.show_hide);
        agecheckbox = findViewById(R.id.agebox);
        termscheckbox = findViewById(R.id.termbox);
        terms = findViewById(R.id.terms);

        firebaseAuth = FirebaseAuth.getInstance();

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = etEmail.getText().toString();
                final String pwd = etPassword.getText().toString();
                final String repwd = etconfirmpassword.getText().toString();
                final String name = etUsername.getText().toString();

                if (!refercode.getText().toString().isEmpty()){
                    checker = 1;
                }

                if (name.isEmpty()) {
                    etUsername.setError("please Enter your name");
                    etUsername.requestFocus();
                } else if (name.length() < 3) {
                    etUsername.setError("Username must contain at least 3 Character");
                    etUsername.requestFocus();
                }else if (name.length() >15){
                    etUsername.setError("Username is too long");
                    etUsername.requestFocus();
                }
                else {
                    FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.child("Users").child(name).exists()){
                                Toast.makeText(SignUp_Activity.this, "Username is not available", Toast.LENGTH_SHORT).show();
                            } else if (email.isEmpty()) {
                                    etEmail.setError("Please Enter Email Id");
                                    etEmail.requestFocus();
                            } else if (etphoneno.getText().toString().isEmpty()) {
                                    etphoneno.setError("Please Enter your Number");
                                    etphoneno.requestFocus();
                            } else if (etphoneno.getText().toString().length() != 10) {
                                    etphoneno.setError("Please Enter a valid Number");
                                    etphoneno.requestFocus();
                            } else if (pwd.isEmpty()) {
                                    etPassword.setError("Please Enter Your Password");
                                    etPassword.requestFocus();
                            } else if (repwd.isEmpty()) {
                                    etconfirmpassword.setError("Please Enter Your Password");
                                    etconfirmpassword.requestFocus();
                            } else if (!pwd.equals(repwd)) {
                                    Toast.makeText(SignUp_Activity.this, "Password mismatch", Toast.LENGTH_SHORT).show();
                            } else if (!agecheckbox.isChecked()) {
                                    Toast.makeText(SignUp_Activity.this, "Not eligible to create an account", Toast.LENGTH_SHORT).show();
                            } else if (!termscheckbox.isChecked()) {
                                    Toast.makeText(SignUp_Activity.this, "Please agree the terms and condition", Toast.LENGTH_SHORT).show();
                            } else if (!(email.isEmpty() && pwd.isEmpty())) {
                                    firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(SignUp_Activity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (!task.isSuccessful()) {
                                                Toast.makeText(SignUp_Activity.this, "Registration unsuccessful! please try again", Toast.LENGTH_SHORT).show();
                                            } else {
                                                FirebaseUser mfirebaseuser = firebaseAuth.getCurrentUser();
                                                phoneno = Long.parseLong(etphoneno.getText().toString());
                                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                                assert mfirebaseuser != null;
                                                mfirebaseuser.updateProfile(profileChangeRequest);
                                                String correctemailid = email.replace(".com","");
                                                FirebaseDatabase.getInstance().getReference().child("Users").child(name).child("Username").setValue(name);
                                                FirebaseDatabase.getInstance().getReference().child("Users").child(name).child("EmailId").setValue(correctemailid);
                                                FirebaseDatabase.getInstance().getReference().child("Users").child(name).child("Phoneno").setValue(phoneno);
                                                FirebaseDatabase.getInstance().getReference().child("Users").child(name).child("Balance").setValue("0");

                                                if (checker == 1){
                                                    FirebaseDatabase.getInstance().getReference().child("Refer").child(name).child("RC").setValue(refercode.getText().toString());
                                                }

                                                updateUI(mfirebaseuser);
                                            }
                                        }

                                    });
                                } else
                                    Toast.makeText(SignUp_Activity.this, "Error Occurred", Toast.LENGTH_SHORT).show();


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }


            }
        });

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        troubleshoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Help.class);
                startActivity(intent);
            }
        });

        passwordcheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etPassword.setSelection(etPassword.getText().length());
                    etconfirmpassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etconfirmpassword.setSelection(etconfirmpassword.getText().length());
                } else {
                    etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etPassword.setSelection(etPassword.getText().length());
                    etconfirmpassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etconfirmpassword.setSelection(etconfirmpassword.getText().length());
                }
            }
        });

        terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent interm = new Intent(SignUp_Activity.this,Termsandconditions.class);
                startActivity(interm);
            }
        });
    }//protectedb end

    private void updateUI(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            Toast.makeText(SignUp_Activity.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
            FirebaseAuth.getInstance().signOut();
            Intent into = new Intent(SignUp_Activity.this, MainActivity.class);
            startActivity(into);
            finish();
        }

    }
}