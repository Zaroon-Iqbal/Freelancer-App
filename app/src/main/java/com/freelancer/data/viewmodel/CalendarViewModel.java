package com.freelancer.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.freelancer.data.model.FirestoreRepository;

/**
 * The Calendar ViewModel.
 *
 * This class is responsible for managing all data required by the calendar view,
 * and for communicating with other parts of the application to acquire that data.
 */
public class CalendarViewModel extends AndroidViewModel {
    private final FirestoreRepository firestoreRepository;

    public CalendarViewModel(@NonNull Application application) {
        super(application);
        firestoreRepository = new FirestoreRepository(application);

    }

    public void createAppointment() {
        firestoreRepository.addBooking();
    }

    /*
    This Method is used to call the firestoreRepository method for retrieving the specified field.
     */
    public void retrieveAppointment(String Collection, String Document, String Field){
        firestoreRepository.retrieveAppointment(Collection, Document, Field);
    }
}