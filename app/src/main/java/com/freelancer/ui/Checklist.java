package com.freelancer.ui;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.freelancer.R;
import com.freelancer.ui.bidding.ContractorBidInfo;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//This creates a checklist that the user can use to keep track of anything.
//Created by Edward Kuoch
public class Checklist extends AppCompatActivity {

    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView listView;
    private Button button;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference colRef;
    private String userID = FirebaseAuth.getInstance().getCurrentUser().getUid();
    private boolean read = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checklist);

        //Connects the button, listviews, and adapters.
        listView = findViewById(R.id.checklistView);
        button = findViewById(R.id.addTaskButton);

        //Connects the Database
        colRef = db.collection("Checklist").document(userID).collection("list");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addItem(v);
            }
        });

        //Adds the items to an arraylist
        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);
        colRef.addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "Listen failed.", e);
                    return;
                }
                if (read) {
                    for (QueryDocumentSnapshot doc : value) {
                        Log.d(TAG, doc.getString("item"));
                        items.add(doc.getString("item"));
                    }
                    itemsAdapter.notifyDataSetChanged();
                    Log.d(TAG, "checklist " + items);
                    read = false;
                }
            }
        });
        setUpListViewListener();
    }

    //Deletes the item.
    private void setUpListViewListener(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = getApplicationContext();
                Toast.makeText(context, "Item Removed", Toast.LENGTH_LONG).show();
                colRef.document(items.get(position)).delete();
                items.remove(position);
                itemsAdapter.notifyDataSetChanged();
                return true;
            }
        });
    }

    //Allows the user to add an item after clicking the button
    private void addItem(View v){
        EditText input = findViewById(R.id.editNewTask);
        Map<String, Object> newText = new HashMap<>();
        String text = input.getText().toString();
        if(!(text.equals(""))){
            newText.put("item", text);
            colRef.document(text).set(newText);
            itemsAdapter.add(text);
            input.setText("");
            itemsAdapter.notifyDataSetChanged();
        }
        else{
            Toast.makeText(getApplicationContext(), "Please enter text", Toast.LENGTH_LONG).show();
        }
    }
}
