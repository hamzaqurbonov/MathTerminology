package com.example.mathterminology;

import android.content.Intent;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder>  {

    private Context context;
    HistoryFragment historyFragment;
    DbHistory dbHistory ;
private ArrayList<HistoryModel> courseModalArrayList;


public HistoryAdapter(ArrayList<HistoryModel> courseModalArrayList, Context context) {
        this.courseModalArrayList = courseModalArrayList;
        this.context = context;
        dbHistory = new DbHistory(context);
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
//        holder.idTebel.setText(Integer.toString(modal.getId()));


    holder.deleteSelect.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            // calling a method to delete our course.
            dbHistory.deleteSelect(Integer.toString(modal.getId()));

//            Log.d("demo1", String.valueOf(modal.getId()));
//            Log.d("demo1", modal.getCourseTracks());


            Toast.makeText(v.getContext(), "Deleted the course", Toast.LENGTH_SHORT).show();
//            HistoryFragment.onResume();

        }
    });




        }

@Override
public int getItemCount() {
        return courseModalArrayList.size();
        }

public class ViewHolder extends RecyclerView.ViewHolder {
    private TextView  courseTracksTV, courseIdTest, idTebel;
    ImageView deleteSelect;


    public ViewHolder(@NonNull View itemView) {
        super(itemView);
//        idTebel = itemView.findViewById(R.id.id_tebel);
        courseTracksTV = itemView.findViewById(R.id.idTVCourseTracks);
        courseIdTest = itemView.findViewById(R.id.idTest);
        deleteSelect = itemView.findViewById(R.id.delete_select);
    }
}

}
