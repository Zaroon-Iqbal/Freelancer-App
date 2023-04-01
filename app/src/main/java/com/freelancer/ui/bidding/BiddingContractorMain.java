package com.freelancer.ui.bidding;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;

import java.util.ArrayList;

//Activity for the Contractor to view and start their bids.
public class BiddingContractorMain extends AppCompatActivity {

    private ArrayList<ContractorBidInfo> list = new ArrayList<>();
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText actName, startPrice, desc;
    private Button mainAdd, popAdd, cancel;
    private ListView listView;
    private ContractorBidAdapter bidAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidding_contractor_main);
        mainAdd = findViewById(R.id.addBid);
        listView = findViewById(R.id.bidListView);
        //TODO sample data, delete after linking to database
        list.add(new ContractorBidInfo(R.drawable.table, "Selling Table", "$100", "Previous customer cancelled their order, selling off this table"));

        bidAdapter = new ContractorBidAdapter(this, R.layout.bidding_list_row, list);
        listView.setAdapter(bidAdapter);

        mainAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewBidDialog();
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
                list.add(new ContractorBidInfo(R.drawable.hammer, actName.getText().toString(), startPrice.getText().toString(), desc.getText().toString()));
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
