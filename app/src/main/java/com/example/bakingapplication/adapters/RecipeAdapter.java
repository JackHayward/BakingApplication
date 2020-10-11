package com.example.bakingapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bakingapplication.R;
import com.example.bakingapplication.RecipeDetailsActivity;
import com.example.bakingapplication.models.Recipe;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {
  private final String RECIPE_LIST = "recipe_list";
  Context context;
  List<Recipe> recipeList;

  public RecipeAdapter(Context context, List<Recipe> recipeList) {
    this.context = context;
    this.recipeList = recipeList;
  }

  public static class RecipeViewHolder extends RecyclerView.ViewHolder {
    TextView recipeTitle;
    ImageView recipeImage;

    public RecipeViewHolder(@NonNull View itemView) {
      super(itemView);
      recipeTitle = itemView.findViewById(R.id.recipe_title);
      recipeImage = itemView.findViewById(R.id.recipe_image);
    }
  }

  @NonNull @Override
  public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item, parent, false);
    return new RecipeViewHolder(v);
  }

  @Override public void onBindViewHolder(@NonNull RecipeViewHolder holder, final int position) {
    holder.recipeTitle.setText(recipeList.get(position).getName());

    holder.itemView.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View view) {
        Recipe recipe = recipeList.get(position);
        Intent intent = new Intent(context, RecipeDetailsActivity.class);
        intent.putExtra(RECIPE_LIST, recipe);
        context.startActivity(intent);
      }
    });
  }

  @Override public int getItemCount() {
    return recipeList.size();
  }
}
