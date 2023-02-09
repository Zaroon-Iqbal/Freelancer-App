package com.freelancer.data.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.freelancer.data.model.FirestoreRepository;

import java.util.Date;

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

    public void createAppointment(String title, Date startTime, Date endTime) {
        firestoreRepository.saveAppointment(title, startTime, endTime);
    }
}