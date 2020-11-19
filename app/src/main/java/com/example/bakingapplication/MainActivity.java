package com.example.bakingapplication;

import android.content.Context;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.loader.content.AsyncTaskLoader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.bakingapplication.adapters.RecipeAdapter;
import com.example.bakingapplication.helpers.RecipeClient;
import com.example.bakingapplication.helpers.RecipeHelper;
import com.example.bakingapplication.helpers.RecipeService;
import com.example.bakingapplication.models.Recipe;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

  private RecyclerView recyclerView;
  private RecyclerView.Adapter adapter;
  private RecyclerView.LayoutManager layoutManager;
  private Context context;
  private RecipeService recipeService;
  public static boolean  allRecipesDownloaded = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    recipeService = new RecipeClient().recipeService;

    new FetchRecipesAsync(this);
    context = this;
  }

  private class FetchRecipesAsync extends AsyncTaskLoader<Recipe> {
    public FetchRecipesAsync(@NonNull Context context) {
      super(context);
      loadInBackground();
    }

    @Nullable @Override public Recipe loadInBackground() {
      recyclerView = findViewById(R.id.recipe_recyclerview);
      Call<List<Recipe>> call = recipeService.getRecipes();

      call.enqueue(new Callback<List<Recipe>>() {
        @Override
        public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
          List<Recipe> recipeList = response.body();
          layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
          adapter = new RecipeAdapter(context, recipeList);

          recyclerView.setLayoutManager(layoutManager);
          recyclerView.setAdapter(adapter);
          allRecipesDownloaded = true;
        }

        @Override
        public void onFailure(Call<List<Recipe>> call, Throwable t) {
          Log.d("ERROR ON FAILURE", "onFailure: " + t.toString());
        }
      });
      return null;
    }
  }
}