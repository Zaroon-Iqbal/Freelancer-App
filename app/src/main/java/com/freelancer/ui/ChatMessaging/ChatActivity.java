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

public class ChatActivity extends AppCompatActivity {
    ActivityChatBinding binding;

    String receiverId;

    DatabaseReference databaseReferenceSender, databaseReferenceReceiver;

    String senderRoom, receiverRoom;

    MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.activity_chat);
        binding = ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        receiverId = getIntent().getStringExtra("id");

        senderRoom = FirebaseAuth.getInstance().getUid() + receiverId;
        receiverRoom = receiverId + FirebaseAuth.getInstance().getUid();
        RecyclerView chatR = findViewById(R.id.chat_recycler);
        messageAdapter = new MessageAdapter(this);
       // chatR.setAdapter(messageAdapter);
        binding.chatRecycler.setAdapter(messageAdapter);
        binding.chatRecycler.setLayoutManager(new LinearLayoutManager(this));
       // chatR.setLayoutManager(new LinearLayoutManager(this));
        databaseReferenceSender = FirebaseDatabase.getInstance().getReference("chats").child(senderRoom);
        databaseReferenceReceiver = FirebaseDatabase.getInstance().getReference("chats").child(receiverRoom);

        databaseReferenceSender.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageAdapter.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                    messageAdapter.add(messageModel);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.sendMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // EditText mes = findViewById(R.id.messageEd);
                String message = binding.messageEd.getText().toString();
                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();

                if(message.trim().length() > 0){
                    Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();

                    send(message);
                }
            }
        });
    }

    private void send(String message) {
        String messageId = UUID.randomUUID().toString();
        MessageModel messageModel = new MessageModel(messageId, FirebaseAuth.getInstance().getUid(), message);
        messageAdapter.add(messageModel);
        databaseReferenceSender.child(messageId).setValue(messageModel);
        databaseReferenceReceiver.child(messageId).setValue(messageModel);
    }
}