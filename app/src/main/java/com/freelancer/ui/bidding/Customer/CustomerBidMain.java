package com.freelancer.ui.bidding.Customer;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.freelancer.ui.bidding.BidderList;
import com.freelancer.ui.bidding.BiddingContractorMain;
import com.freelancer.ui.bidding.ContractorBidAdapter;
import com.freelancer.ui.bidding.ContractorBidInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//The main activity for the customer side of bidding
//Created by Edward Kuoch
public class CustomerBidMain extends AppCompatActivity {
    private ArrayList<ContractorBidInfo> list;
    private Button pending, winning;
    private ListView listView;
    private ContractorBidAdapter bidAdapter;
    private String userID;
    private FirebaseFirestore db;
    private CollectionReference colRef;
    private boolean read = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidding_consumer_main);

        pending = findViewById(R.id.browseBid);
        winning = findViewById(R.id.winningBid);

        listView = findViewById(R.id.biddingListView);
        list = new ArrayList<>();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        colRef = db.collection("biddingConsumer").document(userID).collection("Pending");

        bidAdapter = new ContractorBidAdapter(this, R.layout.bidding_list_row, list);
        listView.setAdapter(bidAdapter);

        pending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerBidMain.this, CustomerBidActivities.class);
                i.putExtra("activity_key", "Browse Bids");
                startActivity(i);
            }
        });

        winning.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CustomerBidMain.this, CustomerBidActivities.class);
                i.putExtra("activity_key", "Winning Bids");
                startActivity(i);
            }
        });

        deletePending();
    }

    //Connects the Database
    @Override
    protected void onStart() {
        super.onStart();
        colRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if(read) {
                    for (QueryDocumentSnapshot doc : value) {
                        list.add(doc.toObject(ContractorBidInfo.class));
                    }
                    bidAdapter.notifyDataSetChanged();
                    Log.d(TAG, "All bids " + list);
                    read = false;
                }
            }
        });
    }

    //Deletes a pending bid
    private void deletePending(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String bidID = list.get(position).getBID();
                DocumentReference auction = db.collection("biddingActivities").document(bidID).collection("bidderList").document(userID);
                colRef.document(bidID).delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
                auction.delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d(TAG, "DocumentSnapshot successfully deleted!");
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.w(TAG, "Error deleting document", e);
                            }
                        });
                Context context = getApplicationContext();
                Toast.makeText(context, "Bidding Removed", Toast.LENGTH_LONG).show();
                list.remove(position);
                bidAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }
}
