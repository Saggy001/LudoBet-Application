package com.Saggy.ludobet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
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

public class MainActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    EditText etlEmail, etlPassword;
    TextView signUp,forgetpass;
    Button loginButton;
    CheckBox checkBox;
    String email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        loginButton = findViewById(R.id.login_btn);
        etlEmail = findViewById(R.id.email_edit);
        etlPassword = findViewById(R.id.password_edit);
        signUp = findViewById(R.id.sign_up_login_screen);
        checkBox = findViewById(R.id.show_hide);
        forgetpass = findViewById(R.id.forgetpass);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUp_Activity.class);
                startActivity(intent);
                finish();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                email = etlEmail.getText().toString();
                password = etlPassword.getText().toString();

                if (email.isEmpty()) {
                    etlEmail.setError("Please Enter Email Id");
                    etlEmail.requestFocus();
                } else if (password.isEmpty()) {
                    etlPassword.setError("Please Enter Your Password");
                    etlPassword.requestFocus();
                } else if (!(email.isEmpty() && password.isEmpty())) {
                    firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Login Error, Please Login Again", Toast.LENGTH_SHORT).show();

                            } else {
                                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                                updateUI(firebaseUser);
                            }
                        }
                    });
                } else
                    Toast.makeText(MainActivity.this, "Error Occurred", Toast.LENGTH_SHORT).show();
            }
        });

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View dialog = layoutInflater.inflate(R.layout.forgetpswd,null);

                TextView heading = dialog.findViewById(R.id.heading);
                TextView subheading = dialog.findViewById(R.id.subheading);
                Button resetbutton = dialog.findViewById(R.id.resetbutton);
                final EditText editemail = dialog.findViewById(R.id.editemail);

                resetbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (editemail.getText().toString().isEmpty()) {
                            editemail.setError("Please Enter your Email");
                            editemail.requestFocus();
                        } else {
                            firebaseAuth.sendPasswordResetEmail(editemail.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                        Toast.makeText(MainActivity.this, "Password Reset Email Sent", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(MainActivity.this, "Invalid Email Id", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });


                builder.setView(dialog);
                builder.show();

            }
        });


        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    etlPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    etlPassword.setSelection(etlPassword.getText().length());
                } else {
                    etlPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    etlPassword.setSelection(etlPassword.getText().length());
                }
            }
        });


    }

    //update firebase and shift control
    private void updateUI(FirebaseUser firebaseUser) {
        if (firebaseUser != null) {
            //if (firebaseUser.isEmailVerified()) {
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
                Intent intohome = new Intent(MainActivity.this, Home_Activity.class);
                intohome.putExtra("var",1);
                startActivity(intohome);
                finish();
           /* } else {
                Toast.makeText(this, "Verify Your Email", Toast.LENGTH_SHORT).show();
                Intent into = new Intent(MainActivity.this, Confirmation_Email.class);
                startActivity(into);
            }*/
        }
        else
            Toast.makeText(this, "SignIn to continue", Toast.LENGTH_SHORT).show();
    }

    //firstcontrol
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        updateUI(firebaseUser);
        // GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);//to get user information by google
    }
}
