package com.Saggy.ludobet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Wallet extends AppCompatActivity {
    private static final int UPI_PAYMENT = 1;
    Button sell, buy;
    FirebaseUser user;
    String Usname;
    ImageButton back;
    TextView balance;
    ImageButton needhelp;
    int abcount = 0;
    int varai = 0;
    String UpiId, Note, name, Amount,email,correctemail;
    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);

        sell = findViewById(R.id.sellbutton);
        buy = findViewById(R.id.buybutton);
        back = findViewById(R.id.backbutton);
        balance = findViewById(R.id.userbalance);
        needhelp = findViewById(R.id.helpbutton);

        user = FirebaseAuth.getInstance().getCurrentUser();
        email= user.getEmail();
        Usname = user.getDisplayName();
        correctemail = email.replace(".com","");

        final String currenttime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());


        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                balance.setText(dataSnapshot.child("Users").child(Usname).child("Balance").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                /*Intent intent = new Intent(Wallet.this, Home_Activity.class);
                intent.putExtra("var",0);
                startActivity(intent);
                finish();*/
            }
        });
        needhelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intohelp = new Intent(Wallet.this, Help.class);
                startActivity(intohelp);
            }
        });


        //transection with admin
        final RelativeLayout relativeLayout = findViewById(R.id.withdrawalbox);
        relativeLayout.setVisibility(View.GONE);
        final RelativeLayout relativeLayout1 = findViewById(R.id.buybox);
        relativeLayout1.setVisibility(View.GONE);
        final TextView method = findViewById(R.id.method);
        final TextView amountrequesttar = findViewById(R.id.Amount);
        final TextView status = findViewById(R.id.Statuspending);
        final TextView method2 = findViewById(R.id.method2);
        final ImageButton deletesellreq = findViewById(R.id.deletesellrequest);
        final TextView amountrequesttar2 = findViewById(R.id.Amount2);
        final TextView status2 = findViewById(R.id.Statuspending2);
        final TextView numbertxt = findViewById(R.id.status2);
        final TextView notranstext = findViewById(R.id.tag6);
        notranstext.setVisibility(View.GONE);

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("Withdrawal_Request").child(Usname).exists()){
                    relativeLayout.setVisibility(View.VISIBLE);
                    notranstext.setVisibility(View.GONE);
                    method.setText(dataSnapshot.child("Withdrawal_Request").child(Usname).child("Method").getValue(String.class));
                    amountrequesttar.setText(dataSnapshot.child("Withdrawal_Request").child(Usname).child("Amount").getValue(String.class));
                    status.setText(dataSnapshot.child("Withdrawal_Request").child(Usname).child("Number").getValue(String.class));
                }
                else abcount = 1;
                if (dataSnapshot.child("Add_Request").child(Usname).exists()) {
                    relativeLayout1.setVisibility(View.VISIBLE);
                    notranstext.setVisibility(View.GONE);
                    amountrequesttar2.setText(dataSnapshot.child("Add_Request").child(Usname).child("Amount").getValue(String.class));
                    status2.setText(dataSnapshot.child("Add_Request").child(Usname).child("Txnid").getValue(String.class));
                }
                else if (abcount== 1){
                    notranstext.setVisibility(View.VISIBLE);
                    relativeLayout.setVisibility(View.GONE);
                    relativeLayout1.setVisibility(View.GONE);
                }


            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
            });

        deletesellreq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = new ProgressDialog(Wallet.this);
                mProgressDialog.setMessage("Deleting Sell Request... Please Wait");
                mProgressDialog.setCanceledOnTouchOutside(false);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                String beforebalance = balance.getText().toString();
                int beeforebalanceint = Integer.parseInt(beforebalance);
                String sellamount = amountrequesttar.getText().toString();
                int sellamountint = Integer.parseInt(sellamount);
                final int FinalBalanceint = beeforebalanceint + sellamountint;
                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child("Withdrawal_Request").child(Usname).exists()) {
                            Query query = FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname);
                            query.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    dataSnapshot.getRef().removeValue();
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(FinalBalanceint));
                                    mProgressDialog.dismiss();
                                    relativeLayout.setVisibility(View.GONE);
                                    Toast.makeText(Wallet.this, "Sell Request Deleted,Balance Updated", Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                }
                            });
                        } else {
                            mProgressDialog.dismiss();
                            Toast.makeText(Wallet.this, "Error Occured, Contact Admin", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });






        //user wants to sell chips
        sell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Wallet.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View dialog = layoutInflater.inflate(R.layout.sellchipsdialog,null);

                final CheckBox paytm = dialog.findViewById(R.id.paytm);
                final CheckBox googlepay = dialog.findViewById(R.id.googlepay);
                final CheckBox phonepe = dialog.findViewById(R.id.phonepe);
                final CheckBox upi = dialog.findViewById(R.id.upi);
                final EditText amount = dialog.findViewById(R.id.amount);
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
                                    Toast.makeText(Wallet.this, "You already have a Withdrawal Request", Toast.LENGTH_SHORT).show();
                                } else if (!(paytm.isChecked()||googlepay.isChecked()||phonepe.isChecked()||upi.isChecked())){
                                    Toast.makeText(Wallet.this,"Please select any payment method",Toast.LENGTH_SHORT).show();
                                } else if (amount.getText().toString().isEmpty()){
                                    amount.setError("Please Enter Amount");
                                    amount.requestFocus();
                                } else if (Integer.parseInt(amount.getText().toString()) > Integer.parseInt(balance.getText().toString()) ){
                                    Toast.makeText(Wallet.this, "Play more to reach your goal", Toast.LENGTH_SHORT).show();
                                } else if (Integer.parseInt(amount.getText().toString()) <10){
                                    Toast.makeText(Wallet.this,"Minimum amount is 10 ruppees",Toast.LENGTH_LONG).show();
                                } else if (address.getText().toString().isEmpty()){
                                    address.setError("Enter Phone Number or UPI Address");
                                    address.requestFocus();
                                } else if (!agecheck.isChecked()){
                                    Toast.makeText(Wallet.this, "Selling Request Failed", Toast.LENGTH_SHORT).show();
                                } else if (paytm.isChecked()){
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Method").setValue("paytm");
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Amount").setValue(amount.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Number").setValue(address.getText().toString());
                                    Toast.makeText(Wallet.this,"Sold Successfully",Toast.LENGTH_LONG).show();
                                    int newbalance = Integer.parseInt(balance.getText().toString()) - Integer.parseInt(amount.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(newbalance));
                                } else if (upi.isChecked()){
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Method").setValue("UPI");
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Amount").setValue(amount.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Number").setValue(address.getText().toString());
                                    Toast.makeText(Wallet.this,"Sold Successfully",Toast.LENGTH_LONG).show();
                                    int newbalance = Integer.parseInt(balance.getText().toString()) - Integer.parseInt(amount.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(newbalance));
                                } else if (phonepe.isChecked()){
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Method").setValue("Phonepe");
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Amount").setValue(amount.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Number").setValue(address.getText().toString());
                                    Toast.makeText(Wallet.this,"Sold Successfully",Toast.LENGTH_LONG).show();
                                    int newbalance = Integer.parseInt(balance.getText().toString()) - Integer.parseInt(amount.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(newbalance));
                                } else if (googlepay.isChecked()){
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Method").setValue("googlepay");
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Amount").setValue(amount.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("Withdrawal_Request").child(Usname).child("Number").setValue(address.getText().toString());
                                    Toast.makeText(Wallet.this,"Sold Successfully",Toast.LENGTH_LONG).show();
                                    int newbalance = Integer.parseInt(balance.getText().toString()) - Integer.parseInt(amount.getText().toString());
                                    FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Balance").setValue(String.valueOf(newbalance));
                                } else{
                                    Toast.makeText(Wallet.this,"Sorry something went wrong",Toast.LENGTH_SHORT).show();
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
        });
        //sell dialog ends



        //user wants to buy chips
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Wallet.this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View mview = layoutInflater.inflate(R.layout.buychipsdialog, null);
                final androidx.appcompat.widget.AppCompatCheckBox paytm = mview.findViewById(R.id.paytmcheckbox);
                final androidx.appcompat.widget.AppCompatCheckBox upi = mview.findViewById(R.id.upicheckbox);
                final EditText amount = mview.findViewById(R.id.amount);
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
                                    Toast.makeText(Wallet.this,"whatsapp is not installed in your phone",Toast.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });



                      /* String url = "http://api.whatsapp.com/send?phone=" + "91" +"8168774163";
                       try {
                           PackageManager pm = getApplicationContext().getPackageManager();
                           pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
                           Intent intent =new Intent(Intent.ACTION_VIEW);
                           intent.setData(Uri.parse(url));
                           startActivity(intent);
                       } catch (PackageManager.NameNotFoundException e) {
                          Toast.makeText(Wallet.this,"whatsapp is not installed in your phone",Toast.LENGTH_LONG).show();
                       }*/
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
                            Toast.makeText(Wallet.this,"Please Select a Payment method",Toast.LENGTH_SHORT).show();
                        }
                        else if (amount.getText().toString().isEmpty()){
                            Toast.makeText(Wallet.this,"Please Enter Amount",Toast.LENGTH_SHORT).show();
                        }
                        else if (Integer.parseInt(amount.getText().toString()) <10){
                            Toast.makeText(Wallet.this,"Minimum amount is 10 ruppees",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            if (paytm.isChecked()){//paytm
                                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        if (dataSnapshot.child("Add_Request").child(Usname).exists()){
                                            Toast.makeText(Wallet.this, "You already have a buy chips request", Toast.LENGTH_SHORT).show();
                                        }
                                        else{

                                            AlertDialog.Builder builder = new AlertDialog.Builder(Wallet.this);
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
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(Wallet.this);
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
                                                                        Toast.makeText(Wallet.this, "You already have a Buy Chips request", Toast.LENGTH_SHORT).show();
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
                                                                        Toast.makeText(Wallet.this, "Request Added Successfully", Toast.LENGTH_SHORT).show();
                                                                        FirebaseDatabase.getInstance().getReference().child("Add_Request").child(Usname).child("Amount").setValue(amountother.getText().toString());
                                                                        FirebaseDatabase.getInstance().getReference().child("Add_Request").child(Usname).child("Txnid").setValue(txnidorth.getText().toString());
                                                                        FirebaseDatabase.getInstance().getReference().child("Add_Request").child(Usname).child("Number").setValue(recipantno.getText().toString());
                                                                        onBackPressed();
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
                                            paytmamount.setText(amount.getText().toString());
                                            copyb.setOnClickListener(new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                    Toast.makeText(Wallet.this, "Number Copied to Clipboard", Toast.LENGTH_SHORT).show();
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
                                                                Toast.makeText(Wallet.this, "You already have a buy chips request", Toast.LENGTH_SHORT).show();
                                                            } else if (txnid.getText().toString().isEmpty()){
                                                                txnid.setError("Please Enter txn Id");
                                                                txnid.requestFocus();
                                                            }
                                                            else {
                                                                Toast.makeText(Wallet.this, "Request Added Successfully", Toast.LENGTH_SHORT).show();
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
                                Amount = amount.getText().toString();
                                FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        UpiId = dataSnapshot.child("Admin").child("upi").getValue(String.class);
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                                //UpiId = "sagarchaddha16@oksbi";
                                name = "LudoBet Star";
                                Note = "Add Amount";

                                Toast.makeText(Wallet.this, "Don't Press Back, window will automatically come back after payment success", Toast.LENGTH_SHORT).show();
                                payUsingUpi(Amount, UpiId, name, Note);
                            }
                        }
                    }
                });

                builder.setView(mview);
                builder.show();
            }
        });
    }//buy ends





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
            android.widget.Toast.makeText(Wallet.this,"No UPI apps found on Your device, please install one to continue", android.widget.Toast.LENGTH_SHORT).show();
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

        if (isConnectionAvailable(Wallet.this)) {

            String str = data.get(0);
            android.util.Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
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
                android.widget.Toast.makeText(Wallet.this, "Payment Success, Balance Updated", android.widget.Toast.LENGTH_LONG).show();
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

                android.widget.Toast.makeText(Wallet.this, "Payment Cancelled", android.widget.Toast.LENGTH_SHORT).show();
            }else {

                android.widget.Toast.makeText(Wallet.this, "Transaction failed, Please try again.", android.widget.Toast.LENGTH_SHORT).show();
            }
        } else {
            android.widget.Toast.makeText(Wallet.this, "Please check Your Internet Connection and try again", android.widget.Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isConnectionAvailable(android.content.Context context) {
       // Network network = null;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            //network = connectivityManager.getActiveNetwork();
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }
}//upi integration end
