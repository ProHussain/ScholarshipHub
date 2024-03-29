package com.hashmac.scholarshiphub.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hashmac.scholarshiphub.adapter.ScholarshipsAdapter;
import com.hashmac.scholarshiphub.databinding.ActivityScholarshipsListBinding;
import com.hashmac.scholarshiphub.dto.Scholarship;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import timber.log.Timber;

public class ScholarshipsListActivity extends AppCompatActivity {
    private ActivityScholarshipsListBinding binding;
    private FirebaseFirestore fireStore;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScholarshipsListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        fireStore = FirebaseFirestore.getInstance();
        initToolbar();
        initArgs();
    }

    private void initArgs() {
        String query = getIntent().getStringExtra("Query");
        String action = getIntent().getStringExtra("Action");
        if (action == null) {
            action = "Filter";
        }
        Objects.requireNonNull(getSupportActionBar()).setTitle(query);
        fetchScholarships(query, action);
    }

    private void fetchScholarships(String query, String action) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        fireStore.collection("scholarships").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                List<Scholarship> scholarships = task.getResult().toObjects(Scholarship.class);
                if (action.equals("Search"))
                    searchScholarships(scholarships, query);
                else
                    filterScholarships(scholarships, query);
            } else {
                progressDialog.dismiss();
                Timber.e("Error fetching scholarships");
            }
        });
    }

    private void searchScholarships(List<Scholarship> scholarships, String query) {
        Timber.d("Searching Scholarships");
        Timber.d("Query: %s", query);
        Timber.d("Scholarships: %s", scholarships);
        List<Scholarship> searchedScholarships = new ArrayList<>();
        for (Scholarship scholarship : scholarships) {
            if (scholarship.getName().toLowerCase().contains(query.toLowerCase())
                    || scholarship.getCountry().toLowerCase().contains(query.toLowerCase())
                    || scholarship.getDegreeLevel().toLowerCase().contains(query.toLowerCase())
                    || scholarship.getUniversity().toLowerCase().contains(query.toLowerCase())
                    || scholarship.getSubjects().toLowerCase().contains(query.toLowerCase())) {
                searchedScholarships.add(scholarship);
            }
        }
        progressDialog.dismiss();
        ScholarshipsAdapter adapter = new ScholarshipsAdapter();
        adapter.setScholarships(searchedScholarships);
        binding.recyclerView.setAdapter(adapter);
    }

    private void filterScholarships(List<Scholarship> scholarships, String query) {
        Timber.d("Filtering Scholarships");
        Timber.d("Query: %s", query);
        Timber.d("Scholarships: %s", scholarships);
        List<Scholarship> filteredScholarships = new ArrayList<>();
        for (Scholarship scholarship : scholarships) {
            // For Country
            if (scholarship.getCountry().toLowerCase().contains(query.toLowerCase())) {
                filteredScholarships.add(scholarship);
            }
            // For Degree
            String[] degrees = scholarship.getDegreeLevel().split(",");
            for (String degree : degrees) {
                if (degree.toLowerCase().contains(query.toLowerCase())) {
                    filteredScholarships.add(scholarship);
                }
            }
            // For University
            if (scholarship.getUniversity().toLowerCase().contains(query.toLowerCase())) {
                filteredScholarships.add(scholarship);
            }

            // For Nationality
            if (scholarship.getContinent().toLowerCase().contains(query.toLowerCase())) {
                filteredScholarships.add(scholarship);
            }

            // For Subject
            String[] subjects = scholarship.getSubjects().split(",");
            for (String subject : subjects) {
                if (subject.toLowerCase().contains(query.toLowerCase())) {
                    filteredScholarships.add(scholarship);
                }
            }
        }

        progressDialog.dismiss();
        ScholarshipsAdapter adapter = new ScholarshipsAdapter();
        adapter.setScholarships(filteredScholarships);
        binding.recyclerView.setAdapter(adapter);
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}