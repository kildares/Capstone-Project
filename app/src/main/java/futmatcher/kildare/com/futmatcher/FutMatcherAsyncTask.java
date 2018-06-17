package futmatcher.kildare.com.futmatcher;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import futmatcher.kildare.com.futmatcher.model.Match;
import futmatcher.kildare.com.futmatcher.persistence.FutMatcherFirebaseDatabase;

/**
 * Created by kilda on 6/16/2018.
 */

public class FutMatcherAsyncTask extends AsyncTask<Void, Void, List<Match>> {

    private FutMatcherFirebaseDatabase mFirebaseDatabase;
    private LoadedMatchListData mListener;

    public FutMatcherAsyncTask(LoadedMatchListData listener){
        mListener = listener;
        mFirebaseDatabase = FutMatcherFirebaseDatabase.getInstance();
    }

    @Override
    protected List<Match> doInBackground(Void... voids) {

        //List<Match> matchList = mFirebaseDatabase.getMatchList();
        return null;
    }

    @Override
    protected void onPostExecute(List<Match> matches) {
        mListener.onLoadedMatchListData(matches);
    }

    public interface LoadedMatchListData
    {
        void onLoadedMatchListData(List<Match> matchList);
    }
}
