package com.hashmac.scholarshiphub.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.hashmac.scholarshiphub.R;
import com.hashmac.scholarshiphub.databinding.ActivityScholarshipDetailsBinding;
import com.hashmac.scholarshiphub.dto.Scholarship;

import java.util.Objects;

public class ScholarshipDetailsActivity extends AppCompatActivity {
    private ActivityScholarshipDetailsBinding binding;
    String scholarshipUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScholarshipDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initArgs();
        initClickListeners();
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

    private void initClickListeners() {
        binding.btnViewDetails.setOnClickListener(v -> {
            Intent view = new Intent();
            view.setAction(Intent.ACTION_VIEW);
            view.setData(Uri.parse(scholarshipUrl));
            startActivity(view);
        });

        binding.btnChatWithAdmin.setOnClickListener(v -> {
            String phoneNumber = "+923016907146";
            Intent chat = new Intent();
            chat.setAction(Intent.ACTION_VIEW);
            chat.setData(Uri.parse("https://wa.me/" + phoneNumber));
            startActivity(chat);
        });
    }

    private void initArgs() {
        Scholarship scholarship = (Scholarship) getIntent().getSerializableExtra("scholarship");

        if (scholarship != null) {
            scholarshipUrl = scholarship.getScholarshipUrl();
            Glide.with(this)
                    .load(scholarship.getImageUrl())
                    .placeholder(R.drawable.temp)
                    .into(binding.ivScholarship);
            binding.tvScholarshipName.setText(scholarship.getName());
            binding.tvFundType.setText(scholarship.getFunds());
            binding.tvUniversities.setText(String.format("%s Universities", scholarship.getCountry()));
            binding.tvDegreeLevel.setText(scholarship.getDegreeLevel());
            binding.tvSubjects.setText(scholarship.getSubjects());
            binding.tvNationality.setText(scholarship.getStudents());
            binding.tvCountry.setText(scholarship.getCountry());
            binding.tvExpiryDate.setText(scholarship.getExpiryDate());
            binding.tvDescription.setText(scholarship.getDescription());

            Objects.requireNonNull(getSupportActionBar()).setTitle(scholarship.getName());
        }
    }
}