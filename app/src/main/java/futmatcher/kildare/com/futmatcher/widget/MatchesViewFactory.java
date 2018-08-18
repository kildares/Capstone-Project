package futmatcher.kildare.com.futmatcher.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.firebaselistenerfactory.FirebaseChildEventFactory;
import futmatcher.kildare.com.futmatcher.firebaselistenerfactory.FirebaseEventListener;
import futmatcher.kildare.com.futmatcher.persistence.FutMatcherFirebaseDatabase;

/**
 * Created by kilda on 8/13/2018.
 */

public class MatchesViewFactory implements RemoteViewsService.RemoteViewsFactory{

	private static final String LOG_TAG = "WIDGET_VIEW_FACTORY";

	private Context mContext;
	private static List<String> mMatches;
	private int mWidgetId;

	private static FirebaseEventListener mFirebaseListener;

	public MatchesViewFactory(Context context,Intent intent){
		mContext = context;
		Bundle bundle = intent.getExtras();
		mWidgetId = bundle.getInt(mContext.getString(R.string.key_widget_id));
	}


	@Override
	public void onCreate() {
		if(mFirebaseListener == null){
			FutMatcherFirebaseDatabase.getInstance().addChildEventListenerToReference(
					FirebaseChildEventFactory.getListener(FirebaseChildEventFactory.ListenerType.WIDGET,this));
		}
		if(mMatches  == null)
			mMatches = convertMatchSetToList();
	}

	@Override
	public void onDataSetChanged() {
		mMatches = convertMatchSetToList();
	}

	@Override
	public void onDestroy() {

	}

	@Override
	public int getCount() {
		if(mMatches != null)
			return mMatches.size();
		else
			return 0;
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
		return false;
	}


	public void updateWidget(){
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mContext);
		MatchesAppWidget.updateAppWidget(mContext,appWidgetManager,mWidgetId);
	}


	public Set<String> getStoredMatches()
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		return preferences.getStringSet(mContext.getString(R.string.key_widget_title_set),new HashSet<String>());
	}

	public void storeMatches()
	{
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
		SharedPreferences.Editor editor = preferences.edit();
		Set<String> set = new HashSet<>(mMatches);
		editor.putStringSet(mContext.getString(R.string.key_widget_title_set), set);
		editor.apply();
	}


	public List<String> convertMatchSetToList(){
		List<String> list = new ArrayList<>();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
			getStoredMatches().forEach(m -> {
				if (!list.contains(m))
					list.add(m);
			});
		}
		return list;
	}

	public void addMatch(String title) {
		mMatches = convertMatchSetToList();
		mMatches.add(title);
		storeMatches();
		Log.i(LOG_TAG,"MATCH ADDED: " + title);
		updateWidget();
	}

	public void replaceMatch(String s, String title) {
		mMatches = convertMatchSetToList();
		mMatches.remove(s);
		mMatches.add(title);
		storeMatches();
		Log.i(LOG_TAG,"MATCH UPDATED: " + title);
		updateWidget();
	}

	public void removeMatch(String title) {
		mMatches = convertMatchSetToList();
		mMatches.remove(title);
		storeMatches();
		Log.i(LOG_TAG,"MATCH REMOVED: " + title);
		updateWidget();
	}
}
