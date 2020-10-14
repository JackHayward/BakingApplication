package com.example.bakingapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bakingapplication.adapters.RecipeIngredientsAdapter;
import com.example.bakingapplication.models.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {
  Recipe recipe;
  private RecyclerView recyclerView;
  private RecyclerView.Adapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private Context context = this;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_details);

    Intent recipeIntent = getIntent();
    recipe = recipeIntent.getParcelableExtra("recipe_list");

    recyclerView = findViewById(R.id.recipe_ingredients_recyclerview);
    layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
    adapter = new RecipeIngredientsAdapter(context, recipe.getIngredients());
    recyclerView.setLayoutManager(layoutManager);
    recyclerView.setAdapter(adapter);
  }
}
