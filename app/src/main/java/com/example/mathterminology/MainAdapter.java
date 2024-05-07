package com.example.mathterminology;

import static com.google.firebase.database.DatabaseKt.getSnapshots;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter< RecyclerView.ViewHolder> {


    public RecyclerViewClickListner listner;
    MainActivity activity;

    ArrayList<String> nextArrayList;


    public MainAdapter(ArrayList<String> nextArrayList, RecyclerViewClickListner listner) {
        this.nextArrayList = nextArrayList;
        this.activity = activity;
        this.listner = listner;
    }




    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        return new MainAdapter.Activity2AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TextView Url= ((Activity2AdapterViewHolder) holder).first_2;
        Url.setText(nextArrayList.get(position));

    }

    @Override
    public int getItemCount() {
        int dd =  nextArrayList.size();
        Log.d("demo21", "nextArrayList " + nextArrayList +"  "+ dd);
        return dd;
    }






//    class LongHolder extends RecyclerView.ViewHolder {
//        TextView first_2, last_2;
////        ImageView imgChildItem;
//
//        public LongHolder(View itemView) {
//            super(itemView);
//            first_2 = itemView.findViewById(R.id.first_2);
////            Url = itemView.findViewById(R.id.first_name);
////            imgChildItem = itemView.findViewById(R.id.img_child_item);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION && listner != null) {
//                        listner.onItemClick(getSnapshots().getSnapshot(position), position);
//                    }
//                }
//            });
//        }
//    }
//
//    public interface OnItemClickListner {
//        void onItemClick(DocumentSnapshot documentSnapshot, int position);
//    }
//    public void setItemClickListner(OnItemClickListner listner) {
//        this.listner = listner;
//    }





    public class Activity2AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        View view;
        TextView first_2, last_2;

        public Activity2AdapterViewHolder(View v) {
            super(v);
            view = v;

            first_2 = view.findViewById(R.id.first_2);
            last_2 = view.findViewById(R.id.last_2);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listner.onClick(view, getAdapterPosition());
        }

    }

    public interface RecyclerViewClickListner {
        void onClick(View v, int position);
    }
}
