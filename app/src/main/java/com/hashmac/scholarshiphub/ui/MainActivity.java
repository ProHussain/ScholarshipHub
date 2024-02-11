package com.hashmac.scholarshiphub.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.hashmac.scholarshiphub.adapter.CommonAdapter;
import com.hashmac.scholarshiphub.adapter.UniversityAdapter;
import com.hashmac.scholarshiphub.databinding.ActivityMainBinding;
import com.hashmac.scholarshiphub.dto.Common;
import com.hashmac.scholarshiphub.dto.University;
import com.hashmac.scholarshiphub.utils.DataSource;
import com.hashmac.scholarshiphub.utils.ViewAll;

import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CommonAdapter awardingCountryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initAwardingCountries();
        initNationality();
        initSubjects();
        initUniversities();
        initDegreeLevel();
    }

    private void initUniversities() {
        List<University> universities = DataSource.getUniversities();
        UniversityAdapter universityAdapter = new UniversityAdapter(universities);
        binding.layoutUniversities.rvUniversities.setAdapter(universityAdapter);
        binding.layoutUniversities.tvViewAll.setOnClickListener(v -> openViewAll(ViewAll.UNIVERSITIES));
    }

    private void initDegreeLevel() {
        List<Common> degreeLevels = DataSource.getDegreeLevels();
        CommonAdapter degreeLevelAdapter = new CommonAdapter(degreeLevels);
        binding.layoutDegreeLevel.rvDegreeLevel.setAdapter(degreeLevelAdapter);
        binding.layoutDegreeLevel.tvViewAll.setOnClickListener(v -> openViewAll(ViewAll.DEGREES));
    }

    private void initSubjects() {
        List<Common> subjects = DataSource.getSubjects();
        CommonAdapter subjectsAdapter = new CommonAdapter(subjects);
        binding.layoutSubjects.rvSubjects.setAdapter(subjectsAdapter);
        binding.layoutSubjects.tvViewAll.setOnClickListener(v -> openViewAll(ViewAll.SUBJECTS));
    }

    private void initNationality() {
        List<Common> yourNationalities = DataSource.getContinents();
        CommonAdapter yourNationalityAdapter = new CommonAdapter(yourNationalities);
        binding.layoutNationality.rvNationalities.setAdapter(yourNationalityAdapter);
    }

    private void initAwardingCountries() {
        List<Common> awardingCountries = DataSource.getAwardingCountries().subList(0, 8);
        awardingCountryAdapter = new CommonAdapter(awardingCountries);
        binding.layoutAwardingCountries.rvAwardingCountries.setAdapter(awardingCountryAdapter);
        binding.layoutAwardingCountries.rvAwardingCountries.setHasFixedSize(true);
        binding.layoutAwardingCountries.tvViewAll.setOnClickListener(v -> openViewAll(ViewAll.COUNTRIES));
    }

    private void openViewAll(ViewAll query) {
        Intent intent = new Intent(this, ViewAllActivity.class);
        intent.putExtra("ViewAll", query);
        startActivity(intent);
    }
}