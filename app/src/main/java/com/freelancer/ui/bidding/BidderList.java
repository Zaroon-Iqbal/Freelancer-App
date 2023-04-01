package com.freelancer.ui.bidding;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;

import java.util.ArrayList;

//The List of bidders for a particular activity set up by a Contractor
//Created by Edward Kuoch
public class BidderList extends AppCompatActivity {
    private ArrayList<BiddingCustomerInfo> list = new ArrayList<>();
    private Button acceptBid;
    private ListView listView;
    private BiddingCustomerAdapter bcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bidder_list_activity);

        //Creates the listViews and adapters.
        acceptBid = findViewById(R.id.acceptBidder);
        listView = findViewById(R.id.biddersListView);
        //TODO Remove sample data
        list.add(new BiddingCustomerInfo(R.drawable.profile_icons, "Joe Mama", "$90"));
        list.add(new BiddingCustomerInfo(R.drawable.profile_icons, "John Smith", "$0"));
        bcAdapter = new BiddingCustomerAdapter(this, R.layout.bidder_list_row, list);
        listView.setAdapter(bcAdapter);

        //Shows the visible selection after tapping.
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!list.get(position).isSelected()){
                    view.setBackgroundResource(R.color.md_theme_dark_inversePrimary);
                    list.get(position).setSelected(true);
                    for(int i = 0; i < list.size(); i++){
                        if (i == position) {
                            continue;
                        }
                        else {
                            list.get(i).setSelected(false);
                        }
                    }
                }
                else {
                    view.setBackgroundResource(R.color.md_theme_dark_inverseSurface);
                    list.get(position).setSelected(false);
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
