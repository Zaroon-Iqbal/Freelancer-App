package com.freelancer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class Queue extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_queue);

        /* // Add some jobs to the queue for testing
        addJob(new Runnable() {
            @Override
            public void run() {
                Log.d("JobQueue", "Job 1 started");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("JobQueue", "Job 1 finished");
            }
        });
        addJob(new Runnable() {
            @Override
            public void run() {
                Log.d("JobQueue", "Job 2 started");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("JobQueue", "Job 2 finished");
            }
        });
    }

    private synchronized void addJob(Runnable job) {
        jobQueue.add(job);
        if (!isProcessingJobs) {
            isProcessingJobs = true;
            processJobs();
        }
    }

    private void add(Runnable job) {
    }

    private synchronized void processJobs() {
        final Runnable job = jobQueue.peek();
        if (job == null) {
            isProcessingJobs = false;
            return;
        }
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                job.run();
                jobQueue.remove();
                processJobs();
            }
        });
    }

    private void remove() {
    }

    private Runnable peek() {
    } */
    }
}



