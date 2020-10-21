package com.example.bakingapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bakingapplication.R;
import com.example.bakingapplication.RecipeDetailsActivity;
import com.example.bakingapplication.StepActivity;
import com.example.bakingapplication.models.Ingredient;
import com.example.bakingapplication.models.Step;
import java.util.List;

public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeIngredientsViewHolder> {
  Context context;
  List<Step> recipeSteps;
  private final String RECIPE_STEP = "recipe_step";

  public RecipeStepsAdapter(Context context, List<Step> recipeSteps) {
    this.context = context;
    this.recipeSteps = recipeSteps;
  }

  public static class RecipeIngredientsViewHolder extends RecyclerView.ViewHolder {
    TextView step;

    public RecipeIngredientsViewHolder(@NonNull View itemView) {
      super(itemView);
      step = itemView.findViewById(R.id.recipe_step);
    }
  }

  @NonNull @Override
  public RecipeIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_item, parent, false);
    return new RecipeIngredientsViewHolder(v);
  }

  @Override public void onBindViewHolder(@NonNull RecipeIngredientsViewHolder holder, final int position) {
    holder.step.setText(recipeSteps.get(position).getShortDescription());

    holder.itemView.setOnClickListener(new View.OnClickListener() {

      @Override public void onClick(View view) {
        Step step = recipeSteps.get(position);
        Intent intent = new Intent(context, StepActivity.class);
        intent.putExtra(RECIPE_STEP, step);
        context.startActivity(intent);
      }
    });
  }

  @Override public int getItemCount() {
    return recipeSteps.size();
  }
}
