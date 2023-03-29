package com.freelancer.ui.ChatMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.freelancer.R;
import com.freelancer.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

/**
 * THis class is used to handle and manage the chat activity between users on the application.
 */
public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;

    String receiverId;

    DatabaseReference databaseReferenceSender, databaseReferenceReceiver;

    String senderRoom, receiverRoom;

    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding is used to seemlessly connect the xml attributes
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        receiverId = getIntent().getStringExtra("id");//Used to get the recievers ID

        senderRoom = FirebaseAuth.getInstance().getUid() + receiverId;//used to distinguish the senders ID from the combination of both user ID's

        receiverRoom = receiverId + FirebaseAuth.getInstance().getUid();//used to distinguish the recievers ID from the combination of both user ID's
        messageAdapter = new MessageAdapter(this);//message adpater used to format the way the messages appear
        binding.chatRecycler.setAdapter(messageAdapter);
        binding.chatRecycler.setLayoutManager(new LinearLayoutManager(this));
        //different child databases are used for the sender and reciever
        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("chats").child(receiverRoom);

        //When a sender sends a message the value in the database will change
        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);//message model is used to get the requiered sender/reciever ids

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //when the send message button is clicked in the UI
        binding.sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = binding.messageEd.getText().toString();
                Toast.makeText(getApplicationContext(), "TESTING", Toast.LENGTH_SHORT).show();

                if(message.trim().length() > 0){//if there is a non empty message
                    Toast.makeText(getApplicationContext(), "Message Sent", Toast.LENGTH_SHORT).show();
                    send(message);
                }
            }
        });
    }

    /**
     * method used to send a message to the other user from the database
     * @param message
     */
    private void send(String message) {
        String messageId = UUID.randomUUID().toString();
        MessageModel messageModel = new MessageModel(messageId, FirebaseAuth.getInstance().getUid(), message);
        messageAdapter.add(messageModel);
        //the value are set for each child database accordingly so that they can be displayed on each users device
        databaseReferenceSender.child(messageId).setValue(messageModel);
        databaseReferenceReceiver.child(messageId).setValue(messageModel);
    }
}