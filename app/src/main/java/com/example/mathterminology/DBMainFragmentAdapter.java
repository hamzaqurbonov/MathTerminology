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

import com.orhanobut.dialogplus.OnItemClickListener;

import java.util.ArrayList;

public class DBMainFragmentAdapter  extends RecyclerView.Adapter<DBMainFragmentAdapter.ViewHolder>  {

    private Context context;
    private OnItemClickListener listner;
    HistoryFragment historyFragment;
    DBMainFragment dbMainFragment ;
    private ArrayList<MainFragmentModel> мodalArrayList;

    private OnItemClickListener listener;



    public DBMainFragmentAdapter(ArrayList<MainFragmentModel> мodalArrayList, Context context ) {
        this.мodalArrayList = мodalArrayList;
        this.context = context;
        dbMainFragment = new DBMainFragment(context);
    }

    @NonNull
    @Override
    public DBMainFragmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new DBMainFragmentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DBMainFragmentAdapter.ViewHolder holder, int position) {
        MainFragmentModel modal = мodalArrayList.get(position);
        holder.courseTracksTV.setText(modal.getWord());


        // Fragmentга click қилиш
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onItemClick(v, position);
                }
            }
        });


    }

    void Refresh(ArrayList<MainFragmentModel> events) {
        мodalArrayList.clear();
        мodalArrayList.addAll(events);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return мodalArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private TextView courseTracksTV, courseIdTest, idTebel;
        ImageView deleteSelect;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//        idTebel = itemView.findViewById(R.id.id_tebel);
            courseTracksTV = itemView.findViewById(R.id.idTVCourseTracks);
            courseIdTest = itemView.findViewById(R.id.idTest);
            deleteSelect = itemView.findViewById(R.id.delete_select);
        }

    }

    // Fragmentга click қилиш
    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

}