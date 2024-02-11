package com.hashmac.scholarshiphub.admin.ui;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.hashmac.scholarshiphub.admin.R;
import com.hashmac.scholarshiphub.admin.databinding.ActivityScholarshipFormBinding;
import com.hashmac.scholarshiphub.admin.dto.Scholarship;
import com.hashmac.scholarshiphub.admin.repo.ScholarshipRepository;
import com.hashmac.scholarshiphub.admin.utils.RepoListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScholarshipFormActivity extends AppCompatActivity implements RepoListener {
    private ActivityScholarshipFormBinding binding;
    private ScholarshipRepository repository;
    private Uri imageUri = null;
    private ProgressDialog progressDialog;
    private Scholarship scholarship;
    private final ActivityResultLauncher<Intent> pickImageLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            Intent data = result.getData();
            if (data != null) {
                imageUri = data.getData();
                binding.ivScholarship.setImageURI(imageUri);
            }
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityScholarshipFormBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        repository = ScholarshipRepository.getInstance();
        repository.setListener((RepoListener) this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initArgs();
        initViews();
        initClickListeners();
    }

    private void initArgs() {
        Scholarship scholarship = (Scholarship) getIntent().getSerializableExtra("scholarship");
        if (scholarship != null) {
            this.scholarship = scholarship;
            Glide.with(this).load(scholarship.getImageUrl()).placeholder(R.drawable.temp).into(binding.ivScholarship);
            binding.etTitle.setText(scholarship.getName());
            binding.actvCountry.setText(scholarship.getCountry());
            binding.actvContinent.setText(scholarship.getContinent());
            binding.etUniversity.setText(scholarship.getUniversity());
            binding.etSubjects.setText(scholarship.getSubjects());
            binding.etDegreeLevel.setText(scholarship.getDegreeLevel());
            binding.actvStatus.setText(scholarship.getFunds());
            binding.actvStudents.setText(scholarship.getStudents());
            binding.etDescription.setText(scholarship.getDescription());
        }
        binding.btnSubmit.setText(scholarship == null ? "Add" : "Update");
        binding.btnDelete.setVisibility(scholarship == null ? View.GONE : View.VISIBLE);
    }

    private void initViews() {
        binding.etSubjects.setOnClickListener(v -> showMultiSubjectDialog());
        binding.actvContinent.setOnClickListener(v -> showContinentSpinner());
        binding.etDegreeLevel.setOnClickListener(v -> showDegreeLevelDialog());
        binding.actvStudents.setOnClickListener(v -> showStudentsSpinner());
        binding.actvStatus.setOnClickListener(v -> showStatusSpinner());
        initCountryAutoComplete();
        progressDialog = new ProgressDialog(this);
    }

    private void showStatusSpinner() {
        String[] statusArray = getResources().getStringArray(R.array.funds_status);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, statusArray);
        binding.actvStatus.setAdapter(adapter);
        binding.actvStatus.showDropDown();
    }

    private void showStudentsSpinner() {
        String[] studentsArray = getResources().getStringArray(R.array.student_types);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, studentsArray);
        binding.actvStudents.setAdapter(adapter);
        binding.actvStudents.showDropDown();
    }

    private void showDegreeLevelDialog() {
        String[] degreeLevelsArray = getResources().getStringArray(R.array.degree_levels);
        boolean[] checkedItems = new boolean[degreeLevelsArray.length];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Items");
        builder.setMultiChoiceItems(degreeLevelsArray, checkedItems, (dialog, which, isChecked) -> {
            checkedItems[which] = isChecked;
        });
        builder.setCancelable(false);

        builder.setPositiveButton("Done", (dialog, which) -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < checkedItems.length; i++) {
                if (checkedItems[i]) {
                    stringBuilder.append(degreeLevelsArray[i]).append(", ");
                }
            }
            binding.etDegreeLevel.setText(stringBuilder.toString());
        });
        builder.setNegativeButton("CANCEL", (dialog, which) -> {});
        builder.create();
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showContinentSpinner() {
        String[] continentsArray = getResources().getStringArray(R.array.continents);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, continentsArray);
        binding.actvContinent.setAdapter(adapter);
        binding.actvContinent.showDropDown();
    }

    private void initCountryAutoComplete() {
        String[] countriesArray = getResources().getStringArray(R.array.countries);
        List<String> countriesList = new ArrayList<>(Arrays.asList(countriesArray));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, countriesList);
        binding.actvCountry.setAdapter(adapter);
    }

    private void showMultiSubjectDialog() {
        String[] subjectsArray = getResources().getStringArray(R.array.subjects);
        boolean[] checkedItems = new boolean[subjectsArray.length];
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Items");
        builder.setMultiChoiceItems(subjectsArray, checkedItems, (dialog, which, isChecked) -> {
            checkedItems[which] = isChecked;
        });
        builder.setCancelable(false);

        builder.setPositiveButton("Done", (dialog, which) -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < checkedItems.length; i++) {
                if (checkedItems[i]) {
                    stringBuilder.append(subjectsArray[i]).append(", ");
                }
            }
            binding.etSubjects.setText(stringBuilder.toString());
        });
        builder.setNegativeButton("CANCEL", (dialog, which) -> {});
        builder.create();
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void initClickListeners() {
        binding.btnSubmit.setOnClickListener(v -> {
            if (binding.btnSubmit.getText().toString().equals("Add")) {
                addScholarship();
            } else {
                updateScholarship();
            }
        });
        binding.ivPickImage.setOnClickListener(v -> pickImage());
        binding.btnDelete.setOnClickListener(v -> deleteScholarship());
    }

    private void deleteScholarship() {
        if (scholarship != null) {
            progressDialog.setMessage("Deleting scholarship...");
            progressDialog.show();
            repository.deleteScholarship(scholarship);
        }
    }

    private void updateScholarship() {
        String name = binding.etTitle.getText().toString().trim();
        String country = binding.actvCountry.getText().toString();
        String continent = binding.actvContinent.getText().toString();
        String university = binding.etUniversity.getText().toString();
        String subjects = binding.etSubjects.getText().toString();
        String degreeLevel = binding.etDegreeLevel.getText().toString();
        String funds = binding.actvStatus.getText().toString();
        String students = binding.actvStudents.getText().toString();
        String description = binding.etDescription.getText().toString().trim();
        if (name.isEmpty() || country.isEmpty() || continent.isEmpty() || university.isEmpty() || subjects.isEmpty() || degreeLevel.isEmpty() || funds.isEmpty() || students.isEmpty() || description.isEmpty()) {
            return;
        }
        progressDialog.setMessage("Updating scholarship...");
        progressDialog.show();
        Scholarship temp = new Scholarship(this.scholarship.getId(), name, country, continent, university, subjects, degreeLevel, funds, students, description, this.scholarship.getImageUrl(), this.scholarship.getCreatedAt(), null);
        repository.updateScholarship(temp, imageUri);
    }

    private void pickImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        pickImageLauncher.launch(intent);
    }

    private void addScholarship() {
        String name = binding.etTitle.getText().toString().trim();
        String country = binding.actvCountry.getText().toString();
        String continent = binding.actvContinent.getText().toString();
        String university = binding.etUniversity.getText().toString();
        String subjects = binding.etSubjects.getText().toString();
        String degreeLevel = binding.etDegreeLevel.getText().toString();
        String funds = binding.actvStatus.getText().toString();
        String students = binding.actvStudents.getText().toString();
        String description = binding.etDescription.getText().toString().trim();
        if (name.isEmpty() || country.isEmpty() || continent.isEmpty() || university.isEmpty() || subjects.isEmpty() || degreeLevel.isEmpty() || funds.isEmpty() || students.isEmpty() || description.isEmpty()) {
            return;
        }
        if (imageUri == null) {
            Toast.makeText(this, "Please pick an image", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Adding scholarship...");
        progressDialog.show();
        Scholarship scholarship = new Scholarship(null, name, country, continent, university, subjects, degreeLevel, funds, students, description, null, null, null);
        repository.addScholarship(scholarship, imageUri);
    }

    @Override
    public void onRepoSuccess() {
        progressDialog.dismiss();
        Toast.makeText(this, "Scholarship added successfully", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onRepoFailure(String message) {
        progressDialog.dismiss();
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onRepoFetch(List<Scholarship> scholarships) {

    }
}