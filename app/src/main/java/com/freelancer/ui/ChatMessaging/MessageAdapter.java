package com.freelancer.ui.ChatMessaging;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
/*
This class is used to hold the messages that are sent between the users
 */
public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private Context context;
    private List<MessageModel> messageList;

    /**
     * constructor to initialize values
     * @param context
     */
    public MessageAdapter(Context context) {
        this.context = context;
        messageList = new ArrayList<>();//stored in a arraylist
    }

    /**
     * add a message to the list
     * @param messageModel
     */
    public void add(MessageModel messageModel){
        messageList.add(messageModel);//add to arraylist
        notifyDataSetChanged();//notify firebase of the change
    }

    /**
     * clear and notify database
     */
    public void clear(){
        messageList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    //set the correct xml as the inflator
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapter.MyViewHolder holder, int position) {
        MessageModel messageModel = messageList.get(position);
        holder.msg.setText(messageModel.getMessage());

        //used to set the colors of the sender and reciever boxes of the messages
        if (messageModel.getSenderId().equals(FirebaseAuth.getInstance().getUid()))
        {
            holder.main.setBackgroundColor(ContextCompat.getColor(context,R.color.dark_orange));
            holder.msg.setTextColor(ContextCompat.getColor(context, R.color.white));

        }else {
            holder.main.setBackgroundColor(ContextCompat.getColor(context,R.color.black));
            holder.msg.setTextColor(ContextCompat.getColor(context, R.color.white));
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView msg;
        private LinearLayout main;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            msg = itemView.findViewById(R.id.messages);//set the values to corresponding xml element
            main = itemView.findViewById(R.id.mainMessageLayout);
        }
    }
}
