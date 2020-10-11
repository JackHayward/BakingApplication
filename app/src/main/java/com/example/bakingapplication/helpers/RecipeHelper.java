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


}
