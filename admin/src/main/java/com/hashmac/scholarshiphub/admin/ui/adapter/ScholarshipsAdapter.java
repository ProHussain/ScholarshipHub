package com.hashmac.scholarshiphub.admin.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hashmac.scholarshiphub.admin.R;
import com.hashmac.scholarshiphub.admin.databinding.ItemScholarshipBinding;
import com.hashmac.scholarshiphub.admin.dto.Scholarship;
import com.hashmac.scholarshiphub.admin.ui.ScholarshipFormActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.microedition.khronos.opengles.GL;

import timber.log.Timber;

public class ScholarshipsAdapter extends RecyclerView.Adapter<ScholarshipsAdapter.ScholarshipViewHolder>{
    private List<Scholarship> scholarships = new ArrayList<>();
    private OnScholarshipClickListener listener;

    public ScholarshipsAdapter(OnScholarshipClickListener listener) {
        this.listener = listener;
    }

    public void setScholarships(List<Scholarship> scholarships) {
        this.scholarships.clear();
        this.scholarships = scholarships;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ScholarshipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemScholarshipBinding binding = ItemScholarshipBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ScholarshipViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ScholarshipViewHolder holder, int position) {
        holder.bind(scholarships.get(position));
    }

    @Override
    public int getItemCount() {
        return scholarships.size();
    }

    public class ScholarshipViewHolder extends RecyclerView.ViewHolder{
        private final ItemScholarshipBinding binding;
        public ScholarshipViewHolder(@NonNull ItemScholarshipBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Scholarship scholarship) {
            Glide.with(binding.getRoot().getContext()).load(scholarship.getImageUrl()).placeholder(R.drawable.temp).into(binding.ivScholarship);
            binding.tvScholarshipName.setText(scholarship.getName());
            binding.tvFundType.setText(scholarship.getFunds());
            binding.tvUniversities.setText(String.format("%s Universities", scholarship.getCountry()));
            binding.tvDegreeLevel.setText(scholarship.getDegreeLevel());
            binding.tvSubjects.setText(scholarship.getSubjects());
            binding.tvNationality.setText(scholarship.getStudents());
            binding.tvCountry.setText(scholarship.getCountry());
            binding.tvExpiryDate.setText(scholarship.getExpiryDate());

            if (isDateExpired(scholarship.getExpiryDate())) {
                binding.btnViewDetails.setBackgroundColor(binding.getRoot().getContext().getResources().getColor(R.color.colorAccent));
                binding.btnViewDetails.setText(R.string.expired);
                binding.btnViewDetails.setTextColor(binding.getRoot().getContext().getResources().getColor(R.color.white));
            }

            binding.getRoot().setOnClickListener(view -> {
                Intent intent = new Intent(binding.getRoot().getContext(), ScholarshipFormActivity.class);
                intent.putExtra("scholarship", scholarship);
                binding.getRoot().getContext().startActivity(intent);
            });

            binding.btnViewDetails.setOnClickListener(view -> {
                if (isDateExpired(scholarship.getExpiryDate())) {
                    listener.onScholarshipClick(scholarship.getName());
                    return;
                }
                Intent intent = new Intent(binding.getRoot().getContext(), ScholarshipFormActivity.class);
                intent.putExtra("scholarship", scholarship);
                binding.getRoot().getContext().startActivity(intent);
            });
        }

        private boolean isDateExpired(String expiryDate) {
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            try {
                return Objects.requireNonNull(sdf.parse(expiryDate)).before(sdf.parse(sdf.format(System.currentTimeMillis())));
            } catch (Exception e) {
                Timber.e(e);
                return false;
            }
        }
    }

    public interface OnScholarshipClickListener {
        void onScholarshipClick(String scholarship);
    }
}
