package futmatcher.kildare.com.futmatcher.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.model.Match;

/**
 * Created by kilda on 6/9/2018.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder>{

    List<Match> Matches;
    Context mContext;

    public MatchAdapter(Context context, List<Match> matches)
    {
        Matches = matches;
        mContext = context;
    }

    @NonNull
    @Override
    public MatchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.match_list_item, parent, false);
        return new MatchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MatchViewHolder holder, int position) {

        holder.MatchTitle.setText(Matches.get(position).getTitle());
        holder.MatchDate.setText(Matches.get(position).getDate());
        holder.MatchLocation.setText(Matches.get(position).getLocation());
    }

    @Override
    public int getItemCount() {
        if(Matches == null)
            return 0;
        else
            return Matches.size();
    }

    public class MatchViewHolder extends RecyclerView.ViewHolder{

        TextView MatchTitle;
        TextView MatchLocation;
        TextView MatchDate;

        public MatchViewHolder(View view)
        {
            super(view);

            MatchTitle = view.findViewById(R.id.tv_list_match_title);
            MatchLocation = view.findViewById(R.id.tv_list_match_location);
            MatchDate = view.findViewById(R.id.tv_list_match_date);
        }
    }
}
