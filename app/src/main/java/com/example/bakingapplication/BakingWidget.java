package com.example.bakingapplication;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.*;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidget extends AppWidgetProvider {
  public static String CHANGED = "android.appwidget.action.APPWIDGET_UPDATE";
public static final String ACTION_RECIPE_CHANGED= "com.example.bakingapplication.ACTION_RECIPE_CHANGED";

  static AppWidgetManager awm;
  static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
      int appWidgetId) {

    CharSequence widgetText = context.getString(R.string.appwidget_text);
    // Construct the RemoteViews object
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
    //views.setTextViewText(R.id.appwidget_text, widgetText);

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views);
    awm = appWidgetManager;
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
  public void onReceive(Context context, Intent intent){
    super.onReceive(context,intent);

    if (intent.getAction().equals(CHANGED)){
      if (intent.hasExtra("RecipeName")) {
        String rn = intent.getStringExtra("RecipeName");
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget);
        int id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        views.setTextViewText(R.id.appwidget_text, rn);
        awm.updateAppWidget(id, views);
      }
    }
  }
}