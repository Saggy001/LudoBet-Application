package com.Saggy.ludobet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.service.autofill.FieldClassification;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class Fix_Match extends AppCompatActivity {

    TextView Headingmatch,hinttext,ruletext,ruleone,ruletwo,conditiontext,updatelatetext,roomcodetext;
    EditText roomcodeedit,ludokingusername;
    FirebaseUser fuser;
    RelativeLayout usernamenotelayout, ludokingidlayout;
    String username12;
    Button whatsapp,callbutton,updateresult,usernamesetbutton,uploadscreenshot,roomcodeset;
    ImageView copytobord1,winningproff;
    TextView changerooomcode;

    public static final int IMAGE_GALLARY_REQUEST = 20;

    int percent;
    java.util.Date date;
    String goti ;
    int gotichecker =0;

    DateFormat datefomat;
    Time time;

    String afterbal;
    String beforebal;

    String afterbalofopponent;
    String beforebalofopponent;

    String Matchname;

    String RoomcodeTime;

    ChatMessage chatMessage;

    String opponent;
    ProgressDialog mProgressDialog;

    String Opponentnumber;

    int donotpressback;

    int finalbal;

    String universalDate;
    int i;

    String y;
    int count = 1;

    FirebaseStorage storage;
    StorageReference storageReference;

    String mybal;

    String roomcode;

    String opponentbal;

    private static final String TAG = "StartMatch";


    int pendingMatch = 0;
    int notpendingMatch = 0;

    java.util.ArrayList<String> roomcodearay = new java.util.ArrayList<>();
    boolean onbackPressed;
    Boolean isImageUploaded = false;
    String Opponentnumber1;
    CountDownTimer countDownTimer;
    int resultCount = 1;
    int result;

    //override methods to complete result
    public void showProgressDialog(int i, final String y){

        if (i == 1){

            deletedchallenge();
            android.widget.Toast.makeText(Fix_Match.this, "Your Balance will be updated after the Opponent's Result", android.widget.Toast.LENGTH_SHORT).show();
        }else if (i == 2){

            deletedchallenge();
            android.widget.Toast.makeText(Fix_Match.this, "Waiting for Opponent's Response ..", android.widget.Toast.LENGTH_SHORT).show();
        }else if (i == 0){
            deletedchallenge();
            android.widget.Toast.makeText(Fix_Match.this, "Balance will be updated", android.widget.Toast.LENGTH_SHORT).show();
        }

//        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(getIntent().getExtras().getString("Matchname")).child("Balance Updated").setValue("true");

        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                if (!dataSnapshot.child("Matches").child(Matchname).child("Balance Updated").exists()) {

                    if (y.equals("win")) {

                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                                if (!dataSnapshot.child("Matches").child(Matchname).child("Balance Updated").exists()) {

                                    if (resultCount == 1) {

                                        resultCount = 2;

                                        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");


                                        int matchval = Integer.parseInt(getIntent().getExtras().getString("matchValue"));
                                        if(matchval >= 500)
                                            percent = (int) (matchval * .05);
                                        else
                                            percent = (int) (matchval * .10);

                                        int mybalint = Integer.parseInt(mybal);

                                        int finalbal = mybalint + 2 * matchval - percent;
                                        String afterbal = String.valueOf(finalbal);

                                        FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(universalDate).child("AfterBal").setValue(afterbal);

                                        int totalwin = Integer.parseInt(toatlwin);
                                        totalwin = totalwin + matchval - percent;

                                        int totalwin1 = Integer.parseInt(toatlwinforopp);
                                        totalwin1 = totalwin1 - matchval;

                                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("Total").setValue(String.valueOf(totalwin));
                                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("Total").setValue(String.valueOf(totalwin1));

                                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(username12).child("Balance").setValue(String.valueOf(afterbal));

                                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child("Balance Updated").setValue("true");

                                        deleteMatch(Matchname + " by " + username12);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {

                            }
                        });

                    } else if (y.equals("lost")) {

                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                            @Override
                            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                                if (!dataSnapshot.child("Matches").child(Matchname).child("Balance Updated").exists()) {
                                    if (resultCount == 1) {

                                        resultCount = 2;

                                        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");


                                        int matchval = Integer.parseInt(getIntent().getExtras().getString("matchValue"));
                                        if(matchval >= 500)
                                            percent = (int) (matchval * .05);
                                        else
                                            percent = (int) (matchval * .10);

                                        int mybalint = Integer.parseInt(opponentbal);


                                        int finalbal = mybalint + 2 * matchval - percent;

                                        String afterbalofopponent = String.valueOf(finalbal);

                                        int totalwin = Integer.parseInt(toatlwin);
                                        totalwin = totalwin - matchval;

                                        int totalwin1 = Integer.parseInt(toatlwinforopp);
                                        totalwin1 = totalwin1 + matchval - percent;

                                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("Total").setValue(String.valueOf(totalwin));
                                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("Total").setValue(String.valueOf(totalwin1));

                                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(universalDate).child("AfterBal").setValue(afterbalofopponent);

                                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(opponent).child("Balance").setValue(String.valueOf(afterbalofopponent));

                                        com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child("Balance Updated").setValue("true");

                                        deleteMatch(Matchname + " by " + opponent);
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {

                            }
                        });

                    } else if (y.equals("cancel")) {

                        // update the bal .. Balance will be same as before
                        if (resultCount == 1) {

                            resultCount = 2;

                            // my balance
                            int matchval = Integer.parseInt(getIntent().getExtras().getString("matchValue"));
                            int mybalint = Integer.parseInt(mybal);
                            int finalbal = mybalint + matchval;

                            // Opp balance
                            int oppbalint = Integer.parseInt(opponentbal);
                            int finalbalopp = oppbalint + matchval;

                            //if (finalbal == aftercancelme && finalbalopp == aftercancelopp) {

                                com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(opponent).child("Balance").setValue(String.valueOf(finalbalopp));
                                com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Users").child(username12).child("Balance").setValue(String.valueOf(finalbal));

                                com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child("Balance Updated").setValue("true");
                                deleteMatchforCancel();

                        }
                    }
                }
            }
            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {

            }
        });

        onBackPressed();
    }

    //method to delete name from dechallenge so that user can set another challnge
    private void deletedchallenge() {
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

        Query queryforDchallenge = reff.child("DChallenge").child(username12);

        queryforDchallenge.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                dataSnapshot.getRef().removeValue();
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
            }
        });
    }

    //override method to upload user result
    public void showProgressDialog1(final int i) {

        mProgressDialog.setMessage("Uploading Result, wait a few seconds. Do Not press Back");
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setCancelable(false);

        donotpressback = 1;
        mProgressDialog.show();

        java.util.Random random = new java.util.Random();

        countDownTimer = new android.os.CountDownTimer((random.nextInt(7) + 2) * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                com.google.firebase.database.FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                        if (i == 1) {

                            if (!dataSnapshot.child("Matches").child(Matchname).child("Balance Updated").exists()) {

                                if (dataSnapshot.child("Matches").child(Matchname).child(" I LOST by " + opponent).exists()) {

                                    // Update and Delete this Match

                                    DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                                    String dateString = simpleDateFormat.format(java.util.Calendar.getInstance().getTime());
                                    universalDate = dateString;

                                    showProgressDialog(0, "win");

                                    FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(dateString).setValue(new historyList(" WON ₹" + getIntent().getExtras().getString("matchValue") + " by " + opponent, dateString));
                                    FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString).setValue(new historyList(" LOST ₹" + getIntent().getExtras().getString("matchValue") + " by " + username12, dateString));

//                                    deleteMatch(getIntent().getExtras().getString("Matchname") + " by " + getIntent().getExtras().getString("Username"));

                                    // my before and after balance.
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(dateString).child("BeforeBal").setValue(beforebal);

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(dateString).child("Match RoomCode").setValue(roomcode);
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString).child("Match RoomCode").setValue(roomcode);

                                    // opponent's before and after balance.
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString).child("BeforeBal").setValue(beforebalofopponent);
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString).child("AfterBal").setValue(opponentbal);

                                } else if (dataSnapshot.child("Matches").child(Matchname).child(" I WON by " + opponent).exists()) {

                                    Toast.makeText(Fix_Match.this, "Your Match is Pending, balance will be updated shortly ", android.widget.Toast.LENGTH_LONG).show();
                                    FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child(" I WON by " + username12).setValue(username12);
                                    FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(username12).child(Matchname).setValue(new matchispending(Matchname));
                                    FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(opponent).child(Matchname).setValue(new matchispending(Matchname));
                                    showProgressDialog(0, "");

                                    deleteMatchFromChallenges();


                                } else if (dataSnapshot.child("Matches").child(Matchname).child(" CANCEL GAME by " + opponent).exists()) {

                                    android.widget.Toast.makeText(Fix_Match.this, "Your Match is Pending, balance will be updated shortly ", android.widget.Toast.LENGTH_LONG).show();

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child(" I WON by " + username12).setValue(username12);

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(username12).child(Matchname).setValue(new matchispending(Matchname));
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(opponent).child(Matchname).setValue(new matchispending(Matchname));
                                    showProgressDialog(0, "");

                                    deleteMatchFromChallenges();

                                } else {

                                    result = 1;

                                    FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child(" I WON by " + username12).setValue(username12);
                                    showProgressDialog(1, "");
                                }
                            }else {

                                android.widget.Toast.makeText(Fix_Match.this, "Contact Admin if Balance not Updated Correctly.", android.widget.Toast.LENGTH_SHORT).show();
                            }
                        } else if (i == 2) {

                            if (!dataSnapshot.child("Matches").child(Matchname).child("Balance Updated").exists()) {

                                if (dataSnapshot.child("Matches").child(Matchname).child(" I WON by " + opponent).exists()) {

                                    DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                                    String dateString = simpleDateFormat.format(java.util.Calendar.getInstance().getTime());
                                    universalDate = dateString;

                                    showProgressDialog(0, "lost");

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(dateString).setValue(new historyList(" LOST ₹" + getIntent().getExtras().getString("matchValue") + " by " + opponent, dateString));

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString).setValue(new historyList(" WON ₹" + getIntent().getExtras().getString("matchValue") + " by " + username12, dateString));


                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(dateString).child("BeforeBal").setValue(beforebal);
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(dateString).child("AfterBal").setValue(mybal);

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(dateString).child("Match RoomCode").setValue(roomcode);
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString).child("Match RoomCode").setValue(roomcode);

                                    // opponent's before and after balance.
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString).child("BeforeBal").setValue(beforebalofopponent);
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString).child("AfterBal").setValue(afterbalofopponent);


                                } else if (dataSnapshot.child("Matches").child(username12).child(" I LOST by " + opponent).exists()) {

                                    android.widget.Toast.makeText(Fix_Match.this, "Your Match is Pending, balance will be updated shortly ", android.widget.Toast.LENGTH_LONG).show();

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(username12).child(" I LOST by " + username12).setValue(username12);

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(username12).child(Matchname).setValue(new matchispending(Matchname));
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(opponent).child(Matchname).setValue(new matchispending(Matchname));
                                    showProgressDialog(0, "");

                                    deleteMatchFromChallenges();

//                                    onBackPressed();

                                } else if (dataSnapshot.child("Matches").child(Matchname).child(" CANCEL GAME by " + opponent).exists()) {

                                    android.widget.Toast.makeText(Fix_Match.this, "Your Match is Pending, balance will be updated shortly ", android.widget.Toast.LENGTH_LONG).show();

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child(" I LOST by " + username12).setValue(username12);

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(username12).child(Matchname).push().setValue(new matchispending(Matchname));
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(opponent).child(Matchname).push().setValue(new matchispending(Matchname));
                                    showProgressDialog(0, "");

                                    deleteMatchFromChallenges();

                                } else {

                                    result = 1;
                                    FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child(" I LOST by " + username12).setValue(username12);

                                    showProgressDialog(1, "");
                                }
                            }else {

                                android.widget.Toast.makeText(Fix_Match.this, "Contact Admin if Balance not Updated Correctly.", android.widget.Toast.LENGTH_SHORT).show();
                            }

//                            android.widget.Toast.makeText(com.example.chichi.ludobetapp.StartMatch.this, "Waiting for Opponent's Response ..", android.widget.Toast.LENGTH_SHORT).show();
                        } else if (i == 3) {

                            if (!dataSnapshot.child("Matches").child(Matchname).child("Balance Updated").exists()) {

                                if (dataSnapshot.child("Matches").child(Matchname).child(" CANCEL GAME by " + opponent).exists()) {

                                    if (!dataSnapshot.child("Matches").child(Matchname).child("Match Cancelled").exists()) {



                                        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                                        String dateString1 = simpleDateFormat.format(java.util.Calendar.getInstance().getTime());
                                        universalDate = dateString1;

                                        showProgressDialog(0, "cancel");

                                        FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(dateString1).setValue(new historyList(" CANCELLED ₹" + getIntent().getExtras().getString("matchValue") + " against " + opponent, dateString1));
                                        FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString1).setValue(new historyList(" CANCELLED ₹" + getIntent().getExtras().getString("matchValue") + " against " + username12, dateString1));
                                        FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(dateString1).child("BeforeBal").setValue(beforebal);
                                        FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(dateString1).child("AfterBal").setValue(beforebal);
                                        FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("history").child(dateString1).child("Match RoomCode").setValue(roomcode);
                                        FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString1).child("Match RoomCode").setValue(roomcode);
                                        FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString1).child("BeforeBal").setValue(beforebalofopponent);
                                        FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("history").child(dateString1).child("AfterBal").setValue(beforebalofopponent);

                                        FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child("Match Cancelled").setValue("true");
                                    }

                                } else if (dataSnapshot.child("Matches").child(Matchname).child(" I WON by " + opponent).exists()) {

                                    android.widget.Toast.makeText(Fix_Match.this, "Your Match is Pending, balance will be updated shortly ", android.widget.Toast.LENGTH_LONG).show();

                                    FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child(" CANCEL GAME by " + username12).setValue(username12);

                                    FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(username12).child(Matchname).setValue(new matchispending(Matchname));
                                    FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(opponent).child(Matchname).setValue(new matchispending(Matchname));
                                    showProgressDialog(0, "");

                                    deleteMatchFromChallenges();
//                                    onBackPressed();

                                } else if (dataSnapshot.child("Matches").child(Matchname).child(" I LOST by " + opponent).exists()) {

                                    android.widget.Toast.makeText(Fix_Match.this, "Your Match is Pending, balance will be updated shortly ", android.widget.Toast.LENGTH_LONG).show();

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child(" CANCEL GAME by " + username12).setValue(username12);

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(username12).child(Matchname).setValue(new matchispending(Matchname));
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("pendingMatches").child(opponent).child(Matchname).setValue(new matchispending(Matchname));
                                    showProgressDialog(0, "");

                                    deleteMatchFromChallenges();

//                                    onBackPressed();

                                } else {

                                    result = 1;
//                                getIntent().putExtra("Result", result);

                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child(" CANCEL GAME by " + username12).setValue(username12);

                                    showProgressDialog(2, "");
                                }
                            } else {

                               Toast.makeText(Fix_Match.this, "Contact Admin if Balance not Updated Correctly.", Toast.LENGTH_SHORT).show();
                            }

                        }else {

                            android.widget.Toast.makeText(Fix_Match.this, " Post Result First, Then Go back", android.widget.Toast.LENGTH_SHORT).show();

                        }


                        mProgressDialog.dismiss();
                        onBackPressed();
                    }

                    @Override
                    public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {

                    }
                });
            }
        };
        countDownTimer.start();
    }

    String toatlwin;
    String toatlwinforopp;
    Integer toatlwinint;
    Integer toatlwinint1;

    //    int afterwinme;
    int aftercancelme;

    //    int afterwinopp;
    int aftercancelopp;

//oncreate starts from here so whatever the code first implement from here

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fixmatchlayout);
        final RadioButton iwon = findViewById(R.id.iwon);
        final RadioButton ilost = findViewById(R.id.ilost);
        final RadioButton cancelmatch = findViewById(R.id.cancelmatch);
        whatsapp = findViewById(R.id.whatsapp);//messageopponebnet
        callbutton = findViewById(R.id.call);//messageem
        uploadscreenshot = findViewById(R.id.uploadscreenshot);//uploadss
        updateresult = findViewById(R.id.updateresult);//backbutton
        Headingmatch = findViewById(R.id.headingmatch);//whovswho
        roomcodeedit = findViewById(R.id.roomcodeedit);//roomcodeinput
        roomcodetext = findViewById(R.id.roomcodetext);//roomcodetextview
        roomcodeset = findViewById(R.id.roomcodeset);//button2
        copytobord1 = findViewById(R.id.copytoclipboard);
        changerooomcode = findViewById(R.id.changeroomcode);
        winningproff = findViewById(R.id.winingproof);
        usernamenotelayout = findViewById(R.id.usernamenote);
        ludokingidlayout = findViewById(R.id.ludokinguserlayout);
        usernamesetbutton = findViewById(R.id.usernameset);
        ludokingusername = findViewById(R.id.ludousername);

        ludokingidlayout.setVisibility(View.GONE);
        usernamenotelayout.setVisibility(View.GONE);

        Matchname = getIntent().getExtras().getString("Matchname");
        goti = getIntent().getExtras().getString("goti");
        if (goti.equals(" Full Game")){
            gotichecker = 1;
        }
        else if (goti.equals(" 1 Goti")){
            gotichecker = 2;
        }
        else if (goti.equals(" 2 Goti")){
            gotichecker = 3;
        }
        else if (goti.equals(" 3 Goti")){
            gotichecker = 4;
        }
        else if (goti.equals(" Snack And Ladder")){
            gotichecker = 5;
        }

        fuser = FirebaseAuth.getInstance().getCurrentUser();
        assert fuser != null;
        username12 = fuser.getDisplayName();
        mProgressDialog = new ProgressDialog(Fix_Match.this);
        Headingmatch.setText(Matchname);

        //getting user baalnce
        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mybal = dataSnapshot.child("Users").child(username12).child("Balance").getValue(String.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

       // android.widget.TextView warningTextView = (android.widget.TextView) findViewById(com.example.chichi.ludobetapp.R.id.warningtextView1);
        //button2.setAlpha(0.6f);

        storageReference = FirebaseStorage.getInstance().getReference();
        storage = FirebaseStorage.getInstance();

        uploadscreenshot.setClickable(false);
        uploadscreenshot.setAlpha(0.5f);

        iwon.setEnabled(false);
        ilost.setEnabled(false);

        if (gotichecker == 1 || gotichecker == 5 ) {
            //Alert dialog to show a warning
            AlertDialog.Builder builder = new AlertDialog.Builder(Fix_Match.this);
            builder.setMessage("You will lose your Money if :\n1. You Posted Wrong Result.\n2. You don't Post Result within 5 min. after the Match.\n\nYour money will also be deducted from your balance.");
            builder.setCancelable(false);
            builder.setPositiveButton("I Understand", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(android.content.DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();

        } else if (gotichecker == 2 || gotichecker == 3 || gotichecker == 4){
            //Alert dialog to show a warning
            AlertDialog.Builder builder = new AlertDialog.Builder(Fix_Match.this);
            builder.setMessage("For this game, enter your LudoKing username first to continue.\n\nIf you enter wrong username by mistake then don't join the room otherwise you are responsible for your loss.");
            builder.setCancelable(false);
            builder.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
                @Override
                public void onClick(android.content.DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.show();
        }
        // IF CHALLENGE WAS MINE ..
        if (username12.equals(getIntent().getExtras().getString("myname"))) {
            opponent = getIntent().getExtras().getString("Opponentname");
        } else if (username12.equals(getIntent().getExtras().getString("Opponentname"))) {
            opponent = getIntent().getExtras().getString("myname");
        }

        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Bal of mine
              //  mybal = dataSnapshot.child("Users").child(Objects.requireNonNull(getIntent().getExtras().getString("Username"))).child("Balance").getValue(String.class);
                // int mybalint = Integer.parseInt(mybal);
                String matchstring = getIntent().getExtras().getString("matchValue");
                int matchval = Integer.parseInt(matchstring);
                // aftercancelme = mybalint + matchval;
                // for Total of all history

                assert username12 != null;
                if (!dataSnapshot.child("History").child(username12).child("Total").exists()){
                    FirebaseDatabase.getInstance().getReference().child("History").child(username12).child("Total").setValue("0");
                    toatlwin = dataSnapshot.child("History").child(username12).child("Total").getValue(String.class);
                }else if (dataSnapshot.child("History").child(username12).child("Total").exists()){
                    toatlwin = dataSnapshot.child("History").child(username12).child("Total").getValue(String.class);
                }

                if (!dataSnapshot.child("History").child(opponent).child("Total").exists()){
                    FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("Total").setValue("0");
                    toatlwinforopp = dataSnapshot.child("History").child(opponent).child("Total").getValue(String.class);
                }else if (dataSnapshot.child("History").child(opponent).child("Total").exists()){
                    toatlwinforopp = dataSnapshot.child("History").child(opponent).child("Total").getValue(String.class);
                }

                // beforebal = String.valueOf(beforebalint);
                beforebal = dataSnapshot.child("Matches").child(Matchname).child("Before balance").child(username12 +" : Before Balance").getValue(String.class);
                // Bal of my opponent
                opponentbal = dataSnapshot.child("Users").child(opponent).child("Balance").getValue(String.class);


                int mybalintopp = Integer.parseInt(opponentbal);
                aftercancelopp = mybalintopp + matchval;

                try {
                    //                    toatlwinint1 = dataSnapshot.child("History").child(opponent).child("Total").getValue(Integer.class);
                    toatlwinforopp = dataSnapshot.child("History").child(opponent).child("Total").getValue(String.class);
//                    toatlwinforopp = String.valueOf(toatlwinint1);
                }catch (Exception e){

                    FirebaseDatabase.getInstance().getReference().child("History").child(opponent).child("Total").setValue("0");
                    toatlwinforopp = dataSnapshot.child("History").child(opponent).child("Total").getValue(String.class);

//                    toatlwinint1 = dataSnapshot.child("History").child(opponent).child("Total").getValue(Integer.class);
//                    toatlwinforopp = String.valueOf(toatlwinint1);
                }

//                int oppint = Integer.parseInt(getIntent().getExtras().getString("oppbal"));

//                beforebalofopponent = String.valueOf(beforebaloppint);
                beforebalofopponent = dataSnapshot.child("Matches").child(Matchname).child("Before balance").child(opponent +" : Before Balance").getValue(String.class);

                Opponentnumber1 = String.valueOf(dataSnapshot.child("Users").child(opponent).child("Phoneno").getValue(Long.class));
                Opponentnumber = "91" +Opponentnumber1;
            }

            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {

            }
        });

        roomcodeedit.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(android.text.Editable s) {

                if (s.toString().trim().length()>8 || s.toString().trim().length()<=9){

                    roomcodeset.setAlpha(1);
                }else {
                    roomcodeset.setAlpha(0.6f);
                }
            }
        });

        //whatsapp button config
        whatsapp.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
//                String indianformat = "91" + opponentnumber;
//                int toInt = Integer.parseInt(indianformat);
                String url = "https://api.whatsapp.com/send?phone=" + Opponentnumber;
                try{
                    android.content.pm.PackageManager pm = getApplicationContext().getPackageManager();
                    pm.getPackageInfo("com.whatsapp", android.content.pm.PackageManager.GET_ACTIVITIES);
                    android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
                    intent.setData(android.net.Uri.parse(url));
                    startActivity(intent);
                }catch (android.content.pm.PackageManager.NameNotFoundException e){
                    Toast.makeText(Fix_Match.this, "Whatsapp is not Installed in Your Phone", android.widget.Toast.LENGTH_LONG).show();
                }
            }
        });

        //call button config
        callbutton.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                android.content.Intent intent = new android.content.Intent(android.content.Intent.ACTION_DIAL);
                intent.setData(android.net.Uri.parse("tel:" + Opponentnumber1));
                startActivity(intent);
            }
        });





        // if i was a challenger so i have to enter the roomcode
        if (username12.equals(getIntent().getExtras().getString("myname"))) {

            //if i want to play 1 goti / 2 goti / 3 goti
            if (gotichecker == 2 || gotichecker == 3 || gotichecker == 4){

                usernamenotelayout.setVisibility(View.VISIBLE);
                ludokingidlayout.setVisibility(View.VISIBLE);
                copytobord1.setVisibility(View.GONE);
                iwon.setEnabled(false);
                ilost.setEnabled(false);
                roomcodetext.setVisibility(View.GONE);
                changerooomcode.setVisibility(View.GONE);
                roomcodeedit.setVisibility(View.GONE);
                roomcodeset.setVisibility(View.GONE);

                FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Matches").child(Matchname).child("LudoKingID").exists()) {
                            if (dataSnapshot.child("Matches").child(Matchname).child("LudoKingID").child(username12).exists()) {
                                usernamesetbutton.setClickable(false);
                                ludokingusername.setClickable(false);
                                ludokingusername.setFocusableInTouchMode(false);
                                ludokingusername.setFocusable(false);
                                ludokingusername.setText(dataSnapshot.child("Matches").child(Matchname).child("LudoKingID").child(username12).getValue(String.class));

                                copytobord1.setVisibility(android.view.View.INVISIBLE);
                                copytobord1.setClickable(false);

                                if (dataSnapshot.child("Matches").child(Matchname).child("RoomCode").exists()) {
                                    if (dataSnapshot.child("Matches").child(Matchname).child("RoomCoderequest").exists()) {
                                        String roomcoderequest = dataSnapshot.child("Matches").child(Matchname).child("RoomCoderequest").getValue(String.class);
                                        roomcodetext.setVisibility(View.VISIBLE);
                                        roomcodetext.setText(roomcoderequest);
                                        iwon.setEnabled(false);
                                        ilost.setEnabled(false);
//                            button3.setVisibility(android.view.View.VISIBLE);
                                        changerooomcode.setVisibility(View.GONE);
                                        roomcodeedit.setVisibility(android.view.View.VISIBLE);
                                        roomcodeset.setVisibility(android.view.View.VISIBLE);
                                    } else {
//                        String roomcode = dataSnapshot.child("Matches").child(getIntent().getExtras().getString("myname") + " vs " + getIntent().getExtras().getString("Opponentname")).child("RoomCode").getValue(String.class);
                                        roomcode = dataSnapshot.child("Matches").child(Matchname).child("RoomCode").child("RoomCode").getValue(String.class);
                                        roomcodetext.setVisibility(View.VISIBLE);
                                        roomcodetext.setText(roomcode);
                                        iwon.setEnabled(true);
                                        ilost.setEnabled(true);
//                        roomcodetextView.setText(roomcodeinput.getText());
                                        roomcodeset.setVisibility(View.GONE);
                                        roomcodeedit.setVisibility(View.GONE);
                                        changerooomcode.setVisibility(View.GONE);
                       /*
                        button3.setOnClickListener(new android.view.View.OnClickListener() {
                            @Override
                            public void onClick(android.view.View v) {
                                roomcodetextView.setVisibility(View.GONE);
                                button3.setVisibility(View.GONE);
                                roomcodeinput.setVisibility(android.view.View.VISIBLE);
                                button2.setVisibility(android.view.View.VISIBLE);
                            }
                        });*/
                                    }
                                } else {
                                    iwon.setEnabled(false);
                                    ilost.setEnabled(false);
                                    roomcodetext.setVisibility(View.GONE);
                                    changerooomcode.setVisibility(View.GONE);
                                    roomcodeset.setVisibility(View.VISIBLE);
                                    roomcodeedit.setVisibility(View.VISIBLE);
                                }
                            } else {
                                ludokingusername.requestFocus();
                                Toast.makeText(Fix_Match.this, "Enter LudoKing Username to continue", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            //if full game of snack ladder
            //normal game
            else if (gotichecker == 1 || gotichecker == 5)
            {
                copytobord1.setVisibility(android.view.View.INVISIBLE);
                copytobord1.setClickable(false);

                //check whether roomcode is already given or not
                FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
//                    for (com.google.firebase.database.DataSnapshot data : dataSnapshot.getChildren()) {
                        if (dataSnapshot.child("Matches").child(Matchname).child("RoomCode").exists()) {
                            if (dataSnapshot.child("Matches").child(Matchname).child("RoomCoderequest").exists()) {
                                String roomcoderequest = dataSnapshot.child("Matches").child(Matchname).child("RoomCoderequest").getValue(String.class);
                                roomcodetext.setVisibility(View.VISIBLE);
                                roomcodetext.setText(roomcoderequest);
                                iwon.setEnabled(false);
                                ilost.setEnabled(false);
//                            button3.setVisibility(android.view.View.VISIBLE);
                                changerooomcode.setVisibility(View.GONE);
                                roomcodeedit.setVisibility(android.view.View.VISIBLE);
                                roomcodeset.setVisibility(android.view.View.VISIBLE);
                            } else {
//                        String roomcode = dataSnapshot.child("Matches").child(getIntent().getExtras().getString("myname") + " vs " + getIntent().getExtras().getString("Opponentname")).child("RoomCode").getValue(String.class);
                                roomcode = dataSnapshot.child("Matches").child(Matchname).child("RoomCode").child("RoomCode").getValue(String.class);
                                roomcodetext.setVisibility(View.VISIBLE);
                                roomcodetext.setText(roomcode);
                                iwon.setEnabled(true);
                                ilost.setEnabled(true);
//                        roomcodetextView.setText(roomcodeinput.getText());
                                roomcodeset.setVisibility(View.GONE);
                                roomcodeedit.setVisibility(View.GONE);
                                changerooomcode.setVisibility(View.GONE);
                       /*
                        button3.setOnClickListener(new android.view.View.OnClickListener() {
                            @Override
                            public void onClick(android.view.View v) {
                                roomcodetextView.setVisibility(View.GONE);
                                button3.setVisibility(View.GONE);
                                roomcodeinput.setVisibility(android.view.View.VISIBLE);
                                button2.setVisibility(android.view.View.VISIBLE);
                            }
                        });*/
                            }
                        } else {
                            iwon.setEnabled(false);
                            ilost.setEnabled(false);
                            roomcodetext.setVisibility(View.GONE);
                            changerooomcode.setVisibility(View.GONE);
                            roomcodeset.setVisibility(View.VISIBLE);
                            roomcodeedit.setVisibility(View.VISIBLE);
                            copytobord1.setVisibility(android.view.View.INVISIBLE);
                            copytobord1.setClickable(false);
                        }
                        //                  }
                    }
                    @Override
                    public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                    }
                });
            }
            else
                Toast.makeText(this, "contact admin for this issue", Toast.LENGTH_SHORT).show();

            //if user click on set uttpon to set ludoking username and gpo forward
            usernamesetbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ludokingusername.getText().toString().length() >2){
                        FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child("LudoKingID").child(username12).setValue(ludokingusername.getText().toString());
                        ludokingusername.setClickable(false);
                        usernamesetbutton.setClickable(false);
                        ludokingusername.setFocusableInTouchMode(false);
                        ludokingusername.setFocusable(false);
                        iwon.setEnabled(false);
                        ilost.setEnabled(false);
                        roomcodetext.setVisibility(View.GONE);
                        changerooomcode.setVisibility(View.GONE);
                        roomcodeset.setVisibility(View.VISIBLE);
                        roomcodeedit.setVisibility(View.VISIBLE);

                    }else
                        Toast.makeText(Fix_Match.this, "Enter Valid Username", Toast.LENGTH_SHORT).show();
                }
            });

            //if user click on set button to set roomcode
            roomcodeset.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    if (roomcodeedit.getText().toString().length() == 8) {
                        // Remove (if exists) Room code Request ..
                       FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                           @Override
                           public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                               if (dataSnapshot.child("Matches").child(Matchname).child("RoomCoderequest").exists()) {
                                   DatabaseReference ref = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
                                   Query q = ref.child("Matches").child(Matchname).orderByChild("RoomCoderequest");
                                   q.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                dataSnapshot1.getRef().removeValue();
                                            }
                                        }
                                        @Override
                                        public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                                        }
                                    });
                                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a dd-MM-yyyy");
                                    RoomcodeTime = dateFormat.format(java.util.Calendar.getInstance().getTime());
                                    FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child("RoomCode").child("RoomCode").setValue(roomcodeedit.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child("RoomCode").child(" Time :"+RoomcodeTime).setValue(roomcodeedit.getText().toString());
                                    roomcodeedit.setVisibility(View.GONE);
                                    roomcodeset.setVisibility(View.GONE);
                                    roomcodetext.setVisibility(View.VISIBLE);
                                    iwon.setEnabled(true);
                                    ilost.setEnabled(true);
                                    roomcodetext.setText(roomcodeedit.getText().toString());
                                }else {

                                    DateFormat dateFormat = new SimpleDateFormat("hh:mm a dd-MM-yyyy");
                                    RoomcodeTime = dateFormat.format(java.util.Calendar.getInstance().getTime());
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child("RoomCode").child("RoomCode").setValue(roomcodeedit.getText().toString());
                                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child("RoomCode").child(" Time :"+RoomcodeTime).setValue(roomcodeedit.getText().toString());
                                    roomcodeedit.setVisibility(View.GONE);
                                    roomcodeset.setVisibility(View.GONE);
                                    roomcodetext.setVisibility(View.VISIBLE);
                                    iwon.setEnabled(true);
                                    ilost.setEnabled(true);
                                    roomcodetext.setText(roomcodeedit.getText().toString());
//                                  button3.setVisibility(View.VISIBLE);
                                }
                            }
                            @Override
                            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                            }
                        });
                    } else {
                        android.widget.Toast.makeText(Fix_Match.this, "Invalid Room Code", android.widget.Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        //firstperson ends

        //if user is a requester
        else if (username12.equals(getIntent().getExtras().getString("Opponentname"))) {
            // IF I M THE OPPONENT ..

            //if i want to play 1 goti / 2 goti / 3 goti
            if (gotichecker == 2 || gotichecker == 3 || gotichecker == 4) {


                usernamenotelayout.setVisibility(View.VISIBLE);
                ludokingidlayout.setVisibility(View.VISIBLE);
                copytobord1.setVisibility(View.GONE);
                iwon.setEnabled(false);
                ilost.setEnabled(false);
                roomcodetext.setVisibility(View.GONE);
                changerooomcode.setVisibility(View.GONE);
                roomcodeedit.setVisibility(View.GONE);
                roomcodeset.setVisibility(View.GONE);

                FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Matches").child(Matchname).child("LudoKingID").exists()) {
                            if (dataSnapshot.child("Matches").child(Matchname).child("LudoKingID").child(username12).exists()) {
                                usernamesetbutton.setClickable(false);
                                ludokingusername.setClickable(false);
                                ludokingusername.setFocusableInTouchMode(false);
                                ludokingusername.setFocusable(false);
                                ludokingusername.setText(dataSnapshot.child("Matches").child(Matchname).child("LudoKingID").child(username12).getValue(String.class));

                                copytobord1.setVisibility(android.view.View.VISIBLE);
                                copytobord1.setClickable(true);
                                roomcodetext.setVisibility(View.VISIBLE);

                                if (dataSnapshot.child("Matches").child(Matchname).child("RoomCode").exists()) {
                                    if (dataSnapshot.child("Matches").child(Matchname).child("RoomCoderequest").exists()) {
//                         String roomcode = dataSnapshot.child("Matches").child(getIntent().getExtras().getString("myname") + " vs " + getIntent().getExtras().getString("Opponentname")).child("RoomCode").getValue(String.class);
                                        roomcodetext.setText(" Waiting for Room Code .. ");
                                        copytobord1.setVisibility(android.view.View.INVISIBLE);
                                        copytobord1.setClickable(false);
                                        changerooomcode.setVisibility(android.view.View.INVISIBLE);
                                        changerooomcode.setClickable(false);
                                        iwon.setEnabled(false);
                                        ilost.setEnabled(false);
                                    } else {
//                          String roomcode = dataSnapshot.child("Matches").child(getIntent().getExtras().getString("myname") + " vs " + getIntent().getExtras().getString("Opponentname")).child("RoomCode").getValue(String.class);
                                        String roomcode = dataSnapshot.child("Matches").child(Matchname).child("RoomCode").child("RoomCode").getValue(String.class);
                                        iwon.setEnabled(true);
                                        ilost.setEnabled(true);
                                        copytobord1.setVisibility(android.view.View.VISIBLE);
                                        copytobord1.setClickable(true);
                                        changerooomcode.setVisibility(View.VISIBLE);
                                        changerooomcode.setClickable(true);
                                        roomcodetext.setText(roomcode);
                                        if (!roomcodearay.contains(roomcode)) {
                                            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.accomplished);
                                            mp.start();
                                            roomcodearay.add(roomcode);
                                        }

                                        copytobord1.setOnClickListener(new android.view.View.OnClickListener() {
                                            @Override
                                            public void onClick(android.view.View v) {

                                                android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(android.content.Context.CLIPBOARD_SERVICE);
                                                android.content.ClipData clip = android.content.ClipData.newPlainText(" Roomcode Text", roomcodetext.getText().toString());
                                                clipboard.setPrimaryClip(clip);

                                                android.widget.Toast.makeText(Fix_Match.this, "Room Code copied !!", android.widget.Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                } else {
                                    roomcodetext.setText("Waiting for Room Code .. ");
                                    iwon.setEnabled(false);
                                    ilost.setEnabled(false);
                                    copytobord1.setVisibility(android.view.View.INVISIBLE);
                                    copytobord1.setClickable(false);
                                    changerooomcode.setVisibility(android.view.View.INVISIBLE);
                                    changerooomcode.setClickable(false);
                                }
                            } else
                                Toast.makeText(Fix_Match.this, "Enter LudoKing Username to continue", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });

            } else if (gotichecker == 1 || gotichecker == 5){

                roomcodeedit.setVisibility(android.view.View.GONE);
                roomcodeset.setVisibility(android.view.View.GONE);
                changerooomcode.setVisibility(View.GONE);
                copytobord1.setVisibility(android.view.View.VISIBLE);
                copytobord1.setClickable(true);

                FirebaseDatabase.getInstance().getReference().addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Matches").child(Matchname).child("RoomCode").exists()) {
                            if (dataSnapshot.child("Matches").child(Matchname).child("RoomCoderequest").exists()) {
//                         String roomcode = dataSnapshot.child("Matches").child(getIntent().getExtras().getString("myname") + " vs " + getIntent().getExtras().getString("Opponentname")).child("RoomCode").getValue(String.class);
                                roomcodetext.setText(" Waiting for Room Code .. ");
                                copytobord1.setVisibility(android.view.View.INVISIBLE);
                                copytobord1.setClickable(false);
                                changerooomcode.setVisibility(android.view.View.INVISIBLE);
                                changerooomcode.setClickable(false);
                                iwon.setEnabled(false);
                                ilost.setEnabled(false);
                            } else {
//                          String roomcode = dataSnapshot.child("Matches").child(getIntent().getExtras().getString("myname") + " vs " + getIntent().getExtras().getString("Opponentname")).child("RoomCode").getValue(String.class);
                                String roomcode = dataSnapshot.child("Matches").child(Matchname).child("RoomCode").child("RoomCode").getValue(String.class);
                                iwon.setEnabled(true);
                                ilost.setEnabled(true);
                                copytobord1.setVisibility(android.view.View.VISIBLE);
                                copytobord1.setClickable(true);
                                changerooomcode.setClickable(true);
                                roomcodetext.setText(roomcode);
                                if (!roomcodearay.contains(roomcode)) {
                                    MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.accomplished);
                                    mp.start();
                                    roomcodearay.add(roomcode);
                                }

                                copytobord1.setOnClickListener(new android.view.View.OnClickListener() {
                                    @Override
                                    public void onClick(android.view.View v) {

                                        android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getSystemService(android.content.Context.CLIPBOARD_SERVICE);
                                        android.content.ClipData clip = android.content.ClipData.newPlainText(" Roomcode Text", roomcodetext.getText().toString());
                                        clipboard.setPrimaryClip(clip);

                                        android.widget.Toast.makeText(Fix_Match.this, "Room Code copied !!", android.widget.Toast.LENGTH_SHORT).show();
                                    }
                                });

                                changerooomcode.setVisibility(View.VISIBLE);
                            }
                        } else {

                            roomcodetext.setText("Waiting for Room Code .. ");

                            iwon.setEnabled(false);
                            ilost.setEnabled(false);
                            copytobord1.setVisibility(android.view.View.INVISIBLE);
                            copytobord1.setClickable(false);
                            changerooomcode.setVisibility(android.view.View.INVISIBLE);
                            changerooomcode.setClickable(false);
                        }

                    }

                    @Override
                    public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {

                    }
                });
            } else
                Toast.makeText(this, "Contact admin for this issue", Toast.LENGTH_SHORT).show();

            //if user click on set uttpon to set ludoking username and gpo forward
            usernamesetbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ludokingusername.getText().toString().length() >2){
                        FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child("LudoKingID").child(username12).setValue(ludokingusername.getText().toString());
                        ludokingusername.setClickable(false);
                        ludokingusername.setFocusableInTouchMode(false);
                        ludokingusername.setFocusable(false);
                        usernamesetbutton.setClickable(false);
                        roomcodeset.setVisibility(View.GONE);
                        roomcodeedit.setVisibility(View.GONE);

                        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("Matches").child(Matchname).child("RoomCode").exists()){
                                    String roomcode = dataSnapshot.child("Matches").child(Matchname).child("RoomCode").child("RoomCode").getValue(String.class);
                                    iwon.setEnabled(true);
                                    ilost.setEnabled(true);
                                    roomcodetext.setVisibility(View.VISIBLE);
                                    copytobord1.setVisibility(android.view.View.VISIBLE);
                                    copytobord1.setClickable(true);
                                    changerooomcode.setClickable(true);
                                    roomcodetext.setText(roomcode);
                                    if (!roomcodearay.contains(roomcode)) {
                                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.accomplished);
                                        mp.start();
                                        roomcodearay.add(roomcode);
                                    }
                                    changerooomcode.setVisibility(View.VISIBLE);
                                }else {
                                    roomcodetext.setText("Waiting for Room Code .. ");
                                    iwon.setEnabled(false);
                                    ilost.setEnabled(false);
                                    roomcodetext.setVisibility(View.VISIBLE);
                                    copytobord1.setVisibility(android.view.View.INVISIBLE);
                                    copytobord1.setClickable(false);
                                    changerooomcode.setVisibility(android.view.View.INVISIBLE);
                                    changerooomcode.setClickable(false);
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });

                    }else
                        Toast.makeText(Fix_Match.this, "Enter Valid Username", Toast.LENGTH_SHORT).show();
                }
            });

            changerooomcode.setOnClickListener(new android.view.View.OnClickListener() {
                @Override
                public void onClick(android.view.View v) {
                    roomcodetext.setText("Waiting for Room Code .. ");
                    changerooomcode.setVisibility(View.GONE);
                    copytobord1.setVisibility(View.GONE);
                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child("RoomCoderequest").setValue("");
                }
            });
        }

        //i won click
        iwon.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (ilost.isChecked() | cancelmatch.isChecked()) {
                    ilost.setChecked(false);
                    cancelmatch.setChecked(false);
                }
                uploadscreenshot.setAlpha(1);
                uploadscreenshot.setClickable(true);
            }
        });

        ilost.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (iwon.isChecked() | cancelmatch.isChecked()) {
                    iwon.setChecked(false);
                    cancelmatch.setChecked(false);
                }
                uploadscreenshot.setAlpha(0.5f);
                uploadscreenshot.setClickable(false);
            }
        });

        cancelmatch.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (ilost.isChecked() | iwon.isChecked()) {
                    ilost.setChecked(false);
                    iwon.setChecked(false);
                }
                uploadscreenshot.setAlpha(0.5f);
                uploadscreenshot.setClickable(false);
            }
        });

        uploadscreenshot.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                if (iwon.isChecked()) {
                    onImageGallaryClicked();
                } else {
                    android.widget.Toast.makeText(Fix_Match.this, "Check on Your Result First", android.widget.Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Changed so many things .. Debug and make Apk first

        updateresult.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {

                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                        if (iwon.isChecked() | ilost.isChecked() | cancelmatch.isChecked()) {
                            if (count == 1) {
                                count = 2;
                                if (iwon.isChecked()) {
                                    winningproff.setVisibility(View.VISIBLE);
                                    if (isImageUploaded) {
                                        if (dataSnapshot.child("Matches").child(Matchname).child(" I WON by " + username12).exists()) {
                                            Toast.makeText(Fix_Match.this, "Contact Admin for this App", Toast.LENGTH_SHORT).show();
                                            onBackPressed();
                                        }else if (!dataSnapshot.child("Matches").child(Matchname).child(" I WON by " + username12).exists()){
                                            showProgressDialog1(1);
                                        }
                                    } else {
                                        count = 1;
                                        android.widget.Toast.makeText(Fix_Match.this, "Please Upload Winning Screenshot First", Toast.LENGTH_SHORT).show();
                                    }
                                } else if (ilost.isChecked()) {
                                    if (dataSnapshot.child("Matches").child(Matchname).child(" I LOST by " + username12).exists()) {
                                        android.widget.Toast.makeText(Fix_Match.this, "Contact Admin for this App", android.widget.Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                    }else if (!dataSnapshot.child("Matches").child(Matchname).child(" I LOST by " + username12).exists()) {
                                        showProgressDialog1(2);
                                    }
                                } else if (cancelmatch.isChecked()) {
                                    if (dataSnapshot.child("Matches").child(Matchname).child(" CANCEL GAME by " + username12).exists()) {
                                        android.widget.Toast.makeText(Fix_Match.this, "Contact Admin for this App", android.widget.Toast.LENGTH_SHORT).show();
                                        onBackPressed();
                                    }else if (!dataSnapshot.child("Matches").child(Matchname).child(" CANCEL GAME by " + username12).exists()) {
                                        DateFormat simpleDateFormat = new SimpleDateFormat("hh:mm a dd-MM-yyyy");
                                        String dateString = simpleDateFormat.format(java.util.Calendar.getInstance().getTime());
                                        FirebaseDatabase.getInstance().getReference().child("Matches").child(Matchname).child(" CANCEL GAME at ").setValue(dateString);
                                        showProgressDialog1(3);
                                    }
                                }
                            } else {
                                android.widget.Toast.makeText(Fix_Match.this, "Please Wait, Result is Uploading", android.widget.Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(Fix_Match.this, "Select your result first", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                    }
                });
            }

        });
        updateresult.setEnabled(true);
    }
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
        if (keyCode == android.view.KeyEvent.KEYCODE_BACK) {
            if(donotpressback == 1){
                android.widget.Toast.makeText(Fix_Match.this, "Please Wait Till Its done", android.widget.Toast.LENGTH_SHORT).show();
            }else {
                onBackPressed();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    //override function to delete match
    public void deleteMatch(String name) {
        // Delete the match from matches
        DatabaseReference reference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("Matches").child(Matchname);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
            }
            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
            }
        });
       StorageReference image = storage.getReferenceFromUrl("gs://ludobet-cd423.appspot.com/"+ name);
        image.delete().addOnSuccessListener(new com.google.android.gms.tasks.OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                android.widget.Toast.makeText(Fix_Match.this, "Your Balance has been Updated", android.widget.Toast.LENGTH_SHORT).show();
            }
        });

        // delete the match from challenges ..
        DatabaseReference reference1 = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
        Query query1 = reference1.child("Challenges").child(Matchname);

        query1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
            }
            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
            }
        });
    }

    //override function to delete match for cancel
    public void deleteMatchforCancel() {
        // Delete the match from matches
        com.google.firebase.database.DatabaseReference reference = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query query = reference.child("Matches").child(Matchname);
        query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
            }
            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
            }
        });
        // delete the match from challenges ..
        com.google.firebase.database.DatabaseReference reference1 = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query query1 = reference1.child("Challenges").child(Matchname);
        query1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
            }
            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
            }
        });

    }

    //override method to delete match from challeges
    public void deleteMatchFromChallenges(){
        com.google.firebase.database.DatabaseReference reference1 = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
        com.google.firebase.database.Query query1 = reference1.child("Challenges").child(Matchname);
        query1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().removeValue();
            }
            @Override
            public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
            }
        });
    }

    public void onImageGallaryClicked() {
        // Implicit intent will be used here ..
        android.content.Intent pickPhoto = new android.content.Intent(android.content.Intent.ACTION_PICK);
        java.io.File pictureDirectory = android.os.Environment.getExternalStoragePublicDirectory(android.os.Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        // URI representation ..
        android.net.Uri data = android.net.Uri.parse(pictureDirectoryPath);
        pickPhoto.setDataAndType(data, "image/*"); // "image/*" for all types and image/png for png images only
        startActivityForResult(pickPhoto, IMAGE_GALLARY_REQUEST);
    }

    android.graphics.Bitmap bitmap;
    String scRoomCode;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_GALLARY_REQUEST) {
                Uri ImageUri = data.getData();
                try {

                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), ImageUri);
                        winningproff.setImageBitmap(bitmap);
                    } catch (IOException e) {

                        e.printStackTrace();
                    }

                    StorageReference storageRef = storageReference.child(Matchname + " by " + username12);

                    mProgressDialog.setMessage("Uploading Screenshot Image, Do Not press back");
                    mProgressDialog.setCanceledOnTouchOutside(false);
                    mProgressDialog.setCancelable(false);
                    donotpressback = 1;
                    mProgressDialog.show();

                    storageRef.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content

                            Toast.makeText(Fix_Match.this, "Upload Success", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                            donotpressback = 0;

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {

                            Toast.makeText(Fix_Match.this, "Upload Failed, Try Again", Toast.LENGTH_SHORT).show();
                            mProgressDialog.dismiss();
                        }
                    });

                    isImageUploaded = true;

                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(this, "Unable to open the Image, Try Again", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
    }

