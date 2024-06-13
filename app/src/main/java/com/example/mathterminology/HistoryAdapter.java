package com.example.mathterminology;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>  {

private ArrayList<HistoryModel> courseModalArrayList;
private Context context;

public HistoryAdapter(ArrayList<HistoryModel> courseModalArrayList, Context context) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
        }

@NonNull
@Override
public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_item, parent, false);
        return new ViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    HistoryModel modal = courseModalArrayList.get(position);
        holder.courseTracksTV.setText(modal.getCourseTracks());
        holder.courseIdTest.setText(modal.getCourseTest());
        }

@Override
public int getItemCount() {
        return courseModalArrayList.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView  courseTracksTV, courseIdTest ;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        courseTracksTV = itemView.findViewById(R.id.idTVCourseTracks);
        courseIdTest = itemView.findViewById(R.id.idTest);
    }
}

}
