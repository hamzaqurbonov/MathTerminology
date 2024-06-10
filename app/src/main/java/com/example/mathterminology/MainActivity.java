package com.example.mathterminology;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    RecyclerView rview;
    myadapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        setSupportActionBar(findViewById(R.id.toolbar));
//        getSupportActionBar().setTitle("");

//        setUpRecyclerView();

        BottomNavigationView bottomNav = findViewById(R.id.botton_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.body_container, new MainFragment()).commit();



    }

    private  final BottomNavigationView.OnNavigationItemSelectedListener navListener = item -> {
        // By using switch we can easily get
        // the selected fragment
        // by using there id.
        Fragment selectedFragment = null;
        int itemId = item.getItemId();
        if (itemId == R.id.nav_main) {
            selectedFragment = new MainFragment();

        } else if (itemId == R.id.nav_live) {
            selectedFragment = new HistoryFragment();

        }
//            else if (itemId == R.id.nav_favorite) {
//                selectedFragment = new NotificationsFragment();
//
//            }
        // It will help to replace the
        // one fragment to other.
        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.body_container, selectedFragment).commit();
        }
        return true;
    };
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        rview.getRecycledViewPool().clear();
//        adapter.notifyDataSetChanged();
//        adapter.startListening();
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        adapter.stopListening();
//
//    }
//
//    private void setUpRecyclerView() {
//        rview = findViewById(R.id.rview);
//        rview.setLayoutManager(new LinearLayoutManager(this));
//
//        FirebaseRecyclerOptions<model> options =
//                new FirebaseRecyclerOptions.Builder<model>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("mathterminology"), model.class)
//                        .build();
//
//        adapter = new myadapter(options);
//        rview.setAdapter(adapter);
//
//        adapter.setItemClickListner(new myadapter.OnItemClickListner() {
//            @Override
//            public void onItemClick(DataSnapshot documentSnapshot, int position) {
////                LongModel noteMode = documentSnapshot.toObject(LongModel.class);
////                String dokumentId = documentSnapshot.getId();
////                String path = documentSnapshot.getReference().getPath();
////                Toast.makeText(MainActivity.this,  position + path  + id , Toast.LENGTH_SHORT).show();
////                Log.d("demo22", String.valueOf( path));
//                String getWord = adapter.getItem(position).getWord();
//                String getTranslate = adapter.getItem(position).getTranslate();
//
////                String getImageUrl = adapter.getItem(position).getImageUrl();
//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                intent.putExtra("word", getWord);
//                intent.putExtra("translate", getTranslate);
////                intent.putExtra("dokumentId", dokumentId);
//                startActivity(intent);
//
//            }
//        });
//
//    }
//
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_item,menu);
//
//        MenuItem item = menu.findItem(R.id.search_1);
//
//        SearchView searchView = (SearchView)item.getActionView();
//        searchView.setQueryHint("Qidiruv");
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String s) {
//                mainFragment.processSearch(s);
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String s) {
//                mainFragment.processSearch(s);
//                return false;
//            }
//        });
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    private void processSearch(String s) {
//
//        FirebaseRecyclerOptions<model> options =
//                new FirebaseRecyclerOptions.Builder<model>()
//                        .setQuery(FirebaseDatabase.getInstance().getReference().child("mathterminology").orderByChild("word").startAt(s).endAt(s + "\uf8ff") ,model.class)
//                        .build();
//        adapter = new myadapter(options);
//        adapter.startListening();
//        rview.setAdapter(adapter);
//
//        adapter.setItemClickListner(new myadapter.OnItemClickListner() {
//            @Override
//            public void onItemClick(DataSnapshot documentSnapshot, int position) {
////                LongModel noteMode = documentSnapshot.toObject(LongModel.class);
////                String dokumentId = documentSnapshot.getId();
////                String path = documentSnapshot.getReference().getPath();
////                Toast.makeText(MainActivity.this,  position + path  + id , Toast.LENGTH_SHORT).show();
////                Log.d("demo22", String.valueOf( path));
//                String getWord = adapter.getItem(position).getWord();
//                String getTranslate = adapter.getItem(position).getTranslate();
//
////                String getImageUrl = adapter.getItem(position).getImageUrl();
//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                intent.putExtra("word", getWord);
//                intent.putExtra("translate", getTranslate);
////                intent.putExtra("dokumentId", dokumentId);
//                startActivity(intent);
//
//            }
//        });
//    }
}