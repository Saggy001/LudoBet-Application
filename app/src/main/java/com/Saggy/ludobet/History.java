package com.Saggy.ludobet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class History extends AppCompatActivity {
    ImageButton backbutton1;
    ListView listViewhistory;
    FirebaseUser mfuse;
    String Usname;
    TextView tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        tag = findViewById(R.id.tag);
        tag.setVisibility(View.GONE);
        backbutton1 = findViewById(R.id.backbutton1);
        listViewhistory = findViewById(R.id.listviewhistory);

        mfuse = FirebaseAuth.getInstance().getCurrentUser();
        assert mfuse != null;
        Usname = mfuse.getDisplayName();

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("History").child(Usname).exists()) {
                    FirebaseListAdapter<historyList> adapter = new FirebaseListAdapter<historyList>(History.this , historyList.class , R.layout.listitemhistory,FirebaseDatabase.getInstance().getReference().child("History").child(Usname).child("history")) {
                        @Override
                        protected void populateView(View v, historyList model, int position) {

                            TextView matchhistory = v.findViewById(R.id.matchhistory);
                            TextView timehistory = v.findViewById(R.id.timehistory);
                            matchhistory.setText(model.getHistory());
                            timehistory.setText(model.getDateandtime());
                            if (model.getHistory().contains("LOST"))
                                matchhistory.setTextColor(Color.RED);
                            else if (model.getHistory().contains("CANCELLED"))
                                matchhistory.setTextColor(Color.GRAY);
                            else if(model.getHistory().contains("Added"))
                                matchhistory.setTextColor(Color.GREEN);
                        }
                        @Override
                        public historyList getItem(int pos) {

                            return super.getItem(getCount() - 1 - pos);
                        }
                    };
                    listViewhistory.setAdapter(adapter);
                }
                else  tag.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        backbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}