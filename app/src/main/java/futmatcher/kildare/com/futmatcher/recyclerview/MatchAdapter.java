package futmatcher.kildare.com.futmatcher.recyclerview;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.List;

import futmatcher.kildare.com.futmatcher.R;
import futmatcher.kildare.com.futmatcher.model.Match;

/**
 * Created by kilda on 6/9/2018.
 */

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder>{

    List<Match> Matches;
    Context mContext;
    OnMatchItemClickListener mListener;

    public MatchAdapter(Context context, List<Match> matches, OnMatchItemClickListener listener)
    {
        Matches = matches;
        mContext = context;
        mListener = listener;
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

        holder.MatchTitle.setText(mContext.getString(R.string.rv_title)+ " " + Matches.get(position).getTitle());
        holder.MatchDate.setText(mContext.getString(R.string.rv_date)+ " " + Matches.get(position).getDate());
        holder.MatchLocation.setText(mContext.getString(R.string.rv_location)+ " " + Matches.get(position).getLocation());

    }

    @Override
    public int getItemCount() {
        if(Matches == null)
            return 0;
        else
            return Matches.size();
    }

    public void addMatch(Match match)
    {
        Matches.add(match);
        notifyDataSetChanged();
    }

    public void updateMatch(Match match)
    {
        for(int i = 0 ; i < Matches.size() ; i++){
            if(Matches.get(i).getTitle().equals(match.getTitle())){
                Matches.remove(i);
                Matches.add(match);
                break;
            }
        }
        notifyDataSetChanged();
    }

    public void removeMatch(Match match)
    {
        Matches.remove(match);
        notifyDataSetChanged();
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


            view.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Toast.makeText(mContext, "Position " + Integer.toString(position), Toast.LENGTH_SHORT).show();
                    if(mListener != null){
                        mListener.onClickMatchItem(Matches.get(position));
                    }
                }
            });

        }


    }

    public interface OnMatchItemClickListener
    {
        void onClickMatchItem(Match match);
    }


}
