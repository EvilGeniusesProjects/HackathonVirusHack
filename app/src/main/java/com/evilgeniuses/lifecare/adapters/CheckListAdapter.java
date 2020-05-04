package com.evilgeniuses.lifecare.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.activities.CheckListActivity;
import com.evilgeniuses.lifecare.models.CheckList;

import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.List;

public class CheckListAdapter extends RecyclerView.Adapter<CheckListAdapter.ViewHolder>{

    private Context mContext;
    private List<CheckList> mCheckList;
    private boolean ischat;
    String lastMessage;
    String lastMessageTime;

    public CheckListAdapter(Context mContext, List<CheckList> mCheckList, boolean ischat){
        this.mCheckList = mCheckList;
        this.mContext = mContext;
        this.ischat = ischat;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_check_list, parent, false);
        return new CheckListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CheckList checkList = mCheckList.get(position);


        holder.textViewTime.setText(checkList.getСheckListTime());
        holder.textViewName.setText(checkList.getСheckListName());
        holder.textViewName.setText(checkList.getСheckListName());


        DateTime today = new DateTime();
        DateTime start = DateTime.parse(checkList.getСheckListStartDate());
        Days days = Days.daysBetween(start, today);



        int duration = Integer.valueOf(checkList.getСheckListDuration());

        int left = days.getDays();
        holder.progressBar.setMax(duration);
        holder.progressBar.setProgress(left);
        holder.textViewPeriod.setText(left + " день из " + duration + " дней");



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, CheckListActivity.class);
                intent.putExtra("checkListID", checkList.getСheckID());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mCheckList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTime;
        public TextView textViewName;
        public TextView textViewPeriod;
        public ProgressBar progressBar;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewName = itemView.findViewById(R.id.textViewDate);
            progressBar = itemView.findViewById(R.id.progressBar);
            textViewPeriod = itemView.findViewById(R.id.textViewComment);

        }
    }
}
