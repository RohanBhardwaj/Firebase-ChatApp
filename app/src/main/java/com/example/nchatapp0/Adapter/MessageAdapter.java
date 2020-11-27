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
import com.example.nchatapp0.MessageActivity;
import com.example.nchatapp0.Model.Chat;
import com.example.nchatapp0.Model.Users;
import com.example.nchatapp0.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    private Context context;
    private List<Chat> mChat;
    private String imaURL;

    FirebaseUser fuser;


    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;

    public MessageAdapter(Context context, List<Chat> mChat , String imaURL) {
        this.context = context;
        this.mChat = mChat;
        this.imaURL = imaURL;
    }


    @NonNull
    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //viewtype value from getItemViewType fn
        if(viewType == MSG_TYPE_RIGHT){
        View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right,parent,false);
        return new MessageAdapter.ViewHolder(view);
        }
        else {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left,parent,false);
            return new MessageAdapter.ViewHolder(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.ViewHolder holder, int position) {
        Chat chat = mChat.get(position);
        holder.show_message.setText(chat.getMessage());
        holder.show_date.setText(chat.getTimestamp());
        //holder.show_time.setText(chat.getTime());
        if(imaURL.equals("default")){
            holder.profile_image.setImageResource(R.mipmap.ic_default);
        }
        else{
            Glide.with(context).load(imaURL).circleCrop().into(holder.profile_image);
        }


    }

    @Override
    public int getItemCount() {
        return mChat.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView show_message;
        public ImageView profile_image;
        public TextView show_date;
        public TextView show_time;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            show_message = itemView.findViewById(R.id.show_message);
            profile_image = itemView.findViewById(R.id.profile_image);
            show_date = itemView.findViewById(R.id.show_date);
           // show_time = itemView.findViewById(R.id.show_time);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fuser = FirebaseAuth.getInstance().getCurrentUser();
        //if its our message put it on right side of the screen
        //else put in on the left side
        if(mChat.get(position).getSender().equals(fuser.getUid())){
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }
    }
}
