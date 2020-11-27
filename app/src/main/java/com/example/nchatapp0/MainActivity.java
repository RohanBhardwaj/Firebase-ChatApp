package com.example.nchatapp0;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.nchatapp0.Fragments.ChatFragment;
import com.example.nchatapp0.Fragments.GroupChat;
import com.example.nchatapp0.Fragments.ProfileFragment;
import com.example.nchatapp0.Fragments.UserFragment;
import com.example.nchatapp0.Model.Chat;
import com.example.nchatapp0.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    FirebaseUser firebaseUser;
    DatabaseReference myRef;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        myRef = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users users = dataSnapshot.getValue(Users.class);
              //  Toast.makeText(MainActivity.this,"User Login:"+users.getUsername(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Tab Layout
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.view_pager);

       ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

//        myRef = FirebaseDatabase.getInstance().getReference("Chats");
//        myRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
//                int unread =0;
//                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
//                    Chat chat = snapshot.getValue(Chat.class);
//                    if(chat.getReceiver().equals())
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//
        viewPagerAdapter.addFragment(new ChatFragment(),"Chats");
        viewPagerAdapter.addFragment(new UserFragment(),"Users");
       // viewPagerAdapter.addFragment(new ProfileFragment(),"Profile");
        viewPagerAdapter.addFragment(new GroupChat(),"Forums");
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


    }

    //LogOut
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    //When the user selects an item from the options menu
    // the system calls your activity's onOptionsItemSelected() method.
    // This method passes the MenuItem selected.
    // We can identify the item by calling getItemId(), which returns the unique ID for the menu item
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                return true;

            // case R.id.search_user
            case R.id.setting:
                startActivity(new Intent(MainActivity.this,SettingActivity.class));
                return true;

            case R.id.create_group:
                RequestNewGroup();
                return true;

        }
        return false;
    }

    private void RequestNewGroup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this,R.style.AlertDialog);
        builder.setTitle("Enter Forum Name: ");
        final EditText groupNameField = new EditText(MainActivity.this);
        groupNameField.setHint("type here");
        builder.setView(groupNameField);
        builder.setPositiveButton("Create", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String groupName = groupNameField.getText().toString();
                if(TextUtils.isEmpty(groupName)){
                    Toast.makeText(MainActivity.this, "Forum Name can't be empty", Toast.LENGTH_SHORT).show();
                }
                else{

                    CreateNewGroup(groupName);

                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    private void CreateNewGroup(final String groupName){
        String userid = firebaseUser.getUid();
        FirebaseDatabase.getInstance().getReference("Forums").child(groupName).setValue("")
//        myRef = FirebaseDatabase.getInstance().getReference("Groups");
//        HashMap<String,Object> hashMap = new HashMap<>();
//        hashMap.put("GroupName",groupName);
//                myRef.setValue(hashMap)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(MainActivity.this, groupName+" is created", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    //ViewPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter{
        private ArrayList<Fragment> fragments;
        private ArrayList<String> titles;

        ViewPagerAdapter(FragmentManager fm){
            super(fm);
            this.fragments = new ArrayList<>();
            this.titles = new ArrayList<>();
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        public void addFragment(Fragment fragment,String title){
            fragments.add(fragment);
            titles.add(title);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }


}