package com.example.mathterminology;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

import com.google.firebase.database.DataSnapshot;

public class myadapter extends FirebaseRecyclerAdapter<model,myadapter.myviewholder>{

    private OnItemClickListner listner;
    public myadapter(@NonNull FirebaseRecyclerOptions<model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull model model)
    {
        holder.name.setText(model.getWord());
//        holder.course.setText(model.getTranslate());
//        holder.email.setText(model.getEmail());
//        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder
    {
        CircleImageView img;
        TextView name,course,email;
        public myviewholder(@NonNull View itemView)
        {
            super(itemView);
//            img=(CircleImageView)itemView.findViewById(R.id.img1);
            name=(TextView)itemView.findViewById(R.id.nametext);
//            course=(TextView)itemView.findViewById(R.id.coursetext);
//            email=(TextView)itemView.findViewById(R.id.emailtext);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listner != null) {
                        listner.onItemClick(getSnapshots().getSnapshot(position), position);
                    }
                }
            });
        }
    }

    public interface OnItemClickListner {
        void onItemClick(DataSnapshot documentSnapshot, int position);
    }
    public void setItemClickListner(OnItemClickListner listner) {
        this.listner = listner;
    }
}
