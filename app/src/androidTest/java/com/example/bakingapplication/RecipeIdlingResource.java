package com.example.bakingapplication;

import androidx.test.espresso.IdlingResource;

public class RecipeIdlingResource implements IdlingResource {
  private ResourceCallback  resourceCallback;

  @Override
  public String getName() {
    return RecipeIdlingResource.class.getName();

  }

  @Override
  public boolean isIdleNow() {
    boolean idle = MainActivity.allRecipesDownloaded;
    if (idle && resourceCallback != null) {
      resourceCallback.onTransitionToIdle();
    }
    return idle;
  }

  @Override
  public void registerIdleTransitionCallback(ResourceCallback callback) {
    this.resourceCallback = callback;

  }
}
