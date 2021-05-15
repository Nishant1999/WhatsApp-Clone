package com.example.whatsappclone.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappclone.R;
import com.example.whatsappclone.adapter.ChatListAdapter;
import com.example.whatsappclone.model.Chat;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    private ArrayList<Chat> chats;
    private RecyclerView chatRV;
    private ChatListAdapter chatListAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_chat, container, false);
        chats=new ArrayList<>();
        //getChatList();
        chatRV=view.findViewById(R.id.fragment_chat_view);
        chatRV.setLayoutManager(new LinearLayoutManager(getContext()));
        chatRV.setHasFixedSize(true);

        chatListAdapter=new ChatListAdapter(chats,getContext());
        chatRV.setAdapter(chatListAdapter);
        return view;
    }

    private void getChatList() {
        chats.add(new Chat("1","Nishant","Hello Friends","15/04/21","https://image.shutterstock.com/image-photo/mountains-under-mist-morning-amazing-260nw-1725825019.jpg"));
        chats.add(new Chat("2","Abhishek","Hello Friends","15/04/21","https://image.shutterstock.com/image-photo/mountains-under-mist-morning-amazing-260nw-1725825019.jpg"));
        chats.add(new Chat("3","Hemant","Hello Friends","15/04/21","https://image.shutterstock.com/image-photo/mountains-under-mist-morning-amazing-260nw-1725825019.jpg"));
        chats.add(new Chat("4","Rahul","Hello Friends","15/04/21","https://image.shutterstock.com/image-photo/mountains-under-mist-morning-amazing-260nw-1725825019.jpg"));

    }
}