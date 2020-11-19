package com.example.bakingapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import butterknife.BindView;
import com.example.bakingapplication.fragments.PlayerFragment;
import com.example.bakingapplication.models.Recipe;
import com.example.bakingapplication.models.Step;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MenuItem;
import java.util.ArrayList;

public class StepDetailActivity extends AppCompatActivity implements PlayerFragment.OnOptionClickListener {
  private final String RECIPE = "recipe";
  private ArrayList<Step> steps = new ArrayList<>();
  private Step step;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_detail);

    Intent recipeIntent = getIntent();
    if (recipeIntent != null) {
      if (recipeIntent.hasExtra(RECIPE)) {
        step = recipeIntent.getParcelableExtra(RECIPE);
      }
      if (recipeIntent.hasExtra("step_list")) {
        steps = recipeIntent.getParcelableArrayListExtra("step_list");
      }
    }

    if (savedInstanceState == null) {
      PlayerFragment playerFragment = new PlayerFragment();
      Bundle bundle = new Bundle();
      bundle.putParcelable(RECIPE, step);
      playerFragment.setArguments(bundle);

      FragmentManager fragmentManager = getSupportFragmentManager();
      fragmentManager.beginTransaction().replace(R.id.player_container, playerFragment).commit();
    }
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == android.R.id.home) {
      navigateUpTo(new Intent(this, StepListActivity.class));
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override public void onOptionSelected(String option, Step step) {
    Context context = this;
    Intent intent = new Intent(context, StepDetailActivity.class);

    switch (option) {
      case "next":
        if (steps.get(step.getId()).getId() == steps.size() - 1) {
          return;
        }
        finish();
        step = steps.get(step.getId() + 1);
        intent.putExtra("recipe", step);
        intent.putParcelableArrayListExtra("step_list", steps);
        context.startActivity(intent);
        break;

      case "previous":
        if (steps.get(step.getId()).getId() == 0) {
          return;
        }
        finish();
        step = steps.get(step.getId() - 1);
        intent.putExtra("recipe", step);
        intent.putParcelableArrayListExtra("step_list", steps);
        context.startActivity(intent);
        break;
    }
  }

  @Override
  public void onAttachFragment(@NonNull Fragment fragment) {
    super.onAttachFragment(fragment);
    if (fragment instanceof PlayerFragment) {
      PlayerFragment playerFragment = (PlayerFragment) fragment;
      playerFragment.setCallBack(this);
    }
  }
}