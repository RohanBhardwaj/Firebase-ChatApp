package com.example.nchatapp0.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nchatapp0.GroupChatActivity;
import com.example.nchatapp0.MessageActivity;
import com.example.nchatapp0.Model.Chat;
import com.example.nchatapp0.Model.Gchat;
import com.example.nchatapp0.Model.Groups;
import com.example.nchatapp0.Model.Users;
import com.example.nchatapp0.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.List;

public class GroupAdapter extends RecyclerView.Adapter<GroupAdapter.ViewHolder> {
    private Context context;
    private List<Gchat> mGChat;
    private String imaURL ;
    private String username;
    private DatabaseReference reference;
    public GroupAdapter(Context context, List<Gchat> mGChat) {
        this.context = context;
        this.mGChat = mGChat;

    }


    @NonNull
    @Override
    public GroupAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.group_item,parent,false);
        return new GroupAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GroupAdapter.ViewHolder holder, int position) {
        Gchat chat = mGChat.get(position);
        holder.username.setText(chat.getUsername());
        holder.show_message.setText(chat.getMessage());
        holder.show_date.setText(chat.getTimeStamp());
       // holder.show_time.setText(chat.getTime());
        imaURL = chat.getImage();
        if(imaURL.equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_default);
        }
        else{
            Glide.with(context).load(imaURL).circleCrop().into(holder.profile_image);
        }
    }

    @Override
    public int getItemCount() {
        return mGChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public TextView show_message;
        public ImageView profile_image;
        public TextView show_date;
        public TextView show_time;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username =itemView.findViewById(R.id.username);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            show_date = itemView.findViewById(R.id.show_date);
            //show_time = itemView.findViewById(R.id.show_time);
        }
    }

}
