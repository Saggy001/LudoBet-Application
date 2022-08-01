package com.Saggy.ludobet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ReferAndEarn extends AppCompatActivity {
    Button applink ,refercode;
    ImageButton backbutton;
    ImageView copycode,copylink;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refer_and_earn);

        applink = findViewById(R.id.applink);
        refercode = findViewById(R.id.referalcode);
        copycode = findViewById(R.id.copycode);
        copylink = findViewById(R.id.copylink);
        backbutton = findViewById(R.id.backbutton1);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        assert firebaseUser != null;
        final String Usname = firebaseUser.getDisplayName();

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              applink.setText(dataSnapshot.child("Admin").child("applink").getValue(String.class));
                assert Usname != null;
                refercode.setText(dataSnapshot.child("Users").child(Usname).child("RC").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        copylink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("" , applink.getText().toString());
                assert clipboardManager != null;
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(ReferAndEarn.this, "Link Copied !!", Toast.LENGTH_SHORT).show();
            }
        });

        copycode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("" , refercode.getText().toString());
                assert clipboardManager != null;
                clipboardManager.setPrimaryClip(clip);
                Toast.makeText(ReferAndEarn.this, "Referral Code Copied !!", Toast.LENGTH_SHORT).show();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


}
