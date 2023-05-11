package com.freelancer.ui.login;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.annotation.NonNull;

import com.freelancer.R;
import com.freelancer.joblisting.creation.model.JobInfoModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
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

    @SuppressLint("MissingInflatedId")
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        b2 = findViewById(R.id.backBtn);
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
                            String temp = snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "title"))) + "\n" +
                                    snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "description")))+ "\t\t\n$" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "basePrice"))) + "\t\t" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "radius"))) + " mi";

                            // JobInfoModel jobInfoModel = snapshot.get("jobInfo", JobInfoModel.class);
                            // Log.i("Job info", "Data: " + jobInfoModel);

                            Log.d(TAG, "onSuccess: " + temp);
                            arr.add(temp);
                            //if (snapshot.getString("name") != null) {
                            //arr.add(temp);
                            //}
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
                .whereGreaterThanOrEqualTo(FieldPath.of("jobInfo", "basePrice"), 100)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "title"))) + "\n" +
                                    snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "description")))+ "\t\t\n$" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "basePrice"))) + "\t\t" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "radius"))) + " mi";
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
                .whereLessThan(FieldPath.of("jobInfo", "basePrice"), 100)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "title"))) + "\n" +
                                    snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "description")))+ "\t\t\n$" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "basePrice"))) + "\t\t" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "radius"))) + " mi";
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
                .whereLessThan(FieldPath.of("jobInfo", "basePrice"), 50)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "title"))) + "\n" +
                                    snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "description")))+ "\t\t\n$" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "basePrice"))) + "\t\t" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "radius"))) + " mi";
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
                .whereGreaterThanOrEqualTo(FieldPath.of("jobInfo", "radius"), 50)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "title"))) + "\n" +
                                    snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "description")))+ "\t\t\n$" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "basePrice"))) + "\t\t" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "radius"))) + " mi";
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
                .whereLessThanOrEqualTo(FieldPath.of("jobInfo", "radius"), 20)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "title"))) + "\n" +
                                    snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "description")))+ "\t\t\n$" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "basePrice"))) + "\t\t" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "radius"))) + " mi";
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
                .whereLessThanOrEqualTo(FieldPath.of("jobInfo", "radius"), 10)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotList = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot snapshot : snapshotList) {
                            Log.d(TAG, "onSuccess: " + snapshot.getData().toString());
                            String temp = snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "title"))) + "\n" +
                                    snapshot.getString(String.valueOf(FieldPath.of("jobInfo", "description")))+ "\t\t\n$" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "basePrice"))) + "\t\t" +
                                    snapshot.getDouble(String.valueOf(FieldPath.of("jobInfo", "radius"))) + " mi";
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
