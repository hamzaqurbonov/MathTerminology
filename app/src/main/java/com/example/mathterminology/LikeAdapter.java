package com.example.mathterminology;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LikeAdapter extends RecyclerView.Adapter<LikeAdapter.ViewHolder> {
    private ArrayList<LakeModel> modalArrayList;
    private Context context;

    DbLike dbLike ;

    public LikeAdapter(ArrayList<LakeModel> modalArrayList, Context context) {
        this.modalArrayList = modalArrayList;
        this.context = context;
        dbLike = new DbLike(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.like_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        LakeModel lakeModel  = modalArrayList.get(position);
        holder.Word.setText(lakeModel.getCourseTracks());
        holder.Traslate.setText(lakeModel.getCourseTest());

        holder.deleteSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dbLike.deleteSelect(Integer.toString(lakeModel.getId()));

                Toast.makeText(v.getContext(), "Matin o'chirildi", Toast.LENGTH_SHORT).show();
                Refresh(dbLike.readCourses());
            }

        });

    }

    void Refresh(ArrayList<LakeModel> events) {
        modalArrayList.clear();
        modalArrayList.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return modalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView Traslate, Word ;
        ImageView deleteSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            Traslate = itemView.findViewById(R.id.textLikeIdTraslate);
            Word = itemView.findViewById(R.id.textLikeIdWord);
            deleteSelect = itemView.findViewById(R.id.delete_select);
        }
    }

}
