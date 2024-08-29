package com.valenpatel.videocallingapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceConfig;
import com.zegocloud.uikit.prebuilt.videoconference.ZegoUIKitPrebuiltVideoConferenceFragment;
import com.zegocloud.uikit.prebuilt.videoconference.config.ZegoLeaveConfirmDialogInfo;

public class ConferenceActivity extends AppCompatActivity {
    TextView meetingIdConference;
    ImageView shareImageConference, closeImageView;
    FrameLayout frameLayout;

    String meetingId, userId, name;

    ZegoUIKitPrebuiltVideoConferenceConfig config = new ZegoUIKitPrebuiltVideoConferenceConfig();
MediaRecorder mediaRecorder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conference);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

mediaRecorder = new MediaRecorder();
        meetingIdConference = findViewById(R.id.meetingIdConferenceActivity);
        shareImageConference = findViewById(R.id.shareImageView);
        closeImageView = findViewById(R.id.closeImageView);
        frameLayout = findViewById(R.id.frameLayoutConferenceActivity);

        meetingId = getIntent().getStringExtra("meeting_ID");
        userId = getIntent().getStringExtra("user_Id");
        name = getIntent().getStringExtra("user_name");


        closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                meetingIdConference.setVisibility(View.GONE);
            }
        });


        meetingIdConference.setText("Meet ID : " + meetingId);

        addFragment();

        shareImageConference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, "Join meeting\nMeeting ID: " + meetingId);
                startActivity(Intent.createChooser(intent, "Share via"));
            }
        });
    }

    public void addFragment() {
        long appID = 1961326485;
        String appSign = "f1397ad877c62145d50856aafcd40777dd25ffd49f84a3567c8c0762a2be3aa3";


        config.turnOnMicrophoneWhenJoining = false;
        config.turnOnCameraWhenJoining = false;

        ZegoUIKitPrebuiltVideoConferenceFragment fragment = ZegoUIKitPrebuiltVideoConferenceFragment
                .newInstance(appID, appSign, userId, name, meetingId, config);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayoutConferenceActivity, fragment)
                .commitNow();


        //leave call on red button clicked
        ZegoLeaveConfirmDialogInfo leaveConfirmDialogInfo = new ZegoLeaveConfirmDialogInfo();
        leaveConfirmDialogInfo.title = "Leave the conference";
        leaveConfirmDialogInfo.message = "Are you sure to leave the conference?";
        leaveConfirmDialogInfo.cancelButtonName = "Cancel";
        leaveConfirmDialogInfo.confirmButtonName = "Confirm";
        config.leaveConfirmDialogInfo = leaveConfirmDialogInfo;

    }

    //OnBackPresses Function
    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ConferenceActivity.this);
        builder.setTitle("End Call");
        builder.setMessage("Do you want to end this call?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish(); //this closes the activity only
//                finishAffinity();      this closes the whole app
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

}