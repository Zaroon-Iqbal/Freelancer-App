package com.freelancer.ui.bidding.Customer;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.freelancer.R;
import com.freelancer.ui.bidding.BiddingCustomerInfo;
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

//The second activity that determines if the user is viewing their
//winnings or browsing other bids. Created by Edward Kuoch
public class CustomerBidActivities extends AppCompatActivity {
    private ArrayList<ContractorBidInfo> list;
    private TextView title, actName, desc, startPrice;
    private ListView listView;
    private EditText bidPrice;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private ImageView imageView;
    private Button popBid, popCancel;
    private ContractorBidAdapter bidAdapter;
    private String userID;
    private String bid_activity;
    private FirebaseFirestore db;
    private CollectionReference colRef;
    private boolean read = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidding_consumer_activities);

        //Connects Buttons
        title = findViewById(R.id.bidTextView);
        list = new ArrayList<>();
        listView = findViewById(R.id.bidListView);
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        //Connects database
        db = FirebaseFirestore.getInstance();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            bid_activity = extras.getString("activity_key");
        }

        bidAdapter = new ContractorBidAdapter(this, R.layout.bidding_list_row, list);
        listView.setAdapter(bidAdapter);

        if(bid_activity.equals("Browse Bids")) {
            title.setText(bid_activity);
            colRef = db.collection("biddingActivities");
            //connectDB(colRef);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    createNewBidDialog(list.get(position));
                }
            });

        }
        else {
            title.setText(bid_activity);
            colRef = db.collection("biddingConsumer").document(userID).collection("Winning");
            //connectDB(colRef);
        }
    }

    //view the database
    @Override
    protected void onStart() {
        super.onStart();
        connectDB(colRef);
    }

    //Reads from the Database
    public void connectDB(CollectionReference cr){
        cr.addSnapshotListener(new EventListener<QuerySnapshot>() {
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

    //Creates a pop up to bid on.
    public void createNewBidDialog(ContractorBidInfo contractor){
        dialogBuilder = new AlertDialog.Builder(this);
        final View createBidView = getLayoutInflater().inflate(R.layout.bidding_view_popup, null);

        imageView = (ImageView) createBidView.findViewById(R.id.bidPopImageView);
        actName = (TextView) createBidView.findViewById(R.id.bidPopActNameView);
        startPrice = (TextView) createBidView.findViewById(R.id.bidPopStartPriceView);
        bidPrice = (EditText) createBidView.findViewById(R.id.editBidPrice);
        desc = (TextView) createBidView.findViewById(R.id.bidPopDescView);
        popBid = (Button) createBidView.findViewById(R.id.bidPopAddView);
        popCancel = (Button) createBidView.findViewById(R.id.bidPopCancelView);

        Glide.with(createBidView).load(contractor.getURL()).into(imageView);
        startPrice.setText(contractor.getStartingPrice());
        actName.setText(contractor.getActivityName());
        desc.setText(contractor.getDescription());

        dialogBuilder.setView(createBidView);
        dialog = dialogBuilder.create();
        dialog.show();

        //Adds the user input to create a new list item.
        popBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contractorID = contractor.getContractorID();
                String auctionID = contractor.getBID();
                DocumentReference newBidder = colRef.document(auctionID).collection("bidderList").document(userID);
                DocumentReference newPending = db.collection("biddingConsumer").document(userID).collection("Pending").document(auctionID);
                BiddingCustomerInfo newCustomer = new BiddingCustomerInfo(contractorID, auctionID, userID,
                        "https://firebasestorage.googleapis.com/v0/b/freelancer-775c2.appspot.com/o/biddingsImages%2FProfile_avatar_placeholder_large%5B1%5D.png?alt=media&token=77d61675-21cc-4406-a081-01014369cca4",
                        FirebaseAuth.getInstance().getCurrentUser().getEmail(), bidPrice.getText().toString());
                newBidder.set(newCustomer);
                newPending.set(contractor);
                db.collection("biddingConsumer").document(userID).update("bidID", userID);
                bidAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        //Closes pop up.
        popCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
