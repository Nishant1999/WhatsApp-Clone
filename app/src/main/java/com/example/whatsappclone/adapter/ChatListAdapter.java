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
import com.example.whatsappclone.model.Chat;


import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ChatListAdapter extends RecyclerView.Adapter<ChatListAdapter.ViewHolder> {

    private ArrayList<Chat> chatList;
    private Context context;

    public ChatListAdapter(ArrayList<Chat> chatList, Context context) {
        this.chatList = chatList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.layout_chat_list,parent,false);
            return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Chat chats=chatList.get(position);

        holder.userNameTXT.setText(chats.getUserName());

        holder.dateTXT.setText(chats.getDate());

        holder.descriptionTXT.setText(chats.getDescription());

        if(chats.getUrlProfile().equals("")){
            holder.iconProfileIV.setImageResource(R.drawable.icon_male_profile);
        }
        else {
            Glide.with(context).load(chats.getUrlProfile()).into(holder.iconProfileIV);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, ChattingActivity.class)
                        .putExtra("userID", chats.getUserId())
                        .putExtra("userName", chats.getUserName())
                        .putExtra("imageProfile", chats.getUrlProfile()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTXT,descriptionTXT,dateTXT;
        private CircleImageView iconProfileIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameTXT=itemView.findViewById(R.id.txtUsername);
            descriptionTXT=itemView.findViewById(R.id.txtDescription);
            dateTXT=itemView.findViewById(R.id.txtDate);
            iconProfileIV=itemView.findViewById(R.id.icon_image_view);

        }
    }
}
