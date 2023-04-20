package com.example.myapplication;

import androidx.annotation.NonNull;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.zegocloud.uikit.prebuilt.call.invite.widget.ZegoSendCallInvitationButton;
import com.zegocloud.uikit.service.defines.ZegoUIKitUser;

import java.util.Collections;

public class CallActivity extends AppCompatActivity {

    ZegoSendCallInvitationButton voiceCall, videoCall;
    EditText receiver;
    String userId;
    String targetUserId;
    Button callNormally, bt_verify;
    String rvMobile = "";
    UserModel receiverData = new UserModel();

    // creating a variable for our
    // Database Reference for Firebase.
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);
        receiver = findViewById(R.id.receiver);
        voiceCall = findViewById(R.id.voiceCall);
        videoCall = findViewById(R.id.videoCall);
        callNormally = findViewById(R.id.callNormally);
        bt_verify = findViewById(R.id.bt_verify);
        callNormally.setVisibility(View.GONE);
        voiceCall.setVisibility(View.GONE);
        videoCall.setVisibility(View.GONE);

        userId = getIntent().getStringExtra("userId");

        bt_verify.setOnClickListener(v -> {
            targetUserId = receiver.getText().toString();
            userExist(targetUserId);

            receiver.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    rvMobile = "";
                    callNormally.setVisibility(View.GONE);
                    voiceCall.setVisibility(View.GONE);
                    videoCall.setVisibility(View.GONE);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        });
    }

    private void userExist(String targetUserId) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(targetUserId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                receiverData = snapshot.getValue(UserModel.class);
                if (receiverData != null) {
                    rvMobile = receiverData.getMobileNumber();
                }
                if (rvMobile.isEmpty()) {
                    Toast.makeText(CallActivity.this, "Not Using Our App.", Toast.LENGTH_SHORT).show();
                    callNormally.setVisibility(View.VISIBLE);
                    voiceCall.setVisibility(View.GONE);
                    videoCall.setVisibility(View.GONE);
                    callNormally.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent I = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + targetUserId));
                            startActivity(I);
                        }
                    });
                } else {
                    Toast.makeText(CallActivity.this, "Using Our App.", Toast.LENGTH_SHORT).show();

                    callNormally.setVisibility(View.GONE);
                    voiceCall.setVisibility(View.VISIBLE);
                    videoCall.setVisibility(View.VISIBLE);
                    setVoiceCall(targetUserId);
                    setVideoCall(targetUserId);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(CallActivity.this, "Not Using Our App.", Toast.LENGTH_SHORT).show();
            }
        });
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