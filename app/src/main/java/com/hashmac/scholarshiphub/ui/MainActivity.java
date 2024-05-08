package com.hashmac.scholarshiphub.ui;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.hashmac.scholarshiphub.adapter.CommonAdapter;
import com.hashmac.scholarshiphub.adapter.UniversityAdapter;
import com.hashmac.scholarshiphub.databinding.ActivityMainBinding;
import com.hashmac.scholarshiphub.dto.Common;
import com.hashmac.scholarshiphub.dto.University;
import com.hashmac.scholarshiphub.utils.DataSource;
import com.hashmac.scholarshiphub.utils.ViewAll;

import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private CommonAdapter awardingCountryAdapter;

    private final ActivityResultLauncher<String> requestPermissionLauncher =
            registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
                if (isGranted) {
                    // FCM SDK (and your app) can post notifications.
                } else {
                    Toast.makeText(this, "Notification permission denied", Toast.LENGTH_SHORT).show();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initNotificationPermission();
        initSearch();
        initAwardingCountries();
        initNationality();
        initSubjects();
        initUniversities();
        initDegreeLevel();
    }

    private void initNotificationPermission() {
        // Check notification permission
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) ==
                    PackageManager.PERMISSION_GRANTED) {
                // FCM SDK (and your app) can post notifications.
            } else {
                requestPermissionLauncher.launch(android.Manifest.permission.POST_NOTIFICATIONS);
            }
        }
    }

    private void initSearch() {
        binding.etSearch.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH || event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                String query = Objects.requireNonNull(binding.etSearch.getText()).toString().trim();
                if (!query.isEmpty()) {
                    Intent intent = new Intent(this, ScholarshipsListActivity.class);
                    intent.putExtra("Action", "Search");
                    intent.putExtra("Query", query);
                    startActivity(intent);
                }
                return true;
            }
            return false;
        });
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