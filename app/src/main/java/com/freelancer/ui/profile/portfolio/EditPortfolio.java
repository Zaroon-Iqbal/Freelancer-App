package com.freelancer.ui.profile.portfolio;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.freelancer.R;
import com.freelancer.databinding.ActivityEditPortfolioBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.SortedMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class EditPortfolio extends AppCompatActivity implements RecyclerViewInterface{
    Toolbar toolbar;
    Toolbar deletebar;
    RecyclerView recyclerView;
    PortfolioRecyclerview adapter;
    ArrayList<ImageInfo> list;
    CollectionReference collection;
    HashSet<Integer> set;
    String[] uids;
    Date[] dates;
    HashMap<String,Object> data;
    StorageReference storageReference;
    int numOfPics;
    FirebaseUser user;
    ProgressBar progressBar;


    ActivityResultLauncher<Intent> pickPhotos = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), uri -> {
        if(uri != null)
        {
            Intent i = uri.getData();
            if(i != null)
            {
                progressBar.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
                toolbar.setVisibility(View.GONE);
                ClipData uris = i.getClipData();

                uploadImages(uris);
            }
        }
    });

    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        ActivityEditPortfolioBinding binding = ActivityEditPortfolioBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        user = FirebaseAuth.getInstance().getCurrentUser();

        toolbar = binding.topAppBar;
        deletebar = binding.contextAppBar;
        recyclerView = binding.images;
        progressBar = binding.progress;

        toolbar.setVisibility(View.VISIBLE);

        storageReference = FirebaseStorage.getInstance().getReference().child(user.getUid());

        collection = FirebaseFirestore.getInstance().collection("userListings")
                .document(getIntent().getStringExtra("documentId")).collection("Portfolio");

        displayPortfolio();

        recyclerView.setLayoutManager(new GridLayoutManager(EditPortfolio.this,2));

        set = new HashSet<>();
        data = new HashMap<>();


        toolbar.setNavigationOnClickListener(v -> finish());

        toolbar.setOnMenuItemClickListener((Toolbar.OnMenuItemClickListener) item -> {
            switch (item.getItemId()){
                case R.id.add:
                    addImages();
                    return true;
                case R.id.delete:
                    toolbar.setVisibility(View.GONE);
                    deletebar.setVisibility(View.VISIBLE);
                    adapter.delete = true;
                    adapter.notifyItemRangeChanged(0, adapter.getItemCount());
                    return true;
            }
            return false;
        });

        deletebar.setNavigationOnClickListener(v -> {
            deletebar.setVisibility(View.GONE);
            toolbar.setVisibility(View.VISIBLE);
            adapter.delete = false;
            adapter.notifyItemRangeChanged(0, adapter.getItemCount());
        });

        deletebar.setOnMenuItemClickListener(item -> {
            if (item.getItemId() == R.id.trashCan) {
                deleteImages();
                return true;
            }
            return false;
        });
    }

    private void addImages() {
        Intent i = new Intent(MediaStore.ACTION_PICK_IMAGES);
        i.putExtra(MediaStore.EXTRA_PICK_IMAGES_MAX,5);

        pickPhotos.launch(i);
    }

    private void uploadImages(ClipData uris) {
        numOfPics = uris.getItemCount();

        createIds(numOfPics);


        for(int x = 0; x< uris.getItemCount(); x++) {
            StorageReference ref = storageReference.child(uids[uris.getItemCount() - x - 1]);
            ref.putFile(uris.getItemAt(x).getUri()).addOnCompleteListener(task -> {
                --numOfPics;
                if(numOfPics == 0)
                    createReferences();
            });
        }
    }

    private void createReferences(){
        for(int x = 0; x < uids.length; x++){
            StorageReference ref = storageReference.child(uids[x]);
            ref.getDownloadUrl().addOnSuccessListener(new CompleteListenerExtension(collection,this,uids,dates,x));
        }
    }

    private void createIds(int num) {
        uids = new String[num];
        dates = new Date[num];
        for(int i = 0; i < num; i++){
            uids[i] = UUID.randomUUID().toString();
            dates[i] = new Date();
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e){
                Log.e("EXCEPTION---","Sleep threw an exception",e);
            }
        }
    }

    private void deleteImages() {
        DocumentReference document;
        ArrayList<ImageInfo> copy = new ArrayList<>(list);
        numOfPics = set.size() * 2;
        System.out.println(set);
        for(Integer i: set){
            document = collection.document(copy.get(i).path);
            document.delete().addOnCompleteListener(task -> {
                --numOfPics;
                if(!task.isSuccessful()){
                    Log.e("ERROR----","Could not delete an image from Firestore", task.getException());
                }
                if(numOfPics == 0){
                    displayPortfolio();
                }
            });
            storageReference.child(copy.get(i).path).delete().addOnCompleteListener(task -> {
                --numOfPics;
                if(!task.isSuccessful()){
                    Log.e("ERROR----","Could not delete an image from Storage", task.getException());
                }
                if(numOfPics == 0)
                    displayPortfolio();
            });
        }
        deletebar.setVisibility(View.GONE);
        toolbar.setVisibility(View.VISIBLE);
    }

    void displayPortfolio() {
        list = new ArrayList<>();

        collection.orderBy("Timestamp", Query.Direction.DESCENDING).get().addOnSuccessListener(value -> {
            if(!value.isEmpty()){
                for(DocumentSnapshot documents:value.getDocuments()){
                    list.add(new ImageInfo(documents.getString("ProfilePhoto"),documents.getId()));
                }
                adapter = new PortfolioRecyclerview(list,EditPortfolio.this);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
            }
            else{
                adapter = new PortfolioRecyclerview(list,EditPortfolio.this);
                recyclerView.setAdapter(adapter);
                progressBar.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                toolbar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onItemClicked(int pos,ArrayList<?> otherList) {
        ArrayList<ImageInfo> data = (ArrayList<ImageInfo>) otherList;
        data.get(pos).selected = !data.get(pos).selected;
        adapter.notifyItemChanged(pos);
        if(data.get(pos).selected)
            set.add(pos);
        else
            set.remove(pos);
    }
}
