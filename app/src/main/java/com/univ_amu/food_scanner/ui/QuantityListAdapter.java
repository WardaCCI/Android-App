package com.univ_amu.food_scanner.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.univ_amu.food_scanner.data.Quantity;
import com.univ_amu.food_scanner.databinding.QuantityItemBinding;

public class QuantityListAdapter extends ListAdapter<Quantity, QuantityListAdapter.ViewHolder> {

    QuantityListAdapter() {
        super(diffUtilCallback);
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Utiliser la classe QuantityItemBinding pour créer la vue à partir du layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        QuantityItemBinding binding = QuantityItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Quantity quantity = getItem(position);
        holder.bind(quantity);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private QuantityItemBinding quantityItemBinding;
        public Quantity quantity;

        public ViewHolder(QuantityItemBinding binding) {
            super(binding.getRoot());
            this.quantityItemBinding = binding;
            this.quantityItemBinding.setViewHolder(this);
        }

        void bind(Quantity quantity) {
            this.quantity = quantity;
            this.quantityItemBinding.invalidateAll();
        }
    }

    private static final DiffUtil.ItemCallback<Quantity> diffUtilCallback =
            new DiffUtil.ItemCallback<Quantity>() {
                @Override
                public boolean areItemsTheSame(Quantity oldQuantity, Quantity newQuantity) {
                    return oldQuantity.foodCode == newQuantity.foodCode;
                }

                @Override
                public boolean areContentsTheSame(Quantity oldQuantity, Quantity newQuantity) {
                    return areItemsTheSame(oldQuantity, newQuantity);
                }
            };
}
