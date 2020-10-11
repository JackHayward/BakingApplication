package com.example.bakingapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.bakingapplication.models.Recipe;

public class RecipeDetailsActivity extends AppCompatActivity {
  Recipe recipe;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_details);

    Intent recipeIntent = getIntent();
    recipe = recipeIntent.getParcelableExtra("recipe_list");
  }
}
