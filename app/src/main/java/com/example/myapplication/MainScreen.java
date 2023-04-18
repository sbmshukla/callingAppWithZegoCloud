package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.zegocloud.uikit.prebuilt.call.config.ZegoNotificationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig;
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationService;

public class MainScreen extends AppCompatActivity {

    EditText userId;
    Button startButton;
    String userIdStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        userId=findViewById(R.id.editTextText);
        startButton=findViewById(R.id.button);

        startButton.setOnClickListener(v -> {
            userIdStr=userId.getText().toString();
            if (userIdStr.isEmpty()){
                return;
            }
            startService(userIdStr);
            Intent i=new Intent(MainScreen.this,CallActivity.class);
            i.putExtra("userId",userIdStr);
            startActivity(i);
        });

    }
    void startService(String userIdStr){
        Application application = getApplication(); // Android's application context
        long appID = 1934717576;   // yourAppID
        String appSign ="8b2472ac8f32716831d46a92d38465017d272bfe81db9305260ca1192b942295";  // yourAppSign
        String userID =userIdStr; // yourUserID, userID should only contain numbers, English characters, and '_'.
        String userName =userIdStr;   // yourUserName

        ZegoUIKitPrebuiltCallInvitationConfig callInvitationConfig = new ZegoUIKitPrebuiltCallInvitationConfig();
        callInvitationConfig.notifyWhenAppRunningInBackgroundOrQuit = true;
        ZegoNotificationConfig notificationConfig = new ZegoNotificationConfig();
        notificationConfig.sound = "zego_uikit_sound_call";
        notificationConfig.channelID = "CallInvitation";
        notificationConfig.channelName = "CallInvitation";
        ZegoUIKitPrebuiltCallInvitationService.init(application, appID, appSign, userID, userName,callInvitationConfig);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ZegoUIKitPrebuiltCallInvitationService.unInit();
    }
}