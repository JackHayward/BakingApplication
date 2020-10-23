//package com.example.bakingapplication;
//
//import android.content.Context;
//import android.content.Intent;
//import android.os.Bundle;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.FragmentManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.bakingapplication.fragments.RecipeDetailsFragment;
//import com.example.bakingapplication.models.Recipe;
//
//public class RecipeDetailsActivity extends AppCompatActivity {
//  Recipe recipe;
//  private RecyclerView ingredientsRecyclerView;
//  private RecyclerView.Adapter ingredientsAdapter;
//  private RecyclerView stepsRecyclerView;
//  private RecyclerView.Adapter stepsAdapter;
//  private RecyclerView.LayoutManager ingredientsLayoutManager;
//  private RecyclerView.LayoutManager stepsLayoutManager;
//  private Context context = this;
//  private final String RECIPE = "recipe";
//
//  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_recipe_details);
//
//    Bundle bundle = new Bundle();
//    Intent recipeIntent = getIntent();
//    recipe = recipeIntent.getParcelableExtra("recipe_list");
//    bundle.putParcelable(RECIPE, recipe);
//
//    RecipeDetailsFragment recipeDetailsFragment = new RecipeDetailsFragment();
//    recipeDetailsFragment.setArguments(bundle);
//
//    FragmentManager fragmentManager = getSupportFragmentManager();
//    fragmentManager.beginTransaction().replace(R.id.recipe_details_container, recipeDetailsFragment).commit();
//
//    //if (findViewById(R.id.tablet_recipe_details) == null) {
//    //
//    //}
//
//    //ingredientsLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//    //ingredientsRecyclerView = findViewById(R.id.recipe_ingredients_recyclerview);
//    //ingredientsAdapter = new RecipeIngredientsAdapter(context, recipe.getIngredients());
//    //ingredientsRecyclerView.setLayoutManager(ingredientsLayoutManager);
//    //ingredientsRecyclerView.setAdapter(ingredientsAdapter);
//    //
//    //stepsLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
//    //stepsRecyclerView = findViewById(R.id.recipe_steps_recyclerview);
//    //stepsAdapter = new RecipeStepsAdapter(context, recipe.getSteps());
//    //stepsRecyclerView.setLayoutManager(stepsLayoutManager);
//    //stepsRecyclerView.setAdapter(stepsAdapter);
//  }
//}
