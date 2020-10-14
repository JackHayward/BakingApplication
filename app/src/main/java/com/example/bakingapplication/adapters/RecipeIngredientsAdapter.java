package com.example.bakingapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bakingapplication.R;
import com.example.bakingapplication.models.Ingredient;
import java.util.List;

public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.RecipeIngredientsViewHolder> {
  Context context;
  List<Ingredient> recipeIngredients;

  public RecipeIngredientsAdapter(Context context, List<Ingredient> recipeIngredients) {
    this.context = context;
    this.recipeIngredients = recipeIngredients;
  }

  public static class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder {
    TextView ingredient;

    public RecipeIngredientsViewHolder(@NonNull View itemView) {
      super(itemView);
      ingredient = itemView.findViewById(R.id.ingredient_text);
    }
  }

  @NonNull @Override
  public RecipeIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_ingredients_item, parent, false);
    return new RecipeIngredientsViewHolder(v);
  }

  @Override public void onBindViewHolder(@NonNull RecipeIngredientsViewHolder holder, final int position) {
    holder.ingredient.setText(recipeIngredients.get(position).getIngredient());
  }

  @Override public int getItemCount() {
    return recipeIngredients.size();
  }
}
