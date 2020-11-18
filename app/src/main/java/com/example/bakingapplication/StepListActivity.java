package com.example.bakingapplication;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.bakingapplication.fragments.PlayerFragment;
import com.example.bakingapplication.models.Ingredient;
import com.example.bakingapplication.models.Step;
import java.util.ArrayList;

public class StepListActivity extends AppCompatActivity
    implements PlayerFragment.OnOptionClickListener {

  static ArrayList<Step> steps;
  static ArrayList<Ingredient> ingredients;
  private final String RECIPE = "recipe";
  private final String INGREDIENTS = "ingredient";
  private boolean mTwoPane;

  RecyclerView recyclerView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_recipe_list);

    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    toolbar.setTitle(getTitle());

    Bundle bundle = new Bundle();
    Intent recipeIntent = getIntent();
    steps = recipeIntent.getParcelableArrayListExtra(RECIPE);
    ingredients = recipeIntent.getParcelableArrayListExtra(INGREDIENTS);
    bundle.putParcelableArrayList(RECIPE, steps);
    bundle.putParcelableArrayList(INGREDIENTS, ingredients);

    if (findViewById(R.id.recipe_detail_container) != null) {
      mTwoPane = true;
    }

    recyclerView = findViewById(R.id.recipe_list);
    setupRecyclerView(recyclerView);

    if (mTwoPane) {
      Step step = steps.get(0);
      Bundle arguments = new Bundle();
      PlayerFragment fragment = new PlayerFragment();
      arguments.putParcelable("recipe", step);
      fragment.setArguments(arguments);
      getSupportFragmentManager().beginTransaction()
          .replace(R.id.recipe_detail_container, fragment)
          .commit();
    }
  }

  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, steps, mTwoPane));
  }

  @Override public void onOptionSelected(String option, Step step) {
    Bundle arguments = new Bundle();
    PlayerFragment fragment = new PlayerFragment();

    switch (option) {
      case "next":
        if (steps.get(step.getId()).getId() == steps.size() - 1) {
          return;
        }
        step = steps.get(step.getId() + 1);

        arguments.putParcelable("recipe", step);
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.recipe_detail_container, fragment)
            .commit();
        break;

      case "previous":
        if (steps.get(step.getId()).getId() == 0) {
          return;
        }
        step = steps.get(step.getId() - 1);

        arguments.putParcelable("recipe", step);
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
            .replace(R.id.recipe_detail_container, fragment)
            .commit();
        break;
    }
  }

  public static class SimpleItemRecyclerViewAdapter
      extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final StepListActivity mParentActivity;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Step item = (Step) view.getTag();
        Intent intent1 = new Intent(BakingWidget.CHANGED);
        intent1.putParcelableArrayListExtra("RecipeName", ingredients);
        intent1.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, new ArrayList<>(BakingWidget.appWidgetIds));
        mParentActivity.getApplicationContext().sendBroadcast(intent1);

        if (mTwoPane) {
          Bundle arguments = new Bundle();
          PlayerFragment fragment = new PlayerFragment();
          arguments.putParcelable("recipe", item);
          fragment.setArguments(arguments);
          mParentActivity.getSupportFragmentManager().beginTransaction()
              .replace(R.id.recipe_detail_container, fragment)
              .commit();
        } else {
          Context context = view.getContext();
          Intent intent = new Intent(context, StepDetailActivity.class);
          intent.putExtra("recipe", item);
          intent.putParcelableArrayListExtra("step_list", steps);

          context.startActivity(intent);
        }
      }
    };

    SimpleItemRecyclerViewAdapter(StepListActivity parent, ArrayList<Step> items, boolean twoPane) {
      steps = items;
      mParentActivity = parent;
      mTwoPane = twoPane;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
          .inflate(R.layout.recipe_list_content, parent, false);
      return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
      holder.mIdView.setText(String.valueOf(position + 1));
      holder.mContentView.setText(steps.get(position).getShortDescription());

      holder.itemView.setTag(steps.get(position));
      holder.itemView.setOnClickListener(mOnClickListener);
    }

    @Override
    public int getItemCount() {
      return steps.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
      final TextView mIdView;
      final TextView mContentView;

      ViewHolder(View view) {
        super(view);
        mIdView = (TextView) view.findViewById(R.id.id_text);
        mContentView = (TextView) view.findViewById(R.id.content);
      }
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