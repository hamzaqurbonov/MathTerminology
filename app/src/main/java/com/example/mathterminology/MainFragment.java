package com.example.mathterminology;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
//import android.view.MenuItem;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;


import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;



public class MainFragment extends Fragment {

    DbHistory dbHistory;
    RecyclerView rview;
    myadapter adapter;
    MenuItem menuItem;
    SearchView searchView;
    Toolbar toolbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =    inflater.inflate(R.layout.fragment_main, container, false);

        toolbar = view.findViewById( R.id.toolbar );

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.setSupportActionBar( toolbar );
        activity.getSupportActionBar().setTitle("");
        rview = view.findViewById(R.id.rview);
        setUpRecyclerView();
        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        dbHistory = new DbHistory(getContext());
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_item,menu);
        menuItem = menu.findItem(R.id.search_1);
        searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setIconified(true);
        searchView.setQueryHint("Qidiruv");

        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                processSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                processSearch(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }



    private void setUpRecyclerView() {


//        rview = findViewById(R.id.rview);
        rview.setLayoutManager(new LinearLayoutManager(getContext()));

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("mathterminology"), model.class)
                        .build();

        adapter = new myadapter(options);
        rview.setAdapter(adapter);

        adapter.setItemClickListner(new myadapter.OnItemClickListner() {
            @Override
            public void onItemClick(DataSnapshot documentSnapshot, int position) {
//                LongModel noteMode = documentSnapshot.toObject(LongModel.class);
//                String dokumentId = documentSnapshot.getId();
//                String path = documentSnapshot.getReference().getPath();
//                Toast.makeText(MainActivity.this,  position + path  + id , Toast.LENGTH_SHORT).show();
//                Log.d("demo22", String.valueOf( path));
                String getWord = adapter.getItem(position).getWord();
                String getTranslate = adapter.getItem(position).getTranslate();

//                String getImageUrl = adapter.getItem(position).getImageUrl();
                Intent intent = new Intent(getContext(), MainActivity2.class);
                intent.putExtra("word", getWord);
                intent.putExtra("translate", getTranslate);

                dbHistory.addNewCourse(getWord, getTranslate);
//                intent.putExtra("dokumentId", dokumentId);
                startActivity(intent);

            }
        });

    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//       getMenuInflater().inflate(R.menu.menu_item,menu);
//
//        MenuItem item = menu.findItem(R.id.search_1);
//
//        SearchView searchView = (SearchView)item.getActionView();
//        searchView.setQueryHint("Qidiruv");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                processSearch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                processSearch(s);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }

    void processSearch(String s) {

        FirebaseRecyclerOptions<model> options =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("mathterminology").orderByChild("word").startAt(s).endAt(s + "\uf8ff") ,model.class)
                        .build();
        adapter = new myadapter(options);
        adapter.startListening();
        rview.setAdapter(adapter);

        adapter.setItemClickListner(new myadapter.OnItemClickListner() {
            @Override
            public void onItemClick(DataSnapshot documentSnapshot, int position) {
//                LongModel noteMode = documentSnapshot.toObject(LongModel.class);
//                String dokumentId = documentSnapshot.getId();
//                String path = documentSnapshot.getReference().getPath();
//                Toast.makeText(MainActivity.this,  position + path  + id , Toast.LENGTH_SHORT).show();
//                Log.d("demo22", String.valueOf( path));
                String getWord = adapter.getItem(position).getWord();
                String getTranslate = adapter.getItem(position).getTranslate();

//                String getImageUrl = adapter.getItem(position).getImageUrl();
                Intent intent = new Intent(getContext(), MainActivity2.class);
                intent.putExtra("word", getWord);
                intent.putExtra("translate", getTranslate);
//                intent.putExtra("dokumentId", dokumentId);
                startActivity(intent);

            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        rview.getRecycledViewPool().clear();
        adapter.notifyDataSetChanged();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();

    }


}