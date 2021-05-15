package com.example.whatsappclone.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsappclone.R;
import com.example.whatsappclone.activity.ChattingActivity;
import com.example.whatsappclone.model.User;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ViewHolder> {

    private ArrayList<User> userList;
    private Context context;

    public ContactListAdapter(ArrayList<User> users, Context context) {
        this.userList = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_contact_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User users=userList.get(position);
        holder.userNameTV.setText(users.getUserName());
        holder.descriptionTV.setText(users.getBio());
        Glide.with(context).load(users.getImageProfile()).into(holder.circleImageView);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ChattingActivity.class)
                        .putExtra("userID", users.getUserID())
                        .putExtra("userName", users.getUserName())
                        .putExtra("imageProfile", users.getImageProfile()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageView;
        private TextView userNameTV,descriptionTV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView=itemView.findViewById(R.id.image_profile);
            userNameTV=itemView.findViewById(R.id.tv_username);
            descriptionTV=itemView.findViewById(R.id.tv_desc);
        }
    }
}
