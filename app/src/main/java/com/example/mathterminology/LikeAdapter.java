package com.example.mathterminology;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder> {
    private ArrayList<LakeModel> courseModalArrayList;
    private Context context;

    public LikeAdapter(ArrayList<LakeModel> courseModalArrayList, Context context) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public LikeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.like_item, parent, false);
        return new LikeAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LikeAdapter.ViewHolder holder, int position) {
        LakeModel lakeModel  = courseModalArrayList.get(position);
        holder.courseTracksTV.setText(lakeModel.getCourseTracks());
        holder.courseIdTest.setText(lakeModel.getCourseTest());
    }

    @Override
    public int getItemCount() {
        return courseModalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView courseTracksTV, courseIdTest ;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            courseTracksTV = itemView.findViewById(R.id.textLikeIdTraslate);
            courseIdTest = itemView.findViewById(R.id.textLikeIdWord);
        }
    }

}
