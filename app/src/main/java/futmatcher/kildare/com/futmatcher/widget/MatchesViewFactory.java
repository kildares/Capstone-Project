package futmatcher.kildare.com.futmatcher.widget;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.firebaselistenerfactory.FirebaseChildEventFactory;
import futmatcher.kildare.com.futmatcher.persistence.FutMatcherFirebaseDatabase;

/**
 * Created by kilda on 8/13/2018.
 */

public class MatchesViewFactory implements RemoteViewsService.RemoteViewsFactory{

	private Context mContext;
	private List<String> mMatches;
	private int mWidgetId;

	public MatchesViewFactory(Context context,Intent intent){
		mContext = context;
		mMatches = intent.getStringArrayListExtra(context.getString(R.string.key_widget_match_list));
		Bundle bundle = intent.getExtras();
		mWidgetId = bundle.getInt(mContext.getString(R.string.key_widget_id));

		FutMatcherFirebaseDatabase.getInstance().addChildEventListenerToReference(
				FirebaseChildEventFactory.getListener(FirebaseChildEventFactory.ListenerType.WIDGET,this));
	}

	public void setMatches(List<String> matches){
		mMatches = matches;
	}


	@Override
	public void onCreate() {

	}

	@Override
	public void onDataSetChanged() {

	}

	@Override
	public void onDestroy() {

	}

	@Override
	public int getCount() {
		return (mMatches != null) ? mMatches.size() : 0 ;
	}

	@Override
	public RemoteViews getViewAt(int i) {

		RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
		views.setTextViewText(R.id.tv_widget_item, mMatches.get(i));
		return views;
	}

	@Override
	public RemoteViews getLoadingView() {
		return null;
	}

	@Override
	public int getViewTypeCount() {
		return 1;
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}


	public void updateWidget(List<String> matches){
		mMatches = matches;
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
		MatchesAppWidget.updateAppWidget(mContext,appWidgetManager,mWidgetId);

	}
}
