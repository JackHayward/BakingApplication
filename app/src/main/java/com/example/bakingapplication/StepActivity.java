//package com.example.bakingapplication;
//
//import android.content.Intent;
//import android.content.res.Configuration;
//import android.widget.Button;
//import android.widget.FrameLayout;
//import android.widget.TextView;
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import android.os.Bundle;
//import androidx.fragment.app.FragmentManager;
//import com.example.bakingapplication.fragments.PlayerFragment;
//import com.example.bakingapplication.models.Step;
//
//public class StepActivity extends AppCompatActivity {
//  private final String RECIPE_STEP = "recipe_step";
//  private Step step;
//  private FrameLayout playerLayout;
//  private TextView instruction;
//  private Button previousStep;
//  private Button nextStep;
//
//  @Override
//  protected void onCreate(Bundle savedInstanceState) {
//    super.onCreate(savedInstanceState);
//    setContentView(R.layout.activity_step);
//    playerLayout = findViewById(R.id.step_player_layout);
//    instruction = findViewById(R.id.step_instruction);
//    previousStep = findViewById(R.id.previous_button);
//    nextStep = findViewById(R.id.next_button);
//
//    Intent stepIntent = getIntent();
//    step = stepIntent.getParcelableExtra(RECIPE_STEP);
//
//    PlayerFragment playerFragment = new PlayerFragment();
//    Bundle bundle = new Bundle();
//    bundle.putParcelable(RECIPE_STEP, step);
//    playerFragment.setArguments(bundle);
//
//    FragmentManager fragmentManager = getSupportFragmentManager();
//    fragmentManager.beginTransaction().replace(R.id.step_player_layout, playerFragment).commit();
//
//    instruction.setText(step.getDescription());
//  }
//}