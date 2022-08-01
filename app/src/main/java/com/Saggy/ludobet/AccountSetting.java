package com.Saggy.ludobet;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AccountSetting extends AppCompatActivity {

    TextView editprofile,emailidtext;
    EditText editnumber,editname;
    Button savebutton;
    ImageButton backbutton1;
    String email;
    String balance;
    String refercode;
    int count = 0;
    long nubr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        editprofile = findViewById(R.id.editdetails);
        emailidtext = findViewById(R.id.emailtag);
        editname = findViewById(R.id.nametag);
        editnumber = findViewById(R.id.phonetag);
        savebutton = findViewById(R.id.savebutton);
        backbutton1 = findViewById(R.id.backbutton1);

        editname.setFocusable(false);
        editname.setFocusableInTouchMode(false);
        editnumber.setFocusableInTouchMode(false);
        editnumber.setFocusable(false);

        final FirebaseUser User = FirebaseAuth.getInstance().getCurrentUser();
        assert User != null;
        email = User.getEmail();
        final String Usname = User.getDisplayName();
        emailidtext.setText(email);
        final String correctemail = email.replace(".com","");

        FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                assert Usname != null;
                editname.setText(dataSnapshot.child("Users").child(Usname).child("Username").getValue(String.class));
                nubr = dataSnapshot.child("Users").child(Usname).child("Phoneno").getValue(Long.class);
                refercode = dataSnapshot.child("Users").child(Usname).child("RC").getValue(String.class);
                balance = dataSnapshot.child("Users").child(Usname).child("Balance").getValue(String.class);
                editnumber.setText(String.valueOf(nubr));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editname.setFocusable(true);
                editname.setFocusableInTouchMode(true);
                editnumber.setFocusableInTouchMode(true);
                editnumber.setFocusable(true);
                Toast.makeText(AccountSetting.this, "You can now change the Display Name and Whatsapp Number", Toast.LENGTH_LONG).show();
                editname.requestFocus();
                count = 1;
            }
        });

        savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(AccountSetting.this);
                progressDialog.setMessage("Profile Updating... Please Wait");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.show();
                if (count == 0){
                    Toast.makeText(AccountSetting.this, "Profile is already saved", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if (editname.getText().toString().isEmpty()){
                    editname.setError("Field Can't be Empty");
                    editname.requestFocus();
                    progressDialog.dismiss();
                }
                else if (editname.getText().toString().length() < 3) {
                    editname.setError("Name must contain atleast 3 character");
                    editname.requestFocus();
                    progressDialog.dismiss();
                }
                else if (editname.getText().toString().length() > 15){
                    editname.setError("Username is too long");
                    editname.requestFocus();
                    progressDialog.dismiss();
                }
                else if (editnumber.getText().toString().isEmpty()){
                    editnumber.setError("Field can't be empty");
                    editnumber.requestFocus();
                    progressDialog.dismiss();
                }
                else if (editnumber.getText().toString().length() != 10){
                    editnumber.setError("Invalid number! Please check again");
                    editnumber.requestFocus();
                    progressDialog.dismiss();
                }
                else if(!editname.getText().toString().equals(Usname)){//user name change
                    FirebaseDatabase.getInstance().getReference().addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            assert Usname != null;
                            if (dataSnapshot.child("Withdrawal_Request").child(Usname).exists()){
                                Toast.makeText(AccountSetting.this, "You have a Sell Chips request, You can't change Username now", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else if (dataSnapshot.child("Add_Request").child(Usname).exists()) {
                                Toast.makeText(AccountSetting.this, "You have a Buy Chips request, You can't change Username now", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else if (dataSnapshot.child("DChallenge").child(Usname).exists()){
                                Toast.makeText(AccountSetting.this, "You have set a challenge", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else if (dataSnapshot.child("pendingMatches").child(Usname).exists()) {
                                Toast.makeText(AccountSetting.this, "You have a match in pending, Wait for the result.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }else if (dataSnapshot.child("Users").child(editname.getText().toString()).exists()){
                                Toast.makeText(AccountSetting.this, "Username is Unavailable", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else {
                                FirebaseDatabase.getInstance().getReference().child("Users").child(editname.getText().toString()).child("Username").setValue(editname.getText().toString());
                                FirebaseDatabase.getInstance().getReference().child("Users").child(editname.getText().toString()).child("Phoneno").setValue(Long.parseLong(editnumber.getText().toString()));
                                FirebaseDatabase.getInstance().getReference().child("Users").child(editname.getText().toString()).child("Balance").setValue(balance);
                                FirebaseDatabase.getInstance().getReference().child("Users").child(editname.getText().toString()).child("EmailId").setValue(correctemail);
                                FirebaseDatabase.getInstance().getReference().child("Users").child(editname.getText().toString()).child("RC").setValue(refercode);

                                FirebaseDatabase.getInstance().getReference().child("Admin").child("ChangeName").child(editname.getText().toString()).setValue(Usname+" to "+ editname.getText().toString());

                                UserProfileChangeRequest profileChangeRequest = new UserProfileChangeRequest.Builder().setDisplayName(editname.getText().toString()).build();
                                User.updateProfile(profileChangeRequest);
                                Toast.makeText(AccountSetting.this, "Login again to update profile successfully", Toast.LENGTH_SHORT).show();
                                FirebaseAuth.getInstance().signOut();
                               /* Query query = FirebaseDatabase.getInstance().getReference().child("Users").child(Usname);
                                query.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        dataSnapshot.getRef().removeValue();
                                        FirebaseAuth.getInstance().signOut();
                                        startActivity(new Intent(AccountSetting.this,MainActivity.class));
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
*/
                               startActivity(new Intent(AccountSetting.this,MainActivity.class));
                               progressDialog.dismiss();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else if (Long.parseLong(editnumber.getText().toString()) != nubr){//only change phone no
                    FirebaseDatabase.getInstance().getReference().child("Users").child(Usname).child("Phoneno").setValue(Long.parseLong(editnumber.getText().toString()));
                    Toast.makeText(AccountSetting.this, "Your Phone no. is changed successfully", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();}
                else{
                    Toast.makeText(AccountSetting.this, "Profile is already saved", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }
        });

        backbutton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
              /*  Intent intent =new Intent(AccountSetting.this,Home_Activity.class);
                intent.putExtra("var",0);
                startActivity(intent);
                finish();*/
            }
        });

    }
}
