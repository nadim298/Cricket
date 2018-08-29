package me.h.shakawat.allcricketforb.RecyclerView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import me.h.shakawat.allcricketforb.Model.WorldCup;
import me.h.shakawat.allcricketforb.R;

public class AsiaCupRecyclerView extends RecyclerView.Adapter<AsiaCupRecyclerView.MyViewHolder> {



    private Context mContext;
    private List<WorldCup> mData;

    public AsiaCupRecyclerView(Context mContext, List<WorldCup> mData)
    {
        this.mContext = mContext;
        this.mData = mData;
    }


    @NonNull
    @Override
    public AsiaCupRecyclerView.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v;
        v = LayoutInflater.from(mContext).inflate(R.layout.worldcupsimpleview,parent,false);

        final AsiaCupRecyclerView.MyViewHolder myViewHolder = new AsiaCupRecyclerView.MyViewHolder(v);

        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AsiaCupRecyclerView.MyViewHolder holder, int position) {

        holder.playNoText.setText(mData.get(position).getPlayNoTxt());
        holder.groupNameText.setText(mData.get(position).getGroupNameTxt());
        holder.teamNameText.setText(mData.get(position).getTeamNameTxt());
        holder.dateText.setText(mData.get(position).getDateTxt());
        holder.timeText.setText(mData.get(position).getTimeTxt());
        holder.stadiumNametext.setText(mData.get(position).getStadiumTxt());

        holder.flagNum_1.setImageResource(mData.get(position).getFlag_1());
        holder.flagNum_2.setImageResource(mData.get(position).getFlag_2());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView playNoText;
        private TextView groupNameText;
        private TextView teamNameText;
        private TextView dateText;
        private TextView timeText;
        private TextView stadiumNametext;

        private ImageView flagNum_1;
        private ImageView flagNum_2;



        public MyViewHolder(View itemView) {
            super(itemView);


            playNoText = itemView.findViewById(R.id.playNoTextViewId);
            groupNameText = itemView.findViewById(R.id.groupNametextView_Id);
            teamNameText = itemView.findViewById(R.id.teamNameTextView_Id);
            dateText = itemView.findViewById(R.id.dateTextView_Id);
            timeText = itemView.findViewById(R.id.timeTextView_Id);
            stadiumNametext = itemView.findViewById(R.id.stadiumNameTextView_Id);

            flagNum_1 = itemView.findViewById(R.id.flagNum_1_imageViewId);
            flagNum_2 = itemView.findViewById(R.id.flagNum_2_imageView_Id);

        }
    }


}
