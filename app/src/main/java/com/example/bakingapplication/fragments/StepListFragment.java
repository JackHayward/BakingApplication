//package com.example.bakingapplication.fragments;
//
//import android.os.Bundle;
//import androidx.fragment.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.example.bakingapplication.R;
//import com.example.bakingapplication.adapters.RecipeIngredientsAdapter;
//import com.example.bakingapplication.adapters.RecipeStepsAdapter;
//import com.example.bakingapplication.models.Recipe;
//
//public class StepListFragment extends Fragment {
//
//  private RecyclerView recyclerView;
//  private RecyclerView.Adapter ingredientsAdapter;
//  private RecyclerView.Adapter stepsAdapter;
//  private final String RECIPE = "recipe";
//  private Recipe recipe;
//
//  public RecipeDetailsFragment() {
//  }
//
//  public static RecipeDetailsFragment newInstance(String param1, String param2) {
//    RecipeDetailsFragment fragment = new RecipeDetailsFragment();
//    Bundle args = new Bundle();
//    fragment.setArguments(args);
//    return fragment;
//  }
//
//  @Override
//  public void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    if (getArguments() != null) {
//
//    }
//  }
//
//  @Override
//  public View onCreateView(LayoutInflater inflater, ViewGroup container,
//      Bundle savedInstanceState) {
//
//    if (getArguments() != null) {
//      recipe = getArguments().getParcelable(RECIPE);
//    }
//
//    View view = inflater.inflate(R.layout.fragment_recipe_details, container, false);
//    ingredientsAdapter = new RecipeIngredientsAdapter(getContext(), recipe.getIngredients());
//    stepsAdapter = new RecipeStepsAdapter(getContext(), recipe.getSteps());
//
//    recyclerView = view.findViewById(R.id.recipe_ingredients_recyclerview);
//    recyclerView.setHasFixedSize(true);
//    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//    recyclerView.setAdapter(ingredientsAdapter);
//
//    recyclerView = view.findViewById(R.id.recipe_steps_recyclerview);
//    recyclerView.setHasFixedSize(true);
//    recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
//    recyclerView.setAdapter(stepsAdapter);
//
//    return view;
//  }
//}