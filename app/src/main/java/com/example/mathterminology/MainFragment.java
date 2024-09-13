package com.example.mathterminology;

import static android.content.ContentValues.TAG;

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
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class MainFragment extends Fragment {
    SwipeRefreshLayout swipeRefreshLayout;
    DbHistory dbHistory;
    RecyclerView rview;
    myadapter adapter;
    MenuItem menuItem;
    SearchView searchView;
    Toolbar toolbar;
    ProgressBar progressBar;

    boolean isLoading = false; // Юклаш жараёни учун
    String lastKey = null; // Pagination учун охирги элемент калити

    FirebaseRecyclerOptions<model> options;
    Query query;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setTitle("");

        rview = view.findViewById(R.id.rview);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        progressBar = view.findViewById(R.id.progressBar);

        setUpRecyclerView();
        setUpSwipeRefresh();

        return view;
    }

    private void setUpRecyclerView() {
//        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);
        FirebaseRecyclerOptions<model> searchOptions =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference()
                                .child("mathterminology")
                                .limitToFirst(100), model.class)
                        .build();
        Log.d("demo41", "onDataChange1:  " + lastKey);
        adapter = new myadapter(searchOptions);
        rview.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.startListening();
        rview.setAdapter(adapter);

        // Кейинги маълумотларни юклаш учун скроллинг кузатувчиси
        rview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                progressBar.setVisibility(View.GONE);
                if (!isLoading && linearLayoutManager != null &&
                        linearLayoutManager.findLastVisibleItemPosition() == adapter.getItemCount() - 1) {
                    loadMoreData(); // Кейинги маълумотларни юклаш
                }
            }
        });

        adapter.setItemClickListner(new myadapter.OnItemClickListner() {
            @Override
            public void onItemClick(DataSnapshot documentSnapshot, int position) {
                String getWord = adapter.getItem(position).getWord();
                String getTranslate = adapter.getItem(position).getTranslate();

                Intent intent = new Intent(getContext(), MainActivity2.class);
                intent.putExtra("word", getWord);
                intent.putExtra("translate", getTranslate);

                dbHistory.addNewCourse(getWord, getTranslate);
                startActivity(intent);
            }
        });
    }

    private void loadMoreData() {
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        Query newQuery = FirebaseDatabase.getInstance().getReference().child("mathterminology").startAt(lastKey);

        newQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Охирги элементнинг калитини олиш
                        lastKey = snapshot.getKey();
                        Log.d("demo41", "onDataChange2:  " + lastKey);

                    }
                    // Янги маълумотларни қўшиш учун адаптерни янгилаш
                    FirebaseRecyclerOptions<model> newOptions = new FirebaseRecyclerOptions.Builder<model>()
                            .setQuery(newQuery, model.class)
                            .build();
                    rview.getRecycledViewPool().clear();

                    adapter.updateOptions(newOptions); // Адаптерга янги маълумотларни бериш
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE); // Маълумотлар юкланганда
                    isLoading = false;
                } else {
                    progressBar.setVisibility(View.GONE); // Агар маълумот топилмаса
                    isLoading = false;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressBar.setVisibility(View.GONE);
                isLoading = false;
            }
        });
    }

    private void setUpSwipeRefresh() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

//                setUpRecyclerView();
                loadMoreData();

                // Янгидан юкланишни бошлаш
//                setUpRecyclerView();
                // Юкланиш тугаганда анимацияни тўхтатиш
                swipeRefreshLayout.setRefreshing(false);
            }
        });
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

    void processSearch(String s) {
        FirebaseRecyclerOptions<model> searchOptions =
                new FirebaseRecyclerOptions.Builder<model>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("mathterminology")
                                .orderByChild("word")
                                .startAt(s.toLowerCase())
                                .endAt(s.toLowerCase() + "\uf8ff"), model.class)
                        .build();
        adapter = new myadapter(searchOptions);
        adapter.startListening();
        rview.setAdapter(adapter);

        adapter.setItemClickListner(new myadapter.OnItemClickListner() {
            @Override
            public void onItemClick(DataSnapshot documentSnapshot, int position) {
                String getWord = adapter.getItem(position).getWord();
                String getTranslate = adapter.getItem(position).getTranslate();

                Intent intent = new Intent(getContext(), MainActivity2.class);
                intent.putExtra("word", getWord);
                intent.putExtra("translate", getTranslate);
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
