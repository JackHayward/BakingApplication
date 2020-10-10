package com.example.bakingapplication.helpers;

import android.util.Log;
import com.example.bakingapplication.models.Recipe;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeHelper {
  static RecipeService recipeService = new RecipeClient().recipeService;

  public static List<Recipe> fetchRecipes() {
    final List<Recipe> recipeList = new ArrayList<>();
    Call<List<Recipe>> call = recipeService.getRecipes();

    call.enqueue(new Callback<List<Recipe>>() {
      @Override
      public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
        List<Recipe> recipe = response.body();
        if (!recipe.isEmpty()) {
          recipeList.addAll(recipe);
        }
      }

      @Override
      public void onFailure(Call<List<Recipe>> call, Throwable t) {
        Log.d("Error", t.toString());
      }
    });
    return recipeList;
  }
}
