package com.example.bakingapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.fragment.app.FragmentManager;
import com.example.bakingapplication.fragments.PlayerFragment;
import com.example.bakingapplication.models.Recipe;
import com.example.bakingapplication.models.Step;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity {
  private final String RECIPE = "recipe";
  private Recipe recipe;
  private FrameLayout playerLayout;
  private TextView instruction;
  private Step step;
  private Button nextStepButton;
  private Button previousStepButton;
  private ArrayList<Step> stepArrayList = new ArrayList<>();
  private Context context = this;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_detail);

    playerLayout = findViewById(R.id.player_container);
    nextStepButton = findViewById(R.id.btn_next_step);
    previousStepButton = findViewById(R.id.btn_previous_step);

    nextStepButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (stepArrayList.get(step.getId()).getId() == stepArrayList.size() -1) {
          return;
        }
        finish();
        overridePendingTransition(0, 0);
        step = stepArrayList.get(step.getId()+1);
        Intent intent = new Intent(context, StepDetailActivity.class);
        intent.putExtra("recipe", step);
        intent.putParcelableArrayListExtra("step_list", stepArrayList);
        startActivity(intent);
        overridePendingTransition(0, 0);
      }
    });

    previousStepButton.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        if (stepArrayList.get(step.getId()).getId() == 0) {
          return;
        }
        finish();
        overridePendingTransition(0, 0);
        step = stepArrayList.get(step.getId()-1);
        Intent intent = new Intent(context, StepDetailActivity.class);
        intent.putExtra("recipe", step);
        intent.putParcelableArrayListExtra("step_list", stepArrayList);
        startActivity(intent);
        overridePendingTransition(0, 0);
      }
    });

    // Show the Up button in the action bar.
    //ActionBar actionBar = getSupportActionBar();
    //if (actionBar != null) {
    //  actionBar.setDisplayHomeAsUpEnabled(true);
    //}

    Intent recipeIntent = getIntent();
    step = recipeIntent.getParcelableExtra(RECIPE);
    stepArrayList = recipeIntent.getParcelableArrayListExtra("step_list");

    PlayerFragment playerFragment = new PlayerFragment();
    Bundle bundle = new Bundle();
    bundle.putParcelable(RECIPE, step);
    playerFragment.setArguments(bundle);

    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.player_container, playerFragment).commit();

    // savedInstanceState is non-null when there is fragment state
    // saved from previous configurations of this activity
    // (e.g. when rotating the screen from portrait to landscape).
    // In this case, the fragment will automatically be re-added
    // to its container so we don"t need to manually add it.
    // For more information, see the Fragments API guide at:
    //
    // http://developer.android.com/guide/components/fragments.html
    //
    //if (savedInstanceState == null) {
    //  // Create the detail fragment and add it to the activity
    //  // using a fragment transaction.
    //  Bundle arguments = new Bundle();
    //  arguments.putString(RecipeDetailFragment.ARG_ITEM_ID,
    //      getIntent().getStringExtra(RecipeDetailFragment.ARG_ITEM_ID));
    //  RecipeDetailFragment fragment = new RecipeDetailFragment();
    //  fragment.setArguments(arguments);
    //  getSupportFragmentManager().beginTransaction()
    //      .add(R.id.player_container, fragment)
    //      .commit();
    //}
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      // This ID represents the Home or Up button. In the case of this
      // activity, the Up button is shown. For
      // more details, see the Navigation pattern on Android Design:
      //
      // http://developer.android.com/design/patterns/navigation.html#up-vs-back
      //
      navigateUpTo(new Intent(this, StepListActivity.class));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }
}