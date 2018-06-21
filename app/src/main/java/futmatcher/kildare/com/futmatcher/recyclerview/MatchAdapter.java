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

public class MatchAdapter extends RecyclerView.Adapter<MatchAdapter.MatchViewHolder> implements ChildEventListener {

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


    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        Match match = dataSnapshot.getValue(Match.class);
        Matches.add(match);
        notifyDataSetChanged();

        Toast.makeText(mContext,"onChildAdded",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        Match match = dataSnapshot.getValue(Match.class);
        for(int i = 0 ; i < Matches.size() ; i++){
            if(Matches.get(i).getTitle().equals(match.getTitle())){
                Matches.remove(i);
                Matches.add(match);
                break;
            }
        }

        Toast.makeText(mContext,"onChildChanged",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        Match match = dataSnapshot.getValue(Match.class);
        Matches.remove(match);

        Toast.makeText(mContext,"onChildRemoved",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        Toast.makeText(mContext,"onChildMoved",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {
        Toast.makeText(mContext,"onCancelled",Toast.LENGTH_LONG).show();
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
