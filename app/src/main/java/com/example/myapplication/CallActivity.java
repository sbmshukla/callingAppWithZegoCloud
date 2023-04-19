package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Collections;

public class CallActivity extends AppCompatActivity {

    ZegoSendCallInvitationButton voiceCall, videoCall;
    EditText receiver;
    String userId;
    String targetUserId;
    Button button2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        receiver = findViewById(R.id.receiver);
        voiceCall = findViewById(R.id.voiceCall);
        videoCall = findViewById(R.id.videoCall);
        button2 = findViewById(R.id.button2);

        userId = getIntent().getStringExtra("userId");
        targetUserId = receiver.getText().toString().trim();
        receiver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                targetUserId = receiver.getText().toString().trim();
                if (userExist(targetUserId)) {
                    setVoiceCall(targetUserId);
                    setVideoCall(targetUserId);
                } else {

                    button2.setOnClickListener(v -> {

                        Intent I = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + targetUserId));
                        startActivity(I);

                    });
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private boolean userExist(String targetUserId) {
        return false;
    }


    public void setVoiceCall(String targetUserId) {
        voiceCall.setIsVideoCall(false);
        voiceCall.setResourceID("zego_uikit_call");
        voiceCall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserId)));

    }

    public void setVideoCall(String targetUserId) {
        videoCall.setIsVideoCall(true);
        videoCall.setResourceID("zego_uikit_call");
        videoCall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserId)));
    }

}