package com.hashmac.scholarshiphub.ui;

import static java.lang.System.in;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.hashmac.scholarshiphub.R;
import com.hashmac.scholarshiphub.adapter.ViewAllAdapter;
import com.hashmac.scholarshiphub.databinding.ActivityViewAllBinding;
import com.hashmac.scholarshiphub.dto.Common;
import com.hashmac.scholarshiphub.dto.University;
import com.hashmac.scholarshiphub.utils.DataSource;
import com.hashmac.scholarshiphub.utils.ViewAll;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ViewAllActivity extends AppCompatActivity {
    private ActivityViewAllBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAllBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initToolbar();
        initArgs();
    }

    private void initToolbar() {
        setSupportActionBar(binding.toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initArgs() {
        ViewAll args = (ViewAll) getIntent().getSerializableExtra("ViewAll");
        if (args == null) {
            finish();
        } else {
            switch (args) {
                case DEGREES:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Degrees");
                    fetchDegrees();
                    break;
                case SUBJECTS:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Subjects");
                    fetchSubjects();
                    break;
                case UNIVERSITIES:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Universities");
                    fetchUniversities();
                    break;
                case COUNTRIES:
                    Objects.requireNonNull(getSupportActionBar()).setTitle("Countries");
                    fetchCountries();
                    break;
                default:
                    finish();
            }
        }

    }

    private void fetchUniversities() {
        List<Common> universities = new ArrayList<>();
        for (University university : DataSource.getUniversities()) {
            universities.add(new Common(university.getTitle(), university.getImage()));
        }
        ViewAllAdapter adapter = new ViewAllAdapter(universities);
        binding.recyclerView.setAdapter(adapter);
    }

    private void fetchCountries() {
        ViewAllAdapter adapter = new ViewAllAdapter(DataSource.getAwardingCountries());
        binding.recyclerView.setAdapter(adapter);
    }

    private void fetchSubjects() {
        ViewAllAdapter adapter = new ViewAllAdapter(DataSource.getSubjects());
        binding.recyclerView.setAdapter(adapter);
    }

    private void fetchDegrees() {
        ViewAllAdapter adapter = new ViewAllAdapter(DataSource.getDegreeLevels());
        binding.recyclerView.setAdapter(adapter);
    }
}