package com.example.mathterminology;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MainAdapter extends extends RecyclerView.Adapter< RecyclerView.ViewHolder> {


private RecyclerViewClickListner listner;
        MainActivity2 activity2;
        List<String> activityllist ;


public Activity2Adapter(MainActivity2 activity2, List<String> activityllist, RecyclerViewClickListner listner) {
        this.activityllist = activityllist;
        this.activity2 = activity2;
        this.listner = listner;
        }


@NonNull
@Override
public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity2_item, parent, false);

        return new Activity2Adapter.Activity2AdapterViewHolder(view);
        }

@Override
public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        TextView Url= ((Activity2AdapterViewHolder) holder).last_name;
        Url.setText(activity2.activityllist.get(position));

        }

@Override
public int getItemCount() {
        int dd =  activity2.activityllist.size();
        return dd;
        }



public class Activity2AdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    View view;
    TextView Url, last_name;

    public Activity2AdapterViewHolder(View v) {
        super(v);
        view = v;

        Url = view.findViewById(R.id.first_2);
        last_name = view.findViewById(R.id.last_2);
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
