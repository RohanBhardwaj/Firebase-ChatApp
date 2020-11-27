package com.example.nchatapp0;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.nchatapp0.Adapter.GroupAdapter;
import com.example.nchatapp0.Adapter.MessageAdapter;
import com.example.nchatapp0.Fragments.GroupChat;
import com.example.nchatapp0.Model.Chat;
import com.example.nchatapp0.Model.Gchat;
import com.example.nchatapp0.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.SimpleTimeZone;

//public class GroupChatActivity extends AppCompatActivity {
//
//   // private Toolbar mToolbar;
//    private ImageButton sendMessageButton;
//    private EditText userMessageInput;
//    private ScrollView mScrollView;
//    private TextView displayTextMessage;
//    private TextView groupName;
//    private DatabaseReference userRef,groupRef,groupMsgKeyRef;
//
//    private FirebaseAuth mAuth;
//    private String currentGroupName,currentUserID,currentUserName,currentDate,currentTime;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_group_chat2);
//        InitializeFields();
//        currentGroupName = getIntent().getExtras().get("groupName").toString();
//        groupName.setText(currentGroupName);
//
//        mAuth =FirebaseAuth.getInstance();
//        currentUserID = mAuth.getCurrentUser().getUid();
//        userRef = FirebaseDatabase.getInstance().getReference("MyUsers").child(currentUserID);
//        groupRef = FirebaseDatabase.getInstance().getReference().child("Groups").child(currentGroupName);
//        getUserInfo();
//        sendMessageButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                SaveMessageToDatabase();
//                userMessageInput.setText("");
//            }
//        });
//    }
//
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        groupRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                if(dataSnapshot.exists()){
//                    DisplayMessages(dataSnapshot);
//                }
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                if(dataSnapshot.exists()){
//                    DisplayMessages(dataSnapshot);
//                }
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void DisplayMessages(DataSnapshot dataSnapshot) {
//        Iterator iterator = dataSnapshot.getChildren().iterator();
//        while(iterator.hasNext()){
//            String Date = (String) ((DataSnapshot)iterator.next()).getValue();
//            String ChatMsg = (String) ((DataSnapshot)iterator.next()).getValue();
//            String Time = (String) ((DataSnapshot)iterator.next()).getValue();
//            String ChatName = (String) ((DataSnapshot)iterator.next()).getValue();
//            displayTextMessage.append(ChatName+":"+ChatMsg+"\n"+Date+"   "+Time+"\n");
//        }
//
//
//    }
//
//    private void InitializeFields() {
//        groupName = findViewById(R.id.group_name);
//       // mToolbar = findViewById(R.id.toolbar);
//        sendMessageButton = findViewById(R.id.btn_send);
//        userMessageInput = findViewById(R.id.text_send);
//        mScrollView = findViewById(R.id.recycler_view_group_chat);
//        displayTextMessage = findViewById(R.id.group_chat_text_display);
//    }
//
//    private void getUserInfo() {
//        userRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                currentUserName = dataSnapshot.child("username").getValue().toString();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//
//    private void SaveMessageToDatabase(){
//        String message = userMessageInput.getText().toString();
//        String messageKey = groupRef.push().getKey(); //generates a key for every message
//        if(TextUtils.isEmpty(message)){
//            Toast.makeText(this, "Can't send Empty Text", Toast.LENGTH_SHORT).show();
//        }
//        else {
//
//            Calendar calForDate =Calendar.getInstance();
//            SimpleDateFormat currentDateFormat = new SimpleDateFormat("MMM dd, yyyy ");
//            currentDate = currentDateFormat.format(calForDate.getTime());
//
//            Calendar calForTime = Calendar.getInstance();
//            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm");
//            currentTime = currentTimeFormat.format(calForTime.getTime());
//
//            HashMap<String,Object> groupMessageKey = new HashMap<>();
//            groupRef.updateChildren(groupMessageKey);
//
//            groupMsgKeyRef = groupRef.child(messageKey);
//            HashMap<String,Object> messages = new HashMap<>();
//            messages.put("username",currentUserName);
//            messages.put("message",message);
//            messages.put("date",currentDate);
//            messages.put("time",currentTime);
//            groupMsgKeyRef.updateChildren(messages);
//        }
//
//    }
//}
public class GroupChatActivity extends AppCompatActivity {

    // private Toolbar mToolbar;
    private ImageButton sendMessageButton;
    private EditText userMessageInput;
    private ScrollView mScrollView;
    private TextView displayTextMessage;
    private TextView groupName;
    private ImageView imageView;
    private TextView username;
    private DatabaseReference userRef,groupRef,groupMsgKeyRef,reference;
    private TextView timestamp;
    RecyclerView recyclerView;
    List<Gchat> mGChat = new ArrayList<>();
    Users user;
    GroupAdapter groupAdapter;

    private FirebaseAuth mAuth;
    private String currentGroupName,currentUserID,currentUserName,currentDate,currentTime,profilepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat2);
        InitializeFields();
        currentGroupName = getIntent().getExtras().get("groupName").toString();
        groupName.setText(currentGroupName);

        mAuth =FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        userRef = FirebaseDatabase.getInstance().getReference("MyUsers").child(currentUserID);
        groupRef = FirebaseDatabase.getInstance().getReference().child("Forums").child(currentGroupName);
        getUserInfo();
        sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveMessageToDatabase();
                userMessageInput.setText("");
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        groupRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                if(dataSnapshot.exists()){
                    DisplayMessages(dataSnapshot);
                }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void DisplayMessages(DataSnapshot dataSnapshot) {

//        for(DataSnapshot snapshot: dataSnapshot.getChildren()){

            //Gchat gchat = snapshot.getValue(Gchat.class);

        Iterator iterator = dataSnapshot.getChildren().iterator();
        while(iterator.hasNext()){


            String ChatMsg = (String) ((DataSnapshot)iterator.next()).getValue();
            String Image = (String) ((DataSnapshot)iterator.next()).getValue();
            String Timestamp = (String) ((DataSnapshot)iterator.next()).getValue();
          //  String Time = (String) ((DataSnapshot)iterator.next()).getValue();
            String ChatName = (String) ((DataSnapshot)iterator.next()).getValue();
            Gchat gchat = new Gchat(ChatName,ChatMsg,Timestamp,Image);
//        gchat.setDate(Date);
//        gchat.setMessage(ChatMsg);
//        gchat.setTime(Time);
//        gchat.setUsername(ChatName);
            mGChat.add(gchat);
            reference = FirebaseDatabase.getInstance().getReference("MyUsers").child(gchat.getUsername());
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                   Users  userz = dataSnapshot.getValue(Users.class);

                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            groupAdapter = new GroupAdapter(GroupChatActivity.this,mGChat);
            recyclerView.setAdapter(groupAdapter);


        }



//        Iterator iterator = dataSnapshot.getChildren().iterator();
//        while(iterator.hasNext()){
//            String Date = (String) ((DataSnapshot)iterator.next()).getValue();
//            String ChatMsg = (String) ((DataSnapshot)iterator.next()).getValue();
//            String Time = (String) ((DataSnapshot)iterator.next()).getValue();
//            String ChatName = (String) ((DataSnapshot)iterator.next()).getValue();
//
//            displayTextMessage.append(ChatMsg+"\n");
//            timestamp.append(Date+"   "+Time+"\n");
//            reference = FirebaseDatabase.getInstance().getReference("MyUsers").child(ChatName); // making the reference of the user with whom we want to talk
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    Users user = dataSnapshot.getValue(Users.class);
//                    username.setText(user.getUsername());
//
//                    if(user.getImageURL().equals("default")){
//                        imageView.setImageResource(R.mipmap.ic_default);
//                    }
//                    else{
//                        Glide.with(GroupChatActivity.this).load(user.getImageURL()).circleCrop().into(imageView);
//                    }
//
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });
//        }


    }

    private void InitializeFields() {
        groupName = findViewById(R.id.group_name);
        // mToolbar = findViewById(R.id.toolbar);
        sendMessageButton = findViewById(R.id.btn_send);
        userMessageInput = findViewById(R.id.text_send);
//        mScrollView = findViewById(R.id.recycler_view_group_chat);
//        displayTextMessage = findViewById(R.id.group_chat_text_display);
//        username = findViewById(R.id.username);
//        imageView = findViewById(R.id.imageView);
//        timestamp = findViewById(R.id.timestamp);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true); //viewholders are shown from bottom to top
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void getUserInfo() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUserName = dataSnapshot.child("username").getValue().toString();
                profilepic = dataSnapshot.child("imageURL").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void SaveMessageToDatabase(){
        String message = userMessageInput.getText().toString();
        String messageKey = groupRef.push().getKey(); //generates a key for every message
        if(TextUtils.isEmpty(message)){
            Toast.makeText(this, "Can't send Empty Text", Toast.LENGTH_SHORT).show();
        }
        else {

            Calendar calForDate =Calendar.getInstance();
            SimpleDateFormat currentDateFormat = new SimpleDateFormat("yyyyMMdd'T'HHmmssSSSZ");
            currentDate = currentDateFormat.format(calForDate.getTime());

            Calendar calForTime = Calendar.getInstance();
            SimpleDateFormat currentTimeFormat = new SimpleDateFormat("hh:mm");
            currentTime = currentTimeFormat.format(calForTime.getTime());

            HashMap<String,Object> groupMessageKey = new HashMap<>();
            groupRef.updateChildren(groupMessageKey);

            groupMsgKeyRef = groupRef.child(messageKey);
            HashMap<String,Object> messages = new HashMap<>();
            messages.put("username",currentUserName);   // for now putting userID here
            messages.put("message",message);
            messages.put("timestamp",currentDate);
           // messages.put("time",currentTime);
            messages.put("profilepic",profilepic);
            groupMsgKeyRef.updateChildren(messages);
        }

    }
}