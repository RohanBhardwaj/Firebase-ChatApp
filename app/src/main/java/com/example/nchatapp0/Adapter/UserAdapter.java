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
import com.example.nchatapp0.Model.Users;
import com.example.nchatapp0.Model.Users;
import com.example.nchatapp0.R;

import java.util.List;

//to recieve all the user information from database
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context context;
    private List<Users> mUsers;

    public UserAdapter(Context context, List<Users> mUsers) {
        this.context = context;
        this.mUsers = mUsers;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_item,parent,false);
        return new UserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    final Users user = mUsers.get(position);
    holder.username.setText(user.getUsername());
    if(user.getImageURL().equals("default")){
        holder.imageView.setImageResource(R.mipmap.ic_default);
    }
    else{
        Glide.with(context).load(user.getImageURL()).circleCrop().into(holder.imageView);
    }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent i =new Intent(context, MessageActivity.class);
            i.putExtra("userid",user.getId());//passing the clicked id
            context.startActivity(i);
        }
    });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView username;
        public ImageView imageView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            username = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView3);
        }
    }


}
