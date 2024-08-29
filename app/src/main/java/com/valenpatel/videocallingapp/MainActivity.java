package com.valenpatel.videocallingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

;

import java.util.Random;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    TextInputEditText meetingIDinput, nameInput;
    Button joinRoomButton, createMeetingButton;
    String name;
    ImageView imageView;
    SharedPreferences preferences;

    //        protected PowerManager.WakeLock mWakeLock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);  //this keeps the app screen on

        meetingIDinput = findViewById(R.id.enterRoomIdTextInputEdiText);
        joinRoomButton = findViewById(R.id.joinButtonMain);
        nameInput = findViewById(R.id.nameTextInputEdiText);
        createMeetingButton = findViewById(R.id.createMeetingButtonMain);
        imageView = findViewById(R.id.moreImageView);
        preferences = getSharedPreferences("name_pref", MODE_PRIVATE);
        nameInput.setText(preferences.getString("name", ""));

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
            }
        });

        joinRoomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String meetingId = meetingIDinput.getText().toString();
                if (meetingId.length() != 10) {
                    meetingIDinput.setError("Meeting ID Must Contain 10 digits");
                    meetingIDinput.requestFocus();
                    return;

                }
                name = nameInput.getText().toString();
                if (name.isEmpty()) {
                    nameInput.setError("Name is required to join the meeting");
                    nameInput.requestFocus();
                    return;

                }
                startMeeting(meetingId, name);
            }
        });


        createMeetingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameInput.getText().toString();
                Toast.makeText(MainActivity.this, "" + name, Toast.LENGTH_SHORT).show();
                if (name.isEmpty()) {
                    nameInput.setError("Name is required to Create the meeting");
                    nameInput.requestFocus();
                    return;

                }
                startMeeting(getRandomMeetingId(), name);

            }
        });


    }  //OnCreate ENDS here


    public void startMeeting(String meetingId, String name) {
        preferences.edit().putString("name", name).apply();
        String userID = UUID.randomUUID().toString();
        Intent intent = new Intent(MainActivity.this, ConferenceActivity.class);
        intent.putExtra("meeting_ID", meetingId);
        intent.putExtra("user_name", name);
        intent.putExtra("user_Id", userID);
        startActivity(intent);
    }

    String getRandomMeetingId() {
        StringBuilder id = new StringBuilder();
        while (id.length() != 10) {
            int random = new Random().nextInt(10);
            id.append(random);
        }
        return id.toString();
    }
}
