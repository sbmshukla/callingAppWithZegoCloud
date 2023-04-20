package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

public class MainScreen extends AppCompatActivity {

    EditText userId,ed_email,ed_password;
    Button startButton;
    String userIdStr,userEmailStr,userPasswordStr;

    UserModel userModel=new UserModel();
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);


        firebaseDatabase = FirebaseDatabase.getInstance();
        // below line is used to get reference for our database.


        //allocate Space
        userId = findViewById(R.id.ed_mobile_number);
        ed_email = findViewById(R.id.ed_email);
        ed_password = findViewById(R.id.ed_password);
        startButton = findViewById(R.id.createUserAccount);




        startButton.setOnClickListener(v -> {

            //initializing space
            userIdStr = userId.getText().toString();
            userEmailStr = ed_email.getText().toString();
            userPasswordStr = ed_password.getText().toString();
            databaseReference = firebaseDatabase.getReference(userIdStr);
            if (userIdStr.isEmpty()||userEmailStr.isEmpty()||userPasswordStr.isEmpty()) {
                return;
            }
            else {
                addDatatoFirebase(userIdStr, userEmailStr, userPasswordStr);

                startService(userIdStr);
                Intent i = new Intent(MainScreen.this, CallActivity.class);
                i.putExtra("userId", userIdStr);
                startActivity(i);
            }

        });

    }

    private void addDatatoFirebase(String userIdStr, String userEmailStr, String userPasswordStr) {
        userModel.setMobileNumber(userIdStr);
        userModel.setEmailAddress(userEmailStr);
        userModel.setUserPassword(userPasswordStr);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(userModel);

                // after adding this data we are showing toast message.
                Toast.makeText(MainScreen.this, "Account Created Successfully!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainScreen.this, "Failed to create your account! " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    void startService(String userIdStr) {
        Application application = getApplication(); // Android's application context
        long appID = 1934717576;   // yourAppID
        String appSign = "8b2472ac8f32716831d46a92d38465017d272bfe81db9305260ca1192b942295";  // yourAppSign
        String userID = userIdStr; // yourUserID, userID should only contain numbers, English characters, and '_'.
        String userName = userIdStr;   // yourUserName

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";


        ZegoUIKitPrebuiltCallInvitationService.init(application, appID, appSign, userID, userName, callInvitationConfig);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZegoUIKitPrebuiltCallInvitationService.unInit();
    }
}