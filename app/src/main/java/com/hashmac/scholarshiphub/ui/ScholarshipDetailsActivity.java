package com.hashmac.scholarshiphub.ui;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

    }
}