package com.freelancer.ui.bidding;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

//Activity for the Contractor to view and start their bids. Created by Edward Kuoch
public class BiddingContractorMain extends AppCompatActivity {
    private boolean read = true;
    private ArrayList<ContractorBidInfo> list;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText actName, startPrice, desc;
    private Button mainAdd, popAdd, cancel;
    private ListView listView;
    private ContractorBidAdapter bidAdapter;
    private String userID;
    private FirebaseFirestore db;
    private CollectionReference colRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidding_contractor_main);
        //Connects the buttons, the listView, and the adapter
        mainAdd = findViewById(R.id.addBid);
        listView = findViewById(R.id.bidListView);
        list = new ArrayList<>();
        userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        db = FirebaseFirestore.getInstance();
        colRef = db.collection("biddings").document("contractorBids").collection(userID);
        //TODO sample data, delete after linking to database
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
                        //ContractorBidInfo contractor = doc.toObject(ContractorBidInfo.class);
                        list.add(doc.toObject(ContractorBidInfo.class));
                    }
                    bidAdapter.notifyDataSetChanged();
                    Log.d(TAG, "All bids " + list);
                    read = false;
                }
            }
        });
        bidAdapter = new ContractorBidAdapter(this, R.layout.bidding_list_row, list);
        listView.setAdapter(bidAdapter);

        //Creates a pop up menu when clicking the creating bid button.
        mainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewBidDialog();
            }
        });
        deleteBid();
        viewBidders();
    }

    //Deletes a bidding activity.
    private void deleteBid(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Bidding Removed", Toast.LENGTH_LONG).show();
                list.remove(position);
                bidAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    //Clicking on the listView item takes the user to
    //a different activity where they can see the bidders.
    private void viewBidders(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(BiddingContractorMain.this, BidderList.class);
                i.putExtra("bID_key", list.get(position).getBID());
                startActivity(new Intent(BiddingContractorMain.this, BidderList.class));
            }
        });
    }

    //Creates a dialog box to create a bid.
    public void createNewBidDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View createBidView = getLayoutInflater().inflate(R.layout.bidding_create_popup, null);

        //TODO add image as an option
        actName = (EditText) createBidView.findViewById(R.id.bidPopActName);
        startPrice = (EditText) createBidView.findViewById(R.id.bidPopStartPrice);
        desc = (EditText) createBidView.findViewById(R.id.bidPopDesc);
        popAdd = (Button) createBidView.findViewById(R.id.bidPopAdd);
        cancel = (Button) createBidView.findViewById(R.id.bidPopCancel);

        dialogBuilder.setView(createBidView);
        dialog = dialogBuilder.create();
        dialog.show();

        //Adds the user input to create a new list item.
        popAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DocumentReference newDocRef = colRef.document();
                ContractorBidInfo newContractor = new ContractorBidInfo(newDocRef.getId(), "https://firebasestorage.googleapis.com/v0/b/freelancer-775c2.appspot.com/o/biddingsImages%2Fhammer.png?alt=media&token=4f51a447-4ef1-43f8-a173-449b62053ff5",
                        actName.getText().toString(), startPrice.getText().toString(), desc.getText().toString());
                newDocRef.set(newContractor);
                list.add(newContractor);
                bidAdapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        });

        //Closes pop up.
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}
