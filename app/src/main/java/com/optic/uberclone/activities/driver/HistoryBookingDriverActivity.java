package com.optic.uberclone.activities.driver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.optic.uberclone.R;
import com.optic.uberclone.adapters.HistoryBookingDriverAdapter;
import com.optic.uberclone.models.HistoryBooking;
import com.optic.uberclone.providers.AuthProvider;

public class HistoryBookingDriverActivity extends AppCompatActivity {

    private RecyclerView mReciclerView;
    private HistoryBookingDriverAdapter mAdapter;
    private AuthProvider mAuthProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_booking_driver);

        mReciclerView = findViewById(R.id.recyclerViewHistoryBooking);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mReciclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuthProvider = new AuthProvider();
        Query query = FirebaseDatabase.getInstance().getReference()
                .child("HistoryBooking")
                .orderByChild("idDriver")
                .equalTo(mAuthProvider.getId());
        FirebaseRecyclerOptions<HistoryBooking> options = new FirebaseRecyclerOptions.Builder<HistoryBooking>()
                .setQuery(query, HistoryBooking.class)
                .build();
        mAdapter = new HistoryBookingDriverAdapter(options, HistoryBookingDriverActivity.this);

        mReciclerView.setAdapter(mAdapter);
        mAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAdapter.stopListening();
    }
}
