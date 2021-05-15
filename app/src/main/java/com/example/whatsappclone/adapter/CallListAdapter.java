package com.example.whatsappclone.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.whatsappclone.R;
import com.example.whatsappclone.model.Call;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;



public class CallListAdapter extends RecyclerView.Adapter<CallListAdapter.ViewHolder> {

    private ArrayList<Call> callList;
    private Context context;

    public CallListAdapter(ArrayList<Call> callList, Context context) {
        this.callList = callList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(context).inflate(R.layout.layout_call_list,parent,false);
            return new ViewHolder(view);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Call calls=callList.get(position);

        holder.userNameTXT.setText(calls.getUserName());

        holder.dateTXT.setText(calls.getDate());

        if(calls.getCallType().equals("missed")){
            holder.arrowDownwardIV.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_downward));
            holder.arrowDownwardIV.getDrawable().setTint(context.getResources().getColor(R.color.arrowColor));
        }
        else if(calls.getCallType().equals("income")){
            holder.arrowDownwardIV.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_downward));
            holder.arrowDownwardIV.getDrawable().setTint(context.getResources().getColor(R.color.colorPrimary));
        }
        else{
            holder.arrowDownwardIV.setImageDrawable(context.getDrawable(R.drawable.ic_arrow_upward));
            holder.arrowDownwardIV.getDrawable().setTint(context.getResources().getColor(R.color.colorPrimary));
        }

        Glide.with(context).load(calls.getUrlProfile()).into(holder.iconProfileIV);
    }

    @Override
    public int getItemCount() {
        return callList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder {
        private TextView userNameTXT,dateTXT;
        private CircleImageView iconProfileIV;
        ImageView arrowDownwardIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userNameTXT=itemView.findViewById(R.id.txtUsername);
            dateTXT=itemView.findViewById(R.id.call_date);
            arrowDownwardIV=itemView.findViewById(R.id.arrow_downward);
            iconProfileIV=itemView.findViewById(R.id.icon_image_view);

        }
    }
}
