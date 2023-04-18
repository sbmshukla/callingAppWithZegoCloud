package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Collection;
import java.util.Collections;

public class CallActivity extends AppCompatActivity {

    ZegoSendCallInvitationButton voiceCall,videoCall;
    EditText receiver;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        receiver=findViewById(R.id.receiver);
        voiceCall=findViewById(R.id.voiceCall);
        videoCall=findViewById(R.id.videoCall);
        userId=getIntent().getStringExtra("userId");
        receiver.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String targetUserId=receiver.getText().toString().trim();
                setVoiceCall(targetUserId);
                setVideoCall(targetUserId);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    public void setVoiceCall(String targetUserId) {
        voiceCall.setIsVideoCall(false);
        voiceCall.setResourceID("zego_uikit_call");
        voiceCall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserId)));
    }
    public void setVideoCall(String targetUserId){
        videoCall.setIsVideoCall(true);
        videoCall.setResourceID("zego_uikit_call");
        videoCall.setInvitees(Collections.singletonList(new ZegoUIKitUser(targetUserId)));
    }
}