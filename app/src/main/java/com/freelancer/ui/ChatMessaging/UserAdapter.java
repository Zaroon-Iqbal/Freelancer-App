package com.freelancer.ui.ChatMessaging;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to create a list of contacts for the chat page.
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {
    private Context context;
    private List<UserModel> userList;

    /**
     * constructor to iniitialze values
     * @param context
     */
    public UserAdapter(Context context) {
        this.context = context;
        userList = new ArrayList<>();//stored in an arraylist
    }

    /**
     * add a user to contacts
     * @param userModel
     */
    public void add(UserModel userModel){
        userList.add(userModel);
        notifyDataSetChanged();//notify the database that the data has changed
    }

    /**
     * clear users and notify data changed
     */
    public void clear(){
        userList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    /**
     * used to set the inflator to be of the user row xml
     */
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    /**
     * used to set the values of the elements of a user
     */
    public void onBindViewHolder(@NonNull UserAdapter.MyViewHolder holder, int position) {
        UserModel userModel = userList.get(position);
        holder.name.setText(userModel.getUserName());
        holder.email.setText(userModel.getUserEmail());

        // used to make sure to launch the chat activy once somone clicks on a contact
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ChatActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id", userModel.getUserId());
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView name, email;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.userName1);
            email = itemView.findViewById(R.id.userEmail1);
        }
    }
}
