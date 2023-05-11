package com.freelancer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;

import com.freelancer.ui.login.HomePage;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.core.View;

import java.util.ArrayList;
import java.util.List;

public class Search extends Activity {
    private static final String TAG = "MyActivity";
    View v;
    SearchView searchView;
    ListView listView;
    ArrayList<String> arr;
    ArrayAdapter<String> adapter;
    Button b2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        b2 = findViewById(R.id.favoritesBtn);
        b2.setOnClickListener(new android.view.View.OnClickListener() {

            public void onClick(android.view.View v) {
                Intent i = new Intent(Search.this, HomePage.class);
                startActivity(i);
            }
        });

        searchView = findViewById(R.id.searchView);
        listView = findViewById(R.id.listView);
        //listView.setVisibility(android.view.View.GONE);
        arr = new ArrayList<>();
        readDocument(v);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            // Called when the query text is changed by the user
            public boolean onQueryTextChange(String s) {
                listView.setVisibility(android.view.View.VISIBLE);
                adapter.getFilter().filter(s);
                return false;
            }
        });
    }

    public void readDocument(View view) {
        FirebaseFirestore.getInstance()
                .collection("jobListings")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString("title") + "\n" + snapshot.getString("description") + "\t\t$" + snapshot.getDouble("basePrice") + "\t\t" + snapshot.getDouble("radius") + "mi";
                            Log.d(TAG, "onSuccess: " + temp);
                            arr.add(temp);
                            Log.d(TAG, "onSuccess: " + arr);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }

    public void onClickHigh(android.view.View view) {
        listView.setVisibility(android.view.View.VISIBLE);
        arr = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);
        FirebaseFirestore.getInstance()
                .collection("jobListings")
                .whereGreaterThanOrEqualTo("basePrice", 100)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString("title") + "\n" + snapshot.getString("description") + "\t\t$" + snapshot.getDouble("basePrice") + "\t\t" + snapshot.getDouble("radius") + "mi";
                            Log.d(TAG, "onSuccess: " + temp);
                            arr.add(temp);
                            Log.d(TAG, "onSuccess: " + arr);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }


    public void onClickMedium(android.view.View view) {
        listView.setVisibility(android.view.View.VISIBLE);
        arr = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);
        FirebaseFirestore.getInstance()
                .collection("jobListings")
                .whereLessThan("basePrice", 100)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString("title") + "\n" + snapshot.getString("description") + "\t\t$" + snapshot.getDouble("basePrice") + "\t\t" + snapshot.getDouble("radius") + "mi";
                            Log.d(TAG, "onSuccess: " + temp);
                            arr.add(temp);
                            Log.d(TAG, "onSuccess: " + arr);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }

    public void onClickLow(android.view.View view) {
        listView.setVisibility(android.view.View.VISIBLE);
        arr = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);
        FirebaseFirestore.getInstance()
                .collection("jobListings")
                .whereLessThan("basePrice", 50)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString("title") + "\n" + snapshot.getString("description") + "\t\t$" + snapshot.getDouble("basePrice") + "\t\t" + snapshot.getDouble("radius") + "mi";
                            Log.d(TAG, "onSuccess: " + temp);
                            arr.add(temp);
                            Log.d(TAG, "onSuccess: " + arr);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
    }

    public void onClickFar(android.view.View view) {
        listView.setVisibility(android.view.View.VISIBLE);
        arr = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);
        FirebaseFirestore.getInstance()
                .collection("jobListings")
                .whereGreaterThanOrEqualTo("radius", 50)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString("title") + "\n" + snapshot.getString("description") + "\t\t$" + snapshot.getDouble("basePrice") + "\t\t" + snapshot.getDouble("radius") + "mi";
                            Log.d(TAG, "onSuccess: " + temp);
                            arr.add(temp);
                            Log.d(TAG, "onSuccess: " + arr);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
            }

    public void onClickMid(android.view.View view) {
        listView.setVisibility(android.view.View.VISIBLE);
        arr = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);
        FirebaseFirestore.getInstance()
                .collection("jobListings")
                .whereLessThan("radius", 50)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString("title") + "\n" + snapshot.getString("description") + "\t\t$" + snapshot.getDouble("basePrice") + "\t\t" + snapshot.getDouble("radius") + "mi";
                            Log.d(TAG, "onSuccess: " + temp);
                            arr.add(temp);
                            Log.d(TAG, "onSuccess: " + arr);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
            }

    public void onClickClose(android.view.View view) {
        listView.setVisibility(android.view.View.VISIBLE);
        arr = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arr);
        listView.setAdapter(adapter);
        FirebaseFirestore.getInstance()
                .collection("jobListings")
                .whereLessThanOrEqualTo("radius", 20)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString("title") + "\n" + snapshot.getString("description") + "\t\t$" + snapshot.getDouble("basePrice") + "\t\t" + snapshot.getDouble("radius") + "mi";
                            Log.d(TAG, "onSuccess: " + temp);
                            arr.add(temp);
                            Log.d(TAG, "onSuccess: " + arr);
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "onFailure: ", e);
                    }
                });
            }
        }






