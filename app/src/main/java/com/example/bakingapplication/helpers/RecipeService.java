package com.example.bakingapplication.helpers;

import com.example.bakingapplication.models.Recipe;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface RecipeService {
  @GET("baking.json")
  Call<List<Recipe>> getRecipes();
}
