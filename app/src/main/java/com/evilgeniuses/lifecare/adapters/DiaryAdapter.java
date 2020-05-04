package com.evilgeniuses.lifecare.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.evilgeniuses.lifecare.R;
import com.evilgeniuses.lifecare.activities.DiaryActivity;
import com.evilgeniuses.lifecare.models.Diary;

import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder>{

    private Context mContext;
    private List<Diary> mDiary;
    private boolean ischat;


    public DiaryAdapter(Context mContext, List<Diary> mDiary, boolean ischat){
        this.mDiary = mDiary;
        this.mContext = mContext;
        this.ischat = ischat;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_diary, parent, false);
        return new DiaryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final Diary diary = mDiary.get(position);


        if (diary.getImgage1().equals("STANDARD")){
            holder.imageView.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(diary.getImgage1()).override(256, 256).into(holder.imageView);
        }

        holder.textViewDate.setText(diary.getDate().substring(0, 10));
        holder.textViewTime.setText(diary.getDate().substring(11, 16));
        holder.textViewComment.setText(diary.getComment());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, DiaryActivity.class);
                intent.putExtra("diaryID", diary.getId());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDiary.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewDate;
        public TextView textViewTime;
        public TextView textViewComment;
        public ImageView imageView;


        public ViewHolder(View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewTime = itemView.findViewById(R.id.textViewTime);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
