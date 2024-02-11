package com.hashmac.scholarshiphub.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hashmac.scholarshiphub.R;
import com.hashmac.scholarshiphub.databinding.ItemMainUniversityBinding;
import com.hashmac.scholarshiphub.dto.University;
import com.hashmac.scholarshiphub.ui.ScholarshipsListActivity;

import java.util.List;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.ViewHolder> {
    List<University> universities;

    public UniversityAdapter(List<University> universities) {
        this.universities = universities;
    }
    @NonNull
    @Override
    public UniversityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainUniversityBinding binding = ItemMainUniversityBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull UniversityAdapter.ViewHolder holder, int position) {
        holder.bind(universities.get(position));
    }

    @Override
    public int getItemCount() {
        return universities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private ItemMainUniversityBinding binding;
        public ViewHolder(@NonNull ItemMainUniversityBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
        public void bind(University university){
            Glide.with(binding.ivItem.getContext()).load(university.getImage()).into(binding.ivItem);
            binding.tvTitle.setText(university.getTitle());
            binding.tvSubTitle.setText(university.getNumber());
            binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent(binding.getRoot().getContext(), ScholarshipsListActivity.class);
                intent.putExtra("Query", university.getTitle().replace("Universities", "").trim());
                binding.getRoot().getContext().startActivity(intent);
            });
        }
    }
}
