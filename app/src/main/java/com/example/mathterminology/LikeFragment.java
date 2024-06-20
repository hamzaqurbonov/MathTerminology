package com.example.mathterminology;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

public class LikeFragment extends Fragment {


    private ArrayList<LakeModel> modalArrayList;
    private DbLike dbLike;
    private LikeAdapter likeAdapter;
    private RecyclerView recyWiewLike;
    Toolbar toolbar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_like, container, false);

        recyWiewLike = view. findViewById(R.id.recyLike);


        toolbar = view.findViewById( R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);


        modalArrayList = new ArrayList<>();
        dbLike = new DbLike(getActivity());
//
        modalArrayList = dbLike.readCourses();
//
        likeAdapter = new LikeAdapter(modalArrayList, getActivity());
//
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        recyWiewLike.setLayoutManager(linearLayoutManager);
//
        recyWiewLike.setAdapter(likeAdapter);

        return view;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_delete,menu);

//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_delete, menu);
//        return super.onCreateOptionsMenu(menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all){
            confirmDialog();
        }
        return super.onOptionsItemSelected(item);

    }

    public void confirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Matinni o'chirish");
        builder.setMessage("Barcha matinni o'chirishni istaysizmi?");
        builder.setPositiveButton("Ha", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
//                DBHandler myDB = new DBHandler(ViewCourses.this);
                dbLike.deleteAllData();
                Toast.makeText(getContext(),  "Barcha matin o'chirildi!", Toast.LENGTH_SHORT).show();
                //Refresh Activity
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);

//                finish();
            }
        });
        builder.setNegativeButton("Yo'q", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }

}