package com.example.whatsappclone.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.whatsappclone.R;
import com.example.whatsappclone.adapter.CallListAdapter;
import com.example.whatsappclone.model.Call;

import java.util.ArrayList;

public class CallFragment extends Fragment {

    private ArrayList<Call> calls;
    private RecyclerView callRV;
    private CallListAdapter callListAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_call, container, false);

        calls=new ArrayList<>();
        //getCallList();
        callRV=view.findViewById(R.id.fragment_call_view);
        callRV.setLayoutManager(new LinearLayoutManager(getContext()));
        callRV.setHasFixedSize(true);
        callListAdapter=new CallListAdapter(calls,getContext());
        callRV.setAdapter(callListAdapter);
        return view;
    }

    private void getCallList() {
        calls.add(new Call("001",
                "Nishant",
                "15/04/2021 , 9:04 pm",
                "https://image.shutterstock.com/image-photo/mountains-under-mist-morning-amazing-260nw-1725825019.jpg",
                "income"));

        calls.add(new Call("002",
                "Abhishek",
                "15/04/2021 , 9:24 pm",
                "https://image.shutterstock.com/image-photo/mountains-under-mist-morning-amazing-260nw-1725825019.jpg",
                "missed"));
    }
}