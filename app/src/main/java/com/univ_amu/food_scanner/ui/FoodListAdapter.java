package com.univ_amu.food_scanner.ui;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.univ_amu.food_scanner.data.Food;
import com.univ_amu.food_scanner.databinding.FoodItemBinding;
import com.univ_amu.food_scanner.databinding.FragmentFoodBinding;
import com.univ_amu.food_scanner.databinding.FragmentFoodListBinding;

public class FoodListAdapter extends ListAdapter<Food, FoodListAdapter.ViewHolder> {


    FoodListAdapter() {
        super(diffUtilCallback);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Utiliser la classe FoodItemBinding pour créer la vue à partir du layout
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        FoodItemBinding binding = FoodItemBinding.inflate(inflater, parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Food food = getItem(position);
        holder.bind(food);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private FoodItemBinding foodItemBinding;
        public Food food;
        public ViewHolder(FoodItemBinding binding) {
            super(binding.getRoot());
            this.foodItemBinding = binding;
            this.foodItemBinding.setViewHolder(this);
        }

        void bind(Food food) {
            this.food = food;
            this.foodItemBinding.invalidateAll();
        }

        public void onClick(View view){
            if(foodItemBinding != null){
                NavDirections action = FoodListFragmentDirections.actionFoodListFragmentToFoodFragment(food.code);
                Navigation.findNavController(foodItemBinding.getRoot()).navigate(action);
            }
        }
    }

    private static final DiffUtil.ItemCallback<Food> diffUtilCallback =
            new DiffUtil.ItemCallback<Food>() {
                @Override
                public boolean areItemsTheSame(Food oldFood, Food newFood) {
                    return oldFood.code.equals(newFood.code);
                }
                @Override
                public boolean areContentsTheSame(Food oldFood, Food newFood) {
                    return this.areItemsTheSame(oldFood, newFood);
                }
            };

}