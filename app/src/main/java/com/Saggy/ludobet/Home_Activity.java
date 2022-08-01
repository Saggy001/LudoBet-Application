package com.Saggy.ludobet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Home_Activity extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    FirebaseUser user;
    TextView appname, username, balance;
    ImageView accountperson;
    ImageButton sell, buy;
    Spinner gotispinner;
    Button set,play;
    EditText input;
    String email,Usname;
    String text;
    String  opponentsname;
    String remiveRequestUser,requesteduserBal2;
    String userbal;
    int gotichecker = 0;
    String removeRequestText,userbalancef;
    int bal,matchvalueint;
    ProgressDialog mProgressDialog;

    public static int version = 4;   //appverison
    int appversion;
    String notification_message;
    String notificated_user;
    String notification_sender;

    String applink;
    boolean Mychallengeexist = false;
    boolean startLoop = false;
    String mUser = null;
    String mText = null;

    String mText2 = null;
    String UserforVIEW;
    String TextforVIEW;

    int colorcount =0;
    private static final int UPI_PAYMENT = 1;
    String name,Amount,UpiId,Note;
    ListView listOfMessage;

    String value;

    CountDownTimer countDownTimer;
    String matchValue;
    ValueEventListener valueEventListener;
    ValueEventListener valueEventListener2;
    ValueEventListener valueEventListener3;

    boolean isrequested = false;

    String messageTime = "";

    boolean isAccepted = false;
    boolean removeRequest = false;
    boolean requestAvailable = false;

    FirebaseListAdapter adapter;
    java.util.ArrayList<String> newNotificationArray1 = new java.util.ArrayList<>();
    java.util.ArrayList<String> matchesArray = new java.util.ArrayList<>();

    String notification;
    String usertodelete;
    String texttodelete;
    String opponentPhone;

    String matchAmount;
    boolean requestdelete;

    FirebaseListAdapter arrayAdapter;
    String realValue = "";





    //main process starts
    //oncreate starts
    @Override
    protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_home_);
            firebaseAuth = FirebaseAuth.getInstance();
            user = firebaseAuth.getCurrentUser();
            appname = findViewById(R.id.appname);
            username = findViewById(R.id.username);
            gotispinner = findViewById(R.id.spinnergoti);
            balance = findViewById(R.id.chips);
            accountperson = findViewById(R.id.accountperson);
            sell = findViewById(R.id.sellchips);
            buy = findViewById(R.id.buychips);
            set = findViewById(R.id.setamount);
            input = findViewById(R.id.amountedit);

            email = user.getEmail();
            Usname = user.getDisplayName();

            mProgressDialog = new ProgressDialog(Home_Activity.this);
            mProgressDialog.setMessage("LudoBet Star is Loading ... Please Wait");
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();


            //get users balance
            FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userbalancef = dataSnapshot.child("Users").child(Usname).child("Balance").getValue(String.class);
                    username.setText(Usname);
                    balance.setText(userbalancef);
                    bal = Integer.parseInt(userbalancef);
                    mProgressDialog.dismiss();
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
            userbal = balance.getText().toString();



            //check for app update
            // notification
            int var = getIntent().getExtras().getInt("var");
            if (var == 1){
                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        appversion = dataSnapshot.child("Admin").child("appversion").getValue(Integer.class);
                        if (appversion != version) {
                            applink = dataSnapshot.child("Admin").child("applink").getValue(String.class);
                            AlertDialog builder = new AlertDialog.Builder(Home_Activity.this)
                                    .setTitle("Update Available")
                                    .setMessage("A new Update of the LudoBet Star App is available.\n\n-Payment Gateway improved.\n-App become more stable.\n-Some new features have been added.\n\n" + applink + "\n\nDownload the new apk and install to continue with the LudoBet Star.\n")
                                    .setPositiveButton("Download",null)
                                    .setNegativeButton("cancel",null)
                                    .show();
                            Button postivebutton = builder.getButton(AlertDialog.BUTTON_POSITIVE);
                            postivebutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                   Intent intent = new Intent(Intent.ACTION_VIEW);
                                   intent.setData(Uri.parse(applink));
                                   startActivity(intent);
                                   finish();
                                }
                            });
                            Button negative = builder.getButton(AlertDialog.BUTTON_NEGATIVE);
                            negative.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    FirebaseAuth.getInstance().signOut();
                                    startActivity(new Intent(Home_Activity.this,MainActivity.class));
                                    finish();
                                }
                            });
                        }
                        AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                        builder.setTitle("LudoBet Star Notification").setMessage(dataSnapshot.child("Notification").getValue(String.class))
                                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }
                                ).show();
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }




            //set array adapter to spinner
            ArrayAdapter<CharSequence> arrayadapter = ArrayAdapter.createFromResource(this,R.array.gotisystem,android.R.layout.simple_spinner_item);
            arrayadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            gotispinner.setAdapter(arrayadapter);




            //chatmessage adapetr starts and challenges and mathces loaded
            listOfMessage = findViewById(R.id.listviewfixmatch);
            adapter = new FirebaseListAdapter<ChatMessage>(Home_Activity.this, ChatMessage.class, R.layout.listviewmatch, FirebaseDatabase.getInstance().getReference().child("Challenges")) {
                @SuppressLint("RestrictedApi")
                @Override
                protected void populateView(View v, ChatMessage model, final int position) {
                    TextView messageUser = v.findViewById(R.id.matchtag);
                    messageUser.setText(model.getMessageUser() + model.getMessageText());
                    play = v.findViewById(R.id.play);
                    ImageButton view = v.findViewById(R.id.deletebutton);

                    try {
                        if (getItem(position).getMessageText().contains("\n")) {
                            text = getItem(position).getMessageText().replace("\n" + "  ", "");
                        } else
                            text = getItem(position).getMessageText();
                    } catch (NullPointerException e) {
                        // Null pointer Exception
                    }

                    //check whether i am challenger or requester
                    if (getItem(position).getMessageUser().equals(Usname)) {
                        // This is my Challenge ..
                        Mychallengeexist = true;
                        play.setTag("VIEW");
                        notifyDataSetChanged();
                        play.setBackgroundResource(R.drawable.listviewbuttonview); }
                    //anyother person set challenge
                    else if (!getItem(position).getMessageUser().equals(Usname)) {
                        play.setTag("PLAY");
                        notifyDataSetChanged();
                        play.setBackgroundResource(R.drawable.listviewbutton);
                    }

                    String textNotification = "";
                    try {
                        if (getItem(position).getMessageText().contains("\n")) {
                            textNotification = getItem(position).getMessageText().replace("\n" + "  ", "");
                        } else {
                            textNotification = getItem(position).getMessageText();
                        }
                    } catch (NullPointerException e) {
                        // Null pointer Exception
                    }

                    final String finalTextNotification = textNotification;
                    try {
                        if (getItem(position).getMessageUser().equals(Usname)) {
                            valueEventListener2 = FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    newNotificationArray1.add("fuck");
                                    newNotificationArray1.add("jade");
                                    notification = finalTextNotification;

                                    //check for whether someone reqested me or not
                                    if (dataSnapshot.child("Requests").child(Usname).child(finalTextNotification).exists()) {
                                        try {
                                            if (getItem(position).getMessageUser().equals(Usname)) {
                                            }
                                        } catch (Exception e) {
                                            // index less than 0
                                        }
                                        if (!newNotificationArray1.contains(notification)) {
                                            newNotificationArray1.add(notification);
                                            MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.eventually);
                                            TextforVIEW = finalTextNotification;
                                            String extra = " wants to play for ";
                                            //matchAmount = finalTextNotification.replace(extra, "");
                                            String abc = finalTextNotification.replace(extra, "");
                                            String def = abc.replace(" Full Game","");
                                            String ghi = def.replace(" 1 Goti","");
                                            String jkl = ghi.replace(" 2 Goti","");
                                            String mno = jkl.replace(" 3 Goti","");
                                            matchAmount = mno.replace(" Snack And Ladder","");
                                            UserforVIEW = getItem(position).getMessageUser();
                                            mp.start();

                                            if (!requestdelete){
                                                if (!isFinishing()){
                                                    //showalertdilog requwest beginsn
                                                    final AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                                                    builder.setTitle(" Who Requested your Challenge");
                                                    builder.setMessage(" Match Amount : " + matchAmount);
                                                    LayoutInflater layoutInflater = Home_Activity.this.getLayoutInflater();
                                                    View notifications = layoutInflater.inflate(R.layout.notificationdialogrequest, null);
                                                    ListView listView = notifications.findViewById(R.id.listviewnotification);
                                                    builder.setView(notifications);
                                                    AlertDialog alertDialog = builder.create();
                                                    alertDialog.show();

                                                    //load opponents request
                                                    arrayAdapter = new FirebaseListAdapter<Notification>(Home_Activity.this, Notification.class, R.layout.listitemnotification, com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Requests").child(Usname)) {
                                                        @Override
                                                        protected void populateView(View v, com.Saggy.ludobet.Notification model, final int position) {
                                                            TextView NotificationText = v.findViewById(R.id.opponentrequest);
                                                            NotificationText.setText(model.getNotification());
                                                            Button Accept = v.findViewById(R.id.acceptrequest);
                                                            View view2 = v.findViewById(R.id.view2);
                                                            if (NotificationText.getText().toString().equals("")) {
                                                                Accept.setVisibility(android.view.View.GONE);
                                                                NotificationText.setVisibility(View.GONE);
                                                                view2.setVisibility(View.GONE);
                                                            }

                                                            //if I accepted users request to play
                                                            Accept.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    isAccepted = false;
                                                                    String name = getItem(position).getNotification();
                                                                    String value = " wants to Play with You";
                                                                    opponentsname = "";
                                                                    requesteduserBal2 = "";
                                                                    opponentsname = name.replace(value, "");
                                                                    FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                            requesteduserBal2 = dataSnapshot.child("Users").child(opponentsname).child("Balance").getValue(String.class);
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                                                                        }
                                                                    });
                                                                    final String Text = TextforVIEW;
                                                                    final String User = UserforVIEW;
                                                                    realValue = "";
                                                                    String matchvalue = TextforVIEW;//wants to play for 100 full game
                                                                    matchvalueint = 0;
                                                                    String replace = " wants to play for ";

                                                                    //check null point exception
                                                                    String def = matchvalue.replace(" Full Game","");
                                                                    String ghi = def.replace(" 1 Goti","");
                                                                    String jkl = ghi.replace(" 2 Goti","");
                                                                    String mno = jkl.replace(" 3 Goti","");
                                                                    String abc = mno.replace(" Snack And Ladder","");

                                                                    if (!abc.equals("")) {
                                                                        try {
                                                                            realValue = abc.replace(replace, "");
                                                                            //String  = matchvalue.replace(replace, "");
                                                                        } catch (Exception e) {
                                                                        }
                                                                        if (realValue.equals("")) {
                                                                            android.widget.Toast.makeText(Home_Activity.this, "Press back once", android.widget.Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            matchvalueint = Integer.parseInt(realValue);
                                                                        }

                                                                    }
                                                                    final String goti = Text.replace(replace+realValue,"");

                                                                    //Reconfirm to place a match with your opponent
                                                                    AlertDialog.Builder innerDialog = new android.app.AlertDialog.Builder(Home_Activity.this);
                                                                    innerDialog.setTitle(opponentsname);
                                                                    innerDialog.setMessage("Click on Accept to Accept the Challenge");
                                                                    innerDialog.setPositiveButton("ACCEPT", new android.content.DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(android.content.DialogInterface dialog, int which) {
                                                                            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                                    String checkBal = dataSnapshot.child("Users").child(opponentsname).child("Balance").getValue(String.class);
                                                                                    String checkBal1 = String.valueOf(bal);
                                                                                    int checkBalint = Integer.parseInt(checkBal);
                                                                                    int checkBalint1 = Integer.parseInt(checkBal1);
                                                                                    //check whether request still exist or not
                                                                                    if (!isrequested) {
                                                                                        if (dataSnapshot.child("Challenges").child(User + Text).exists()) {
                                                                                            if (!realValue.equals("")) {
                                                                                                //String goti = Text.replace(" wants to play for "+matchvalueint,"");
                                                                                                if (dataSnapshot.child("Challenges").child(Usname + " vs " + opponentsname + " for " + matchvalueint + goti).exists()) {
                                                                                                    Toast.makeText(Home_Activity.this, "Failed, You already have a Match of same Amount with this Person.",Toast.LENGTH_LONG).show();
                                                                                                } else if (dataSnapshot.child("pendingMatches").child(Usname).child(Usname + " vs " + opponentsname + " for " + matchvalueint + goti).exists()) {
                                                                                                    Toast.makeText(Home_Activity.this, "Failed, You have a Match Pending with this Person.", Toast.LENGTH_LONG).show();
                                                                                                } else if (checkBalint < matchvalueint) {
                                                                                                    Toast.makeText(Home_Activity.this, "Opponent's doesn't have Sufficient Balence, Match not Possible",Toast.LENGTH_LONG).show();
                                                                                                } else if (checkBalint1 < matchvalueint) {
                                                                                                    Toast.makeText(Home_Activity.this, "Insufficient Balance", Toast.LENGTH_LONG).show();
                                                                                                } else {
                                                                                                    if (matchvalueint == 0){
                                                                                                        Toast.makeText(Home_Activity.this, "Press back once", android.widget.Toast.LENGTH_SHORT).show();
                                                                                                        return;
                                                                                                    }else if (matchvalueint > 0){
                                                                                                        isAccepted = true;
                                                                                                        int requesteduserBal2int = Integer.parseInt(requesteduserBal2);
                                                                                                        requesteduserBal2int = requesteduserBal2int - matchvalueint;
                                                                                                        int mybalint = bal;
                                                                                                        mybalint = mybalint - matchvalueint;
                                                                                                        FirebaseDatabase.getInstance().getReference().child("Users").child(opponentsname).child("Balance").setValue(String.valueOf(requesteduserBal2int));
                                                                                                        FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(mybalint));
                                                                                                        adapter.notifyDataSetChanged();
                                                                                                    }
                                                                                                    FirebaseDatabase.getInstance().getReference().child("DChallenge").child(opponentsname).child(opponentsname).setValue("Yes");
                                                                                                    play.setBackgroundResource(R.drawable.listviewbuttonfixmatch);
                                                                                                    Toast.makeText(Home_Activity.this, " Accepted, Press back to Start the Match ", android.widget.Toast.LENGTH_LONG).show();
                                                                                                    builder.setMessage("Go back to START the Match.");

                                                                                                    //match placed and request deleted
                                                                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                                                                    Query query = ref.child("Requests").child(Usname);
                                                                                                    query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                                                                        @Override
                                                                                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                                                            for (DataSnapshot RequestdataSnapshot : dataSnapshot.getChildren()) {
                                                                                                                RequestdataSnapshot.getRef().removeValue();
                                                                                                            }
                                                                                                        }
                                                                                                        @Override
                                                                                                        public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                                                                                                        }
                                                                                                    });

                                                    /*DatabaseReference reff = FirebaseDatabase.getInstance().getReference();

                                                    com.google.firebase.database.Query queryforDchallenge = reff.child("DChallenge").child(Usname);

                                                    queryforDchallenge.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {

                                                            dataSnapshot.getRef().removeValue();
                                                        }

                                                        @Override
                                                        public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                                                        }
                                                    });*/

                                                                                                    // delete the challenge .. after placed match successfully
                                                                                                    DatabaseReference ref1 = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
                                                                                                    Toast.makeText(Home_Activity.this, Usname + " wants to play for " +realValue+goti, Toast.LENGTH_SHORT).show();
                                                                                                    Query query1 = ref1.child("Challenges").child(Usname + " wants to play for " + realValue+goti);
                                                                                                    query1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                                                                        @Override
                                                                                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                                                            for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                                                                dataSnapshot1.getRef().removeValue();
                                                                                                            }
                                                                                                        }
                                                                                                        @Override
                                                                                                        public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(Home_Activity.this, "Match not possible", android.widget.Toast.LENGTH_LONG).show(); }
                                                                                        } else {
                                                                                            Toast.makeText(Home_Activity.this, "Press back once.", android.widget.Toast.LENGTH_LONG).show(); }
                                                                                    }else {
                                                                                        Toast.makeText(Home_Activity.this, "Failed, You have Requested someone", android.widget.Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                                @Override
                                                                                public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                                                                                }
                                                                            });
                                                                            dialog.dismiss();
                                                                        }
                                                                    });
                                                                    innerDialog.setNegativeButton("CANCEL", new android.content.DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(android.content.DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                        }
                                                                    });
                                                                    innerDialog.show();
                                                                    adapter.notifyDataSetChanged();
                                                                }

                                                            });
                                                        }
                                                    };
//        arrayAdapter.add(Notification_String);
                                                    listView.setAdapter(arrayAdapter);
//        builder.setView(notifications);
                                                    builder.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(android.content.DialogInterface dialog, int which) {
                                                            // finish();
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    } else {
                                        if (newNotificationArray1.contains(notification)) {
                                            newNotificationArray1.remove(notification);
                                        }
//com.google.firebase.database.FirebaseDatabase.getInstance().getReference().removeEventListener(valueEventListener2);
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        }
                    } catch (Exception e) {
                        // index less than 1
                    }
                    if (isrequested) {
                        try {
                            valueEventListener = FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (!dataSnapshot.child("Challenges").child(mUser + mText2).exists()) {
//                                    android.widget.Toast.makeText(com.example.chichi.ludobetapp.MainActivity.this, mUser + " declined your Request", android.widget.Toast.LENGTH_SHORT).show();
                                        if (isrequested) {
                                            isrequested = false;
                                        }
//                                    mUser = null;
//                                    mText = null;
                                        FirebaseDatabase.getInstance().getReference().removeEventListener(valueEventListener);
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
//                      if (getItem(position).getMessageUser().equals(mUser) && getItem(position).getMessageText().equals(mText) && getItem(position).getMessageTime().equals(messageTime)) {
                            if (getItem(position).getMessageUser().equals(mUser) && getItem(position).getMessageText().equals(mText)) {
                                play.setTag("isCLICKED");
                            }
                        } catch (NullPointerException e) {
//                        android.widget.Toast.makeText(MainActivity.this, "The Challenge You Requested no longer Extsts", android.widget.Toast.LENGTH_SHORT).show();
                        }
                    }
                    if (removeRequest) {
                        try {
//                        if (getItem(position).getMessageUser().contains(remiveRequestUser) && getItem(position).getMessageText().equals(removeRequestText)) {
                            if (getItem(position).getMessageUser().equals(remiveRequestUser) && getItem(position).getMessageText().equals(removeRequestText)) {
                                play.setTag("PLAY");
                                notifyDataSetChanged();
                            }
                        } catch (NullPointerException e) {
                            // Null Exception takes place ..
                        }
                    }
                    if (isAccepted) {
                        String messagetext;
                        if (TextforVIEW.contains("\n")) {
                            messagetext = TextforVIEW.replace("\n" + "  ", "");
                        } else if (!TextforVIEW.contains("\n")) {
                            messagetext = TextforVIEW;
                        } else {
                            messagetext = TextforVIEW;
                        }
                        String extra = " wants to play for ";
                        String def = messagetext.replace(" Full Game","");
                        String ghi = def.replace(" 1 Goti","");
                        String jkl = ghi.replace(" 2 Goti","");
                        String mno = jkl.replace(" 3 Goti","");
                        String abc = mno.replace(" Snack And Ladder","");
                        String value = abc.replace(extra, "");
                        matchValue = value;
                        String goti = messagetext.replace(extra+matchValue,"");
                        DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm a");
//                    java.text.DateFormat simpleDateFormat1 = new java.text.SimpleDateFormat("HH:mm a");
                        String dateString = simpleDateFormat.format(Calendar.getInstance().getTime());
//                    com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Challenges").child(CurrentUsername + " vs " + opponentsname + " for " + value).setValue(new ChatMessage(CurrentUsername, " vs " + opponentsname + " for " + value, ""));
                        FirebaseDatabase.getInstance().getReference().child("Challenges").child(Usname + " vs " + opponentsname + " for " + value +goti).setValue(new ChatMessage(Usname, " vs " + opponentsname + " for " + value+goti, dateString));
                        isAccepted = false;
                        notifyDataSetChanged();
                    }
                    try {
                        if (getItem(position).getMessageText().contains(" vs ")) {
                            if (getItem(position).getMessageUser().equals(Usname) || getItem(position).getMessageText().contains(Usname + " ")) {
                            /*
                            try {
                                listOfMessage.smoothScrollToPosition(adapter.getCount() - 1);
                            }catch (Exception e){
                                // Exception
                            }
*/

                            //audio add
                                valueEventListener3 = FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        try {
                                            if (dataSnapshot.child("Challenges").child(getItem(position).getMessageUser() + getItem(position).getMessageText()).exists()) {
                                                if (getItem(position).getMessageText().contains(Usname + " ")) {
                                                    if (!matchesArray.contains(getItem(position).getMessageUser() + getItem(position).getMessageText())) {
                                                        matchesArray.add(getItem(position).getMessageUser() + getItem(position).getMessageText());
                                                        MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.eventually);
                                                        mp.start();
                                                    }
                                                } else {
                                                    if (matchesArray.contains(getItem(position).getMessageUser() + getItem(position).getMessageText())) {
                                                        matchesArray.remove(getItem(position).getMessageUser() + getItem(position).getMessageText());
                                                    }
                                                }
                                                FirebaseDatabase.getInstance().getReference().removeEventListener(valueEventListener3);
                                            }
                                        } catch (Exception e) {
//                                        android.widget.Toast.makeText(com.example.chichi.ludobetapp.MainActivity.this, "Previous Match balance has been Updated.", android.widget.Toast.LENGTH_SHORT).show();
                                            // index is -1;
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }
                        }
                    } catch (Exception e) {
                        // Index fucked up
                    }
                    try {
                        if (getItem(position).getMessageText().contains(" vs ")) {
                            view.setClickable(false);
                            view.setVisibility(View.INVISIBLE);
                            if (getItem(position).getMessageUser().equals(Usname) || getItem(position).getMessageText().contains(Usname + " ")) {
                                play.setTag("START");
                            } else {
                                play.setTag("HIDDEN");
                            }
                        }
                    } catch (NullPointerException e) {
                    }
                    if (play.getTag().equals("VIEW")) {
                        play.setClickable(true);
                        play.setVisibility(View.VISIBLE);
                        play.setText("VIEW");
                        play.setAlpha(0.85f);
                        play.setTextColor(Color.BLACK);
                        view.setClickable(true);
                        view.setVisibility(View.VISIBLE);
                    }
                    if (play.getTag().equals("PLAY")) {
                        play.setClickable(true);
                        play.setVisibility(View.VISIBLE);
                        play.setText("PLAY");
                        play.setTextColor(Color.WHITE);
                        play.setAlpha(1);
                        view.setClickable(false);
                        view.setVisibility(View.INVISIBLE);
                    }
                    if (play.getTag().equals("isCLICKED")) {
                        play.setClickable(true);
                        play.setVisibility(View.VISIBLE);
                        //play.setBackgroundColor(android.graphics.Color.rgb(255, 165, 0));
                        play.setTextColor(Color.WHITE);
                        play.setAlpha(0.7f);
                        play.setText("REQUESTED");
                        view.setClickable(true);
                        play.setBackgroundResource(R.drawable.listviewbuttonview);
                        view.setVisibility(View.VISIBLE);
                    }
                    if (play.getTag().equals("RequestAvailable")) {
                        play.setBackgroundColor(Color.GRAY);
                        play.setTextColor(Color.WHITE);
                    }
                    if (play.getTag().equals("START")) {
                        play.setBackgroundResource(R.drawable.listviewbuttonfixmatch);
                        play.setClickable(true);
                        play.setVisibility(View.VISIBLE);
//                    play.setBackgroundColor(android.graphics.Color.rgb(196, 48, 43));
//                    play.setAlpha(0.4f);
                        play.setText("START");
                        play.setTextColor(Color.WHITE);
                        view.setClickable(false);
                        view.setVisibility(View.INVISIBLE);
                    }
                    if (play.getTag().equals("HIDDEN")) {
                        play.setClickable(false);
                        play.setVisibility(View.INVISIBLE);
                    }

                    //when opponent click on play and request got send
                    play.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (v.getTag().equals("PLAY")) {
                                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (!dataSnapshot.child("DChallenge").child(Usname).exists()) {
                                            removeRequest = false;
                                            String extra;
                                            String text;
                                            if (getItem(position).getMessageText().contains("\n")) {
                                                text = getItem(position).getMessageText().replace("\n" + "  ", "");
                                            } else {
                                                text = getItem(position).getMessageText();
                                            }
                                            extra = " wants to play for ";
                                            String def = text.replace(" Full Game","");
                                            String ghi = def.replace(" 1 Goti","");
                                            String jkl = ghi.replace(" 2 Goti","");
                                            String mno = jkl.replace(" 3 Goti","");
                                            String abc = mno.replace(" Snack And Ladder","");
                                            value = abc.replace(extra, "");
//                            String value = mText.replace(extra,"");
                                            int amount = Integer.parseInt(value);
                                            if (bal < amount) {
                                                Toast.makeText(Home_Activity.this, " You Don't have Sufficient Balance to make this Match ", Toast.LENGTH_SHORT).show();
                                            } else if (!isrequested) {
//                                if (requestedcount != 1) {
//                                    play.setEnabled(false);
//                                    isrequested = true;
                                                    mUser = getItem(position).getMessageUser();
//                                    mText = text;
                                                    mText = getItem(position).getMessageText();
                                                    mText2 = text;
                                                    isrequested = true;
                                                    messageTime = getItem(position).getMessageTime();
                                                    notifyDataSetChanged();
                                                    remiveRequestUser = getItem(position).getMessageUser();
                                                    removeRequestText = getItem(position).getMessageText();
                                                    Toast.makeText(Home_Activity.this, getItem(position).getMessageUser() + " has been Requested", Toast.LENGTH_SHORT).show();
                                                    getIntent().putExtra("NotificatedUser", getItem(position).getMessageUser());
                                                    getIntent().putExtra("NotificatedUser'smessage", text);
                                                    notificated_user = getItem(position).getMessageUser();
                                                    notification_sender = Usname;
                                                    notification_message = Usname + " wants to Play with You";
                                                    FirebaseDatabase.getInstance().getReference().child("Requests").child(getItem(position).getMessageUser()).child(text).push().setValue(new Notification(notification_message));
                                                    FirebaseDatabase.getInstance().getReference().child("Requests").child(getItem(position).getMessageUser()).push().setValue(new Notification(notification_message));
                                                } else {
                                                    Toast.makeText(Home_Activity.this, "You have Already requested someone, You can only request them after Starting that Match", Toast.LENGTH_LONG).show();
                                                }
                                        }
                                        else {
                                            Toast.makeText(Home_Activity.this, "Your Challenge already exist, you can not request anyone", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                    }
                                });
                            } else if (v.getTag().equals("VIEW")) {
                                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        String text;
                                        if (getItem(position).getMessageText().contains("\n")) {
                                            text = getItem(position).getMessageText().replace("\n" + "  ", "");
                                        } else {
                                            text = getItem(position).getMessageText();
                                        }
                                        String extra = " wants to play for ";
                                        String def = text.replace(" Full Game","");
                                        String ghi = def.replace(" 1 Goti","");
                                        String jkl = ghi.replace( " 2 Goti","");
                                        String mno = jkl.replace(" 3 Goti","");
                                        String abc = mno.replace(" Snack And Ladder","");
                                        matchAmount = abc.replace(extra, "");
                                        if (dataSnapshot.child("Requests").child(Usname).child(text).exists()) {
                                            UserforVIEW = getItem(position).getMessageUser();
                                            TextforVIEW = text;
                                            if (!requestdelete){
                                                if (!isFinishing()){

                                                    //showaletrdialof requets starts here
                                                    final AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                                                    builder.setTitle(" Who Requested your Challenge");
                                                    builder.setMessage(" Match Amount : " + matchAmount);
                                                    android.view.LayoutInflater layoutInflater = Home_Activity.this.getLayoutInflater();
                                                    View notifications = layoutInflater.inflate(R.layout.notificationdialogrequest, null);
                                                    ListView listView = notifications.findViewById(R.id.listviewnotification);
                                                    builder.setView(notifications);
                                                    AlertDialog alertDialog = builder.create();
                                                    alertDialog.show();
                                                    arrayAdapter = new FirebaseListAdapter<Notification>(Home_Activity.this, Notification.class, R.layout.listitemnotification, FirebaseDatabase.getInstance().getReference().child("Requests").child(Usname)) {
                                                        @Override
                                                        protected void populateView(View v, com.Saggy.ludobet.Notification model, final int position) {
                                                            TextView NotificationText = v.findViewById(R.id.opponentrequest);
                                                            NotificationText.setText(model.getNotification());
                                                            Button Accept = v.findViewById(R.id.acceptrequest);
                                                            View view2 = v.findViewById(R.id.view2);
                                                            if (NotificationText.getText().toString().equals("")) {
                                                                Accept.setVisibility(android.view.View.GONE);
                                                                NotificationText.setVisibility(View.GONE);
                                                                view2.setVisibility(View.GONE);
                                                            }

                                                            //when user accept challenge request
                                                            Accept.setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    isAccepted = false;
                                                                    String name = getItem(position).getNotification();
                                                                    String value = " wants to Play with You";
                                                                    opponentsname = "";
                                                                    requesteduserBal2 = "";
                                                                    opponentsname = name.replace(value, "");
                                                                    FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
                                                                        @Override
                                                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                            requesteduserBal2 = dataSnapshot.child("Users").child(opponentsname).child("Balance").getValue(String.class);
                                                                            // requesteduserBal2 = requesteduser2.getBalance();
                                                                        }
                                                                        @Override
                                                                        public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                                                                        }
                                                                    });
                                                                    final String Text = TextforVIEW;
                                                                    final String User = UserforVIEW;
                                                                    realValue = "";
                                                                    String matchvalue = TextforVIEW;
                                                                    matchvalueint = 0;
                                                                    String replace = " wants to play for ";
                                                                    String def = matchvalue.replace(" Full Game","");
                                                                    String ghi = def.replace(" 1 Goti","");
                                                                    String jkl = ghi.replace(" 2 Goti","");
                                                                    String mno = jkl.replace(" 3 Goti","");
                                                                    String abc = mno.replace(" Snack And Ladder","");
                                                                    if (!abc.equals("")) {
                                                                        try {
                                                                            realValue = abc.replace(replace, "");
                                                                        } catch (Exception e) {
                                                                       }
                                                                        if (realValue.equals("")) {
                                                                            android.widget.Toast.makeText(Home_Activity.this, "Press back once", android.widget.Toast.LENGTH_SHORT).show();
                                                                        } else {
                                                                            matchvalueint = Integer.parseInt(realValue);
                                                                        }
                                                                    }
                                                                    final String goti = matchvalue.replace(replace+realValue,"");
                                                                    android.app.AlertDialog.Builder innerDialog = new android.app.AlertDialog.Builder(Home_Activity.this);
                                                                    innerDialog.setTitle(opponentsname);
                                                                    innerDialog.setMessage("Click on Accept to Accept the Challenge");
                                                                    innerDialog.setPositiveButton("ACCEPT", new android.content.DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(android.content.DialogInterface dialog, int which) {
                                                                            com.google.firebase.database.FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                                                @Override
                                                                                public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                                    String checkBal = dataSnapshot.child("Users").child(opponentsname).child("Balance").getValue(String.class);
                                                                                    String checkBal1 = String.valueOf(bal);
                                                                                    int checkBalint = Integer.parseInt(checkBal);
                                                                                    int checkBalint1 = Integer.parseInt(checkBal1);
                                                                                    if (!isrequested) {
                                                                                        if (dataSnapshot.child("Challenges").child(User + Text).exists()) {
                                                                                            if (!realValue.equals("")) {
                                                                                                if (dataSnapshot.child("Challenges").child(Usname + " vs " + opponentsname + " for " + matchvalueint+goti).exists()) {
                                                                                                    Toast.makeText(Home_Activity.this, "Failed, You already have a Match of same Amount with this Person.",Toast.LENGTH_LONG).show();
                                                                                                } else if (dataSnapshot.child("pendingMatches").child(Usname).child(Usname + " vs " + opponentsname + " for " + matchvalueint+goti).exists()) {
                                                                                                    Toast.makeText(Home_Activity.this, "Failed, You have a Match Pending with this Person.", Toast.LENGTH_LONG).show();
                                                                                                } else if (checkBalint < matchvalueint) {
                                                                                                    Toast.makeText(Home_Activity.this, "Opponent's doesn't have Sufficient Balence, Match not Possible",Toast.LENGTH_LONG).show();
                                                                                                } else if (checkBalint1 < matchvalueint) {
                                                                                                    Toast.makeText(Home_Activity.this, "Insufficient Balance", Toast.LENGTH_LONG).show();
                                                                                                } else {
                                                                                                    if (matchvalueint == 0){
                                                                                                        Toast.makeText(Home_Activity.this, "Press back once", android.widget.Toast.LENGTH_SHORT).show();
                                                                                                        return;
                                                                                                    }else if (matchvalueint != 0 && matchvalueint > 0){
                                                                                                        isAccepted = true;
                                                                                                        int requesteduserBal2int = Integer.parseInt(requesteduserBal2);
                                                                                                        requesteduserBal2int = requesteduserBal2int - matchvalueint;
                                                                                                        int mybalint = bal;
                                                                                                        mybalint = mybalint - matchvalueint;
                                                                                                        FirebaseDatabase.getInstance().getReference().child("Users").child(opponentsname).child("Balance").setValue(String.valueOf(requesteduserBal2int));
                                                                                                        FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(mybalint));
                                                                                                        adapter.notifyDataSetChanged();
                                                                                                    }
                                                                                                    FirebaseDatabase.getInstance().getReference().child("DChallenge").child(opponentsname).child(opponentsname).setValue("Yes");
                                                                                                    play.setBackgroundResource(R.drawable.listviewbuttonfixmatch);
                                                                                                    Toast.makeText(Home_Activity.this, " Accepted, Press back to Start the Match ", android.widget.Toast.LENGTH_LONG).show();
                                                                                                    builder.setMessage("Go back to START the Match.");

                                                                                                    //delete opponents request
                                                                                                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                                                                    Query query = ref.child("Requests").child(Usname);
                                                                                                    query.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                                                                        @Override
                                                                                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                                                            for (DataSnapshot RequestdataSnapshot : dataSnapshot.getChildren()) {
                                                                                                                RequestdataSnapshot.getRef().removeValue();
                                                                                                            }
                                                                                                        }
                                                                                                        @Override
                                                                                                        public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                                                                                                        }
                                                                                                    });
                                                    /*DatabaseReference reff = FirebaseDatabase.getInstance().getReference();
                                                    com.google.firebase.database.Query queryforDchallenge = reff.child("DChallenge").child(Usname);
                                                    queryforDchallenge.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                            dataSnapshot.getRef().removeValue();
                                                        }
                                                        @Override
                                                        public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                                                        }
                                                    });*/
                                                                                                    // delete the challenge ..
                                                                                                    DatabaseReference ref1 = com.google.firebase.database.FirebaseDatabase.getInstance().getReference();
                                                                                                    Query query1 = ref1.child("Challenges").child(Usname + " wants to play for " + realValue + goti);
                                                                                                    query1.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                                                                                                        @Override
                                                                                                        public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                                                                                                            for (com.google.firebase.database.DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                                                                dataSnapshot1.getRef().removeValue();
                                                                                                            }
                                                                                                        }
                                                                                                        @Override
                                                                                                        public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                                                                                                        }
                                                                                                    });
                                                                                                }
                                                                                            } else {
                                                                                                Toast.makeText(Home_Activity.this, "Match not possible", android.widget.Toast.LENGTH_LONG).show();
                                                                                            }
                                                                                        } else {
                                                                                            Toast.makeText(Home_Activity.this, "Press back once.", android.widget.Toast.LENGTH_LONG).show();
                                                                                        }
                                                                                    }else {
                                                                                        Toast.makeText(Home_Activity.this, "Failed, You have Requested someone", android.widget.Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                                @Override
                                                                                public void onCancelled(com.google.firebase.database.DatabaseError databaseError) {
                                                                                }
                                                                            });
                                                                            // Any other requests Exists ..
//                            Intent intent = new android.content.Intent(MainActivity.this,StartMatch.class);
//                            startActivity(intent);
                                                                            dialog.dismiss();
//                            onBackPressed();
                                                                        }
                                                                    });
                                                                    innerDialog.setNegativeButton("CANCEL", new android.content.DialogInterface.OnClickListener() {
                                                                        @Override
                                                                        public void onClick(android.content.DialogInterface dialog, int which) {
                                                                            dialog.dismiss();
                                                                        }
                                                                    });
                                                                    innerDialog.show();
                                                                    adapter.notifyDataSetChanged();
                                                                }

                                                            });
                                                        }
                                                    };
//        arrayAdapter.add(Notification_String);
                                                    listView.setAdapter(arrayAdapter);
//        builder.setView(notifications);
                                                    builder.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
                                                        @Override
                                                        public void onClick(android.content.DialogInterface dialog, int which) {
                                                            // finish();
                                                            dialog.dismiss();
                                                        }
                                                    });
                                                }
                                            }
                                        } else {
                                            Toast.makeText(Home_Activity.this, "Nobody has Challenged You Yet", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            } else if (v.getTag().equals("isCLICKED")) {
                                Toast.makeText(Home_Activity.this, " ALREADY REQUESTED ", Toast.LENGTH_SHORT).show(); }

                            //match gor fixed and opponent just enter fix match class
                            else if (v.getTag().equals("START")) {
                                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        isAccepted = false;
                                        if (dataSnapshot.child("Matches").child(getItem(position).getMessageUser() + getItem(position).getMessageText()).child(" I WON by " + Usname).exists() | dataSnapshot.child("Matches").child(getItem(position).getMessageUser() + getItem(position).getMessageText()).child(" I LOST by " + Usname).exists() | dataSnapshot.child("Matches").child(getItem(position).getMessageUser() + getItem(position).getMessageText()).child(" CANCEL GAME by " + Usname).exists()) {
                                            Toast.makeText(Home_Activity.this, "Your Opponent hasn't posted the Result Yet", Toast.LENGTH_SHORT).show();
                                            AlertDialog.Builder Areyousure = new AlertDialog.Builder(Home_Activity.this);
                                            String message = getItem(position).getMessageText();
                                            String value = " vs ";
                                            String opponentname = message.replace(value, "");
                                            String value1 = " for ";
                                            String opponent = opponentname.replace(value1, "");
                                            String def = opponent.replace(" Full Game","");
                                            String ghi = def.replace(" 1 Goti","");
                                            String jkl = ghi.replace( " 2 Goti","");
                                            String mno = jkl.replace(" 3 Goti","");
                                            String abc = mno.replace(" Snack And Ladder","");
                                            String firstname1 = abc.replaceAll("[^A-Za-z_.]", "");
                                            String phonenumber = String.valueOf(dataSnapshot.child("users").child(firstname1).child("Phoneno").getValue(Long.class));
                                            String phonenumber1 = String.valueOf(dataSnapshot.child("Users").child(getItem(position).getMessageUser()).child("Phoneno").getValue(Long.class));

                                            //when you have uploaded result but your opponent haven't
                                            if (firstname1.equals(Usname)) {
                                                opponentPhone = phonenumber1;
                                                Areyousure.setTitle("Contact " + getItem(position).getMessageUser());
                                                Areyousure.setMessage("Call " + getItem(position).getMessageUser() + " instead of Admin" + "\n" + getItem(position).getMessageUser() + " को Phone या whatsApp करें ");
                                            } else if (getItem(position).getMessageUser().equals(Usname)) {
                                                opponentPhone = phonenumber;
                                                Areyousure.setTitle("Contact " + firstname1);
                                                Areyousure.setMessage("Call " + firstname1 + " instead of Admin" + "\n" + firstname1 + " को Phone या whatsApp करें ");
                                            }
                                            Areyousure.setNegativeButton("WhatApp", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    String url = "https://api.whatsapp.com/send?phone=" + opponentPhone;
                                                    try {
                                                        PackageManager pm = getApplicationContext().getPackageManager();
                                                        pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                                                        Intent intent = new Intent(Intent.ACTION_VIEW);
                                                        intent.setData(Uri.parse(url));
                                                        startActivity(intent);
                                                    } catch (PackageManager.NameNotFoundException e) {
                                                        Toast.makeText(Home_Activity.this, "Whatsapp is not Installed in Your Phone", Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            });
                                            Areyousure.setPositiveButton("Call", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    Intent intent = new Intent(Intent.ACTION_DIAL);
                                                    intent.setData(Uri.parse("tel:" + opponentPhone));
                                                    startActivity(intent);
                                                }
                                            });
                                            Areyousure.show();
                                        } else {
                                            Intent intent = new Intent(Home_Activity.this, Fix_Match.class);
                                            // this using of getItem(position) here can cause the app to break down when View.Clicked ..
//                                    intent.putExtra("Opponentname", opponentsname);
                                            String message = getItem(position).getMessageText();
                                            String value = " vs ";
                                            String opponentname = message.replace(value, "");
                                            String value1 = " for ";
                                            String opponent = opponentname.replace(value1, "");      // vs surya for 50 Full Game
                                            String def = opponent.replace(" Full Game","");
                                            String ghi = def.replace(" 1 Goti","");
                                            String jkl = ghi.replace(" 2 Goti","");
                                            String mno = jkl.replace(" 3 Goti","");
                                            String abc = mno.replace(" Snack And Ladder","");
                                            String balancef = String.valueOf(bal);
                                            String firstname1 = abc.replaceAll("[^A-Za-z_.]", "");
                                            String matchValue = abc.replace(firstname1, "");
                                            String goti = opponent.replace(firstname1+matchValue,"");
                                            int matchValueint = Integer.parseInt(matchValue);
                                           // String phonenumber = String.valueOf(dataSnapshot.child("Users").child(firstname1).child("Phoneno").getValue(Long.class));
                                            String oppbal = dataSnapshot.child("Users").child(firstname1).child("Balance").getValue(String.class);
                                            String oppbal1 = dataSnapshot.child("Users").child(getItem(position).getMessageUser()).child("Balance").getValue(String.class);
                                            int oppbalint = Integer.parseInt(oppbal);
                                            int oppbalint1 = Integer.parseInt(oppbal1);
                                           // String myphonenumber = String.valueOf(dataSnapshot.child("Users").child(Usname).child("Phoneno").getValue(Long.class));
                                            String mybal = dataSnapshot.child("Users").child(Usname).child("Balance").getValue(String.class);
                                            int mybalint = Integer.parseInt(mybal);
                                            int mybeforebal = mybalint + matchValueint;
                                            int oppbeforebal = oppbalint + matchValueint;
                                            int oppbeforebal1 = oppbalint1 + matchValueint;
                                            if (!dataSnapshot.child("Matches").child(getItem(position).getMessageUser() + getItem(position).getMessageText()).child("Before balance").exists()) {
                                                if (getItem(position).getMessageUser().equals(Usname)) {
                                                    FirebaseDatabase.getInstance().getReference().child("Matches").child(getItem(position).getMessageUser() + getItem(position).getMessageText()).child("Before balance").child(Usname + " : Before Balance").setValue(String.valueOf(mybeforebal));
                                                    FirebaseDatabase.getInstance().getReference().child("Matches").child(getItem(position).getMessageUser() + getItem(position).getMessageText()).child("Before balance").child(firstname1 + " : Before Balance").setValue(String.valueOf(oppbeforebal));
                                                } else if (firstname1.equals(Usname)) {
                                                    FirebaseDatabase.getInstance().getReference().child("Matches").child(getItem(position).getMessageUser() + getItem(position).getMessageText()).child("Before balance").child(Usname + " : Before Balance").setValue(String.valueOf(mybeforebal));
                                                    FirebaseDatabase.getInstance().getReference().child("Matches").child(getItem(position).getMessageUser() + getItem(position).getMessageText()).child("Before balance").child(getItem(position).getMessageUser() + " : Before Balance").setValue(String.valueOf(oppbeforebal1));
                                                }
                                            }
                                            intent.putExtra("goti",goti);
                                            intent.putExtra("Opponentname", firstname1);
                                            intent.putExtra("bal", balancef);
                                            intent.putExtra("matchValue", matchValue);
                                            intent.putExtra("myname", getItem(position).getMessageUser());
                                            intent.putExtra("Matchname", getItem(position).getMessageUser() + getItem(position).getMessageText());
                                            startActivity(intent);
                                        }
                                    }
                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
                            }
                        }
                        // android.widget.Toast.makeText(MainActivity.this, getItem(position).getMessageUser() , android.widget.Toast.LENGTH_SHORT).show();
//                    }
                    });
                    view.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (getItem(position).getMessageUser().equals(mUser)) {
                                final String text;
                                if (getItem(position).getMessageText().contains("\n")) {
                                    text = getItem(position).getMessageText().replace("\n" + "  ", "");
                                } else {
                                    text = getItem(position).getMessageText();
                                }
                                AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                                builder.setMessage("Are you sure you want to Delete the Request ?");
                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        // change the button to play ..
                                        removeRequest = true;
                                        isrequested = false;
                                        startLoop = false;
                                        notifyDataSetChanged();
                                        // 3 things Left ..
                                        String Extra = " wants to play for ";
                                        String def = text.replace(" Full Game","");
                                        String ghi = def.replace(" 1 Goti","");
                                        String jkl = ghi.replace(" 2 Goti","");
                                        String mno = jkl.replace(" 3 Goti","");
                                        String abc = mno.replace(" Snack And Ladder","");
                                        String amount = abc.replace(Extra, "");

                                        // Delete the fucking Request ..
                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                        Query query1 = ref.child("Requests").child(getItem(position).getMessageUser()).orderByChild("notification").equalTo(Usname + " wants to Play with You");
                                        query1.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                    dataSnapshot1.getRef().removeValue();
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                        Query query2 = ref1.child("Requests").child(getItem(position).getMessageUser()).child(text).orderByChild("notification").equalTo(Usname + " wants to Play with You");
                                        query2.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                    dataSnapshot1.getRef().removeValue();
                                                }
                                            }
                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                            }
                                        });
                                        dialog.dismiss();
                                        Toast.makeText(Home_Activity.this, "Request deleted !!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                            } else if (getItem(position).getMessageUser().equals(Usname)) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                                builder.setMessage("Are you sure you want to Delete Your Challenge ?");
                                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        usertodelete = "";
                                        usertodelete = getItem(position).getMessageUser();
                                        texttodelete = "";
                                        requestdelete = true;
                                        if (getItem(position).getMessageText().contains("\n")) {
                                            texttodelete = getItem(position).getMessageText().replace("\n" + "  ", "");
                                        } else {
                                            texttodelete = getItem(position).getMessageText();
                                        }
                                        FirebaseDatabase.getInstance().getReference().removeEventListener(valueEventListener2);
                                        mProgressDialog = new ProgressDialog(Home_Activity.this);
                                        mProgressDialog.setMessage("Deleting Challenge, please wait ..\n(depends upon Internet speed)");
                                        mProgressDialog.setCanceledOnTouchOutside(false);
                                        mProgressDialog.setCancelable(false);
                                        mProgressDialog.show();
                                        countDownTimer = new CountDownTimer(5000, 1000) {
                                            @Override
                                            public void onTick(long millisUntilFinished) {
                                            }
                                            @Override
                                            public void onFinish() {
                                                String user = usertodelete;
                                                String text = texttodelete;
                                                requestdelete = false;
                                                String Extra = " wants to play for ";
                                                String def = text.replace(" Full Game","");
                                                String ghi = def.replace(" 1 Goti","");
                                                String jkl = ghi.replace(" 2 Goti","");
                                                String mno = jkl.replace(" 3 Goti","");
                                                String abc = mno.replace(" Snack And Ladder","");
                                                String amount = abc.replace(Extra, "");
                                                int amountint = Integer.parseInt(amount);
                                                String goti = text.replace(Extra+amount,"");
                                                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                // if any Requests exists for my Challenge ..
                                                requestAvailable = false;
                                                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        if (dataSnapshot.child("Requests").child(Usname).exists()) {
                                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                            Query q = ref.child("Requests").child(Usname);
                                                            q.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                        dataSnapshot1.getRef().removeValue();
                                                                    }
                                                                }
                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {
                                                                }
                                                            });
                                                        }
                                                        if (dataSnapshot.child("DChallenge").child(Usname).exists()) {
                                                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                                            Query queryforDchallenge = ref.child("DChallenge").child(Usname);
                                                            queryforDchallenge.addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                                        dataSnapshot1.getRef().removeValue();
                                                                    }
                                                                }
                                                                @Override
                                                                public void onCancelled(DatabaseError databaseError) {
                                                                }
                                                            });
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                    }
                                                });
                                                Query query = ref.child("Challenges").child(user + " wants to play for " +amount+goti);
                                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                                            dataSnapshot1.getRef().removeValue();
                                                        }
                                                    }
                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                    }
                                                });
//                bal = bal + amountint;
                                                Mychallengeexist = false;

//                com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("users").child(CurrentUsername).child("bal").setValue("c" + String.valueOf(bal));
                                                Toast.makeText(Home_Activity.this, "Challenge delete success !!", Toast.LENGTH_SHORT).show();
                                                mProgressDialog.dismiss();
                                            }
                                        };
                                        countDownTimer.start();
                                    }
                                });
                                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                            }
                        }
                    });
                }

                @Override
                public ChatMessage getItem(int pos) {
/*     try {
                   return super.getItem(getCount() - 1 - pos);

                } catch (ArrayIndexOutOfBoundsException e) {

                    return super.getItem(getCount() - pos);
                }
*/
                    return super.getItem(getCount() - 1 - pos);
                }
            };
            listOfMessage.setAdapter(adapter);
        //chatmessage list adapter close


//no. of tasks has been completed
// loading of opponents challenges
// accepted opponents challenge
// requesting opponent to play match
// succesfully placed match and intent to fix match



//now some listeners to do
            final int minAmount = 30;
            final String currenttime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
            balance.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Home_Activity.this, Wallet.class);
                    startActivity(intent);
                }
            });
            appname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    colorchange();
                }
            });

            //user want to place his challenge
            set.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    DateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm a");
                    final String dateString = simpleDateFormat.format(Calendar.getInstance().getTime()) + " for " + input.getText().toString() + " " + simpleDateFormat1.format(Calendar.getInstance().getTime());
                    if (input.getText().toString().isEmpty()) {
                        Toast.makeText(Home_Activity.this, " PLEASE ADD AMOUNT FIRST ", android.widget.Toast.LENGTH_SHORT).show();
                    } else {
                        String value = input.getText().toString();
                        final int inputvalue = Integer.parseInt(value);
                        if (inputvalue == 0 | inputvalue < minAmount | inputvalue % 5 != 0) {
                            Toast.makeText(Home_Activity.this, "CHALLENGE MUST BE GREATER THAN " + minAmount + ", AND MUST BE IN MULTIPLE OF 5 ", Toast.LENGTH_LONG).show();
                        } else if (bal < inputvalue) {
                            Toast.makeText(Home_Activity.this, "YOU DON'T HAVE ENOUGH BALANCE TO SET CHALLENGE FOR " + inputvalue, Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.child("Challenges").child(Usname + " wants to play for " + input.getText().toString()).exists()) {
                                        AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                                        builder.setMessage("You have Already set a challenge for " + input.getText().toString());
                                        builder.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(android.content.DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.show();
                                    } else if (dataSnapshot.child("DChallenge").child(Usname).exists()) {
                                        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Home_Activity.this);
                                        builder.setMessage("Already a Challenge Exist or result of previous match is pending. You can't place more than one Challenge at a time.");
                                        builder.setPositiveButton("OK", new android.content.DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(android.content.DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });
                                        builder.show();
                                    }
                                    else if (isrequested){
                                        Toast.makeText(Home_Activity.this, "You can not set a challenge, you already have requested someone.", Toast.LENGTH_SHORT).show();
                                    }
                                        else {

                                            if (gotispinner.getSelectedItem().toString().equals("Full Game")){
                                                FirebaseDatabase.getInstance().getReference().child("Challenges").child(Usname + " wants to play for " + input.getText().toString()+" Full Game").setValue(new ChatMessage(Usname, " wants to play \n  for " + input.getText().toString()+" Full Game", dateString));
                                                FirebaseDatabase.getInstance().getReference().child("DChallenge").child(Usname).child(Usname).setValue("Yes");
                                                Mychallengeexist = true;
                                                input.setText("");
                                                input.requestFocus();
                                            }
                                            else if (gotispinner.getSelectedItem().toString().equals("1 Goti")){
                                                FirebaseDatabase.getInstance().getReference().child("Challenges").child(Usname + " wants to play for " + input.getText().toString()+" 1 Goti").setValue(new ChatMessage(Usname, " wants to play \n  for " + input.getText().toString() +" 1 Goti", dateString));
                                                FirebaseDatabase.getInstance().getReference().child("DChallenge").child(Usname).child(Usname).setValue("Yes");
                                                Mychallengeexist = true;
                                                input.setText("");
                                                input.requestFocus();
                                            }
                                            else if (gotispinner.getSelectedItem().toString().equals("2 Goti")){
                                                FirebaseDatabase.getInstance().getReference().child("Challenges").child(Usname + " wants to play for " + input.getText().toString()+" 2 Goti").setValue(new ChatMessage(Usname, " wants to play \n  for " + input.getText().toString()+" 2 Goti", dateString));
                                                FirebaseDatabase.getInstance().getReference().child("DChallenge").child(Usname).child(Usname).setValue("Yes");
                                                Mychallengeexist = true;
                                                input.setText("");
                                                input.requestFocus();
                                            }
                                            else if (gotispinner.getSelectedItem().toString().equals("3 Goti")){
                                                FirebaseDatabase.getInstance().getReference().child("Challenges").child(Usname + " wants to play for " + input.getText().toString()+" 3 Goti").setValue(new ChatMessage(Usname, " wants to play \n  for " + input.getText().toString()+" 3 Goti", dateString));
                                                FirebaseDatabase.getInstance().getReference().child("DChallenge").child(Usname).child(Usname).setValue("Yes");
                                                Mychallengeexist = true;
                                                input.setText("");
                                                input.requestFocus();
                                            }
                                            else if (gotispinner.getSelectedItem().toString().equals("Snake")){
                                                FirebaseDatabase.getInstance().getReference().child("Challenges").child(Usname + " wants to play for " + input.getText().toString()+" Snack And Ladder").setValue(new ChatMessage(Usname, " wants to play \n  for " + input.getText().toString()+" Snack And Ladder", dateString));
                                                FirebaseDatabase.getInstance().getReference().child("DChallenge").child(Usname).child(Usname).setValue("Yes");
                                                Mychallengeexist = true;
                                                input.setText("");
                                                input.requestFocus();
                                            }
                                            else
                                                Toast.makeText(Home_Activity.this, "Unable to place your challenge", Toast.LENGTH_SHORT).show();

                                    }
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
/*                      com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Challenges").push().setValue(new ChatMessage(CurrentUsername + " wants to play for " + input.getText().toString()));
                            com.google.firebase.database.FirebaseDatabase.getInstance().getReference().child("Challenges").push().setValue(new ChatMessage(CurrentUsername, " wants to play for " + input.getText().toString()));
                            updateedbal = updateedbal - inputvalue;
                            input.setText("");
                            input.requestFocus();*/
                        }
                    }
                }
            });

//set button eds




        // sell chips dialog begin
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                        LayoutInflater layoutInflater = getLayoutInflater();
                        View dialog = layoutInflater.inflate(R.layout.sellchipsdialog,null);
                        final CheckBox paytm = dialog.findViewById(R.id.paytm);
                        final CheckBox googlepay = dialog.findViewById(R.id.googlepay);
                        final CheckBox phonepe = dialog.findViewById(R.id.phonepe);
                        final CheckBox upi = dialog.findViewById(R.id.upi);
                        final EditText sellamount = dialog.findViewById(R.id.amount);
                        final EditText address = dialog.findViewById(R.id.phoneno);
                        Button proceed = dialog.findViewById(R.id.proceed);
                        final CheckBox agecheck = dialog.findViewById(R.id.checkboxage);
                        TextView prtime = dialog.findViewById(R.id.prtime);
                        String timetrash = currenttime.replace(":","");
                        int tim = Integer.parseInt(timetrash);
                        if (tim > 1200 && tim < 1800)
                            prtime.setText("18:00");
                        else if (tim >= 1800 && tim <2359)
                            prtime.setText("23:59");
                        else {
                            prtime.setText("12:00");
                        }

                        paytm.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                address.setHint("Paytm Number");

                                if (googlepay.isChecked()){
                                    googlepay.toggle();
                                }
                                else if (upi.isChecked())
                                    upi.toggle();
                                else if (phonepe.isChecked())
                                    phonepe.toggle();
                                else if (paytm.isChecked()){
                                }
                                else {
                                    paytm.toggle();
                                }
                            }
                        });
                        upi.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                address.setHint("Payment Address or Number");
                                if (googlepay.isChecked()){
                                    googlepay.toggle();
                                }
                                else if (paytm.isChecked())
                                    paytm.toggle();
                                else if (phonepe.isChecked())
                                    phonepe.toggle();
                                else if (upi.isChecked()){
                                }else
                                {
                                    upi.toggle();
                                }
                            }
                        });
                        googlepay.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                address.setHint("Payment Address or Number");
                                if (paytm.isChecked()){
                                    paytm.toggle();
                                }
                                else if (upi.isChecked())
                                    upi.toggle();
                                else if (phonepe.isChecked())
                                    phonepe.toggle();
                                else if (googlepay.isChecked()){
                                }
                                else {
                                    googlepay.toggle();
                                }
                            }
                        });
                        phonepe.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                address.setHint("Payment Address or Number");
                                if (googlepay.isChecked()){
                                    googlepay.toggle();
                                }
                                else if (upi.isChecked())
                                    upi.toggle();
                                else if (paytm.isChecked())
                                    paytm.toggle();
                                else if (phonepe.isChecked()){
                                }else {
                                    phonepe.toggle();
                                }
                            }
                        });
                        proceed.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.child("Withdrawal_Request").child(Usname).exists()){
                                            Toast.makeText(Home_Activity.this, "You already have a Withdrawal Request", Toast.LENGTH_SHORT).show();
                                        } else if (!(paytm.isChecked()||googlepay.isChecked()||phonepe.isChecked()||upi.isChecked())){
                                            Toast.makeText(Home_Activity.this,"Please select any payment method",Toast.LENGTH_SHORT).show();
                                        } else if (sellamount.getText().toString().isEmpty()){
                                            sellamount.setError("Please Enter Amount");
                                            sellamount.requestFocus();
                                        } else if (Integer.parseInt(sellamount.getText().toString()) > Integer.parseInt(balance.getText().toString()) ){
                                            Toast.makeText(Home_Activity.this, "Play more to reach your goal", Toast.LENGTH_SHORT).show();
                                        } else if (Integer.parseInt(sellamount.getText().toString()) <10){
                                            Toast.makeText(Home_Activity.this,"Minimum amount is 10 ruppees",Toast.LENGTH_LONG).show();
                                        } else if (address.getText().toString().isEmpty()){
                                            address.setError("Enter Phone Number or UPI Address");
                                            address.requestFocus();
                                        } else if (!agecheck.isChecked()){
                                            Toast.makeText(Home_Activity.this, "Selling Request Failed", Toast.LENGTH_SHORT).show();
                                        } else if (paytm.isChecked()){
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Method").setValue("paytm");
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Amount").setValue(sellamount.getText().toString());
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Number").setValue(address.getText().toString());
                                            Toast.makeText(Home_Activity.this,"Sold Successfully",Toast.LENGTH_LONG).show();
                                            int newbalance = Integer.parseInt(balance.getText().toString()) - Integer.parseInt(sellamount.getText().toString());
                                            FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(newbalance));
                                        } else if (upi.isChecked()){
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Method").setValue("UPI");
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Amount").setValue(sellamount.getText().toString());
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Number").setValue(address.getText().toString());
                                            Toast.makeText(Home_Activity.this,"Sold Successfully",Toast.LENGTH_LONG).show();
                                            int newbalance = Integer.parseInt(balance.getText().toString()) - Integer.parseInt(sellamount.getText().toString());
                                            FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(newbalance));
                                        } else if (phonepe.isChecked()){
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Method").setValue("Phonepe");
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Amount").setValue(sellamount.getText().toString());
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Number").setValue(address.getText().toString());
                                            Toast.makeText(Home_Activity.this,"Sold Successfully",Toast.LENGTH_LONG).show();
                                            int newbalance = Integer.parseInt(balance.getText().toString()) - Integer.parseInt(sellamount.getText().toString());
                                            FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(newbalance));
                                        } else if (googlepay.isChecked()){
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Method").setValue("googlepay");
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Amount").setValue(sellamount.getText().toString());
                                            FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Number").setValue(address.getText().toString());
                                            Toast.makeText(Home_Activity.this,"Sold Successfully",Toast.LENGTH_LONG).show();
                                            int newbalance = Integer.parseInt(balance.getText().toString()) - Integer.parseInt(sellamount.getText().toString());
                                            FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(newbalance));
                                        } else{
                                            Toast.makeText(Home_Activity.this,"Sorry something went wrong",Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });

                            }
                        });
                        builder.setView(dialog);
                        builder.show();
                    }
                });//sell chips end







        //Buy chips dialog start
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View mview = layoutInflater.inflate(R.layout.buychipsdialog, null);
                final androidx.appcompat.widget.AppCompatCheckBox paytm = mview.findViewById(R.id.paytmcheckbox);
                final androidx.appcompat.widget.AppCompatCheckBox upi = mview.findViewById(R.id.upicheckbox);
                final EditText buyamount = mview.findViewById(R.id.amount);
                Button proceed = mview.findViewById(R.id.proceed);
                TextView contactadmin = mview.findViewById(R.id.contactadmin);
                TextView gpayclick = mview.findViewById(R.id.googleppayclick);
                TextView paytmclick = mview.findViewById(R.id.paytmclick);
                TextView phonepeclick = mview.findViewById(R.id.phonepeclick);

                gpayclick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://g.co/payinvite/65ke90";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
                phonepeclick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://phon.pe/gy3mmqaf";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
                paytmclick.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://play.google.com/store/apps/details?id=net.one97.paytm";
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);
                    }
                });
                contactadmin.setOnClickListener(new View.OnClickListener() {
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
                                    Toast.makeText(Home_Activity.this,"whatsapp is not installed in your phone",Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });
                upi.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (paytm.isChecked()){
                            paytm.toggle();
                        }
                        else if (upi.isChecked()){

                        }
                        else
                            upi.toggle();
                    }
                });
                paytm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (upi.isChecked()){
                            upi.toggle();
                        }
                        else if (paytm.isChecked()){

                        }
                        else
                            paytm.toggle();
                    }
                });
                proceed.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!(paytm.isChecked()||upi.isChecked())){
                            Toast.makeText(Home_Activity.this,"Please Select a Payment method",Toast.LENGTH_SHORT).show();
                        }
                        else if (buyamount.getText().toString().isEmpty()){
                            Toast.makeText(Home_Activity.this,"Please Enter Amount",Toast.LENGTH_SHORT).show();
                        }
                        else if (Integer.parseInt(buyamount.getText().toString()) <10){
                            Toast.makeText(Home_Activity.this,"Minimum amount is 10 ruppees",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if (paytm.isChecked()){//paytm
                                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.child("Add_Request").child(Usname).exists()){
                                            Toast.makeText(Home_Activity.this, "You already have a buy chips request", Toast.LENGTH_SHORT).show();
                                        }
                                        else{

                                            AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                                            LayoutInflater layoutInflater = getLayoutInflater();
                                            View mywview = layoutInflater.inflate(R.layout.paytmbuydialog, null);

                                            final TextView phonenumberpaytm = mywview.findViewById(R.id.paytmno);
                                            final TextView paytmamount = mywview.findViewById(R.id.amountpaytm);
                                            Button continuebutton = mywview.findViewById(R.id.continue1);
                                            final EditText txnid = mywview.findViewById(R.id.txnid);
                                            ImageView copyb = mywview.findViewById(R.id.copytoclipboard);
                                            TextView otheno = mywview.findViewById(R.id.otherno);

                                            otheno.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                                                    LayoutInflater layoutInflater = getLayoutInflater();
                                                    View mnview = layoutInflater.inflate(R.layout.othernopaytm, null);
                                                    final EditText recipantno = mnview.findViewById(R.id.recipientno);
                                                    final EditText txnidorth = mnview.findViewById(R.id.transidortime);
                                                    final EditText amountother = mnview.findViewById(R.id.amountpaid);
                                                    Button addmoney = mnview.findViewById(R.id.addmoney);

                                                    addmoney.setOnClickListener(new View.OnClickListener() {
                                                        @Override
                                                        public void onClick(View v) {
                                                            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                                                @Override
                                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                    if (dataSnapshot.child("Add_Request").child(Usname).exists()){
                                                                        Toast.makeText(Home_Activity.this, "You already have a Buy Chips request", Toast.LENGTH_SHORT).show();
                                                                    }
                                                                    else if (recipantno.getText().toString().isEmpty()){
                                                                        recipantno.setError("Field can not be Empty");
                                                                        recipantno.requestFocus();
                                                                    }
                                                                    else if (amountother.getText().toString().isEmpty()){
                                                                        amountother.setError("Field can not be Empty");
                                                                        amountother.requestFocus();
                                                                    }
                                                                    else if (txnidorth.getText().toString().isEmpty()){
                                                                        txnidorth.setError("Field can not be Empty");
                                                                        txnidorth.requestFocus();
                                                                    }else  {
                                                                        Toast.makeText(Home_Activity.this, "Request Added Successfully", Toast.LENGTH_SHORT).show();
                                                                        FirebaseDatabase.getInstance().getReference().child("Add_Request").child(Usname).child("Amount").setValue(amountother.getText().toString());
                                                                        FirebaseDatabase.getInstance().getReference().child("Add_Request").child(Usname).child("Txnid").setValue(txnidorth.getText().toString());
                                                                        FirebaseDatabase.getInstance().getReference().child("Add_Request").child(Usname).child("Number").setValue(recipantno.getText().toString());
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                                }
                                                            });
                                                        }
                                                    });

                                                    builder.setView(mnview);
                                                    builder.show();
                                                }
                                            });

                                            FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    phonenumberpaytm.setText(String.valueOf(dataSnapshot.child("Admin").child("Paytmno").getValue(Long.class)));
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            });
                                            paytmamount.setText(buyamount.getText().toString());
                                            copyb.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    Toast.makeText(Home_Activity.this, "Number Copied to Clipboard", Toast.LENGTH_SHORT).show();
                                                    ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                                    ClipData clip = ClipData.newPlainText("Number Copied to Clipboard" , phonenumberpaytm.getText().toString());
                                                    clipboardManager.setPrimaryClip(clip);

                                                }
                                            });
                                            continuebutton.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {
                                                    FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                            if (dataSnapshot.child("Add_Request").child(Usname).exists()){
                                                                Toast.makeText(Home_Activity.this, "You already have a Buy Chips request", Toast.LENGTH_SHORT).show();
                                                            } else if (txnid.getText().toString().isEmpty()){
                                                                txnid.setError("Please Enter txn Id");
                                                                txnid.requestFocus();
                                                            }
                                                            else {
                                                                Toast.makeText(Home_Activity.this, "Request Added Successfully", Toast.LENGTH_SHORT).show();
                                                                FirebaseDatabase.getInstance().getReference().child("Add_Request").child(Usname).child("Amount").setValue(paytmamount.getText().toString());
                                                                FirebaseDatabase.getInstance().getReference().child("Add_Request").child(Usname).child("Txnid").setValue(txnid.getText().toString());
                                                            }
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                                        }
                                                    });
                                                }
                                            });
                                            builder.setView(mywview);
                                            builder.show();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                            }
                            else{//upi
                                Amount = buyamount.getText().toString();
                                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        UpiId = dataSnapshot.child("Admin").child("upi").getValue(String.class);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                name = "LudoBet Star";
                                Note = "Add Amount";

                                Toast.makeText(Home_Activity.this, "Don't Press Back, window will automatically come back after payment success", Toast.LENGTH_SHORT).show();
                                payUsingUpi(Amount, UpiId, name, Note);
                            }
                        }
                    }
                });

                builder.setView(mview);
                builder.show();
            }
        });
    //buy ends




        //dialog menu details
        //menubar
        accountperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View dialogView = layoutInflater.inflate(R.layout.dialog_menu,null);
                ImageView account = dialogView.findViewById(R.id.image_view);
                TextView textemail = dialogView.findViewById(R.id.m_email);
                TextView dialogname = dialogView.findViewById(R.id.m_user_name);
                TextView logout = dialogView.findViewById(R.id.logout);
                Button pending = dialogView.findViewById(R.id.pendingview);
                Button history = dialogView.findViewById(R.id.historybutton);
                Button help = dialogView.findViewById(R.id.helpview);
                Button terms = dialogView.findViewById(R.id.termsview);
                Button invite = dialogView.findViewById(R.id.inviteview);
                Button wallet = dialogView.findViewById(R.id.wallletview);
                textemail.setText(email);
                dialogname.setText(Usname);
                account.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intosetting = new Intent(Home_Activity.this,AccountSetting.class);
                        startActivity(intosetting);
                    }
                });
                pending.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("pendingMatches").child(Usname).exists()){
                                    Intent intopending = new Intent(Home_Activity.this,Pending.class);
                                    startActivity(intopending);
                                }
                                else{
                                    Toast.makeText(Home_Activity.this,"No Pending Match Exist",Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                });
                history.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intohistory = new Intent(Home_Activity.this,History.class);
                        startActivity(intohistory);
                    }
                });
                wallet.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intowallet = new Intent(Home_Activity.this,Wallet.class);
                        startActivity(intowallet);
                    }
                });
                invite.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.child("Users").child(Usname).child("RC").exists()){
                                    Intent intorefer = new Intent(Home_Activity.this , ReferAndEarn.class);
                                    startActivity(intorefer);
                                }
                                else
                                    Toast.makeText(Home_Activity.this,"Your referral code is not generated yet. Play your first match to unlock referral earning or just contact the admin.",Toast.LENGTH_LONG).show();
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });

                       /* FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                applink = dataSnapshot.child("Admin").child("applink").getValue(String.class);
                                AlertDialog dialog = new AlertDialog.Builder(Home_Activity.this)
                                        .setTitle("Invite a Friend")
                                        .setMessage("If you want to invite your friend also in \"LudoBet Star\" then Copy the link below and send it to your Friend.\n\n"+ applink)
                                        .setPositiveButton("Copy",null)
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                            }
                                        }).show();

                              Button positivebutton = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                                positivebutton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                                        ClipData clip = ClipData.newPlainText("" , applink);
                                        clipboardManager.setPrimaryClip(clip);
                                        Toast.makeText(Home_Activity.this, "Link Copied !!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });*/
                    }
                });
                help.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intohelp = new Intent(Home_Activity.this,Help.class);
                        startActivity(intohelp);
                    }
                });
                terms.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intoterm = new Intent(Home_Activity.this,Termsandconditions.class);
                        startActivity(intoterm);
                    }
                });
                logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        firebaseAuth.signOut();
                        Intent intoout = new Intent(Home_Activity.this,MainActivity.class);
                        startActivity(intoout); }
                });

                builder.setView(dialogView);
                builder.show();
            }
        });


    }//end of on create

//now some override methods


    //appname colorful
    private void colorchange() {
        if (colorcount == 0){
            colorcount = 1;
            appname.setTextColor(Color.RED); }
        else if (colorcount == 1){
            colorcount = 2;
            appname.setTextColor(Color.GREEN); }
        else if (colorcount==2){
            appname.setTextColor(Color.BLUE);
            colorcount = 3; }
        else if (colorcount == 3){
            colorcount = 4;
            appname.setTextColor(Color.YELLOW); }
        else if (colorcount==4){
            appname.setTextColor(Color.GRAY);
            colorcount = 5; }
        else if (colorcount == 5){
            colorcount = 6;
            appname.setTextColor(Color.MAGENTA); }
        else if (colorcount==6){
            appname.setTextColor(Color.LTGRAY);
            colorcount = 7; }
        else if (colorcount == 7){
            colorcount = 8;
            appname.setTextColor(Color.CYAN); }
        else {
            appname.setTextColor(Color.BLACK);
            colorcount = 0; }
    }


    //upi integration starts
    public void payUsingUpi(String amount, String upiId, String name, String note) {

        // phonepe needs TxnRefId and TxnId as a compulsary Input parameter, that's why its returning null ..

        Long tsLong = System.currentTimeMillis()/1000;
        String trxnId = tsLong.toString()+"UPI";

        android.net.Uri uri = android.net.Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .appendQueryParameter("tid", trxnId)
                .appendQueryParameter("tr", trxnId)
                .build();

        android.content.Intent upiPayIntent = new android.content.Intent(android.content.Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

//        upiPayIntent.setData(uriforgooglepay);
//        upiPayIntent.setData(uriforpaytm);
//        upiPayIntent.setData(uriforbhim);
//        upiPayIntent.setData(uriforAmazon);
//        upiPayIntent.setData(uriforphonepe);

        // will always show a dialog to user to choose an app
        android.content.Intent chooser = android.content.Intent.createChooser(upiPayIntent, "Pay with ..");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(upiPayIntent, UPI_PAYMENT);
//            startActivity(upiPayIntent);
        } else {
            android.widget.Toast.makeText(Home_Activity.this,"No UPI apps found on Your device, please install one to continue", android.widget.Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, android.content.Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case UPI_PAYMENT:

                if ((RESULT_OK == resultCode) || (resultCode == 11)) {

                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        android.util.Log.d("UPI", "onActivityResult: " + trxt);
                        java.util.ArrayList<String> dataList = new java.util.ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        android.util.Log.d("UPI", "onActivityResult: " + "Return data is null");
                        java.util.ArrayList<String> dataList = new java.util.ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    android.util.Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    java.util.ArrayList<String> dataList = new java.util.ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }
    private void upiPaymentDataOperation(java.util.ArrayList<String> data) {
        if (isConnectionAvailable(Home_Activity.this)) {
            String str = data.get(0);
            Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
            String paymentCancel = "";
            if(str == null) str = "discard";
            String status = "";
            String approvalRefNo = "";
            String response[] = str.split("&");
            for (int i = 0; i < response.length; i++) {
                String equalStr[] = response[i].split("=");
                if(equalStr.length >= 2) {
                    // this is the problem. Status, in case of PhonePay, don't equals any of this shit, Remove the second else and try again.. Maybe spelling is wrong in case of PhonePay.
                    if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                        status = equalStr[1].toLowerCase();
                    }
                    else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                        approvalRefNo = equalStr[1];
                    }
                }
                else {
                    paymentCancel = "Payment cancelled.";
                }
            }
            if (status.equals("success")) {
                //Code to handle successful transaction here.
                android.widget.Toast.makeText(Home_Activity.this, "Payment Success, Balance Updated", android.widget.Toast.LENGTH_LONG).show();
                android.util.Log.d("UPI", "responseStr: "+approvalRefNo);


                int newbalance = Integer.parseInt(balance.getText().toString()) + Integer.parseInt(Amount);

                FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(newbalance));

                DateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss a");
                String dateString = simpleDateFormat.format(Calendar.getInstance().getTime());

                FirebaseDatabase.getInstance().getReference().child("History").child(Usname).child("history").child(dateString).setValue(new historyList(" Added ₹" +Amount + " through UPI ", dateString));


                //FirebaseDatabase.getInstance().getReference().child("Order Request").child(email).child("Amount").setValue(Amount);
                //FirebaseDatabase.getInstance().getReference().child("Order Request").child(email).child("Order Amount Status").setValue("paid");
                //Intent

            }
            else if("Payment cancelled by user.".equals(paymentCancel)) {

                android.widget.Toast.makeText(Home_Activity.this, "Payment Cancelled", android.widget.Toast.LENGTH_SHORT).show();
            }else {

                android.widget.Toast.makeText(Home_Activity.this, "Transaction failed, Please try again.", android.widget.Toast.LENGTH_SHORT).show();
            }
        } else {
            android.widget.Toast.makeText(Home_Activity.this, "Please check Your Internet Connection and try again", android.widget.Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isConnectionAvailable(android.content.Context context) {
        android.net.ConnectivityManager connectivityManager = (android.net.ConnectivityManager) context.getSystemService(android.content.Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            android.net.NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    } //end of upi integration

    @Override
    public void onBackPressed() {
        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("DChallenge").child(Usname).exists()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Home_Activity.this);
                    builder.setMessage("You have set a Challenge or you are in a match, To get sound Notification of Upcoming requests, Don't close the App by back button, Do not remove it from Recent Apps.")
                            .setPositiveButton("Okay, I understand", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    }
                            ).show();
                }
                else finish();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }



}// end of public class



