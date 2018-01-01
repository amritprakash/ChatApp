package com.amrit.chatapp;

import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *
 * Created by hp on 13-02-2016.
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {

    public static String id = "222";
    private LayoutInflater inflater;
    private List<Message> chat = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ChatAdapter(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    /*public void setChatList(List<String> chat) {
        this.chat = chat;
        notifyDataSetChanged();
    }*/

    public void addChatMessage(Message msg){
        chat.add(msg);
        //Collections.reverse(chat);
        notifyItemInserted(chat.size()-1);
    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.chat_item, parent, false);
        ChatViewHolder chatViewHolder = new ChatViewHolder(view, onItemClickListener);
        return chatViewHolder;
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {

        Message msg = chat.get(position);
        try{
            holder.msgTxt.setText(msg.getText());
            //SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy hh:mm");
            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
            holder.msgTime.setText(sdf.format(msg.getDate()));
            if(msg.getId().equals(ChatAdapter.id)){
                ((LinearLayout.LayoutParams) holder.msgLayout.getLayoutParams()).gravity = Gravity.RIGHT;
            }else{
                ((LinearLayout.LayoutParams) holder.msgLayout.getLayoutParams()).gravity = Gravity.LEFT;
            }

        }catch(Exception e){
        }

    }

    @Override
    public int getItemCount() {

        return chat.size();
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView msgTxt;
        private TextView msgTime;
        private LinearLayout msgLayout;
        public ChatViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            msgTxt = itemView.findViewById(R.id.msgTxt);
            msgTime = itemView.findViewById(R.id.msgTime);
            msgLayout = itemView.findViewById(R.id.msg_layout);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //this.onItemClickListener.onItemClick(dealUrl);

        }
    }

    public interface OnItemClickListener
    {
        void onItemClick(String s);
    }

}