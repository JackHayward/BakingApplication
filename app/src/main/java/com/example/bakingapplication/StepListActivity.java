package com.example.bakingapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.bakingapplication.fragments.PlayerFragment;
import com.example.bakingapplication.models.Step;
import java.util.ArrayList;

public class StepListActivity extends AppCompatActivity {

  static ArrayList<Step> steps;
  private final String RECIPE = "recipe";

  private boolean mTwoPane;

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
    bundle.putParcelableArrayList(RECIPE, steps);

    if (findViewById(R.id.recipe_detail_container) != null) {
      mTwoPane = true;
    }

    View recyclerView = findViewById(R.id.recipe_list);
    assert recyclerView != null;
    setupRecyclerView((RecyclerView) recyclerView);
  }

  private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
    recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, steps, mTwoPane));
  }

  public static class SimpleItemRecyclerViewAdapter
      extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

    private final StepListActivity mParentActivity;
    private final boolean mTwoPane;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Step item = (Step) view.getTag();
        if (mTwoPane) {
          Bundle arguments = new Bundle();
          arguments.putParcelable("recipe", item);
          PlayerFragment fragment = new PlayerFragment();
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
      holder.mIdView.setText(String.valueOf(position +1));
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
}