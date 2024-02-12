package com.hashmac.scholarshiphub.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.hashmac.scholarshiphub.R;
import com.hashmac.scholarshiphub.databinding.ActivityScholarshipDetailsBinding;
import com.hashmac.scholarshiphub.dto.Scholarship;

public class ScholarshipDetailsActivity extends AppCompatActivity {
    private ActivityScholarshipDetailsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityScholarshipDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initArgs();
    }

    private void initArgs() {
        Scholarship scholarship = (Scholarship) getIntent().getSerializableExtra("scholarship");

        if (scholarship != null) {
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
            binding.tvDescription.setText(scholarship.getDescription());
        }
    }
}