package com.Saggy.ludobet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Pending extends AppCompatActivity {
ListView listView;
ImageButton backbutton1;
FirebaseListAdapter adapter;
FirebaseUser fUser;
String Usname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);

        backbutton1 = findViewById(R.id.backbutton1);
        listView = findViewById(R.id.listviewpend);
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        Usname = fUser.getDisplayName();

        adapter = new FirebaseListAdapter<matchispending>(Pending.this,matchispending.class,R.layout.listviewpending, FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(Usname)){

            @Override
            protected void populateView(View v, matchispending model, int position) {

                TextView matchnametext = v.findViewById(R.id.matchname);
                matchnametext.setText(model.getMatchname());


            }
        };
        listView.setAdapter(adapter);


        backbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });
    }
}
