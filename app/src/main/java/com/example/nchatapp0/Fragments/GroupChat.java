package com.example.nchatapp0.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.nchatapp0.Adapter.GroupAdapter;
import com.example.nchatapp0.Adapter.UserAdapter;
import com.example.nchatapp0.GroupChatActivity;
import com.example.nchatapp0.Model.Groups;
import com.example.nchatapp0.Model.Users;
import com.example.nchatapp0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class GroupChat extends Fragment {

    private View groupFragmentView;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> listOfGroups = new ArrayList<>();
    private DatabaseReference groupRef;
    EditText search_users;



    public GroupChat() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        groupFragmentView = inflater.inflate(R.layout.fragment_group_chat, container, false);

        groupRef = FirebaseDatabase.getInstance().getReference().child("Forums");

        InitializeField();

        RetrieveAndDisplayGroup();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String currentGroupName = parent.getItemAtPosition(position).toString();
                Intent intent = new Intent(getContext(), GroupChatActivity.class);
                intent.putExtra("groupName",currentGroupName);
                startActivity(intent);
            }
        });


        return groupFragmentView;
    }



    private void InitializeField() {
        listView = groupFragmentView.findViewById(R.id.list_view);
        arrayAdapter = new ArrayAdapter<String>(getContext(),android.R.layout.simple_list_item_1,listOfGroups);//change simple list to design
        listView.setAdapter(arrayAdapter);
    }

    private void RetrieveAndDisplayGroup() {
        groupRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Set<String> set = new HashSet<>();
                Iterator iterator = dataSnapshot.getChildren().iterator();
                while (iterator.hasNext()){
                    set.add(((DataSnapshot)iterator.next()).getKey());
                }

                listOfGroups.clear();
                listOfGroups.addAll(set);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
//public class GroupChat extends Fragment{
//    private RecyclerView recyclerView;
//    private GroupAdapter groupAdapter;
//    private List<Groups> mGroups;
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view =inflater.inflate(R.layout.fragment_group,container,false);
//        recyclerView = view.findViewById(R.id.recyclerView);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        mGroups = new ArrayList<>();
//        ReadGroups();
//
//
//
//        return view;
//    }
//
//
//    private void ReadGroups(){
//        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Groups").child(firebaseUser.getUid());
//        reference.addValueEventListener(new ValueEventListener() {  //To read data at a path and listen for changes
//            //onDataChange() method to read a static snapshot of the contents at a given path, as they existed at the time of the event
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//
//                    mGroups.clear();
//                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                        Groups group = snapshot.getValue(Groups.class); //getvalue arranges the data in the User class
//
//                        //to prevent the user from seeing himself as a User in the list
//
//                            mGroups.add(group);
//                     //ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff
//                        groupAdapter = new GroupAdapter(getContext(), mGroups);
//                        recyclerView.setAdapter(groupAdapter);
//
//                    }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }
//}