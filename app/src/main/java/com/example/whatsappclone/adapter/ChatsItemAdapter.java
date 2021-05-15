package com.example.whatsappclone.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.whatsappclone.R;
import com.example.whatsappclone.model.ChatsItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class ChatsItemAdapter extends RecyclerView.Adapter<ChatsItemAdapter.ViewHolder> {

    private ArrayList<ChatsItem> chatsItems;
    private Context context;
    public static final int MSG_TYPE_LEFT=0;
    public static final int MSG_TYPE_RIGHT=1;
    private FirebaseUser firebaseUser;

    public ChatsItemAdapter(ArrayList<ChatsItem> chatsItems, Context context) {
        this.chatsItems = chatsItems;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==MSG_TYPE_LEFT) {
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new ViewHolder(view);
        }
        else if(viewType==MSG_TYPE_RIGHT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new ViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.bind(chatsItems.get(position));
    }

    @Override
    public int getItemCount() {
        return chatsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textMessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage=itemView.findViewById(R.id.tv_text_message);
        }
        void bind(ChatsItem chatsItem){
                textMessage.setText(chatsItem.getTextMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        if(chatsItems.get(position).getSender().equals(firebaseUser.getUid()))
        {
            return MSG_TYPE_RIGHT;
        }
        else{
            return MSG_TYPE_LEFT;
        }

    }
}
