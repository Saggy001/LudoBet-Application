package com.Saggy.ludobet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class usermanual extends AppCompatActivity {

    ImageView image1 , image2, image3 , image4, image5;
    TextView tag1,tag2,tag3,tag4,tag5;
    TextView heading,content;
    TextView content2;
    TextView content3;
    TextView content4;
    TextView content5;
    TextView content6;
    TextView content7;
    TextView content8;
    TextView content9;
    TextView content10;
    TextView content11;
    ImageButton backbutton;
    int count =0,num;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermanual);
        heading = findViewById(R.id.textheadre);
        content = findViewById(R.id.textcontent);
        content2 = findViewById(R.id.textcontent2);
        content3 = findViewById(R.id.textcontent3);
        content4 = findViewById(R.id.textcontent4);
        content5 = findViewById(R.id.textcontent5);
        content6 = findViewById(R.id.textcontent6);
        content7 = findViewById(R.id.textcontent7);
        content8 = findViewById(R.id.textcontent8);
        content9 = findViewById(R.id.textcontent9);
        content10 = findViewById(R.id.textcontent10);
        content11 = findViewById(R.id.textcontent11);
        backbutton = findViewById(R.id.backbutton);
        tag1 = findViewById(R.id.tagno1);
        tag2 = findViewById(R.id.tagno2);
        tag3 = findViewById(R.id.tagno3);
        tag4 = findViewById(R.id.tagno4);
        tag5 = findViewById(R.id.tagno5);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        image5 = findViewById(R.id.image5);
        image4 = findViewById(R.id.image4);

        tag1.setVisibility(View.GONE);
        tag2.setVisibility(View.GONE);
        tag3.setVisibility(View.GONE);
        tag4.setVisibility(View.GONE);
        tag5.setVisibility(View.GONE);
        image1.setVisibility(View.GONE);
        image2.setVisibility(View.GONE);
        image3.setVisibility(View.GONE);
        image4.setVisibility(View.GONE);
        image5.setVisibility(View.GONE);
        count = getIntent().getExtras().getInt("count");
        num = getIntent().getExtras().getInt("num");

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               onBackPressed();
            }
        });

        if (count == 1){
            heading.setText("Steps to create an account");
            content.setText("1. Click on SignUp button on login screen.");
            content2.setText("2. Enter your email which should be valid.");
            content3.setText("3. Enter a Username or Nickname.");
            content4.setText("4. Enter a 10 digit valid whatsapp number.");
            content5.setText("5. Enter a password which has at least 6 letter.");
            content6.setText("6. Enter your password again for confirmation.");
            content7.setText("7. Read and agree the terms and conditions.");
            content8.setText("8. Make sure you are eligible to create an account.");
            content9.setText("9. Now click on signup button to create an account.");
            content10.setText("10. Your account will be successfully created.");
            content11.setText("11. Now Login with your credentials to continue.");
        }

        else if (count == 3){
            heading.setText("Steps to Sell LudoBet Star Chips");
            content.setText("1. Click on \"Sell Chips\" Button.");
            content2.setText("2. Choose Payment Method.");
            content3.setText("3. Enter the amount you want to withdrawal.");
            content4.setText("4. Enter your payment number or address.");
            content5.setText("5. Agree the terms box to continue.");
            content6.setText("6. Now click on PROCEED to finish the process.");
            content7.setText("7. you will get your withdrawal on the next processing time.");
            content8.setVisibility(View.GONE);
            content9.setVisibility(View.GONE);
            content10.setVisibility(View.GONE);
            content11.setVisibility(View.GONE);
        }
        else if(count == 4){
            heading.setText("Steps to Buy LudoBet Star Chips");
            content.setText("1. Click on \"Buy Chips\" Button.");
            content2.setText("2. Choose Payment Method.");
            content3.setText("3. Enter the amount you want to Add in your Balance.");
            content4.setText("4. Now click on PROCEED to finish the process.");
            content5.setText("5. If you are adding through paytm, pay on a given Paytm Number and enter the amount and Transaction Id or Time , now click on Done.");
            content6.setText("6. The Transaction Id is 11 digit unique key provided by the Paytm whenever you pay on any number.");
            content7.setText("7. It took around 20 min to add through paytm.");
            content8.setText("8. If you are adding through upi, Choose the upi app and pay successfully.");
            content9.setText("9. Don't Press Back, window will automatically come back after payment success");
            content10.setText("10. If you don't have any payment application then download one with the given link below");
            content11.setVisibility(View.GONE);
        }
        else if (count == 5){
            heading.setText("Steps to Fix Match with someone");
            content.setText("1. Set a Challenge of the amount you want to play for, or Click on \"PLAY\" on someone's Challenge but make sure your balance has that much amount of chips.");
            content2.setText("2. If someone requested to your Challenge and you have to play with him, Then click on \"VIEW\" and then \"Accept\" to fix the match.");
            content3.setText("3. Now click on \"START\" to start the match.");
            content4.setText("4. If the Challenge was set by you, so now you have to enter the Roomcode. To do so, First, Open \"Ludo King\" and Click on \"Play with Friends\". Second, Create a Room and enter the Roomcode in the \"LudoBet Star\".");
            content5.setText("5. Now click on Set Button to send the Roomcode to your Opponent, and Go back to \"Ludo King\" App and wait for your Opponent to join.");
            content6.setText("6. If the Challenge was set by your Opponent, Then wait for the Roomcode. When the Roomcode is uploaded, copy the Roomcode and join the Room in the \"Ludo King\" App.");
            content7.setText("7. Your Opponent will start the match, play the match and update the result after the match so that you can play another match.");
            content8.setText("8. You will get Notification sound whenever someone requested you or your opponent uploaded the Roomcode of your match.");
            content9.setVisibility(View.GONE);
            content10.setVisibility(View.GONE);
            content11.setVisibility(View.GONE);
        }
        else if (count==6){
            heading.setText("When the match goes to Pending");
            content.setText("1. When the result of the match mismatch, The match goes to Pending.");
            content2.setText("2. If you Won the match and your Opponent also Update \"I Won\" or Update \"Cancel Match\".");
            content3.setText("3. If you have appeal to cancel the Match and your Opponent Update \"I Won\" or \"I Lost\".");
            content4.setText("4. If you Lost the match and your Opponent also Update \"I Lost\" or Update \"Cancel Match\".");
            content5.setText("5. If a match goes to pending, then admin will solve this match.");
            content6.setText("6. Balance will be updated according to the rules and fine of 50 Ruppees will be charged who Updated the wrong result.");
            content7.setText("7. You can see your Pending match in the Pending Section.");
            content8.setVisibility(View.GONE);
            content9.setVisibility(View.GONE);
            content10.setVisibility(View.GONE);
            content11.setVisibility(View.GONE);
        }
        else if(count == 7){
            heading.setText("How to Update Match Result");
            content.setText("1. If you have Won the match, Take winning Screenshot of the result.");
            content2.setText("2. Now come back to \"LudoBet Star\" and Click on \"I Won\".");
            content3.setText("3. Now click on \"Upload Screenshot\" and select the winning screenshot of your match.");
            content4.setText("4. Now click on \"Send Result\" and your Result will be sent successfully.");
            content5.setText("5. If you have Lost the match, Click On \"I Lost\" and then \"Send Result\".");
            content6.setText("6. If you want to Cancel the match, Click on \"Cancel Match\" and then \"Send Result\".");
            content7.setText("7. Your Balance will be updated after your Opponent's Response.");
            content8.setVisibility(View.GONE);
            content9.setVisibility(View.GONE);
            content10.setVisibility(View.GONE);
            content11.setVisibility(View.GONE);
        }
        else if (count == 8){
            heading.setText("Transaction with admin");
            content.setText("1. In the Wallet Section, You will see your active transaction with admin.");
            content2.setText("2. If you have set a Sell Chips Request your can see its status in Wallet Section.");
            content3.setText("3. While adding Buy Chips Request , Make sure you have added correct Transaction Id or Time because ones the request sent it can't be deleted again.");
            content4.setVisibility(View.GONE);
            content5.setVisibility(View.GONE);
            content6.setVisibility(View.GONE);
            content7.setVisibility(View.GONE);
            content8.setVisibility(View.GONE);
            content9.setVisibility(View.GONE);
            content10.setVisibility(View.GONE);
            content11.setVisibility(View.GONE);
        }
        else if (count == 10){
            heading.setText("Steps to change Username or Whatsapp Number");
            content.setText("1. Click on the top left menu bar on the home page.");
            content2.setText("2. Now click on the account image.");
            content3.setText("3. Click on the \"Edit Profile\" text on the right side.");
            content4.setText("4. Enter new Display Name and Number.");
            content5.setText("5. To Change Display Name, Make sure you don't have any Sell or Add Chips Request, You haven't set any Challenge and no result is Pending by your side.");
            content6.setText("6. Click on the SAVE button to successfully update your profile.");
            content7.setText("7. Your profile is updated now.");
            content8.setText("8. If you are changing Username , you need to Sign In again to switch to a new Username.");
            content9.setVisibility(View.GONE);
            content10.setVisibility(View.GONE);
            content11.setVisibility(View.GONE);
        }
        else if (count == 14){
            heading.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            content2.setVisibility(View.GONE);
            content3.setVisibility(View.GONE);
            content4.setVisibility(View.GONE);
            content5.setVisibility(View.GONE);
            content6.setVisibility(View.GONE);
            content7.setVisibility(View.GONE);
            content8.setVisibility(View.GONE);
            content9.setVisibility(View.GONE);
            content10.setVisibility(View.GONE);
            content11.setVisibility(View.GONE);

            tag1.setVisibility(View.VISIBLE);
            tag2.setVisibility(View.VISIBLE);
            tag3.setVisibility(View.VISIBLE);
            tag4.setVisibility(View.VISIBLE);
            tag5.setVisibility(View.VISIBLE);
            image1.setVisibility(View.VISIBLE);
            image2.setVisibility(View.VISIBLE);
            image3.setVisibility(View.VISIBLE);
            image4.setVisibility(View.VISIBLE);
            image5.setVisibility(View.VISIBLE);

        }


    }
}
