package com.hashmac.scholarshiphub.admin.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hashmac.scholarshiphub.admin.R;
import com.hashmac.scholarshiphub.admin.databinding.ActivityMainBinding;
import com.hashmac.scholarshiphub.admin.dto.Scholarship;
import com.hashmac.scholarshiphub.admin.repo.ScholarshipRepository;
import com.hashmac.scholarshiphub.admin.ui.adapter.ScholarshipsAdapter;
import com.hashmac.scholarshiphub.admin.utils.RepoListener;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements RepoListener {
    private ActivityMainBinding binding;
    private ScholarshipRepository repository;
    private ScholarshipsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = ScholarshipRepository.getInstance();
        repository.setListener(this);
        initAdapter();
        initClickListeners();
    }

    @Override
    protected void onResume() {
        super.onResume();
        fetchScholarships();
    }

    private void initAdapter() {
        adapter = new ScholarshipsAdapter(scholarship -> repository.sendNotification(scholarship,"Scholarship date is expired"));
        binding.recyclerView.setAdapter(adapter);
    }

    private void fetchScholarships() {
        repository.fetchScholarships();
    }

    private void initClickListeners() {
        binding.fabAddScholarship.setOnClickListener(view -> {
            startActivity(new Intent(this, ScholarshipFormActivity.class));
        });
    }

    @Override
    public void onRepoSuccess() {
        // Show success message
    }

    @Override
    public void onRepoFailure(String message) {
        // Show error message
    }

    @Override
    public void onRepoFetch(List<Scholarship> scholarships) {
        adapter.setScholarships(scholarships);
    }
}