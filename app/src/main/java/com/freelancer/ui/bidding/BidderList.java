package com.freelancer.ui.bidding;

import static android.content.ContentValues.TAG;

import android.content.Context;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//The List of bidders for a particular activity set up by a Contractor
//Created by Edward Kuoch
public class BidderList extends AppCompatActivity {
    private ArrayList<BiddingCustomerInfo> list;
    private Button acceptBid;
    private ListView listView;
    private BiddingCustomerAdapter bcAdapter;
    private String userID;
    private String bid_activity = "";
    private FirebaseFirestore db;
    private CollectionReference colRef;
    private boolean read = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidder_list_activity);

        //Creates the listViews and adapters.
        acceptBid = findViewById(R.id.acceptBidder);
        listView = findViewById(R.id.biddersListView);
        list = new ArrayList<>();

        //Connects to the database
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            bid_activity = extras.getString("bID_key");
        }
        colRef = db.collection("biddings").document("contractorBids")
                .collection(userID).document(bid_activity).collection("bidderList");

        //Converts everything into a view list
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
                        list.add(doc.toObject(BiddingCustomerInfo.class));
                    }
                    bcAdapter.notifyDataSetChanged();
                    Log.d(TAG, "All bids " + list);
                    read = false;
                }
            }
        });

        bcAdapter = new BiddingCustomerAdapter(this, R.layout.bidder_list_row, list);
        listView.setAdapter(bcAdapter);
        //Shows the visible selection after tapping.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for(int i = 0; i < listView.getCount(); i++) {
                    if(!list.get(position).isSelected() && i == position){
                        view.setBackgroundResource(R.color.md_theme_dark_inversePrimary);
                        list.get(position).setSelected(true);
                    }
                    else {
                        listView.getChildAt(i).setBackgroundResource(R.color.md_theme_dark_inverseSurface);
                        list.get(i).setSelected(false);
                    }
                }
            }
        });
        deleteBidder();
        acceptBidder();
    }

    //Deletes a bidder
    private void deleteBidder(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                colRef.document(list.get(position).getBidID()).delete()
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
                bcAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    //After selecting a bidder, accepts their offer.
    private void acceptBidder(){
        //TODO accepts the bid and closes the activity
    }
}
