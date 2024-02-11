package com.hashmac.scholarshiphub.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hashmac.scholarshiphub.databinding.ItemMainCommonBinding;
import com.hashmac.scholarshiphub.dto.Common;
import com.hashmac.scholarshiphub.ui.ScholarshipsListActivity;

import java.util.List;

public class CommonAdapter extends RecyclerView.Adapter<CommonAdapter.ViewHolder> {
    List<Common> commonList;

    public CommonAdapter(List<Common> commonList) {
        this.commonList = commonList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemMainCommonBinding binding = ItemMainCommonBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(commonList.get(position));
    }

    @Override
    public int getItemCount() {
        return commonList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemMainCommonBinding binding;
        public ViewHolder(@NonNull ItemMainCommonBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }

        public void bind(Common common) {
            binding.tvTitle.setText(common.getTitle());
            Glide.with(binding.getRoot()).load(common.getImage()).into(binding.ivItem);
            binding.getRoot().setOnClickListener(v -> {
                Intent intent = new Intent(binding.getRoot().getContext(), ScholarshipsListActivity.class);
                intent.putExtra("Query", common.getTitle());
                binding.getRoot().getContext().startActivity(intent);
            });
        }
    }
}
