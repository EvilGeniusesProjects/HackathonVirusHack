package com.evilgeniuses.lifecare.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.activities.DiaryActivity;
import com.evilgeniuses.lifecare.activities.MonitoringActivity;
import com.evilgeniuses.lifecare.models.Diary;
import com.evilgeniuses.lifecare.models.Monitoring;

import java.util.List;

public class MonitoringAdapter extends RecyclerView.Adapter<MonitoringAdapter.ViewHolder>{

    private Context mContext;
    private List<Monitoring> mMonitoring;
    private boolean ischat;


    public MonitoringAdapter(Context mContext, List<Monitoring> mMonitoring, boolean ischat){
        this.mMonitoring = mMonitoring;
        this.mContext = mContext;
        this.ischat = ischat;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_monitoring, parent, false);
        return new MonitoringAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Monitoring monitoring = mMonitoring.get(position);



        holder.textViewDate.setText(monitoring.getDate().substring(0, 10));
        holder.textViewTime.setText(monitoring.getDate().substring(11, 16));
        holder.textViewComment.setText(monitoring.getComment());
        holder.ratingBar.setProgress(Integer.valueOf(monitoring.getFeeling()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, MonitoringActivity.class);
                intent.putExtra("monitoringID", monitoring.getID());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mMonitoring.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewDate;
        public TextView textViewTime;
        public TextView textViewComment;
        public RatingBar ratingBar;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }
    }
}
