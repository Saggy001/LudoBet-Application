package com.Saggy.ludobet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Help extends AppCompatActivity {
ImageButton account,sell,buy,fixmatch,pending,update,transaction,backbutton,updateprofile,gotisystem;
TextView contactadmin,contactadmin2,youtubevideo,updatevideo;
int num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.helpactivity);
        account = findViewById(R.id.accountcreate);
        sell = findViewById(R.id.sellchips);
        gotisystem = findViewById(R.id.gotibutton);
        buy = findViewById(R.id.buychips);
        fixmatch = findViewById(R.id.fixmatch);
        pending = findViewById(R.id.pendingmatch);
        update = findViewById(R.id.updationresult);
        transaction = findViewById(R.id.transactionissue);
        updateprofile = findViewById(R.id.updateprofileissue);
        contactadmin = findViewById(R.id.contactadmin);
        backbutton = findViewById(R.id.backbutton1);
        contactadmin2 = findViewById(R.id.contactadmin2);
        youtubevideo = findViewById(R.id.youtubevideo);
        updatevideo = findViewById(R.id.youtubevideoupdate);

        gotisystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intoimage = new Intent(Help.this , usermanual.class);
                intoimage.putExtra("count",14);
                intoimage.putExtra("num",num);
                startActivity(intoimage);

            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intoaccount = new Intent(Help.this,usermanual.class);
                Intoaccount.putExtra("count",1);
                Intoaccount.putExtra("num",num);
                startActivity(Intoaccount);

            }
        });
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intoaccount = new Intent(Help.this,usermanual.class);
                Intoaccount.putExtra("count",3);
                Intoaccount.putExtra("num",num);
                startActivity(Intoaccount);

            }
        });
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intoaccount = new Intent(Help.this,usermanual.class);
                Intoaccount.putExtra("count",4);
                Intoaccount.putExtra("num",num);
                startActivity(Intoaccount);
                }
        });
        fixmatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intoaccount = new Intent(Help.this,usermanual.class);
                Intoaccount.putExtra("count",5);
                Intoaccount.putExtra("num",num);
                startActivity(Intoaccount);

            }
        });
        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intoaccount = new Intent(Help.this,usermanual.class);
                Intoaccount.putExtra("count",6);
                Intoaccount.putExtra("num",num);
                startActivity(Intoaccount);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intoaccount = new Intent(Help.this,usermanual.class);
                Intoaccount.putExtra("count",7);
                Intoaccount.putExtra("num",num);
                startActivity(Intoaccount);
            }
        });
        transaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intoaccount = new Intent(Help.this,usermanual.class);
                Intoaccount.putExtra("count",8);
                Intoaccount.putExtra("num",num);
                startActivity(Intoaccount);
            }
        });
        updateprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Intoaccount = new Intent(Help.this,usermanual.class);
                Intoaccount.putExtra("count",10);
                Intoaccount.putExtra("num",num);
                startActivity(Intoaccount);
            }
        });
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              onBackPressed();
            }
        });

        contactadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String url = "http://api.whatsapp.com/send?phone=" + "91" +String.valueOf(dataSnapshot.child("Admin").child("adminno").getValue(Long.class));
                        try {
                            PackageManager pm = getApplicationContext().getPackageManager();
                            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        } catch (PackageManager.NameNotFoundException e) {
                            Toast.makeText(Help.this,"whatsapp is not installed in your phone",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            }
        });

        contactadmin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String url = "http://api.whatsapp.com/send?phone=" + "91" +String.valueOf(dataSnapshot.child("Admin").child("Adminno").getValue(Long.class));
                        try {
                            PackageManager pm = getApplicationContext().getPackageManager();
                            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(url));
                            startActivity(intent);
                        } catch (PackageManager.NameNotFoundException e) {
                            Toast.makeText(Help.this,"whatsapp is not installed in your phone",Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });


            }
        });

        youtubevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://youtu.be/YpgXjPfw4kU";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
                startActivity(intent);
            }
        });

        updatevideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "https://youtu.be/zj-M3YU4w_0";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(uri));
                startActivity(intent);

            }
        });

    }
}
