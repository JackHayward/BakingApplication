package com.example.bakingapplication;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.*;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RemoteViews;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.example.bakingapplication.models.Ingredient;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {
  public static String CHANGED = "android.appwidget.action.APPWIDGET_UPDATE";
  public static final String ACTION_RECIPE_CHANGED =
      "com.example.bakingapplication.ACTION_RECIPE_CHANGED";

  static AppWidgetManager awm;
  static Set<Integer> appWidgetIds = new LinkedHashSet<>();

  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
      int appWidgetId) {

    CharSequence widgetText = context.getString(R.string.appwidget_text);
    // Construct the RemoteViews object
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
    views.setTextViewText(R.id.appwidget_text, widgetText);

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(new ComponentName(context, BakingWidget.class), views);
    awm = appWidgetManager;
    Log.d("WIDGET ID", String.valueOf(appWidgetId));

    appWidgetIds.add(appWidgetId);
  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
    // There may be multiple widgets active, so update all of them
    for (int appWidgetId : appWidgetIds) {
      updateAppWidget(context, appWidgetManager, appWidgetId);
    }
  }

  @Override
  public void onEnabled(Context context) {
    // Enter relevant functionality for when the first widget is created
  }

  @Override
  public void onDisabled(Context context) {
    // Enter relevant functionality for when the last widget is disabled
  }

  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);

    if (intent.getAction().equals(CHANGED)) {
      if (intent.hasExtra("RecipeName")) {
        //String rn = intent.getStringExtra("RecipeName");
        ArrayList<Ingredient> ingredients = intent.getParcelableArrayListExtra("RecipeName");
        String ingredientItems = convertIngredientsListToString(ingredients);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        List<Integer> ids = intent.getIntegerArrayListExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS);
        for(Integer id: ids) {


          views.setTextViewText(R.id.appwidget_text, ingredientItems); // TODO: use setRemoteAdapter and a ListView
          awm.updateAppWidget(id, views);
        }
      }
    }
  }

  public ArrayList<String> convertIngredientsToStrings(ArrayList<Ingredient> ingredients) {
    ArrayList<String> finalList = new ArrayList<>();

    for (Ingredient ingredient : ingredients) {
      finalList.add(ingredient.getIngredient());
    }

    return finalList;
  }

  public String convertIngredientsListToString(ArrayList<Ingredient> ingredients) {
    String finalString = "";

    if (ingredients != null) {
      for (Ingredient ingredient : ingredients) {
        finalString += "\n" + ingredient.getIngredient();
      }
    }

    return finalString;
  }
}